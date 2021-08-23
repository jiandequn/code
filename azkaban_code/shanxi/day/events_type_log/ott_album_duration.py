#!/usr/bin/env python
# -*- coding: utf-8 -*-
# 数据根式 时间戳+播放位置
#不可用于带有其他参数的数据udf格式不好用select user_id,transform(tdata) from table ;会异常；只支持全行输入
#1-首页推荐位点击播放,0-详情页点击播放,2-大屏播放,3-选集,4-续播,5-心跳
import copy
import sys
import traceback

def isStartPlay(s,isEndPlay):
    if (s == 0 and isEndPlay==0) or s == 1 \
            or s == 2 or s == 3 \
            or s == 4:
        return True;
    return False;

def printMsg(a,albumId,videoId,stamp,directPlay,duration):
    print "\t".join(a+[str(albumId),str(videoId),str(directPlay),str(duration),str(stamp)])

def repairAlbumAndVideo2(cur,arr,idx):
    c_direct_play = cur[3]
    if cur[1]>0 and cur[2]>0:
        return True
    elif cur[1]==0 and cur[2]==0:
        if c_direct_play==5 or (c_direct_play==0 and cur[4]>0):
            if idx>0:
                cur[1]=arr[idx-1][1]
                cur[2]=arr[idx-1][2]
                return True
        if idx < len(arr):
            if arr[idx + 1][1] > 0 and arr[idx + 1][2] > 0:
                cur[2] = arr[idx + 1][2]
                cur[1] = arr[idx + 1][1]
                return True
            if repairAlbumAndVideo2(arr[idx + 1], arr, idx + 1):
                cur[2] = arr[idx + 1][2]
                cur[1] = arr[idx + 1][1]
                return True;
            else:  # 不管递归成不成功，用一个填补
                cur[2] = arr[idx + 1][2]
                cur[1] = arr[idx + 1][1]
                return True;
        if idx > 0:
            if arr[idx - 1][1] > 0 and arr[idx - 1][2] > 0:
                if cur[2] == arr[idx - 1][2]:
                    cur[1] == arr[idx - 1][1]
                    return True;
    elif cur[1]==0 and cur[2]>0:
        if c_direct_play==5 or c_direct_play==4 or c_direct_play==3 or(c_direct_play==0 and cur[4]>0):
            if idx > 0:
                if arr[idx - 1][1] > 0 and arr[idx - 1][2] > 0:
                    cur[1] = arr[idx - 1][1]
                    return True;
        if idx<len(arr):
            if arr[idx+1][1]>0 and arr[idx+1][2]>0:
                if cur[2]==arr[idx+1][2]:
                    cur[1]=arr[idx+1][1];
                    return True;
                elif arr[idx+1][3] ==3 or arr[idx+1][3]==4:#表示在同一个专辑中
                    cur[1]=arr[idx+1][1];
                    return True;
                else:#跟第后一个专辑不匹配，看能不能往前找到
                    if idx>0:
                        if arr[idx-1][1]>0 and arr[idx-1][2]>0:
                            if cur[2]==arr[idx-1][2]:
                                cur[1]=arr[idx-1][1]
                                return True;
                cur[1]=arr[idx + 1][1];
                return True
            if repairAlbumAndVideo2(arr[idx + 1], arr, idx + 1):
                cur[1] = arr[idx + 1][1]
                return True;
            else:  # 不管递归成不成功，用一个填补
                cur[1] = arr[idx + 1][1]
                return True;
        if idx > 0:
            if arr[idx - 1][1] > 0 and arr[idx - 1][2] > 0:
                if cur[2] == arr[idx - 1][2]:
                    cur[1] = arr[idx - 1][1]
                    return True;
    elif cur[1]>0 and cur[2]==0:
        if c_direct_play==5:
            if idx>0:
                cur[2]=arr[idx-1][2]
                return True;
            else:
                if idx< len(arr):
                    if arr[idx+1][1]>0 and arr[idx+1][2]>0:
                        cur[2] = arr[idx + 1][2]
                        return True
                    if repairAlbumAndVideo2(arr[idx + 1], arr, idx + 1):
                        cur[2] = arr[idx + 1][2]
                        return True;
                    else:  # 不管递归成不成功，用一个填补
                        cur[2] = arr[idx + 1][2]
                        return True;
    else:
        return True;
    return False
