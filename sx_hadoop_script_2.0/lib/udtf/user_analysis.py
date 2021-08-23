#!/usr/bin/env python
# -*- coding: utf-8 -*-
# 数据根式 时间戳+播放位置
#不可用于带有其他参数的数据udf格式不好用select user_id,transform(tdata) from table ;会异常；只支持全行输入
import datetime
import sys
#待解决电视剧剧集ID 同一时间点续播和点播剧集ID排序问题；
def compareLine(a, b):
    #时间戳比较
    if a[0]>b[0]: return 1
    if a[0]<b[0]: return -1
    # 同一时间点产生的日志信息，编制内容排序序号比较

    if a[-1] > b[-1]: return 1
    if a[-1] < b[-1]: return -1
    if a[-1] == 0:return 0;
    return 0;
def compareAlbumPlay(arr=[]):
    dlen = len(arr);
    if dlen<4:
        return arr;
    i=0;
    while(i<dlen-1):
        i=i+1;
        before = arr[i - 1];
        cur=arr[i]
        after = arr[i + 1];
        if before[0]!=cur[0]:continue
        if cur[1] != "COLUMN_TYPE_HISTORY_POINT" or \
            before[1] !="COLUMN_TYPE_HISTORY_POINT" or \
            after[1]!="COLUMN_TYPE_HISTORY_POINT":continue
        if cur[3].get(""):3







#编制内容排序序号；便于排序
def editContentSeq(k=[]):
    if k[1] == "AUTH_PRODUCT":
        k.append(3);
    elif k[1] == "COLUMN_TYPE_HISTORY_POINT" and k[3].has_key('operateType'):
        if k[3].get('operateType') == 'add':
            if k[3].get('timePosition') == 0:
                k.append(6)
            else: #timePosition 非0
                k.append(4)
        else:#operateType=get
            k.append(5)
    if len(k) == 4: k.append(0);
    return k;

def printMsg(a,stamp,duration):
    print "\t".join(a+[str(duration),str(stamp)])
for line in sys.stdin:
# for line in ["6029	9998240	VOD	6	18185	92839	1596800103_12,1596800119_13,1596800623_549,1596800127_23"]:
    try:
        results=[];
        d = line.strip().split('\t');
        detail = d[-1].split(","); #用户行为数据数组
        del d[-1]
        res = [];
        for det in detail:
            k = det.split(";",3); #0时间戳，1事件类型 2事件名称 3其他数据
            dic={i.split("=")[0]:i.split("=")[1] for i in k[-1].split(";")}
            dic["eventsType"]=k[1]
            dic["eTypeName"]=k[2]
            stamp=k[0];
            del k[-1];
            k.append(dic);
            #对播放事件进行排序处理
            editContentSeq(k);
            res.append(k)
        res=sorted(res, cmp=compareLine)
        #播控事件比较  a[-1]=6 timePosition=0的两个剧集ID的比较


        dlen = len(detail);
        i = 0;
        while i < dlen:
            cur = res[i];
            playPageTime = 0;
            if cur[1] == 0:  # 开始播放
                if i + 1 < dlen:
                    last = res[i + 1];
                    if last[1] > 0:  # 播放的位置返回了
                        playPageTime = last[0] - cur[0];
                        i = i + 1;
                    else:  # 表示播放没有返回，无法统计到播放时长
                        playPageTime = 0;
                else:
                    playPageTime = 0;
                printMsg(d,cur[0], playPageTime)
            else:  # 开始播放没找到为0的位置
                if i + 1 < dlen:
                    last = res[i + 1];
                    if last[1] != 0: #连续不为0的记录
                        playPageTime = last[1] - cur[1]
                        flagTime =last[0]-cur[0];
                        if flagTime>9000 :#如果同个剧集时差相差2.5个小时，作为两次观看
                            printMsg(d,cur[0], 0)
                            # printMsg(d,last[0], playPageTime)
                        else:
                            i=i+1
                            printMsg(d,cur[0], flagTime)
                    else:
                        printMsg(d,cur[0], 0)
                else:
                    printMsg(d,str(cur[0]), 0)
            i = i + 1;
    except Exception, ex:
         print ex;
         pass
