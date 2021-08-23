#!/bin/bash
#读取配置文件数据
for line in `cat conf/common.conf | grep -Ev '^$|#'`
do
  eval "$line"
done

#hadoop上配置优先处理
hadoop_conf_path=/${hive_province}/conf/date.conf
for line in `hadoop fs -cat ${hadoop_conf_path} | grep -Ev '^$|#'`
do
  eval "$line"
done

modelType=$1
startDate=${startDate}
endDate=${endDate}
isUpdateDate=${isUpdateDate}
if [ $isUpdateDate -eq 0 ];then
	echo "第一次无需修改，更新date.conf为isUpdateDate=1"
	isUpdateDate=1
else
	if [ -z "$modelType" ] || [ "$modelType" == "day" ];then
		startDate=$endDate
		endDate=`date -d "${endDate} +1 days" +%Y-%m-%d`
	elif [ "$modelType" == "week" ]; then
		startDate=$endDate
		endDate=`date -d "${startDate} +7 days" +%Y-%m-%d`
	elif [ "$modelType" == "month" ]; then
		startDate=$endDate
		endDate=`date -d "${startDate} +1 months" +%Y-%m-%d`
	elif  "$modelType" == "quarter" ]; then
		startDate=$endDate
		endDate=`date -d "${startDate} +3 months" +%Y-%m-%d`
	fi	
fi
mkdir -p tmp
echo "" > tmp/date.conf
echo "startDate=${startDate}" >> tmp/date.conf
echo "endDate=${endDate}" >> tmp/date.conf
echo "isUpdateDate=1" >> tmp/date.conf
echo "" >> tmp/date.conf
hadoop fs -copyFromLocal -f tmp/date.conf /${hive_province}/conf/
for line in `hadoop fs -cat ${hadoop_conf_path}`
do
  echo "$line"
done
#重置队列文件
dateType=$modelType
#配置yarn队列
yarn_queues=default,hive
#如果yarn_queue_weight为空，表示随机获取
yarn_queue_weight=1
source ./tool/queue.sh
init_yarn_queue