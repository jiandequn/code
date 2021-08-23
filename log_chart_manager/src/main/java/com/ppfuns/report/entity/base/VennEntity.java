package com.ppfuns.report.entity.base;

import java.util.Arrays;
import java.util.List;

/**
 * 韦恩图
 * Created by Administrator on 2020/9/24.
 */
public class VennEntity {
    private List<String> sets;
    private Long value;
    private String name;

    public List<String> getSets() {
        return Arrays.asList(name.split(","));
    }

    public void setSets(List<String> sets) {
        this.sets = sets;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
