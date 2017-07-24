package com.kingja.cardpackage.entiy;

/**
 * Description:TODO
 * Create Time:2017/7/24 11:11
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class RegionForShiMing {

    /**
     * TaskID : 1
     * PHONE : 13805771234
     * PASSWORD : 123456
     * ValidCodeSN : 123456
     * ValidCode : 0123
     * SOFTVERSION : 1.1
     * SOFTTYPE : 1
     * PHONEINFO : {"SYSTEMTYPE":"ANDROID","SYSTEMVERSION":"5.1","DEVICEMODEL":"","DEVICEID":"XXXX",
     * "IMSI":"123456789012345","IMEI":"0123456789ABCDEF0123456789ABCDEF","ICCID":"12345678901234567890",
     * "WIFIMAC":"001122334455","BTMAC":"112233445566"}
     */

    private String TaskID;
    private String PHONE;
    private String PASSWORD;
    private String ValidCodeSN;
    private String ValidCode;
    private double SOFTVERSION;
    private int SOFTTYPE;
    private PhoneInfo PHONEINFO;

    public String getTaskID() {
        return TaskID;
    }

    public void setTaskID(String TaskID) {
        this.TaskID = TaskID;
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

    public String getValidCodeSN() {
        return ValidCodeSN;
    }

    public void setValidCodeSN(String ValidCodeSN) {
        this.ValidCodeSN = ValidCodeSN;
    }

    public String getValidCode() {
        return ValidCode;
    }

    public void setValidCode(String ValidCode) {
        this.ValidCode = ValidCode;
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
