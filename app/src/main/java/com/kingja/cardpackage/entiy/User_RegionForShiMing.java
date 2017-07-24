package com.kingja.cardpackage.entiy;

/**
 * Description:TODO
 * Create Time:2017/7/24 11:13
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class User_RegionForShiMing {

    /**
     * ResultCode : 0
     * ResultText : 注册成功
     * DataTypeCode : User_RegionForShiMing
     * TaskID :  1
     * Content : {"TOKEN":"XXX","USERID":"XXX","PHONE":"13805771234"}
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
         * TOKEN : XXX
         * USERID : XXX
         * PHONE : 13805771234
         */

        private String TOKEN;
        private String USERID;
        private String PHONE;

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
    }
}
