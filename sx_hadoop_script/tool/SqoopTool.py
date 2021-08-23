#!/usr/bin/env python
# -*- coding: utf-8 -*-
from tool import ConsoleUtils


def execScrpit(fileName,arr={}):#创建目录
    k= open("sqoop/%s" %fileName,"r")
    lines =k.readlines();
    if len(lines)>0:
        content=''.join(lines)
        if len(arr)>0:
            content = content % arr
    print content
    ConsoleUtils.cmd(content)