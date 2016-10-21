package com.kingja.cardpackage.entiy;

import java.io.Serializable;

/**
 * Description：TODO
 * Create Time：2016/9/7 9:58
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ShopBean implements Serializable{

    private String SHOPID;
    private String SHOPNAME;
    private String STARTTIME;
    private String ENDTIME;
    private String STARTWORKTIME;
    private String ENDWORKTIME;
    private int DEPLOYSTATUS;

    public int getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(int STATUS) {
        this.STATUS = STATUS;
    }

    private int STATUS;
    private String SHOPTYPE;
    private String STANDARDADDRCODE;
    private String ADDRESS;
    private String IDENTITYCARD;
    private String OWNERNAME;
    private String PHONE;
    private String JWHCODE;

    public String getSHOPID() {
        return SHOPID;
    }

    public void setSHOPID(String SHOPID) {
        this.SHOPID = SHOPID;
    }

    public String getSHOPNAME() {
        return SHOPNAME;
    }

    public void setSHOPNAME(String SHOPNAME) {
        this.SHOPNAME = SHOPNAME;
    }

    public String getSTARTTIME() {
        return STARTTIME;
    }

    public void setSTARTTIME(String STARTTIME) {
        this.STARTTIME = STARTTIME;
    }

    public String getENDTIME() {
        return ENDTIME;
    }

    public void setENDTIME(String ENDTIME) {
        this.ENDTIME = ENDTIME;
    }

    public String getSTARTWORKTIME() {
        return STARTWORKTIME;
    }

    public void setSTARTWORKTIME(String STARTWORKTIME) {
        this.STARTWORKTIME = STARTWORKTIME;
    }

    public String getENDWORKTIME() {
        return ENDWORKTIME;
    }

    public void setENDWORKTIME(String ENDWORKTIME) {
        this.ENDWORKTIME = ENDWORKTIME;
    }

    public int getDEPLOYSTATUS() {
        return DEPLOYSTATUS;
    }

    public void setDEPLOYSTATUS(int DEPLOYSTATUS) {
        this.DEPLOYSTATUS = DEPLOYSTATUS;
    }

    public String getSHOPTYPE() {
        return SHOPTYPE;
    }

    public void setSHOPTYPE(String SHOPTYPE) {
        this.SHOPTYPE = SHOPTYPE;
    }

    public String getSTANDARDADDRCODE() {
        return STANDARDADDRCODE;
    }

    public void setSTANDARDADDRCODE(String STANDARDADDRCODE) {
        this.STANDARDADDRCODE = STANDARDADDRCODE;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getIDENTITYCARD() {
        return IDENTITYCARD;
    }

    public void setIDENTITYCARD(String IDENTITYCARD) {
        this.IDENTITYCARD = IDENTITYCARD;
    }

    public String getOWNERNAME() {
        return OWNERNAME;
    }

    public void setOWNERNAME(String OWNERNAME) {
        this.OWNERNAME = OWNERNAME;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public String getJWHCODE() {
        return JWHCODE;
    }

    public void setJWHCODE(String JWHCODE) {
        this.JWHCODE = JWHCODE;
    }
}
