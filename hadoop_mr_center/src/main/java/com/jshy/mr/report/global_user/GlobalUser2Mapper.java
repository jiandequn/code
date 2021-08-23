package com.jshy.mr.report.global_user;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * 用户停留时间周长
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/9/27
 * Time: 10:05
 * To change this template use File | Settings | File Templates.
 */
public class GlobalUser2Mapper extends
        Mapper<LongWritable, Text, GlobalUserWritable, LongWritable> {
    GlobalUserWritable writable = new GlobalUserWritable();
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String text =  value.toString();
        if(StringUtils.isEmpty(text)){
            return ;
        }
        String[] arr = text.split("\t");

        if(!StringUtils.isEmpty(arr[0])) writable.setYear(Integer.valueOf(arr[0]));
        if(!StringUtils.isEmpty(arr[1])) writable.setQuarter(Integer.valueOf(arr[1]));
        if(!StringUtils.isEmpty(arr[2])) writable.setMonth(Integer.valueOf(arr[2]));
        if(!StringUtils.isEmpty(arr[3])) writable.setWeek(Integer.valueOf(arr[3]));
        if(!StringUtils.isEmpty(arr[4])) writable.setDay(Integer.valueOf(arr[4]));
        if(!StringUtils.isEmpty(arr[5])) writable.setParentColumnId(arr[5]);
        if(!StringUtils.isEmpty(arr[6])) writable.setUserType(arr[6]);
//        if(!StringUtils.isEmpty(arr[7])) writable.setMac(arr[7]);
//        if(!StringUtils.isEmpty(arr[8])) writable.setSn(arr[8]);
//        if(!StringUtils.isEmpty(arr[9])) writable.setUserId(arr[9]);
        if(!StringUtils.isEmpty(arr[10])) writable.setCount(Long.valueOf(arr[10]));
        context.write(writable, new LongWritable(1));
    }
}
