#!/bin/bash
source /etc/profile
source ./config.sh
poll_yarn_queue
sqoop export \
-Dmapreduce.job.queuename=${yarn_queue} \
--connect "${mysql_url}?useUnicode=true&characterEncoding=utf-8" \
--username ${mysql_username} \
--password ${mysql_password} \
--export-dir /${hive_province}/hive/reports/app/retention/user/day \
--table app_retention_user_count_day \
--columns t_date,parent_column_id,user_type,${retentionField} \
--update-key t_date,parent_column_id,user_type \
--update-mode allowinsert \
--input-null-string '\\N'  \
--input-null-non-string '\\N' \
-input-fields-terminated-by '\t' \
--outdir ${sqoop_outdir}/mr_java

put_yarn_queue
