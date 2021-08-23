#!/usr/bin/env python
# -*- coding: utf-8 -*-
import os
import sys

import datetime

import runStep
from README import explainFuncion
from logger import Logger
from tool import HadoopTool
from tool import HiveTool
from utils import Constant

from valid import validateParam
from valid.dateValidator import DateValidator

sys.stdout = Logger(); #日志监控
argArr = sys.argv;
notRun = argArr.count("--help") > 0 or argArr.count("-h") > 0;  # 是否展示说明
step = ['1','2','3','4','5','6','7','8','9','10','11','12']
if notRun:  # 表示用户需要展示说明
    explainFuncion();
    pass
else:
    params = validateParam.convertParam(argArr);  # 把参数key，value化
    startDate = DateValidator().vlidateParam(key="startDate", params=params);
    endDate = DateValidator().vlidateParam(key="endDate", params=params);
    daysNum = (endDate - startDate).days;
    if daysNum>0:
        if params.has_key("step"):
            step = str.split(params.get("step"),',')
        if step.count("1"):runStep.backupLog(startDate,endDate);#把需要处理的数据复制到加载到hive中
        if step.count("2"):runStep.generateEventLogsData(startDate,endDate);#把待处理的时间内数据按类型，年，月，日分区存储
        if step.count("3"):runStep.computeStatisticsByDay(startDate,endDate)    #按天统计信息
        if step.count("4"):runStep.computeRetentionBy2Day(startDate,endDate);  #根据时间段统计2日留存用户数
        if step.count("5"): runStep.computeRetentionBy3Day(startDate, endDate);  # 根据时间段统计3日留存用户数
        # if step.count("4"):runStep.computeStatisticsByWeek(startDate,endDate) #按周统计












