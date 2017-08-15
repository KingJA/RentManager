package com.kingja.cardpackage.entiy;

/**
 * Description:TODO
 * Create Time:2017/7/25 13:09
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class User_MessageCountForShiMing {

    /**
     * ResultCode : 0
     * ResultText : 获取信息成功
     * DataTypeCode : User_MessageCountForShiMing
     * TaskID :  1
     * Content : {"UnReadCount":2}
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
         * UnReadCount : 2
         */

        private int UnReadCount;

        public int getUnReadCount() {
            return UnReadCount;
        }

        public void setUnReadCount(int UnReadCount) {
            this.UnReadCount = UnReadCount;
        }
    }
}
