#!/usr/bin/env python
# -*- coding: utf-8 -*-
# 数据根式 时间戳+播放位置
#不可用于带有其他参数的数据udf格式不好用select user_id,transform(tdata) from table ;会异常；只支持全行输入
import json

import time
import traceback
from datetime import datetime
import sys
reload(sys)
sys.setdefaultencoding('utf-8')
#待解决电视剧剧集ID 同一时间点续播和点播剧集ID排序问题；
#排序比较
def compareLine(a, b):
    #时间戳比较
    if a[0]>b[0]: return 1
    if a[0]<b[0]: return -1
    # 同一时间点产生的日志信息，编制内容排序序号比较

    if a[-1] > b[-1]: return 1
    if a[-1] < b[-1]: return -1
    if a[-1] == 0:return 0;
    return 0;
#同时间纬度内操作排序
def compareMap(a,b):
    if a['seq'] > b['seq']: return 1
    if a['seq'] < b['seq']: return -1
    return 0;
#详情内容及其播放排序
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

#打印用户点击打印日志操作的流程
def printUserAction(user,stampSort,stampMap):
    print '\n用户ID=%s,用户类型=%s,区域=%s,产品ID=%s 的操作行为流程：'%(user[0],user[1],user[2],user[3])
    #执行时间步骤
    for stamp in stampSort:
        detailCur=stampMap[stamp];
        for d in detailCur:
            c=d.copy();
            del c["eventsType"],c["eTypeName"],c['seq']
            print '\t时间：%s 事件:%s-%s\t%s' % (datetime.fromtimestamp(long(stamp)).strftime("%Y-%m-%d %H:%M:%S"),d["eventsType"],d["eTypeName"],json.dumps(c,ensure_ascii=False))

