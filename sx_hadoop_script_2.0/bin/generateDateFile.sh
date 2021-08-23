#!/bin/bash

# sh generateDateFile.sh hadoop 20201120.log
# 该函数根据日志生成对应的hadoop日志存储目录；
# hadoop fs -copyFromLocal 2020/11/ /data2/logs/2020/11/
generateHadoopDataFile(){
  echo "生成hadoop文件"
  IFS=$'\n' # 修改默认分隔符
  OLDIFS="$IFS"
  echo $1
  if [ -z $1 ];then
    echo "请输入文件"
    return
  fi
  filename=$1
  datelist=`cat $filename|sed 's/.*createTime=\(.*\):.*:.*/\1/g'|sort|uniq`
  for line in $datelist;
  do
    date_dir=`date +%Y/%m/%d/%H -d " $line" `
    if [ ! -d "$date_dir" ]; then
          echo '创建目录：'$date_dir
          mkdir -p $date_dir
    fi
    stamp=`date -d "$line" +%s`
    g_file=$date_dir/$stamp.log
    echo "追加内容：$g_file"
    grep "createTime=$line" $filename >> $g_file
  done
}

# 根据即可info.log提取相关日志数据
# 拷贝数据到hadoop服务器：scp -r root@10.131.45.113:/data/logs/api .
# 解压log.gz包数据: gzip -d 113/api/*/info*.log.gz
# 获取符合行日志采集数据： cat {113..115}/api/*/info.log|grep 'Collection events:' |sed 's/.*\(eventsType=.*\):END/\1/g' >> 20201120.log
getApiLogFile(){
  echo "获取api日志文件"
}

func=$1
echo $1
if [ -z $func ]
then
  echo "请输入执行的函数映射[hadoop,api];hadoop表示执行生成hadoop日志文件，api执行提取info.log的日志提取"
elif [ "${func}" == "hadoop" ]
then
  generateHadoopDataFile $2
elif [ "${func}" == "api" ]
then
  getApiLogFile
else
  echo "函数映射不对；"
  echo "请输入执行的函数映射[hadoop,api];hadoop表示执行生成hadoop日志文件，api执行提取info.log的日志提取"
fi