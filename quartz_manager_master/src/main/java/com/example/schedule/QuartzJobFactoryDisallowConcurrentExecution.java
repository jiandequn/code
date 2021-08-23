package com.example.schedule;

import com.example.model.ScheduleJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


/**
 *
 * @模块名称：批跑自动化
 * @Description 若一个方法一次执行不完下次轮转时则等待改方法执行完后才执行下一次操作
 * @version：1.0
 * @author：cc
 * @date：2018年5月15日 上午10:39:13
 */
@DisallowConcurrentExecution
public class QuartzJobFactoryDisallowConcurrentExecution implements Job {

    public void execute(JobExecutionContext context) throws JobExecutionException {
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
        TaskUtils.invokMethod(scheduleJob);

    }
}