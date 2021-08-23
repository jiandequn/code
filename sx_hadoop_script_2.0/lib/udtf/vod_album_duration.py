#!/usr/bin/env python
# -*- coding: utf-8 -*-
# 数据根式 时间戳+播放位置
#不可用于带有其他参数的数据udf格式不好用select user_id,transform(tdata) from table ;会异常；只支持全行输入
import datetime
import sys
def comparePlay(a, b):
    if a[0]>b[0]: return 1
    if a[0]<b[0]: return -1
    if a[1]>b[1]: return -1
    if a[1]<b[1]: return 1
    return 0;
def printMsg(a,stamp,duration):
    print "\t".join(a+[str(duration),str(stamp)])
for line in sys.stdin:
# for line in ["6029	9998240	VOD	6	18185	92839	1596800103_12,1596800119_13,1596800623_549,1596800127_23"]:
    try:
        results=[];
        d = line.strip().split('\t');
        detail = d[-1].split(",");
        del d[-1]
        res = [];
        for det in detail:
            k = det.split("_", 1);
            res.append([int(k[0]), int(k[1])])
        res=sorted(res, cmp=comparePlay)
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
