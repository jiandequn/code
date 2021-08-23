package com.ppfuns.report.entity.base;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SerieBean {
    public final static String COLUMN="column";
    public final static String SPLINE="spline";
    public final static String LINE="line";
    public final static String AREA="area";
    private String name;
    private String type="column"; //column|spline
    private int yAxis;
    private List data;
    private String valueSuffix;
    Map<String,String> toolTipMap;
    public SerieBean(){}
    public SerieBean(String name, String type, int yAxis, String valueSuffix ,List data) {
        this.name = name;
        this.type = type;
        this.yAxis = yAxis;
        this.data = data;
        this.valueSuffix = valueSuffix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getyAxis() {
        return yAxis;
    }

    public void setyAxis(int yAxis) {
        this.yAxis = yAxis;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }

    public String getValueSuffix() {
        return valueSuffix;
    }

    public void setValueSuffix(String valueSuffix) {
        this.valueSuffix = valueSuffix;
    }
    public Map<String,Object> getSerieMap(){
        Map<String,Object> serie = new HashMap<>();
        serie.put("name",name);
        serie.put("type",type);
        if(yAxis > 0){
            serie.put("yAxis",yAxis);
        }
        if(StringUtils.isNotEmpty(valueSuffix)){
            Map<String,String> toolTipMap = new HashMap<>();
            toolTipMap.put("valueSuffix",valueSuffix);
            serie.put("tooltip",toolTipMap);
        }
        serie.put("data",data);
        return serie;
    }
}
