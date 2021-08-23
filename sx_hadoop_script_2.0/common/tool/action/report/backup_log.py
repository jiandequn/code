#!/usr/bin/env python
# -*- coding: utf-8 -*-
import datetime
from common.tool.action.report.base.day import DayBuilder
from common.utils import Constant
day_step="1";
class BackupLog(DayBuilder):
    def start_day(self):
        if not self._logic_validate(1):return
        print "处理步骤【%d】" % 1
        print '开始备份hive待处理日志数据...'
        i = 0;
        hive_backup_dir = ''.join([Constant.HADOOP_HDFS_URI, Constant.HIVE_DIR, "backup/"])
        self._action._hadoopUtils.rm(hive_backup_dir);

        while True:
            t = self._action._startDate + datetime.timedelta(days=i);
            i = i + 1;
            if t > self._action._endDate: break;
            # 处理数据
            sd = t.strftime("%Y-%m-%d");
            print t.year, t.month, t.day
            # 清理中间HIVE备份库的数据（如果load 原始日志，会导致数据迁移位置）
            hive_back_date_url = ''.join([hive_backup_dir, sd])
            self._action._hadoopUtils.mkdir(hive_back_date_url);
            self._action._hadoopUtils.cp(
                ''.join([Constant.HADOOP_HDFS_URI, Constant.HADOOP_LOG_DIR, t.strftime("%Y/%m/%d"), "/*/*"]),
                hive_back_date_url);
            # self.hiveUtils.exce("LOAD DATA  INPATH '%s/*' overwrite INTO TABLE yn_hadoop.source_logs PARTITION (y='%s',m='%s',d='%s');"
            #               %(hive_back_date_url,t.strftime("%Y"),t.strftime("%m"),t.strftime("%d")))
            self._action._hiveUtils.exceFile("log_analysis/loadLogs.hive",
                                    hived={"hive.back.url": hive_back_date_url, "year": t.strftime("%Y"),
                                           "month": t.strftime("%m"), "day": t.strftime("%d")});
            print '备份hive待处理日志数据已完成。'