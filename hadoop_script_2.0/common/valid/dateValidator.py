#!/usr/bin/env python
# -*- coding: utf-8 -*-
import time
import datetime

__author__ = "jiandequn"

from common.valid.validator import Validator

""""
时间验证器实现
"""
class DateValidator(Validator):
    def __init__(self, date_df="%Y-%m-%d"):
        self.date_df = date_df

    def vlidate(self, val):
        return self.is_date(val);

    def is_date(self,dateStr):
        try:
            datetime.datetime.strptime(dateStr,self.date_df)
            return True
        except:
            return False

    def formatValue(self, val):
        return datetime.datetime.strptime(val, self.date_df)
