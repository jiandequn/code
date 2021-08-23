package com.ppfuns.utils;

import org.springframework.util.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/10/24
 * Time: 10:40
 * To change this template use File | Settings | File Templates.
 */
public enum SqlTableEnum {
    ORIGINAL_RECORD_LOG("专区日志记录","original_record_log",""),
    ALBUM_PLAY_LOG("专辑播放记录","album_play_log",""),
    FIRST_PAGE_LOG("首页访问记录","first_page_log",""),
    DETAIL_PAGE_LOG("详情页访问记录","detail_page_log","");

    private String desc;
    private String tableName;
    private String tableSql;
    SqlTableEnum(String desc,String tableName,String tableSql){
         this.desc = desc;
         this.tableName = tableName;
         this.tableSql =tableSql;
    }
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableSql() {
        return tableSql;
    }

    public void setTableSql(String tableSql) {
        this.tableSql = tableSql;
    }
    public static  SqlTableEnum getTable(String tableName){
        if(StringUtils.isEmpty(tableName)) return null;
        SqlTableEnum [] es = SqlTableEnum.values();
        for(SqlTableEnum e : es){
            if(e.tableName.equalsIgnoreCase(tableName)){
                return e;
            }
        }
        return null;
    }
    public static String isExist(String alisTableName){
        SqlTableEnum [] es = SqlTableEnum.values();
        for(SqlTableEnum e : es){
            if(alisTableName.contains(e.getTableName())){
                return e.getTableName();
            }
        }
        return alisTableName;
    }
}
