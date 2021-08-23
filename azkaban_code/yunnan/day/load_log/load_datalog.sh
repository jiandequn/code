#!/bin/bash
source ./config.sh
#删除目录下的迁移日志
backup_path=/${hive_province}/hive/backup/
hadoop fs -rm -R -f ${backup_path}
#开始备份数据
s_stamp=$sdate
e_stamp=$edate
poll_yarn_queue
while [ $s_stamp -lt $e_stamp ]; do
	dest=${backup_path}`date -d "@$s_stamp" +%Y-%m-%d`
	echo $dest
	hadoop fs -mkdir -p $dest
	hadoop fs -cp -p ${hdfs_path}`date -d "@$s_stamp" +%Y/%m/%d`/*/* $dest
	y=`date -d "@$s_stamp" +%Y`
	m=`date -d "@$s_stamp" +%m`
	d=`date -d "@$s_stamp" +%d`
	hive --database=${hive_db} --hiveconf mapreduce.job.queuename=$yarn_queue -d hive.back.url=$dest -d year=$y -d month=$m -d day=$d -f day/load_log/loadLogs.hive
	s_stamp=$[$s_stamp+86400]
done
put_yarn_queue

