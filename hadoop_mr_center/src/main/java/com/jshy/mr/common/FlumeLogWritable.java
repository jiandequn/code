package com.jshy.mr.common;

import com.jshy.mr.utils.CommonUtils;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/10/11
 * Time: 14:27
 * To change this template use File | Settings | File Templates.
 */
public class FlumeLogWritable implements WritableComparable<FlumeLogWritable> {
    private String eventsType = "";
    private String mac = "";
    private String sn = "";
    private String userId = "";
    private String userType = "";
    private String parentColumnId = "";
    private String columnId = "";
    private String contentId = "";
    private String contentType = "";
    private String operateType = "";
    private String timePosition = "";
    private String createTime = "";
    private String nowSpm = "";
    private String afterSpm = "";
    private String pos = "";
    private String posName = "";
    private String keyWord = "";
    private String code = "";
    private String message = "";
    private String productId = "";
    private String isEffective = "";
    private String resourceId = "";
    private String videoId="";

    public String getEventsType() {
        return eventsType;
    }

    public void setEventsType(String eventsType) {
        this.eventsType = eventsType;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getTimePosition() {
        return timePosition;
    }

    public void setTimePosition(String timePosition) {
        this.timePosition = timePosition;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getNowSpm() {
        return nowSpm;
    }

    public void setNowSpm(String nowSpm) {
        this.nowSpm = nowSpm;
    }

    public String getAfterSpm() {
        return afterSpm;
    }

    public void setAfterSpm(String afterSpm) {
        this.afterSpm = afterSpm;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getPosName() {
        return posName;
    }

    public void setPosName(String posName) {
        this.posName = posName;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(String isEffective) {
        this.isEffective = isEffective;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    @Override
    public int compareTo(FlumeLogWritable o) {
        return this.toString().compareTo(o.toString());
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(eventsType);
        dataOutput.writeUTF(mac);
        dataOutput.writeUTF(sn);
        dataOutput.writeUTF(userId);
        dataOutput.writeUTF(userType);
        dataOutput.writeUTF(parentColumnId);
        dataOutput.writeUTF(columnId);
        dataOutput.writeUTF(contentId);
        dataOutput.writeUTF(contentType);
        dataOutput.writeUTF(operateType);
        dataOutput.writeUTF(timePosition);
        dataOutput.writeUTF(createTime);
        dataOutput.writeUTF(nowSpm);
        dataOutput.writeUTF(afterSpm);
        dataOutput.writeUTF(pos);
        dataOutput.writeUTF(posName);
        dataOutput.writeUTF(keyWord);
        dataOutput.writeUTF(code);
        dataOutput.writeUTF(message);
        dataOutput.writeUTF(productId);
        dataOutput.writeUTF(isEffective);
        dataOutput.writeUTF(resourceId);
        dataOutput.writeUTF(videoId);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        eventsType=dataInput.readUTF();
        mac=dataInput.readUTF();
        sn=dataInput.readUTF();
        userId=dataInput.readUTF();
        userType=dataInput.readUTF();
        parentColumnId=dataInput.readUTF();
        columnId=dataInput.readUTF();
        contentId=dataInput.readUTF();
        contentType=dataInput.readUTF();
        operateType=dataInput.readUTF();
        timePosition=dataInput.readUTF();
        createTime=dataInput.readUTF();
        nowSpm=dataInput.readUTF();
        afterSpm=dataInput.readUTF();
        pos=dataInput.readUTF();
        posName=dataInput.readUTF();
        keyWord=dataInput.readUTF();
        code=dataInput.readUTF();
        message=dataInput.readUTF();
        productId = dataInput.readUTF();
        isEffective = dataInput.readUTF();
        resourceId= dataInput.readUTF();
        videoId = dataInput.readUTF();
    }

    @Override
    public String toString() {
        return CommonUtils.toStringFormat( eventsType, mac,sn, userId,userType, parentColumnId, columnId,
                contentId, contentType, operateType,timePosition, createTime,
                nowSpm, afterSpm, pos, posName,keyWord,code,message,productId,isEffective,resourceId,videoId);
    }
}
