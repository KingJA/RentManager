package com.kingja.cardpackage.entiy;

import java.util.List;

/**
 * Description：TODO
 * Create Time：2016/8/6 15:36
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ShangPu_List {


    /**
     * ResultCode : 0
     * ResultText : 查询成功
     * DataTypeCode : ShangPu_List
     * TaskID : 1
     * Content : [{"SHOPID":"c28da94cbfaa41f19a404e2de0bcfd13","SHOPNAME":"","STARTWORKTIME":"21:00","ENDWORKTIME":"08:00","DEPLOYSTATUS":1,"STATUS":1,"SHOPTYPE":-1,"STANDARDADDRCODE":"330327201310007606000007","ADDRESS":"龙湾区蒲州街道杨宅路71弄4-1号","IDENTITYCARD":"330302196511283237","OWNERNAME":"test","PHONE":"13456058817","JWHCODE":"330327003026"},{"SHOPID":"FE1A697028714E9F94CFF7D537069","SHOPNAME":"多美丽","STARTWORKTIME":"00:00","ENDWORKTIME":"00:00","DEPLOYSTATUS":1,"STATUS":0,"SHOPTYPE":4,"STANDARDADDRCODE":"","ADDRESS":"就是","IDENTITYCARD":"330302196511283237","OWNERNAME":"金友发","PHONE":"13456058817","JWHCODE":""},{"SHOPID":"530ba0f0e7214db194cf588f188f52bf","SHOPNAME":"肯德基","STARTWORKTIME":"21:00","ENDWORKTIME":"08:00","DEPLOYSTATUS":1,"STATUS":1,"SHOPTYPE":5,"STANDARDADDRCODE":"330302201305007542000005","ADDRESS":"龙湾区蒲州街道河头路167号","IDENTITYCARD":"330302196511283237","OWNERNAME":"test","PHONE":"13456058817","JWHCODE":"330302003005"}]
     */

    private String ResultCode;
    private String ResultText;
    private String DataTypeCode;
    private String TaskID;
    /**
     * SHOPID : c28da94cbfaa41f19a404e2de0bcfd13
     * SHOPNAME :
     * STARTWORKTIME : 21:00
     * ENDWORKTIME : 08:00
     * DEPLOYSTATUS : 1
     * STATUS : 1
     * SHOPTYPE : -1
     * STANDARDADDRCODE : 330327201310007606000007
     * ADDRESS : 龙湾区蒲州街道杨宅路71弄4-1号
     * IDENTITYCARD : 330302196511283237
     * OWNERNAME : test
     * PHONE : 13456058817
     * JWHCODE : 330327003026
     */

    private List<ShopBean> Content;

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

    public List<ShopBean> getContent() {
        return Content;
    }

    public void setContent(List<ShopBean> Content) {
        this.Content = Content;
    }


}
