#!/usr/bin/env python
# -*- coding: utf-8 -*-

def explainFuncion():
    pidFile = open("conf/README.txt", "r", buffering=0);
    for line in pidFile.readlines():
         print line
