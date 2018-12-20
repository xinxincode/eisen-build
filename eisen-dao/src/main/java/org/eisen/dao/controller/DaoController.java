package org.eisen.dao.controller;


import org.eisen.dao.orm.db2.mapper.TbFileDetailMapper1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DaoController {

    @Autowired
    org.eisen.dao.orm.db1.mapper.TbFileDetailMapper tbFileDetailMapper1;
    @Autowired
    TbFileDetailMapper1 tbFileDetailMapper12;

    @Autowired
    DaoConf d;

    @RequestMapping("/t1")
    public Object tbean1() {
        return tbFileDetailMapper1.selectAll();
    }

    @RequestMapping("/t2")
    public Object tbean2() {
        return tbFileDetailMapper12.selectAll();
    }

    @RequestMapping("/t3")
    public Object t3() {
        return d.d();
    }

}
