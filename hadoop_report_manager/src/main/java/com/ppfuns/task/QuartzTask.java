package com.ppfuns.task;

import com.ppfuns.dao.RunClassDao;
import com.ppfuns.entity.table.RunClassEntity;
import com.ppfuns.utils.EntityProperties;
import com.ppfuns.utils.HadoopUtils;
import com.ppfuns.utils.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/11/26
 * Time: 11:19
 * To change this template use File | Settings | File Templates.
 */
@Component
public class QuartzTask {
    private final static Logger logger = LoggerFactory.getLogger(QuartzTask.class);

    /**
     * 周一凌晨4点1分执行
     */
    @Scheduled(cron = "0 1 4 ? * MON")
    public void hadoopScheduled(){
        EntityProperties entityProperties = SpringContextUtil.getBean(EntityProperties.class);
        RunClassDao runClassDao  = SpringContextUtil.getBean(RunClassDao.class);
        List<RunClassEntity> runClazzes = runClassDao.getCanRun(null);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.now();
        int weekDay = localDate.getDayOfWeek().getValue();
        LocalDate lastSunDate= localDate.minusDays(weekDay+6);//获取上周第一天
        String time = lastSunDate.format(formatter)+"~"+localDate.format(formatter);
        HadoopUtils.run(runClazzes,time,"tableInfo",entityProperties);
        logger.info("springScheduled run:" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }
}
