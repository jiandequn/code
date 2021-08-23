package com.jshy.mr.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/9/26
 * Time: 16:49
 * To change this template use File | Settings | File Templates.
 */
public enum UserTypeEnum {
    OTT("ott"),VOD("vod");
    private String name;
    UserTypeEnum(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public static boolean isNotExist(String name){
        if(StringUtils.isEmpty(name)){
            return true;
        }
        for(UserTypeEnum e : UserTypeEnum.values()){
            if(e.getName().equalsIgnoreCase(name)){
                return false;
            }
        }
        return true;
    }
}
