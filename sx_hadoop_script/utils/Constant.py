#!/usr/bin/env python
# -*- coding: utf-8 -*-

from tool.Config import config

conf = config("conf/conf.ini");
HADOOP_HDFS_URI=conf.getValue("HADOOP", "hadoop.hdfs.uri");
HADOOP_LOG_DIR=conf.getValue("HADOOP", "hdfs.log.dir");
HIVE_DIR=conf.getValue("HIVE","hive.dir")
SHELL_FLAG= True if conf.getValue("SSH","is_client")=="true" else False;
