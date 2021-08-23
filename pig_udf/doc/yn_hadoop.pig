-- 注册自定义插件
REGISTER pig_udf.jar;
-- 自定义函数来源
DEFINE PpfunsStorage  com.ppfuns.PpfunsStorage;
-- 加载hadoop日志数据  如：/data/logs/2019/11/{11,18}
dataLogs = LOAD '$input'
   USING PpfunsStorage()
   as (eventsType:chararray,mac:chararray, sn:chararray, userId:chararray, userType:chararray,
   parentColumnId:chararray,columnId:chararray,nowSpm:chararray,afterSpm:chararray,
   posName:chararray,contentId:chararray,contentType:chararray,
   operateType:chararray,keyWord:chararray,createTime:datetime);
-- 数据清洗，过滤无效数据
filter_data = FILTER dataLogs BY (not userId in ('12735840','9275505','11966583','9576436','9324688','12734575','uid','undefined')) AND parentColumnId in('38','6029') AND createTime>=$startDate AND createTime <=$endDate
-- 进行日志拆分为：detailPageLogs 详情页日志，loginPageLogs 首页日志，albumPlayLogs 专辑播放日志
SPLIT filter_data into detailPageLogs if eventsType=='operationDetails', loginPageLogs if (eventsType=='operationPage' and (nowSpm matches '^^6029.PAGE_YNGDJSJPB.0.0.*' OR nowSpm matches '^38.PAGE_YNGDJSJ.0.0.*')),albumPlayLogs if(eventsType=='operateResumePoint');
-- 存储日志数据
fs -rm -r /pig/data/
STORE detailPageLogs INTO '/pig/data/detail_page ' USING PigStorage (',');
STORE albumPlayLogs INTO '/pig/data/album_play ' USING PigStorage (',');
STORE loginPageLogs INTO '/pig/data/login_Page ' USING PigStorage (',');
-- 访问用户信息
group_user_info = GROUP filter_data by (mac,sn,userId,userType);
group_user_info2 = foreach group_user_info  Generate
   (group_user_info.mac, group_user_info.sn,group_user_info.userId,group_user_info.userType), MIN(filter_data.createTime);

-- 日用户累计停留时长  nowSpm-afterSpm之间的时间戳
filter_data2 = FILTER filter_data BY afterSpm is not null AND nowSpm is not null;
strsplit_data = FOREACH filter_data2 GENERATE (mac,sn,userId,userType), STRSPLIT (nowSpm,'.',6) as nSpm,STRSPLIT (afterSpm,'.',6) as aSpm;
-- filter_data3 = FILTER strsplit_data BY SIZE(nSpm.$5)==13 AND SIZE(aSpm.$5)==13;
user_stay_duration = FOREACH strsplit_data {
        S = FILTER nSpm BY SIZE($5)==13;
        S1 = FILTER nSpm BY SIZE($5)==13;
        GENERATE (S.$6-S1.$6) as stayDuration;
}
-- 日统计用户数
user_info = FOREACH filter_data GENERATE (mac,sn,userId,userType,GetWeek(createTime) as w,GetWeekYear(createTime) as y,GetMonth(createTime) as m,GetDay(createTime) as d;
group_user_info_day = GROUP user_info by (mac,sn,userId,userType,y,m,d);
group_user_day =  GROUP group_user_info_day by (userType,y,m,d);
group_user_num_day= FOREACH group_user_day GENERATE userType,y,m,d,COUNT($1) as userCount;
-- 周统计用户数
user_info = FOREACH filter_data GENERATE (mac,sn,userId,userType,GetWeek(createTime) as w,GetWeekYear(createTime) as y,GetMonth(createTime) as m,GetDay(createTime) as d;
group_user_info_week = GROUP user_info by (mac,sn,userId,userType,y,w);
group_user_week =  GROUP group_user_info_week by (userType,y,w);
group_user_num_week= FOREACH group_user_week GENERATE userType,y,m,d,COUNT($1) as userCount;
-- STORE processedtest INTO 'table' USING org.apache.pig.piggybank.storage.DBStorage('com.mysql.jdbc.Driver', 'jdbc:mysql://localhost/test2', 'root',  '', 'INSERT INTO test6(name1, name2, name3, name4) VALUES (?, ?, ?, ?)');
