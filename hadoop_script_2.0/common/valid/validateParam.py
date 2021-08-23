#!/usr/bin/env python
# -*- coding: utf-8 -*-
def convertParam(argArr=[]):
    params = {};
    for arg in argArr:
        arr =  str.split(arg, "=");
        if len(arr)==2:
            params[arr[0]]=arr[1];
    return  params;