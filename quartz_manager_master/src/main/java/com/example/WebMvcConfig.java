package com.example;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * Spring内部的一种配置方式
 采用JavaBean的形式来代替传统的xml配置文件形式进行针对框架个性化定制
 实现有两种方式
 1、接口implements WebMvcConfigurer
 2.继承 extends    WebMvcConfigurationSupport
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/5/27
 * Time: 13:56
 * To change this template use File | Settings | File Templates.
 */
//@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor( new UserActionInterceptor())
                .addPathPatterns("/user/**", "/auth/**","schedule/**")
                .excludePathPatterns("/user/sendMsg", "/user/login");
        super.addInterceptors(registry);
    }
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }
}
