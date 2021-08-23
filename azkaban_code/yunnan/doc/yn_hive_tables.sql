CREATE TABLE `album`(
  `album_id` int, 
  `album_name` string, 
  `content_type` int, 
  `labels` string)
COMMENT 'mysql????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/sqoop/album'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619635438')
;
CREATE TABLE `album_content_type`(
  `content_type` int, 
  `content_type_name` string)
COMMENT '????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/sqoop/album_content_type'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619635538')
;
CREATE TABLE `album_content_type_tmp`(
  `content_type` int, 
  `content_type_name` string)
COMMENT '????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/sqoop/tmp/album_content_type'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619635338')
;
CREATE TABLE `album_label_manager`(
  `label_id` int, 
  `label_name` string)
COMMENT '????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/sqoop/album_content_type'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1602751258')
;
CREATE TABLE `album_label_manager_tmp`(
  `label_id` int, 
  `label_name` string)
COMMENT '????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/sqoop/tmp/album_label_manager'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619635307')
;
CREATE TABLE `album_play_log`(
  `parent_column_id` string COMMENT '???', 
  `user_type` string COMMENT '????', 
  `mac` string COMMENT 'MAC', 
  `sn` string COMMENT 'SN', 
  `user_id` string COMMENT '??ID', 
  `album_id` string COMMENT '??ID', 
  `album_name` string COMMENT '????', 
  `content_type` string COMMENT '????', 
  `content_type_name` string COMMENT '??????', 
  `labels` string COMMENT '????', 
  `video_id` string COMMENT '??ID', 
  `video_name` string COMMENT '????', 
  `video_duration` string COMMENT '????', 
  `time_position` string COMMENT '????', 
  `create_time` string COMMENT '????')
COMMENT '??????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/log/album_play_log'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619380000')
;
CREATE TABLE `album_tmp`(
  `album_id` int, 
  `album_name` string, 
  `content_type` int, 
  `labels` string)
COMMENT 'mysql????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/sqoop/tmp/album'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619635242')
;
CREATE TABLE `app_album_play_count_rank_day`(
  `t_date` string COMMENT '??', 
  `parent_column_id` string COMMENT '??ID', 
  `album_id` int COMMENT '??ID', 
  `album_name` string COMMENT '????', 
  `content_type` int COMMENT '????', 
  `content_type_name` string COMMENT '????', 
  `play_count` bigint COMMENT '????')
COMMENT '??????????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/reports/app/rank/play_album/play_count/day'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619638600')
;
CREATE TABLE `app_album_play_count_rank_week`(
  `y` string COMMENT '??', 
  `w` string COMMENT '?', 
  `parent_column_id` string COMMENT '??ID', 
  `album_id` int COMMENT '??ID', 
  `album_name` string COMMENT '????', 
  `content_type` int COMMENT '????', 
  `content_type_name` string COMMENT '????', 
  `play_count` bigint COMMENT '????')
COMMENT '??????????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/reports/app/rank/play_album/play_count/week'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619381248')
;
CREATE TABLE `app_album_play_log`(
  `parent_column_id` string COMMENT '??ID', 
  `user_id` string, 
  `user_type` string COMMENT '????', 
  `area_code` string COMMENT '??', 
  `album_id` string COMMENT '??', 
  `video_id` string COMMENT '??', 
  `duration` bigint COMMENT '????', 
  `create_time` string COMMENT '????')
COMMENT '?????'
PARTITIONED BY ( 
  `y` string, 
  `m` string, 
  `d` string)
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'collection.delim'=',', 
  'field.delim'='\t', 
  'mapkey.delim'=':', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/app_album_play_log'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1602751259')
;
CREATE TABLE `app_album_play_log_tmp`(
  `parent_column_id` string COMMENT '??ID', 
  `user_id` string, 
  `user_type` string COMMENT '????', 
  `area_code` string COMMENT '??', 
  `album_id` string COMMENT '??', 
  `video_id` string COMMENT '??', 
  `tdata` string COMMENT '????')
