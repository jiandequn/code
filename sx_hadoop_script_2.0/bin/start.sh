#!/bin/sh
. /etc/profile
#安装bin目录
SCRIPT_HOME_BIN=$(dirname $(readlink -f $0))
#执行文件
PYTHONFILE='start.py'
#linux nohup日志文件
LOG=/home/app/logs/script_python_logs
#进入HOME目录
cd ${SCRIPT_HOME_BIN}/..
#获取PID
pid=`ps -ef|grep $PYTHONFILE|grep -v grep|awk '{print $2}'`
#PID不存在即没有启动
if [ -z $pid ];then
   echo "准备启动执行文件:${PYTHONFILE}"
   echo `su --shell=/bin/bash hadoop -c "cd ${SCRIPT_HOME_BIN}/..;/usr/bin/nohup python ${PYTHONFILE} runMode=$1 >/dev/null 2>${LOG}/error.log &"`
   pid=`ps -ef|grep $PYTHONFILE|grep -v grep|awk '{print $2}'`
   echo "启动进程ID=[${pid}]"
else
  #获取对应应用的PID
   echo "执行文件已经启动[进程ID=${pid}]"
fi
