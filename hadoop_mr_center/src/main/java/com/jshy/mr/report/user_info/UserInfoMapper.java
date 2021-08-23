package com.jshy.mr.report.user_info;

import com.jshy.mr.common.FlumeLogWritable;
import com.jshy.mr.entity.SpmEntity;
import com.jshy.mr.utils.CommonUtils;
import com.jshy.mr.utils.DateUtils;
import com.jshy.mr.utils.EventsTypeEnum;
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
public class UserInfoMapper extends
        Mapper<LongWritable, FlumeLogWritable, UserInfoWritable, Text> {
    UserInfoWritable writable = new UserInfoWritable();
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        ValidateUtils.initCleanEntity(context.getConfiguration());
        DateUtils.STATISTICAL_METHOD = CommonUtils.statisticalMethod(context.getConfiguration());
        super.setup(context);
    }
    @Override
    protected void map(LongWritable key, FlumeLogWritable value, Context context) throws IOException, InterruptedException {
        if(!ValidateUtils.validate(value)) return;
        if(!EventsTypeEnum.OPERATION_DETAILS.getName().equals(value.getEventsType())
                && !EventsTypeEnum.OPERATION_PAGE.getName().equals(value.getEventsType())){
                return;
        }
//        writable.setParentColumnId("6029");
        writable.setMac(value.getMac());
        writable.setSn(value.getSn());
        writable.setUserType(value.getUserType());
        writable.setUserId(value.getUserId());
        writable.setCreateTime(value.getCreateTime().substring(0,10));
        SpmEntity spmEntity = SpmEntity.initSpm(value.getAfterSpm());
        writable.setAreaCode(spmEntity.getAreaCode());
        context.write(writable, new Text(value.getCreateTime()));
    }
}
