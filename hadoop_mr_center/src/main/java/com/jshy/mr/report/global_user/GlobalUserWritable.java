package com.jshy.mr.report.global_user;

import com.jshy.mr.model.BaseTimeEntity;
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
public class GlobalUserWritable extends BaseTimeEntity<GlobalUserWritable>  implements DBWritable {

    private Long count=0l; //停留时间

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public int compareTo(GlobalUserWritable o) {
        return this.toString().compareTo(o.toString());
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        super.readFields(dataInput);
        this.count = dataInput.readLong();
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        super.write(dataOutput);
        dataOutput.writeLong(this.count);
    }

    @Override
    public String toString() {
        return CommonUtils.toStringFormat(super.toString(),this.count);
    }


    @Override
    public void write(PreparedStatement preparedStatement) throws SQLException {
        Integer i= 1;
        i = writeDateSql(preparedStatement, i);
        preparedStatement.setLong(i++,this.count);
        preparedStatement.setLong(i++,this.count);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {

    }

//    @Override
//    public int hashCode() {
//        return this.getUserId();
//    }
}
