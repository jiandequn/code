package com.jshy.mr.entity;

import org.apache.commons.lang3.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/11/11
 * Time: 16:57
 * To change this template use File | Settings | File Templates.
 */
public class SpmEntity {
    private String parentColumnId = ""; //一级栏目id
    private String pageCode = "";   //".PAGE_" + pageCode(页面编码)
    private String columnId = "";  //当栏目ID
    private String contentId = ""; //内容ID
    private String timeStamp = ""; //时间戳
    private String areaCode = ""; //区域码

    public String getParentColumnId() {
        return parentColumnId;
    }

    public void setParentColumnId(String parentColumnId) {
        this.parentColumnId = parentColumnId;
    }

    public String getPageCode() {
        return pageCode;
    }

    public void setPageCode(String pageCode) {
        this.pageCode = pageCode;
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
    public static SpmEntity initSpm(String spm){
        SpmEntity spmEntity = new SpmEntity();
        if(StringUtils.isNotEmpty(spm)){
            String[] ns = spm.split("\\.");
            spmEntity.parentColumnId=ns[0];
            if(ns.length>1) spmEntity.pageCode = ns[1];
            if(ns.length>2) spmEntity.columnId = ns[2];
            if(ns.length>3) spmEntity.contentId = ns[3];
            if(ns.length>4) spmEntity.timeStamp = ns[4];
            if(ns.length>5) spmEntity.areaCode = ns[5];
        }
        return spmEntity;
    }
}