COMMENT '?????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'collection.delim'=',', 
  'field.delim'='\t', 
  'mapkey.delim'=':', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/tmp/app_album_play_log'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619635936')
;
CREATE TABLE `app_album_user_count_rank_day`(
  `t_date` string COMMENT '??', 
  `parent_column_id` string COMMENT '??ID', 
  `album_id` int COMMENT '??ID', 
  `album_name` string COMMENT '????', 
  `content_type` int COMMENT '????', 
  `content_type_name` string COMMENT '????', 
  `user_count` bigint COMMENT '???')
COMMENT '???????????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/reports/app/rank/play_album/user_count/day'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619638522')
;
CREATE TABLE `app_album_user_count_rank_week`(
  `y` string COMMENT '??', 
  `w` string COMMENT '?', 
  `parent_column_id` string COMMENT '??ID', 
  `album_id` int COMMENT '??ID', 
  `album_name` string COMMENT '????', 
  `content_type` int COMMENT '????', 
  `content_type_name` string COMMENT '????', 
  `user_count` bigint COMMENT '???')
COMMENT '???????????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/reports/app/rank/play_album/user_count/week'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619381171')
;
CREATE TABLE `app_area_visit_count_day`(
  `t_date` string COMMENT '??', 
  `parent_column_id` string COMMENT '??', 
  `user_type` string, 
  `area_code` string COMMENT '????', 
  `page_user_count` int COMMENT '?????', 
  `play_user_count` int COMMENT '?????', 
  `play_count` int COMMENT '????', 
  `duration` bigint COMMENT '????')
COMMENT '???????????????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/reports/app/area_visit_count/day'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619636312')
;
CREATE TABLE `app_area_visit_count_month`(
  `y` int COMMENT '??', 
  `m` int COMMENT '?', 
  `parent_column_id` string COMMENT '??', 
  `user_type` string, 
  `area_code` string COMMENT '????', 
  `page_user_count` int COMMENT '?????', 
  `play_user_count` int COMMENT '?????', 
  `play_count` int COMMENT '????', 
  `duration` bigint COMMENT '????')
COMMENT '???????????????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/reports/app/area_visit_count/month'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1617479106')
;
CREATE TABLE `app_area_visit_count_week`(
  `y` int COMMENT '??', 
  `w` int COMMENT '?', 
  `parent_column_id` string COMMENT '??', 
  `user_type` string, 
  `area_code` string COMMENT '????', 
  `page_user_count` int COMMENT '?????', 
  `play_user_count` int COMMENT '?????', 
  `play_count` int COMMENT '????', 
  `duration` bigint COMMENT '????')
COMMENT '???????????????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/reports/app/area_visit_count/week'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619381744')
;
CREATE TABLE `app_bookmark_rank`(
  `t_date` string COMMENT '????', 
  `parent_column_id` string COMMENT '??ID', 
  `album_id` string COMMENT '??ID', 
  `album_name` string COMMENT '??ID', 
  `content_type` string COMMENT '??ID', 
  `content_type_name` string COMMENT '??ID', 
  `count` int COMMENT '??')
COMMENT '????????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/reports/app/rank/album_bookmark/total'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619636990')
;
CREATE TABLE `app_bookmark_rank_day`(
  `t_date` string COMMENT '????', 
  `parent_column_id` string COMMENT '??ID', 
  `album_id` string COMMENT '??ID', 
  `album_name` string COMMENT '??ID', 
  `content_type` string COMMENT '??ID', 
  `content_type_name` string COMMENT '??ID', 
  `count` int COMMENT '??')
COMMENT '????????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/reports/app/rank/album_bookmark/day'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619637091')
;
CREATE TABLE `app_bookmark_rank_week`(
  `y` string COMMENT '??', 
  `w` string COMMENT '?', 
  `parent_column_id` string COMMENT '??ID', 
  `album_id` string COMMENT '??ID', 
  `album_name` string COMMENT '??ID', 
  `content_type` string COMMENT '??ID', 
  `content_type_name` string COMMENT '??ID', 
  `count` int COMMENT '??')
