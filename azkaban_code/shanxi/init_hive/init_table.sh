#!/bin/bash
#读取配置文件数据
hadoop_conf_path=conf/*.conf
for line in `cat ${hadoop_conf_path} | grep -Ev '^$|#'`
do
  eval "$line"
done
#hadoop上配置优先处理
hadoop_conf_path=/${hive_province}/conf/*.conf
hadoop fs -test -f $hadoop_conf_path
if [ $? -eq 0 ]; then
	for line in `hadoop fs -cat ${hadoop_conf_path} | grep -Ev '^$|#'`
	do
	  eval "$line"
	done
fi
set -v
hive --database=default -d hive.province=${hive_province} -d hive.database=${hive_db} -f init_hive/base.hive
hive --database=${hive_db} -d hive.province=${hive_province} -f init_hive/report.hive
set +v