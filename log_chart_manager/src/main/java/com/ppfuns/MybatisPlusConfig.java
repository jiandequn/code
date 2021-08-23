package com.ppfuns;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.ppfuns.*.mapper")
public class MybatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        // paginationInterceptor.setLimit(500);
        return paginationInterceptor;
    }

//    @Bean
//    public Trigger printTimeJobTrigger() {
//        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/1 * * * * ?");
//        return TriggerBuilder.newTrigger()
//                .forJob(printTimeJobDetail())//关联上述的JobDetail
//                .withIdentity("quartzTaskService")//给Trigger起个名字
//                .withSchedule(cronScheduleBuilder)
//                .build();
//    }
    /**
     * 定时任务
     * @return
     */
//    @Bean
//    public JobDetail testJobDetail() {
//        return JobBuilder.newJob(TestJobBean.class)
//                .withIdentity("testJobDetail")
//                .storeDurably()
//                .build();
//    }

    /**
     * 触发器，每间隔一段时间触发定时任务
     * @param jobDetail 具体执行的定时任务
     * @return
     */
//    @Bean
//    public Trigger testJobTrigger(@Qualifier("testJobDetail") JobDetail jobDetail) {
//        ScheduleBuilder scheduleBuilder = SimpleScheduleBuilder
//                .simpleSchedule()
//                .withIntervalInSeconds(3) // 定时任务间隔时间
//                .repeatForever(); // 触发器无限循环触发
//        return TriggerBuilder.newTrigger()
//                .forJob(jobDetail)
//                .withIdentity("testJobTrigger")
//                .withSchedule(scheduleBuilder)
//                .build();
//    }
}