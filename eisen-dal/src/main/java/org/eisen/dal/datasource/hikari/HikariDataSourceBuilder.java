package org.eisen.dal.datasource.hikari;


import com.codahale.metrics.MetricRegistry;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.metrics.MetricsTrackerFactory;
import com.zaxxer.hikari.metrics.dropwizard.CodahaleMetricsTrackerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @Author Eisen
 * @Date 2018/12/16 23:04
 * @Description:
 **/
public class HikariDataSourceBuilder {


    private String url;                         //链接地址
    private String username;                    //用户名
    private String password;                    //密码
    private String driver;                      //驱动
    private String poolname;                    //连接池名字

    private boolean isAutoCommit = true;        //自动提交
    private long connectionTimeout = 30000L;    //客户端获取链接超时时间
    private long idleTimeout = 600000L;         //连接最大空闲时间
    private long maxLifetime = 1800000L;        //连接最大存在时间
    private int minIdle = -1;                   //连接池最小存在连接数默认与maxPoolSize相同
    private int maxPoolSize = 10;               //连接池最大容量
    private long leakDetectionThreshold = 0L;   //连接离开连接池的最大时间,超时后抛异常但不影响

    private MetricsTrackerFactory mtf;


    HikariConfig config = new HikariConfig();


    HikariDataSourceBuilder url(String var) {
        this.url = var;
        return this;
    }

    HikariDataSourceBuilder username(String var) {
        this.username = var;
        return this;
    }

    HikariDataSourceBuilder password(String var) {
        this.password = var;
        return this;
    }

    HikariDataSourceBuilder driver(String var) {
        this.driver = var;
        return this;
    }

    HikariDataSourceBuilder poolname(String var) {
        this.poolname = var;
        return this;
    }

    HikariDataSourceBuilder isAutoCommit(boolean var) {
        this.isAutoCommit = var;
        return this;
    }

    HikariDataSourceBuilder connectionTimeout(long var) {
        this.connectionTimeout = var;
        return this;
    }

    HikariDataSourceBuilder idleTimeout(long var) {
        this.idleTimeout = var;
        return this;
    }

    HikariDataSourceBuilder maxLifetime(long var) {
        this.maxLifetime = var;
        return this;
    }

    HikariDataSourceBuilder minIdle(int var) {
        this.minIdle = var;
        return this;
    }

    HikariDataSourceBuilder maxPoolSize(int var) {
        this.maxPoolSize = var;
        return this;
    }

    HikariDataSourceBuilder leakDetectionThreshold(long var) {
        this.leakDetectionThreshold = var;
        return this;
    }

    HikariDataSourceBuilder addDataSourceProperty(Map<String, String> kv) {
        for (String key : kv.keySet()) {
            config.addDataSourceProperty(key, kv.get(key));
        }
        return this;
    }

    HikariDataSourceBuilder addDataSourceProperty(String k, String v) {
        config.addDataSourceProperty(k, v);
        return this;
    }

    HikariDataSourceBuilder defaultProperty() {
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
        config.addDataSourceProperty("cacheResultSetMetadata", "true");
        config.addDataSourceProperty("cacheServerConfiguration", "true");
        config.addDataSourceProperty("elideSetAutoCommits", "true");
        config.addDataSourceProperty("rewriteBatchedStatements", "true");
        config.addDataSourceProperty("maintainTimeStats", "false");
        return this;
    }


    /**
     * 监控设置
     */
    HikariDataSourceBuilder addHealthCheckProperty(String k, String v) {
        synchronized (this.getClass()) {
            if (mtf == null) {
                mtf = new CodahaleMetricsTrackerFactory(new MetricRegistry());
                config.setMetricsTrackerFactory(mtf);
            }
        }
        config.addHealthCheckProperty(k, v);
        return this;
    }


    /**
     * 监控设置
     */
    HikariDataSourceBuilder addHealthCheckProperty(MetricsTrackerFactory mtf, Map<String, String> kv) {
        config.setMetricsTrackerFactory(mtf);
        for (String key : kv.keySet()) {
            config.addHealthCheckProperty(key, kv.get(key));
        }
        return this;
    }

