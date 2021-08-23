#!/usr/bin/env python
# -*- coding: utf-8 -*-
class HadoopUtil(object):
    def __init__(self,console):
        self.console = console;
        pass
    def cp(self,src,dest): #复制
        self.console.cmd("hadoop fs -cp -p %s %s" % (src,dest))

    def mkdir(self,path):#创建目录
        self.console.cmd("hadoop fs -mkdir -p %s" % path)

    def rm(self,path):#移除
        self.console.cmd("hadoop fs -rm -R -f %s" %path);
