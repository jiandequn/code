package com.example;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2020/3/3
 * Time: 14:35
 * To change this template use File | Settings | File Templates.
 */
public class MyClassLoader extends URLClassLoader {

    public MyClassLoader(final String path, final ClassLoader parent) throws MalformedURLException {
        super(new URL[]{new URL(path)}, parent);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return super.loadClass(name);
    }

    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        String clazzName = "com.ppfuns.HelloWorld";
        Class clazz = null;
        try {

            clazz = Class.forName(clazzName);
        } catch (ClassNotFoundException e) {
            System.out.println("无clazz类");
        }
        try {
            tt();
        } catch (Exception e) {
            System.out.println("=====");
        }
        if(clazz == null){
            String path = "jar:file:///opt/classloader-test.jar!/";
            String jarPath="jar:file:D:\\code\\hive_demo\\target\\hive_demo-1.0.0.jar!/";
            MyClassLoader myClassLoader = new MyClassLoader(jarPath,Thread.currentThread().getContextClassLoader().getParent());
            clazz = myClassLoader.loadClass(clazzName);
        }
        if(clazz == null) System.out.println("无clazz类");
        Method method = clazz.getMethod("test",String.class);
        Object obj = clazz.newInstance();
        method.invoke(obj,"caomeng");
    }
    public static void tt() throws Exception{
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("account", ""));
        formparams.add(new BasicNameValuePair("password", ""));
        HttpEntity reqEntity = new UrlEncodedFormEntity(formparams, "utf-8");

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)//一、连接超时：connectionTimeout-->指的是连接一个url的连接等待时间
                .setSocketTimeout(5000)// 二、读取数据超时：SocketTimeout-->指的是连接上一个url，获取response的返回等待时间
                .setConnectionRequestTimeout(5000)
                .build();

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://cnivi.com.cn/login");
        post.setEntity(reqEntity);
        post.setConfig(requestConfig);
        HttpResponse response = client.execute(post);

        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity resEntity = response.getEntity();
            String message = EntityUtils.toString(resEntity, "utf-8");
            System.out.println(message);
        } else {
            System.out.println("请求失败");
        }
    }
}
