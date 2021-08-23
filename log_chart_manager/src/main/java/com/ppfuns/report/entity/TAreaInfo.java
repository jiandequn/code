package com.ppfuns.report.entity;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author jian.dq
 * @since 2020-09-28
 */
public class TAreaInfo implements Serializable {
    private Integer id;

    private static final long serialVersionUID = 1L;

    /**
     * ott,vod
     */
    private String type;

    /**
     * 地区编码
     */
    private String areaNo;

    /**
     * 地区名称
     */
    private String areaName;

    /**
     * 城市编码
     */
    private String cityNo;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 状态
     */
    private Integer isEffective;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getAreaNo() {
        return areaNo;
    }

    public void setAreaNo(String areaNo) {
        this.areaNo = areaNo;
    }
    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
    public String getCityNo() {
        return cityNo;
    }

    public void setCityNo(String cityNo) {
        this.cityNo = cityNo;
    }
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public Integer getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(Integer isEffective) {
        this.isEffective = isEffective;
    }

    @Override
    public String toString() {
        return "TAreaInfo{" +
            "type=" + type +
            ", areaNo=" + areaNo +
            ", areaName=" + areaName +
            ", cityNo=" + cityNo +
            ", cityName=" + cityName +
            ", isEffective=" + isEffective +
        "}";
    }
}
