package com.jshy.mr.model;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/11/12
 * Time: 15:29
 * To change this template use File | Settings | File Templates.
 */
public enum StatisticalMethodEnum {
    YEAR(1,"year","按年"),
    QUARTER(2,"quarter","按季度统计"),
    MONTH(3,"month","按月统计"),
    WEEK(4,"week","按周统计"),
    DAY(5,"day","按天统计"),
    NO_TIME(6,"null","总统计");

    private int id;
    private String name;
    private String text;

    StatisticalMethodEnum(int id,String name,String text){
        this.id=id;
        this.name = name;
        this.text = text;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    public static StatisticalMethodEnum get(int i){
         StatisticalMethodEnum[] values = StatisticalMethodEnum.values();
         for(StatisticalMethodEnum statisticalMethodEnum : values){
             if(statisticalMethodEnum.getId() == i){
                 return statisticalMethodEnum;
             }
         }
        return StatisticalMethodEnum.WEEK;
    }
    public static StatisticalMethodEnum getByName(String name){
        StatisticalMethodEnum[] values = StatisticalMethodEnum.values();
        for(StatisticalMethodEnum statisticalMethodEnum : values){
            if(statisticalMethodEnum.getName().equals(name)){
                return statisticalMethodEnum;
            }
        }
        return StatisticalMethodEnum.WEEK;
    }
}
