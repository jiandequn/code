#!/usr/bin/env python
# -*- coding: utf-8 -*-
from tool import HiveTool
from tool.Config import config

def initHiveTable():
    conf = config("conf/conf.ini");
    mysql_url=conf.getValue("MYSQL", "mysql_url")
    mysql_port=conf.getValue("MYSQL", "mysql_port")
    mysql_database=conf.getValue("MYSQL", "mysql_database")
    username = conf.getValue("MYSQL", "mysql_user");
    password = conf.getValue("MYSQL", "mysql_pwd");
    url= "jdbc:mysql://%s:%s/%s"%(mysql_url,mysql_port,mysql_database)
    hived = {
        "jdbc.url":url,
        "jdbc.username":username,
        "jdbc.password":password
    }
    HiveTool.exceFile("createLogsTable.hive",hived=hived)

initHiveTable();

