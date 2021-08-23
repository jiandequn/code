#!/usr/bin/env python
# -*- coding: utf-8 -*-
# 数据根式 时间戳+播放位置
# 不可用于带有其他参数的数据udf格式不好用select user_id,transform(tdata) from table ;会异常；只支持全行输入
import sys

def getValue(dic, st):
    return dic.pop(st, '');

for line in sys.stdin:
    # for line in ["2020-01-01 06:59:55	eventsType=auth_product;mac=AC4AFE6D0A28;sn=12053500305080AC4AFE6D0A28;userId=10664624;userType=1;productId=PT20190731100641096;isEffective=1;resourceId=;operateType=auth_product"]:
    arr = line.strip().split('\t');
    createTime = arr[0];
    dic = {i.split("=")[0]: i.split("=")[1] for i in arr[-1].split(";")};
    eventsType = getValue(dic, 'eventsType');
    mac = getValue(dic, 'mac');
    sn = getValue(dic, 'sn');
    userId = getValue(dic, 'userId');
    userType = getValue(dic, 'userType');
    areaCode = getValue(dic, 'areaCode');
    parentColumnId = getValue(dic, 'parentColumnId');
    ca = getValue(dic, 'ca');
    eventsName = getValue(dic, 'eventsName');
    # events_name,area_code,mac,sn,user_id,ca,user_type,parent_column_id,create_time,t_data,events_type
    dataArr = [];
    for k in dic.keys():
        dataArr.append('%s=%s' % (k, dic[k]))
    print "\t".join(
        [eventsName, areaCode, mac,sn, userId, ca, userType, parentColumnId, createTime, ';'.join(dataArr), eventsType])
