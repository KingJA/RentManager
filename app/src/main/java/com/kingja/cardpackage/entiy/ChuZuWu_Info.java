package com.kingja.cardpackage.entiy;

/**
 * Description：TODO
 * Create Time：2016/9/8 9:21
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ChuZuWu_Info {

    /**
     * ResultCode : 0
     * ResultText : 操作成功
     * DataTypeCode : ChuZuWu_Info
     * TaskID :  1
     * Content : {"HOUSEID":"XXX","HOUSENAME":"出租房1","ADDRESS":"永安江路88号","OWNERNAME":"黄荣智","PHONE":"18857758345","ISREGISTER":0,"STANDARDADDRCODE":"3303022015885422563563","XQCODE":"330302","PCSCODE":"330302098000","JWHCODE":"330302008001","ISFAVORITE":0,"HAS":"1023","RoomList":[{"ROOMID":"XXX","ROOMNO":101,"DEPLOYSTATUS":1,"HEADCOUNT":2,"SHOUQUANCOUNT":2,"STATIONNO":"123456"},{"ROOMID":"XXX","ROOMNO":102,"DEPLOYSTATUS":1,"HEADCOUNT":2,"SHOUQUANCOUNT":2,"STATIONNO":"123456"}]}
     */

    private int ResultCode;
    private String ResultText;
    private String DataTypeCode;
    private String TaskID;
    /**
     * HOUSEID : XXX
     * HOUSENAME : 出租房1
     * ADDRESS : 永安江路88号
     * OWNERNAME : 黄荣智
     * PHONE : 18857758345
     * ISREGISTER : 0
     * STANDARDADDRCODE : 3303022015885422563563
     * XQCODE : 330302
     * PCSCODE : 330302098000
     * JWHCODE : 330302008001
     * ISFAVORITE : 0
     * HAS : 1023
     * RoomList : [{"ROOMID":"XXX","ROOMNO":101,"DEPLOYSTATUS":1,"HEADCOUNT":2,"SHOUQUANCOUNT":2,"STATIONNO":"123456"},{"ROOMID":"XXX","ROOMNO":102,"DEPLOYSTATUS":1,"HEADCOUNT":2,"SHOUQUANCOUNT":2,"STATIONNO":"123456"}]
     */

    private RentBean Content;

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

    public RentBean getContent() {
        return Content;
    }

    public void setContent(RentBean Content) {
        this.Content = Content;
    }


}
