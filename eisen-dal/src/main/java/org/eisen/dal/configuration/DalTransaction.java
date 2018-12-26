package org.eisen.dal.configuration;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import static org.eisen.dal.configuration.DalInit.dataSourceTransactionManagerMap;
import static org.eisen.dal.configuration.DalInit.getMapperDBId;

/**
 * @Author Eisen
 * @Date 2018/12/24 22:32
 * @Description:
 *      spring-mybatis事务控制
 *      1.由于spring控制事务是由ThreadLocal实现.
 *          即使不同数据源也要遵循先开启的事务先关闭 后开启的事务后关闭原则
 *      2.解决1的办法就拿一个新的SqlSession控制吧
 *
 **/
public class DalTransaction {

    public DataSourceTransactionManager transactionManager;
    public TransactionStatus status;
    public DefaultTransactionDefinition def;

    public DalTransaction(Object mapper, int propagationBehavior) {
        init(getMapperDBId(mapper), propagationBehavior);
    }

    public DalTransaction(Object mapper) {
        init(getMapperDBId(mapper), TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    }

    public DalTransaction(String id, int propagationBehavior) {
        init(id, propagationBehavior);
    }

    public DalTransaction(String id) {
        init(id, TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    }


    public void init(String id, int propagationBehavior) {
        //1.事务控制管理器
        transactionManager = dataSourceTransactionManagerMap.get(id);
        //2.定义事务隔离级别，开启新事务
        def = new DefaultTransactionDefinition(propagationBehavior);
        //3.事务状态
        status = transactionManager.getTransaction(def);
    }


    public void rollbackToSavepoint(Object obj) {
        status.rollbackToSavepoint(obj);
    }

    public Object createSavepoint() {
        return status.createSavepoint();
    }

    public void commit() {
        transactionManager.commit(status);
    }

    public void rollback() {
        transactionManager.rollback(status);
    }

}
