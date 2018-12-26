package org.eisen.dal.controller;


import org.eisen.dal.service.DalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DalController {

    @Autowired
    org.eisen.dal.orm.db1.mapper.TbFileDetailMapper tbFileDetailMapper1;

    @Autowired
    DalService dalService;

    @RequestMapping("/t1")
    public Object tbean1() {

        return tbFileDetailMapper1.selectAll();
    }

    @RequestMapping("/t2")
    public Object tbean2() throws InterruptedException {
        dalService.Transactional();
        return tbFileDetailMapper1.selectAll();
    }
    @RequestMapping("/t3")
    public Object tbean3() {
        dalService.Transactional();
        return tbFileDetailMapper1.selectAll();
    }


}
