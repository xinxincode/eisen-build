package org.eisen.file.controller;

import org.eisen.file.component.FileCheck;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;

/**
 * @Author Eisen
 * @Date 2018/12/12 20:28
 * @Description:
 **/
@RestController
public class FileController {

    static String filePath = "D:/path/";

    static {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    @RequestMapping("/upload")
    public Object upload(HttpServletRequest request) throws IOException {
        CommonsMultipartResolver cmr = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (!cmr.isMultipart(request)) {
            return 0;
        }
        MultipartHttpServletRequest mreq = (MultipartHttpServletRequest) request;
        Iterator ite = mreq.getFileNames();
        MultipartFile mFile;
        while (ite.hasNext()) {
            mFile = mreq.getFile(ite.next().toString());
            FileInputStream in = (FileInputStream) mFile.getInputStream();

            if (mFile == null || mFile.isEmpty()) {
                continue;
            }

            String path = filePath + mFile.getOriginalFilename();
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


    @RequestMapping("/uploadCloud")
    public Object uploadCloud(HttpServletRequest request) throws IOException, NoSuchAlgorithmException {
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
            FileInputStream in = (FileInputStream) mFile.getInputStream();
            String sha_512 = FileCheck.getCheckValue(in, FileCheck.SHA_512);

            String path = filePath + mFile.getOriginalFilename();
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


    public static void outFile(FileInputStream fileIn, FileOutputStream fileOut) throws IOException {
        BufferedInputStream in = new BufferedInputStream(fileIn, 8 * 1024);
        BufferedOutputStream out = new BufferedOutputStream(fileOut, 8 * 1024);
        int c;
        while ((c = in.read()) != -1) {
            out.write(c);
            //刷新缓冲区
            out.flush();
        }
        out.close();
        in.close();
    }


    public static void main(String[] args) throws IOException {
        File file = new File("D:\\path\\ojdbc5.jar");
        FileInputStream in = new FileInputStream(file);
//        in.mark(0);
        File file1 = new File("/path/o5.jar");
        FileOutputStream outputStream = new FileOutputStream(file1);
//        byte[] bytes = new byte[2048];
//        int i = in.read(bytes, 0, bytes.length);
//        while (i != -1) {
//            outputStream.write(bytes, 0, i);
//            i = in.read(bytes, 0, i);
//        }
//        in.close();
//        outputStream.close();

        outFile(in, outputStream);

    }

}
