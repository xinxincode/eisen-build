package org.eisen.dao.mybatis;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.metrics.MetricsTrackerFactory;
import com.zaxxer.hikari.metrics.dropwizard.CodahaleMetricsTrackerFactory;
import org.apache.ibatis.io.Resources;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @Author Eisen
 * @Date 2018/12/15 20:12
 * @Description:
 **/
public class DataMybatis {
    public static void main(String[] args) throws Exception {
        new DataMybatis().getDataSource();

    }

    public void my() throws IOException {
        Reader reader = Resources.getResourceAsReader("");
//        SqlSessionFactory ssf = new SqlSessionFactoryBuilder().build();
    }

    public void m() {


//        DataSource dataSource = BlogDataSourceFactory.getBlogDataSource();
//        TransactionFactory transactionFactory = new JdbcTransactionFactory();
//        Environment environment = new Environment("development", transactionFactory, dataSource);
//        Configuration configuration = new Configuration(environment);
//        configuration.addMapper(BlogMapper.class);
//        configuration.add
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    }

    public void getDataSource() throws SQLException, InterruptedException, JsonProcessingException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://192.168.200.177:3306/eisendb");
        config.setUsername("eisen");
        config.setPassword("eisen");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");

        //连接池名字 用于日志打印前缀
        config.setPoolName("p1");

        //自动提交 默认true
        config.setAutoCommit(true);


        /**
         *  客户端等待池中连接的最大毫秒数,如果在没有连接可用的情况下超时抛出SQLException
         *  最低250毫秒,默认值：30000(30秒)
         */
        config.setConnectionTimeout(30000);

        /**
         * 允许连接空闲的时间
         * 链接空闲时间超过设定值,且连接池中的连接数超过设定的最小值则移除
         * 允许的最小值为10000毫秒（10秒）, 默认值：600000（10分钟）
         */
        config.setIdleTimeout(600000);

        /**
         * 此属性控制连接的最长生命周期。
         * 使用中的连接永远不会退役，只有当它关闭时才会被删除。
         * 建议设置此值，它应该比数据库的最大连接时间小几秒
         * 值0表示无限，当然也受制于idleTimeout值的设置。
         * 默认值：1800000（30分钟）
         */
        config.setMaxLifetime(1800000);

        /**
         * 如果您的驱动程序支持JDBC4，我们强烈建议您不要设置此属性。
         * 这适用于不支持JDBC4的“遗留”驱动程序Connection.isValid() API。
         * 这是在从池中给出连接之前执行的查询，以验证与数据库的连接是否仍然存在。
         * 再次尝试运行没有此属性的池，如果您的驱动程序不符合JDBC4，HikariCP将记录错误以通知您。
         * 默认值：无
         */
//        config.setConnectionTestQuery(null);

        /**
         * 此属性控制HikariCP尝试在池中维护的最小空闲连接数。
         * 如果空闲连接低于此值并且池中的总连接数小于maximumPoolSize，则HikariCP将尽最大努力快速有效地添加其他连接。
         * 但是，为了获得最高性能和对峰值需求的响应，我们建议不要设置此值，而是允许HikariCP充当固定大小的连接池。
         * 默认值：与maximumPoolSize相同
         */
//        config.setMinimumIdle();

        /**
         * 此属性控制允许池到达的最大大小，包括空闲和正在使用的连接。
         * 基本上，此值将确定到数据库后端的实际连接的最大数量。
         * 对此的合理值最好由您的执行环境决定。
         * 当池达到此大小且没有空闲连接可用时，对getConnection()的调用将阻塞最多connectionTimeout毫秒。
         *
         * 避免死锁所需的最小值（并非最佳值）：
         * Tn x（Cm - 1）+ 1
         * Tn是最大线程数，Cm是单个线程持有的最大同时连接数。
         *
         * 默认值：10
         */
        config.setMaximumPoolSize(2);

        /**
         * 设置监控
         */
        MetricRegistry mr = new MetricRegistry();
//        config.setMetricRegistry(mr);//cannot use setMetricsTrackerFactory() and setMetricRegistry() together
        MetricsTrackerFactory mtf = new CodahaleMetricsTrackerFactory(mr);
        config.setMetricsTrackerFactory(mtf);
        config.addHealthCheckProperty("connectivityCheckTimeoutMs", "1000");
        config.addHealthCheckProperty("expected99thPercentileMs", "10");

        /**
         * 连接泄露检测 连接离开连接池的时间
         * 如果超过该时间 连接池会在子线程中抛出一个异常 不会影响主线程的执行
         * 开发时启用，生产时关闭
         * 默认0 不开启 最低 5000 毫秒
         */
        config.setLeakDetectionThreshold(5000);


        //sql缓存开启
        config.addDataSourceProperty("cachePrepStmts", "true");
        //每个连接预处理sql缓存的数量 最好设置在250-500之间
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        //缓存预处理sql的最大长度 mysql默认256 最好2048
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        //新版的mysql 可以设置在服务端缓存预处理的sql 用新版mysql的话可以加入这个
        config.addDataSourceProperty("useServerPrepStmts", "true");

        //官网描述提升性能
        config.addDataSourceProperty("useLocalSessionState", "true");
        config.addDataSourceProperty("rewriteBatchedStatements", "true");
        config.addDataSourceProperty("cacheResultSetMetadata", "true");
        config.addDataSourceProperty("cacheServerConfiguration", "true");
        config.addDataSourceProperty("elideSetAutoCommits", "true");
        config.addDataSourceProperty("rewriteBatchedStatements", "true");
        config.addDataSourceProperty("maintainTimeStats", "false");
        HikariDataSource ds = new HikariDataSource(config);
        Connection conn = ds.getConnection();
        conn.close();
        conn = ds.getConnection();

        Statement stat = conn.createStatement();
        stat.execute("select * from tb_file_detail");
        Thread.sleep(2000);

//        mr = ds.getHikariConfigMXBean()

        com.codahale.metrics.Timer tm = mr.timer("p1.pool.Wait");//请求获取连接等待时长
        long[] l = tm.getSnapshot().getValues();
        Gson gson = new GsonBuilder().create();
        System.out.println(gson.toJson(l) + "   " + tm.getCount());


        Histogram hg = mr.histogram("p1.pool.Usage");//客户端使用连接的时长
        l = hg.getSnapshot().getValues();
        System.out.println(gson.toJson(l));

        Gauge cg = mr.getGauges().get("p1.pool.TotalConnections");//连接总数
        System.out.println(cg.getValue());
        cg = mr.getGauges().get("p1.pool.IdleConnections");//空闲连接数
        System.out.println(cg.getValue());
        cg = mr.getGauges().get("p1.pool.IdleConnections");//使用中的连接数
        System.out.println(cg.getValue());
        cg = mr.getGauges().get("p1.pool.PendingConnections");//请求获取连接的等待线程数量
        System.out.println(cg.getValue());
        //ds.getHikariConfigMXBean(); ds.getHikariPoolMXBean(); 运行时更改配置




        ResultSet rs = stat.getResultSet();
        int r = rs.getMetaData().getColumnCount();
        while (rs.next()) {
            for (int i = 1; i <= r; i++) {
                System.out.print(rs.getObject(i));
                System.out.print("  ");
            }
            System.out.println();
            System.out.println("======>>");
        }
        Thread.sleep(2000);
        rs.close();
        stat.close();
        conn.close();

    }

}
