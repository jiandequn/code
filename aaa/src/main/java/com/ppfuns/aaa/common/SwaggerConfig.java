package com.ppfuns.aaa.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/1/16
 * Time: 10:31
 * To change this template use File | Settings | File Templates.
 */
@Configuration  //启动配置
@EnableSwagger2      //启动swagger
@ComponentScan(basePackages = {"com.ppfuns.aaa.action"})  //扫描Action层接口列表目录
@EnableWebMvc
public class SwaggerConfig extends WebMvcConfigurationSupport {
    @Bean
    public Docket customDocket() {
        //
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("22", "https://www.ppfuns.com", "978030744@qq.com");
        return new ApiInfo("AAA 22",//大标题 title
                "Swagger 22 demo",//小标题
                "0.0.1",//版本
                "www.baidu.com",//termsOfServiceUrl
                 contact,//作者
                "22",//链接显示文字
                "https://www.ppfuns1.com"//网站链接
        );
    }
}
