package com.ppfuns.report.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 * 专区奖品的抽奖按日统计(AppPrizeDetailCountDay)表实体类
 *
 * @author jdq
 * @since 2021-06-15 18:47:50
 */
@SuppressWarnings("serial")
public class AppPrizeDetailCountDay extends Model<AppPrizeDetailCountDay> {
    //日期
    private String tDate;
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
