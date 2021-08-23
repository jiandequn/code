package com.jshy.mr.report.global_user;

import com.jshy.mr.common.FlumeLogWritable;
import com.jshy.mr.utils.CommonUtils;
import com.jshy.mr.utils.DateUtils;
import com.jshy.mr.utils.ValidateUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
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
public class GlobalUserMapper extends
        Mapper<LongWritable, FlumeLogWritable, GlobalUserWritable, NullWritable> {
    GlobalUserWritable writable = new GlobalUserWritable();
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        ValidateUtils.initCleanEntity(context.getConfiguration());
        DateUtils.STATISTICAL_METHOD = CommonUtils.statisticalMethod(context.getConfiguration());
        super.setup(context);
    }
    @Override
    protected void map(LongWritable key, FlumeLogWritable value, Context context) throws IOException, InterruptedException {
        if(!ValidateUtils.validate(value)) return;
        writable.setParentColumnId(value.getParentColumnId());
        writable.setMac(value.getMac());
        writable.setSn(value.getSn());
        writable.setUserType(value.getUserType());
        writable.setUserId(value.getUserId());
        DateUtils.parseTimeBase(writable,value.getCreateTime());

        context.write(writable, NullWritable.get());
    }
}