COMMENT '????????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/reports/app/rank/album_bookmark/week'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619382093')
;
CREATE TABLE `app_column_stay_duration`(
  `t_date` string COMMENT '??', 
  `parent_column_id` string COMMENT '??ID', 
  `before_column_id` string COMMENT '??ID', 
  `before_column_code` string COMMENT '??CODE', 
  `before_column_name` string COMMENT '??CODE', 
  `user_count` int COMMENT '???', 
  `stay_duration` bigint COMMENT '????')
COMMENT '?????????????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/reports/app/column_stay_duration/total'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619638028')
;
CREATE TABLE `app_column_stay_duration_day`(
  `t_date` string COMMENT '??', 
  `parent_column_id` string COMMENT '??ID', 
  `before_column_id` string COMMENT '??ID', 
  `before_column_code` string COMMENT '??CODE', 
  `before_column_name` string COMMENT '??CODE', 
  `user_count` int COMMENT '???', 
  `stay_duration` bigint COMMENT '????')
COMMENT '?????????????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/reports/app/column_stay_duration/day'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619638155')
;
CREATE TABLE `app_column_stay_duration_week`(
  `y` int COMMENT '??', 
  `w` int COMMENT '?', 
  `parent_column_id` string COMMENT '??ID', 
  `before_column_id` string COMMENT '??ID', 
  `before_column_code` string COMMENT '??CODE', 
  `before_column_name` string COMMENT '??CODE', 
  `user_count` int COMMENT '???', 
  `stay_duration` bigint COMMENT '????')
COMMENT '?????????????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/reports/app/column_stay_duration/week'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619382389')
;
CREATE TABLE `app_detail_page_count`(
  `t_date` string COMMENT '??', 
  `parent_column_id` string COMMENT '??ID', 
  `user_type` string COMMENT '????', 
  `user_count` int COMMENT '???', 
  `visit_count` int COMMENT '???')
COMMENT '??????????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/reports/app/detail_page/count/total'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619637612')
;
CREATE TABLE `app_detail_page_count_day`(
  `t_date` string COMMENT '??', 
  `parent_column_id` string COMMENT '??ID', 
  `user_type` string COMMENT '????', 
  `user_count` int COMMENT '???', 
  `visit_count` int COMMENT '???')
COMMENT '???????????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/reports/app/detail_page/count/day'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619637508')
;
CREATE TABLE `app_detail_page_count_week`(
  `y` int COMMENT '??', 
  `w` int COMMENT '?', 
  `parent_column_id` string COMMENT '??ID', 
  `user_type` string COMMENT '????', 
  `user_count` int COMMENT '???', 
  `visit_count` int COMMENT '???')
COMMENT '???????????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/reports/app/detail_page/count/week'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619382496')
;
CREATE TABLE `app_increase_user`(
  `parent_column_id` string, 
  `user_type` string, 
  `user_id` string, 
  `area_code` string, 
  `create_time` string COMMENT '??????')
COMMENT '??????'
PARTITIONED BY ( 
  `y` string, 
  `m` string, 
  `d` string)
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'collection.delim'=',', 
  'field.delim'='\t', 
  'mapkey.delim'=':', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/user/increase'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1602751272')
;
CREATE TABLE `app_inlet_count_day`(
  `t_date` string COMMENT '??', 
  `user_type` string COMMENT '????', 
  `parent_column_id` string COMMENT '??ID', 
  `user_count` int COMMENT '???', 
  `visit_count` bigint COMMENT '????')
COMMENT '??????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/reports/app/inlet/day'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619638348')
;
CREATE TABLE `app_inlet_count_week`(
  `y` string COMMENT '??', 
  `w` string COMMENT '?', 
  `user_type` string COMMENT '????', 
  `parent_column_id` string COMMENT '??ID', 
  `user_count` int COMMENT '???', 
  `visit_count` bigint COMMENT '????')
