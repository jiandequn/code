#!/usr/bin/env python
# -*- coding: utf-8 -*-
from common.tool import HiveTool
from common.tool.Config import config
from common.tool.ConsoleUtils import ConsoleUtil
from common.utils import Constant


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
        "jdbc.password":password,
        "hive.database":Constant.HIVE_DATABASE,
        "hive.province":Constant.HIVE_PROVINCE
    }
    console = ConsoleUtil()
    hiveUtils = HiveTool.HiveUtil(console)
    hiveUtils.exceFile("init_table/base.hive",hived=hived,hive_database="default")
    hiveUtils.exceFile("init_table/report.hive", hived=hived)
    # hiveUtils.exceFile("init_table/initTable.hive", hived=hived)
    # hiveUtils.exceFile("init_table/initTable.hive", hived=hived)
    # hiveUtils.exceFile("statistic/log/initLogTable.hive",hived=hived)
    if Constant.conf.getValue("OTHER", "yn_task") == "1":
        hiveUtils.exceFile("init_table/yunnan.hive", hived=hived)

initHiveTable();

