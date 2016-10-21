package com.tdr.wisdome.model;

/**
 * 车辆报警适配器
 * Created by Linus_Xie on 2016/8/24.
 */
public class CarAlarmInfo {

    /**
     * LISTID : E9E11112-27A9-64EB-2A4D-538FA436ECEC
     * PLATENUMBER : YC000008
     * THEFTNO : null
     * WEICHATID : null
     * DEVICEID : 135991
     * MONITORTIME : 2015-10-26 15:07:29
     * MESSAGE : 您的电动车YC000008于2015-10-26 15:07:29经过基站胜利西路-香粉弄(135991),请确认。
     * MSGTYPE : null
     * ISSUCCESS : null
     * PUSHTIME : null
     * FAILREASON : null
     * RESERVED1 : null
     * RESERVED2 : null
     * RESERVED3 : null
     * IsRead : 0
     */

    private String listId;
    private String plateNumber;//车牌号
    private String theftNo;
    private String weichatId;
    private String deviceId;
    private String monitorTime;//产生轨迹时间
    private String message;//信息内容
    private String msgType;
    private String isSuccess;
    private String pushTime;
    private String failReason;
    private String reserved1;
    private String reserved2;
    private String reserved3;
    private String IsRead;//是否已读

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getTheftNo() {
        return theftNo;
    }

    public void setTheftNo(String theftNo) {
        this.theftNo = theftNo;
    }

    public String getWeichatId() {
        return weichatId;
    }

    public void setWeichatId(String weichatId) {
        this.weichatId = weichatId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getMonitorTime() {
        return monitorTime;
    }

    public void setMonitorTime(String monitorTime) {
        this.monitorTime = monitorTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getPushTime() {
        return pushTime;
    }

    public void setPushTime(String pushTime) {
        this.pushTime = pushTime;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public String getReserved1() {
        return reserved1;
    }

    public void setReserved1(String reserved1) {
        this.reserved1 = reserved1;
    }

    public String getReserved2() {
        return reserved2;
    }

    public void setReserved2(String reserved2) {
        this.reserved2 = reserved2;
    }

    public String getReserved3() {
        return reserved3;
    }

    public void setReserved3(String reserved3) {
        this.reserved3 = reserved3;
    }

    public String getIsRead() {
        return IsRead;
    }

    public void setIsRead(String isRead) {
        IsRead = isRead;
    }
}
