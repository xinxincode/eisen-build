package org.eisen.bios.utils;

/**
 * @Author Eisen
 * @Date 2018/12/22 17:42
 * @Description:
 **/
public class StringUtils {
    public static boolean isEmpty(String s) {
        return s == null || "".equals(s.trim());
    }

    public static boolean isEmpty(String... ss) {
        if (ss == null || ss.length == 0) return true;
        for (String s : ss) {
            if (isEmpty(s)) return true;
        }
        return false;
    }

}
