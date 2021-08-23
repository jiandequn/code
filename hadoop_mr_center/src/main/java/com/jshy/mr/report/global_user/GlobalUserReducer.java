package com.jshy.mr.report.global_user;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * 统计用户播放时长
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/9/19
 * Time: 10:06
 * To change this template use File | Settings | File Templates.
 */
public class GlobalUserReducer extends Reducer<GlobalUserWritable, NullWritable, GlobalUserWritable, NullWritable>{

    @Override
    protected void reduce(GlobalUserWritable key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
        context.write(key,NullWritable.get());
    }
}


