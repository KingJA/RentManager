package com.tdr.wisdome.util;

import android.os.Environment;
import android.preference.PreferenceManager;

import com.kingja.cardpackage.base.App;

/**
 * Created by Linus_Xie on 2016/6/20.
 */
public class Constants {

    /***
     * 微信
     */


    public static final String APP_ID = "";
    public static class ShowMsgActivity {
        public static final String STitle = "showmsg_title";
        public static final String SMessage = "showmsg_message";
        public static final String BAThumbData = "showmsg_thumb_data";
    }

    public static final String SAVE_PATH = Environment.getExternalStorageDirectory() + "/Wisdom/com.tdr.wisdome/cardImage/";
    public static final int HANDLER_KEY_GETVERSION_SUCCESS = 0;
    public static final int HANDLER_KEY_GETVERSION_FAIL = HANDLER_KEY_GETVERSION_SUCCESS + 1;
    /**
     * 请求CODE
     */
    public final static int HANDLER_KEY_CHANNELREQUEST = HANDLER_KEY_GETVERSION_FAIL + 1;
    /**
     * 调整返回的RESULTCODE
     */
    public final static int HANDLER_KEY_CHANNELRESULT = HANDLER_KEY_CHANNELREQUEST + 1;

    /**
     * Webservice参数
     */
    public static final String WEBSERVER_URL = "http://122.228.188.210:20043/WebCardHolder.asmx";// WebServices访问地址，测试
//    public static final String WEBSERVER_URL = "http://122.228.188.212:13100/RentalEstate.asmx";// WebServices访问地址，测试
//        public static final String WEBSERVER_URL = "http://116.255.205.110:1001/WEBCARDHOLDER.asmx";// WebServices访问地址，正式
    public static final String WEBSERVER_NAMESPACE = "http://tempuri.org/";// 命名空间

    public static final String WEBSERVER_CARDHOLDER = "CardHolder";//智慧e点通服务

    /**
     * 网络参数
     */
    public static final String netACTION = "android.net.conn.CONNECTIVITY_CHANGE";

    /**
     * 程序常用字段存储
     */
    private static final String TOKEN = "token";// 通信令牌
    private static final String USERID = "userId";// 用户编号
    private static final String USERNAME = "userName";// 用户名
    private static final String USERPHONE = "userPhone";//手机号码
    private static final String USERIDENTITYCARD = "userIdentitycard";//用户身份证
    private static final String REALNAME = "realName";//真实姓名
    private static final String PERMANENTADDR = "permanentAddr";//户籍地址
    private static final String CERTIFICATION = "Certification";//（0：否 1:是,2 认证中 3认证失败）
    private static final String CERTIFICATIONMODE = "certificationMode";//（0：本地认证 1:第三方认证）

    private static final String FACEID = "faceId";//头像ID
    private static final String FACEBASE = "faceBase";//用户头像

    private static final String CITYCODE = "userCityCode";//城市代码
    private static final String CITYNAME = "cityName";//城市名称

    private static final String CARDNAME = "cardMsg";//卡片名字
    private static final String CARDCODE = "cardCode";//卡片编号

    /**********************
     * 老人字段
     **************************/
    private static final String LOVEDNUMBER = "lovedNumber";//被关爱人设备编号
    private static final String LOVEDNAME = "lovedName";//被关爱人
    private static final String LOVEDINDENTITY = "loveIdentity";//被关爱人身份证
    private static final String LOVEDPHONE = "lovedPhone";//被关爱人手机
    private static final String LOVEDADDRESS = "loveAddress";//被关爱人居住地址
    private static final String LOVEDBODYPHOTO = "lovedBodyPhoto";//被关爱人半身照
    private static final String LOVEDDISEASE = "lovedDisease";//被关爱人病情
    private static final String LOVEDREMARKS = "lovedRemarks";//被关爱人备注
    private static final String SHOWPHOTO = "showPhoto";//显示的老人照片

