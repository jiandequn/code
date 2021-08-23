#!/usr/bin/env python
# -*- coding: utf-8 -*-
import datetime
from enum import Enum
class RunMode(Enum):
    DAY = 'day'
    MONTH = 'month'
    WEEK = 'week'
    QUARTER = 'quarter'
    YEAR = 'year'


"""根据类型获取时间返回"""


def getTime(run_model):
    if run_model == RunMode.DAY:
        today = datetime.datetime.today()
        endDate = datetime.datetime(today.year, today.month, today.day, 0, 0, 0)
        startDate = endDate + datetime.timedelta(days=-1)
        return startDate, endDate;
    elif run_model == RunMode.WEEK:
        week = datetime.date.today().weekday();
        monday = datetime.date.today() - datetime.timedelta(days=(week + 7))
        sunday = datetime.date.today() - datetime.timedelta(days=(week))
        return monday, sunday
    elif run_model == RunMode.MONTH:
        lastDay = datetime.datetime.today() - datetime.timedelta(days=(datetime.datetime.today().day))
        firstDay = lastDay - datetime.timedelta(days=(lastDay.day - 1))
        return firstDay, lastDay;
    else:
        return getTime(RunMode.WEEK);

"""获取枚举类型"""
def getRunModeEnum(run_model):
    for runmode in RunMode:
        if runmode.value == run_model:
            return runmode;
    return RunMode.WEEK; #sx_log|yn_log
