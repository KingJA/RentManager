package com.kingja.cardpackage.entiy;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2017/7/7 10:49
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class Police_Policemeninfo {


    /**
     * ResultCode : 0
     * ResultText : 操作成功
     * DataTypeCode :  Police_Policemeninfo
     * TaskID :  1
     * Content : [{"RYYWBM":"0123456789ABCDEF0123456789ABCDEF","IDENTITYCARD":"TEST","CHINESENAME":"张三",
     * "XQCODE":"330302","PCSCODE":"330302002 ","SHPCS":"温州市公安局鹿城区分局五马派出所","POLICENO":"","PHONE":"13736350001"},
     * {"RYYWBM":"0123456789ABCDEF0123456789ABCDEF","IDENTITYCARD":"TEST","CHINESENAME":"张三","XQCODE":"330302",
     * "PCSCODE":"330302002","SHPCS":"温州市公安局鹿城区分局五马派出所","POLICENO":"","PHONE":"13736350001"}]
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
         * RYYWBM : 0123456789ABCDEF0123456789ABCDEF
         * IDENTITYCARD : TEST
         * CHINESENAME : 张三
         * XQCODE : 330302
         * PCSCODE : 330302002
         * SHPCS : 温州市公安局鹿城区分局五马派出所
         * POLICENO :
         * PHONE : 13736350001
         */

        private String RYYWBM;
        private String IDENTITYCARD;
        private String CHINESENAME;
        private String XQCODE;
        private String PCSCODE;
        private String SHPCS;
        private String POLICENO;
        private String PHONE;

        public String getRYYWBM() {
            return RYYWBM;
        }

        public void setRYYWBM(String RYYWBM) {
            this.RYYWBM = RYYWBM;
        }

        public String getIDENTITYCARD() {
            return IDENTITYCARD;
        }

        public void setIDENTITYCARD(String IDENTITYCARD) {
            this.IDENTITYCARD = IDENTITYCARD;
        }

        public String getCHINESENAME() {
            return CHINESENAME;
        }

        public void setCHINESENAME(String CHINESENAME) {
            this.CHINESENAME = CHINESENAME;
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

        public String getSHPCS() {
            return SHPCS;
        }

        public void setSHPCS(String SHPCS) {
            this.SHPCS = SHPCS;
        }

        public String getPOLICENO() {
            return POLICENO;
        }

        public void setPOLICENO(String POLICENO) {
            this.POLICENO = POLICENO;
        }

        public String getPHONE() {
            return PHONE;
        }

        public void setPHONE(String PHONE) {
            this.PHONE = PHONE;
        }
    }
}
