#!/usr/bin/env python
# -*- coding: utf-8 -*-
__author__ = "jiandequn"
import ConfigParser
class config:
    def __init__(self,path):
        self.config = ConfigParser.ConfigParser()
        self.config.readfp(open(path))
    def getValue(self,section,key):
        return self.config.get(section, key)

if __name__ == "__main__":
    conf = config("conf/conf.ini");
    host = conf.getValue("MONGODB", "host");
    port = conf.getValue("MONGODB", "port");
    database = conf.getValue("MONGODB", "database");
    print host
    print port
    print database

