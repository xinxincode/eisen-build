package org.eisen.dal.service;

import org.apache.ibatis.session.SqlSession;
import org.eisen.dal.configuration.DalInit;
import org.eisen.dal.configuration.DalTransaction;
import org.eisen.dal.orm.db1.mapper.TbFileDetailMapper;
import org.eisen.dal.orm.db1.model.TbFileDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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


    //    @Transactional(transactionManager = "db1")
    public String Transactional() {

        SqlSession sqlSession = DalInit.sqlSessionFactoryManagerMap.get(DalInit.getMapperDBId(tbFileDetailMapper2)).openSession(false);
        tbFileDetailMapper2 = sqlSession.getMapper(org.eisen.dal.orm.db2.mapper.TbFileDetailMapper.class);

        DalTransaction transaction1 = new DalTransaction(tbFileDetailMapper1);

        List<TbFileDetail> list1 = tbFileDetailMapper1.selectAll();
        List<org.eisen.dal.orm.db2.model.TbFileDetail> list2 = tbFileDetailMapper2.selectAll();
        TbFileDetail tb1 = list1.get(0);
        org.eisen.dal.orm.db2.model.TbFileDetail tb2 = list2.get(0);


        tb1.setFileName("测试事务1");
        tb2.setFileName("测试事务1");
        tbFileDetailMapper1.updateByPrimaryKey(tb1);
        tbFileDetailMapper2.updateByPrimaryKey(tb2);

        Object point1 = transaction1.createSavepoint();
        sqlSession.rollback(true);
        sqlSession.commit();
        sqlSession.close();
        tb1.setFileName("测试事务2");
        tb2.setFileName("测试事务2");
        tbFileDetailMapper1.updateByPrimaryKey(tb1);
        tbFileDetailMapper2.updateByPrimaryKey(tb2);

        transaction1.rollbackToSavepoint(point1);
        transaction1.commit();

        return null;
    }


}
