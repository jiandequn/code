#!/usr/bin/env python
# -*- coding: utf-8 -*-
import datetime
from common.tool.action.report.base.day import DayBuilder

day_step="3";
class IncreaseUser(DayBuilder):
    def start_day(self):
        if not self._logic_validate(3): return;
        print '开始处理新增用户统计数据'
        self._action._sqoopUtils.execScrpit("report/app/increase_user/import_user.sqoop")  # 同步全量用户信息
        self._action._hiveUtils.exceFile(filePath="report/app/increase_user/app_increase_user.hive",
                                hived={"startDate": self._action._startDate.strftime("%Y-%m-%d"),
                                       "endDate": self._action._endDate.strftime("%Y-%m-%d")});
        sd = self._action._startDate + datetime.timedelta(days=0);  #
        ed = self._action._endDate + datetime.timedelta(days=0);  #
        while sd < ed:
            param = {"y": sd.strftime("%Y"), "m": sd.strftime("%m"), "d": sd.strftime("%d")}
            self._action._sqoopUtils.execScrpit("report/app/increase_user/export_increase_user.sqoop", param)
            sd = sd + datetime.timedelta(days=1);
        print '日新增用户统计数据已完成';
