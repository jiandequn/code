package com.ppfuns.report.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 * 专区抽奖按周统计(AppDrawPrizeCountWeek)表实体类
 *
 * @author jdq
 * @since 2021-06-02 17:03:32
 */
@SuppressWarnings("serial")
public class AppDrawPrizeCountWeek extends Model<AppDrawPrizeCountWeek> {
    //年份
    private String y;
    //周
    private String w;
    //用户类型
    private String userType;
    //专区ID
    private Integer parentColumnId;
    //抽奖用户数
    private Integer drawUserCount;
    //抽奖次数
    private Integer drawCount;
    //中奖用户数
    private Integer drawedUserCount;
    //中奖次数
    private Integer drawedCount;


    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getW() {
        return w;
    }

    public void setW(String w) {
        this.w = w;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Integer getParentColumnId() {
        return parentColumnId;
    }

    public void setParentColumnId(Integer parentColumnId) {
        this.parentColumnId = parentColumnId;
    }

    public Integer getDrawUserCount() {
        return drawUserCount;
    }

    public void setDrawUserCount(Integer drawUserCount) {
        this.drawUserCount = drawUserCount;
    }

    public Integer getDrawCount() {
        return drawCount;
    }

    public void setDrawCount(Integer drawCount) {
        this.drawCount = drawCount;
    }

    public Integer getDrawedUserCount() {
        return drawedUserCount;
    }

    public void setDrawedUserCount(Integer drawedUserCount) {
        this.drawedUserCount = drawedUserCount;
    }

    public Integer getDrawedCount() {
        return drawedCount;
    }

    public void setDrawedCount(Integer drawedCount) {
        this.drawedCount = drawedCount;
    }

}
