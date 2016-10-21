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
    private static final String USER_ID = "userId";
    private static final String USER_PHONE = "userPhone";
    private static final String USER_NAME = "userName";
    private static final String ID_CARD = "userIdentitycard";
    private static final String EMPTY = "";

    /*================================GET================================*/
    public static String getToken() {
        return (String) SpUtils.get(TOKEN, EMPTY);
    }

    public static String getUserId() {
        return (String) SpUtils.get(USER_ID, EMPTY);
    }

    public static String getUserPhone() {
        return (String) SpUtils.get(USER_PHONE, EMPTY);
    }

    public static String getUserName() {
        return (String) SpUtils.get(USER_NAME, EMPTY);
    }

    public static String getIdCard() {
        return (String) SpUtils.get(ID_CARD, EMPTY);
    }

    public static String getRealName() {
        return (String) SpUtils.get(REAL_NAME, EMPTY);
    }

    /*================================PUT================================*/

    public static void putToken(String token) {
        SpUtils.put(TOKEN, token);
    }

    public static void putUserId(String userId) {
        SpUtils.put(USER_ID, userId);
    }

    public static void putUserPhone(String userPhone) {
        SpUtils.put(USER_PHONE, userPhone);
    }

    public static void putUserName(String userName) {
        SpUtils.put(USER_NAME, userName);
    }

    public static void putIdCard(String idCard) {
        SpUtils.put(ID_CARD, idCard);
    }


}
