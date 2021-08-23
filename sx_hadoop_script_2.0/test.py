# -*- coding: utf-8 -*-
import time

import datetime

# from common.tool.action.factory import ReportFactory
#
# startDate=datetime.datetime.strptime('2020-07-27', '%Y-%m-%d')
# endDate=datetime.datetime.strptime('2020-10-26', '%Y-%m-%d')
# while(startDate<endDate):
#     eDate=startDate + datetime.timedelta(days=7);
#     print startDate,eDate
#     ReportFactory(startDate, eDate, runMode="day").creator().start(step=[1,2])
#     startDate=eDate
#
# #
from common.tool import LinuxShell
cmdShell = LinuxShell.SSHCommand(section="SSH");
rs = cmdShell.cmdAndResult("hadoop fs -cat /data2/logs/2020/09/04/00/*")
cmdShell.close()
def reggreta(rs):
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
        detail = "%s;%s;%s%s"%(stamp,eventsType,eventsName,b[0])
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

for line in reggreta(rs):
    print line
print '条目：%d'%len(rs)