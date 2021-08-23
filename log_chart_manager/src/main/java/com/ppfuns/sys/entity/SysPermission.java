package com.ppfuns.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author jian.dq
 * @since 2020-03-09
 */
@TableName("sys_permission")
public class SysPermission implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;

    /**
     * 菜单编号
     */
    private String code;

    /**
     * 菜单图标名称
     */
    private String icon;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 父菜单id
     */
    private Integer parentId;

    /**
     * 父菜单id
     */
    private String parentIds;

    /**
     * 菜单排序
     */
    private Integer seq;

    /**
     * 权限分类（0 菜单；1 功能）
     */
    private Integer type;

    /**
     * 描述
     */
    private String descpt;

    /**
     * 菜单url
     */
    private String path;

    /**
     * 添加时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
    @TableField(exist = false)
    private boolean checked=false;
    @TableField(exist = false)
    private boolean is_open;
    @TableField(exist = false)
    private boolean is_show=true;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }
    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    public String getDescpt() {
        return descpt;
    }

    public void setDescpt(String descpt) {
        this.descpt = descpt;
    }
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean getIs_open() {
        return is_open;
    }

    public void setIs_open(boolean is_open) {
        this.is_open = is_open;
    }

    public boolean getIs_show() {
        return is_show;
    }

    public void setIs_show(boolean is_show) {
        this.is_show = is_show;
    }

    @Override
    public String toString() {
        return "Permission{" +
            "code=" + code +
            ", icon=" + icon +
            ", name=" + name +
            ", parentId=" + parentId +
            ", parentIds=" + parentIds +
            ", seq=" + seq +
            ", type=" + type +
            ", descpt=" + descpt +
            ", path=" + path +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
        "}";
    }
}
