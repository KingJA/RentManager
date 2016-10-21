package com.kingja.cardpackage.entiy;

import java.util.List;

/**
 * Description：TODO
 * Create Time：2016/9/1 17:12
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class Common_AddDevice_Param {

    /**
     * TaskID : 1
     * DEVICEID : 0123456789ABCDEF0123456789ABCDEF
     * DEVICETYPE : 1023
     * DEVICECODE : 1234
     * DEVICENAME : 门禁
     * OTHERTYPE : 2
     * OTHERID : 0123456789ABCDEF0123456789ABCDEF
     * ROOMID : 0123456789ABCDEF0123456789ABCDE0
     * PHOTOCOUNT : 2
     * PHOTOLIST : [{"LISTID":"0123456789ABCDEF0123456789ABCDEF","TAG":"设备","IMAGE":"base64"},{"LISTID":"0123456789ABCDEF0123456789ABCDE0","TAG":"线","IMAGE":"base64"}]
     */

    private String TaskID;
    private String DEVICEID;
    private long DEVICETYPE;
    private long DEVICECODE;
    private String DEVICENAME;
    private int OTHERTYPE;
    private String OTHERID;
    private String ROOMID;
    private int PHOTOCOUNT;
    /**
     * LISTID : 0123456789ABCDEF0123456789ABCDEF
     * TAG : 设备
     * IMAGE : base64
     */

    private List<PHOTOLISTBean> PHOTOLIST;

    public String getTaskID() {
        return TaskID;
    }

    public void setTaskID(String TaskID) {
        this.TaskID = TaskID;
    }

    public String getDEVICEID() {
        return DEVICEID;
    }

    public void setDEVICEID(String DEVICEID) {
        this.DEVICEID = DEVICEID;
    }

    public long getDEVICETYPE() {
        return DEVICETYPE;
    }

    public void setDEVICETYPE(long DEVICETYPE) {
        this.DEVICETYPE = DEVICETYPE;
    }

    public long getDEVICECODE() {
        return DEVICECODE;
    }

    public void setDEVICECODE(long DEVICECODE) {
        this.DEVICECODE = DEVICECODE;
    }

    public String getDEVICENAME() {
        return DEVICENAME;
    }

    public void setDEVICENAME(String DEVICENAME) {
        this.DEVICENAME = DEVICENAME;
    }

    public int getOTHERTYPE() {
        return OTHERTYPE;
    }

    public void setOTHERTYPE(int OTHERTYPE) {
        this.OTHERTYPE = OTHERTYPE;
    }

    public String getOTHERID() {
        return OTHERID;
    }

    public void setOTHERID(String OTHERID) {
        this.OTHERID = OTHERID;
    }

    public String getROOMID() {
        return ROOMID;
    }

    public void setROOMID(String ROOMID) {
        this.ROOMID = ROOMID;
    }

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
}
