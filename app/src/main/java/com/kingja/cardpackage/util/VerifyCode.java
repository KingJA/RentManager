package com.kingja.cardpackage.util;

import android.util.Log;

import com.tdr.wisdome.util.CRC16M;


/**
 * Description：校验码
 * Create Time：2016/9/1 15:22
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class VerifyCode {
    private static final String TAG = "VerifyCode";

    public static String checkDeviceCode(String base) {
        byte[] b = TendencyEncrypt.decode(base.getBytes());
        if (b.length != 8) {
            return "";
        }
        byte[] c = new byte[6];
        for (int i = 0; i < c.length; i++) {
            c[i] = b[i];
        }
        Log.i(TAG, "base" + TendencyEncrypt.bytesToHexString(b));
        Log.i(TAG, "key" + TendencyEncrypt.bytesToHexString(c));

        String key = TendencyEncrypt.encode(c);
        short sh = CRC16M.CalculateCrc16(key.getBytes());
        byte d = (byte) (0xff & sh);
        Log.i(TAG, "d: " + TendencyEncrypt.bytesToHexString(new byte[]{d}));
        byte g = (byte) (0xff & sh >> 8);
        Log.i(TAG, "g: " + TendencyEncrypt.bytesToHexString(new byte[]{g}));
        if (d == b[6] && g == b[7]) {
            byte[] h = TendencyEncrypt.decode(key.getBytes());
            key = TendencyEncrypt.bytesToHexString(h);
            Log.e("good", key);
            return key.toUpperCase();
        } else {
            return "";
        }
    }
}
