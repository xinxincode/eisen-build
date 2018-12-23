package org.eisen.dal.controller;


import org.eisen.dal.configuration.MybatisInit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DalController {

    @Autowired
    org.eisen.dal.orm.db1.mapper.TbFileDetailMapper tbFileDetailMapper1;


    @Transactional
    @RequestMapping("/t1")
    public Object tbean1() throws ClassNotFoundException {
        System.out.println(MybatisInit.getMapperDBId(tbFileDetailMapper1.getClass()));
        return tbFileDetailMapper1.selectAll();
    }

}