def repairAlbumAndVideo(cur,arr,idx):
    c_direct_play=cur[3]
    if cur[1]==0 or cur[2]==0:
        if c_direct_play == 5:
            if idx>0: #往前找
                if cur[0]-arr[idx-1][0]<=21:
                    cur[1]=arr[idx-1][1]
                    cur[2]=arr[idx-1][2]
                    return True;
            #没有找到，往后找
            if idx + 1 >= len(arr): return False  # 没有可修复数据；丢弃
            else:
                if arr[idx+1][0]-cur[0]<=21 and arr[idx+1][3]==5:
                    if arr[idx + 1][1] > 0 and arr[idx + 1][2] > 0:
                        cur[1] = arr[idx + 1][1]
                        cur[2] = arr[idx + 1][2]
                        return True;
                return False;
        elif c_direct_play==3 or c_direct_play==4:
            if idx+1 >= len(arr): return False #没有可修复数据；丢弃
            else:
                if cur[2]>0:
                    if cur[2]==arr[idx+1][2]:
                        cur[1] = arr[idx + 1][1]
                        return True;
                    else: #如果下一个不等于,就跳过
                        if idx+2 >= len(arr) and arr[idx+2][2]==cur[2]:
                            cur[1] = arr[idx + 2][1]
                            return True;
                if arr[idx+1][0]-cur[0]<=21 and arr[idx+1][3]==5:
                    if arr[idx+1][1]>0 and arr[idx+1][2]>0:
                        cur[1] = arr[idx+1][1]
                        cur[2] = arr[idx+1][2]
                        return True;
                if idx>0: #如果往前走没有找到；因为是选集，专辑ID肯定存在；
                    if cur[2]>0:
                        cur[1]=arr[idx-1][1]
                        return True
    return False;

def recursion(cur,arr,idx):
    if idx>=len(arr):return(cur[0],idx);
    idx_v = res[idx];
    directPlay = idx_v[3];
    stamp = idx_v[0]  # 开始播放时间戳
    is_end_play=idx_v[4];
    repairAlbumAndVideo2(idx_v,arr,idx)
    if cur[2]==idx_v[2]: #判断albumId和videoId
        if directPlay == 0 or directPlay == 1 \
                or directPlay == 2 or directPlay == 3 \
                or directPlay == 4:  # 开始新的剧集统计
            if is_end_play > 0:
                return (idx_v[0], idx + 1)
            return (cur[0], idx)
        else:
            if stamp - cur[0] <= 10800:
                return recursion(idx_v, arr, idx + 1);
            return (cur[0], idx);
    else:
        return (cur[0],idx)

def comparePlay(a, b):  #0时间 #1专辑 #2剧集，#3播放状态directPlay #4是否有播放位移
    if a[0]>b[0]: return 1  #时间
    if a[0]<b[0]: return -1
    if a[3] > b[3]: return -1  # 时间
    if a[3] < b[3]: return 1
    return 0;
for line in sys.stdin: #parentColumnId userId areaCode 时间戳_专辑ID_剧集ID_状态_播放timePostion是否大于0  (状态 1表示不为0的timePosition 2表示operateType=get,3表示timePosition=0)
    try:
        results=[];
        d = line.strip().split('\t');
        detail = d[-1].split(",");
        del d[-1]
        res = [];
        for det in sorted(detail):
            k = det.split("_", 4);
            res.append([int(k[0]), int(k[1]),int(k[2]),int(k[3]),int(k[4])])
        res=sorted(res, cmp=comparePlay)
        # res1=copy.deepcopy(res);
        dlen = len(detail);
        i = 0;
        repairAlbumAndVideo2(res[0],res,i);
        while i < dlen:
            cur = res[i];
            startStamp = cur[0]  # 开始播放时间戳
            directPlay = cur[3];
            isEndPlay=cur[4]
            repairAlbumAndVideo2(cur, res, i)
            if (directPlay==0 and isEndPlay==0)or directPlay == 1 \
            or directPlay == 2 or directPlay == 3 \
            or directPlay == 4: #开始新的剧集统计
                endStamp, end_index = recursion(cur, res, i + 1);
            # if isStartPlay(directPlay,isEndPlay) :
            #     endStamp,end_index=recursion(cur,res,i+1);
                printMsg(d,cur[1],cur[2], startStamp,directPlay, endStamp-startStamp);
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

