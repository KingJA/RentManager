package com.tdr.wisdome.model;

import java.io.Serializable;

/**
 * 亲情关爱对象类
 * Created by Linus_Xie on 2016/8/16.
 */
public class OlderInfo implements Serializable {

    private String careNumber;//老人ID
    private String customerName;//老人姓名
    private String customerIdCard;//老人身份证
    private String customMobile;//老人手机号码
    private String customerAddress;//地址
    private String targetType;//对象类型(0 老人 1小孩 2宠物)
    private String movementArea;//活动中心地址
    private String centrePointLng;//中心点经度
    private String centrePointLat;//中心点维度
    private String radius;//预警距离
    private String customerPhoto;//半身照
    private String customerPhotoId;//半身照ID
    private String healthCondition;//病情描述
    private String emtnotice;//备注
    private String personType;//当前用户类型, 比如登记人=0， 普通关联人=1
    private String isRegedit;//默认1，0代表未登记

    public String getIsRegedit() {
        return isRegedit;
    }

    public void setIsRegedit(String isRegedit) {
        this.isRegedit = isRegedit;
    }

    public String getCareNumber() {
        return careNumber;
    }

    public void setCareNumber(String careNumber) {
        this.careNumber = careNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerIdCard() {
        return customerIdCard;
    }

    public void setCustomerIdCard(String customerIdCard) {
        this.customerIdCard = customerIdCard;
    }

    public String getCustomMobile() {
        return customMobile;
    }

    public void setCustomMobile(String customMobile) {
        this.customMobile = customMobile;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getMovementArea() {
        return movementArea;
    }

    public void setMovementArea(String movementArea) {
        this.movementArea = movementArea;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public String getCentrePointLng() {
        return centrePointLng;
    }

    public void setCentrePointLng(String centrePointLng) {
        this.centrePointLng = centrePointLng;
    }

    public String getCentrePointLat() {
        return centrePointLat;
    }

    public void setCentrePointLat(String centrePointLat) {
        this.centrePointLat = centrePointLat;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getCustomerPhoto() {
        return customerPhoto;
    }

    public void setCustomerPhoto(String customerPhoto) {
        this.customerPhoto = customerPhoto;
    }

    public String getCustomerPhotoId() {
        return customerPhotoId;
    }

    public void setCustomerPhotoId(String customerPhotoId) {
        this.customerPhotoId = customerPhotoId;
    }

    public String getHealthCondition() {
        return healthCondition;
    }

    public void setHealthCondition(String healthCondition) {
        this.healthCondition = healthCondition;
    }

    public String getEmtnotice() {
        return emtnotice;
    }

    public void setEmtnotice(String emtnotice) {
        this.emtnotice = emtnotice;
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }
}
