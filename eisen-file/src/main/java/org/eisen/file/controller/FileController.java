package org.eisen.file.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Eisen
 * @Date 2018/12/12 20:28
 * @Description:
 **/
@RestController
public class FileController {

    @RequestMapping("/upload")
    public Object upload() {
        return "SUCCESS";
    }

}
