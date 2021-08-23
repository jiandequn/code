#!/usr/bin/env python
# -*- coding: utf-8 -*-
# 数据根式 时间戳+播放位置
#不可用于带有其他参数的数据udf格式不好用select user_id,transform(tdata) from table ;会异常；只支持全行输入
import traceback

import datetime
import sys
from enum import Enum


class OperateStatus(Enum):
    TIME_POSITION_0 = 3
    GET = 2
    TIME_POSITION_LAGER_0 = 1

def comparePlay(a, b):
    if a[0]>b[0]: return 1
    if a[0]<b[0]: return -1
    if a[1]>b[1]: return -1
    if a[1]<b[1]: return 1
    return 0;
def printMsg(a,stamp,duration):
    print "\t".join(a+[str(duration),str(stamp)])

def recursion(cur,arr,idx):
    print idx
    if idx>=len(arr):return(cur[0],idx);
    idx_v = res[idx];
    state = idx_v[1];
    stamp = idx_v[0]  # 开始播放时间戳
    if state==OperateStatus.TIME_POSITION_0 or state==OperateStatus.TIME_POSITION_LAGER_0:#开始位置递归寻找
        if cur[2]==idx_v[2] and cur[3]==idx_v[3] and stamp-cur[0]<=21:
            return recursion(idx_v,arr,idx+1)
        else:return (cur[0],idx)#跳到其他内容播放了
    else:#state==OperateStatus.GET //直接结束
        return (cur[0],idx)
# for line in sys.stdin: #parentColumnId userId userType areaCode 时间戳_状态_专辑ID_剧集ID  (状态 1表示不为0的timePosition 2表示operateType=get,3表示timePosition=0)
for line in ["13183	1000016222	OTT	1001	1607901264_2_58972_0,1607901264_1_58972_190195,1607962764_2_58972_0,1607896459_3_58972_190193,1607934192_3_58972_190195,1607962764_1_58972_190206"]:
    try:
        results=[];
        d = line.strip().split('\t');
        detail = d[-1].split(",");
        del d[-1]
        res = [];
        for det in sorted(detail):
            k = det.split("_", 3);
            res.append([int(k[0]), int(k[1]),k[2],k[3]])
        # res=sorted(res, cmp=comparePlay)
        dlen = len(detail);
        i = 0;
        while i < dlen:
            cur = res[i];
            state = cur[1];
            startStamp=cur[0] #开始播放时间戳
            if state==OperateStatus.TIME_POSITION_0.value or state==OperateStatus.TIME_POSITION_LAGER_0.value:#开始位置递归寻找
                endStamp,end_index=recursion(cur,res,i+1);
                printMsg(d, startStamp, endStamp-startStamp);
                i=end_index;
            else:i=i+1;
    except Exception, ex:
        print 'str(Exception):\t', str(Exception)
        print ex;
        print 'str(Exception):\t', str(Exception)
        print 'str(e):\t\t', str(ex)
        print 'repr(e):\t', repr(ex)
        print 'e.message:\t', ex.message
        print 'traceback.print_exc():';
        traceback.print_exc()
        print 'traceback.format_exc():\n%s' % traceback.format_exc()
