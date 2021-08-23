#!/usr/bin/env python
# -*- coding: utf-8 -*-
import datetime
from common.tool.action.report.base.day import DayBuilder
from common.utils import Constant
day_step="5,6,7"
class RetentionUser(DayBuilder):
    def start_day(self):
        if self._logic_validate(5):self.__computeRetentionBy2Day();
        elif self._logic_validate(6):self.__computeRetentionBy3Day();
        elif self._logic_validate(7):self.__computeAddUserRetentionBy2Day();

    def __computeRetentionBy2Day(self):
        print '开始处理对2日留存统计'
        # 开始日期和结束日期往前推1天
        self.__retentionByDay("user_2day_count")

        print '对2日留存统计已完成'
        return None

    def __computeRetentionBy3Day(self):
        print '开始处理对3日留存统计'
        retentionDayNum = 3;
        # 开始日期和结束日期往前推2天
        self.__retentionByDay("user_3day_count", retentionDayNum)
        print '对3日留存统计已完成'
        return None

    def __computeAddUserRetentionBy2Day(self):
        print '开始处理对新增用户2日留存统计'
        retentionDayNum = 2;
        # 开始日期和结束日期往前推2天AddUserRetentionByNumDay
        self.__retentionByDay("add_user_2day_count", retentionDayNum,
                              hiveFile="AddUserRetentionByNumDay")
        print '对新增用户2日留存统计已完成'

    def __retentionByDay(self, update_field="user_2day_count", retentionDayNum=2,
                         hiveFile="RetentionByNumDay"):
        # 开始日期和结束日期往前推2天
        self._action._hiveUtils.exce("truncate table %s.app_retention_count_day" % Constant.HIVE_DATABASE)  # 清空表
        sd = self._action._startDate + datetime.timedelta(days=1 - retentionDayNum);  #
        ed = sd + datetime.timedelta(days=retentionDayNum - 1)
        while ed < self._action._endDate:
            startDateStr = sd.strftime("%Y-%m-%d");
            endDateStr = ed.strftime("%Y-%m-%d");
            self._action._hiveUtils.exceFile(filePath="report/app/retention/%s.hive" % hiveFile,
                                    hived={"startDate": startDateStr, "endDate": endDateStr,
                                           "dayNum": retentionDayNum});
            sd = sd + datetime.timedelta(days=1);  #
            ed = ed + datetime.timedelta(days=1);
            # 对留存用户数据进行导入
        self._action._sqoopUtils.execScrpit("report/app/retention/exportRetentionUserCount.sqoop", {"count_field": update_field})