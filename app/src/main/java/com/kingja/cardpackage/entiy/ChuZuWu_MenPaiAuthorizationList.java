package com.kingja.cardpackage.entiy;

import java.util.List;

/**
 * Description：TODO
 * Create Time：2016/8/15 16:56
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ChuZuWu_MenPaiAuthorizationList {


    /**
     * ResultCode : 0
     * ResultText : 查询成功
     * DataTypeCode : CHUZUWU_MENPAIAUTHORIZATIONLIST
     * TaskID : 1
     * Content : {"HOUSEID":"0a6f98b1ca6946af8aada366d7cc21d6","PERSONNELINFOLIST":[{"LISTID":"96D489EC36834817AF8A705ADB8C2000","NAME":"hello","IDENTITYCARD":"123456789000283237","PHONENUM":"","ROOMID":"d05653e2ba924ce8a2fe95749d1ee344","INTIME":"0001-01-01 00:00:00","OUTTIME":"0001-01-01 00:00:00","CARDID":"E12345678BBB1100","CARDTYPE":9},{"LISTID":"96D489EC36834817AF8A705ADB8C23F1","NAME":"静静","IDENTITYCARD":"123456789100283237","PHONENUM":"","ROOMID":"d05653e2ba924ce8a2fe95749d1ee344","INTIME":"0001-01-01 00:00:00","OUTTIME":"0001-01-01 00:00:00","CARDID":"1234","CARDTYPE":9}]}
     * StableVersion : 1.26
     */

    private int ResultCode;
    private String ResultText;
    private String DataTypeCode;
    private int TaskID;
    /**
     * HOUSEID : 0a6f98b1ca6946af8aada366d7cc21d6
     * PERSONNELINFOLIST : [{"LISTID":"96D489EC36834817AF8A705ADB8C2000","NAME":"hello","IDENTITYCARD":"123456789000283237","PHONENUM":"","ROOMID":"d05653e2ba924ce8a2fe95749d1ee344","INTIME":"0001-01-01 00:00:00","OUTTIME":"0001-01-01 00:00:00","CARDID":"E12345678BBB1100","CARDTYPE":9},{"LISTID":"96D489EC36834817AF8A705ADB8C23F1","NAME":"静静","IDENTITYCARD":"123456789100283237","PHONENUM":"","ROOMID":"d05653e2ba924ce8a2fe95749d1ee344","INTIME":"0001-01-01 00:00:00","OUTTIME":"0001-01-01 00:00:00","CARDID":"1234","CARDTYPE":9}]
     */

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
        private String HOUSEID;
        /**
         * LISTID : 96D489EC36834817AF8A705ADB8C2000
         * NAME : hello
         * IDENTITYCARD : 123456789000283237
         * PHONENUM :
         * ROOMID : d05653e2ba924ce8a2fe95749d1ee344
         * INTIME : 0001-01-01 00:00:00
         * OUTTIME : 0001-01-01 00:00:00
         * CARDID : E12345678BBB1100
         * CARDTYPE : 9
         */

        private List<PERSONNELINFOLISTBean> PERSONNELINFOLIST;

        public String getHOUSEID() {
            return HOUSEID;
        }

        public void setHOUSEID(String HOUSEID) {
            this.HOUSEID = HOUSEID;
        }

        public List<PERSONNELINFOLISTBean> getPERSONNELINFOLIST() {
            return PERSONNELINFOLIST;
        }

        public void setPERSONNELINFOLIST(List<PERSONNELINFOLISTBean> PERSONNELINFOLIST) {
            this.PERSONNELINFOLIST = PERSONNELINFOLIST;
        }

        public static class PERSONNELINFOLISTBean {
            private String LISTID;
            private String NAME;
            private String IDENTITYCARD;
            private String PHONENUM;
            private String ROOMID;
            private String INTIME;
            private String OUTTIME;
            private String CARDID;
            private int CARDTYPE;

            public String getLISTID() {
                return LISTID;
            }

            public void setLISTID(String LISTID) {
                this.LISTID = LISTID;
            }

            public String getNAME() {
                return NAME;
            }

            public void setNAME(String NAME) {
                this.NAME = NAME;
            }

            public String getIDENTITYCARD() {
                return IDENTITYCARD;
            }

            public void setIDENTITYCARD(String IDENTITYCARD) {
                this.IDENTITYCARD = IDENTITYCARD;
            }

            public String getPHONENUM() {
                return PHONENUM;
            }

            public void setPHONENUM(String PHONENUM) {
                this.PHONENUM = PHONENUM;
            }

            public String getROOMID() {
                return ROOMID;
            }

            public void setROOMID(String ROOMID) {
                this.ROOMID = ROOMID;
            }

            public String getINTIME() {
                return INTIME;
            }

            public void setINTIME(String INTIME) {
                this.INTIME = INTIME;
            }

            public String getOUTTIME() {
                return OUTTIME;
            }

            public void setOUTTIME(String OUTTIME) {
                this.OUTTIME = OUTTIME;
            }

            public String getCARDID() {
                return CARDID;
            }

            public void setCARDID(String CARDID) {
                this.CARDID = CARDID;
            }

            public int getCARDTYPE() {
                return CARDTYPE;
            }

            public void setCARDTYPE(int CARDTYPE) {
                this.CARDTYPE = CARDTYPE;
            }
        }
    }
}