#打印用户操作输出验证结构
def printUserResult(user,stampSort,stampMap):
    print '\n用户ID=%s,用户类型=%s,区域=%s,产品ID=%s 的操作行为验证流程：' % (user[0], user[1], user[2], user[3])
    previous_nowSpm=[];#前一个nowSpm
    i=0;
    j=0;
    desc_msg=""
    out_arr=[];
    flag=True;
    while i<len(stampSort):
        if flag:j=0
        detailCur = stampMap[stampSort[i]];
        while j < len(detailCur):
            detail=detailCur[j]
            out_arr=[detail]
            if detail.has_key("nowSpm") and detail.has_key('afterSpm'):#页签操作
                nSpm=detail.get('nowSpm').split('.');
                fSpm=detail.get('afterSpm').split('.');
                if len(nSpm)<5 or len(fSpm)<5:
                    desc_msg='非正常页面内容，nowSpm|afterSpm非正常格式 \t'
                else:
                    if fSpm[4]=='0':#表示时间戳为0，由外部进入我方产品
                        desc_msg='\n外部进入产品'
                    else:#验证fSpm来源是否正常
                        if len(previous_nowSpm)==0:
                            desc_msg='该页记录的afterSpm找不到来源'
                        elif previous_nowSpm[0]==fSpm[0] and previous_nowSpm[1]==fSpm[1] \
                            and previous_nowSpm[2]==fSpm[2] and previous_nowSpm[4]==fSpm[4]:
                            if fSpm[3]!='0':#含有内容传递
                               contentId= fSpm[3]
                            desc_msg='上页的nowSpm和当前页afterSpm承接正确'
                        else:
                            desc_msg='上页的nowSpm和当前页afterSpm承接异常'
                    previous_nowSpm=nSpm;
                j=j+1;
            else:#非页签操作：
                if detail.get('eventsType')=='AUTH_PRODUCT':
                    desc_msg='鉴权'
                elif detail.get('eventsType')=='COLUMN_TYPE_HISTORY_POINT':
                    if detail.get('operateType')=='get':
                        desc_msg='获取播放记录'
                    elif detail.get('operateType')=='del':
                        desc_msg='删除播放记录'
                    else:#add当新增播放记录时，往下找当前剧集的数据是否有返回
                        if j+1<len(detailCur):#发现播放记录有还有记录时，验证该记录是否是返回的
                            lastDetail=detailCur[j+1];
                            if (lastDetail.get("eventsType")=="COLUMN_TYPE_HISTORY_POINT" and lastDetail.get('operateType')=='get') \
                                    or lastDetail.get("eventsType")=="AUTH_PRODUCT" \
                                 or lastDetail.get("eventsType")=="PAGE_ALBUM_DETAILS":
                                desc_msg='播放返回没找到播放开始记录'
                            elif lastDetail.get("eventsType")=="COLUMN_TYPE_HISTORY_POINT" and lastDetail.get('operateType')=='add' \
                                and lastDetail.get('albumId')==detail.get('albumId')  and lastDetail.get('videoId')==detail.get('videoId') \
                                    and lastDetail.get('timePosition') == detail.get('timePosition'):
                                desc_msg='重复上报点播记录'
                                out_arr.append(lastDetail);
                                j=j+1;
                            else:
                                desc_msg='无法找到记录的正常操作。。。同一时间点还有其他事件上报'
                        else:#取下一个i+1时间点的数据
                            flag=False;
                            j=0;
                            if user[1].upper() == 'OTT':  # 循环检查stampSort
                                k = i + 1
                                while k < len(stampSort):
                                    lastStamp = stampSort[i + 1]
                                    detailArr = stampMap[stampSort[k]];
                                    lastDetail = detailArr[0];
                                    if len(detailArr) > 1:  # 同一时间点产生多条记录预判应该播放结束
                                        if lastDetail.get(
                                                "eventsType") == "COLUMN_TYPE_HISTORY_POINT" and lastDetail.get(
                                            'operateType') == 'add' \
                                                and lastDetail.get('albumId') == detail.get(
                                            'albumId') and lastDetail.get(
                                            'videoId') == detail.get('videoId'):
                                            desc_msg = "播放时长%s" % (long(lastStamp) - long(stampSort[i]))
                                            out_arr.append(lastDetail);  # 下一个循环要跳过lastDetail
                                            j = j + 1;
                                        else:
                                            desc_msg = "未找到终止播放记录数据"
                                        break;
                                    else:#下个节点只有一条记录情况下
                                        if lastDetail.get("eventsType") == "COLUMN_TYPE_HISTORY_POINT" and \
                                            lastDetail.get('operateType') == 'add' and \
                                                lastDetail.get('albumId') == detail.get('albumId') and \
                                                lastDetail.get('videoId') == detail.get('videoId'):
                                            desc_msg="播放时长%s"%(long(lastStamp)-long(stampSort[i]));
                                            out_arr.append(lastDetail);
                                            k=k + 1;
                                i=k;
                            else:#VOD
                                lastStamp=stampSort[i + 1]
                                lastDetailArr = stampMap[stampSort[i+1]];
                                lastDetail = lastDetailArr[0];
                                if len(lastDetailArr)>1:#预判应该退出
                                    if lastDetail.get("eventsType") == "COLUMN_TYPE_HISTORY_POINT" and lastDetail.get(
                                            'operateType') == 'add' \
                                            and lastDetail.get('albumId') == detail.get('albumId') and lastDetail.get(
                                        'videoId') == detail.get('videoId'):
                                        desc_msg="播放时长%s"%(long(lastStamp)-long(stampSort[i]))
                                        out_arr.append(lastDetail); #下一个循环要跳过lastDetail
                                        j=j+1;
                                    else:
                                        desc_msg="未找到终止播放记录数据"
                                else:
                                    if lastDetail.get("eventsType") == "COLUMN_TYPE_HISTORY_POINT" and lastDetail.get(
                                            'operateType') == 'add' \
                                            and lastDetail.get('albumId') == detail.get('albumId') and lastDetail.get(
                                        'videoId') == detail.get('videoId'):
                                        desc_msg = '终止播放'
                                        out_arr.append(lastDetail);  # 下一个循环要跳过lastDetail
                                        j = j + 1
                                        i=i+1;

                            print desc_msg
                            print json.dumps(out_arr,ensure_ascii=False);
                            break;
            j=j+1;
            print desc_msg
            print json.dumps(out_arr,ensure_ascii=False);
        i=i+1;




