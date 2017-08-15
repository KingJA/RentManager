package com.kingja.cardpackage.util;

/**
 * Description：TODO
 * Create Time：2016/8/15 13:51
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class DataManager {
    private static final String TOKEN = "token";
    private static final String REAL_NAME = "realName";
    private static final String BIRTHDAY = "birthday";
    private static final String ADDRESS = "address";
    private static final String SEX = "sex";
    private static final String USER_ID = "userId";
    private static final String PHONE = "Phone";
    private static final String USER_NAME = "userName";
    private static final String LAST_PAGE = "LAST_PAGE";
    private static final String IDENTITYCARD = "identitycard";
    private static final String CERTIFICATION = "CERTIFICATION";
    private static final String EMPTY = "";
    private static final int INT_EMPTY = -1;

    /*================================GET================================*/
    public static String getToken() {
        return (String) SpUtils.get(TOKEN, EMPTY);
    }

    public static String getUserId() {
        return (String) SpUtils.get(USER_ID, EMPTY);
    }

    public static String getPhone() {
        return (String) SpUtils.get(PHONE, EMPTY);
    }

    public static String getUserName() {
        return (String) SpUtils.get(USER_NAME, EMPTY);
    }

    public static String getIdentitycard() {
        return (String) SpUtils.get(IDENTITYCARD, EMPTY);
    }

    public static String getRealName() {
        return (String) SpUtils.get(REAL_NAME, EMPTY);
    }

    public static Integer getLastPage() {
        return (Integer) SpUtils.get(LAST_PAGE, INT_EMPTY);
    }

    public static String getAddress() {
        return (String) SpUtils.get(ADDRESS, EMPTY);
    }

    public static String getSex() {
        return (String) SpUtils.get(SEX, EMPTY);
    }

    public static String getBirthday() {
        return (String) SpUtils.get(BIRTHDAY, EMPTY);
    }

    public static String getCertification() {
        return (String) SpUtils.get(CERTIFICATION, EMPTY);
    }

    /*================================PUT================================*/

    public static void putToken(String token) {
        SpUtils.put(TOKEN, token);
    }

    public static void putUserId(String userId) {
        SpUtils.put(USER_ID, userId);
    }

    public static void putPhone(String userPhone) {
        SpUtils.put(PHONE, userPhone);
    }

    public static void putUserName(String userName) {
        SpUtils.put(USER_NAME, userName);
    }

    public static void putIdCard(String idCard) {
        SpUtils.put(IDENTITYCARD, idCard);
    }

    public static void putLastPage(int pageIndex) {
        SpUtils.put(LAST_PAGE, pageIndex);
    }

    public static void putAddresse(String address) {
        SpUtils.put(ADDRESS, address);
    }

    public static void putBirthday(String birthday) {
        SpUtils.put(BIRTHDAY, birthday);
    }

    public static void putSex(String sex) {
        SpUtils.put(SEX, sex);
    }

    public static void putRealName(String realName) {
        SpUtils.put(REAL_NAME, realName);
    }

    public static void putCertification(String certification) {
        SpUtils.put(CERTIFICATION, certification);
    }


}
