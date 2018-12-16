package org.eisen.dao.datasource;


import com.codahale.metrics.MetricRegistry;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.metrics.MetricsTrackerFactory;
import com.zaxxer.hikari.metrics.dropwizard.CodahaleMetricsTrackerFactory;

import javax.sql.DataSource;

/**
 * @Author Eisen
 * @Date 2018/12/16 23:04
 * @Description:
 **/
public class HikariDataSourceBuilder {


    private String url;
    private String username;
    private String password;
    private String driver;
    private String poolname;
    HikariConfig config = new HikariConfig();


    public HikariDataSourceBuilder url(String var) {
        this.url = var;
        return this;
    }

    public HikariDataSourceBuilder username(String var) {
        this.username = var;
        return this;
    }

    public HikariDataSourceBuilder password(String var) {
        this.password = var;
        return this;
    }

    public HikariDataSourceBuilder driver(String var) {
        this.driver = var;
        return this;
    }

    public HikariDataSourceBuilder poolname(String var) {
        //连接池名字 用于日志打印前缀
        this.poolname = var;
        return this;
    }


    private void ssNotNull(String... ss) {

        if (ss == null || ss.length == 0) {
            throw new RuntimeException("This value can't be null");
        }

        for (String s : ss) {
            if (s == null || s.equals("")) {
                throw new RuntimeException("This value can't be null");
            }
        }

    }


    public HikariDataSource build() {

        ssNotNull(url, driver, username, password, poolname);
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driver);
        config.setPoolName(poolname);

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

        return ds;
    }


}
