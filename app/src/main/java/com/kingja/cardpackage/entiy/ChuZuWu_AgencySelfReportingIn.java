package com.kingja.cardpackage.entiy;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2017/5/10 11:17
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ChuZuWu_AgencySelfReportingIn {

    /**
     * TaskID : 1
     * ADDRESS : 永安江路22号
     * ROOMNO : 303
     * PEOPLECOUNT : 2
     * PEOPLELIST : [{"LISTID":"0123456789ABCDEF0123456789ABCDEF","NAME":"张三","IDENTITYCARD":"330302198701234567",
     * "PHONE":"13805778888","REPORTERROLE":1,"OPERATOR":"0123456789ABCDEF0123456789ABCDEF","TERMINAL":1,
     * "OPERATORPHONE":"13905771234","HEIGHT":180}]
     */

    private String XQCODE;
    private String PCSCODE;
    private String JWHCODE;
    private String TaskID;
    private String ADDRESS;
    private String ROOMNO;
    private int PEOPLECOUNT;
    private List<ApplyPerson> PEOPLELIST;

    public String getTaskID() {
        return TaskID;
    }

    public void setTaskID(String TaskID) {
        this.TaskID = TaskID;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getROOMNO() {
        return ROOMNO;
    }

    public void setROOMNO(String ROOMNO) {
        this.ROOMNO = ROOMNO;
    }

    public int getPEOPLECOUNT() {
        return PEOPLECOUNT;
    }

    public void setPEOPLECOUNT(int PEOPLECOUNT) {
        this.PEOPLECOUNT = PEOPLECOUNT;
    }

    public List<ApplyPerson> getPEOPLELIST() {
        return PEOPLELIST;
    }

    public void setPEOPLELIST(List<ApplyPerson> PEOPLELIST) {
        this.PEOPLELIST = PEOPLELIST;
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

    public String getJWHCODE() {
        return JWHCODE;
    }

    public void setJWHCODE(String JWHCODE) {
        this.JWHCODE = JWHCODE;
    }

}
