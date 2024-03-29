package com.ppfuns.report.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 按周统计专辑详情页专辑类型访问(AppDetailPageAlbumContentTypeCountWeek)表实体类
 *
 * @author jdq
 * @since 2021-07-28 16:22:17
 */
@SuppressWarnings("serial")
public class AppDetailPageAlbumContentTypeCountWeek extends Model<AppDetailPageAlbumContentTypeCountWeek> {
    //年份
    private Integer y;
    //周
    private Object w;
    //专区ID
    private String parentColumnId;
    //用户类型
    private String userType;
    //专辑类型
    private String contentType;
    //类型名称
    private String contentTypeName;
    //用户数
    private Long userCount;
    //点击数
    private Long visitCount;


    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Object getW() {
        return w;
    }

    public void setW(Object w) {
        this.w = w;
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

    public Long getUserCount() {
        return userCount;
    }

    public void setUserCount(Long userCount) {
        this.userCount = userCount;
    }

    public Long getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(Long visitCount) {
        this.visitCount = visitCount;
    }

}
