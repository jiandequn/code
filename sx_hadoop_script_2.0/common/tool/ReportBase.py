#!/usr/bin/env python
# -*- coding: utf-8 -*-
import math
import abc
class ReportBase(object):
    _metaclass__ = abc.ABCMeta
    @abc.abstractproperty
    def currentTable(self):
        """
             定义当前操作表
           """
        raise NotImplementedError
    def __init__(self,startDate,endDate,runMode='day'):
        self.__startDate=startDate;
        self.__endDate=endDate;
    """
              启动任务：
            """
    def start(self):
        print "生成报表数据:"
        
    """按日生成数据报表接口"""
    @abc.abstractmethod
    def __day__(self):
        """数据表处理中心"""
        raise NotImplementedError

    """周生成数据报表接口"""
    @abc.abstractmethod
    def __week__(self):
        """数据表处理中心"""
        raise NotImplementedError

    """月份生成数据报表接口"""

    @abc.abstractmethod
    def __month__(self):
        """数据表处理中心"""
        raise NotImplementedError

    """年份生成数据报表接口"""

    @abc.abstractmethod
    def __year__(self):
        """数据表处理中心"""
        raise NotImplementedError

    """季度生成数据报表接口"""
    @abc.abstractmethod
    def __quarter__(self):
        """数据表处理中心"""
        raise NotImplementedError
