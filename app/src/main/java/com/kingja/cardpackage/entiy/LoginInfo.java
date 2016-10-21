package com.kingja.cardpackage.entiy;

/**
 * Created by Linus_Xie on 2016/6/27.
 */
public class LoginInfo {

    private String TaskID;
    private String USERNAME;

    public String getREALNAME() {
        return REALNAME;
    }

    public void setREALNAME(String REALNAME) {
        this.REALNAME = REALNAME;
    }

    private String REALNAME;
    private String IDENTITYCARD;
    private String PHONENUM;
    private String SOFTVERSION;
    private int SOFTTYPE;
    private String CARDTYPE;
    private PhoneInfo PHONEINFO;

    public String getTaskID() {
        return TaskID;
    }

    public void setTaskID(String taskID) {
        TaskID = taskID;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getIDENTITYCARD() {
        return IDENTITYCARD;
    }

    public void setIDENTITYCARD(String IDENTITYCARD) {
        this.IDENTITYCARD = IDENTITYCARD;
    }

    public String getPHONENUM() {
        return PHONENUM;
    }

    public void setPHONENUM(String PHONENUM) {
        this.PHONENUM = PHONENUM;
    }

    public String getSOFTVERSION() {
        return SOFTVERSION;
    }

    public void setSOFTVERSION(String SOFTVERSION) {
        this.SOFTVERSION = SOFTVERSION;
    }

    public int getSOFTTYPE() {
        return SOFTTYPE;
    }

    public void setSOFTTYPE(int SOFTTYPE) {
        this.SOFTTYPE = SOFTTYPE;
    }

    public String getCARDTYPE() {
        return CARDTYPE;
    }

    public void setCARDTYPE(String CARDTYPE) {
        this.CARDTYPE = CARDTYPE;
    }

    public PhoneInfo getPHONEINFO() {
        return PHONEINFO;
    }

    public void setPHONEINFO(PhoneInfo PHONEINFO) {
        this.PHONEINFO = PHONEINFO;
    }
}
