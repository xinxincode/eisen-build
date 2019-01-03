package org.eisen.bios.hash;

import org.eisen.bios.fileopreate.CheckFile;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author Eisen
 * @Date 2019/1/3 22:04
 * @Description:
 **/
public class DemoMessageDigest {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        String abc = "abc";
        ByteBuffer bf = ByteBuffer.wrap(abc.getBytes());
        md.update(bf);
        String s = CheckFile.bytesToHex(md.digest());
        System.out.println(s);
        MD5.main(args);
    }
}
