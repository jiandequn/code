#!/usr/bin/env python
# -*- coding: utf-8 -*-
import datetime

from tool import HadoopTool, SqoopTool
from tool import HiveTool
from utils import Constant


def backupLog(startDate,endDate):
    print '开始备份hive待处理日志数据...'
    i = 0;
    hive_backup_dir = ''.join([Constant.HADOOP_HDFS_URI, Constant.HIVE_DIR, "backup/"])
    HadoopTool.rm(hive_backup_dir);
    while True:
        t = startDate + datetime.timedelta(days=i);
        i = i + 1;
        if t > endDate: break;
        # 处理数据
        sd = t.strftime("%Y-%m-%d");
        print t.year, t.month, t.day
        # 清理中间HIVE备份库的数据（如果load 原始日志，会导致数据迁移位置）
        hive_back_date_url = ''.join([hive_backup_dir, sd])
        HadoopTool.mkdir(hive_back_date_url);
        HadoopTool.cp(''.join([Constant.HADOOP_HDFS_URI, Constant.HADOOP_LOG_DIR, t.strftime("%Y/%m/%d"), "/*/*"]),
                      hive_back_date_url);
        # HiveTool.exce("LOAD DATA  INPATH '%s/*' overwrite INTO TABLE yn_hadoop.yn_logs PARTITION (y='%s',m='%s',d='%s');"
        #               %(hive_back_date_url,t.strftime("%Y"),t.strftime("%m"),t.strftime("%d")))
        HiveTool.exceFile("loadLogs.hive", hived={"hive.back.url": hive_back_date_url, "year": t.strftime("%Y"),
                                                  "month": t.strftime("%m"), "day": t.strftime("%d")});
        print '备份hive待处理日志数据已完成。'

def generateEventLogsData(startDate,endDate):
    # 需分析文件已Load完；对日期范围内数据数据进行分区和分时间存储
    print '开始处理对yn_logs进行分类处理'
    HiveTool.exceFile(filePath="eventsType.hive",
                      hived={"startDate": startDate.strftime("%Y-%m-%d"), "endDate": endDate.strftime("%Y-%m-%d")});
    print 'yn_logs进行分类处理已完成';


def computeStatisticsByDay(startDate, endDate):
    print '开始处理对日统计数据'
    SqoopTool.execScrpit("importUser.sqoop")  #同步全量用户信息
    HiveTool.exceFile(filePath="statistic/RegionbyDay.hive",
                      hived={"startDate": startDate.strftime("%Y-%m-%d"), "endDate": endDate.strftime("%Y-%m-%d")});
    HiveTool.exceFile(filePath="statistic/add_user_day.hive",
                      hived={"startDate": startDate.strftime("%Y-%m-%d"), "endDate": endDate.strftime("%Y-%m-%d")});
    SqoopTool.execScrpit("exportAppvisitCount.sqoop")
    sd = startDate + datetime.timedelta(days=0);  #
    ed = endDate + datetime.timedelta(days=0);  #
    while sd < ed:
        param={"y":sd.strftime("%Y"),"m":sd.strftime("%m"),"d":sd.strftime("%d")}
        SqoopTool.execScrpit("syncAddUser.sqoop",param)
        sd = sd + datetime.timedelta(days=1);#
    print '日统计数据已完成';
    return None
def computeRetentionBy2Day(startDate,endDate):
    print '开始处理对2日留存统计'
    retentionDayNum = 2;
    #开始日期和结束日期往前推1天
    sd = startDate + datetime.timedelta(days=-1); #
    ed = endDate + datetime.timedelta(days=-1); #

    while sd<ed:
        startDateStr = sd.strftime("%Y-%m-%d");
        sd = sd + datetime.timedelta(days=1);  #
        endDateStr =  sd.strftime("%Y-%m-%d");
        HiveTool.exceFile(filePath="statistic/retention/RetentionByNumDay.hive",
                          hived={"startDate": startDateStr, "endDate": endDateStr,"dayNum":2});

    print '对2日留存统计已完成'
    return None
def computeRetentionBy3Day(startDate,endDate):
    print '开始处理对3日留存统计'
    retentionDayNum = 3;
    # 开始日期和结束日期往前推2天
    sd = startDate + datetime.timedelta(days=1-retentionDayNum);  #
    ed = endDate + datetime.timedelta(days=1-retentionDayNum);  #
    while sd < ed:
        startDateStr = sd.strftime("%Y-%m-%d");
        sd = sd + datetime.timedelta(days=retentionDayNum-1);  #
        endDateStr = sd.strftime("%Y-%m-%d");
        HiveTool.exceFile(filePath="statistic/retention/RetentionByNumDay.hive",
                          hived={"startDate": startDateStr, "endDate": endDateStr, "dayNum": 3});
    print '对3日留存统计已完成'
    return None