COMMENT '??????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/reports/app/inlet/week'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619382635')
;
CREATE TABLE `app_inlet_position_count_day`(
  `t_date` string COMMENT '??', 
  `user_type` string COMMENT '????', 
  `events_type` string COMMENT '????', 
  `parent_column_id` string COMMENT '??ID', 
  `spm` string COMMENT '??Spm', 
  `user_count` int COMMENT '???', 
  `visit_count` bigint COMMENT '????')
COMMENT '??????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/reports/app/inlet_position/day'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619638297')
;
CREATE TABLE `app_inlet_position_count_week`(
  `y` string COMMENT '??', 
  `w` string COMMENT '?', 
  `user_type` string COMMENT '????', 
  `events_type` string COMMENT '????', 
  `parent_column_id` string COMMENT '??ID', 
  `spm` string COMMENT '??Spm', 
  `user_count` int COMMENT '???', 
  `visit_count` bigint COMMENT '????')
COMMENT '??????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/reports/app/inlet_position/week'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619382582')
;
CREATE TABLE `app_retention_count_day`(
  `t_date` string COMMENT '??', 
  `parent_column_id` string COMMENT '??ID', 
  `user_type` string COMMENT '????', 
  `user_count` int COMMENT '?????')
COMMENT '?????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/reports/app/retention/user/day'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619636852')
;
CREATE TABLE `app_search_album_rank`(
  `t_date` string COMMENT '??', 
  `parent_column_id` string COMMENT '??ID', 
  `album_id` string COMMENT '??ID', 
  `album_name` string COMMENT '??ID', 
  `content_type` string COMMENT '??ID', 
  `content_type_name` string COMMENT '??ID', 
  `count` int COMMENT '???')
COMMENT '?????????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/reports/app/rank/search_album/total'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619637297')
;
CREATE TABLE `app_search_album_rank_day`(
  `t_date` string COMMENT '??', 
  `parent_column_id` string COMMENT '??ID', 
  `album_id` string COMMENT '??ID', 
  `album_name` string COMMENT '??ID', 
  `content_type` string COMMENT '??ID', 
  `content_type_name` string COMMENT '??ID', 
  `count` int COMMENT '???')
COMMENT '?????????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/reports/app/rank/search_album/day'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619637401')
;
CREATE TABLE `app_search_album_rank_week`(
  `y` string COMMENT '??', 
  `w` string COMMENT '?', 
  `parent_column_id` string COMMENT '??ID', 
  `album_id` string COMMENT '??ID', 
  `album_name` string COMMENT '??ID', 
  `content_type` string COMMENT '??ID', 
  `content_type_name` string COMMENT '??ID', 
  `count` int COMMENT '???')
COMMENT '????????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/reports/app/rank/search_album/week'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619382229')
;
CREATE TABLE `app_stay_duration`(
  `t_date` string COMMENT '??', 
  `parent_column_id` string COMMENT '??ID', 
  `user_count` int COMMENT '???', 
  `stay_duration` bigint COMMENT '????')
COMMENT '????????????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/reports/app/stay_duration/total'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619637899')
;
CREATE TABLE `app_stay_duration_day`(
  `t_date` string COMMENT '??', 
  `parent_column_id` string COMMENT '??ID', 
  `user_count` int COMMENT '???', 
  `stay_duration` bigint COMMENT '????')
COMMENT '????????????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/reports/app/stay_duration/day'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619638081')
;
CREATE TABLE `app_stay_duration_week`(
  `y` int COMMENT '??', 
  `w` int COMMENT '?', 
  `parent_column_id` string COMMENT '??ID', 
  `user_count` int COMMENT '???', 
  `stay_duration` bigint COMMENT '????')
