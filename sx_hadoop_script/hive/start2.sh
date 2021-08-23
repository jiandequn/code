#!/bin/bash
#开始时间
EXPORT_START_DATE=$1
#结束时间
EXPORT_END_DATE=$2
i=$EXPORT_START_DATE
#hadoop的dfs path
HADOOP_HDFS_URL=hdfs://192.168.15.21:9000
#日志路径
HADOOP_LOG_PATH=/data/logs
#根据时间执行数据加载
while [[ $i < `date -d "+1 day $EXPORT_END_DATE" +%Y/%m/%d` ]]
  do
    YEAR=`date -d "+0 day $i" +%Y`
    MONTH=`date -d "+0 day $i" +%m`
    DAY=`date -d "+0 day $i" +%d`
    echo $YEAR,$MONTH,$DAY,$i
    hive -d hadoop.log.dir=$HADOOP_LOG_PATH/$YEAR/$MONTH/$DAY/*/*  -d hadoop.hdfs.url=$HADOOP_HDFS_URL -d year=$YEAR -d month=$MONTH -d day=$DAY -S -f loadLogs.hive
    i=`date -d "+1 day $i" +%Y/%m/%d`
    if [ $i = `date -d "+1 day $EXPORT_END_DATE" +%Y/%m/%d` ];then   #删除这个目录时间点的数据
        hadoop fs -rm -f -R  $HADOOP_HDFS_URL$HADOOP_LOG_PATH/$YEAR/$MONTH/$DAY/*
    fi
done

#根据时间按分类进行
hive -d startDate=$1 -d endDate=$2 -f eventsType.hive

#hive -d startDate=$1 -d endDate=$2 -f report/addUserByDay.hive