package com.jshy.mr.model;

import com.jshy.mr.utils.CommonUtils;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/9/27
 * Time: 11:23
 * To change this template use File | Settings | File Templates.
 */
public class AlbumCountWritable implements WritableComparable<AlbumCountWritable>{

    private Long playCount = 0L;  //播放次数
    private Long timePosition = 0L;   //播放时间长
    private Long userCount = 0L; //播放用户数

    public Long getPlayCount() {
        return playCount;
    }

    public void setPlayCount(Long playCount) {
        this.playCount = playCount;
    }

    public Long getTimePosition() {
        return timePosition;
    }

    public void setTimePosition(Long timePosition) {
        this.timePosition = timePosition;
    }

    public Long getUserCount() {
        return userCount;
    }

    public void setUserCount(Long userCount) {
        this.userCount = userCount;
    }

    @Override
    public int compareTo(AlbumCountWritable o) {
        return this.compareTo(o);
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(this.playCount);
        dataOutput.writeLong(this.timePosition);
        dataOutput.writeLong(this.userCount);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.playCount = dataInput.readLong();
        this.timePosition = dataInput.readLong();
        this.userCount = dataInput.readLong();
    }
    @Override
    public String toString() {
        return CommonUtils.toStringFormat(this.playCount,this.timePosition,this.userCount);
    }

}
