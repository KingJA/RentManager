package com.kingja.cardpackage.entiy;

import java.util.List;

/**
 * Description：TODO
 * Create Time：2016/8/30 17:12
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ShangPu_DismissEmployee {

    /**
     * ResultCode : 0
     * ResultText : 操作成功
     * DataTypeCode : ShangPu_DismissEmployee
     * TaskID :  1
     * Content : []
     */

    private int ResultCode;
    private String ResultText;
    private String DataTypeCode;
    private String TaskID;
    private List<?> Content;

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

    public List<?> getContent() {
        return Content;
    }

    public void setContent(List<?> Content) {
        this.Content = Content;
    }
}
