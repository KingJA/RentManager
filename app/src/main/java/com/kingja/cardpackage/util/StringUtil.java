package com.kingja.cardpackage.util;

import java.util.UUID;

/**
 * Description：TODO
 * Create Time：2016/8/22 14:33
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class StringUtil {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
