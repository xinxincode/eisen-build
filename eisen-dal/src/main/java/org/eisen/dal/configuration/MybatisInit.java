package org.eisen.dal.configuration;

import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.eisen.bios.fileopreate.ReadFile;
import org.eisen.bios.utils.StringUtils;
import org.eisen.dal.datasource.DataSourceFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

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
public class MybatisInit {

    public static final Map<String, DataSource> dataSourceFactoryManagerMap = new HashMap<>();
    public static final Map<String, SqlSessionFactory> sqlSessionFactoryManagerMap = new HashMap<String, SqlSessionFactory>();
    public static final Map<String, TransactionFactory> transactionFactoryManagerMap = new HashMap<String, TransactionFactory>();
    public static final Map<String, SqlSessionTemplate> sqlSessionTemplateManagerMap = new HashMap<String, SqlSessionTemplate>();
    public static final Map<String, DataSourceTransactionManager> dataSourceTransactionManagerMap = new HashMap<String, DataSourceTransactionManager>();


    /**
     * 传入一个mapper 返回该mapper对应的id
     *
     * @throws ClassNotFoundException
     */
    public static String getMapperDBId(Class clz) throws ClassNotFoundException {
        if (clz == null) {
            throw new RuntimeException("传入类型不能为空");
        }
        Set<String> keys = sqlSessionTemplateManagerMap.keySet();
        Configuration conf;
        for (String key : keys) {
            conf = sqlSessionFactoryManagerMap.get(key).getConfiguration();
            if (conf.getMapperRegistry().hasMapper(clz)) {
                return conf.getEnvironment().getId();
            }
        }
        throw new ClassNotFoundException("未找到对应mapper id");
        /* 另一种获取mapper id的方式
         * 考虑如果以后多数据源共享一个mapper目录可能会用到吧 好像没太有必要 不共享就行
        Set<String> keys = mapperManagerMap.keySet();
        for (String key : keys) {
            if (mapperManagerMap.get(key).contains(obj)) {
                return key;
            }
        }
        throw new ClassNotFoundException("未找到对应mapper id");
        * */

    }


    public TransactionStatus transaction(String id) {
        //1.获取事务控制管理器
        DataSourceTransactionManager transactionManager = dataSourceTransactionManagerMap.get(id);
        //2.获取事务定义
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //3.设置事务隔离级别，开启新事务
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        //4.获得事务状态
        TransactionStatus status = transactionManager.getTransaction(def);
        return status;
    }


    /**
     * @param em 格式如下:
     *           文件路径?前缀 多个以 , 分隔
     *           path?prefeix,path
     * @throws IOException
     */
    public synchronized static void initMybatis(String em) throws IOException {
        String[] ms = em.trim().split(",");
        for (String s : ms) {
            if (StringUtils.isEmpty(s)) {
                continue;
            }
            String[] pathPrefix = s.trim().split("\\?");
            String path = pathPrefix[0];
            String prefix = "";
            if (pathPrefix.length >= 2) {
                prefix = pathPrefix[1];
            }
            initMybatis(path, prefix);
        }
    }

    /**
     * 根据配置文件初始化mybatis
     * 1.会初始化一个数据库连接池
     * 2.会初始化myabts sqlSession factory等bean
     * 3.初始化mapper并交给spring容器 单例管理
     *
     * @param path
     * @param prefix
     * @throws IOException
     */
    public synchronized static void initMybatis(String path, String prefix) throws IOException {
        /*
         * id为配置文件中的 'id' 的值 id不可重复
         * 若id为空则生成一个默认的id 为配置文件路径('/'被替换为'.')+.前缀
         * 1.如果没指定数据库poolname 则poolname为这个id
         * 2.此id为mybatis的环境id
         * 3.此id为所有此类静态map的key
         */
        Map props = ReadFile.readProjectProps(path, prefix);
        String id = (String) props.get("id");
        if (dataSourceFactoryManagerMap.containsKey(id)) {
            throw new RuntimeException("已存在此数据库id:" + id);
        }
        if (StringUtils.isEmpty(id)) {
            id = path.replace("/", ".").replace("\\", ".") + (StringUtils.isEmpty(prefix) ? "" : "." + prefix);
        }
        if (!props.containsKey("poolname")) {
            props.put("poolname", id);
        }
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
            throw new RuntimeException("请指定mapper包名 多个以 , 隔开 如 mapper-locations=org.mapper1,org.mapper2");
        }
        String[] ps = packageNames.split(",");

        for (String s : ps) {
            if (StringUtils.isEmpty(s)) {
                continue;
            }
            conf.addMappers(s);

        }
        //通过SqlSessionFactoryBuilder生成SqlSession工厂
        SqlSessionFactory ssf = new SqlSessionFactoryBuilder().build(conf);
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
            for (Class<?> clz : cc) {
                //向spring容器中注册单例
                ApplicationContextProvider.defaultListableBeanFactory.registerSingleton(clz.getName(), sst.getMapper(clz));
            }
        }

        //放入静态map中方便以后用
        dataSourceFactoryManagerMap.put(id, ds);
        transactionFactoryManagerMap.put(id, smtf);
        sqlSessionFactoryManagerMap.put(id, ssf);
        sqlSessionTemplateManagerMap.put(id, sst);
        dataSourceTransactionManagerMap.put(id, dtm);
    }


}