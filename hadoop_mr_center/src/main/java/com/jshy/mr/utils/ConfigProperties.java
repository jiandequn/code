package com.jshy.mr.utils;

import org.eclipse.jetty.util.ajax.JSON;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.Properties;

/**
 * 初始化配置属性
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/9/26
 * Time: 15:16
 * To change this template use File | Settings | File Templates.
 */
public class ConfigProperties implements Serializable {
    private Properties config ;
    public Properties instance(String path){
        try {
            config.load(new FileInputStream(path));
            Enumeration e=  config.elements();
            System.out.println("读取配置文件："+ JSON.toString(e));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return config;
    }

    /**
     * 读取默认的config.properties文件
     * @param key
     * @return value
     */
    public String getString(String key) {
        try {
            return config.getProperty(key);
        } catch (MissingResourceException e) {
            throw new RuntimeException( "! config : "+ key + '!');
        }
    }

    public Integer getInt(String key) {
        try {
            return Integer.valueOf(config.getProperty(key));
        } catch (MissingResourceException e) {
            throw new RuntimeException( "! config : "+ key + '!');
        }
    }
}
