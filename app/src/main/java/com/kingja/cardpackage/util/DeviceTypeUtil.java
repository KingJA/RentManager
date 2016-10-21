package com.kingja.cardpackage.util;

import com.kingja.cardpackage.db.DbDaoXutils3;
import com.kingja.cardpackage.entiy.Basic_Dictionary_Kj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description：TODO
 * Create Time：2016/9/1 16:45
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class DeviceTypeUtil {
    /**
     * @param tag 1.店铺 2.出租房 3.社区
     * @return
     */

    public static List<String> getDeviceTypes(String tag) {
        List<String> deviceList = new ArrayList<>();
        List<Basic_Dictionary_Kj> mShopDeviceList = (List<Basic_Dictionary_Kj>) DbDaoXutils3.getInstance().selectAllWhereNotEquil(Basic_Dictionary_Kj.class, "COLUMNCODE", "DEVICETYPE", "USERANG", "");
        for (Basic_Dictionary_Kj bean : mShopDeviceList) {
            if (bean.getUSERANG().contains(tag)) {
                deviceList.add(bean.getCOLUMNVALUE());
            }
        }
        return deviceList;

    }

    public static String getColumComment(String columnCode, String columnValue) {
        String result;
        Basic_Dictionary_Kj bean = (Basic_Dictionary_Kj) DbDaoXutils3.getInstance().sleectFirst(Basic_Dictionary_Kj.class, "COLUMNCODE", columnCode, "COLUMNVALUE", columnValue);
        result = bean.getCOLUMNCOMMENT();
        return result;
    }

    public static Map<String, String> getTypeMap(String columnCode) {
        Map<String, String> typeMap = new HashMap<>();
        List<Basic_Dictionary_Kj> typeList = (List<Basic_Dictionary_Kj>) DbDaoXutils3.getInstance().selectAllWhere(Basic_Dictionary_Kj.class, "COLUMNCODE", "DEVICETYPE");
        for (Basic_Dictionary_Kj bean : typeList) {
            typeMap.put(bean.getCOLUMNVALUE(), bean.getCOLUMNCOMMENT());
        }
        return typeMap;

    }
}
