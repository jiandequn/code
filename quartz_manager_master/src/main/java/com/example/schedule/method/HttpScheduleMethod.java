package com.example.schedule.method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/5/20
 * Time: 11:00
 * To change this template use File | Settings | File Templates.
 */
@Component("HttpScheduleMethod")
public class HttpScheduleMethod {
    private static Logger logger = LoggerFactory.getLogger(HttpScheduleMethod.class);

    public void getMethod(String url){
        logger.info("执行GET方法");
        logger.info(url);
    }
    public void postMethod(String url,String params){
        logger.info("执行POST方法");
        logger.info(url+"   "+params);
    }
    public void test(){
        logger.info("执行test方法");
    }
    public void test1(){
        logger.info("执行test1方法");
    }
}
