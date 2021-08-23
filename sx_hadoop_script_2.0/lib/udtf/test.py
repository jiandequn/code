#!/usr/bin/env python
# -*- coding: utf-8 -*-
# 数据根式 时间戳+播放位置
#不可用于带有其他参数的数据udf格式不好用select user_id,transform(tdata) from table ;会异常；只支持全行输入
import sys
import traceback


def isStartPlay(s):#1-首页推荐位点击播放,0-详情页点击播放,2-大屏播放,3-选集,4-续播,5-心跳
    if s == 0 or s == 1 \
            or s == 2 or s == 3 \
            or s == 4:
        return True;
    return False;
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
def printMsg(a,stamp,directPlay,duration):
    print "\t".join(a+[str(duration),str(directPlay),str(stamp)])

for line in sys.stdin:
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
        print 'traceback.format_exc():\n%s' % traceback.format_exc()



# for line in["13183 0617190020E000B8BA681AD32D 2300 35634 125970 1596318531_0,1596318537_0"]:
#         results = [];
        # d = line.strip().split('\t');
        # print d[0]
        # res = [];
        # for det in sorted(detail):
        #     k = det.split("_", 1);
        #     res.append([int(k[0]), int(k[1])])
        # # res=sorted(res, cmp=comparePlay)
        # dlen = len(detail);
        # i = 0;
        # while i < dlen:
        #     cur = res[i];
        #     directPlay = cur[1];
        #     startStamp = cur[0]  # 开始播放时间戳
        #     if isStartPlay(directPlay):
        #         endStamp, end_index = recursion(cur, res, i + 1);
        #         printMsg(d, startStamp, directPlay, endStamp - startStamp);
        #         i = end_index;
        #     else:
        #         i = i + 1;
