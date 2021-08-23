package com.ppfuns.report.entity;

import java.io.Serializable;

/**
 * <p>
 * 日志中需清理用户ID表
 * </p>
 *
 * @author jian.dq
 * @since 2020-09-28
 */
public class CleanUser implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 是否启用 0禁用 1启用
     */
    private Integer isEffective;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
    public Integer getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(Integer isEffective) {
        this.isEffective = isEffective;
    }

    @Override
    public String toString() {
        return "CleanUser{" +
            "userId=" + userId +
            ", userType=" + userType +
            ", isEffective=" + isEffective +
        "}";
    }
}
