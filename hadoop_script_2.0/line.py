#!/usr/bin/env python
# -*- coding: utf-8 -*-

import os

import sys
reload(sys)
sys.setdefaultencoding('utf-8')
lines=open('/home/app/mongo_log/data/txt.log',"r").readlines();
lines14=open('/home/app/mongo_log/data/txt14.log',"r").readlines();

command="hadoop fs -cat /data/logs/2020/12/{{17..29}/*,30/{00..10},30/15}/* |grep %s"
for line14 in lines14:
    if lines.count(line14)==0:
        print line14
