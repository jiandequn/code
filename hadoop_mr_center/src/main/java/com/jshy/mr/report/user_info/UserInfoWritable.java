package com.jshy.mr.report.user_info;

import com.jshy.mr.model.BaseEntity;
import com.jshy.mr.utils.CommonUtils;
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
 * Date: 2019/11/12
 * Time: 14:08
 * To change this template use File | Settings | File Templates.
 */
public class UserInfoWritable extends BaseEntity<UserInfoWritable> implements DBWritable {

    private String parentColumnId="";
    private String userType="";
    private String createTime=""; //最近访问时间
    private String areaCode = ""; //区域码

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    @Override
    public int compareTo(UserInfoWritable o) {
        return this.toString().compareTo(o.toString());
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.parentColumnId = dataInput.readUTF();
        this.userType = dataInput.readUTF();
        super.readFields(dataInput);
        this.areaCode = dataInput.readUTF();
        this.createTime = dataInput.readUTF();
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.parentColumnId);
        dataOutput.writeUTF(this.userType);
        super.write(dataOutput);
        dataOutput.writeUTF(this.areaCode);
        dataOutput.writeUTF(this.createTime);
    }

    @Override
    public String toString() {
        return CommonUtils.toStringFormat(this.parentColumnId,this.userType,super.toString(),this.areaCode,this.createTime);
    }


    @Override
    public void write(PreparedStatement preparedStatement) throws SQLException {
        Integer i= 1;
        preparedStatement.setString(i++,this.parentColumnId);
        preparedStatement.setString(i++,this.userType);
        i = writeUserSql(preparedStatement, i);
        preparedStatement.setString(i++,this.areaCode);
        preparedStatement.setString(i++,this.createTime);
        preparedStatement.setString(i++,this.createTime);
        preparedStatement.setString(i++,this.createTime);
        preparedStatement.setString(i++,this.areaCode);
        preparedStatement.setString(i++,this.areaCode);
        preparedStatement.setString(i++,this.areaCode);
//        preparedStatement.setString(i++,this.areaCode);
        //用户过滤新增的用户select条件
//        preparedStatement.setString(i++,this.parentColumnId);
//        preparedStatement.setString(i++,this.userType);
//        i = writeUserSql(preparedStatement, i);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
