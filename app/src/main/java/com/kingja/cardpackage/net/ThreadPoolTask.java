package com.kingja.cardpackage.net;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.kingja.cardpackage.entiy.ErrorResult;
import com.kingja.cardpackage.util.ActivityManager;
import com.kingja.cardpackage.util.ToastUtil;
import com.orhanobut.logger.Logger;
import com.tdr.wisdome.actvitiy.LoginActivity;
import com.tdr.wisdome.base.MyApplication;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：WebService管理类
 * 创建人：KingJA
 * 创建时间：2016/3/22 13:08
 * 修改备注：
 */
public class ThreadPoolTask implements Runnable {


    private static final String TAG = "ThreadPoolTask";
    private String token;
    private String dataTypeCode;
    private String cardType;
    private Object privateParam;
    private Class clazz;

    private WebServiceCallBack callBack;
    private int resultCode;
    private ErrorResult errorResult;

    public ThreadPoolTask(Builder builder) {
        this.token = builder.token;
        this.dataTypeCode = builder.dataTypeCode;
        this.cardType = builder.cardType;
        this.privateParam = builder.privateParam;
        this.clazz = builder.clazz;
        this.callBack = builder.callBack;
    }

    public void execute() {
        PoolManager.getInstance().execute(this);
    }


    @Override
    public void run() {
        Map<String, Object> generalParam = getGeneralParam(token, cardType, dataTypeCode, privateParam);
        try {
            final String json = WebServiceManager.getInstance().load(generalParam);
            JSONObject rootObject = new JSONObject(json);
            resultCode = rootObject.getInt("ResultCode");
            if (resultCode != 0) {
                Log.i("UNSUCCESS", json);
                Logger.json(json);
                String resultText = rootObject.getString("ResultText");
//                String dataTypeCode1 = rootObject.getString("DataTypeCode");
                errorResult = new ErrorResult();
                errorResult.setResultCode(resultCode);
                errorResult.setResultText(resultText);
//                errorResult.setDataTypeCode(dataTypeCode1);
                if (callBack != null) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showToast(errorResult.getResultText());
                            callBack.onErrorResult(errorResult);
                            if (resultCode == 3) {
                                Log.e(TAG, "resultCode == 3");
                                ActivityManager.getAppManager().finishAllActivity();
                                Intent intent = new Intent(MyApplication.getContext(), LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                MyApplication.getContext().startActivity(intent);
//                                android.os.Process.killProcess(android.os.Process.myPid());
                            }
                            ToastUtil.showToast(errorResult.getResultText());
                        }
                    });
                }
            } else {
                Log.i("SUCCESS", json);
                Logger.json(json);

                if (callBack != null) {

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onSuccess(json2Bean(json, clazz));
                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    ToastUtil.showToast("连接超时");
                    errorResult = new ErrorResult();
                    errorResult.setResultCode(403);
                    errorResult.setResultText("连接超时");
                    callBack.onErrorResult(errorResult);
                }
            });
        }
    }

/*
    1001 我的住房#
    1002 我家出租房#
    1004 我的店#
    1007 出租房代管#
    1003 我的车
    1005 亲情关爱
    1006 服务商城
    */

    public Map<String, Object> getGeneralParam(String token, String cardType, String dataTypeCode, Object privateParam) {
        Gson gson = new Gson();
        String json = gson.toJson(privateParam);
        Map<String, Object> generalParam = new HashMap<>();
        generalParam.put("token", token);
        generalParam.put("encryption", "0");
        generalParam.put("DataTypeCode", dataTypeCode);
        generalParam.put("content", json);
        generalParam.put("cardType", cardType);
        generalParam.put("taskId", "1");
        Log.e("PARAM", gson.toJson(generalParam));
        Logger.json(gson.toJson(generalParam));
        return generalParam;

    }


    public static class Builder {
        private String token;
        private String cardType;
        private String dataTypeCode;
        private Object privateParam;
        private Class clazz;
        private WebServiceCallBack callBack;

        public ThreadPoolTask.Builder setGeneralParam(String token, String cardType, String dataTypeCode, Object privateParam) {
            this.token = token;
            this.cardType = cardType;
            this.dataTypeCode = dataTypeCode;
            this.privateParam = privateParam;
            return this;
        }

        public <T> ThreadPoolTask.Builder setBeanType(Class<T> clazz) {
            this.clazz = clazz;
            return this;
        }

        public <T> ThreadPoolTask.Builder setCallBack(WebServiceCallBack<T> callBack) {
            this.callBack = callBack;
            return this;


        }


        public ThreadPoolTask build() {
            return new ThreadPoolTask(this);
        }
    }

    private <T> T json2Bean(String json, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }

}
