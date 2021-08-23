#!/usr/bin/env python
# -*- coding: utf-8 -*-
import os
import subprocess

from common.utils import Constant


def actionLocalCmd(command):
    # print "执行本地命令：%s" %command
    subp = subprocess.Popen(command, shell=True, stdout=subprocess.PIPE,cwd=Constant.SCRIPT_PATH)
    c = subp.stdout.readline()
    while c:
        print c
        c = subp.stdout.readline()
    print subp.returncode

def actionLocalOsCmd(cmd): #不推荐使用，无输出内容
    print "执行本地命令：%s" % cmd
    os.system(cmd)
