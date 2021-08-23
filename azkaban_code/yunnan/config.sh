#!/bin/bash
#读取配置文件数据

for line in `cat conf/*.conf | grep -Ev '^$|#'`
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

#脚本传参方式
for i in "$@"; do
	echo $i
    eval $i
done


script_pid=$$
sdate=
edate=
currentStamp=$(((`date -d today +%s`+3600*8)/86400*86400-3600*8))
auto_date=0
function stop_pid(){
	echo "异常，退出执行shell脚本"
	exit 1
	# sleep 1 && kill $script_pid
}
function getweek(){
	if [ -z $startDate ] && [ -z $endDate ];then
	  	whichday=$((`date -d today +%w`-1))
		edate=`date -d "today -$whichday days" +%s`
		sdate=`date -d "today -$((whichday+7)) days" +%s`
	else
	   	sdate=`date -d "$startDate" +%s`
 		edate=`date -d "$endDate" +%s` 
 		if [ $((($edate-$sdate)%7)) != 0 ];then
 			echo "不在周时间范围内$startDate和$endDate"
			stop_pid
		elif [ $edate -gt $currentStamp ] || [ $sdate -ge $currentStamp ]; then
			echo "开始时间$startDate和$endDate不满足周的要求，要求时间小于等于当前时间"
			stop_pid
		elif [ `date -d "@$sdate" +%w` != 1 ] || [ `date -d "@$sdate" +%w` != 1 ]; then
			echo "开始时间$startDate和$endDate不满足周的要求，要求开始时间和结束时间都为周一"
			stop_pid
		fi
	fi
}
function getyear(){
	if [ -z $startDate ] && [ -z $endDate ];then
	  	syear=`date -d today +%Y`
		sdate=`date -d "$((syear-1))-01-01" +%s`
		edate=`date -d "$syear-01-01" +%s`
	else
	   	sdate=`date -d "$startDate" +%s`
 		edate=`date -d "$endDate" +%s` 
 		if [ `date -d "$startDate" +%m%d` != "0101" ] || [ `date -d "$endDate" +%m%d` != "0101" ];then
			echo "时间${startDate}和${endDate}不满足年的要求，要求年时间范围为 上个年的第一天到这个年的第一天；并且为****-01-01的整数倍"
			stop_pid
		elif [ $edate -gt $currentStamp ] || [ $sdate -ge $currentStamp ]; then
			echo "开始时间${startDate}和${endDate}不满足年的要求，要求时间小于等于当前时间"
			stop_pid
		fi
	fi
}
function getmonth(){
	if [ -z $startDate ] && [ -z $endDate ];then
	  	currentM=`date -d today +%Y-%m`
		edate=`date -d "$currentM-01" +%s`
		sdate=`date -d "$currentM-01 last month" +%s`
	else
	   	sdate=`date -d "$startDate" +%s`
 		edate=`date -d "$endDate" +%s` 
 		if [ `date -d "$startDate" +%d` != "01" ] ||  [ `date -d "$endDate" +%d` != "01" ];then
			echo "时间${startDate}和${endDate}不满足月的要求，要求月时间范围为 上个月的第一天到这个月的第一天；并且为****-**-01的整数倍"
			stop_pid
		elif [ $edate -gt $currentStamp ] || [ $sdate -ge $currentStamp ]; then
			echo "开始时间${startDate}和${endDate}不满足月的要求，要求时间小于等于当前时间"
			stop_pid
		fi
	fi
}

