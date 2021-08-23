# -*- coding: utf-8 -*-

import datetime
import sys
from common.tool.action.factory import ReportFactory
from common.logger import Logger
from common.utils import RunMode
sys.stdout = Logger(); #日志监控
step = [1,2]
def generateData(start,end):
    while start < end:
        startDate = datetime.datetime.strptime(start, '%Y-%m-%d')
        endDate = startDate + datetime.timedelta(days=1);
        print '执行时间范围:%s~%s'%(startDate,endDate)
        print '\n'
        ReportFactory(startDate, endDate, RunMode.RunMode.DAY.value).creator().start(step)
        start = endDate.strftime("%Y-%m-%d")


today = datetime.datetime.today().strftime('%Y-%m-%d');
if today == '2021-03-26':
    generateData('2020-01-01','2020-04-01');
elif today == '2021-03-20':
    generateData('2020-04-01','2020-08-04');
elif today == '2021-03-21':
    generateData('2019-08-26', '2021-01-01');





