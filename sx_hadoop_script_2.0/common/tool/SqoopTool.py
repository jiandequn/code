#!/usr/bin/env python
# -*- coding: utf-8 -*-
from common.utils import Constant


class SqoopUtil(object):
    def __init__(self,console):
        self.console = console;

    def execScrpit(self,fileName, arr={},dbconf={"db_url":Constant.REPORT_DB_URL,
                                                 "db_username":Constant.REPORT_DB_USERNAME,
                                                 "db_pwdfile":Constant.REPORT_DB_PWDFILE},
                   hivedb=Constant.HIVE_DATABASE):  # 创建目录
        k = open("work/%s" % fileName, "r")
        lines = k.readlines();
        arr.update(dbconf)
        arr.update({"hivedb":hivedb,"sqoop_outdir":Constant.LOG_DIR,"hive_province":Constant.HIVE_PROVINCE})
        if len(lines) > 0:
            content = ''.join(lines)
            if len(arr) > 0:
                content = content % arr
        print content
        self.console.cmd(content)