package com.kingja.cardpackage.entiy;

import java.io.Serializable;
import java.util.List;

/**
 * Description:TODO
 * Create Time:2017/7/6 10:09
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class User_HomePageApplication implements Serializable{

    /**
     * ResultCode : 0
     * ResultText : 操作成功
     * DataTypeCode : User_HomePageApplication
     * TaskID :  1
     * Content : [{"CARDCODE":"1001","CARDNAME":"房东申报","APISERVICE":"http://test.iotone.cn:12026/rentalestate
     * .asmx/RERequest","CARDLOGO":"1001-1","PACKAGECARDCODE":"1002"},{"CARDCODE":"1002","CARDNAME":"中介申报",
     * "APISERVICE":"http://test.iotone.cn:12026/rentalestate.asmx/RERequest","CARDLOGO":"1002-1",
     * "PACKAGECARDCODE":"1008"}]
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

    public static class ContentBean implements Serializable{
        /**
         * CARDCODE : 1001
         * CARDNAME : 房东申报
         * APISERVICE : http://test.iotone.cn:12026/rentalestate.asmx/RERequest
         * CARDLOGO : 1001-1
         * PACKAGECARDCODE : 1002
         */

        private String CARDCODE;
        private String CARDNAME;
        private String APISERVICE;
        private String CARDLOGO;
        private String PACKAGECARDCODE;

        public String getCARDCODE() {
            return CARDCODE;
        }

        public void setCARDCODE(String CARDCODE) {
            this.CARDCODE = CARDCODE;
        }

        public String getCARDNAME() {
            return CARDNAME;
        }

        public void setCARDNAME(String CARDNAME) {
            this.CARDNAME = CARDNAME;
        }

        public String getAPISERVICE() {
            return APISERVICE;
        }

        public void setAPISERVICE(String APISERVICE) {
            this.APISERVICE = APISERVICE;
        }

        public String getCARDLOGO() {
            return CARDLOGO;
        }

        public void setCARDLOGO(String CARDLOGO) {
            this.CARDLOGO = CARDLOGO;
        }

        public String getPACKAGECARDCODE() {
            return PACKAGECARDCODE;
        }

        public void setPACKAGECARDCODE(String PACKAGECARDCODE) {
            this.PACKAGECARDCODE = PACKAGECARDCODE;
        }
    }
}
