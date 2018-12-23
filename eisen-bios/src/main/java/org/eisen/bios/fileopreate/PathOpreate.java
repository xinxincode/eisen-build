package org.eisen.bios.fileopreate;

/**
 * @Author Eisen
 * @Date 2018/12/22 13:16
 * @Description:
 **/
public class PathOpreate {
    public static void main(String[] args) {
        java.net.URL fileURL = DirFile.class.getResource("");
        String path = DirFile.class.getResource("").getPath();
        String path1 = DirFile.class.getResource("/path").getPath();
//        System.out.println(path);
//        System.out.println(path1);
//        getCurrentPath();
    }


    public static String getCurrentPath() {
        return null;
    }

}
