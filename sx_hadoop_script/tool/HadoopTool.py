#!/usr/bin/env python
# -*- coding: utf-8 -*-
from tool import ConsoleUtils
from tool import LocalLinuxCommand
def cp(src,dest): #复制
    ConsoleUtils.cmd("hadoop fs -cp -p %s %s" % (src,dest))

def mkdir(path):#创建目录
    ConsoleUtils.cmd("hadoop fs -mkdir -p %s" % path)

def rm(path):#移除
    ConsoleUtils.cmd("hadoop fs -rm -R -f %s" %path);
