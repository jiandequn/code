#!/bin/bash
mysql_arr=(${mysql_url//// })
echo "${mysql_arr[*]}"
mysqlDb=${mysql_arr[2]}
mysql_arr2=(${mysql_arr[1]//:/ })
mysqlPort=${mysql_arr2[-1]}
mysqlUrl=${mysql_arr2[0]}
python week/other/renameLogTable.py startDate=${startDate} endDate=${endDate} mysqlName=${mysql_username} mysqlPwd=${mysql_password} mysqlUrl=${mysqlUrl} mysqlDb=${mysqlDb} mysqlPort=${mysqlPort}
if [ $? -ne 0 ]; then
    exit 1
fi