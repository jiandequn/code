#!/bin/bash
source /etc/profile
source ./config.sh
s_date=`date -d "@$sdate" +%Y-%m-%d`
e_date=`date -d "@$edate" +%Y-%m-%d`
set -v
  source ./week/other/album_play_csv.sh
set +v


