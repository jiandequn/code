package com.ppfuns.report.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

/**
 * <p>
 * 留存用户数统计
 * </p>
 *
 * @author jian.dq
 * @since 2020-09-23
 */
public class AppRetentionUserCountDay implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日期
     */
    @ExcelProperty(value = "时间",index=0)
    private String tDate;
    @ExcelProperty(value = "专区",index=1)
    private String parentColumnId;
    @ExcelProperty(value = "用户类型",index=2)
    private String userType;

    /**
     * 2日留存用户数
     */
    @TableField(value="user_2day_count")
    @ExcelProperty(value = "2日留存用户数",index=3)
    private Integer user2dayCount;

    /**
     * 3日留存用户数
     */
    @TableField(value="user_3day_count")
    @ExcelProperty(value = "3日留存用户数",index=4)
    private Integer user3dayCount;

    /**
     * 2日新增用户留存数
     */
    @TableField(value="add_user_2day_count")
    @ExcelProperty(value = "新增用户2日留存数",index=5)
    private Integer addUser2dayCount;

    public String gettDate() {
        return tDate;
    }

    public void settDate(String tDate) {
        this.tDate = tDate;
    }
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
    public Integer getUser2dayCount() {
        return user2dayCount;
    }

    public void setUser2dayCount(Integer user2dayCount) {
        this.user2dayCount = user2dayCount;
    }
    public Integer getUser3dayCount() {
        return user3dayCount;
    }

    public void setUser3dayCount(Integer user3dayCount) {
        this.user3dayCount = user3dayCount;
    }
    public Integer getAddUser2dayCount() {
        return addUser2dayCount;
    }

    public void setAddUser2dayCount(Integer addUser2dayCount) {
        this.addUser2dayCount = addUser2dayCount;
    }

    @Override
    public String toString() {
        return "RetentionUserCountDay{" +
            "tDate=" + tDate +
            ", parentColumnId=" + parentColumnId +
            ", userType=" + userType +
            ", user2dayCount=" + user2dayCount +
            ", user3dayCount=" + user3dayCount +
            ", addUser2dayCount=" + addUser2dayCount +
        "}";
    }
}
