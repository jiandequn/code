#!/usr/bin/env python
# -*- coding: utf-8 -*-
# 数据根式 时间戳+播放位置
#不可用于带有其他参数的数据udf格式不好用select user_id,transform(tdata) from table ;会异常；只支持全行输入
import traceback
import sys
# from enum import Enum
#
#
# class DirectPlayEnum(Enum):#1-首页推荐位点击播放,0-详情页点击播放,2-大屏播放,3-选集,4-续播,5-心跳
#     DETAIL_PAGE_PLAY = 0
#     HOME_PAGE_FEATURED_PLAY = 1
#     LARGE_SCREEN_PLAY = 2
#     SELECTED_EPISODES_PLAY=3
#     CONTINUE_PLAY=4
#     HEARTBEAT_PLAY=5


def isStartPlay(s):
    # if s == DirectPlayEnum.DETAIL_PAGE_PLAY.value or s == DirectPlayEnum.HOME_PAGE_FEATURED_PLAY.value \
    #         or s == DirectPlayEnum.LARGE_SCREEN_PLAY.value or s == DirectPlayEnum.SELECTED_EPISODES_PLAY.value \
    #         or s == DirectPlayEnum.CONTINUE_PLAY.value:
    if s == 0 or s == 1 \
            or s == 2 or s == 3 \
            or s == 4:
        return True;
    return False;

def comparePlay(a, b):
    if a[0]>b[0]: return 1
    if a[0]<b[0]: return -1
    if a[1]>b[1]: return -1
    if a[1]<b[1]: return 1
    return 0;
def printMsg(a,stamp,directPlay,duration):
    print "\t".join(a+[str(duration),str(directPlay),str(stamp)])

def recursion(cur,arr,idx):
    # print idx
    if idx>=len(arr):return(cur[0],idx);
    idx_v = res[idx];
    directPlay = idx_v[1];
    stamp = idx_v[0]  # 开始播放时间戳
    if isStartPlay(directPlay):
        return (cur[0],idx)
    else:
        if stamp-cur[0]<=21:
            return recursion(idx_v, arr, idx + 1);
        return (cur[0],idx);


for line in sys.stdin: #parentColumnId userId areaCode 时间戳_状态_专辑ID_剧集ID  (状态 1表示不为0的timePosition 2表示operateType=get,3表示timePosition=0)
# for line in ["13183	06170B0020F01068262A048992	1505	36650	135477	1596306290_0,1596306827_0"]:
    try:
        results=[];
        d = line.strip().split('\t');
        detail = d[-1].split(",");
        del d[-1]
        res = [];
        for det in sorted(detail):
            k = det.split("_", 1);
            res.append([int(k[0]), int(k[1])])
        # res=sorted(res, cmp=comparePlay)
        dlen = len(detail);
        i = 0;
        while i < dlen:
            cur = res[i];
            directPlay = cur[1];
            startStamp=cur[0] #开始播放时间戳
            if isStartPlay(directPlay):
                endStamp,end_index=recursion(cur,res,i+1);
                printMsg(d, startStamp,directPlay, endStamp-startStamp);
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
