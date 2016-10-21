package com.tdr.wisdome.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密算法
 * Created by Linus_Xie on 2016/8/9.
 */
public class MD5 {

    public static String getMD5(String val) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(val.getBytes());
        byte[] m = md5.digest();//加密
        return Utils.bytesToHexString(m);
    }

}
