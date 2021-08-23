#!/bin/bash
source /etc/profile
source ./config.sh
s_date=`date -d "@$sdate" +%Y-%m-%d`
e_date=`date -d "@$edate" +%Y-%m-%d`
if [ ! -f "${scriptPath}" ]; then
	echo "${scriptPath}文件不存在"
	stop_pid
fi
poll_yarn_queue
scriptPathArr=(`echo "${scriptPath}"| tr '/|.' ' '`) 
if [ "${scriptPathArr[${#scriptPathArr[@]}-1]}" == "hive" ]; then
  	hive --database=${hive_db} --hiveconf mapreduce.job.queuename=$yarn_queue -d startDate=$s_date -d endDate=$e_date -f ${scriptPath}
elif [ "${scriptPathArr[${#scriptPathArr[@]}-1]}" == "sqoop" ]; then
  	source ./${scriptPath}
else
  	source ./${scriptPath}
fi
tuichuflag=$?
put_yarn_queue
if [ $tuichuflag -ne 0 ];then
	echo -e \033[31m"脚本运行异常。。。\033[0m"
	exit 1;
fi	



