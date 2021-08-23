#!/usr/bin/env python
# -*- coding: utf-8 -*-
from tool import ConsoleUtils
from tool import LocalLinuxCommand


def exce(sqlScript):
    ConsoleUtils.cmd("hive -e \"%s\"" %sqlScript)

def exceFile(filePath,hived=None,hivevar=None,hiveconf=None):
    hiveParam=""
    if hived :
      for k,v in hived.items():
          hiveParam+=" -d %s=%s" %(k,v);
    if hivevar:
        for k, v in hivevar.items():
            hiveParam += " --hivevar %s=%s" % (k, v);
    if hiveconf:
        for k, v in hivevar.items():
            hiveParam += " --hiveconf %s=%s" % (k, v);
    ConsoleUtils.cmd("cat hive/%s" % (filePath))
    ConsoleUtils.cmd("hive%s -f hive/%s" % (hiveParam,filePath))

