package com.ppfuns.report.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 * 按日统计详情访问(AppDetailPageCountDay)表实体类
 *
 * @author jdq
 * @since 2021-06-15 18:23:59
 */
@SuppressWarnings("serial")
public class AppDetailPageCountDay extends Model<AppDetailPageCountDay> {
    //日期
    private String tDate;
    //专区ID
    private String parentColumnId;
    //用户类型
    private String userType;
    //用户数
    private Long userCount;
    //点击数
    private Long visitCount;


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
