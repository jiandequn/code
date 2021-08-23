#!/usr/bin/env python
# -*- coding: utf-8 -*-
import subprocess
def actionLocalCmd(command):
    print "执行本地命令：%s" %command
    subp = subprocess.Popen(command, shell=True, stdout=subprocess.PIPE)
    c = subp.stdout.readline()
    while c:
        print c
        c = subp.stdout.readline()
    print subp.returncode