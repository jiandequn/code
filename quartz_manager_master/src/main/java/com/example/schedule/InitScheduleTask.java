package com.example.schedule;

import com.example.model.ScheduleJob;
import com.example.service.ScheduleJobService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 参考来至博客：https://blog.csdn.net/u010904188/article/details/80482922
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/5/17
 * Time: 17:28
 * To change this template use File | Settings | File Templates.
 */
public class InitScheduleTask {
    private static Logger logger = LoggerFactory.getLogger(InitScheduleTask.class);
    public static void test(ApplicationContext appCtx) {
        SchedulerFactoryBean schedulerFactoryBean = (SchedulerFactoryBean) appCtx.getBean(SchedulerFactoryBean.class);
        // 这里从数据库中获取任务信息数据(此案例模拟)
        ScheduleJobService scheduleJobService = appCtx.getBean(ScheduleJobService.class);

        List<ScheduleJob> jobList = new ArrayList<>();
        ScheduleJob job1 = new ScheduleJob();
        job1.setJobId((long) 1);
        job1.setJobGroup("测试批跑"); // 任务组
        job1.setJobName("有参方法");// 任务名称
        job1.setIsConcurrent(ScheduleJob.CONCURRENT_IS); // 运行状态sTimetask.getConcurrent() ? "1" : "0"
        job1.setCronExpression("0/5 * * * * ?");// corn表达式
        job1.setSpringId("test");// beanId；相对beanClass优先级高
        job1.setBeanClass("com.example.task.TestComponent");// 完整路径
        job1.setMethodName("test1");// 方法名
        job1.setRuningStatus(ScheduleJob.STATUS_NOT_RUNNING);// 任务运行状态
        job1.setJobStatus(ScheduleJob.STATUS_JOB); // 任务有效状态
        Gson gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        Map< String, Object > params = new HashMap< String, Object >();
        params.put("time", new Date());
        params.put("name", "cc");
        params.put("age", 14);
        job1.setJobData(gson.toJson(params));// 设置参数
        jobList.add(job1);

        ScheduleJob job2 = new ScheduleJob();
        job2.setJobId((long) 2);
        job2.setJobGroup("测试批跑"); // 任务组
        job2.setJobName("无参方法");// 任务名称
        job2.setIsConcurrent(ScheduleJob.CONCURRENT_NOT); // 运行状态sTimetask.getConcurrent() ? "1" : "0"
        job2.setCronExpression("0/5 * * * * ?");
        job2.setSpringId(null);// beanId；相对beanClass优先级高
        job2.setBeanClass("com.example.task.TestComponent");// 完整路径
        job2.setMethodName("test2");// 方法名
        job2.setRuningStatus(ScheduleJob.STATUS_NOT_RUNNING);// 任务运行状态
        job2.setJobStatus(ScheduleJob.STATUS_JOB); // 任务有效状态
        job2.setJobData(null);// 设置参数
        jobList.add(job2);
        for (ScheduleJob job : jobList) {
            try {
                addJob(job,schedulerFactoryBean);
            }
            catch (SchedulerException e) {
                logger.error("添加job错误!", e);
            }
        }
    }
    /**
     * 添加任务
     *
     * @param job
     * @throws SchedulerException
     */
    public static void addJob(ScheduleJob job,SchedulerFactoryBean schedulerFactoryBean) throws SchedulerException {
        if (job == null) {
            return;
        }
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try {
            logger.info(scheduler.getSchedulerName());
            logger.info(scheduler + ":" + job.getJobName() + ":" + job.getJobGroup()
                    + "...........................................add");
        }
        catch (SchedulerException e1) {
            logger.error("初始化scheduler异常！", e1);
        }
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());

        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        // 通过触发器来判断改任务是否已加入
        // 1. 不存在，创建一个
        if (null == trigger && ScheduleJob.STATUS_JOB.equals(job.getJobStatus())) {
            // a.根据job.getIsConcurrent()字段决定job实现类
            Class clazz = ScheduleJob.CONCURRENT_IS.equals(job.getIsConcurrent()) ? QuartzJobFactory.class
                    : QuartzJobFactoryDisallowConcurrentExecution.class;
            // b.根据实现类通过JobBuilder创建JobDetail
            JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getJobName(), job.getJobGroup())
                    .usingJobData("data", job.getJobData()).build();
            // c.传输参数
            jobDetail.getJobDataMap().put("scheduleJob", job);
            // d.通过CronScheduleBuilder和TriggerBuilder创建cron类型的触发器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
            trigger = TriggerBuilder.newTrigger().withDescription(job.getJobId().toString())
                    .withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();
            // e.由JobDetail和Trigger配对添加到调度器中
            scheduler.scheduleJob(jobDetail, trigger);
        }
        // 2.存在，且状态存在则更新Trigger
        else if (trigger != null && ScheduleJob.STATUS_JOB.equals(job.getJobStatus())) {
            // a.Trigger已存在，那么更新相应的定时设置
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

            // b.按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).usingJobData("data", job.getJobData())
                    .withSchedule(scheduleBuilder).build();
            // c.设置参数
            trigger.getJobDataMap().put("scheduleJob", job);
            // d.按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        }
        // 3. 存在且状态为非运行状态下，则删除Trigger
        else if (trigger != null && ScheduleJob.STATUS_NOT_JOB.equals(job.getJobStatus())) {
            JobKey jobKey = new JobKey(job.getJobName(), job.getJobGroup());
            // 根据jobKey删除
            scheduler.deleteJob(jobKey);
        }
    }

    /**
     * 验证job填写的相关参数是否可用
     * @param job
     * @return
     */
    public static boolean validateJob(ScheduleJob job){
        boolean flag = false;
        try {
            Class clazz = Class.forName(job.getBeanClass());
            Object obj = clazz.newInstance();
            Method method = null;
            if(StringUtils.isEmpty(job.getJobData())){
                clazz.getMethod(job.getMethodName());
            }else{
                clazz.getMethod(job.getMethodName(),String.class);
            }
            new CronExpression(job.getCronExpression());
            flag =true;
        } catch (ParseException e) {
            logger.error("时间正在表达式错误:"+job.getCronExpression(),e);
        } catch (ClassNotFoundException e) {
            logger.error("类全路径错误:"+job.getBeanClass(),e);
        } catch (NoSuchMethodException e) {
            logger.error("类全路径下方法找不到:"+job.getBeanClass()+"."+job.getMethodName(),e);
        } catch (IllegalAccessException|InstantiationException e) {
            logger.error("类实例化和调用访问异常:"+job.getBeanClass(),e);
        }finally {
            return flag;
        }

    }
    public static void init(ApplicationContext applicationContext) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(3L);
                    ScheduleJobService scheduleJobService = applicationContext.getBean(ScheduleJobService.class);
                    SchedulerFactoryBean schedulerFactoryBean = (SchedulerFactoryBean) applicationContext.getBean(SchedulerFactoryBean.class);
                    List<ScheduleJob> jobList =  scheduleJobService.getList(null);
                    for (ScheduleJob job : jobList) {
                        if(validateJob(job)){
                            addJob(job,schedulerFactoryBean);
                        }

                    }
                }catch (Exception e) {
                    logger.debug("添加job错误!", e);
                }
            }
        }).start();
    }
    public static boolean remove(ScheduleJob job,SchedulerFactoryBean schedulerFactoryBean) throws SchedulerException {
        if (ScheduleJob.STATUS_NOT_JOB.equals(job.getJobStatus())) {
             addJob(job,schedulerFactoryBean);
            return true;
        }
        return false;
    }
}
