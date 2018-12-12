package org.eisen.file.component;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class FileCheck {

    private static final char[] HEX_ARR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static String bytesToHex(byte[] bs) {
        StringBuilder sb = new StringBuilder(bs.length + bs.length);
        for (byte b : bs) {
            sb.append(HEX_ARR[b >> 4 & 0b1111]);
            sb.append(HEX_ARR[b & 0b1111]);
        }
        return sb.toString();
    }

    public static String getCheckValue(File file, String algorithm) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        FileInputStream in = new FileInputStream(file);
        FileChannel ch = in.getChannel();
        MappedByteBuffer mb = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
        md.update(mb);
        return bytesToHex(md.digest());
    }

    public enum FILE_CHECK_ENUM {
        SHA1,SHA_256,SHA_512,MD5
    }


}
