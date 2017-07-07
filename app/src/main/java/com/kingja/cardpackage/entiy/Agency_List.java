package com.kingja.cardpackage.entiy;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2017/7/7 13:24
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class Agency_List {

    /**
     * ResultCode : 0
     * ResultText : 操作成功
     * DataTypeCode : Agency_List
     * TaskID :  1
     * Content : [{"AGENCYID":"0123456789ABCDEF0123456789ABCDEF","AGENCYNAME":"TEST","XQCODE":"330302",
     * "PCSCODE":"330302002000","ADDRESS":"龙湾区蒲州街道河头路55号","LICENSE":"1005 ","OWNERNAME":"张三 ","PHONE":"13736350001"},
     * {"AGENCYID":"0123456789ABCDEF0123456789ABCDEF","AGENCYNAME":"TEST","XQCODE":"330302","PCSCODE":"330302002 ",
     * "ADDRESS":"龙湾区蒲州街道河头路55号","LICENSE":"1005 ","OWNERNAME":"张三 ","PHONE":"13736350001"}]
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
         * AGENCYID : 0123456789ABCDEF0123456789ABCDEF
         * AGENCYNAME : TEST
         * XQCODE : 330302
         * PCSCODE : 330302002000
         * ADDRESS : 龙湾区蒲州街道河头路55号
         * LICENSE : 1005
         * OWNERNAME : 张三
         * PHONE : 13736350001
         */

        private String AGENCYID;
        private String AGENCYNAME;
        private String XQCODE;
        private String PCSCODE;
        private String ADDRESS;
        private String LICENSE;
        private String OWNERNAME;
        private String PHONE;

        public String getAGENCYID() {
            return AGENCYID;
        }

        public void setAGENCYID(String AGENCYID) {
            this.AGENCYID = AGENCYID;
        }

        public String getAGENCYNAME() {
            return AGENCYNAME;
        }

        public void setAGENCYNAME(String AGENCYNAME) {
            this.AGENCYNAME = AGENCYNAME;
        }

        public String getXQCODE() {
            return XQCODE;
        }

        public void setXQCODE(String XQCODE) {
            this.XQCODE = XQCODE;
        }

        public String getPCSCODE() {
            return PCSCODE;
        }

        public void setPCSCODE(String PCSCODE) {
            this.PCSCODE = PCSCODE;
        }

        public String getADDRESS() {
            return ADDRESS;
        }

        public void setADDRESS(String ADDRESS) {
            this.ADDRESS = ADDRESS;
        }

        public String getLICENSE() {
            return LICENSE;
        }

        public void setLICENSE(String LICENSE) {
            this.LICENSE = LICENSE;
        }

        public String getOWNERNAME() {
            return OWNERNAME;
        }

        public void setOWNERNAME(String OWNERNAME) {
            this.OWNERNAME = OWNERNAME;
        }

        public String getPHONE() {
            return PHONE;
        }

        public void setPHONE(String PHONE) {
            this.PHONE = PHONE;
        }
    }
}
