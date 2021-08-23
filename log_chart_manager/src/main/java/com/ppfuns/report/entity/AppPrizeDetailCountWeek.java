package com.ppfuns.report.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 * 专区奖品的抽奖按周统计(AppPrizeDetailCountWeek)表实体类
 *
 * @author jdq
 * @since 2021-06-15 18:48:35
 */
@SuppressWarnings("serial")
public class AppPrizeDetailCountWeek extends Model<AppPrizeDetailCountWeek> {
    //年份
    private String y;
    //周
    private String w;
    //用户类型
    private String userType;
    //专区ID
    private Integer parentColumnId;
    //奖品ID
    private Integer prizeId;
    //奖品名称
    private String prizeName;
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

    public Integer getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(Integer prizeId) {
        this.prizeId = prizeId;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
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