COMMENT '????????????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/reports/app/stay_duration/week'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619382314')
;
CREATE TABLE `app_subscribe_label_count`(
  `t_date` string COMMENT '??', 
  `parent_column_id` string COMMENT '??ID', 
  `label_id` int COMMENT '??ID', 
  `label_name` string COMMENT '????', 
  `subscribe_count` int COMMENT '?????')
COMMENT '???????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/reports/app/label/subscribe_count/total'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619637734')
;
CREATE TABLE `app_user_stay_column_day`(
  `parent_column_id` string COMMENT '??ID', 
  `user_type` string COMMENT '????', 
  `user_id` string COMMENT '??ID', 
  `before_column_id` int COMMENT '????ID', 
  `before_column_code` string COMMENT '????Code', 
  `stay_duration` bigint COMMENT '????')
COMMENT '??????????'
PARTITIONED BY ( 
  `y` string COMMENT '?', 
  `m` string COMMENT '?', 
  `d` string COMMENT '?')
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/page/stay_duration'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1602751258')
;
CREATE TABLE `app_user_stay_duration_week`(
  `year` string COMMENT '??ID', 
  `week` string COMMENT 'MAC', 
  `parent_column_id` string COMMENT '??ID', 
  `user_type` string COMMENT '????', 
  `mac` string COMMENT 'MAC', 
  `sn` string COMMENT 'SN', 
  `user_id` string COMMENT '??ID', 
  `area_code` string COMMENT '??ID', 
  `stay_duration` string COMMENT '????(??)')
COMMENT '???????????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/log/app_user_stay_duration_week'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619380800')
;
CREATE TABLE `app_video_play_count_rank_day`(
  `t_date` string COMMENT '??', 
  `parent_column_id` string COMMENT '??ID', 
  `album_id` int COMMENT '??ID', 
  `album_name` string COMMENT '????', 
  `content_type` int COMMENT '????', 
  `content_type_name` string COMMENT '????', 
  `video_id` int COMMENT '??ID', 
  `video_name` string COMMENT '????', 
  `play_count` bigint COMMENT '????')
COMMENT '??????????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/reports/app/rank/play_video/play_count/day'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619638853')
;
CREATE TABLE `app_video_play_count_rank_week`(
  `y` string COMMENT '??', 
  `w` string COMMENT '?', 
  `parent_column_id` string COMMENT '??ID', 
  `album_id` int COMMENT '??ID', 
  `album_name` string COMMENT '????', 
  `content_type` int COMMENT '????', 
  `content_type_name` string COMMENT '????', 
  `video_id` int COMMENT '??ID', 
  `video_name` string COMMENT '????', 
  `play_count` bigint COMMENT '????')
COMMENT '??????????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/reports/app/rank/play_video/play_count/week'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619381504')
;
CREATE TABLE `app_video_user_count_rank_day`(
  `t_date` string COMMENT '??', 
  `parent_column_id` string COMMENT '??ID', 
  `album_id` int COMMENT '??ID', 
  `album_name` string COMMENT '????', 
  `content_type` int COMMENT '????', 
  `content_type_name` string COMMENT '????', 
  `video_id` int COMMENT '??ID', 
  `video_name` string COMMENT '????', 
  `user_count` bigint COMMENT '???')
COMMENT '???????????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/reports/app/rank/play_video/user_count/day'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619638778')
;
CREATE TABLE `app_video_user_count_rank_week`(
  `y` string COMMENT '??', 
  `w` string COMMENT '?', 
  `parent_column_id` string COMMENT '??ID', 
  `album_id` int COMMENT '??ID', 
  `album_name` string COMMENT '????', 
  `content_type` int COMMENT '????', 
  `content_type_name` string COMMENT '????', 
  `video_id` int COMMENT '??ID', 
  `video_name` string COMMENT '????', 
  `user_count` bigint COMMENT '???')
COMMENT '???????????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/reports/app/rank/play_video/user_count/week'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619381429')
;
CREATE TABLE `area_update_to_user`(
  `user_type` string, 
  `user_id` string, 
  `area_code` string)
