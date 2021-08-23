package com.ppfuns.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 系统操作日志
 * </p>
 *
 * @author jian.dq
 * @since 2020-03-09
 */
@TableName("sys_operation_log")
public class SysOperationLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 真实名字
     */
    private String realName;

    /**
     * 浏览器
     */
    private String broswer;

    /**
     * 日志内容
     */
    private String content;

    /**
     * 日志级别
     */
    private Integer level;

    /**
     * IP
     */
    private String ip;

    /**
     * 请求URI
     */
    private String reqUri;

    /**
     * 操作类型
     */
    private Integer opType;

    /**
     * 操作时间
     */
    private LocalDateTime opTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
    public String getBroswer() {
        return broswer;
    }

    public void setBroswer(String broswer) {
        this.broswer = broswer;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getReqUri() {
        return reqUri;
    }

    public void setReqUri(String reqUri) {
        this.reqUri = reqUri;
    }
    public Integer getOpType() {
        return opType;
    }

    public void setOpType(Integer opType) {
        this.opType = opType;
    }
    public LocalDateTime getOpTime() {
        return opTime;
    }

    public void setOpTime(LocalDateTime opTime) {
        this.opTime = opTime;
    }

    @Override
    public String toString() {
        return "OperationLog{" +
            "userId=" + userId +
            ", userName=" + userName +
            ", realName=" + realName +
            ", broswer=" + broswer +
            ", content=" + content +
            ", level=" + level +
            ", ip=" + ip +
            ", reqUri=" + reqUri +
            ", opType=" + opType +
            ", opTime=" + opTime +
        "}";
    }
}
