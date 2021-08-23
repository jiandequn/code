package com.jshy.mr.model;

import com.jshy.mr.utils.CommonUtils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/11/12
 * Time: 14:25
 * To change this template use File | Settings | File Templates.
 */
public class BaseTimeEntity<T> extends BaseEntity<T> {
    private int year=0;
    private int quarter=0;
    private int month=0;
    private int week=0;
    private int day=0;
    private String userType="";
    private String parentColumnId="";

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getQuarter() {
        return quarter;
    }

    public void setQuarter(int quarter) {
        this.quarter = quarter;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getParentColumnId() {
        return parentColumnId;
    }

    public void setParentColumnId(String parentColumnId) {
        this.parentColumnId = parentColumnId;
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.year = dataInput.readInt();
        this.quarter = dataInput.readInt();
        this.month = dataInput.readInt();
        this.week = dataInput.readInt();
        this.day = dataInput.readInt();
        this.parentColumnId = dataInput.readUTF();
        this.userType = dataInput.readUTF();
        super.readFields(dataInput);

    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.year);
        dataOutput.writeInt(this.quarter);
        dataOutput.writeInt(this.month);
        dataOutput.writeInt(this.week);
        dataOutput.writeInt(this.day);
        dataOutput.writeUTF(this.parentColumnId);
        dataOutput.writeUTF(this.userType);
        super.write(dataOutput);
    }

    @Override
    public String toString() {
        return CommonUtils.toStringFormat(this.year,this.quarter,this.month,this.week,this.day,parentColumnId,userType,super.toString());
    }

    @Override
    public int compareTo(T o) {
        return 0;
    }
    public int writeDateSql(PreparedStatement preparedStatement,Integer i) throws SQLException {
        if(year != 0){
            if(day != 0 && month != 0){
                String month = this.getMonth()<10?"0"+getMonth():this.getMonth()+"";
                String day = this.getDay()<10?"0"+getDay():this.getDay()+"";
                String date = this.getYear()+"-"+month+"-"+day;
                preparedStatement.setString(i++,date);
            }else if(week != 0){
                preparedStatement.setInt(i++,this.getYear());
                preparedStatement.setInt(i++,this.getWeek());
            }else if(quarter != 0) {
                preparedStatement.setInt(i++,this.getYear());
                preparedStatement.setInt(i++,this.getQuarter());
            }else if(month != 0){
                preparedStatement.setInt(i++,this.getYear());
                preparedStatement.setInt(i++,this.getMonth());
            }else {
                preparedStatement.setInt(i++,this.getYear());
            }
        }
        preparedStatement.setString(i++,this.parentColumnId);
        preparedStatement.setString(i++,this.userType);
        return i;
    }
}
