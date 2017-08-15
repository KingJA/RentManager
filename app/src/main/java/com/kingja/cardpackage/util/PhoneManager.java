package com.kingja.cardpackage.util;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.kingja.cardpackage.entiy.PhoneInfo;

import cn.jpush.android.api.JPushInterface;



public class PhoneManager {

    public static PhoneInfo getInfo(Context context) {
        Context applicationContext = context.getApplicationContext();
        PhoneInfo phoneInfo = new PhoneInfo();
        TelephonyManager mTm = (TelephonyManager) applicationContext.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = mTm.getDeviceId();//国际移动设备标识
        String imsi = mTm.getSubscriberId();//国际移动用户识别码
        String iccid = mTm.getSimSerialNumber();//集成电路卡识别码
        String mtype = android.os.Build.MODEL; // 手机型号
        String SYSTEMVERSION = android.os.Build.VERSION.RELEASE;//系统版本号
        String btmac = BluetoothAdapter.getDefaultAdapter().getAddress();
        phoneInfo.setSYSTEMVERSION(SYSTEMVERSION);//系统版本号
        phoneInfo.setSYSTEMTYPE("Android");//操作系统
        phoneInfo.setDEVICEMODEL(mtype.substring(0,3));//手机型号
        phoneInfo.setIMEI(imei);
        phoneInfo.setIMSI(imsi);
        phoneInfo.setBTMAC(btmac);
        phoneInfo.setICCID(iccid);
        phoneInfo.setDEVICEID(imei);
        phoneInfo.setWIFIMAC(getMacAddress(applicationContext));//WIFI物理地址
        phoneInfo.setCHANNELID(JPushInterface.getRegistrationID(applicationContext));
        Log.e("PhoneManager", "CHANNELID: "+JPushInterface.getRegistrationID(applicationContext));
        phoneInfo.setCHANNELTYPE(1);
        return phoneInfo;
    }

    private static String getMacAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo.getMacAddress();
    }
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }
}
