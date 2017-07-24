package com.kingja.cardpackage.util;

import com.tdr.wisdome.util.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Description:TODO
 * Create Time:2017/7/18 15:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class MD5Util {
    public static String getMD5(String val){
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md5.update(val.getBytes());
        byte[] m = md5.digest();//加密
        return Utils.bytesToHexString(m);
    }
}
