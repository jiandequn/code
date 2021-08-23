#!/bin/bash
#读取配置文件数据
hadoop_conf_path=conf/*.conf
for line in `cat ${hadoop_conf_path} | grep -Ev '^$|#'`
do
  eval "$line"
done
s_date=`date -d "@$sdate" +%Y-%m-%d`
e_date=`date -d "@$edate" +%Y-%m-%d`
set -v
source ./backup_content_from_mysql/album/import_video_to_init.sqoop
hive --database=${hive_db} --hiveconf mapreduce.job.queuename=$yarn_queue -d startDate=$s_date -d endDate=$e_date -f backup_content_from_mysql/backup.hive
set +v