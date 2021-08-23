#!/usr/bin/env python
# -*- coding: utf-8 -*-
import sys

from common.README import explainFuncion
from common.tool.action.factory import ReportFactory
from common.valid import validateParam
from common.valid.dateValidator import DateValidator

from common.logger import Logger

sys.stdout = Logger(); #日志监控
argArr = sys.argv;
notRun = argArr.count("--help") > 0 or argArr.count("-h") > 0;  # 是否展示说明
step = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15]

if notRun:  # 表示用户需要展示说明
    explainFuncion();
    pass
else:
    params = validateParam.convertParam(argArr);  # 把参数key，value化
    if(params.has_key("runMode")):
        run_mode = params.get("runMode")
    if params.has_key("step"):
        step = map(int,str.split(params.get("step"), ','))
    if params.has_key("startDate") and params.has_key("endDate"):
        startDate = DateValidator().vlidateParam(key="startDate", params=params);
        endDate = DateValidator().vlidateParam(key="endDate", params=params);
        ReportFactory(startDate, endDate, run_mode).creator().start(step)
    else:
        ReportFactory(runMode=run_mode).creator().start(step);












