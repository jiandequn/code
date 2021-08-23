#!/bin/bash
source /etc/profile
source ./config.sh
# 导出用户信息到mysql
exec_sqoop(){
	sqoop export \
	-Dmapreduce.job.queuename=${yarn_queue} \
	--connect "${mysql_url}?useUnicode=true&characterEncoding=utf-8" \
	--username ${mysql_username} \
	--password ${mysql_password} \
	--export-dir /${hive_province}/hive/reports/user/increase/y=${y}/m=${m}/d=${d}/ \
	--call 'insert_or_update_user' \
	--input-fields-terminated-by "\t" \
	--input-null-string '\\N'  \
	--input-null-non-string '\\N' \
	--outdir ${sqoop_outdir}/mr_java 
}
s_stamp=$sdate
e_stamp=$edate
poll_yarn_queue
while [ $s_stamp -lt $e_stamp ]; do
	y=`date -d "@$s_stamp" +%Y`
	m=`date -d "@$s_stamp" +%m`
	d=`date -d "@$s_stamp" +%d`
	exec_sqoop
	s_stamp=$[$s_stamp+86400]
done
put_yarn_queue

