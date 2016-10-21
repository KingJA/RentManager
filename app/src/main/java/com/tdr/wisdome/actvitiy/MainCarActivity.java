package com.tdr.wisdome.actvitiy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tdr.wisdome.R;
import com.tdr.wisdome.adapter.MainCarAdapter;
import com.tdr.wisdome.model.BikeCode;
import com.tdr.wisdome.model.CarInfo;
import com.tdr.wisdome.util.Constants;
import com.tdr.wisdome.util.Utils;
import com.tdr.wisdome.util.WebServiceUtils;
import com.tdr.wisdome.view.ZProgressHUD;
import com.tdr.wisdome.view.popupwindow.CarPop;

import net.tsz.afinal.FinalDb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 我的车
 * Created by Linus_Xie on 2016/8/19.
 */
public class MainCarActivity extends Activity implements View.OnClickListener, CarPop.OnCarPopClickListener {
    private static final String TAG = "MainCarActivity";

    private CarPop carPop;

    private Context mContext;

    private ZProgressHUD mProgressHUD;

    private MainCarAdapter mainCarAdapter;
    private List<CarInfo> listCar = new ArrayList<>();

    private FinalDb db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maincar);

        mContext = this;
        db = FinalDb.create(mContext, "WisdomE.db", false);

        initView();

        carPop = new CarPop(image_scan, MainCarActivity.this);
        carPop.setOnCarPopClickListener(MainCarActivity.this);

        initData();
    }

    private ImageView image_back;
    private TextView text_title;
    private ImageView image_scan;

    private ListView list_car;

    private LinearLayout layout_noData;

    private void initView() {
        image_back = (ImageView) findViewById(R.id.image_back);
        image_back.setOnClickListener(this);
        text_title = (TextView) findViewById(R.id.text_title);
        text_title.setText("我的车");
        image_scan = (ImageView) findViewById(R.id.image_scan);
        image_scan.setOnClickListener(this);
        image_scan.setBackgroundResource(R.mipmap.ic_add);
        image_scan.setVisibility(View.VISIBLE);

        list_car = (ListView) findViewById(R.id.list_car);
        mainCarAdapter = new MainCarAdapter(listCar, mContext);
        list_car.setAdapter(mainCarAdapter);
        mProgressHUD = new ZProgressHUD(mContext);

        layout_noData = (LinearLayout) findViewById(R.id.layout_noData);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_back:
                finish();
                break;

            case R.id.image_scan:
                carPop.showPopupWindowDownOffset();
                break;
        }
    }

    private void initData() {
        getCode();
    }

    private void getCarList() {
        mProgressHUD.setMessage("车辆加载中...");
        mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
        mProgressHUD.show();
        final HashMap<String, String> map = new HashMap<>();
        map.put("token", Constants.getToken());
        map.put("cardType", "1003");
        map.put("taskId", "");
        map.put("Encryption", "");
        map.put("DataTypeCode", "GetBindingList");
        map.put("content", "");
        WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map, new WebServiceUtils.WebServiceCallBack() {
            @Override
            public void callBack(String result) {
                if (result != null) {
                    Log.e(TAG, result);
                    try {
                        JSONObject object = new JSONObject(result);
                        int resultCode = object.getInt("ResultCode");
                        String resultText = Utils.initNullStr(object.getString("ResultText"));
                        if (resultCode == 0) {
                            String carList = object.getString("Content");
                            JSONArray array = new JSONArray(carList);
                            if (array.length() > 0) {
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject obj = array.getJSONObject(i);
                                    CarInfo mInfo = new CarInfo();
                                    mInfo.setBindingID(Utils.initNullStr(obj.getString("BindingID")));
                                    mInfo.setEcID(Utils.initNullStr(obj.getString("EcID")));
                                    mInfo.setCreateTime(Utils.initNullStr(obj.getString("CreateTime")));
                                    mInfo.setPlateNumber(Utils.initNullStr(obj.getString("PlateNumber")));
                                    mInfo.setIdentity(Utils.initNullStr(obj.getString("CardID")));
                                    mInfo.setPhone(Utils.initNullStr(obj.getString("Phone")));
                                    mInfo.setStatus(Utils.initNullStr(obj.getString("Status")));
                                    mInfo.setIsRead(Utils.initNullStr(obj.getString("IsRead")));
                                    listCar.add(mInfo);
                                }
                                list_car.setVisibility(View.VISIBLE);
                                layout_noData.setVisibility(View.GONE);
                                mainCarAdapter.notifyDataSetChanged();
                            } else {
                                //没有车辆处理，缺省页
                                list_car.setVisibility(View.GONE);
                                layout_noData.setVisibility(View.VISIBLE);

                            }
                            mProgressHUD.dismiss();
                        } else {
                            mProgressHUD.dismiss();
                            Utils.myToast(mContext, resultText);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        mProgressHUD.dismiss();
                        Utils.myToast(mContext, "JSON解析异常");
                    }
                } else {
                    mProgressHUD.dismiss();
                    Utils.myToast(mContext, "获取数据错误，请稍后重试！");
                }
            }
        });
    }

    private void getCode() {
        mProgressHUD.setMessage("下载编码表中...");
        mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
        mProgressHUD.show();
        JSONObject jsonObject = new JSONObject();
        String lastUpdateTime = "";
        if (Constants.getLastUpdateTime().equals("")) {
            lastUpdateTime = "1990-01-01 00:00:01";
        } else {
            lastUpdateTime = Constants.getLastUpdateTime();
        }
        try {
            jsonObject.put("updatetime", lastUpdateTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("token", Constants.getToken());
        map.put("cardType", "1003");
        map.put("taskId", "");
        map.put("Encryption", "");
        map.put("DataTypeCode", "GetCodeList");
        map.put("content", jsonObject.toString());
        WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map, new WebServiceUtils.WebServiceCallBack() {

            @Override
            public void callBack(String result) {
                if (result != null) {
                    try {
                        JSONObject object = new JSONObject(result);
                        int resultCode = object.getInt("ResultCode");
                        String resultText = Utils.initNullStr(object.getString("ResultText"));
                        if (resultCode == 0) {
                            List<BikeCode> list = new ArrayList<BikeCode>();
                            String codeList = object.getString("Content");
                            JSONArray array = new JSONArray(codeList);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject obj = array.getJSONObject(i);
                                BikeCode bi = new BikeCode();
                                bi.setCodeId(obj.getString("CODEID"));
                                bi.setCode(obj.getString("CODE"));
                                bi.setName(obj.getString("NAME"));
                                bi.setType(obj.getString("TYPE"));
                                bi.setRemark(obj.getString("REMARK"));
                                bi.setSort(obj.getString("SORT"));
                                list.add(bi);
                            }
                            int max = 0;
                            if (list != null && list.size() > 0) {
                                db.deleteAll(BikeCode.class);
                                max = list.size();
                                for (int i = 0; i < max; i++) {
                                    db.save(list.get(i));
                                    Log.e(TAG, "已完成：" + Utils.getDivide(i, max) + "%");
                                }
                                Constants.setLastUpdateTime(Utils.getNowTime());
                            }
                            mProgressHUD.dismiss();
                            getCarList();
                        } else {
                            mProgressHUD.dismiss();
                            Log.e(TAG, resultText);
                            getCarList();
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
    }

    @Override
    public void onCarPop(int position) {
        switch (position) {
            case 0:
                Intent intent0 = new Intent(mContext, CarBindingActivity.class);
                startActivity(intent0);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                finish();
                break;

            case 1:
                Intent intent1 = new Intent(mContext, PreRegistrationActivity.class);
                startActivity(intent1);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;

        }
        carPop.dismiss();
    }
}
