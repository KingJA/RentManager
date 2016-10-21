package com.tdr.wisdome.actvitiy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tdr.wisdome.R;
import com.tdr.wisdome.adapter.OlderDeviceConfigAdapter;
import com.tdr.wisdome.model.GroupItem;
import com.tdr.wisdome.util.Constants;
import com.tdr.wisdome.util.Utils;
import com.tdr.wisdome.util.WebServiceUtils;
import com.tdr.wisdome.view.ZProgressHUD;
import com.tdr.wisdome.zbar.CaptureActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 设备配置
 * Created by Linus_Xie on 2016/8/22.
 */
public class DeviceConfigActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "DeviceConfigActivity";

    private Context mContext;

    private OlderDeviceConfigAdapter mAdapter;

    private List<GroupItem> data = new ArrayList<>();
    private List<GroupItem> data1 = new ArrayList<>();
    private List<GroupItem> data2 = new ArrayList<>();
    private List<GroupItem> temp = new ArrayList<>();
    private String smartcareId = "";
    private String personType = "";

    private ZProgressHUD mProgressHUD;

    private final static int SCANNIN_GREQUEST_CODE = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deviceconfiguration);

        mContext = this;

        smartcareId = getIntent().getStringExtra("smartcareId");
        personType = getIntent().getStringExtra("personType");

        initView();
        initData();
    }

    private ImageView image_back;
    private TextView text_title;
    private ImageView image_scan;

    private ListView list_deviceConfig;

    private void initView() {
        image_back = (ImageView) findViewById(R.id.image_back);
        image_back.setOnClickListener(this);
        text_title = (TextView) findViewById(R.id.text_title);
        text_title.setText("设备配置");
        image_scan = (ImageView) findViewById(R.id.image_scan);
        image_scan.setOnClickListener(this);
        image_scan.setVisibility(View.VISIBLE);

        list_deviceConfig = (ListView) findViewById(R.id.list_deviceConfig);

        mAdapter = new OlderDeviceConfigAdapter(this, data, personType);

        mProgressHUD = new ZProgressHUD(mContext);

    }

    private void initData() {
        mProgressHUD.setMessage("设备配置表请求中...");
        mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
        mProgressHUD.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("SMARTCAREID", smartcareId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("token", Constants.getToken());
        map.put("cardType", "1005");
        map.put("taskId", "");
        map.put("DataTypeCode", "GETTARGETDEVICES");
        map.put("content", jsonObject.toString());

        WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map, new WebServiceUtils.WebServiceCallBack() {
            @Override
            public void callBack(String result) {
                if (result != null) {
                    Log.e(TAG, result);
                    try {
                        JSONObject obj = new JSONObject(result);
                        int resultCode = obj.getInt("ResultCode");
                        String resultText = obj.getString("ResultText");
                        if (resultCode == 0) {
                            String content = obj.getString("Content");
                            JSONObject object = new JSONObject(content);
                            String deviceList = object.getString("DEVICELIST");
                            JSONArray array = new JSONArray(deviceList);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject json = array.getJSONObject(i);
                                String deviceType = json.getString("DEVICETYPE");//设备类型ID(0佩戴 1网关)
                                String deviceName = "";
                                if (deviceType.equals("0")) {
                                    deviceName = "智能手环";
                                } else if (deviceType.equals("1")) {
                                    deviceName = "智能网关";
                                }
                                String careNumber = json.getString("CARENUMBER");
                                GroupItem groupItem = new GroupItem(deviceName, careNumber, smartcareId);
                                temp.add(groupItem);
                            }

                            for (int i = 0; i < temp.size(); i++) {
                                if (temp.get(i).getTitle().equals("已拥有")) {
                                    data1.add(temp.get(i));
                                } else {
                                    data2.add(temp.get(i));
                                }
                            }

                            for (int i = 0; i < data1.size(); i++) {
                                data.add(data1.get(i));
                            }
                            for (int i = 0; i < data2.size(); i++) {
                                data.add(data2.get(i));
                            }

                            mProgressHUD.dismiss();
                            list_deviceConfig.setAdapter(mAdapter);
                        } else {
                            mProgressHUD.dismiss();
                            Utils.myToast(mContext, resultText);
                        }
                    } catch (JSONException e) {
                        mProgressHUD.dismiss();
                        e.printStackTrace();
                        Utils.myToast(mContext, "JSON解析出错");
                    }
                } else {
                    mProgressHUD.dismiss();
                    Utils.myToast(mContext, "获取数据错误，请稍后重试！");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_back:
                finish();
                break;

            case R.id.image_scan:
                Intent intent = new Intent();
                intent.setClass(this, CaptureActivity.class);
                startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if (data == null) {
                    Utils.myToast(mContext, "没有扫描到二维码");
                    break;
                } else {
                    mProgressHUD.setMessage("二维码解析中...");
                    mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
                    mProgressHUD.show();
                    Bundle bundle = data.getExtras();
                    String scanResult = bundle.getString("result");
                    Log.e("二维码内容：", scanResult);
                    Utils.myToast(mContext, "二维码扫描成功，请稍候...");
                    String url = "http://ga.iotone.cn/";
                    if (scanResult.contains(url)) {
                        JSONObject object = new JSONObject();
                        try {
                            object.put("QRCode", url);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        HashMap<String, String> map = new HashMap<>();
                        map.put("token", Constants.getToken());
                        map.put("cardType", "1005");
                        map.put("taskId", "");
                        map.put("DataTypeCode", "ParseQRcode");
                        map.put("content", object.toString());
                        WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map, new WebServiceUtils.WebServiceCallBack() {
                            @Override
                            public void callBack(String result) {
                                if (result != null) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(result);
                                        int resultCode = jsonObject.getInt("ResultCode");
                                        String resultText = jsonObject.getString("ResultText");
                                        if (resultCode == 0) {
                                            String content = jsonObject.getString("Content");
                                            JSONObject obj = new JSONObject(content);
                                            String QRType = obj.getString("QRType");
                                            String type = obj.getString("Type");
                                            String Id = obj.getString("ID");
                                            if (QRType.equals("Q1")) {
                                                mProgressHUD.dismiss();
                                                checkDevice(Id);
                                            } else if (QRType.equals("Q2")) {
                                                mProgressHUD.dismiss();
                                                targetBandDevice(smartcareId, Id);
                                            } else if (QRType.equals("Q3")) {
                                                mProgressHUD.dismiss();
                                                //getShare(Id);
                                                Utils.myToast(mContext, "请在亲情关爱主页面关联老人");
                                            }
                                        } else {
                                            mProgressHUD.dismiss();
                                            Utils.myToast(mContext, resultText);
                                        }
                                    } catch (JSONException e) {
                                        mProgressHUD.dismiss();
                                        e.printStackTrace();
                                        Utils.myToast(mContext, "JSON解析出错");
                                    }
                                } else {
                                    mProgressHUD.dismiss();
                                    Utils.myToast(mContext, "请确认设备是否为关爱设备");
                                }
                            }
                        });

                    } else if (scanResult.contains("?U=")) {
                        final int i = scanResult.indexOf("?U=");
                        String content = scanResult.substring(i + 3);

                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("StrString", content);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("token", Constants.getToken());
                        map.put("cardType", "1005");
                        map.put("taskId", "");
                        map.put("DataTypeCode", "AESDecrypt");
                        map.put("content", jsonObject.toString());

                        WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map, new WebServiceUtils.WebServiceCallBack() {
                            @Override
                            public void callBack(String result) {
                                if (result != null) {
                                    JSONObject object = null;
                                    try {
                                        object = new JSONObject(result);
                                        int resultCode = object.getInt("ResultCode");
                                        String resultText = object.getString("ResultText");
                                        if (resultCode == 0) {
                                            mProgressHUD.dismiss();
                                            String content = object.getString("Content");
                                            JSONObject obj = new JSONObject(content);
                                            String code = obj.getString("StrString");
                                            checkDevice(code);
                                        } else {
                                            mProgressHUD.dismiss();
                                            Utils.myToast(mContext, resultText);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        mProgressHUD.dismiss();
                                        Utils.myToast(mContext, "JSON解析出错");
                                    }

                                } else {
                                    mProgressHUD.dismiss();
                                    Utils.myToast(mContext, "获取数据错误，请稍后重试！");
                                }
                            }
                        });
                    } else {
                        mProgressHUD.dismiss();
                        Utils.myToast(mContext, "非指定亲情关爱设备");
                        break;
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void targetBandDevice(String smartcareId, String id) {
        mProgressHUD.setMessage("设备绑定中...");
        mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
        mProgressHUD.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("SMARTCAREID", smartcareId);
            jsonObject.put("DEVICEID", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final HashMap<String, String> map = new HashMap<>();
        map.put("token", Constants.getToken());
        map.put("cardType", "1005");
        map.put("taskId", "");
        map.put("DataTypeCode", "TargetBandDevice");
        map.put("content", jsonObject.toString());
        WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map, new WebServiceUtils.WebServiceCallBack() {
            @Override
            public void callBack(String result) {
                if (result != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int resultCode = jsonObject.getInt("ResultCode");
                        String resultText = jsonObject.getString("ResultText");
                        if (resultCode == 0) {
                            mProgressHUD.dismiss();
                            Utils.myToast(mContext, "设备绑定成功");
                            initData();
                        } else {
                            mProgressHUD.dismiss();
                            Utils.myToast(mContext, resultText);
                        }
                    } catch (JSONException e) {
                        mProgressHUD.dismiss();
                        e.printStackTrace();
                        Utils.myToast(mContext, "JSON解析出错");
                    }
                } else {
                    mProgressHUD.dismiss();
                    Utils.myToast(mContext, "获取数据错误，请稍后重试！");
                }
            }
        });

    }

    /**
     * 检查设备编码
     *
     * @param id
     */
    private void checkDevice(final String id) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("DEVICEID", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("token", Constants.getToken());
        map.put("cardType", "1005");
        map.put("taskId", "");
        map.put("DataTypeCode", "CheckDeviceNumber");
        map.put("content", jsonObject.toString());

        WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map, new WebServiceUtils.WebServiceCallBack() {
            @Override
            public void callBack(String result) {
                if (result != null) {
                    try {
                        JSONObject json = new JSONObject(result);
                        int resultCode = json.getInt("ResultCode");
                        String resultText = json.getString("ResultText");
                        if (resultCode == 0) {
                            String content = json.getString("Content");
                            JSONObject object = new JSONObject(content);
                            String isMultiuse = object.getString("ISMULTIUSE");
                            String isOccupied = object.getString("ISOCCUPIED");
                            if (isMultiuse.equals("0")) {
                                if (isOccupied.equals("1")) {
                                    Utils.myToast(mContext, "该设备已被使用");
                                } else {
                                    targetBandDevice(smartcareId, id);
                                }
                            }
                        } else {
                            Utils.myToast(mContext, resultText);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Utils.myToast(mContext, "JSON解析出错");
                    }
                } else {
                    Utils.myToast(mContext, "获取数据错误，请稍后重试！");
                }
            }
        });
    }
}