COMMENT '????????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/reports/noapp/update_area_to_user'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1602755631')
;
CREATE TABLE `auth_product_user`(
  `user_id` string COMMENT '??ID', 
  `mac` string COMMENT 'MAC', 
  `sn` string COMMENT 'SN', 
  `user_type` string COMMENT '????', 
  `area_code` string COMMENT '??ID', 
  `third_product_code` string COMMENT '????', 
  `create_time` string COMMENT '??')
COMMENT '??????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/auth/product_user'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619380902')
;
CREATE TABLE `clean_user`(
  `id` int, 
  `user_id` string, 
  `user_type` string, 
  `is_effective` int)
COMMENT '???????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/sqoop/filter/clean_user'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619635786')
;
CREATE TABLE `detail_page_log`(
  `parent_column_id` string COMMENT '???', 
  `user_type` string COMMENT '????', 
  `mac` string COMMENT 'MAC', 
  `sn` string COMMENT 'SN', 
  `user_id` string COMMENT '??ID', 
  `column_id` string COMMENT '??ID', 
  `album_id` string COMMENT '??ID', 
  `content_type` string COMMENT '????1?? 2??', 
  `after_column_id` string COMMENT '????ID', 
  `after_column_code` string COMMENT '????code', 
  `after_column_name` string COMMENT '????Name', 
  `area_code` string COMMENT '???', 
  `create_time` string COMMENT '????', 
  `album_name` string COMMENT '????', 
  `album_content_type_name` string COMMENT '??????', 
  `labels` string COMMENT '????')
COMMENT '?????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/log/detail_page_log'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619379795')
;
CREATE TABLE `events_type_log`(
  `events_name` string COMMENT '????', 
  `area_code` string COMMENT '??', 
  `mac` string COMMENT 'mac', 
  `sn` string, 
  `user_id` string, 
  `ca` string, 
  `user_type` string COMMENT '????', 
  `parent_column_id` string COMMENT '??ID', 
  `create_time` string, 
  `param` map<string,string>)
COMMENT '?????'
PARTITIONED BY ( 
  `events_type` string, 
  `y` string, 
  `m` string, 
  `d` string)
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'collection.delim'=',', 
  'field.delim'='\t', 
  'mapkey.delim'=':', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/events_type_log'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1602751257')
;
CREATE TABLE `login_page_log`(
  `parent_column_id` string COMMENT '???', 
  `user_type` string COMMENT '????', 
  `mac` string COMMENT 'MAC', 
  `sn` string COMMENT 'SN', 
  `user_id` string COMMENT '??ID', 
  `column_id` string COMMENT '??ID', 
  `after_column_id` string COMMENT '???ID', 
  `after_column_code` string COMMENT '???Code', 
  `after_column_name` string COMMENT '????Name', 
  `area_code` string COMMENT '??code', 
  `create_time` string COMMENT '????')
COMMENT '??????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/log/login_page_log'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619379872')
;
CREATE TABLE `noapp_area_visit_count_day`(
  `t_date` string COMMENT '??', 
  `user_type` string, 
  `area_code` string COMMENT '????', 
  `page_user_count` int COMMENT '?????', 
  `play_user_count` int COMMENT '?????', 
  `play_count` int COMMENT '????', 
  `duration` bigint COMMENT '????')
COMMENT '????????????????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/reports/no_app/area_visit_count/day'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619636524')
;
CREATE TABLE `noapp_area_visit_count_month`(
  `y` int COMMENT '??', 
  `m` int COMMENT '?', 
  `user_type` string, 
  `area_code` string COMMENT '????', 
  `page_user_count` int COMMENT '?????', 
  `play_user_count` int COMMENT '?????', 
  `play_count` int COMMENT '????', 
  `duration` bigint COMMENT '????')
