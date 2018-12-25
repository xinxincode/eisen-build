package org.eisen.dal.configuration;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import static org.eisen.dal.configuration.DalInit.dataSourceTransactionManagerMap;

/**
 * @Author Eisen
 * @Date 2018/12/24 22:32
 * @Description:
 **/
public class DalTransaction {

    public DataSourceTransactionManager transactionManager;
    public TransactionStatus status;
    public DefaultTransactionDefinition def;

    DalTransaction(String id) {
        //1.事务控制管理器
        transactionManager = dataSourceTransactionManagerMap.get(id);
        //2.定义事务隔离级别，开启新事务
        def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
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
