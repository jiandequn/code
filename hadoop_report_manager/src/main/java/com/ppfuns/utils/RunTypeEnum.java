package com.ppfuns.utils;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/12/30
 * Time: 11:54
 * To change this template use File | Settings | File Templates.
 */
public enum RunTypeEnum {
    YEAR(1,"y","按年"),
    QUARTER(2,"q","按季度"),
    MONTH(3,"m","按月"),
    WEEK(4,"w","按周"),
    DAY(5,"d","按"),
    NO_TIME(6,"time","时间");

    private int id;
    private String name;
    private String text;

    RunTypeEnum(int id,String name,String text){
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
    public static RunTypeEnum get(int i){
        RunTypeEnum[] values = RunTypeEnum.values();
        for(RunTypeEnum runTypeEnum : values){
            if(runTypeEnum.getId() == i){
                return runTypeEnum;
            }
        }
        return RunTypeEnum.WEEK;
    }
    public static RunTypeEnum getByName(String name){
        RunTypeEnum[] values = RunTypeEnum.values();
        for(RunTypeEnum runTypeEnum : values){
            if(runTypeEnum.getName().equals(name)){
                return runTypeEnum;
            }
        }
        return RunTypeEnum.WEEK;
    }
}