    /****
     * 电动车相关字段
     */
    private static final String LASTUPDATETIME = "lastUpdateTime";//编码表最后更新时间

    public static String getToken() {
        return PreferenceManager.getDefaultSharedPreferences(App.context).getString(TOKEN, "");
    }

    public static void setToken(String value) {
        PreferenceManager.getDefaultSharedPreferences(App.context).edit().putString(TOKEN, value).commit();
    }

    public static String getUserId() {
        return PreferenceManager.getDefaultSharedPreferences(App.context).getString(USERID, "");
    }

    public static void setUserId(String value) {
        PreferenceManager.getDefaultSharedPreferences(App.context).edit().putString(USERID, value).commit();
    }

    public static String getUserName() {
        return PreferenceManager.getDefaultSharedPreferences(App.context).getString(USERNAME, "");
    }

    public static void setUserName(String value) {
        PreferenceManager.getDefaultSharedPreferences(App.context).edit().putString(USERNAME, value).commit();
    }

    public static String getUserPhone() {
        return PreferenceManager.getDefaultSharedPreferences(App.context).getString(USERPHONE, "");
    }

    public static void setUserPhone(String value) {
        PreferenceManager.getDefaultSharedPreferences(App.context).edit().putString(USERPHONE, value).commit();
    }

    public static String getUserIdentitycard() {
        return PreferenceManager.getDefaultSharedPreferences(App.context).getString(USERIDENTITYCARD, "");
    }

    public static void setUserIdentitycard(String value) {
        PreferenceManager.getDefaultSharedPreferences(App.context).edit().putString(USERIDENTITYCARD, value).commit();
    }

    public static String getRealName() {
        return PreferenceManager.getDefaultSharedPreferences(App.context).getString(REALNAME, "");
    }

    public static void setRealName(String value) {
        PreferenceManager.getDefaultSharedPreferences(App.context).edit().putString(REALNAME, value).commit();
    }

    public static String getPermanentAddr() {
        return PreferenceManager.getDefaultSharedPreferences(App.context).getString(PERMANENTADDR, "");
    }

    public static void setPermanentAddr(String value) {
        PreferenceManager.getDefaultSharedPreferences(App.context).edit().putString(PERMANENTADDR, value).commit();
    }

    public static String getCertification() {
        return PreferenceManager.getDefaultSharedPreferences(App.context).getString(CERTIFICATION, "");
    }

    public static void setCertification(String value) {
        PreferenceManager.getDefaultSharedPreferences(App.context).edit().putString(CERTIFICATION, value).commit();
    }

    public static String getFaceId() {
        return PreferenceManager.getDefaultSharedPreferences(App.context).getString(FACEID, "");
    }

    public static void setFaceId(String value) {
        PreferenceManager.getDefaultSharedPreferences(App.context).edit().putString(FACEID, value).commit();
    }

    public static String getFaceBase() {
        return PreferenceManager.getDefaultSharedPreferences(App.context).getString(FACEBASE, "");
    }

    public static void setFaceBase(String value) {
        PreferenceManager.getDefaultSharedPreferences(App.context).edit().putString(FACEBASE, value).commit();
    }

    public static String getCityCode() {
        return PreferenceManager.getDefaultSharedPreferences(App.context).getString(CITYCODE, "");
    }

    public static void setCityCode(String value) {
        PreferenceManager.getDefaultSharedPreferences(App.context).edit().putString(CITYCODE, value).commit();
    }

    public static String getCityName() {
        return PreferenceManager.getDefaultSharedPreferences(App.context).getString(CITYNAME, "");
    }

    public static void setCityName(String value) {
        PreferenceManager.getDefaultSharedPreferences(App.context).edit().putString(CITYNAME, value).commit();
    }

    public static String getCardName() {
        return PreferenceManager.getDefaultSharedPreferences(App.context).getString(CARDNAME, "");
    }

