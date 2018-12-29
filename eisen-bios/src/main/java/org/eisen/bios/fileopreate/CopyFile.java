package org.eisen.bios.fileopreate;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyFile {

    public static void copyFile(String sourceFile, String targetFile) throws IOException {
        copyFile(new FileInputStream(sourceFile), new FileOutputStream(targetFile));
    }

    public static void copyFile(FileInputStream in, FileOutputStream out) throws IOException {
        byte[] bs = new byte[10240];
        int c;
        while ((c = in.read(bs, 0, bs.length)) != -1) {
            out.write(bs, 0, c);
        }
        out.flush();
        out.close();
        in.close();
    }


}
