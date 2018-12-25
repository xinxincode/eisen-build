package org.eisen.dal.service;

import org.eisen.dal.configuration.DalInit;
import org.eisen.dal.orm.db1.mapper.TbFileDetailMapper;
import org.eisen.dal.orm.db1.model.TbFileDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

import static org.eisen.dal.configuration.DalInit.dataSourceTransactionManagerMap;

/**
 * @Author Eisen
 * @Date 2018/12/22 21:06
 * @Description:
 **/
@Service
public class DalService {

    @Autowired
    TbFileDetailMapper tbFileDetailMapper1;
    @Autowired
    org.eisen.dal.orm.db2.mapper.TbFileDetailMapper tbFileDetailMapper2;


    @Transactional(transactionManager = "db1")
    public String Transactional() {

        DataSourceTransactionManager transactionManager;
        TransactionStatus status;
        DefaultTransactionDefinition def;


        //1.事务控制管理器
        transactionManager = dataSourceTransactionManagerMap.get(DalInit.getMapperDBId(tbFileDetailMapper1));
        //2.定义事务隔离级别，开启新事务
        def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        //3.事务状态
        status = transactionManager.getTransaction(def);






        List<TbFileDetail> list1 = tbFileDetailMapper1.selectAll();
        List<org.eisen.dal.orm.db2.model.TbFileDetail> list2 = tbFileDetailMapper2.selectAll();
        TbFileDetail tb1 = list1.get(0);
        org.eisen.dal.orm.db2.model.TbFileDetail tb2 = list2.get(0);


        tb1.setFileName("测试事务11");
        tb2.setFileName("测试事务11");
        tbFileDetailMapper1.updateByPrimaryKey(tb1);
        tbFileDetailMapper2.updateByPrimaryKey(tb2);
        Object obj1 = status.createSavepoint();

        tb1.setFileName("测试事务22");
        tb2.setFileName("测试事务22");
        tbFileDetailMapper1.updateByPrimaryKey(tb1);
        tbFileDetailMapper2.updateByPrimaryKey(tb2);
        System.out.println(status.isCompleted());

        status.rollbackToSavepoint(obj1);
        System.out.println(status.isCompleted());
        transactionManager.commit(status);

        if (1 == 1) {
//            throw new DalException("事务出错");
        }
        return null;
    }


}
