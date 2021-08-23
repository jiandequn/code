package com.ppfuns.report.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * (AlbumContentType)表实体类
 *
 * @author jdq
 * @since 2021-07-07 15:50:01
 */
@SuppressWarnings("serial")
public class AlbumContentType extends Model<AlbumContentType> {

    private String contentType;

    private String contentTypeName;


    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentTypeName() {
        return contentTypeName;
    }

    public void setContentTypeName(String contentTypeName) {
        this.contentTypeName = contentTypeName;
    }

}
