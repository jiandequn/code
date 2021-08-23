#!/usr/bin/env python
# -*- coding: utf-8 -*-
import datetime
import os
import sys
reload(sys)
sys.setdefaultencoding('utf-8')

start="2020-12-17 09";
createTime=datetime.datetime.strptime(start, '%Y-%m-%d %H')
while start<"2020-12-30 16":
    path=createTime.strftime("%Y/%m/%d/%H")
    if os.path.exists("bak/%s" %path):
        lines14 = open('bak/%s/txt.log' % path, "r").readlines();
        if os.path.exists("old/%s" %path):
            lines = open('old/%s/txt.log'%path, "r").readlines();
            for line14 in lines14:
                if lines.count(line14)<=0:
                    print line14
        else:
            for line14 in lines14:
              print line14
    createTime=createTime+datetime.timedelta(hours=1)
    start=createTime.strftime("%Y-%m-%d %H")