package org.eisen.dal.configuration;

import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.apache.ibatis.transaction.TransactionFactory;
import org.eisen.bios.fileopreate.ReadFile;
import org.eisen.bios.utils.StringUtils;
import org.eisen.bios.utils.UrlUtils;
import org.eisen.dal.configuration.datasource.DataSourceFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author Eisen
 * @Date 2018/12/21 20:44
 * @Description:
 **/
public class DalInit {

    public static final Map<String, DataSource> dataSourceFactoryManagerMap = new HashMap<>();
    public static final Map<String, DefaultSqlSessionFactory> sqlSessionFactoryManagerMap = new HashMap<>();
    public static final Map<String, TransactionFactory> transactionFactoryManagerMap = new HashMap<>();
    public static final Map<String, SqlSessionTemplate> sqlSessionTemplateManagerMap = new HashMap<>();
    public static final Map<String, DataSourceTransactionManager> dataSourceTransactionManagerMap = new HashMap<>();
    public static final Map<String, Map<String, Object>> mapperManagerMap = new HashMap<>();


    /**
     * 传入一个mapper 返回该mapper对应的id
     */
    public static String getMapperDBId(Object mapper) {

        if (mapper == null) {
            return null;
        }
        Set<String> keys = mapperManagerMap.keySet();
        for (String key : keys) {
            Map<String, Object> map = mapperManagerMap.get(key);
            if (map.containsValue(mapper)) {
                return key;
            }
        }
        return null;
    }


    /**
     * @param em 格式如下:
     *           文件路径?前缀 多个以 , 分隔
     *           path/db1.txt?prefix=hikari&id=db1,path/db.properties
     *           可以在路径后拼参数 若存在同名参数则会覆盖配置文件中的参数
     *           prefix为获取配置文件指定前缀的参数(key为去掉前缀后的key) 路径拼接的参数则不会去掉前缀
     *
     *           id为配置文件中的 'id' 的值 id不可重复
     *           若id为空则生成一个默认的id 为配置文件路径('/'被替换为'.')+.前缀
     *           1.如果没指定数据库poolname 则poolname为这个id
     *           2.此id为mybatis的环境id
     *           3.此id为所有此类静态map的key
     * @throws IOException
     */
    public synchronized static void initMybatis(String em) throws IOException {
        String[] urls = em.trim().split(",");
        for (String url : urls) {
            if (StringUtils.isEmpty(url)) {
                continue;
            }
            String path = UrlUtils.getDomain(url);
            Map<String, String> params = UrlUtils.getParams(url);
            String prefix = null;
            boolean isNotNull = params != null;
            if (isNotNull) {
                prefix = params.get("prefix");
            }
            Map props = ReadFile.readProjectProps(path, prefix);
            if (isNotNull) {
                props.putAll(params);
            }
            String id = (String) props.get("id");
            //如果id为空默认指定一个id
            if (StringUtils.isEmpty(id)) {
                id = path.replace("/", ".").replace("\\", ".") + (StringUtils.isEmpty(prefix) ? "" : "." + prefix);
            }
            if (dataSourceFactoryManagerMap.containsKey(id)) {
                throw new DalException("已存在此数据库id:" + id);
            }
            if (!props.containsKey("poolname")) {
                props.put("poolname", id);
            }
            initMybatis(props);
        }
    }

    /**
     * 根据配置文件初始化mybatis
     * 1.会初始化一个数据库连接池
     * 2.会初始化myabts sqlSession factory等bean
     * 3.初始化mapper并交给spring容器 单例管理
     *
     * @param props
     * @throws IOException
     */
    public synchronized static void initMybatis(Map<String, String> props) throws IOException {
        String id = props.get("id");
        //获取数据库连接池
        DataSource ds = DataSourceFactory.getDataSource(props);
        //获取一个spring的数据库连接池的事务管理
        DataSourceTransactionManager dtm = new DataSourceTransactionManager(ds);
        //新建一个spring的事务管理工厂
        SpringManagedTransactionFactory smtf = new SpringManagedTransactionFactory();
        //初始化mybatis配置bean
        Configuration conf = new Configuration(new Environment(id, smtf, ds));
        //处理mapper包路径告诉mybatis扫描哪个包下的mapper
        String packageNames = (String) props.get("mapper-locations");
        if (StringUtils.isEmpty(packageNames)) {
            throw new DalException("请指定mapper包名 多个以 , 隔开 如 mapper-locations=org.mapper1,org.mapper2");
        }
        String[] ps = packageNames.split(",");

        for (String s : ps) {
            if (StringUtils.isEmpty(s)) {
                continue;
            }
            conf.addMappers(s);
        }
        //通过SqlSessionFactoryBuilder生成SqlSession工厂
        DefaultSqlSessionFactory ssf = (DefaultSqlSessionFactory) new SqlSessionFactoryBuilder().build(conf);
        //通过配置文件读取SqlSessionTemplate的ExecutorType执行方式 默认为REUSE
        ExecutorType et = ExecutorType.REUSE;
        for (ExecutorType e : ExecutorType.values()) {
            if (e.name().equals(props.get("ExecutorType"))) {
                et = e;
                break;
            }
        }
        //生成SqlSessionTemplate
        SqlSessionTemplate sst = new SqlSessionTemplate(ssf, et);
        //获取所有已生成的mapper 在spring容器中注册为单例
        MapperRegistry mr = sst.getSqlSessionFactory().getConfiguration().getMapperRegistry();
        Collection<Class<?>> cc = mr.getMappers();
        //存放所有的mapper
        if (cc != null) {
            Map<String, Object> mapperMap = new HashMap<>();
            Object mapper;
            for (Class<?> clz : cc) {
                mapper = sst.getMapper(clz);
                //向spring容器中注册单例
                ApplicationContextProvider.defaultListableBeanFactory.registerSingleton(clz.getName(), mapper);
                mapperMap.put(clz.getName(), mapper);
            }
            //将mapper存入静态map方便管理
            mapperManagerMap.put(id, mapperMap);
        }
        //放入静态map中方便以后用
        dataSourceFactoryManagerMap.put(id, ds);
        transactionFactoryManagerMap.put(id, smtf);
        sqlSessionFactoryManagerMap.put(id, ssf);
        sqlSessionTemplateManagerMap.put(id, sst);
        dataSourceTransactionManagerMap.put(id, dtm);

    }


}
