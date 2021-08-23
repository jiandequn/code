package com.jshy.mr.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/9/24
 * Time: 16:40
 * To change this template use File | Settings | File Templates.
 */
public class CommonSqlMapper extends
        Mapper<LongWritable, Text, Text, NullWritable> {
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String text = value.toString();
        if (StringUtils.isEmpty(text)) {
            return;
        }
        context.write(value,NullWritable.get());

    }
}
