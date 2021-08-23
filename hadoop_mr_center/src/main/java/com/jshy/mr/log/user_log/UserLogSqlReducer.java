package com.jshy.mr.log.user_log;

import com.jshy.mr.common.FlumeLogWritable;
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
public class UserLogSqlReducer extends Reducer<FlumeLogWritable, NullWritable, UserLogDbWritable, NullWritable>{
    @Override
    protected void reduce(FlumeLogWritable key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
        UserLogDbWritable writable = new UserLogDbWritable(key);
        context.write(writable, NullWritable.get());
    }
}


