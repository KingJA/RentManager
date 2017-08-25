package com.kingja.cardpackage.entiy;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2017/5/10 11:18
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public  class ApplyPerson {
    /**
     * LISTID : 0123456789ABCDEF0123456789ABCDEF
     * NAME : 张三
     * IDENTITYCARD : 330302198701234567
     * PHONE : 13805778888
     * REPORTERROLE : 1
     * OPERATOR : 0123456789ABCDEF0123456789ABCDEF
     * TERMINAL : 1
     * OPERATORPHONE : 13905771234
     * HEIGHT : 180
     */

    private String LISTID;
    private int REPORTERROLE;
    private String OPERATOR;
    private int TERMINAL;
    private String OPERATORPHONE;
    private String NAME;
    private String IDENTITYCARD;
    private String PHONE;

    public String getOPERATUNIT() {
        return OPERATUNIT;
    }

    public void setOPERATUNIT(String OPERATUNIT) {
        this.OPERATUNIT = OPERATUNIT;
    }

    private String OPERATUNIT;
    private int HEIGHT;
    private int PHOTOCOUNT;
    private List<PHOTOLISTBean> PHOTOLIST;
    public int getPHOTOCOUNT() {
        return PHOTOCOUNT;
    }

    public void setPHOTOCOUNT(int PHOTOCOUNT) {
        this.PHOTOCOUNT = PHOTOCOUNT;
    }

    public List<PHOTOLISTBean> getPHOTOLIST() {
        return PHOTOLIST;
    }

    public void setPHOTOLIST(List<PHOTOLISTBean> PHOTOLIST) {
        this.PHOTOLIST = PHOTOLIST;
    }

    public static class PHOTOLISTBean {
        private String LISTID;
        private String TAG;
        private String IMAGE;

        public String getLISTID() {
            return LISTID;
        }

        public void setLISTID(String LISTID) {
            this.LISTID = LISTID;
        }

        public String getTAG() {
            return TAG;
        }

        public void setTAG(String TAG) {
            this.TAG = TAG;
        }

        public String getIMAGE() {
            return IMAGE;
        }

        public void setIMAGE(String IMAGE) {
            this.IMAGE = IMAGE;
        }
    }
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

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public int getREPORTERROLE() {
        return REPORTERROLE;
    }

    public void setREPORTERROLE(int REPORTERROLE) {
        this.REPORTERROLE = REPORTERROLE;
    }

    public String getOPERATOR() {
        return OPERATOR;
    }

    public void setOPERATOR(String OPERATOR) {
        this.OPERATOR = OPERATOR;
    }

    public int getTERMINAL() {
        return TERMINAL;
    }

    public void setTERMINAL(int TERMINAL) {
        this.TERMINAL = TERMINAL;
    }

    public String getOPERATORPHONE() {
        return OPERATORPHONE;
    }

    public void setOPERATORPHONE(String OPERATORPHONE) {
        this.OPERATORPHONE = OPERATORPHONE;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public void setHEIGHT(int HEIGHT) {
        this.HEIGHT = HEIGHT;
    }

    @Override
    public String toString() {
        return "ApplyPerson{" +
                "NAME='" + NAME + '\'' +
                ", IDENTITYCARD='" + IDENTITYCARD + '\'' +
                ", PHONE='" + PHONE + '\'' +
                ", HEIGHT=" + HEIGHT +
                '}';
    }
}