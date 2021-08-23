package com.jshy.mr.log.first_page;

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
public class FirstPageLogWritable implements DBWritable,WritableComparable<FirstPageLogWritable> {

    private String parentColumnId;
    private String userType;
    private String mac;
    private String sn;
    private String userId;
    private String columnId;
    private String areaCode;
    private String afterColumnId;
    private String afterColumnCode;
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

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAfterColumnId() {
        return afterColumnId;
    }

    public void setAfterColumnId(String afterColumnId) {
        this.afterColumnId = afterColumnId;
    }

    public String getAfterColumnCode() {
        return afterColumnCode;
    }

    public void setAfterColumnCode(String afterColumnCode) {
        this.afterColumnCode = afterColumnCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public void write(PreparedStatement ps) throws SQLException {
        ps.setString(1,this.parentColumnId);
        ps.setString(2,this.userType);
        ps.setString(3,this.mac);
        ps.setString(4,this.sn);
        ps.setString(5,this.userId);
        ps.setString(6,this.columnId);
        ps.setString(7,this.afterColumnId);
        ps.setString(8,this.afterColumnCode);
        ps.setString(9,this.areaCode);
        ps.setString(10,this.createTime);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {
        this.parentColumnId = resultSet.getString("parent_column_id");
        this.userType=resultSet.getString("user_type");
        this.mac = resultSet.getString("mac");
        this.sn=resultSet.getString("sn");
        this.userId = resultSet.getString("user_id");
        this.columnId=resultSet.getString("column_id");
        this.createTime=resultSet.getString("create_time");
        this.areaCode = resultSet.getString("area_code");
        this.afterColumnId = resultSet.getString("after_column_id");
        this.afterColumnCode = resultSet.getString("after_column_code");
    }

    @Override
    public int compareTo(FirstPageLogWritable o) {
        return 0;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.parentColumnId);
        dataOutput.writeUTF(this.userType);
        dataOutput.writeUTF(this.mac);
        dataOutput.writeUTF(this.sn);
        dataOutput.writeUTF(this.userId);
        dataOutput.writeUTF(this.columnId);
        dataOutput.writeUTF(this.afterColumnId);
        dataOutput.writeUTF(this.afterColumnCode);
        dataOutput.writeUTF(this.areaCode);
        dataOutput.writeUTF(this.createTime);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.parentColumnId = dataInput.readUTF();
        this.userType = dataInput.readUTF();
        this.mac = dataInput.readUTF();
        this.sn = dataInput.readUTF();
        this.userId = dataInput.readUTF();
        this.columnId = dataInput.readUTF();
        this.afterColumnId = dataInput.readUTF();
        this.afterColumnCode = dataInput.readUTF();
        this.areaCode = dataInput.readUTF();
        this.createTime = dataInput.readUTF();
    }
}
