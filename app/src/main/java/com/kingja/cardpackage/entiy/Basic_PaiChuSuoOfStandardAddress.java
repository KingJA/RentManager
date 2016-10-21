package com.kingja.cardpackage.entiy;

/**
 * Description：TODO
 * Create Time：2016/10/9 10:50
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class Basic_PaiChuSuoOfStandardAddress {

    /**
     * ResultCode : 0
     * ResultText : 操作成功
     * DataTypeCode : Basic_PaiChuSuoOfStandardAddress
     * TaskID :  1
     * Content : {"geocode":"802CD27246A8440F93CE0DB97B9DD8AB","sszdyjgazzjg":"330327580000","sszdyjxzqh":"330327124"}
     */

    private int ResultCode;
    private String ResultText;
    private String DataTypeCode;
    private String TaskID;
    /**
     * geocode : 802CD27246A8440F93CE0DB97B9DD8AB
     * sszdyjgazzjg : 330327580000
     * sszdyjxzqh : 330327124
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
        private String geocode;
        private String sszdyjgazzjg;
        private String sszdyjxzqh;

        public String getGeocode() {
            return geocode;
        }

        public void setGeocode(String geocode) {
            this.geocode = geocode;
        }

        public String getSszdyjgazzjg() {
            return sszdyjgazzjg;
        }

        public void setSszdyjgazzjg(String sszdyjgazzjg) {
            this.sszdyjgazzjg = sszdyjgazzjg;
        }

        public String getSszdyjxzqh() {
            return sszdyjxzqh;
        }

        public void setSszdyjxzqh(String sszdyjxzqh) {
            this.sszdyjxzqh = sszdyjxzqh;
        }
    }
}
