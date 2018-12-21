package org.eisen.bios.fileopreate;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Author Eisen
 * @Date 2018/12/21
 *      提供读取properties的几种方法
 */
public class ReadFile {

    /**
     * 读取properties文件到Properties bean中
     *
     * @param path 传入路径要求为项目相对路径 不能以 / 开头
     *             比如properteis/prop.properties
     *             properties为项目包内的首路径
     * @return
     * @throws IOException
     */
    public static Properties readPropsInPropsBean(String path) throws IOException {
        Properties props = new Properties();
        InputStream in = ReadFile.class.getClassLoader().getResourceAsStream(path);
        props.load(in);
        return props;
    }


    public static void main(String[] args) throws IOException {
        Properties props = readPropsInPropsBean("path/path2/ds1.properties");
        System.out.println(props);
    }


}
