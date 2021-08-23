#!/bin/bash
. /etc/profile
#安装bin目录
SCRIPT_HOME_BIN=$(dirname $(readlink -f $0))
#执行文件
PYTHONFILE='start.py'

#获取PID
pid=`ps -ef|grep $PYTHONFILE|grep -v grep|awk '{print $2}'`

#PID不存在即没有启动
if [ -z $pid ];then
   echo "文件:${PYTHONFILE},未执行过！"
else
  #获取应用的PID，停掉进程
  echo "准备停掉[进程ID=${pid}]"
  kill $pid
fi