    public static void setCardName(String value) {
        PreferenceManager.getDefaultSharedPreferences(App.context).edit().putString(CARDNAME, value).commit();
    }

    public static String getCardCode() {
        return PreferenceManager.getDefaultSharedPreferences(App.context).getString(CARDCODE, "");
    }

    public static void setCardCode(String value) {
        PreferenceManager.getDefaultSharedPreferences(App.context).edit().putString(CARDCODE, value).commit();
    }

    // 被关爱人数据

    public static String getLovedNumber() {
        return PreferenceManager.getDefaultSharedPreferences(App.context).getString(LOVEDNUMBER, "");
    }

    public static void setLovedNumber(String value) {
        PreferenceManager.getDefaultSharedPreferences(App.context).edit().putString(LOVEDNUMBER, value)
                .commit();
    }

    public static String getLovedName() {
        return PreferenceManager.getDefaultSharedPreferences(App.context).getString(LOVEDNAME, "");
    }

    public static void setLovedName(String value) {
        PreferenceManager.getDefaultSharedPreferences(App.context).edit().putString(LOVEDNAME, value)
                .commit();
    }

    public static String getLovedIndentity() {
        return PreferenceManager.getDefaultSharedPreferences(App.context).getString(LOVEDINDENTITY, "");
    }

    public static void setLovedIndentity(String value) {
        PreferenceManager.getDefaultSharedPreferences(App.context).edit().putString(LOVEDINDENTITY, value)
                .commit();
    }

    public static String getLovedPhone() {
        return PreferenceManager.getDefaultSharedPreferences(App.context).getString(LOVEDPHONE, "");
    }

    public static void setLovedPhone(String value) {
        PreferenceManager.getDefaultSharedPreferences(App.context).edit().putString(LOVEDPHONE, value)
                .commit();
    }

    public static String getLovedAddress() {
        return PreferenceManager.getDefaultSharedPreferences(App.context).getString(LOVEDADDRESS, "");
    }

    public static void setLovedAddress(String value) {
        PreferenceManager.getDefaultSharedPreferences(App.context).edit().putString(LOVEDADDRESS, value)
                .commit();
    }

    public static String getBodyPhoto() {
        return PreferenceManager.getDefaultSharedPreferences(App.context).getString(LOVEDBODYPHOTO, "");
    }

    public static void setBodyPhoto(String value) {
        PreferenceManager.getDefaultSharedPreferences(App.context).edit().putString(LOVEDBODYPHOTO, value)
                .commit();
    }

    public static String getLovedDisease() {
        return PreferenceManager.getDefaultSharedPreferences(App.context).getString(LOVEDDISEASE, "");
    }

    public static void setLovedDisease(String value) {
        PreferenceManager.getDefaultSharedPreferences(App.context).edit().putString(LOVEDDISEASE, value)
                .commit();
    }

    public static String getLovedRemarks() {
        return PreferenceManager.getDefaultSharedPreferences(App.context).getString(LOVEDREMARKS, "");
    }

    public static void setLovedRemarks(String value) {
        PreferenceManager.getDefaultSharedPreferences(App.context).edit().putString(LOVEDREMARKS, value)
                .commit();
    }

    public static String getShowPhoto() {
        return PreferenceManager.getDefaultSharedPreferences(App.context).getString(SHOWPHOTO, "");
    }

    public static void setShowphoto(String value) {
        PreferenceManager.getDefaultSharedPreferences(App.context).edit().putString(SHOWPHOTO, value)
                .commit();
    }

    public static String getLastUpdateTime() {
        return PreferenceManager.getDefaultSharedPreferences(App.context).getString(LASTUPDATETIME, "");
    }

    public static void setLastUpdateTime(String value) {
        PreferenceManager.getDefaultSharedPreferences(App.context).edit().putString(LASTUPDATETIME, value)
                .commit();
    }
}
