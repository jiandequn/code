package com.ppfuns.report.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 * 区域在线订购周统计(AreaOnlineOrderCountMonth)表实体类
 *
 * @author jdq
 * @since 2021-06-02 16:52:37
 */
@SuppressWarnings("serial")
public class AreaOnlineOrderCountMonth extends Model<AreaOnlineOrderCountMonth> {
    //年份
    private String y;
    //月
    private String m;
    //用户类型
    private String userType;
    //区域
    private String areaCode;
    //产品ID
    private Integer productId;
    //产品名称
    private String productName;
    //产品类型
    private Integer productType;
    //产品Code
    private String thirdProductCode;
    //价格
    private Double presentPrice;
    //订购用户数
    private Integer userCount;
    //订购次数
    private Integer orderCount;
    //金额
    private Double amount;
    @TableField(exist = false)
    private String areaName;

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public String getThirdProductCode() {
        return thirdProductCode;
    }

    public void setThirdProductCode(String thirdProductCode) {
        this.thirdProductCode = thirdProductCode;
    }

    public Double getPresentPrice() {
        return presentPrice;
    }

    public void setPresentPrice(Double presentPrice) {
        this.presentPrice = presentPrice;
    }

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
