package com.tdr.wisdome.model;


import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

import java.io.Serializable;

/**
 * Created by Linus_Xie on 2016/8/5.
 */
@Table(name = "city_db")
public class CityInfo implements Serializable {


    /**
     * CityCode : 123
     * CityName : 123
     * ShortName : 123
     * FirstWord : W
     * ParentCode : 123
     * CityType : 1
     * Sort : 123
     * IsValid : 1
     */
    @Id(column = "id")
    private String id;
    private String CityCode;//城市的行政区划代码;PK
    private String CityName;//城市名称
    private String CityPinYin;//城市全拼
    private String ShortName;//城市名称简称，如温州的简称WZ
    private String FirstWord;//名称汉字拼音首字母
    private String ParentCode;//上级行政区划代码
    private String CityType;//城市类型（0：一级 1：二级 2：三级 3：四级)
    private String Sort;//顺序（排序）
    private String IsValid;//是否有效（0:无效 1有效）


    public CityInfo(String CityName, String CityPinYin) {
        this.CityName = CityName;
        this.CityPinYin = CityPinYin;
    }

    public CityInfo() {
    }

    public String getCityCode() {
        return CityCode;
    }

    public void setCityCode(String CityCode) {
        this.CityCode = CityCode;
    }

    public String getCityPinYin() {
        return CityPinYin;
    }

    public void setCityPinYin(String CityPinYin) {
        this.CityPinYin = CityPinYin;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String CityName) {
        this.CityName = CityName;
    }

    public String getShortName() {
        return ShortName;
    }

    public void setShortName(String ShortName) {
        this.ShortName = ShortName;
    }

    public String getFirstWord() {
        return FirstWord;
    }

    public void setFirstWord(String FirstWord) {
        this.FirstWord = FirstWord;
    }

    public String getParentCode() {
        return ParentCode;
    }

    public void setParentCode(String ParentCode) {
        this.ParentCode = ParentCode;
    }

    public String getCityType() {
        return CityType;
    }

    public void setCityType(String CityType) {
        this.CityType = CityType;
    }

    public String getSort() {
        return Sort;
    }

    public void setSort(String Sort) {
        this.Sort = Sort;
    }

    public String getIsValid() {
        return IsValid;
    }

    public void setIsValid(String IsValid) {
        this.IsValid = IsValid;
    }
}

