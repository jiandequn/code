#!/bin/bash

#执行2日留存
echo `./day/noapp_retention_user/retention_user_hive.sh retentionDayNum=2  startDate=2021-01-01 endDate=2021-01-05`
source `./day/noapp_retention_user/retention_user_hive.sh retentionDayNum=3  startDate=2020-08-03 endDate=2020-08-05`