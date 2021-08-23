package com.ppfuns.report.entity.base;

public class CountBean {
    private String name;
    private String value;
    private String type;
    private String format;
    private String text;

    public CountBean(){}
    public CountBean(String name, String value,String type,String format,String text) {
        this.name = name;
        this.value = value;
        this.type=type;
        this.format=format;
        this.text=text;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    /**
     * 值转换方法；重写该方法可以重新定义值
     * @param val
     * @return
     */
    public Object parseVal(Object val){
        return val;
    }
}
