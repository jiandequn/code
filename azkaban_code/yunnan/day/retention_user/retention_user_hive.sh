#!/bin/bash
source /etc/profile
source ./config.sh
retention_day_num=2
retention_hive_file="RetentionByNumDay"
retention_hive_field="user_2day_count"
if [ -n "$retentionDayNum" ];then
	retention_day_num=$retentionDayNum
fi
if [ $retention_day_num -eq 3 ];then
    retention_hive_field="user_3day_count"
elif [ $retention_day_num -eq 2 ] && [ "$retentionType" == "add" ];then
    retention_hive_field="add_user_2day_count"
    retention_hive_file="AddUserRetentionByNumDay"
fi

function export_sqoop(){
    sqoop export \
    -Dmapreduce.job.queuename=${yarn_queue} \
    --connect "${mysql_url}?useUnicode=true&characterEncoding=utf-8" \
    --username ${mysql_username} \
    --password ${mysql_password} \
    --export-dir /${hive_province}/hive/reports/app/retention/user/day \
    --table app_retention_user_count_day \
    --columns t_date,parent_column_id,user_type,${retention_hive_field} \
    --update-key t_date,parent_column_id,user_type \
    --update-mode allowinsert \
    --input-null-string '\\N'  \
    --input-null-non-string '\\N' \
    -input-fields-terminated-by '\t' \
    --outdir ${sqoop_outdir}/mr_java
}


s_stamp=$[$sdate-($retention_day_num -1)*86400]
e_stamp=$sdate
poll_yarn_queue
while [ $e_stamp -lt $edate ]; do
	start_date=`date -d "@$s_stamp" +%Y-%m-%d`
	end_date=`date -d "@$e_stamp" +%Y-%m-%d`
	echo "处理留存信息:时间 $start_date ~$end_date；留存天数:$retention_day_num;留存类型：$retention_hive_field"
	#清除表记录
	hive -e "truncate table ${hive_db}.app_retention_count_day;"
	#计算留存
	hive --database=${hive_db} --hiveconf mapreduce.job.queuename=$yarn_queue -d startDate=$start_date -d endDate=$end_date -d dayNum=$retention_day_num -f day/retention_user/${retention_hive_file}.hive
	#导出到sqoop
    export_sqoop
	s_stamp=$[$s_stamp+86400]
	e_stamp=$[$e_stamp+86400]
done
put_yarn_queue

