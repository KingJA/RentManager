package com.tdr.wisdome.actvitiy;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tdr.wisdome.R;
import com.tdr.wisdome.adapter.PackageAdapter;
import com.tdr.wisdome.model.PackageItem;
import com.tdr.wisdome.util.Constants;
import com.tdr.wisdome.util.Utils;
import com.tdr.wisdome.util.WebServiceUtils;
import com.tdr.wisdome.view.ZProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 我的卡包--管理
 * Created by Linus_Xie on 2016/8/7.
 */
public class PackageActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "PackageActivity";

    private Context mContext;

    private List<PackageItem> data = new ArrayList<>();
    private List<PackageItem> data1 = new ArrayList<>();
    private List<PackageItem> data2 = new ArrayList<>();
    private List<PackageItem> temp = new ArrayList<>();
    private PackageAdapter mPackageAdapter;

    private ZProgressHUD mProgressHUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package);

        mContext = this;

        initView();
        initData();
    }

    private ImageView image_back;
    private TextView text_title;

    private ListView list_package;

    private void initView() {
        image_back = (ImageView) findViewById(R.id.image_back);
        image_back.setOnClickListener(this);
        text_title = (TextView) findViewById(R.id.text_title);
        text_title.setText("我的卡包");

        list_package = (ListView) findViewById(R.id.list_package);
        mPackageAdapter = new PackageAdapter(mContext, data);

        mProgressHUD = new ZProgressHUD(mContext);
        mProgressHUD.setMessage("设备配置表请求中...");
        mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);

    }

    private void initData() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("citycode", Constants.getCityCode());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("token", Constants.getToken());
        map.put("cardType", "");
        map.put("taskId", "");
        map.put("DataTypeCode", "GetCityCardList");
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
                            JSONArray array = new JSONArray(content);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject json = array.getJSONObject(i);
                                String cardCode = json.getString("CardCode");//卡片Code
                                String deviceName = "";
                                if (cardCode.contains(Constants.getCardCode())) {
                                    deviceName = "已拥有";
                                } else {
                                    deviceName = "可添加";
                                }
                                String CardName = json.getString("CardName");

                                int image = 0;
                                if (CardName.equals("我的住房")) {
                                    image = R.mipmap.image_house;
                                } else if (CardName.equals("我的出租房")) {
                                    image = R.mipmap.image_rental;
                                } else if (CardName.equals("我的店")) {
                                    image = R.mipmap.image_shop;
                                } else if (CardName.equals("我的车")) {
                                    image = R.mipmap.image_car;
                                } else if (CardName.equals("亲情关爱")) {
                                    image = R.mipmap.image_care;
                                } else if (CardName.equals("服务商城")) {
                                    image = R.mipmap.image_store;
                                }

                                PackageItem groupItem = new PackageItem(deviceName, CardName, image);
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
                            list_package.setAdapter(mPackageAdapter);
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
        }
    }
}