    /**
     * 默认监控设置
     */
    HikariDataSourceBuilder defaultHealthCheckProperty() {
        MetricRegistry mr = new MetricRegistry();
//        config.setMetricRegistry(mr);//cannot use setMetricsTrackerFactory() and setMetricRegistry() together
        MetricsTrackerFactory mtf = new CodahaleMetricsTrackerFactory(new MetricRegistry());
        config.setMetricsTrackerFactory(mtf);
        config.addHealthCheckProperty("connectivityCheckTimeoutMs", "1000");
        config.addHealthCheckProperty("expected99thPercentileMs", "10");
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


    /**
     * 构建hikari连接池
     *
     * @return
     */
    public HikariDataSource build() {

        ssNotNull(url, driver, username, password, poolname);
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driver);
        config.setPoolName(poolname);


        //自动提交 默认true
        config.setAutoCommit(isAutoCommit);


        /**
         *  客户端等待池中连接的最大毫秒数,如果在没有连接可用的情况下超时抛出SQLException
         *  最低250毫秒,默认值：30000(30秒)
         */
        config.setConnectionTimeout(connectionTimeout);

        /**
         * 允许连接空闲的时间
         * 链接空闲时间超过设定值,且连接池中的连接数超过设定的最小值则移除
         * 允许的最小值为10000毫秒（10秒）, 默认值：600000（10分钟）
         */
        config.setIdleTimeout(idleTimeout);

        /**
         * 此属性控制连接的最长生命周期。
         * 使用中的连接永远不会退役，只有当它关闭时才会被删除。
         * 建议设置此值，它应该比数据库的最大连接时间小几秒
         * 值0表示无限，当然也受制于idleTimeout值的设置。
         * 默认值：1800000（30分钟）
         */
        config.setMaxLifetime(maxLifetime);

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
        config.setMinimumIdle((minIdle == -1L) ? maxPoolSize : minIdle);

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
        config.setMaximumPoolSize(maxPoolSize);


        /**
         * 连接泄露检测 连接离开连接池的时间
         * 如果超过该时间 连接池会在子线程中抛出一个异常 不会影响主线程的执行
         * 开发时启用，生产时关闭
         * 默认0 不开启 最低 5000 毫秒
         */
        config.setLeakDetectionThreshold(leakDetectionThreshold);

        return new HikariDataSource(config);
    }


    /**
     * 根据map配置构建连接池
     *
     * @param kv
     * @return
     */
    static HikariDataSource build(Map<String, String> kv) {
        HikariDataSourceBuilder hdsb = new HikariDataSourceBuilder().
                url(kv.get("url")).
                driver(kv.get("driver")).
                username(kv.get("username")).
                password(kv.get("password")).
                poolname(kv.get("poolname"));

        if (kv.get("isAutoCommit") != null && !"".equals(kv.get("isAutoCommit"))) {
            hdsb.isAutoCommit(Boolean.valueOf(kv.get("isAutoCommit")));
        }
        if (kv.get("connectionTimeout") != null && !"".equals(kv.get("connectionTimeout"))) {
            hdsb.connectionTimeout(Long.valueOf(kv.get("connectionTimeout")));
        }
        if (kv.get("idleTimeout") != null && !"".equals(kv.get("idleTimeout"))) {
            hdsb.idleTimeout(Long.valueOf(kv.get("idleTimeout")));
        }
        if (kv.get("maxLifetime") != null && !"".equals(kv.get("maxLifetime"))) {
            hdsb.maxLifetime(Long.valueOf(kv.get("maxLifetime")));
        }
        if (kv.get("minIdle") != null && !"".equals(kv.get("minIdle"))) {
            hdsb.minIdle(Integer.valueOf(kv.get("minIdle")));
        }
        if (kv.get("maxPoolSize") != null && !"".equals(kv.get("maxPoolSize"))) {
            hdsb.maxPoolSize(Integer.valueOf(kv.get("maxPoolSize")));
        }
        if (kv.get("leakDetectionThreshold") != null && !"".equals(kv.get("leakDetectionThreshold"))) {
            hdsb.leakDetectionThreshold(Long.valueOf(kv.get("leakDetectionThreshold")));
        }

        Set<String> ss = kv.keySet();
        for (String s : ss) {
            if (s != null && s.startsWith("dataSourceProperties")) {
                hdsb.addDataSourceProperty(s.substring("dataSourceProperties".length() + 1), kv.get(s));
            }
            if (s != null && s.startsWith("healthCheckProperty")) {
                hdsb.addHealthCheckProperty(s.substring("healthCheckProperty".length() + 1), kv.get(s));
            }
        }
        return hdsb.build();
    }

    /**
     * 根据配置文件构建连接池
     *
     * @param path
     * @param prefix
     * @return
     * @throws IOException
     */
    public static HikariDataSource build(String path, String prefix) throws IOException {
        Properties props = new Properties();
        InputStream in = HikariDataSourceBuilder.class.getClassLoader().getResourceAsStream(path);
        props.load(in);
        Set<String> ss = props.stringPropertyNames();
        Map<String, String> map = new HashMap<String, String>();
        for (String s : ss) {
            if (s != null && s.startsWith(prefix)) {
                map.put(s.substring(prefix.length() + 1), props.getProperty(s));
            }
        }
        return build(map);
    }


}
