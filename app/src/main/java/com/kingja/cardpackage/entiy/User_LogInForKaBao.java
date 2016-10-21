package com.kingja.cardpackage.entiy;

/**
 * Description：TODO
 * Create Time：2016/8/24 13:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class User_LogInForKaBao {

    /**
     * ResultCode : 0
     * ResultText : 登录成功
     * DataTypeCode : User_LogInForKaBao
     * TaskID : 1
     * Content : {"USERID":"XXX"}
     */

    private int ResultCode;
    private String ResultText;
    private String DataTypeCode;
    private String TaskID;
    /**
     * USERID : XXX
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
        private String USERID;

        public String getUSERID() {
            return USERID;
        }

        public void setUSERID(String USERID) {
            this.USERID = USERID;
        }
    }
}
