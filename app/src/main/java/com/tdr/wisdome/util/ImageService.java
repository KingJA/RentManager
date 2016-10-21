package com.tdr.wisdome.util;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

/**
 * 取卡片图片
 * Created by Linus_Xie on 2016/8/12.
 */
public class ImageService {

    public String path = "";//图片保存路径

    public static void getPic(final String CardLogo,final String cardCode) {

        final JSONObject json = new JSONObject();
        try {
            json.put("PICID", CardLogo);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("token", Constants.getToken());
        map.put("cardType", "");
        map.put("taskId", "");
        map.put("DataTypeCode", "GetPicture");
        map.put("content", json.toString());
        WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map, new WebServiceUtils.WebServiceCallBack() {
            @Override
            public void callBack(String result) {
                if (result != null) {
                    Log.e("获取的照片：", result);
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int resultCode = jsonObject.getInt("ResultCode");
                        String resultText = Utils.initNullStr(jsonObject.getString("ResultText"));
                        if (resultCode == 0) {
                            String content = jsonObject.getString("Content");
                            Utils.saveFile(Utils.stringtoBitmap(content), cardCode + ".png");
                        } else {
                            Log.e("获取的照片出错：", resultText);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("获取的照片出错：", "获取数据错误，请稍后重试！");
                }
            }
        });
    }

}
