package com.kingja.cardpackage.entiy;

/**
 * Description:TODO
 * Create Time:2017/7/18 14:39
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class LogInForShiMing {

    /**
     * TaskID : 1
     * CityCode : 330300
     * PHONE : 18857758345
     * PASSWORD : 123456
     * SOFTVERSION : 1.1
     * SOFTTYPE : 2
     * PHONEINFO : {"SYSTEMTYPE":"ANDROID","SYSTEMVERSION":"5.1","DEVICEMODEL":"","DEVICEID":"XXXXX",
     * "IMSI":"123456789012345","IMEI":"0123456789ABCDEF0123456789ABCDEF","ICCID":"12345678901234567890",
     * "WIFIMAC":"001122334455","BTMAC":"112233445566","CHANNELID":"001122334455"}
     */

    private String TaskID;
    private String CityCode;
    private String PHONE;
    private String PASSWORD;
    private double SOFTVERSION;
    private int SOFTTYPE;
    private PhoneInfo PHONEINFO;

    public String getTaskID() {
        return TaskID;
    }

    public void setTaskID(String TaskID) {
        this.TaskID = TaskID;
    }

    public String getCityCode() {
        return CityCode;
    }

    public void setCityCode(String CityCode) {
        this.CityCode = CityCode;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public double getSOFTVERSION() {
        return SOFTVERSION;
    }

    public void setSOFTVERSION(double SOFTVERSION) {
        this.SOFTVERSION = SOFTVERSION;
    }

    public int getSOFTTYPE() {
        return SOFTTYPE;
    }

    public void setSOFTTYPE(int SOFTTYPE) {
        this.SOFTTYPE = SOFTTYPE;
    }

    public PhoneInfo getPHONEINFO() {
        return PHONEINFO;
    }

    public void setPHONEINFO(PhoneInfo PHONEINFO) {
        this.PHONEINFO = PHONEINFO;
    }


}
