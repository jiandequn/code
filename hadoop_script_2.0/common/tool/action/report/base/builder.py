#!/usr/bin/env python
# -*- coding: utf-8 -*-
import abc
class BaseBuilder(object):
    _metaclass__ = abc.ABCMeta
    def __init__(self, action,runStep):
        self._action = action;
        self._runstep=runStep; #正在执行的步骤位置，提供执行顺序

    def _logic_validate(self,step=-1):
        if not self._runstep==step:
            # print '跳过执行当前类【%s】步骤【%d】\n' %(self.__class__,step)
            return False
        else:
            print '正在执行当前类【%s】步骤【%d】\n' %(self.__class__,step)
            return True