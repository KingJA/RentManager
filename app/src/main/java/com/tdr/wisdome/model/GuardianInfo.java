package com.tdr.wisdome.model;

import java.io.Serializable;

/**
 * 监护人对象
 * Created by Linus_Xie on 2016/8/19.
 */
public class GuardianInfo implements Serializable{

    private String guardianId;//监护人ID
    private String guardianName;//监护人姓名
    private String guardianIdCard;//监护人身份证
    private String guardianMobile;//监护人手机号码
    private String guardianAddress;//监护人地址
    private String enmergencyCall;//备用电话

    public String getGuardianId() {
        return guardianId;
    }

    public void setGuardianId(String guardianId) {
        this.guardianId = guardianId;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
    }

    public String getGuardianIdCard() {
        return guardianIdCard;
    }

    public void setGuardianIdCard(String guardianIdCard) {
        this.guardianIdCard = guardianIdCard;
    }

    public String getGuardianMobile() {
        return guardianMobile;
    }

    public void setGuardianMobile(String guardianMobile) {
        this.guardianMobile = guardianMobile;
    }

    public String getGuardianAddress() {
        return guardianAddress;
    }

    public void setGuardianAddress(String guardianAddress) {
        this.guardianAddress = guardianAddress;
    }

    public String getEnmergencyCall() {
        return enmergencyCall;
    }

    public void setEnmergencyCall(String enmergencyCall) {
        this.enmergencyCall = enmergencyCall;
    }
}
