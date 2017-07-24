package com.kingja.cardpackage.entiy;

/**
 * Description:TODO
 * Create Time:2017/7/22 17:19
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class Common_SendValidCode {

    /**
     * ResultCode : 0
     * ResultText : 发送成功
     * DataTypeCode : Common_SendValidCode
     * TaskID :  1
     * Content : {"PHONENUM":"18857758345","ValidCodeSN":"123456"}
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
         * PHONENUM : 18857758345
         * ValidCodeSN : 123456
         */

        private String PHONENUM;
        private String ValidCodeSN;

        public String getPHONENUM() {
            return PHONENUM;
        }

        public void setPHONENUM(String PHONENUM) {
            this.PHONENUM = PHONENUM;
        }

        public String getValidCodeSN() {
            return ValidCodeSN;
        }

        public void setValidCodeSN(String ValidCodeSN) {
            this.ValidCodeSN = ValidCodeSN;
        }
    }
}
