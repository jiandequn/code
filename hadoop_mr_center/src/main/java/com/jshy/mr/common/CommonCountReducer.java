package com.jshy.mr.common;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * 通用统计计数累加
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/9/19
 * Time: 10:06
 * To change this template use File | Settings | File Templates.
 */
public class CommonCountReducer extends Reducer<Text, LongWritable, Text, LongWritable>{
    @Override
    protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
        long count = 0L;
        for(LongWritable value : values){
            count += value.get();
        }
        context.write(key, new LongWritable(count));
    }
}

