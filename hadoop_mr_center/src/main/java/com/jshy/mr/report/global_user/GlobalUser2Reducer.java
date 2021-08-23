package com.jshy.mr.report.global_user;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

/**
 * 统计用户播放时长
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/9/19
 * Time: 10:06
 * To change this template use File | Settings | File Templates.
 */
public class GlobalUser2Reducer extends Reducer<GlobalUserWritable, LongWritable, GlobalUserWritable, NullWritable>{
    private int statisticalMethodEnumId;
    protected void setup(Reducer<GlobalUserWritable, LongWritable, GlobalUserWritable, NullWritable>.Context context) throws IOException, InterruptedException {

    }

    @Override
    protected void reduce(GlobalUserWritable key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
        Iterator<LongWritable> it = values.iterator();
        Long total = 0L;
        while (it.hasNext()){
            total += it.next().get();
        }
        key.setCount(total);
        context.write(key,NullWritable.get());
    }
}


