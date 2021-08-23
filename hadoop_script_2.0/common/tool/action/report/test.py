#!/usr/bin/env python
# -*- coding: utf-8 -*-

from common.tool.action.report.base.day import DayBuilder
class Test(DayBuilder):
    def start_day(self):
        # 需分析文件已Load完；对日期范围内数据数据进行分区和分时间存储
        if not self._logic_validate(20):
            print '过滤了20步骤'
        else:print '处理了20步骤'
        print "处理步骤【%d】" % 20
