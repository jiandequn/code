package com.jshy.mr.report.user_info;

import com.jshy.mr.utils.CommonUtils;
import com.jshy.mr.utils.DateUtils;
import com.jshy.mr.utils.ValidateUtils;
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
public class UserInfo2Mapper extends
        Mapper<LongWritable, Text, UserInfoWritable, Text> {
    UserInfoWritable writable = new UserInfoWritable();
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        ValidateUtils.initCleanEntity(context.getConfiguration());
        DateUtils.STATISTICAL_METHOD = CommonUtils.statisticalMethod(context.getConfiguration());
        super.setup(context);
    }
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] arr = value.toString().split("\t");
        writable.setParentColumnId(arr[0]);
        writable.setUserType(arr[1]);
        writable.setMac(arr[2]);
        writable.setSn(arr[3]);
        writable.setUserId(arr[4]);
        writable.setAreaCode(arr[5]);
        context.write(writable, new Text(CommonUtils.toStringFormat(arr[6])));
    }
}
