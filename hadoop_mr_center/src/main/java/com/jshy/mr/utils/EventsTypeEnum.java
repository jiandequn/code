package com.jshy.mr.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/9/24
 * Time: 16:54
 * To change this template use File | Settings | File Templates.
 */
public enum EventsTypeEnum {
    OPERATION_PAGE("operationPage","操作页面"),
    AUTH_PRODUCT("auth_product","鉴权"),
    OPERATION_DETAILS("operationDetails","详情页"),
    OPERATION_SEARCH("operationSearch","搜索操作"),
    OPERATE_BOOKMARK("operateBookMark","收藏操作"),
    OPERATE_RESUMEPOINT("operateResumePoint","历史点播操作");

    private String name;
    private String text;
    EventsTypeEnum(String name,String text){
        this.name = name;
        this.text = text;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static boolean isNotExist(String name){
        if(StringUtils.isEmpty(name)){
            return true;
        }
        for(EventsTypeEnum e : EventsTypeEnum.values()){
            if(e.getName().equalsIgnoreCase(name)){
                return false;
            }
        }
        return true;
    }
}
