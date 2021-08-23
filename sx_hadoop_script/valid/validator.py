#!/usr/bin/env python
# -*- coding: utf-8 -*-
import abc
__author__ = "jiandequn"
""""
抽象验证器实现
"""
class Validator(object):
    _metaclass__ = abc.ABCMeta
    @abc.abstractproperty
    def vlidate(self, val):
        """
             定义当前操作表
           """
        raise NotImplementedError

    def vlidateParam(self, key, params={}):
        val = '';
        if params.has_key(key):
            val = params.get(key)
        while not self.vlidate(val):
            val = raw_input("%s:" % key);
            print val;
        return self.formatValue(val);

    @abc.abstractproperty
    def formatValue(self, val):
        """
             定义当前操作表
           """
        raise NotImplementedError
