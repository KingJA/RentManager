package com.kingja.cardpackage.entiy;

/**
 * Description：TODO
 * Create Time：2016/8/31 9:46
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ShangPu_ViewInfo {

    /**
     * ResultCode : 0
     * ResultText : 操作成功
     * DataTypeCode : ShangPu_ViewInfo
     * TaskID :  1
     * Content : {"SHOPID":"XXX","SHOPTYPE":"XXX","SHOPNAME":"XXX","STANDARDADDRCODE":"XXX","ADDRESS":"XXX","IDENTITYCARD":"XXX","OWNERNAME":"XXX","PHONE":"XXX","JWHCODE":"XXX"}
     */

    private int ResultCode;
    private String ResultText;
    private String DataTypeCode;
    private String TaskID;
    /**
     * SHOPID : XXX
     * SHOPTYPE : XXX
     * SHOPNAME : XXX
     * STANDARDADDRCODE : XXX
     * ADDRESS : XXX
     * IDENTITYCARD : XXX
     * OWNERNAME : XXX
     * PHONE : XXX
     * JWHCODE : XXX
     */

    private ShopBean Content;

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

    public ShopBean getContent() {
        return Content;
    }

    public void setContent(ShopBean Content) {
        this.Content = Content;
    }


}
