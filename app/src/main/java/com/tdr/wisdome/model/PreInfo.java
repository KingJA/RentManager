package com.tdr.wisdome.model;

/**
 * Created by Linus_Xie on 2016/8/25.
 */
public class PreInfo {
    private String prerateID;//预登记编号
    private String prerateName;//预登记名称
    private String vehicleBrand;//车辆品牌
    private String vehicleModels;//车辆型号
    private String colorID;//车辆颜色
    private String engineNo;//电机号
    private String shelvesNo;//车架号
    private String buyDate;//购买时间
    private String price;//购买价格
    private String ownerName;//所有者姓名
    private String ownerIdentity;//所有者身份证
    private String phone1;
    private String phone2;
    private String remarks;
    private String createTime;
    private String state;//0 有效 1失效 2 已使用

    public String getPrerateID() {
        return prerateID;
    }

    public void setPrerateID(String prerateID) {
        this.prerateID = prerateID;
    }

    public String getPrerateName() {
        return prerateName;
    }

    public void setPrerateName(String prerateName) {
        this.prerateName = prerateName;
    }

    public String getVehicleBrand() {
        return vehicleBrand;
    }

    public void setVehicleBrand(String vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
    }

    public String getVehicleModels() {
        return vehicleModels;
    }

    public void setVehicleModels(String vehicleModels) {
        this.vehicleModels = vehicleModels;
    }

    public String getColorID() {
        return colorID;
    }

    public void setColorID(String colorID) {
        this.colorID = colorID;
    }

    public String getEngineNo() {
        return engineNo;
    }

    public void setEngineNo(String engineNo) {
        this.engineNo = engineNo;
    }

    public String getShelvesNo() {
        return shelvesNo;
    }

    public void setShelvesNo(String shelvesNo) {
        this.shelvesNo = shelvesNo;
    }

    public String getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerIdentity() {
        return ownerIdentity;
    }

    public void setOwnerIdentity(String ownerIdentity) {
        this.ownerIdentity = ownerIdentity;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
