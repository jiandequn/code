package com.jshy.mr.report.user_stay_duration;

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
public class UserStayDurationWritable extends BaseTimeEntity<UserStayDurationWritable>  implements DBWritable {

    private Long stayDuration=0l; //停留时间

    public Long getStayDuration() {
        return stayDuration;
    }

    public void setStayDuration(Long stayDuration) {
        this.stayDuration = stayDuration;
    }

    @Override
    public int compareTo(UserStayDurationWritable o) {
        return this.toString().compareTo(o.toString());
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        super.readFields(dataInput);
        this.stayDuration = dataInput.readLong();
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        super.write(dataOutput);
        dataOutput.writeLong(this.stayDuration);
    }

    @Override
    public String toString() {
        return CommonUtils.toStringFormat(super.toString(),this.stayDuration);
    }


    @Override
    public void write(PreparedStatement preparedStatement) throws SQLException {
        Integer i= 1;
        i = writeDateSql(preparedStatement, i);
        i = writeUserSql(preparedStatement,i);
        preparedStatement.setLong(i++,this.stayDuration);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {

    }
}
