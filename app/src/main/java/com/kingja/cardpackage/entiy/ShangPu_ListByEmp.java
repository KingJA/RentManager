package com.kingja.cardpackage.entiy;

import java.util.List;

/**
 * Description：TODO
 * Create Time：2016/10/18 10:15
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ShangPu_ListByEmp {

    /**
     * ResultCode : 0
     * ResultText : 操作成功
     * DataTypeCode : ShangPu_ListByEmp
     * TaskID :  1
     * Content : [{"SHOPID":"XXX","SHOPNAME":"商铺1","STARTWORKTIME":"09:00:00","ENDWORKTIME":"21:00:00","DEPLOYSTATUS":1,"SHOPTYPE":"XXX","STANDARDADDRCODE":"XXX","ADDRESS":"XXX","IDENTITYCARD":"XXX","OWNERNAME":"XXX","PHONE":"XXX","JWHCODE":"XXX"}]
     */

    private int ResultCode;
    private String ResultText;
    private String DataTypeCode;
    private String TaskID;
    /**
     * SHOPID : XXX
     * SHOPNAME : 商铺1
     * STARTWORKTIME : 09:00:00
     * ENDWORKTIME : 21:00:00
     * DEPLOYSTATUS : 1
     * SHOPTYPE : XXX
     * STANDARDADDRCODE : XXX
     * ADDRESS : XXX
     * IDENTITYCARD : XXX
     * OWNERNAME : XXX
     * PHONE : XXX
     * JWHCODE : XXX
     */

    private List<ContentBean> Content;

    public int getResultCode() {
        return ResultCode;
    }

    public void setResultCode(int ResultCode) {
        this.ResultCode = ResultCode;
    }

    public String getResultText() {
        return ResultText;
    }

    public void setResultText(String ResultText) {
        this.ResultText = ResultText;
    }

    public String getDataTypeCode() {
        return DataTypeCode;
    }

    public void setDataTypeCode(String DataTypeCode) {
        this.DataTypeCode = DataTypeCode;
    }

    public String getTaskID() {
        return TaskID;
    }

    public void setTaskID(String TaskID) {
        this.TaskID = TaskID;
    }

    public List<ContentBean> getContent() {
        return Content;
    }

    public void setContent(List<ContentBean> Content) {
        this.Content = Content;
    }

    public static class ContentBean {
        private String SHOPID;
        private String SHOPNAME;
        private String STARTWORKTIME;
        private String ENDWORKTIME;
        private int DEPLOYSTATUS;
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
}
