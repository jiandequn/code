package com.ppfuns.report.entity.base;

public enum CountEnum {
    PLAYCOUNT("点播次数","playCount");
    private String name;
    private String value;

    CountEnum(String name, String value) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
