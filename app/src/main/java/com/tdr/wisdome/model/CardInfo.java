package com.tdr.wisdome.model;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

/**
 * 卡包的相关参数
 * Created by Linus_Xie on 2016/8/3.
 */
@Table(name = "card_db")
public class CardInfo {

    /**
     * ListID : 8
     * UserID : null
     * UserCityID : 6
     * CardCode : 2002
     * CardLogo : 1
     * CardCodes : 2001,2002
     * IsDelete : 0
     * CityCode : null
     * CreateTime : 2016-08-06 00:00:00
     * CardName : 我的车SX
     */
    @Id(column = "id")
    private String id;
    private String ListID;
    private String UserID;
    private String UserCityID;
    private String CardCode;
    private String CardLogo;
    private String CardCodes;
    private String IsDelete;//1是已经删除，0可以使用
    private String CityCode;
    private String CreateTime;
    private String CardName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getListID() {
        return ListID;
    }

    public void setListID(String ListID) {
        this.ListID = ListID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public String getUserCityID() {
        return UserCityID;
    }

    public void setUserCityID(String UserCityID) {
        this.UserCityID = UserCityID;
    }

    public String getCardCode() {
        return CardCode;
    }

    public void setCardCode(String CardCode) {
        this.CardCode = CardCode;
    }

    public String getCardLogo() {
        return CardLogo;
    }

    public void setCardLogo(String CardLogo) {
        this.CardLogo = CardLogo;
    }

    public String getCardCodes() {
        return CardCodes;
    }

    public void setCardCodes(String CardCodes) {
        this.CardCodes = CardCodes;
    }

    public String getIsDelete() {
        return IsDelete;
    }

    public void setIsDelete(String IsDelete) {
        this.IsDelete = IsDelete;
    }

    public String getCityCode() {
        return CityCode;
    }

    public void setCityCode(String CityCode) {
        this.CityCode = CityCode;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getCardName() {
        return CardName;
    }

    public void setCardName(String CardName) {
        this.CardName = CardName;
    }
}
