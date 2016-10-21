package com.kingja.cardpackage.entiy;

import java.util.List;

/**
 * Description：TODO
 * Create Time：2016/8/30 16:25
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ShangPu_EmployeeList {

    /**
     * ResultCode : 0
     * ResultText : 操作成功
     * DataTypeCode : ShangPu_EmployeeList
     * TaskID :  1
     * Content : {"SHOPID":"XXX","PERSONNELINFOLIST":[{"LISTID":"0123456789ABCDEF0123456789ABCDEF","NAME":"张三","IDENTITYCARD":"330303199909091234","PHONENUM":"13805771234"},{"LISTID":"0123456789ABCDEF0123456789ABCDE0","NAME":"黄荣智","IDENTITYCARD":"330381198306071811","PHONENUM":"18857758345"}]}
     */

    private int ResultCode;
    private String ResultText;
    private String DataTypeCode;
    private String TaskID;
    /**
     * SHOPID : XXX
     * PERSONNELINFOLIST : [{"LISTID":"0123456789ABCDEF0123456789ABCDEF","NAME":"张三","IDENTITYCARD":"330303199909091234","PHONENUM":"13805771234"},{"LISTID":"0123456789ABCDEF0123456789ABCDE0","NAME":"黄荣智","IDENTITYCARD":"330381198306071811","PHONENUM":"18857758345"}]
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
        private String SHOPID;
        /**
         * LISTID : 0123456789ABCDEF0123456789ABCDEF
         * NAME : 张三
         * IDENTITYCARD : 330303199909091234
         * PHONENUM : 13805771234
         */

        private List<PERSONNELINFOLISTBean> PERSONNELINFOLIST;

        public String getSHOPID() {
            return SHOPID;
        }

        public void setSHOPID(String SHOPID) {
            this.SHOPID = SHOPID;
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
        }
    }
}
