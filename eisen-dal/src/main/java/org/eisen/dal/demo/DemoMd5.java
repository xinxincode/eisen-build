package org.eisen.dal.demo;

import org.apache.tomcat.util.security.MD5Encoder;

public class DemoMd5 {
    public static void main(String[] args) {
        byte[] bs = new byte[16];
        bs[0] = 2;
        bs[1] = 2;
        bs[2] = 's';
        bs[3] = 2;
        String md5 = MD5Encoder.encode(bs);
        System.out.println(md5);
    }
}
