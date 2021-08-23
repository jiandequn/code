package com.jshy.mr.log.first_page;

import com.jshy.mr.common.FlumeLogWritable;
import com.jshy.mr.entity.ParentColumnEntity;
import com.jshy.mr.entity.SpmEntity;
import com.jshy.mr.utils.EventsTypeEnum;
import com.jshy.mr.utils.ValidateUtils;
import org.apache.commons.lang3.StringUtils;
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
public class FirstPageLogMapper extends
        Mapper<LongWritable, FlumeLogWritable, FirstPageLogWritable, NullWritable> {
    private FirstPageLogWritable writable = new FirstPageLogWritable();
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        ValidateUtils.initCleanEntity(context.getConfiguration());
        super.setup(context);
    }
    @Override
    protected void map(LongWritable key, FlumeLogWritable value, Context context) throws IOException, InterruptedException {
        if(!ValidateUtils.validate(value)) return;
        if(!EventsTypeEnum.OPERATION_PAGE.getName().equals(value.getEventsType())){
            return;
        }
        if(!noFirstPage(value.getNowSpm())) return; //不在统计产品序列中


        writable.setParentColumnId(value.getParentColumnId());
        writable.setMac(value.getMac());
        writable.setSn(value.getSn());
        writable.setUserType(value.getUserType());
        writable.setUserId(value.getUserId());
        writable.setCreateTime(value.getCreateTime());
        writable.setColumnId(value.getColumnId());
        SpmEntity spmEntity = SpmEntity.initSpm(value.getAfterSpm());
        writable.setAfterColumnId(spmEntity.getColumnId());
        writable.setAfterColumnCode(spmEntity.getPageCode());
        writable.setAreaCode(spmEntity.getAreaCode());
        context.write(writable, NullWritable.get());
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
