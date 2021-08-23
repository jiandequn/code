#!/bin/sh
#安装目录
HOME=`pwd`
#执行文件
PYTHONFILE='start.py'
#linux nohup日志文件
LOG=${HOME}/log/
#进入HOME目录
cd ${HOME}/..
#获取PID
pid=`ps -ef|grep $PYTHONFILE|grep -v grep|awk '{print $2}'`
#PID不存在即没有启动
if [ -z $pid ];then
   echo "准备启动执行文件:${PYTHONFILE}"
else
  #获取应用的PID，停掉进程
  echo "准备停掉[进程ID=${pid}]"
  kill $pid
fi
/usr/bin/nohup python ${PYTHONFILE} runMode=day >/dev/null 2>${LOG}/error.log &

