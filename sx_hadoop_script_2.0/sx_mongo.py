#!/usr/bin/env python
# -*- coding: utf-8 -*-
import sys

from common.tool import HiveTool
from common.tool.ConsoleUtils import ConsoleUtil
import datetime

from common.valid import validateParam
from common.valid.dateValidator import DateValidator


def startByDay(startDate,endDate):
    # sdate=datetime.datetime.strptime(startDate, '%Y-%m-%d')
    # edate=datetime.datetime.strptime(endDate, '%Y-%m-%d')
    sdate=startDate;
    edate=endDate
    while(sdate<edate):
        endDate=sdate + datetime.timedelta(days=1);
        print sdate,endDate
        generate_album_play(sdate,endDate)
        sdate=endDate
    #
    # #


def generate_album_play(sdate,eDate):
    hived = {
        "hive.database": "sx_mongo",
        "startDate2":(sdate-datetime.timedelta(days=1)).strftime("%Y-%m-%d"),
        "startDate":sdate.strftime("%Y-%m-%d"),
        "endDate":eDate.strftime("%Y-%m-%d")
    }
    console = ConsoleUtil()
    hiveUtils = HiveTool.HiveUtil(console)
    hiveUtils.exceFile("other/shanxi/mongodb/album_play.hive", hived=hived, hive_database="sx_mongo")

argArr = sys.argv;
params = validateParam.convertParam(argArr);  # 把参数key，value化
if params.has_key("startDate") and params.has_key("endDate"):
    startDate = DateValidator().vlidateParam(key="startDate", params=params);
    endDate = DateValidator().vlidateParam(key="endDate", params=params);
    startByDay(startDate,endDate);#按天计算mongo日志的播放数据
else:print 'startDate,endDate参数未指定'


