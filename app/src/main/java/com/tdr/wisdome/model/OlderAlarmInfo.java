package com.tdr.wisdome.model;

/**
 * Created by LInus_Xie on 2016/8/24.
 */
public class OlderAlarmInfo {

    private String alertType;//预警类型,0 超生活区 1超时未归 2电量低 2 静止不动
    private String alertTime;//预警时间
    private String alertContent;//预警内容

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public String getAlertTime() {
        return alertTime;
    }

    public void setAlertTime(String alertTime) {
        this.alertTime = alertTime;
    }

    public String getAlertContent() {
        return alertContent;
    }

    public void setAlertContent(String alertContent) {
        this.alertContent = alertContent;
    }
}
