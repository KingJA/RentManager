package com.kingja.cardpackage.entiy;

/**
 * Description:TODO
 * Create Time:2017/7/18 15:42
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class User_LogInForShiMing {

    /**
     * ResultCode : 0
     * ResultText : 登录成功
     * DataTypeCode : USER_LOGINFORSHIMING
     * TaskID : 1
     * Content : {"TOKEN":"dea3b307-907e-4dcb-859b-c7b603f793b3","USERID":"E4A59000B14A414CBF4CD4F7CEBC5429",
     * "PHONE":"13736350001","CERTIFICATION":"2","IDENTITYCARD":"330326198710110734","RENALNAME":"潘志亮","SEX":"男",
     * "BIRTHDAY":"1987/10/11 0:00:00","HJADDRESS":"温州"}
     * StableVersion : 2.5.9
     */

    private int ResultCode;
    private String ResultText;
    private String DataTypeCode;
    private int TaskID;
    private ContentBean Content;
    private String StableVersion;

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

    public int getTaskID() {
        return TaskID;
    }

    public void setTaskID(int TaskID) {
        this.TaskID = TaskID;
    }

    public ContentBean getContent() {
        return Content;
    }

    public void setContent(ContentBean Content) {
        this.Content = Content;
    }

    public String getStableVersion() {
        return StableVersion;
    }

    public void setStableVersion(String StableVersion) {
        this.StableVersion = StableVersion;
    }

    public static class ContentBean {
        /**
         * TOKEN : dea3b307-907e-4dcb-859b-c7b603f793b3
         * USERID : E4A59000B14A414CBF4CD4F7CEBC5429
         * PHONE : 13736350001
         * CERTIFICATION : 2
         * IDENTITYCARD : 330326198710110734
         * RENALNAME : 潘志亮
         * SEX : 男
         * BIRTHDAY : 1987/10/11 0:00:00
         * HJADDRESS : 温州
         */

        private String TOKEN;
        private String USERID;
        private String PHONE;
        private String CERTIFICATION;
        private String IDENTITYCARD;
        private String RENALNAME;
        private String SEX;
        private String BIRTHDAY;
        private String HJADDRESS;

        public String getTOKEN() {
            return TOKEN;
        }

        public void setTOKEN(String TOKEN) {
            this.TOKEN = TOKEN;
        }

        public String getUSERID() {
            return USERID;
        }

        public void setUSERID(String USERID) {
            this.USERID = USERID;
        }

        public String getPHONE() {
            return PHONE;
        }

        public void setPHONE(String PHONE) {
            this.PHONE = PHONE;
        }

        public String getCERTIFICATION() {
            return CERTIFICATION;
        }

        public void setCERTIFICATION(String CERTIFICATION) {
            this.CERTIFICATION = CERTIFICATION;
        }

        public String getIDENTITYCARD() {
            return IDENTITYCARD;
        }

        public void setIDENTITYCARD(String IDENTITYCARD) {
            this.IDENTITYCARD = IDENTITYCARD;
        }

        public String getRENALNAME() {
            return RENALNAME;
        }

        public void setRENALNAME(String RENALNAME) {
            this.RENALNAME = RENALNAME;
        }

        public String getSEX() {
            return SEX;
        }

        public void setSEX(String SEX) {
            this.SEX = SEX;
        }

        public String getBIRTHDAY() {
            return BIRTHDAY;
        }

        public void setBIRTHDAY(String BIRTHDAY) {
            this.BIRTHDAY = BIRTHDAY;
        }

        public String getHJADDRESS() {
            return HJADDRESS;
        }

        public void setHJADDRESS(String HJADDRESS) {
            this.HJADDRESS = HJADDRESS;
        }
    }
}
