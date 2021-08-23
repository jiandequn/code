package com.jshy.mr.log.user_log;

import com.jshy.mr.common.FlumeLogWritable;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.lang.reflect.InvocationTargetException;
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
public class UserLogDbWritable extends FlumeLogWritable implements DBWritable{

    public UserLogDbWritable() {
    }
    public UserLogDbWritable(FlumeLogWritable value) {
        try {
            BeanUtils.copyProperties(this,value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(PreparedStatement ps) throws SQLException {
        ps.setString(1,this.getEventsType());
        ps.setString(2,this.getMac());
        ps.setString(3,this.getSn());
        ps.setString(4,this.getUserId());
        ps.setString(5,this.getUserType());
        ps.setString(6,this.getParentColumnId());
        ps.setString(7,this.getColumnId());
        ps.setString(8,this.getContentId());
        ps.setString(9,this.getContentType());
        ps.setString(10,this.getOperateType());
        ps.setString(11,this.getTimePosition());
        ps.setString(12,this.getCreateTime());
        ps.setString(13,this.getNowSpm());
        ps.setString(14,this.getAfterSpm());
        ps.setString(15,this.getPos());
        ps.setString(16,this.getPosName());
        ps.setString(17,this.getKeyWord());
        ps.setString(18,this.getCode());
        ps.setString(19,this.getMessage());
        ps.setString(20,this.getProductId());
        ps.setString(21,this.getIsEffective());
        ps.setString(22,this.getResourceId());
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {
//        this.parentColumnId = resultSet.getString("parent_column_id");
//        this.userType=resultSet.getString("user_type");
//        this.mac = resultSet.getString("mac");
//        this.sn=resultSet.getString("sn");
//        this.userId = resultSet.getString("user_id");
//        this.columnId=resultSet.getString("column_id");
//        this.createTime=resultSet.getString("create_time");
    }
}
