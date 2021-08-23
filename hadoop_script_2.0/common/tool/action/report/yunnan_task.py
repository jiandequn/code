#!/usr/bin/env python
# -*- coding: utf-8 -*-
import datetime

from common.tool.action.report.base.week import WeekBuilder
from common.tool.mysqlInit import mysql_init
from common.utils import Constant

week_step="0"
class YunNanTask(WeekBuilder):
    def start_week(self):
        if not Constant.conf.getValue("OTHER", "yn_task") == "1": return
        if not self._logic_validate(0): return;
        print "开始生成云南日志数据"
        # 重命令日志记录表
        self._renameTable(self._action._startDate.strftime("%Y%m%d"), (self._action._endDate + datetime.timedelta(days=-1)).strftime("%Y%m%d"))
        self._action._hiveUtils.exceFile(filePath="other/yunnan/log/query_detail_log.hive",
                                hived={"startDate": self._action._startDate.strftime("%Y-%m-%d"),
                                       "endDate": self._action._endDate.strftime("%Y-%m-%d")});
        self._action._hiveUtils.exceFile(filePath="other/yunnan/log/query_login_log.hive",
                                hived={"startDate": self._action._startDate.strftime("%Y-%m-%d"),
                                       "endDate": self._action._endDate.strftime("%Y-%m-%d")});
        self._action._hiveUtils.exceFile(filePath="other/yunnan/log/query_play_album_log.hive",
                                hived={"startDate": self._action._startDate.strftime("%Y-%m-%d"),
                                       "endDate": self._action._endDate.strftime("%Y-%m-%d")});
        self._action._hiveUtils.exceFile(filePath="other/yunnan/log/app_user_stay_duration_week.hive",
                                hived={"startDate": self._action._startDate.strftime("%Y-%m-%d"),
                                       "endDate": self._action._endDate.strftime("%Y-%m-%d")});
        self._action._hiveUtils.exceFile(filePath="other/yunnan/log/auth_product_user.hive",
                                hived={"startDate": self._action._startDate.strftime("%Y-%m-%d"),
                                       "endDate": self._action._endDate.strftime("%Y-%m-%d")});
        self._action._sqoopUtils.execScrpit(fileName="other/yunnan/log/export_detail_page_log.sqoop")
        self._action._sqoopUtils.execScrpit(fileName="other/yunnan/log/export_login_page_log.sqoop")
        self._action._sqoopUtils.execScrpit(fileName="other/yunnan/log/export_album_play_log.sqoop")
        self._action._sqoopUtils.execScrpit(fileName="other/yunnan/log/export_auth_product_user.sqoop")
        self._action._sqoopUtils.execScrpit(fileName="other/yunnan/log/export_app_user_stay_duration_week.sqoop")
        print "云南日志数据已生成完成"

    def _renameTable(self,startDate, endDate):
        tableArr = [("login_page_log", u"首页访问日志"), ("detail_page_log", u"详情页日志"), ("album_play_log", u"播放记录日志")];
        m = mysql_init()
        for at in tableArr:
            t = at[0];
            tname = at[1];
            t_val = m.query("select SUBSTR(min(create_time),1,10),SUBSTR(max(create_time),1,10) from %s" % t);
            if len(t_val) == 1 and t_val[0][0] is not None and t_val[0][1] is not None:
                print t_val;
                sdate = t_val[0][0].replace("-", "");
                edate = t_val[0][1].replace("-", "");
                renameTable = "%s_%s_%s" % (t, sdate, edate);
                # 检查表是否已存在，如果存在drop table
                m.execute("drop table IF EXISTS %s" % renameTable);
                # 增加描述信息
                renameTableSql = "rename table %s to %s;" % (t, renameTable);
                m.execute(renameTableSql);  # 重命名表
                m.execute("ALTER TABLE %s COMMENT '%s%s_%s';" % (renameTable, tname, sdate, edate))
                createTableSql = "create table %s select * from %s where 1=2" % (t, renameTable);
                m.execute(createTableSql);  # 创建表结构
                m.execute("ALTER TABLE %s COMMENT '%s%s_%s';" % (t, tname, startDate, endDate))
        m.close()