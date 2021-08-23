package com.ppfuns.report.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 专辑类型日统计
 * </p>
 *
 * @author jian.dq
 * @since 2021-02-02
 */
public class AppAlbumContentTypeDay implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日期
     */
    private String tDate;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 专区ID
     */
    @TableField("parent_column_Id")
    private String parentColumnId;

    /**
     * 专辑类型
     */
    private String contentType;

    /**
     * 专辑类型名称
     */
    private String contentTypeName;

    /**
     * 用户数
     */
    private Integer userCount;

    /**
     * 播放次数
     */
    private Long playCount;

    /**
     * 播放时长
     */
    private Long duration;

    public String gettDate() {
        return tDate;
    }

    public void settDate(String tDate) {
        this.tDate = tDate;
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
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    public String getContentTypeName() {
        return contentTypeName;
    }

    public void setContentTypeName(String contentTypeName) {
        this.contentTypeName = contentTypeName;
    }
    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }
    public Long getPlayCount() {
        return playCount;
    }

    public void setPlayCount(Long playCount) {
        this.playCount = playCount;
    }
    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "AppAlbumContentTypeDay{" +
            "tDate=" + tDate +
            ", userType=" + userType +
            ", parentColumnId=" + parentColumnId +
            ", contentType=" + contentType +
            ", contentTypeName=" + contentTypeName +
            ", userCount=" + userCount +
            ", playCount=" + playCount +
            ", duration=" + duration +
        "}";
    }
}
