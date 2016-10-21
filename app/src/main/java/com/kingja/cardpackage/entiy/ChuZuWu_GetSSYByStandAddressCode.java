package com.kingja.cardpackage.entiy;

/**
 * Description：TODO
 * Create Time：2016/9/2 14:30
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ChuZuWu_GetSSYByStandAddressCode {

    /**
     * ResultCode : 0
     * ResultText : 操作成功
     * DataTypeCode : ChuZuWu_GetSSYByStandAddressCode
     * TaskID :  1
     * Content : {"STANDARDADDRCODE":"XXX","IDENTITYCARD":"XXX","OWNERNAME":"XXX","PHONE":"XXX","JWHCODE":"XXX"}
     */

    private int ResultCode;
    private String ResultText;
    private String DataTypeCode;
    private String TaskID;
    /**
     * STANDARDADDRCODE : XXX
     * IDENTITYCARD : XXX
     * OWNERNAME : XXX
     * PHONE : XXX
     * JWHCODE : XXX
     */

    private ContentBean Content;

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

    public ContentBean getContent() {
        return Content;
    }

    public void setContent(ContentBean Content) {
        this.Content = Content;
    }

    public static class ContentBean {
        private String STANDARDADDRCODE;
        private String IDENTITYCARD;
        private String OWNERNAME;
        private String PHONE;
        private String JWHCODE;

        public String getSTANDARDADDRCODE() {
            return STANDARDADDRCODE;
        }

        public void setSTANDARDADDRCODE(String STANDARDADDRCODE) {
            this.STANDARDADDRCODE = STANDARDADDRCODE;
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
