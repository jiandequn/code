package com.ppfuns.report.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * 用户信息
 * </p>
 *
 * @author jian.dq
 * @since 2020-09-15
 */
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 专区ID
     */
    private String parentColumnId;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 区域码
     */
    private String areaCode;
    @TableField(exist = false)
    private String areaName;

    /**
     * 创建时间
     */
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
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
            "parentColumnId=" + parentColumnId +
            ", userType=" + userType +
            ", userId=" + userId +
            ", areaCode=" + areaCode +
            ", createTime=" + createTime +
        "}";
    }
}
