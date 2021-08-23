package com.jdq.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 系统用户表
 * </p>
 *
 * @author jian.dq
 * @since 2020-03-09
 */
@TableName("sys_user")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 账户名
     */
    private String realName;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 手机号
     */
    private String telephone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 新增用户
     */
    private String createUser;

    /**
     * 注册时间
     */
    private Date createTime;

    /**
     * 修改用户
     */
    private String updateUser;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 是否删除（1正常；0已删）
     */
    private Boolean isDel;

    /**
     * 是否在职（1正常；0离职）
     */
    private Boolean isJob;

    /**
     * 更新版本
     */
    private Integer version;
    private boolean sex;
    @TableField(exist = false)
    private List<SysUserRole> userRoleList;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public Boolean getDel() {
        return isDel;
    }

    public void setDel(Boolean isDel) {
        this.isDel = isDel;
    }
    public Boolean getJob() {
        return isJob;
    }

    public void setJob(Boolean isJob) {
        this.isJob = isJob;
    }
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public List<SysUserRole> getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(List<SysUserRole> userRoleList) {
        this.userRoleList = userRoleList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
            "realName=" + realName +
            ", userName=" + userName +
            ", telephone=" + telephone +
            ", email=" + email +
            ", password=" + password +
            ", createUser=" + createUser +
            ", createTime=" + createTime +
            ", updateUser=" + updateUser +
            ", updateTime=" + updateTime +
            ", isDel=" + isDel +
            ", isJob=" + isJob +
            ", version=" + version +
            ", sex=" + sex +
        "}";
    }
}
