package com.ppfuns.report.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 所有专区留存用户数统计(NoappRetentionUserCountDay)表实体类
 *
 * @author jdq
 * @since 2021-07-06 17:03:50
 */
@SuppressWarnings("serial")
public class NoappRetentionUserCountDay extends Model<NoappRetentionUserCountDay> {
    //日期
    private String tDate;

    private String userType;

    //2日留存用户数
    @TableField(value="user_2day_count")
    private Integer user2dayCount;
    //3日留存用户数
    @TableField(value="user_3day_count")
    private Integer user3dayCount;


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

    public Integer getUser2dayCount() {
        return user2dayCount;
    }

    public void setUser2dayCount(Integer user2dayCount) {
        this.user2dayCount = user2dayCount;
    }

    public Integer getUser3dayCount() {
        return user3dayCount;
    }

    public void setUser3dayCount(Integer user3dayCount) {
        this.user3dayCount = user3dayCount;
    }

}