COMMENT '????????????????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/reports/no_app/area_visit_count/month'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1617479339')
;
CREATE TABLE `noapp_area_visit_count_week`(
  `y` int COMMENT '??', 
  `w` int COMMENT '?', 
  `user_type` string, 
  `area_code` string COMMENT '????', 
  `page_user_count` int COMMENT '?????', 
  `play_user_count` int COMMENT '?????', 
  `play_count` int COMMENT '????', 
  `duration` bigint COMMENT '????')
COMMENT '????????????????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/reports/no_app/area_visit_count/week'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619381960')
;
CREATE TABLE `product_column`(
  `column_id` int, 
  `column_name` string, 
  `user_type` string, 
  `start_date` string, 
  `end_date` string)
COMMENT '??????????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/sqoop/filter/product_column'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619635756')
;
CREATE TABLE `short_video`(
  `short_video_id` int, 
  `creator_id` int, 
  `name` string, 
  `labels` string, 
  `cp_id` int, 
  `content_type` int)
COMMENT 'mysql?????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/sqoop/short_video'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619635688')
;
CREATE TABLE `short_video_tmp`(
  `short_video_id` int, 
  `creator_id` int, 
  `name` string, 
  `labels` string, 
  `cp_id` int, 
  `content_type` int)
COMMENT 'mysql?????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/sqoop/tmp/short_video'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619635622')
;
CREATE TABLE `source_logs`(
  `events_type` string COMMENT '??', 
  `events_name` string COMMENT '????', 
  `area_code` string COMMENT '??', 
  `mac` string COMMENT 'mac', 
  `sn` string COMMENT '', 
  `user_id` string COMMENT '', 
  `ca` string COMMENT '', 
  `user_type` string COMMENT '????', 
  `parent_column_id` string COMMENT '??ID', 
  `data` string COMMENT '????', 
  `create_time` string COMMENT '????')
COMMENT '????'
PARTITIONED BY ( 
  `y` string, 
  `m` string, 
  `d` string)
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.RegexSerDe' 
WITH SERDEPROPERTIES ( 
  'input.regex'='^.*eventsType=(.*)\;eventsName=(.*)\;area=(.*)\;mac=(.*)\;sn=(.*)\;userId=(.*)\;ca=(.*)\;userType=(OTT|VOD|ott|vod)\;parentColumnId=([0-9]+)\;(.*)\;createTime=(.{19}).*$', 
  'output.format.string'='%1$s %2$s %3$s $4$s %5$s %6$s %7$s %8$s %9$s %10$s %11$s') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/hive/source_logs'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1602751256')
;
CREATE TABLE `t_column_management`(
  `column_id` int, 
  `column_code` string, 
  `column_name` string, 
  `parent_id` string)
COMMENT '????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/sqoop/column_management'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619635588')
;
CREATE TABLE `t_column_management_tmp`(
  `column_id` int, 
  `column_code` string, 
  `column_name` string, 
  `parent_id` string)
COMMENT '????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/sqoop/tmp/column_management'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619635372')
;
CREATE TABLE `user_info`(
  `parent_column_id` string, 
  `user_type` string, 
  `user_id` string, 
  `area_code` string, 
  `create_time` string)
COMMENT '????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/sqoop/user_info'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619636003')
;
CREATE TABLE `video`(
  `video_id` int, 
  `album_id` int, 
  `video_name` string, 
  `video_duration` string)
COMMENT 'mysql????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/sqoop/video'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619635489')
;
CREATE TABLE `video_tmp`(
  `video_id` int, 
  `album_id` int, 
  `video_name` string, 
  `video_duration` string)
COMMENT 'mysql????'
ROW FORMAT SERDE 
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
WITH SERDEPROPERTIES ( 
  'field.delim'='\t', 
  'serialization.format'='\t') 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  'hdfs://hadoop-cluster/yunnan/sqoop/tmp/video'
TBLPROPERTIES (
  'bucketing_version'='2', 
  'transient_lastDdlTime'='1619635276')
;
