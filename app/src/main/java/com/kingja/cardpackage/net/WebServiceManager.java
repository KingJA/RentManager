package com.kingja.cardpackage.net;

import android.util.Log;

import com.google.gson.Gson;
import com.kingja.cardpackage.util.Constants;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Map;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：WebService管理类
 * 创建人：KingJA
 * 创建时间：2016/3/22 13:08
 * 修改备注：
 */
public class WebServiceManager {
    private static final String TAG = "WebServiceManager";

    private WebServiceManager() {
    }

    private static WebServiceManager mWebServiceManager;

    public static WebServiceManager getInstance() {
        if (mWebServiceManager == null) {
            synchronized (WebServiceManager.class) {
                if (mWebServiceManager == null) {
                    mWebServiceManager = new WebServiceManager();
                }
            }
        }
        return mWebServiceManager;
    }

    public static String load(Map<String, Object> paramMap) throws IOException, XmlPullParserException {
        HttpTransportSE mHttpTransportSE = new HttpTransportSE(Constants.WEBSERVER_URL, 20000);
        SoapObject mSoapObject = new SoapObject(Constants.WEBSERVER_NAMESPACE, Constants.WEBSERVER_REREQUEST);
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            mSoapObject.addProperty(entry.getKey(), entry.getValue());
        }
        SoapSerializationEnvelope mSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        mSoapSerializationEnvelope.dotNet = true;
        mSoapSerializationEnvelope.bodyOut = mSoapObject;
        mHttpTransportSE.call(Constants.WEBSERVER_NAMESPACE+ Constants.WEBSERVER_REREQUEST, mSoapSerializationEnvelope);
        SoapPrimitive retult = (SoapPrimitive) mSoapSerializationEnvelope.getResponse();
        return retult.toString();
    }

    public static <T> T load(Map<String, Object> paramMap, Class<T> clazz) throws IOException, XmlPullParserException {
        HttpTransportSE mHttpTransportSE = new HttpTransportSE(Constants.WEBSERVER_URL);
        SoapObject mSoapObject = new SoapObject(Constants.WEBSERVER_NAMESPACE, Constants.WEBSERVER_REREQUEST);
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            mSoapObject.addProperty(entry.getKey(), entry.getValue());
        }
        SoapSerializationEnvelope mSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        mSoapSerializationEnvelope.dotNet = true;
        mSoapSerializationEnvelope.bodyOut = mSoapObject;
        mHttpTransportSE.call(Constants.WEBSERVER_NAMESPACE+ Constants.WEBSERVER_REREQUEST, mSoapSerializationEnvelope);
        SoapPrimitive result = (SoapPrimitive) mSoapSerializationEnvelope.getResponse();
        Log.i(TAG, "retult: " + result.toString());
        return json2Bean(result.toString(), clazz);

    }

    public static <T> T json2Bean(String json, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }

}
