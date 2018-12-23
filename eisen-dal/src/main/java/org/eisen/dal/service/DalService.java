package org.eisen.dal.service;

import org.eisen.dal.orm.db1.mapper.TbFileDetailMapper;
import org.eisen.dal.orm.db1.model.TbFileDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    @Transactional(transactionManager = "db1")
    public String Transactional() {
        List<TbFileDetail> list1 = tbFileDetailMapper1.selectAll();
        List<org.eisen.dal.orm.db2.model.TbFileDetail> list2 = tbFileDetailMapper2.selectAll();
        TbFileDetail tb1 = list1.get(0);
        org.eisen.dal.orm.db2.model.TbFileDetail tb2 = list2.get(0);

        tb1.setFileName("测试事务31");
        tb2.setFileName("测试事务31");
        tbFileDetailMapper1.updateByPrimaryKey(tb1);
        tbFileDetailMapper2.updateByPrimaryKey(tb2);
        if (1 == 1) {
//            throw new RuntimeException("事务出错");
        }
        return null;
    }


}
