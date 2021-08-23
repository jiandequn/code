#!/bin/bash
#读取配置文件数据
for i in "$@"; do
    echo $i
    eval $i
done
if [ -z $startDate ] && [ -z $endDate ];then
        echo "没有执行时间startDate=yyyy-MM-dd  endDate=yyyy-MM-dd"
	exit 1;
fi
if [ "${flowId}" != "day" ] && [ "${flowId}" != "week" ] && [ "${flowId}" != "month" ];then
        echo "flowId不可知 需flowId=day|week|month"
	exit;
fi
if [ -z ${jobs} ];then
  echo "jobs不可知 需jobs=定义在flow中脚本"
	exit;
fi
min_start_date=$startDate
max_end_date=$endDate

e_date=`date -d "$endDate" +%s`
cur_stamp=$(((`date -d today +%s`+3600*8)/86400*86400-3600*8))
if [ $cur_stamp -lt $e_date ];then #如果结束时间超出当前时间；以当前时间为结束点
  e_date=$cur_stamp;
fi
if [ "${flowId}" = "day" ] ;then
  s_stamp=`date -d "$startDate" +%s`
  e_stamp=$[$s_stamp+86400]
  while [ $e_date -ge $e_stamp ] ; do
	  start_date=`date -d "@$s_stamp" +%Y-%m-%d`
	  end_date=`date -d "@$e_stamp" +%Y-%m-%d`
	  echo "执行day数据startDate=$start_date endDate=$end_date"
    if [ "$USER" != "hadoop" ];then
        echo `su --shell=/bin/bash hadoop -c "python gen.py flowId=${flowId} startDate=$start_date endDate=$end_date jobs=$jobs"`
    else
         echo `python gen.py flowId=${flowId} startDate=$start_date endDate=$end_date jobs=$jobs`
    fi
	  s_stamp=$[$s_stamp+86400]
  	e_stamp=$[$e_stamp+86400]
  done
elif [ "${flowId}" = "week" ] ; then
  if [ `date -d "$startDate" +%w` -ne 1 ] || [ `date -d "$endDate" +%w` -ne 1 ];then
    echo '计算周数据起始时间和结束时间应该为周一'
    exit 1;
  fi  
  s_stamp=`date -d "$startDate" +%s`
  e_stamp=$[$s_stamp+7*86400]
  while [ $e_date -ge $e_stamp ] ; do
    start_date=`date -d "@$s_stamp" +%Y-%m-%d`
    end_date=`date -d "@$e_stamp" +%Y-%m-%d`
    echo "执行week数据startDate=$start_date endDate=$end_date"
    if [ "$USER" != "hadoop" ];then
        echo `su --shell=/bin/bash hadoop -c "python gen.py flowId=${flowId} startDate=$start_date endDate=$end_date jobs=$jobs"`
    else
         echo `python gen.py flowId=${flowId} startDate=$start_date endDate=$end_date jobs=$jobs`
    fi
    s_stamp=$[$s_stamp+7*86400]
    e_stamp=$[$e_stamp+7*86400]
  done

elif [ "${flowId}" = "month" ] ; then
 if [ `date -d "$startDate" +%d` != "01" ] ||  [ `date -d "$endDate" +%d` != "01" ];then
    echo '计算月数据起始时间和结束时间应该为月份第一天'
    exit 1;
  fi 
  s_stamp=`date -d "$startDate" +%s`
  e_stamp=`date -d "$startDate +1 month" +%s`
  while [ $e_date -ge $e_stamp ] ; do
    start_date=`date -d "@$s_stamp" +%Y-%m-%d`
    end_date=`date -d "@$e_stamp" +%Y-%m-%d`
    echo "执行month数据startDate=$start_date endDate=$end_date"
    if [ "$USER" != "hadoop" ];then
        echo `su --shell=/bin/bash hadoop -c "python gen.py flowId=${flowId} startDate=$start_date endDate=$end_date jobs=$jobs"`
    else
         echo `python gen.py flowId=${flowId} startDate=$start_date endDate=$end_date jobs=$jobs`
    fi
    s_stamp=`date -d "$end_date" +%s`
    e_stamp=`date -d "$end_date +1 month" +%s`
  done
else
  echo "flowId不可知 需flowId=day|week|month"
fi
