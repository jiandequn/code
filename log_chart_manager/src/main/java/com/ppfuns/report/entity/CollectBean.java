package com.ppfuns.report.entity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2020/12/18.
 */
public class CollectBean implements Comparable{
    private String username;
    private String userType;
    private String parentColumnId;
    private String areaCode;

    private List<Map<String,String>> data = new ArrayList<>();
    private Map<String,List<Map<String,String>>> sortTimeMap = new HashMap<>();
    private List<String> timeSortList = new ArrayList<>();
    public CollectBean(String username,String userType,String parentColumnId,String areaCode){
        this.userType=userType;
        this.username=username;
        this.parentColumnId=parentColumnId;
        this.areaCode=areaCode;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getParentColumnId() {
        return parentColumnId;
    }

    public void setParentColumnId(String parentColumnId) {
        this.parentColumnId = parentColumnId;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public List<Map<String, String>> getData() {
        return data;
    }

    public void setData(List<Map<String, String>> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object obj) {
        return obj.toString().equals(this.toString());
    }

    @Override
    public java.lang.String toString() {
        return username+"_"+userType+"_"+parentColumnId+"_"+areaCode;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    public Map<String, List<Map<String,String>>> getSortTimeMap() {
        return sortTimeMap;
    }

    public void setSortTimeMap(Map<String, List<Map<String,String>>> sortTimeMap) {
        this.sortTimeMap = sortTimeMap;
    }

    public List<String> getTimeSortList() {
        return timeSortList;
    }

    public void setTimeSortList(List<String> timeSortList) {
        this.timeSortList = timeSortList;
    }
    public void addSortTimeMap(String timeKey,List<Map<String,String>> value){
        this.sortTimeMap.put(timeKey,value);
    }

    @Override
    public int compareTo(Object o) {
        return this.toString().compareTo(o.toString());
    }
}
