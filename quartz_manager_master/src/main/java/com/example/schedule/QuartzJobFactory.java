package com.example.schedule;


import com.example.model.ScheduleJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @模块名称：批跑自动化
 * @Description 计划任务执行处 无状态
 * @version：1.0
 * @author：cc
 * @date：2018年5月15日 上午10:40:06
 */
public class QuartzJobFactory implements Job {

    public void execute(JobExecutionContext context) throws JobExecutionException {
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
        TaskUtils.invokMethod(scheduleJob);
    }
}
