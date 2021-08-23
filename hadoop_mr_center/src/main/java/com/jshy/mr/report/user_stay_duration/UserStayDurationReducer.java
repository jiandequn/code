package com.jshy.mr.report.user_stay_duration;

import org.apache.hadoop.io.LongWritable;
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
public class UserStayDurationReducer extends Reducer<UserStayDurationWritable, LongWritable, UserStayDurationWritable, LongWritable>{

    @Override
    protected void reduce(UserStayDurationWritable key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
        Long tatolStamp = 0L;
        Iterator<LongWritable> vs =values.iterator();
        while(vs.hasNext()){
            tatolStamp += vs.next().get();
        }
        context.write(key,new LongWritable(tatolStamp));
    }
}


