package com.jshy.mr.log.album_play;

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
public class AlbumPlayLogMapper extends
        Mapper<LongWritable, FlumeLogWritable, AlbumPlayLogWritable, NullWritable> {
    private AlbumPlayLogWritable writable = new AlbumPlayLogWritable();
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        ValidateUtils.initCleanEntity(context.getConfiguration());
        super.setup(context);
    }
    @Override
    protected void map(LongWritable key, FlumeLogWritable value, Context context) throws IOException, InterruptedException {
        if(!ValidateUtils.validate(value)) return;
        if(!EventsTypeEnum.OPERATE_RESUMEPOINT.getName().equals(value.getEventsType())
                || !"add".equals(value.getOperateType())){
            return;
        }
        writable.setParentColumnId(value.getParentColumnId());
        writable.setUserType(value.getUserType());
        writable.setMac(value.getMac());
        writable.setSn(value.getSn());
        writable.setUserId(value.getUserId());
        writable.setColumnId(value.getColumnId());
        writable.setAlbumId(value.getContentId());
        writable.setContentType(value.getContentType());
        writable.setTimePosition(value.getTimePosition());
        writable.setCreateTime(value.getCreateTime());
        writable.setVideoId(value.getVideoId());
        context.write(writable, NullWritable.get());
    }
}
