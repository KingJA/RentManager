package com.kingja.cardpackage.entiy;

/**
 * Description：TODO
 * Create Time：2016/8/23 9:57
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ChuZuWu_SetDeployStatus {

    /**
     * ResultCode : 0
     * ResultText : 操作成功
     * DataTypeCode : ChuZuWu_SetDeployStatus
     * TaskID :  1
     * Content : {"ROOMID":"YYYY"}
     */

    private int ResultCode;
    private String ResultText;
    private String DataTypeCode;
    private String TaskID;
    /**
     * ROOMID : YYYY
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
        private String ROOMID;

        public String getROOMID() {
            return ROOMID;
        }

        public void setROOMID(String ROOMID) {
            this.ROOMID = ROOMID;
        }
    }
}
