package com.example.listener;

import com.example.schedule.InitScheduleTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/5/20
 * Time: 9:51
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationEventListener implements ApplicationListener {
     private final static Logger logger = LoggerFactory.getLogger(ApplicationEventListener.class);
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        // 在这里可以监听到Spring Boot的生命周期
        if (event instanceof ApplicationEnvironmentPreparedEvent) { // 初始化环境变量
            logger.info("初始化环境变量");
        } else if (event instanceof ApplicationPreparedEvent) { // 初始化完成
            logger.info("初始化完成");

        } else if (event instanceof ContextRefreshedEvent) { // 应用刷新
            logger.info("应用刷新");
        } else if (event instanceof ApplicationReadyEvent) {// 应用已启动完成
            logger.info("应用已启动完成");
            InitScheduleTask.init(((ApplicationReadyEvent) event).getApplicationContext());
        } else if (event instanceof ContextStartedEvent) { //应用启动，需要在代码动态添加监听器才可捕获
            logger.info("应用启动，需要在代码动态添加监听器才可捕获");
        } else if (event instanceof ContextStoppedEvent) { // 应用停止
            logger.info("应用停止");
        } else if (event instanceof ContextClosedEvent) { // 应用关闭
            logger.info("应用关闭");
        } else {
            logger.info("其他");
        }
    }

}
