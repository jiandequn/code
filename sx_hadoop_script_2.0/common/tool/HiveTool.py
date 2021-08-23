#!/usr/bin/env python
# -*- coding: utf-8 -*-
from common.utils import Constant


class HiveUtil(object):
    def __init__(self,console):
        self.console = console

    def exce(self,sqlScript):
        self.console.cmd("hive -e \"%s\"" % sqlScript)

    def exceFile(self,filePath, hived=None, hivevar=None, hiveconf=None,hive_database=Constant.HIVE_DATABASE):
        hiveParam = ""
        if hived:
            for k, v in hived.items():
                hiveParam += " -d %s=%s" % (k, v);
        if hivevar:
            for k, v in hivevar.items():
                hiveParam += " --hivevar %s=%s" % (k, v);
        if hiveconf:
            for k, v in hivevar.items():
                hiveParam += " --hiveconf %s=%s" % (k, v);
        self.console.cmd("cat work/%s" % (filePath))
        self.console.cmd("hive --database=%s %s -f work/%s" % (hive_database,hiveParam, filePath))


