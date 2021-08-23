#!/usr/bin/env python
# -*- coding: utf-8 -*-
from common.tool.Config import config

conf = config("conf/conf.ini");
LOG_DIR=conf.getValue("LOG","dir")
HADOOP_HDFS_URI=conf.getValue("HADOOP", "hadoop.hdfs.uri");
HADOOP_LOG_DIR=conf.getValue("HADOOP", "hdfs.log.dir");
HIVE_DIR=conf.getValue("HIVE","hive.dir")
HIVE_PROVINCE=conf.getValue("HIVE","hive.province")
HIVE_DATABASE=conf.getValue("HIVE","hive.database")
SHELL_FLAG= True if conf.getValue("SSH","is_client")=="true" else False;
SCRIPT_PATH=conf.getValue("SSH","script.path");
#报表数据库配置
REPORT_DB_URL=conf.getValue("REPORT_DB","db.url");
REPORT_DB_USERNAME=conf.getValue("REPORT_DB","db.username");
REPORT_DB_PWDFILE=conf.getValue("REPORT_DB","db.pwdfile");
#业务内容数据库
CONTENT_CENTER_MANAGER_URL=conf.getValue("CONTENT_CENTER_MANAGER","db.url");
CONTENT_CENTER_MANAGER_USERNAME=conf.getValue("CONTENT_CENTER_MANAGER","db.username");
CONTENT_CENTER_MANAGER_PWDFILE=conf.getValue("CONTENT_CENTER_MANAGER","db.pwdfile");
#业务用户中心数据库
USER_CENTER_MANAGER_URL=conf.getValue("USER_CENTER_MANAGER","db.url");
USER_CENTER_MANAGER_USERNAME=conf.getValue("USER_CENTER_MANAGER","db.username");
USER_CENTER_MANAGER_PWDFILE=conf.getValue("USER_CENTER_MANAGER","db.pwdfile");

