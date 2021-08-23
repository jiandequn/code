package com.ppfuns.report.service.impl;


import com.ppfuns.report.service.AppCountDayService;
import com.ppfuns.report.service.IAppAreaVisitCountDayService;
import com.ppfuns.report.service.INoappAreaVisitCountDayService;
import com.ppfuns.report.service.NoappCountDayService;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


@Service
public class QuartzJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LocalDate now = LocalDate.now();
        LocalDate yestoday = now.plusDays(-1);
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String endDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String startDate = yestoday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if(jobDataMap.containsKey("iNoappAreaVisitCountDayService")) ((INoappAreaVisitCountDayService) jobDataMap.get("iNoappAreaVisitCountDayService")).gengerateData(startDate,endDate);
        if(jobDataMap.containsKey("iAppAreaVisitCountDayService")) ((IAppAreaVisitCountDayService) jobDataMap.get("iAppAreaVisitCountDayService")).gengerateData(startDate,endDate);
        if(jobDataMap.containsKey("appCountDayService")) ((AppCountDayService) jobDataMap.get("appCountDayService")).gengerateData(startDate,endDate);
        if(jobDataMap.containsKey("noappCountDayService")) ((NoappCountDayService) jobDataMap.get("noappCountDayService")).gengerateData(startDate,endDate);
    }
}
