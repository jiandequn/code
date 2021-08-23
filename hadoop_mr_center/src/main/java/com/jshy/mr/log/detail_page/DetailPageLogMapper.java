package com.jshy.mr.log.detail_page;

import com.jshy.mr.common.FlumeLogWritable;
import com.jshy.mr.utils.EventsTypeEnum;
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
public class DetailPageLogMapper extends
        Mapper<LongWritable, FlumeLogWritable, DetailPageLogWritable, NullWritable> {
    private  DetailPageLogWritable writable = new DetailPageLogWritable();
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        ValidateUtils.initCleanEntity(context.getConfiguration());
        super.setup(context);
    }
    @Override
    protected void map(LongWritable key, FlumeLogWritable value, Context context) throws IOException, InterruptedException {
        if(!ValidateUtils.validate(value)) return;
        if(!EventsTypeEnum.OPERATION_DETAILS.getName().equals(value.getEventsType())
                || !"1".equals(value.getContentType())){
            return;
        }
        writable.setParentColumnId(value.getParentColumnId());
        writable.setMac(value.getMac());
        writable.setSn(value.getSn());
        writable.setUserType(value.getUserType());
        writable.setUserId(value.getUserId());
        writable.setCreateTime(value.getCreateTime());
        writable.setAlbumId(value.getContentId());
        writable.setColumnId(value.getColumnId());
        writable.setContentType(value.getContentType());
        writable.initSpm(value.getAfterSpm());
        context.write(writable, NullWritable.get());
    }
}
