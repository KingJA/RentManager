package com.kingja.cardpackage.entiy;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2017/5/15 15:58
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ChuZuWu_AgencySelfReportingList {

    /**
     * ResultCode : 0
     * ResultText : 操作成功
     * DataTypeCode : ChuZuWu_AgencySelfReportingList
     * TaskID :  1
     * Content : [{"LISTID":"0123456789ABCDEF0123456789ABCDEF","NAME":"张三","IDENTITYCARD":"330303199909091234",
     * "HEIGHT":180,"PHONE":"13805771234","ADDRESS":"永安江路22号","ROOMNO":"303","INTIME":"2016-4-13 10:13:23"},
     * {"LISTID":"0123456789ABCDEF0123456789ABCDE0","NAME":"黄荣智","IDENTITYCARD":"330381198306071811","HEIGHT":180,
     * "PHONE":"18857758345","ADDRESS":"永安江路22号","ROOMNO":"303","INTIME":"2016-4-13 10:13:23"}]
     */

    private int ResultCode;
    private String ResultText;
    private String DataTypeCode;
    private String TaskID;
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
        /**
         * LISTID : 0123456789ABCDEF0123456789ABCDEF
         * NAME : 张三
         * IDENTITYCARD : 330303199909091234
         * HEIGHT : 180
         * PHONE : 13805771234
         * ADDRESS : 永安江路22号
         * ROOMNO : 303
         * INTIME : 2016-4-13 10:13:23
         */

        private String XQCODE;
        private String PCSCODE;
        private String JWHCODE;
        private String SSQYMC;
        private String LISTID;
        private String NAME;
        private String IDENTITYCARD;
        private int HEIGHT;
        private String PHONE;
        private String ADDRESS;
        private String ROOMNO;
        private String INTIME;

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

        public String getSSQYMC() {
            return SSQYMC;
        }

        public void setSSQYMC(String SSQYMC) {
            this.SSQYMC = SSQYMC;
        }

        public String getLISTID() {
            return LISTID;
        }

        public void setLISTID(String LISTID) {
            this.LISTID = LISTID;
        }

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }

        public String getIDENTITYCARD() {
            return IDENTITYCARD;
        }

        public void setIDENTITYCARD(String IDENTITYCARD) {
            this.IDENTITYCARD = IDENTITYCARD;
        }

        public int getHEIGHT() {
            return HEIGHT;
        }

        public void setHEIGHT(int HEIGHT) {
            this.HEIGHT = HEIGHT;
        }

        public String getPHONE() {
            return PHONE;
        }

        public void setPHONE(String PHONE) {
            this.PHONE = PHONE;
        }

        public String getADDRESS() {
            return ADDRESS;
        }

        public void setADDRESS(String ADDRESS) {
            this.ADDRESS = ADDRESS;
        }

        public String getROOMNO() {
            return ROOMNO;
        }

        public void setROOMNO(String ROOMNO) {
            this.ROOMNO = ROOMNO;
        }

        public String getINTIME() {
            return INTIME;
        }

        public void setINTIME(String INTIME) {
            this.INTIME = INTIME;
        }
    }
}
