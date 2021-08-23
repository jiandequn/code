#!/usr/bin/env python
# -*- coding: utf-8 -*-
import time
import sys
# 日志数据输出

class Logger(object):
   def __init__(self,filename="log/run-%s.log" % time.strftime("%Y-%m-%d", time.localtime())):
        self.count=0
        self.terminal=sys.stdout
        self.log=open(filename,"a+",buffering=0)

   def write(self,message):
         if message == None or message == "" or message == "\n":
            return;
         self.terminal.write(message);
         if len(message)==1:
            if self.count ==100:
                self.count = 0;
                self.log.write("\n");
            self.log.write("%s" %message);
            self.count= self.count+1;
            return;
         self.log.write("%s %s\n" %(time.strftime("%Y-%m-%d %H:%M:%S", time.localtime()),message));

   def flush(self):
	pass
