#!/bin/bash
source /etc/profile
source ./config.sh
#把任务给yarn指定队列执行
poll_yarn_queue
set -v    #关闭打印命令
source ./${scriptPath}
set +v  #关闭打印命令
put_yarn_queue

