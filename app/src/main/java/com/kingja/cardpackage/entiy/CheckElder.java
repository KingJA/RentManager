package com.kingja.cardpackage.entiy;

import java.util.List;

/**
 * Description：TODO
 * Create Time：2016/9/21 13:35
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class CheckElder {

    /**
     * ResultCode : 0
     * ResultText : ok
     * DataTypeCode : CheckElder
     * TaskID :
     * Content : {"LRINFO":{"CUSTOMERNAME":"张三1号","CUSTOMERIDCARD":"421123199109021930","CUSTOMMOBILE":"","CUSTOMERADDRESS":"火星","TARGETTYPE":"0"},"LRPARAM":{"CENTREPOINTLNG":"120.749005","CENTREPOINTLAT":"27.976028","RADIUS":"2"},"PHOTOINFO":{"PHOTOID":"","CUSTOMERPHOTO":""},"CUSTMERHEALTHINFO":{"HEALTHCONDITION":"高血压,糖尿病,心脏病","EMTNOTICE":""},"GUARDERLIST":[{"GUARDIANID":"d15a513178844fc5be4f2f5c3e8ebeb7","GUARDIANNAME":"啊啊啊","GUARDIANIDCARD":"330381198703250936","GUARDIANMOBILE":"18868269007","GUARDIANADDRESS":"啊啊啊","ENMERGENCYCALL":"","SMARTCAREID":null}],"REGISTERINFO":{"REGERID":null,"REGERMOBILE":"pS4i+3xQE11AK2+LDzDPQg==","REGTIME":null,"RELATIVENAME":""},"GUARDERCOUNT":null,"SMARTCAREID":"5a94d2b5f9854538b91da071eba43848","H5PAGEURL":"http://10.1.6.211:8056/index.html?SMARTCAREID=5a94d2b5f9854538b91da071eba43848","PERSONTYPE":"0","CARENUMBER":"waJJ9/WbxdyK24P1jdv3aA=="}
     */

    private String ResultCode;
    private String ResultText;
    private String DataTypeCode;
    private String TaskID;
    /**
     * LRINFO : {"CUSTOMERNAME":"张三1号","CUSTOMERIDCARD":"421123199109021930","CUSTOMMOBILE":"","CUSTOMERADDRESS":"火星","TARGETTYPE":"0"}
     * LRPARAM : {"CENTREPOINTLNG":"120.749005","CENTREPOINTLAT":"27.976028","RADIUS":"2"}
     * PHOTOINFO : {"PHOTOID":"","CUSTOMERPHOTO":""}
     * CUSTMERHEALTHINFO : {"HEALTHCONDITION":"高血压,糖尿病,心脏病","EMTNOTICE":""}
     * GUARDERLIST : [{"GUARDIANID":"d15a513178844fc5be4f2f5c3e8ebeb7","GUARDIANNAME":"啊啊啊","GUARDIANIDCARD":"330381198703250936","GUARDIANMOBILE":"18868269007","GUARDIANADDRESS":"啊啊啊","ENMERGENCYCALL":"","SMARTCAREID":null}]
     * REGISTERINFO : {"REGERID":null,"REGERMOBILE":"pS4i+3xQE11AK2+LDzDPQg==","REGTIME":null,"RELATIVENAME":""}
     * GUARDERCOUNT : null
     * SMARTCAREID : 5a94d2b5f9854538b91da071eba43848
     * H5PAGEURL : http://10.1.6.211:8056/index.html?SMARTCAREID=5a94d2b5f9854538b91da071eba43848
     * PERSONTYPE : 0
     * CARENUMBER : waJJ9/WbxdyK24P1jdv3aA==
     */

    private ContentBean Content;

    public String getResultCode() {
        return ResultCode;
    }

    public void setResultCode(String ResultCode) {
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
         * CUSTOMERNAME : 张三1号
         * CUSTOMERIDCARD : 421123199109021930
         * CUSTOMMOBILE :
         * CUSTOMERADDRESS : 火星
         * TARGETTYPE : 0
         */

        private LRINFOBean LRINFO;
        /**
         * CENTREPOINTLNG : 120.749005
         * CENTREPOINTLAT : 27.976028
         * RADIUS : 2
         */

        private LRPARAMBean LRPARAM;
        /**
         * PHOTOID :
         * CUSTOMERPHOTO :
         */

        private PHOTOINFOBean PHOTOINFO;
        /**
         * HEALTHCONDITION : 高血压,糖尿病,心脏病
         * EMTNOTICE :
         */

        private CUSTMERHEALTHINFOBean CUSTMERHEALTHINFO;
        /**
         * REGERID : null
         * REGERMOBILE : pS4i+3xQE11AK2+LDzDPQg==
         * REGTIME : null
         * RELATIVENAME :
         */

        private REGISTERINFOBean REGISTERINFO;
        private Object GUARDERCOUNT;
        private String SMARTCAREID;
        private String H5PAGEURL;
        private String PERSONTYPE;
        private String CARENUMBER;
        /**
         * GUARDIANID : d15a513178844fc5be4f2f5c3e8ebeb7
         * GUARDIANNAME : 啊啊啊
         * GUARDIANIDCARD : 330381198703250936
         * GUARDIANMOBILE : 18868269007
         * GUARDIANADDRESS : 啊啊啊
         * ENMERGENCYCALL :
         * SMARTCAREID : null
         */

        private List<GUARDERLISTBean> GUARDERLIST;

        public LRINFOBean getLRINFO() {
            return LRINFO;
        }

        public void setLRINFO(LRINFOBean LRINFO) {
            this.LRINFO = LRINFO;
        }

        public LRPARAMBean getLRPARAM() {
            return LRPARAM;
        }

        public void setLRPARAM(LRPARAMBean LRPARAM) {
            this.LRPARAM = LRPARAM;
        }

        public PHOTOINFOBean getPHOTOINFO() {
            return PHOTOINFO;
        }

        public void setPHOTOINFO(PHOTOINFOBean PHOTOINFO) {
            this.PHOTOINFO = PHOTOINFO;
        }

        public CUSTMERHEALTHINFOBean getCUSTMERHEALTHINFO() {
            return CUSTMERHEALTHINFO;
        }

        public void setCUSTMERHEALTHINFO(CUSTMERHEALTHINFOBean CUSTMERHEALTHINFO) {
            this.CUSTMERHEALTHINFO = CUSTMERHEALTHINFO;
        }

        public REGISTERINFOBean getREGISTERINFO() {
            return REGISTERINFO;
        }

        public void setREGISTERINFO(REGISTERINFOBean REGISTERINFO) {
            this.REGISTERINFO = REGISTERINFO;
        }

        public Object getGUARDERCOUNT() {
            return GUARDERCOUNT;
        }

        public void setGUARDERCOUNT(Object GUARDERCOUNT) {
            this.GUARDERCOUNT = GUARDERCOUNT;
        }

        public String getSMARTCAREID() {
            return SMARTCAREID;
        }

        public void setSMARTCAREID(String SMARTCAREID) {
            this.SMARTCAREID = SMARTCAREID;
        }

        public String getH5PAGEURL() {
            return H5PAGEURL;
        }

        public void setH5PAGEURL(String H5PAGEURL) {
            this.H5PAGEURL = H5PAGEURL;
        }

        public String getPERSONTYPE() {
            return PERSONTYPE;
        }

        public void setPERSONTYPE(String PERSONTYPE) {
            this.PERSONTYPE = PERSONTYPE;
        }

        public String getCARENUMBER() {
            return CARENUMBER;
        }

        public void setCARENUMBER(String CARENUMBER) {
            this.CARENUMBER = CARENUMBER;
        }

        public List<GUARDERLISTBean> getGUARDERLIST() {
            return GUARDERLIST;
        }

        public void setGUARDERLIST(List<GUARDERLISTBean> GUARDERLIST) {
            this.GUARDERLIST = GUARDERLIST;
        }

        public static class LRINFOBean {
            private String CUSTOMERNAME;
            private String CUSTOMERIDCARD;
            private String CUSTOMMOBILE;
            private String CUSTOMERADDRESS;
            private String TARGETTYPE;

            public String getCUSTOMERNAME() {
                return CUSTOMERNAME;
            }

            public void setCUSTOMERNAME(String CUSTOMERNAME) {
                this.CUSTOMERNAME = CUSTOMERNAME;
            }

            public String getCUSTOMERIDCARD() {
                return CUSTOMERIDCARD;
            }

            public void setCUSTOMERIDCARD(String CUSTOMERIDCARD) {
                this.CUSTOMERIDCARD = CUSTOMERIDCARD;
            }

            public String getCUSTOMMOBILE() {
                return CUSTOMMOBILE;
            }

            public void setCUSTOMMOBILE(String CUSTOMMOBILE) {
                this.CUSTOMMOBILE = CUSTOMMOBILE;
            }

            public String getCUSTOMERADDRESS() {
                return CUSTOMERADDRESS;
            }

            public void setCUSTOMERADDRESS(String CUSTOMERADDRESS) {
                this.CUSTOMERADDRESS = CUSTOMERADDRESS;
            }

            public String getTARGETTYPE() {
                return TARGETTYPE;
            }

            public void setTARGETTYPE(String TARGETTYPE) {
                this.TARGETTYPE = TARGETTYPE;
            }
        }

        public static class LRPARAMBean {
            private String CENTREPOINTLNG;
            private String CENTREPOINTLAT;
            private String RADIUS;

            public String getCENTREPOINTLNG() {
                return CENTREPOINTLNG;
            }

            public void setCENTREPOINTLNG(String CENTREPOINTLNG) {
                this.CENTREPOINTLNG = CENTREPOINTLNG;
            }

            public String getCENTREPOINTLAT() {
                return CENTREPOINTLAT;
            }

            public void setCENTREPOINTLAT(String CENTREPOINTLAT) {
                this.CENTREPOINTLAT = CENTREPOINTLAT;
            }

            public String getRADIUS() {
                return RADIUS;
            }

            public void setRADIUS(String RADIUS) {
                this.RADIUS = RADIUS;
            }
        }

        public static class PHOTOINFOBean {
            private String PHOTOID;
            private String CUSTOMERPHOTO;

            public String getPHOTOID() {
                return PHOTOID;
            }

            public void setPHOTOID(String PHOTOID) {
                this.PHOTOID = PHOTOID;
            }

            public String getCUSTOMERPHOTO() {
                return CUSTOMERPHOTO;
            }

            public void setCUSTOMERPHOTO(String CUSTOMERPHOTO) {
                this.CUSTOMERPHOTO = CUSTOMERPHOTO;
            }
        }

        public static class CUSTMERHEALTHINFOBean {
            private String HEALTHCONDITION;
            private String EMTNOTICE;

            public String getHEALTHCONDITION() {
                return HEALTHCONDITION;
            }

            public void setHEALTHCONDITION(String HEALTHCONDITION) {
                this.HEALTHCONDITION = HEALTHCONDITION;
            }

            public String getEMTNOTICE() {
                return EMTNOTICE;
            }

            public void setEMTNOTICE(String EMTNOTICE) {
                this.EMTNOTICE = EMTNOTICE;
            }
        }

        public static class REGISTERINFOBean {
            private Object REGERID;
            private String REGERMOBILE;
            private Object REGTIME;
            private String RELATIVENAME;

            public Object getREGERID() {
                return REGERID;
            }

            public void setREGERID(Object REGERID) {
                this.REGERID = REGERID;
            }

            public String getREGERMOBILE() {
                return REGERMOBILE;
            }

            public void setREGERMOBILE(String REGERMOBILE) {
                this.REGERMOBILE = REGERMOBILE;
            }

            public Object getREGTIME() {
                return REGTIME;
            }

            public void setREGTIME(Object REGTIME) {
                this.REGTIME = REGTIME;
            }

            public String getRELATIVENAME() {
                return RELATIVENAME;
            }

            public void setRELATIVENAME(String RELATIVENAME) {
                this.RELATIVENAME = RELATIVENAME;
            }
        }

        public static class GUARDERLISTBean {
            private String GUARDIANID;
            private String GUARDIANNAME;
            private String GUARDIANIDCARD;
            private String GUARDIANMOBILE;
            private String GUARDIANADDRESS;
            private String ENMERGENCYCALL;
            private Object SMARTCAREID;

            public String getGUARDIANID() {
                return GUARDIANID;
            }

            public void setGUARDIANID(String GUARDIANID) {
                this.GUARDIANID = GUARDIANID;
            }

            public String getGUARDIANNAME() {
                return GUARDIANNAME;
            }

            public void setGUARDIANNAME(String GUARDIANNAME) {
                this.GUARDIANNAME = GUARDIANNAME;
            }

            public String getGUARDIANIDCARD() {
                return GUARDIANIDCARD;
            }

            public void setGUARDIANIDCARD(String GUARDIANIDCARD) {
                this.GUARDIANIDCARD = GUARDIANIDCARD;
            }

            public String getGUARDIANMOBILE() {
                return GUARDIANMOBILE;
            }

            public void setGUARDIANMOBILE(String GUARDIANMOBILE) {
                this.GUARDIANMOBILE = GUARDIANMOBILE;
            }

            public String getGUARDIANADDRESS() {
                return GUARDIANADDRESS;
            }

            public void setGUARDIANADDRESS(String GUARDIANADDRESS) {
                this.GUARDIANADDRESS = GUARDIANADDRESS;
            }

            public String getENMERGENCYCALL() {
                return ENMERGENCYCALL;
            }

            public void setENMERGENCYCALL(String ENMERGENCYCALL) {
                this.ENMERGENCYCALL = ENMERGENCYCALL;
            }

            public Object getSMARTCAREID() {
                return SMARTCAREID;
            }

            public void setSMARTCAREID(Object SMARTCAREID) {
                this.SMARTCAREID = SMARTCAREID;
            }
        }
    }
}
