package com.jdq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.io.File;

@SpringBootApplication
//@EnableScheduling
@MapperScan("com.jdq.*.mapper")
public class ManagerApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ManagerApplication.class, args);
	}
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		StringBuffer filePath =new StringBuffer("spring.config.location=classpath:/");
		String path=System.getProperty("catalina.home")+"/shiro_mananger_conf/";
		if(new File(path).exists()){
			 filePath.append(",").append(path);
		}
		builder.properties(filePath.toString());
		return builder.sources(ManagerApplication.class);
	}
}
