#!/usr/bin/env python
# -*- coding: utf-8 -*-
import abc

from common.tool.action.report.base.builder import BaseBuilder
class QuarterBuilder(BaseBuilder):
    _metaclass__ = abc.ABCMeta
    # def __init__(self,action):
    #     self.__action=action;

    @abc.abstractmethod
    def start_quarter(self):
        """数据按日处理中心"""
        raise NotImplementedError