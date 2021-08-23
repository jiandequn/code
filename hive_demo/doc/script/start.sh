#/bin/bash
# hive -f createLogs.hive
EXPORT_START_DATE=$1
EXPORT_END_DATE=$2
i=$EXPORT_START_DATE
while [[ $i < `date -d "+1 day $EXPORT_END_DATE" +%Y-%m-%d` ]]
  do
    echo $i
    i=`date -d "+1 day $i" +%Y-%m-%d`
done

HADOOP_LOG_PATH=/data/logs
# 遍历时间去执行数据加载 ,要备份好日志数据,load过程,会把数据mv到hive表目录下
while [[ $i < `date -d "+1 day $EXPORT_END_DATE" +%Y%m%d` ]]
  do
    YEAR=`date -d "+1 day $i" +%Y`
    MONTH=`date -d "+1 day $i" +%m`
    DAY=`date -d "+1 day $i" +%d`
    echo $YEAR,$MONTH,$DAY
    hive -d hadoop.log.path=$HADOOP_LOG_PATH/$YEAR/$MONTH/$DAY/*/* -d year=$YEAR -d month=$MONTH -d day=$DAY -S -f loadData.hive
    i=`date -d "+1 day $i" +%Y%m%d`
done
 #开始加载数据 hive -d hadoop.log.path=/data/logs/2020/03/26/{00..23}/* -d year=2020 -d month=03 -d day=26 -f loadData.hive
