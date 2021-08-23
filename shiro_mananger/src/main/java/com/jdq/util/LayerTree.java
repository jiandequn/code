package com.jdq.util;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/6/6
 * Time: 15:28
 * To change this template use File | Settings | File Templates.
 */
public class LayerTree {
    private String id;
    private String label;
    private Boolean checked;
    private Boolean spread;
    private List<LayerTree> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<LayerTree> getChildren() {
        return children;
    }

    public void setChildren(List<LayerTree> children) {
        this.children = children;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Boolean getSpread() {
        return spread;
    }

    public void setSpread(Boolean spread) {
        this.spread = spread;
    }

}
