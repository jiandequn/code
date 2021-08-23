#!/usr/bin/env python
# -*- coding: utf-8 -*-
import os
import subprocess

from common.tool.action.report.base.week import WeekBuilder
from common.utils import Constant

week_step="0"
class ShanXiTask(WeekBuilder):
    def start_week(self):
        if not Constant.conf.getValue("OTHER", "sx_task") == "1": return
        if not self._logic_validate(0): return;
        print '开始处理陕西日志：'
        self._generateLog();
        print '结束陕西日志处理';

    def _generateLog(self):
        y = self._action._startDate.strftime('%Y')
        w = int(self._action._startDate.strftime('%W')) + 1
        fileName = "新版本-历史点播记录%s-%s.csv" % (y, w);
        dir = "%s/%s" % (Constant.conf.getValue("OTHER","sx_task_save_file_dir"),self._action._endDate.strftime("%Y%m%d"));
        cmdstr = """
        hive -e "
        use %s;
        set hive.exec.dynamic.partition=true;
        set hive.exec.dynamic.partition.mode=nonstrict;
        set hive.vectorized.execution.enabled=false;
        set hive.groupby.orderby.position.alias=true; -- 启用别名对于groupby和orderby
        set hive.cli.print.header=true;  -- 将表头输出
        select '' as ott_productName,
        mac,
        sn,
        create_time t,
        parent_column_id as cpId,
        p.column_name as cpName,
        a.album_name albumName,
        e.param['videoId'] as serviceIndex,
        v.video_name as videoName,
        e.param['timePosition'] as consumeTime,
        act.content_type_name as contentTypeName,
        '' as packageType
        from events_type_log e
        left join product_column p on p.column_id=e.parent_column_id
        left join album a on a.album_id=e.param['albumId']
        left join album_content_type act on act.content_type=a.content_type
        left join video v on v.video_id=e.param['videoId']
        where e.events_type='COLUMN_TYPE_HISTORY_POINT' and concat(e.y,e.m,e.d)>='%s'
         and concat(e.y,e.m,e.d)<'%s' and e.param['operateType']='add'
        " | sed 's/[\t]/,/g'  > %s/%s
        """ % (Constant.HIVE_DATABASE,self._action._startDate.strftime("%Y%m%d"),self._action._endDate.strftime("%Y%m%d"), dir, fileName)

        print "删除目录 %s" % dir;
        print self._action._console.cmd("rm -rf %s" % dir);
        print "创建目录 %s" % dir;
        print self._action._console.cmd("mkdir %s" % dir)
        print "生成文件 %s/%s" % (dir, fileName);
        print self._action._console.cmd(cmdstr);
        print "同步文件 %s" % dir;
        scpmd = "sh sx_scp_asny.sh %s" % dir
        print scpmd;
        subp = subprocess.Popen(scpmd, shell=True, stdout=subprocess.PIPE, cwd="%s/bin"% os.getcwd())
        c = subp.stdout.readline()
        while c:
            print c
            c = subp.stdout.readline()
        print subp.returncode
        print "同步文件完成";