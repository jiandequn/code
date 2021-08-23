#!/bin/sh
#用于拆分log日志数据，便于导入hadoop
fileName=$1;
echo '按时间拆分文件：' ${fileName}
tempFile=`date -d today +%s`'.log'
a=0
 awk '{print $1,substr($2,0,2)}' ${fileName}|sort|uniq|while read line
  do
   echo $line
   t=`date -d "${line}" +%s`
   ts=`date -d @${t} "+%Y/%m/%d/%H"`
   echo $ts
   echo `mkdir -p $ts`
   echo `grep "$line" $1 >>$ts/$tempFile`
   let a=a+1
  done
echo $a