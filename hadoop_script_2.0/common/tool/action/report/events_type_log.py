#!/usr/bin/env python
# -*- coding: utf-8 -*-

from common.tool.action.report.base.day import DayBuilder
day_step="2";
class EventsTypeLog(DayBuilder):
    def start_day(self):
        # 需分析文件已Load完；对日期范围内数据数据进行分区和分时间存储
        if not self._logic_validate(2): return;
        print '开始处理对source_logs进行分类处理'
        self._action._sqoopUtils.execScrpit(fileName="log_analysis/import_product_column.sqoop");
        self._action._sqoopUtils.execScrpit(fileName="log_analysis/import_clean_user.sqoop")
        self._action._hiveUtils.exceFile(filePath="log_analysis/eventsType.hive",
                                hived={"startDate": self._action._startDate.strftime("%Y-%m-%d"),
                                       "endDate": self._action._endDate.strftime("%Y-%m-%d")});
        print 'source_logs进行分类处理已完成';