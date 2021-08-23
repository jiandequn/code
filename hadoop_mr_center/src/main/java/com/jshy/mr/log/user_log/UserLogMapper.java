package com.jshy.mr.log.user_log;

import com.jshy.mr.common.FlumeLogWritable;
import com.jshy.mr.utils.ValidateUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * 专辑日点播数
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/9/27
 * Time: 10:05
 * To change this template use File | Settings | File Templates.
 */
public class UserLogMapper extends
        Mapper<LongWritable, FlumeLogWritable, FlumeLogWritable, NullWritable> {
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        ValidateUtils.initCleanEntity(context.getConfiguration());
        super.setup(context);
    }
    @Override
    protected void map(LongWritable key, FlumeLogWritable value, Context context) throws IOException, InterruptedException {
        if(!ValidateUtils.validate(value)) return;
        context.write(value, NullWritable.get());
    }
}
