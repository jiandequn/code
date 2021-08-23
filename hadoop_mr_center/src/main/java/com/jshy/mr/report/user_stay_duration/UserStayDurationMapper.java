package com.jshy.mr.report.user_stay_duration;

import com.jshy.mr.common.FlumeLogWritable;
import com.jshy.mr.entity.ParentColumnEntity;
import com.jshy.mr.entity.SpmEntity;
import com.jshy.mr.utils.CommonUtils;
import com.jshy.mr.utils.DateUtils;
import com.jshy.mr.utils.ValidateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.io.LongWritable;
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
public class UserStayDurationMapper extends
        Mapper<LongWritable, FlumeLogWritable, UserStayDurationWritable, LongWritable> {
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        ValidateUtils.initCleanEntity(context.getConfiguration());
        DateUtils.STATISTICAL_METHOD = CommonUtils.statisticalMethod(context.getConfiguration());
        super.setup(context);
    }
    @Override
    protected void map(LongWritable key, FlumeLogWritable value, Context context) throws IOException, InterruptedException {
        if(!ValidateUtils.validate(value)) return;
        if(StringUtils.isEmpty(value.getAfterSpm())||StringUtils.isEmpty(value.getNowSpm()))return;
        SpmEntity nowSpm = SpmEntity.initSpm(value.getNowSpm());
        if(StringUtils.isEmpty(nowSpm.getTimeStamp()) || "0".equals(nowSpm.getTimeStamp())){
            return;
        }
        SpmEntity afterSpm = SpmEntity.initSpm(value.getAfterSpm());
        if(StringUtils.isEmpty(afterSpm.getTimeStamp()) || "0".equals(afterSpm.getTimeStamp())){
            return;
        }
        if(nowSpm.getTimeStamp().length() != 13 || afterSpm.getTimeStamp().length() != 13) {
                return;
        }
        //计算上一级页面停留时间 如果停留时间在微妙之间，则去掉
        long stayTime = Long.valueOf(nowSpm.getTimeStamp())-Long.valueOf(afterSpm.getTimeStamp());
        if(stayTime<0) return;
        UserStayDurationWritable writable = new UserStayDurationWritable();
        writable.setParentColumnId(value.getParentColumnId());
        writable.setMac(value.getMac());
        writable.setSn(value.getSn());
        writable.setUserType(value.getUserType());
        writable.setUserId(value.getUserId());
        DateUtils.parseTimeBase(writable,value.getCreateTime());
        context.write(writable, new LongWritable(stayTime));
    }

    /**
     * 非首页
     * 样式： 38.PAGE_YNGDJSJ.0.0
     * @param nowSpm
     * @return
     */
    private boolean noFirstPage(String nowSpm){
        if(StringUtils.isEmpty(nowSpm)) return false;
        for(ParentColumnEntity entity : ValidateUtils.cleanEntity.getParentColumnList()){
            StringBuilder sb = new StringBuilder(entity.getColumnId());
            sb.append(".").append("PAGE_").append(entity.getColumnCode()).append(".0.0");
            if(nowSpm.contains(sb)){
                return true;
            }
        }
        return false;
    }
}
