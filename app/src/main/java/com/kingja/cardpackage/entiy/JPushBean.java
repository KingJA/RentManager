package com.kingja.cardpackage.entiy;

/**
 * Description：TODO
 * Create Time：2016/9/6 15:09
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class JPushBean {

    private DataBean data;
    private int MessageType;
    private String CityCode;
    private String CardCode;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getMessageType() {
        return MessageType;
    }

    public void setMessageType(int MessageType) {
        this.MessageType = MessageType;
    }

    public String getCityCode() {
        return CityCode;
    }

    public void setCityCode(String CityCode) {
        this.CityCode = CityCode;
    }

    public String getCardCode() {
        return CardCode;
    }

    public void setCardCode(String CardCode) {
        this.CardCode = CardCode;
    }

    public static class DataBean {
        private String SOURCEID;
        private String APPCARDTYPE;

        public String getSOURCEID() {
            return SOURCEID;
        }

        public void setSOURCEID(String SOURCEID) {
            this.SOURCEID = SOURCEID;
        }

        public String getAPPCARDTYPE() {
            return APPCARDTYPE;
        }

        public void setAPPCARDTYPE(String APPCARDTYPE) {
            this.APPCARDTYPE = APPCARDTYPE;
        }
    }
}