# computeData 可传入行，也可以通过文件导入生成数据导入
# 如下格式：
#      hive -e "
#         use sx_hive;
#         set hive.exec.dynamic.partition=true;
#         set hive.exec.dynamic.partition.mode=nonstrict;
#         set hive.vectorized.execution.enabled=false;
#         set hive.groupby.orderby.position.alias=true; -- 启用别名对于groupby和orderby
#         set hive.cli.print.header=false;  -- 将表头输出
#       select
# /*+mapjoin(p)*/
# yl.user_id,p.user_type as user_type,collect_set(area_code)[0] as area_code,parent_column_id,concat_ws('|', collect_set(concat(unix_timestamp(yl.create_time)+28800,';',events_type,';',events_name,';',yl.data))) as tdata from source_logs yl
# inner join product_column p on p.column_id=yl.parent_column_id and p.start_date<=yl.create_time and p.end_date>yl.create_time
# left join clean_user c on c.user_id = yl.user_id and c.user_type=p.user_type
# where concat(y,'-',m,'-',d)>=date_add('2020-11-13',-1) and concat(y,'-',m,'-',d)<='2020-11-14' and create_time>concat(date_add('2020-11-13',-1),' ','21:30:00') and create_time<=concat('2020-11-14',' ','02:30:00') and c.user_id is null
# group by yl.user_id,p.user_type,parent_column_id;
#         "   > /home/app/mongo_log/111.csv
# file=open('C:\\Users\\Administrator\\Desktop\\111.csv',"r")
# for line in file.readlines():
# for line in sys.stdin:
def computeData(lines=[]):
    cout=0;
    for line in lines:
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
                elif k[1]=="AUTH_PRODUCT":dic['seq']='4';
                elif k[1]=="COLUMN_TYPE_HISTORY_POINT" and dic.has_key('operateType'):
                    if dic.get('operateType') == 'add':
                        if dic.get('timePosition') == '0':
                            dic['seq']='6'
                        else:  # timePosition 非0
                            dic['seq'] = '1'
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

            printUserAction(user=d,stampSort=stampSort,stampMap=stampMap)
            printUserResult(user=d,stampSort=stampSort,stampMap=stampMap)

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

#聚合内容，针对hadoop原始行数据进行用户聚集行为操作
def aggregate(rs):
    # 先map聚合产品
    usermap = {}
    for line in rs:
        a = line.split(";", 9);#eventsType=PAGE_ALBUM_DETAILS;eventsName=专辑详情;area=2;mac=AC4AFEA97B5B;sn=120535011053F0AC4AFEA97B5B;userId=9522816;ca=null;userType=vod;parentColumnId=113187
        userId=a[5].split("=")[1]
        userType = a[7].split("=")[1]
        areaCode = a[2].split("=")[1]
        parentColumnId = a[8].split("=")[1]
        eventsType=a[0].split("=")[1]
        eventsName = a[1].split("=")[1]
        content=a[-1];
        b=content.rpartition(";")
        createTime=b[2].split("=")[1][0:19];
        stamp=int(time.mktime(time.strptime(createTime, "%Y-%m-%d %H:%M:%S")))
        detail = "%s;%s;%s;%s"%(stamp,eventsType,eventsName,b[0])
        userKey="%s|%s|%s|%s"%(userId,userType,areaCode,parentColumnId)
        if usermap.has_key(userKey):
            userInfo=usermap.get(userKey);
            usermap[userKey]="%s|%s" %(userInfo,detail)
        else:usermap[userKey]=detail;
    userlines = []
    for k, v in usermap.items():
        ks=k.split("|")
        userlines.append("%s\t%s\t%s\t%s\t%s" %(ks[0],ks[1],ks[2],ks[3],v))
    return userlines;
    # 聚合用户信息

# 获取远程获取hadoop日志数据
def getHadoopLineData(resultLines):
    return aggregate(resultLines);

# getHadoopLineData("/data2/logs/2020/09/04/*/*")
