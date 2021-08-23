package com.jshy.mr.model;

import com.jshy.mr.utils.CommonUtils;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/9/20
 * Time: 16:40
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseEntity<T> implements WritableComparable<T> {
    private String mac="";
    private String sn="";
    private String userId="";


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

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.mac);
        dataOutput.writeUTF(this.sn);
        dataOutput.writeUTF(this.userId);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.mac = dataInput.readUTF();
        this.sn = dataInput.readUTF();
        this.userId = dataInput.readUTF();


    }

    @Override
    public String toString() {
        return CommonUtils.toStringFormat(mac,sn,userId);
    }

    public int writeUserSql(PreparedStatement preparedStatement,Integer i) throws SQLException {
        preparedStatement.setString(i++,this.getMac());
        preparedStatement.setString(i++,this.getSn());
        preparedStatement.setString(i++,this.getUserId());
        return i;
    }
}
