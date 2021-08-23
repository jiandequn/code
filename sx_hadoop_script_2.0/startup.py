#!/usr/bin/env python
# -*- coding: utf-8 -*-
import datetime

import sys

from common.tool.action.factory import ReportFactory

from common.logger import Logger
from common.utils import RunMode

sys.stdout = Logger(); #日志监控
step = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15]
today = datetime.datetime.today()
print '日数据执行：'
ReportFactory().creator().start(step);#runMode=day;
if today.weekday()==0:
    print '周数据执行：'
    ReportFactory(runMode=RunMode.RunMode.WEEK.value).creator().start(step);
if today.day==4:
    print '月数据执行：'
    ReportFactory(runMode=RunMode.RunMode.MONTH.value).creator().start(step);
