package com.kingja.cardpackage.util;

import android.util.Log;

import com.tdr.wisdome.util.CRC16M;

/**
 * Description：TODO
 * Create Time：2016/9/1 13:42
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class JoinAdd {
    public static String isAdd(String base){
        byte[] b= TendencyEncrypt.decode(base.getBytes());
        byte[] c=new byte[18];
        for(int i=0;i<c.length;i++){
            c[i]=b[i];
        }
        String key=TendencyEncrypt.encode(c);
        short sh= CRC16M.CalculateCrc16(key.getBytes());
        byte d=(byte)(0xff & sh);
        byte g=(byte)(0xff & sh >>8);
        if(d==b[18]&&g==b[19]){
            byte[] h=TendencyEncrypt.decode(key.getBytes());
            key=TendencyEncrypt.bytesToHexString(h);
            Log.e("good", key);
            return key.toUpperCase();
        }else{
            return "";
        }
    }
    /**
     * 生成加入店铺base64
     * @param key
     * @return
     */
    public static String  base64(String  key){
        byte[] d= TendencyEncrypt.HexString2Bytes(key);
        short sh= CRC16M.CalculateCrc16(TendencyEncrypt.encode(d).getBytes());
        byte a=(byte)(0xff & sh);
        byte b=(byte)(0xff & sh >>8);
        byte[] bts=new byte[20];
        for(int i=0;i<d.length;i++){
            bts[i]=d[i];
        }
        bts[18]=a;
        bts[19]=b;
        String t= Base64.encode(bts);
        Log.e("t",t);
        return t;
    }
}
