#!/usr/bin/env python
# -*- coding: utf-8 -*-
# 数据根式 时间戳+播放位置
#不可用于带有其他参数的数据udf格式不好用select user_id,transform(tdata) from table ;会异常；只支持全行输入
import time
import traceback
from datetime import datetime

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
def compareMap(a,b):
    if a['seq'] > b['seq']: return 1
    if a['seq'] < b['seq']: return -1
    return 0;
def sortContent(arr,stampMap,idx):
    if idx>=len(arr):return;
    stampCur=arr[idx];
    detailCur=stampMap.get(stampCur);
    if len(detailCur)>1:
        detailCur= sorted(detailCur,cmp=compareMap)
        #遍历detailCur详情；看看是否存在要对COLUMN_TYPE_HISTORY_POINT add 进行重排序
        for i in range(len(detailCur)-1):
            cur=detailCur[i];
            after=detailCur[i+1]
            if cur["eventsType"]=='COLUMN_TYPE_HISTORY_POINT' \
                and cur.get("operateType")=='add' \
                and cur["seq"] == after["seq"]:
                  #找前一个stamp
                    if idx>0:
                        stampBefore=arr[idx-1]
                        detailBefore=stampMap.get(stampBefore)
                        if detailBefore[-1]["eventsType"] == 'COLUMN_TYPE_HISTORY_POINT' \
                                and detailBefore[-1]["operateType"] == 'add':
                                if detailCur[i+1]["videoId"]==detailBefore[-1]["videoId"]:
                                    detailCur[i]=after;detailCur[i+1]=cur;
    stampMap[stampCur]=detailCur
    sortContent(arr,stampMap,idx + 1);
    return;





#编制内容排序序号；便于排序
def editContentSeq(before,curr=[]):
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

def printUserAnalysis(user,stampSort,stampMap):
    print '用户ID=%s,用户类型=%s,区域=%s,产品ID=%s \n'%(user[0],user[1],user[2],user[3])
    #执行时间步骤
    for stamp in stampSort:
        detailCur=stampMap[stamp];
        for d in detailCur:
            c=d.copy();
            del c["eventsType"],c["eTypeName"]
            print '时间：%s 事件:%s-%s %s' % (datetime.utcfromtimestamp(long(stamp)-28800).strftime("%Y-%m-%d %H:%M:%S"),d["eventsType"],d["eTypeName"], c)

def printUserResult(user,stampSort,stampMap):
    print '用户ID=%s,用户类型=%s,产品ID=%s \n' % (user[0], user[1], user[2])
    for stamp in stampSort:
        detailCur=stampMap[stamp];
        for d in detailCur:
            print '时间点：%s 行为:%s' % (stamp,d)
file=open('C:\\Users\\Administrator\\Desktop\\111.csv',"r")
# for line in sys.stdin:
cout=0;
for line in file.readlines():
    try:
        results=[];cout=cout+1
        d = line.strip().split('\t');
        detail = d[-1].split("|"); #用户行为数据数组
        del d[-1]
        res = [];
        stampMap={};
        for det in detail:
            k = det.split(";",3); #0时间戳，1事件类型 2事件名称 3其他数据
            dic={i.split("=")[0]:i.split("=")[1] for i in k[-1].split(";")}
            dic["eventsType"]=k[1]
            dic["eTypeName"]=k[2]
            if dic.has_key("nowSpm"):
                nowSpm=dic['nowSpm'].split(".");
                if len(nowSpm)<5:dic['seq']=str(k[0])
                else:dic['seq']=nowSpm[4]
            elif k[1]=="AUTH_PRODUCT":dic['seq']='2';
            elif k[1]=="COLUMN_TYPE_HISTORY_POINT" and dic.has_key('operateType'):
                if dic.get('operateType') == 'add':
                    if dic.get('timePosition') == '0':
                        dic['seq']='6'
                    else:  # timePosition 非0
                        dic['seq'] = '4'
                else:  # operateType=get
                    dic['seq'] = '5'
            else:
                dic['seq'] = str(k[0])
            stamp=k[0];
            del k[-1];
            if stampMap.has_key(stamp):
                stampMap.get(stamp).append(dic)
            else: stampMap[stamp]=[dic]
        #对时间进行排序
        stampSort = sorted(stampMap);
        #对时间内的操作顺序进行排序
        sortContent(stampSort,stampMap,0);

        printUserAnalysis(user=d,stampSort=stampSort,stampMap=stampMap)
        dlen = len(stampSort);

        i = 0;
        while i < 0:
            stamp=stampSort[i];
            dataArr=stampMap.get(stamp);
            if len(dataArr)==1: printMsg(d,dataArr[0], playPageTime)
            else:
                continue


        # res=sorted(res, cmp=compareLine)
        #播控事件比较  a[-1]=6 timePosition=0的两个剧集ID的比较

        dlen = len(detail);
        i = 0;
        while i < 0:
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
         print 'str(Exception):\t', str(Exception)
         print ex;
         print 'str(Exception):\t', str(Exception)
         print 'str(e):\t\t', str(ex)
         print 'repr(e):\t', repr(ex)
         print 'e.message:\t', ex.message
         print 'traceback.print_exc():';
         traceback.print_exc()
         print 'traceback.format_exc():\n%s' % traceback.format_exc()
         pass
