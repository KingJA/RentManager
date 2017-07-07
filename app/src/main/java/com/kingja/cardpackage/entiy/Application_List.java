package com.kingja.cardpackage.entiy;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2017/7/6 14:14
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class Application_List {

    /**
     * ResultCode : 0
     * ResultText : 操作成功
     * DataTypeCode :  Application_List
     * TaskID :  1
     * Content : [{"CARDTYPE":1,"CARDTYPENAME":"人员申报","CARDCOUNT":2,"CARDPROPERTY":[{"CARDCODE":"1001",
     * "CARDNAME":"房东申报","APISERVICE":"http://test.iotone.cn:12026/rentalestate.asmx/RERequest","CARDLOGO":"1001-1",
     * "PACKAGECARDCODE":"1002","ISHOMEAPP":1},{"CARDCODE":"1001","CARDNAME":"房东申报","APISERVICE":"http://test.iotone
     * .cn:12026/rentalestate.asmx/RERequest","CARDLOGO":"1001-1","PACKAGECARDCODE":"1002","ISHOMEAPP":1}]}]
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

    public static class ContentBean {
        /**
         * CARDTYPE : 1
         * CARDTYPENAME : 人员申报
         * CARDCOUNT : 2
         * CARDPROPERTY : [{"CARDCODE":"1001","CARDNAME":"房东申报","APISERVICE":"http://test.iotone
         * .cn:12026/rentalestate.asmx/RERequest","CARDLOGO":"1001-1","PACKAGECARDCODE":"1002","ISHOMEAPP":1},
         * {"CARDCODE":"1001","CARDNAME":"房东申报","APISERVICE":"http://test.iotone.cn:12026/rentalestate
         * .asmx/RERequest","CARDLOGO":"1001-1","PACKAGECARDCODE":"1002","ISHOMEAPP":1}]
         */

        private int CARDTYPE;
        private String CARDTYPENAME;
        private int CARDCOUNT;
        private List<CARDPROPERTYBean> CARDPROPERTY;

        public int getCARDTYPE() {
            return CARDTYPE;
        }

        public void setCARDTYPE(int CARDTYPE) {
            this.CARDTYPE = CARDTYPE;
        }

        public String getCARDTYPENAME() {
            return CARDTYPENAME;
        }

        public void setCARDTYPENAME(String CARDTYPENAME) {
            this.CARDTYPENAME = CARDTYPENAME;
        }

        public int getCARDCOUNT() {
            return CARDCOUNT;
        }

        public void setCARDCOUNT(int CARDCOUNT) {
            this.CARDCOUNT = CARDCOUNT;
        }

        public List<CARDPROPERTYBean> getCARDPROPERTY() {
            return CARDPROPERTY;
        }

        public void setCARDPROPERTY(List<CARDPROPERTYBean> CARDPROPERTY) {
            this.CARDPROPERTY = CARDPROPERTY;
        }

        public static class CARDPROPERTYBean {
            /**
             * CARDCODE : 1001
             * CARDNAME : 房东申报
             * APISERVICE : http://test.iotone.cn:12026/rentalestate.asmx/RERequest
             * CARDLOGO : 1001-1
             * PACKAGECARDCODE : 1002
             * ISHOMEAPP : 1
             */

            private String CARDCODE;
            private String CARDNAME;
            private String APISERVICE;
            private String CARDLOGO;
            private String PACKAGECARDCODE;
            private int ISHOMEAPP;

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

            public int getISHOMEAPP() {
                return ISHOMEAPP;
            }

            public void setISHOMEAPP(int ISHOMEAPP) {
                this.ISHOMEAPP = ISHOMEAPP;
            }
        }
    }
}
