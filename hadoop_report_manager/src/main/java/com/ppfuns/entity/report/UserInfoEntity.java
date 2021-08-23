package com.ppfuns.entity.report;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2020/1/2
 * Time: 15:30
 * To change this template use File | Settings | File Templates.
 */
@TableName(value = "user_info")//指定表名
public class UserInfoEntity {
    @ExcelIgnore
    @TableField(value="parent_column_id")
    private String parentColumnId;
    @ExcelProperty(value = "用户类型",index=1)
    @TableField(value="user_type")
    private String userType;
    @ExcelProperty(value = "MAC",index=2)
    @TableField(value="mac")
    private String mac;
    @ExcelProperty(value = "SN",index=3)
    @TableField(value="sn")
    private String sn;
    @ExcelProperty(value = "用户ID",index=4)
    @TableField(value="user_id")
    private String userId;
    @ExcelProperty(value = "创建时间",index=5)
    @TableField(value="create_time")
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
