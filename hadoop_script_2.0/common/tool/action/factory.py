#!/usr/bin/env python
# -*- coding: utf-8 -*-
import datetime
import importlib

from common.tool.action import action
from common.utils import RunMode


class ReportFactory(object):
    def __init__(self, startDate=None, endDate=None, runMode="day"):
        self.__runMode = RunMode.getRunModeEnum(runMode);
        if (not startDate) and (not endDate):
            self.__startDate, self.__endDate = RunMode.getTime(self.__runMode);
        else:
            self.__startDate = startDate;
            self.__endDate = endDate;

    def creator(self):
        return action.BaseAction(self.__startDate, self.__endDate,self.__runMode);



