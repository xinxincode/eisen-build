package org.eisen.bios.utils;

import java.util.HashMap;
import java.util.Map;

import static org.eisen.bios.utils.StringUtils.isEmpty;

/**
 * @Author Eisen
 * @Date 2018/12/24 20:51
 * @Description:
 **/
public class UrlUtils {


    public static Map<String, String> getParams(String url) {

        if (isEmpty(url)) return null;

        int i = url.indexOf("?") + 1;

        if (i == 0 || i == url.length()) return null;

        int j = url.indexOf("#");

        if (i > j && j != -1) return null;
        else j = url.length();

        String[] urls = url.substring(i, j).split("&");
        Map<String, String> map = new HashMap<>();
        String[] param;
        for (i = 0; i < urls.length; i++) {
            if (isEmpty(urls[i]) || !urls[i].contains("=")) {
                continue;
            }
            param = urls[i].split("=");
            if (param.length != 2) {
                map.put(param[0], "");
            }
            map.put(param[0], param[1]);
        }
        return map.size() == 0 ? null : map;
    }

    public static String getDomain(String url) {
        if (isEmpty(url)) return url;
        int i = url.indexOf("?");
        int j = url.indexOf("#");
        int min;
        int max;
        if (i < j) {
            min = i;
            max = j;
        } else {
            min = j;
            max = i;
        }
        if (min != -1) {
            return url.substring(0, min);
        }
        if (max != -1) {
            return url.substring(0, max);
        }
        return url;
    }

    public static void main(String[] args) {
        Object o = null;
        String s = (String) o;
    }
}
