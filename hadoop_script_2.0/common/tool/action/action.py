#!/usr/bin/env python
# -*- coding: utf-8 -*-
import abc
import importlib
import os
import re

from common.tool import HadoopTool, HiveTool, SqoopTool
from common.tool.ConsoleUtils import ConsoleUtil
from common.utils import RunMode


class BaseAction(object):

    def __init__(self,startDate,endDate,runMode=RunMode.RunMode.WEEK):
        self._startDate=startDate;
        self._endDate=endDate;
        self.__runMode=runMode;
        self._console = ConsoleUtil()
        self._hadoopUtils = HadoopTool.HadoopUtil(self._console);
        self._hiveUtils = HiveTool.HiveUtil(self._console)
        self._sqoopUtils = SqoopTool.SqoopUtil(self._console)


    """
              启动任务：
            """
    def start(self,step=[]):
        self._step=step;
        self._run()

    def _run(self):
        for root, dirs, files in os.walk(os.path.join(os.getcwd(), "common", "tool", "action", "report")):
            for fileName in files:
                if (not fileName.startswith("_")) and fileName.endswith(".py"):
                    m = importlib.import_module('common.tool.action.report.%s' % fileName[0:-3])
            break;
        sub_class_list = self._getRunType().__subclasses__()
        sub_class_list2 = self._getSubClass();
        action_sub_class_map = {};
        for i in range(len(sub_class_list)):
            # 获取子类的类名
            class_name = sub_class_list[i].__name__
            for py_file in sub_class_list2:
                stepModel='%s_step' %self.__runMode.value;
                if hasattr(py_file, class_name) and hasattr(py_file,stepModel):
                    stepModelList= map(int, str.split(getattr(py_file,stepModel), ','))
                    t = getattr(py_file, class_name);
                    for stepvalue in stepModelList:
                        if action_sub_class_map.has_key(stepvalue):
                            action_sub_class_map[stepvalue].append(t);
                        else:
                            action_sub_class_map[stepvalue]=[t];
        #配置执行顺序
        for st in self._step:#如果step在相关类中，实例化该类，并执行该类的方法体
            if action_sub_class_map.has_key(st):
                action_class_list = action_sub_class_map.get(st);
                for action_class in action_class_list:
                    getattr(action_class(self, st), 'start_%s' % self.__runMode.value)();

    def _getRunType(self):
        """获取处理类型"""
        m =importlib.import_module('common.tool.action.report.base.%s' % self.__runMode.value)
        return getattr(m,"%sBuilder" % self.__runMode.value.capitalize())

    def _getSubClass(self):
        m = importlib.import_module('common.tool.action.report')
        t = m.__dict__
        py_files = [];
        for a in t:
            if a != 'base' and not re.match("__", a, flags=0):
                py_file = getattr(m, a)
                py_files.append(py_file)
        return py_files;