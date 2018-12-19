package org.eisen.dao.controller;

import org.eisen.dao.orm.mapper.TbFileDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DaoController {

//    @Autowired
    TbFileDetailMapper tbFileDetailMapper;

    @RequestMapping("/t")
    public Object tbean() {
//        TbFileDetailMapper tbFileDetailMapper = (TbFileDetailMapper) ApplicationContextProvider.applicationContext.getBean("TbFileDetailMapper");
        return tbFileDetailMapper.selectAll();
    }


}
