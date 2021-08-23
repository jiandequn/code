package com.ppfuns.report.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 入口专题下详情页访问周统计(AppInletPositionSubjectAlbumCountWeek)表实体类
 *
 * @author jdq
 * @since 2021-08-02 16:52:47
 */
@SuppressWarnings("serial")
public class AppInletPositionSubjectAlbumCountWeek extends Model<AppInletPositionSubjectAlbumCountWeek> {
    //年份
    private String y;
    //周
    private Object w;
    //用户类型
    private String userType;
    //事件
    private String eventsType;
    //专区ID
    private String parentColumnId;
    //spm
    private String afterSpm;
    //专题名称
    private String subjectName;
    //专辑ID
    private Integer albumId;
    //专辑名称
    private String albumName;
    //专辑类型
    private Integer contentType;
    //专辑类型名称
    private String contentTypeName;
    //用户数
    private Integer userCount;
    //点击次数
    private Long visitCount;


    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public Object getW() {
        return w;
    }

    public void setW(Object w) {
        this.w = w;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEventsType() {
        return eventsType;
    }

    public void setEventsType(String eventsType) {
        this.eventsType = eventsType;
    }

    public String getParentColumnId() {
        return parentColumnId;
    }

    public void setParentColumnId(String parentColumnId) {
        this.parentColumnId = parentColumnId;
    }

    public String getAfterSpm() {
        return afterSpm;
    }

    public void setAfterSpm(String afterSpm) {
        this.afterSpm = afterSpm;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
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

    public Long getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(Long visitCount) {
        this.visitCount = visitCount;
    }

}