function getday(){
	if [ -z $startDate ] && [ -z $endDate ];then
		edate=$currentStamp
		sdate=$[$edate-1*86400]
	else
	   	sdate=`date -d "$startDate" +%s` 
 		edate=`date -d "$endDate" +%s` 
 		if [ $edate -gt $currentStamp ] || [ $sdate -ge $currentStamp ]; then
			echo "开始时间${startDate}和${endDate}不满足日的要求，要求时间小于等于当前时间"
			stop_pid
		fi
	fi
}
function getquarter(){
	if [ -z $startDate ] && [ -z $endDate ];then
		c_quarter=$(((`date -d today +%m`-1)/3))
		c_year=`date -d today +%Y`
		if [ $c_quarter -eq 0 ];then  #上一年的最后一个季度
			sdate=`date -d "$((c_year-1))-10-01" +%s`
			edate=`date -d "$c_year-01-01" +%s`
		elif [ $c_quarter -gt 0 ]; then
			sdate=`date -d "$c_year-$((c_quarter*3+1))-01" +%s`
			edate=`date -d "$c_year-$((c_quarter*3+4))-01" +%s`
		else
			echo "无法计算出季度"
			stop_pid	
		fi	
	else
	   	sdate=`date -d "$startDate" +%s` 
 		edate=`date -d "$endDate" +%s` 
 		if [ $edate -gt $currentStamp ] || [ $sdate -ge $currentStamp ]; then
			echo "开始时间${startDate}和${endDate}不满足季度的要求，要求时间小于等于当前时间"
			stop_pid
		elif [ $(((`date -d $startDate +%m`-1)%3)) != 0 ] || [ $(((`date -d $endDate +%m`-1)%3)) != 0 ] || [ `date -d "$startDate" +%d` != "01" ] ||  [ `date -d "$endDate" +%d` != "01" ]; then
			echo "开始时间${startDate}和${endDate}不满足季度的要求，要求时间为季度起始和结束时间，一个季度为3个月；如第1季度(xxxx-01-01~xxxx-04-01);第2季度(xxxx-04-01~xxxx-07-01),第3季度(xxxx-07-01~xxxx-10-01);第4季度(xxxx-10-01~xxxy-01-01)"
			stop_pid
		fi
	fi
}

if [ -z $startDate ] && [ -z $endDate ];then
  echo "启动自动计算时间"
  auto_date=1
elif [ -z "$startDate" ]  || [ -z "$endDate" ];then
	echo '开始时间和结束时间参数不能一个为空'
	stop_pid
elif [ -z `echo $startDate | grep -q '^[0-9]\{4\}-[0-9]\{2\}-[0-9]\{2\}$' && date -d  $startDate +%Y-%m-%d` ] || [ -z `echo $endDate | grep -q '^[0-9]\{4\}-[0-9]\{2\}-[0-9]\{2\}$' && date -d  $endDate +%Y-%m-%d` ]; then
	echo "开始时间或者结束时间不满足格式要求YYYY-mm-dd;${startDate},${endDate}"	
	stop_pid
elif [[ `date -d "$startDate" +%s` -ge `date -d "$endDate" +%s` ]];then
	echo "开始时间不能大于等于结束时间${startDate},${endDate}"
	stop_pid
fi
execfile=$0
execfileArr=(`echo "${execfile}"| tr '/|.' ' '`)   
dateType=
tFlag=`echo $0 | grep -oE 'tool/hive.sh|tool/sqoop.sh|tool/exec.sh'`
if [ -n "$tFlag" ]; then
	dateTypeArr=(`echo "${scriptPath}"| tr '/|.' ' '`)
	dateType=${dateTypeArr[0]}	
else
	dateType=${execfileArr[0]}	
fi

if [ "${dateType}" = "day" ];then
	echo "按天统计"
	getday
elif [ "${dateType}" = "week"  ]; then
	echo "按周统计"
	getweek
elif [[ "${dateType}" = "month"  ]]; then
	echo "按月统计"
	getmonth
elif [[ "${dateType}" = "year"  ]]; then
	echo "按年统计"
	getyear
elif [[ "${dateType}" = "quarter"  ]]; then
	echo "按季度统计"
	getquarter
else
    echo "按其他统计"
	getday
fi
startDate=`date -d "@$sdate" +%Y-%m-%d`
endDate=`date -d "@$edate" +%Y-%m-%d`
echo "按${dateType}统计,时间范围${startDate}~${endDate},时间戳范围${sdate}~${edate}"

#对任务job进行yarn队列出队和入队
source ./tool/queue.sh
