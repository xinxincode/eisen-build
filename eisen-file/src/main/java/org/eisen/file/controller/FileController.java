package org.eisen.file.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * @Author Eisen
 * @Date 2018/12/12 20:28
 * @Description:
 **/
@RestController
public class FileController {

    @RequestMapping("/upload")
    public Object upload(HttpServletRequest request) {
        CommonsMultipartResolver cmr = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (!cmr.isMultipart(request)) {
            return 0;
        }
        MultipartHttpServletRequest mreq = (MultipartHttpServletRequest) request;
        Iterator ite = mreq.getFileNames();
        MultipartFile mFile;
        while (ite.hasNext()) {
            mFile = mreq.getFile(ite.next().toString());
            if (mFile == null || mFile.isEmpty()) {
                continue;
            }

            String path = "/" + mFile.getOriginalFilename();
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            try {
                mFile.transferTo(file);
            } catch (IOException e) {
                e.printStackTrace();
                return 0;
            }

        }
        return 1;
    }

}
