package com.kingja.cardpackage.entiy;

/**
 * Description:TODO
 * Create Time:2017/7/27 15:21
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class User_DetailForShiMing {

    /**
     * ResultCode : 0
     * ResultText : 修改成功
     * DataTypeCode : User_ DetailForShiMing
     * TaskID :  1
     * Content : {"IDENTITYCARD":"330302199701065462","RENALNAME":"张三","PHONE":"13805771234","SEX":"男",
     * "BIRTHER":"1997/1/6","HJADDRESS":"温州市鹿城区五马街道人民路23号"}
     */

    private int ResultCode;
    private String ResultText;
    private String DataTypeCode;
    private String TaskID;
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
        /**
         * IDENTITYCARD : 330302199701065462
         * RENALNAME : 张三
         * PHONE : 13805771234
         * SEX : 男
         * BIRTHER : 1997/1/6
         * HJADDRESS : 温州市鹿城区五马街道人民路23号
         */

        private String IDENTITYCARD;
        private String RENALNAME;
        private String PHONE;
        private String SEX;
        private String BIRTHER;
        private String HJADDRESS;

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

        public String getPHONE() {
            return PHONE;
        }

        public void setPHONE(String PHONE) {
            this.PHONE = PHONE;
        }

        public String getSEX() {
            return SEX;
        }

        public void setSEX(String SEX) {
            this.SEX = SEX;
        }

        public String getBIRTHER() {
            return BIRTHER;
        }

        public void setBIRTHER(String BIRTHER) {
            this.BIRTHER = BIRTHER;
        }

        public String getHJADDRESS() {
            return HJADDRESS;
        }

        public void setHJADDRESS(String HJADDRESS) {
            this.HJADDRESS = HJADDRESS;
        }
    }
}
