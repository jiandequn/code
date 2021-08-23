#!/bin/bash
csvfilename=/home/app/新版本-历史点播记录.csv
dest_ip=192.168.200.141
dest_pass=asd123
dest_dir=/home/app/
dest_username=root
dest_port=22
hive -e "
        use ${hive_db};
        set hive.exec.dynamic.partition=true;
        set hive.exec.dynamic.partition.mode=nonstrict;
        set hive.vectorized.execution.enabled=false;
        set hive.groupby.orderby.position.alias=true; -- 启用别名对于groupby和orderby
        set hive.cli.print.header=true;  -- 将表头输出
        select '' as ott_productName,
        substr(user_id,-12) as mac,
        user_id as sn,
        create_time t,
        parent_column_id as cpId,
        p.column_name as cpName,
        a.album_name albumName,
        e.video_id as serviceIndex,
        v.video_name as videoName,
        e.duration as consumeTime,
        act.content_type_name as contentTypeName,
        '' as packageType
        from app_album_play_log e
        left join product_column p on p.column_id=e.parent_column_id
        left join album a on a.album_id=e.album_id
        left join album_content_type act on act.content_type=a.content_type
        left join video v on v.video_id=e.video_id
        where concat(e.y,'-',e.m,'-',e.d)>='${s_date}' and concat(e.y,'-',e.m,'-',e.d)<'${e_date}' 
    " | sed 's/[\t]/,/g'  > ${csvfilename}


expect -c "
    spawn scp -P ${dest_port} -r $csvfilename ${dest_username}@${dest_ip}:$dest_dir
    expect {
        \"*assword\" {set timeout 300; send \"$dest_pass\r\"; exp_continue;}
        \"yes/no\" {send \"yes\r\";}
    }
expect eof"


