package com.ppfuns.report.utils;

import com.ppfuns.report.entity.base.PpfunsEntity;
import com.ppfuns.report.service.AppCountDayService;
import com.ppfuns.report.service.IAppAreaVisitCountDayService;
import com.ppfuns.report.service.INoappAreaVisitCountDayService;
import com.ppfuns.report.service.NoappCountDayService;
import com.ppfuns.report.service.impl.QuartzJob;
import com.ppfuns.util.PpfunsConfigBean;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.ParseException;


@Component
public class QuartzTaskUtils {
    @Autowired
    AppCountDayService appCountDayService;
    @Autowired
    NoappCountDayService noappCountDayService;
    @Autowired
    private IAppAreaVisitCountDayService iAppAreaVisitCountDayService;
    @Autowired
    private INoappAreaVisitCountDayService iNoappAreaVisitCountDayService;
    @PostConstruct
    public void countDayJobTask() throws ParseException, SchedulerException {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putIfAbsent("appCountDayService",appCountDayService);
        jobDataMap.putIfAbsent("noappCountDayService",noappCountDayService);
        jobDataMap.putIfAbsent("iAppAreaVisitCountDayService",iAppAreaVisitCountDayService);
        jobDataMap.putIfAbsent("iNoappAreaVisitCountDayService",iNoappAreaVisitCountDayService);
        JobDetail job = new JobDetail();
        job.setName("count_day_job");
        job.setJobClass(QuartzJob.class);
        job.setJobDataMap(jobDataMap);

        CronTrigger trigger = new CronTrigger();
        trigger.setName("count_day_trigger");
        trigger.setCronExpression("0 0 7 * * ?");
//        trigger.setCronExpression("30 38 11 * * ?");
        //schedule it
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        scheduler.scheduleJob(job, trigger);

    }
}
