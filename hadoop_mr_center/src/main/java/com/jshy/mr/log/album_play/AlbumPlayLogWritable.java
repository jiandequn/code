package com.jshy.mr.log.album_play;

import com.jshy.mr.utils.CommonUtils;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/9/29
 * Time: 10:11
 * To change this template use File | Settings | File Templates.
 */
public class AlbumPlayLogWritable implements DBWritable,WritableComparable<AlbumPlayLogWritable> {

    private String parentColumnId;
    private String userType;
    private String mac;
    private String sn;
    private String userId;
    private String columnId;
    private String albumId;
    private String videoId;
    private String contentType;
    private String timePosition;
    private String createTime;

    public String getParentColumnId() {
        return parentColumnId;
    }

    public void setParentColumnId(String parentColumnId) {
        this.parentColumnId = parentColumnId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getTimePosition() {
        return timePosition;
    }

    public void setTimePosition(String timePosition) {
        this.timePosition = timePosition;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    @Override
    public void write(PreparedStatement ps) throws SQLException {
        ps.setString(1,this.parentColumnId);
        ps.setString(2,this.userType);
        ps.setString(3,this.mac);
        ps.setString(4,this.sn);
        ps.setString(5,this.userId);
        ps.setString(6,this.columnId);
        ps.setString(7,this.albumId);
        ps.setString(8,this.videoId);
        ps.setString(9,this.contentType);
        ps.setString(10,this.timePosition);
        ps.setString(11,this.createTime);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {
        this.parentColumnId = resultSet.getString("parent_column_id");
        this.userType=resultSet.getString("user_type");
        this.mac = resultSet.getString("mac");
        this.sn=resultSet.getString("sn");
        this.userId = resultSet.getString("user_id");
        this.columnId=resultSet.getString("column_id");
        this.videoId = resultSet.getString("video_id");
        this.albumId = resultSet.getString("album_id");
        this.contentType=resultSet.getString("content_type");
        this.timePosition = resultSet.getString("time_position");
        this.createTime=resultSet.getString("create_time");
    }

    @Override
    public int compareTo(AlbumPlayLogWritable o) {
        return this.toString().compareTo(o.toString());
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.parentColumnId);
        dataOutput.writeUTF(this.userType);
        dataOutput.writeUTF(this.mac);
        dataOutput.writeUTF(this.sn);
        dataOutput.writeUTF(this.userId);
        dataOutput.writeUTF(this.columnId);
        dataOutput.writeUTF(this.albumId);
        dataOutput.writeUTF(this.videoId);
        dataOutput.writeUTF(this.contentType);
        dataOutput.writeUTF(this.timePosition);
        dataOutput.writeUTF(this.createTime);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.parentColumnId = dataInput.readUTF();
        this.userType= dataInput.readUTF();
        this.mac = dataInput.readUTF();
        this.sn= dataInput.readUTF();
        this.userId = dataInput.readUTF();
        this.columnId=dataInput.readUTF();
        this.albumId = dataInput.readUTF();
        this.videoId = dataInput.readUTF();
        this.contentType=dataInput.readUTF();
        this.timePosition = dataInput.readUTF();
        this.createTime=dataInput.readUTF();
    }

    @Override
    public String toString() {
        return CommonUtils.toStringFormat(parentColumnId,userType,mac,sn,userId,columnId,albumId,videoId,contentType,timePosition,createTime);
    }
}
