package com.kingja.cardpackage.util;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.kingja.cardpackage.entiy.PhoneInfo;

import cn.jpush.android.api.JPushInterface;


/**
 * 获取手机参数信息
 * Created by Linus_Xie on 2016/6/25.
 */
public class PhoneUtil {
    private Context mContext;

    public PhoneUtil(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 获取IMEI号，IESI号，手机型号
     */
    public PhoneInfo getInfo() {
        PhoneInfo phoneInfo = new PhoneInfo();
        TelephonyManager mTm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = mTm.getDeviceId();//imei
        String imsi = mTm.getSubscriberId();//imsi
        String iccid = mTm.getSimSerialNumber();//iccid
        String mtype = android.os.Build.MODEL; // 手机型号
        String mtyb = android.os.Build.BRAND;//手机品牌
        String SYSTEMVERSION = android.os.Build.VERSION.RELEASE;//系统版本号
        String androidId = android.provider.Settings.Secure.getString(
                mContext.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        String btmac = BluetoothAdapter.getDefaultAdapter().getAddress();
        phoneInfo.setIMEI(imei);
        phoneInfo.setIMSI(imsi);
        phoneInfo.setBTMAC(btmac);
        phoneInfo.setDEVICEMODEL(mtype.substring(0,3));
        phoneInfo.setICCID(iccid);
        phoneInfo.setSYSTEMVERSION(SYSTEMVERSION);
        phoneInfo.setSYSTEMTYPE("Android");
        phoneInfo.setDEVICEID(imei);
        phoneInfo.setWIFIMAC(getMacAddress());
        phoneInfo.setCHANNELID(JPushInterface.getRegistrationID(mContext));
        return phoneInfo;
    }

    /**
     * .获取手机MAC地址
     * 只有手机开启wifi才能获取到mac地址
     */
    private String getMacAddress() {
        String result = "";
        WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        result = wifiInfo.getMacAddress();
        Log.i("text", "手机macAdd:" + result);
        return result;
    }

}
