package com.tdr.wisdome.model;

/**
 * Created by Linus_Xie on 2016/8/24.
 */
public class CarInfo {

    private String bindingID;//绑定ID
    private String ecID;//车辆ID
    private String createTime;//生成时间
    private String plateNumber;//车牌号
    private String identity;//车辆所有者身份证号码
    private String phone;//车辆所有者手机号码
    private String code;//验证码
    private String auditState;
    private String status;//状态,布控状态，0:无布控，1：布控，2：撤控

    private String isRead;

    public String getBindingID() {
        return bindingID;
    }

    public void setBindingID(String bindingID) {
        this.bindingID = bindingID;
    }

    public String getEcID() {
        return ecID;
    }

    public void setEcID(String ecID) {
        this.ecID = ecID;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAuditState() {
        return auditState;
    }

    public void setAuditState(String auditState) {
        this.auditState = auditState;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }
}
