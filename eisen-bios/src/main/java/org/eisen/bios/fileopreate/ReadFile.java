package org.eisen.bios.fileopreate;

import org.eisen.bios.utils.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Author Eisen
 * @Date 2018/12/21
 * 提供读取properties的几种方法
 */
public class ReadFile {


    /**
     * 读取项目内的properties文件
     *
     * @param path 文件路径为项目相对路径 如properties/prop.properties
     * @return
     * @throws IOException
     */
    public static Properties readProjectProps(String path) throws IOException {
        Properties props = new Properties();
        InputStream in = ReadFile.class.getClassLoader().getResourceAsStream(path);
        props.load(in);
        return props;
    }

    /**
     * 筛选properties bean中前缀为指定前缀的属性
     *
     * @param props  properties bean
     * @param prefix 前缀
     * @return 一个新的 prop bean
     * @throws IOException
     */
    public static Properties readProjectProps(Properties props, String prefix) throws IOException {
        if (props.isEmpty() || StringUtils.isEmpty(prefix)) {
            return props;
        }
        Properties result = new Properties();
        for (String key : props.stringPropertyNames()) {
            if (!key.startsWith(prefix + ".")) {
                continue;
            }
            result.put(key.substring(prefix.length() + 1), props.getProperty(key));
        }
        return result;
    }

    /**
     * 读取properties文件中以prefix开头的属性 key为去掉prefix的
     *
     * @param path   文件路径
     * @param prefix key前缀
     * @return Properties
     * @throws IOException
     */
    public static Properties readProjectProps(String path, String prefix) throws IOException {
        return readProjectProps(readProjectProps(path), prefix);
    }

    public static void main(String[] args) throws IOException {
        Properties props = readProjectProps("path/path2/ds1.properties", "hikari");
        System.out.println(props);
    }


}
