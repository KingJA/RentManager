package com.kingja.cardpackage.entiy;

import java.io.Serializable;
import java.util.List;

/**
 * Description：TODO
 * Create Time：2016/8/5 14:11
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ChuZuWu_ListByRenter implements Serializable{

    /**
     * ResultCode : 0
     * ResultText : 操作成功
     * DataTypeCode : ChuZuWu_ListByRenter
     * TaskID :  1
     * Content : [{"HOUSEID":"XXX","HOUSENAME":"出租房1","DianZiMenPai":"true","ADDRESS":"永安江路88号","OWNERNAME":"黄荣智","PHONE":"18857758345","ISREGISTER":0,"STANDARDADDRCODE":"3303022015885422563563","XQCODE":"330302","PCSCODE":"330302098000","JWHCODE":"330302008001","RoomList":[{"ROOMID":"XXX","ROOMNO":101,"DEPLOYSTATUS":1,"HEADCOUNT":2,"STATIONNO":"123456"},{"ROOMID":"XXX","ROOMNO":102,"DEPLOYSTATUS":1,"HEADCOUNT":2,"STATIONNO":"123456"}]},{"HOUSEID":"XXX2","HOUSENAME":"出租房2","DianziMenPai":"true","ADDRESS":"永安江路88号","OWNERNAME":"黄荣智","PHONE":"18857758345","ISREGISTER":0,"STANDARDADDRCODE":"3303022015885422563563","XQCODE":"330302","PCSCODE":"330302098000","JWHCODE":"330302008001","RoomList":[{"ROOMID":"XXX","ROOMNO":101,"DEPLOYSTATUS":1,"HEADCOUNT":2,"STATIONNO":"123456"}]}]
     */

    private int ResultCode;
    private String ResultText;
    private String DataTypeCode;
    private String TaskID;
    /**
     * HOUSEID : XXX
     * HOUSENAME : 出租房1
     * DianZiMenPai : true
     * ADDRESS : 永安江路88号
     * OWNERNAME : 黄荣智
     * PHONE : 18857758345
     * ISREGISTER : 0
     * STANDARDADDRCODE : 3303022015885422563563
     * XQCODE : 330302
     * PCSCODE : 330302098000
     * JWHCODE : 330302008001
     * RoomList : [{"ROOMID":"XXX","ROOMNO":101,"DEPLOYSTATUS":1,"HEADCOUNT":2,"STATIONNO":"123456"},{"ROOMID":"XXX","ROOMNO":102,"DEPLOYSTATUS":1,"HEADCOUNT":2,"STATIONNO":"123456"}]
     */

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
        private String HOUSEID;
        private String HOUSENAME;
        private String DianZiMenPai;
        private String ADDRESS;
        private String OWNERNAME;
        private String PHONE;
        private int ISREGISTER;
        private String STANDARDADDRCODE;
        private String XQCODE;
        private String PCSCODE;
        private String JWHCODE;
        /**
         * ROOMID : XXX
         * ROOMNO : 101
         * DEPLOYSTATUS : 1
         * HEADCOUNT : 2
         * STATIONNO : 123456
         */

        private List<RoomListBean> RoomList;

        public String getHOUSEID() {
            return HOUSEID;
        }

        public void setHOUSEID(String HOUSEID) {
            this.HOUSEID = HOUSEID;
        }

        public String getHOUSENAME() {
            return HOUSENAME;
        }

        public void setHOUSENAME(String HOUSENAME) {
            this.HOUSENAME = HOUSENAME;
        }

        public String getDianZiMenPai() {
            return DianZiMenPai;
        }

        public void setDianZiMenPai(String DianZiMenPai) {
            this.DianZiMenPai = DianZiMenPai;
        }

        public String getADDRESS() {
            return ADDRESS;
        }

        public void setADDRESS(String ADDRESS) {
            this.ADDRESS = ADDRESS;
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

        public int getISREGISTER() {
            return ISREGISTER;
        }

        public void setISREGISTER(int ISREGISTER) {
            this.ISREGISTER = ISREGISTER;
        }

        public String getSTANDARDADDRCODE() {
            return STANDARDADDRCODE;
        }

        public void setSTANDARDADDRCODE(String STANDARDADDRCODE) {
            this.STANDARDADDRCODE = STANDARDADDRCODE;
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

        public List<RoomListBean> getRoomList() {
            return RoomList;
        }

        public void setRoomList(List<RoomListBean> RoomList) {
            this.RoomList = RoomList;
        }

        public static class RoomListBean implements Serializable{
            private String ROOMID;
            private int ROOMNO;
            private int DEPLOYSTATUS;
            private int HEADCOUNT;
            private String STATIONNO;

            public String getROOMID() {
                return ROOMID;
            }

            public void setROOMID(String ROOMID) {
                this.ROOMID = ROOMID;
            }

            public int getROOMNO() {
                return ROOMNO;
            }

            public void setROOMNO(int ROOMNO) {
                this.ROOMNO = ROOMNO;
            }

            public int getDEPLOYSTATUS() {
                return DEPLOYSTATUS;
            }

            public void setDEPLOYSTATUS(int DEPLOYSTATUS) {
                this.DEPLOYSTATUS = DEPLOYSTATUS;
            }

            public int getHEADCOUNT() {
                return HEADCOUNT;
            }

            public void setHEADCOUNT(int HEADCOUNT) {
                this.HEADCOUNT = HEADCOUNT;
            }

            public String getSTATIONNO() {
                return STATIONNO;
            }

            public void setSTATIONNO(String STATIONNO) {
                this.STATIONNO = STATIONNO;
            }
        }
    }
}
