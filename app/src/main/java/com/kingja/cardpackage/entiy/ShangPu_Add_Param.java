package com.kingja.cardpackage.entiy;

import java.util.List;

/**
 * Description：TODO
 * Create Time：2016/9/2 13:04
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ShangPu_Add_Param {

    /**
     * TaskID : 1
     * SHOPID : 0123456789ABCDEF0123456789ABCDEF
     * SHOPTYPE : 1
     * SHOPNAME : XXX
     * STANDARDADDRCODE : XXX
     * ADDRESS : XXX
     * IDENTITYCARD : XXX
     * OWNERNAME : XXX
     * OWNERNEWUSERID : XXX
     * PHONE : XXX
     * XQCODE : XXX
     * PCSCODE : XXX
     * JWHCODE : XXX
     * LNG : 120.1234567
     * LAT : 27.1234567
     * PHOTOCOUNT : 2
     * PHOTOLIST : [{"LISTID":"0123456789ABCDEF0123456789ABCDEF","TAG":"门","IMAGE":"base64"},{"LISTID":"0123456789ABCDEF0123456789ABCDE0","TAG":"设备","IMAGE":"base64"}]
     */

    private String TaskID;
    private String SHOPID;
    private int SHOPTYPE;
    private String SHOPNAME;
    private String STANDARDADDRCODE;
    private String ADDRESS;
    private String IDENTITYCARD;
    private String OWNERNAME;
    private String OWNERNEWUSERID;
    private String PHONE;
    private String XQCODE;
    private String PCSCODE;
    private String JWHCODE;
    private double LNG;
    private double LAT;
    private int PHOTOCOUNT;
    /**
     * LISTID : 0123456789ABCDEF0123456789ABCDEF
     * TAG : 门
     * IMAGE : base64
     */

    private List<PHOTOLISTBean> PHOTOLIST;

    public String getTaskID() {
        return TaskID;
    }

    public void setTaskID(String TaskID) {
        this.TaskID = TaskID;
    }

    public String getSHOPID() {
        return SHOPID;
    }

    public void setSHOPID(String SHOPID) {
        this.SHOPID = SHOPID;
    }

    public int getSHOPTYPE() {
        return SHOPTYPE;
    }

    public void setSHOPTYPE(int SHOPTYPE) {
        this.SHOPTYPE = SHOPTYPE;
    }

    public String getSHOPNAME() {
        return SHOPNAME;
    }

    public void setSHOPNAME(String SHOPNAME) {
        this.SHOPNAME = SHOPNAME;
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

    public String getOWNERNEWUSERID() {
        return OWNERNEWUSERID;
    }

    public void setOWNERNEWUSERID(String OWNERNEWUSERID) {
        this.OWNERNEWUSERID = OWNERNEWUSERID;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public String getXQCODE() {
        return XQCODE;
    }

    public void setXQCODE(String XQCODE) {
        this.XQCODE = XQCODE;
    }

    public String getPCSCODE() {
        return PCSCODE;
    }

    public void setPCSCODE(String PCSCODE) {
        this.PCSCODE = PCSCODE;
    }

    public String getJWHCODE() {
        return JWHCODE;
    }

    public void setJWHCODE(String JWHCODE) {
        this.JWHCODE = JWHCODE;
    }

    public double getLNG() {
        return LNG;
    }

    public void setLNG(double LNG) {
        this.LNG = LNG;
    }

    public double getLAT() {
        return LAT;
    }

    public void setLAT(double LAT) {
        this.LAT = LAT;
    }

    public int getPHOTOCOUNT() {
        return PHOTOCOUNT;
    }

    public void setPHOTOCOUNT(int PHOTOCOUNT) {
        this.PHOTOCOUNT = PHOTOCOUNT;
    }

    public List<PHOTOLISTBean> getPHOTOLIST() {
        return PHOTOLIST;
    }

    public void setPHOTOLIST(List<PHOTOLISTBean> PHOTOLIST) {
        this.PHOTOLIST = PHOTOLIST;
    }

    public static class PHOTOLISTBean {
        private String LISTID;
        private String TAG;
        private String IMAGE;

        public String getLISTID() {
            return LISTID;
        }

        public void setLISTID(String LISTID) {
            this.LISTID = LISTID;
        }

        public String getTAG() {
            return TAG;
        }

        public void setTAG(String TAG) {
            this.TAG = TAG;
        }

        public String getIMAGE() {
            return IMAGE;
        }

        public void setIMAGE(String IMAGE) {
            this.IMAGE = IMAGE;
        }
    }
}
