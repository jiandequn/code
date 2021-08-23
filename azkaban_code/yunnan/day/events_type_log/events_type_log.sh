#!/bin/bash
source ./config.sh

s_date=`date -d "@$sdate" +%Y-%m-%d`
e_date=`date -d "@$edate" +%Y-%m-%d`
poll_yarn_queue
hive --database=${hive_db} --hiveconf mapreduce.job.queuename=$yarn_queue -d startDate=$s_date -d endDate=$e_date -f $dateType/events_type_log/eventsType.hive
put_yarn_queue