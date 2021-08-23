#!/usr/bin/env python
# -*- coding: utf-8 -*-
import abc
from common.tool.action.report.base.builder import BaseBuilder
class DayBuilder(BaseBuilder):
    _metaclass__ = abc.ABCMeta
    # def __init__(self, action):
    #     self._action = action;


    @abc.abstractmethod
    def start_day(self):
        """数据按日处理中心"""
        raise NotImplementedError