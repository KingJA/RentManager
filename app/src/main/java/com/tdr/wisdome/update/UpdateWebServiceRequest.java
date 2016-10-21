package com.tdr.wisdome.update;

import android.util.Log;

import com.tdr.wisdome.util.Constants;
import com.tdr.wisdome.util.MyHttpTransportSE;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;

public class UpdateWebServiceRequest {

	/**
	 * 获取版本号
	 *
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public VersionInfo getVersionCode(String fileName) throws Exception {

		SoapObject request = new SoapObject(Constants.WEBSERVER_NAMESPACE,
				"GetCode");
		Log.e("亲情宝", "更新参数：" + fileName);
		request.addProperty("FileName", fileName);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.bodyOut = request;

		MyHttpTransportSE ht = new MyHttpTransportSE(
				UpdateManager.WEBSERVER_URL);
		ht.call(Constants.WEBSERVER_NAMESPACE + "GetCode", envelope);
		String result = ((SoapPrimitive) envelope.getResponse()).toString();
		Log.e("FamilyTreasure", "result:" + result);
		return initVersionInfo(result);
	}

	private VersionInfo initVersionInfo(String response) {
		try {
			JSONArray jsonArray = new JSONArray(response);
			JSONObject jsonObject = jsonArray.getJSONObject(0);
			VersionInfo info = new VersionInfo();
			info.setVersionCode(jsonObject.getDouble("android:versionCode"));
			info.setVersionName(jsonObject.getString("android:versionName"));
			info.setPackageName(jsonObject.getString("package"));
			return info;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
