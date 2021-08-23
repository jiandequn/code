package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2020/3/2
 * Time: 17:46
 * To change this template use File | Settings | File Templates.
 */
@Component
public class ApplicationRunnerImpl  implements ApplicationRunner {
    private static Logger logger = LoggerFactory.getLogger(ApplicationRunnerImpl.class);
    @Autowired
    private ApplicationContext applicationContext;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("通过实现ApplicationRunner接口");

        String[] sourceArgs = args.getSourceArgs();
        for (String arg : sourceArgs) {
            System.out.print(arg + " ");
        }
//        InitScheduleTask.init(applicationContext);
    }
}
