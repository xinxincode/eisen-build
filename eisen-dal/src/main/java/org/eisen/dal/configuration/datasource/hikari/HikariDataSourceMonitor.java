package org.eisen.dal.configuration.datasource.hikari;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.MetricRegistry;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.metrics.dropwizard.CodahaleMetricsTrackerFactory;

/**
 * @Author Eisen
 * @Date 2018/12/17 14:57
 * @Description:
 **/
public class HikariDataSourceMonitor {

    /**
     * 连接池监控
     *
     * @param hds
     */
    public void m(HikariDataSource hds) {
        MetricRegistry mr = ((CodahaleMetricsTrackerFactory) hds.getMetricsTrackerFactory()).getRegistry();
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
    }

}
