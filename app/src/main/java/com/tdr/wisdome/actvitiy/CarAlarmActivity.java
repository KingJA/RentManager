package com.tdr.wisdome.actvitiy;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tdr.wisdome.R;
import com.tdr.wisdome.adapter.CarAlarmAdapter;
import com.tdr.wisdome.model.CarAlarmInfo;
import com.tdr.wisdome.util.Constants;
import com.tdr.wisdome.util.Utils;
import com.tdr.wisdome.util.WebServiceUtils;
import com.tdr.wisdome.view.ZProgressHUD;
import com.tdr.wisdome.view.drop.CoverManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 车辆报警记录
 * Created by Linus_Xie on 2016/8/24.
 */
public class CarAlarmActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "CarAlarmActivity";

    private Context mContext;

    private CarAlarmAdapter mCarAlarmAdapter;
    private List<CarAlarmInfo> listCarAlarm = new ArrayList<>();

    private String plateNumber = "";

    private ZProgressHUD mProgressHUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caralarm);
        /*仿QQ气泡*/
        CoverManager.getInstance().init(this);
        mContext = this;
        plateNumber = getIntent().getStringExtra("plateNumber");
        initView();
        initData();
    }

    private ImageView image_back;
    private TextView text_title;

    private ListView list_carAlarm;

    private void initView() {
        image_back = (ImageView) findViewById(R.id.image_back);
        image_back.setOnClickListener(this);
        text_title = (TextView) findViewById(R.id.text_title);
        text_title.setText("报警记录");

        list_carAlarm = (ListView) findViewById(R.id.list_carAlarm);
        mCarAlarmAdapter = new CarAlarmAdapter(mContext, listCarAlarm);
        list_carAlarm.setAdapter(mCarAlarmAdapter);
        CoverManager.getInstance().setMaxDragDistance(250);
        CoverManager.getInstance().setEffectDuration(150);

        mProgressHUD = new ZProgressHUD(mContext);
    }

    private void initData() {
        mProgressHUD.setMessage("请求报警列表...");
        mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
        mProgressHUD.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("platenumber", plateNumber);
            jsonObject.put("pageNum", "0");
            jsonObject.put("pageSize", "10");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("token", Constants.getToken());
        map.put("cardType", "1003");
        map.put("taskId", "");
        map.put("Encryption", "");
        map.put("DataTypeCode", "GetMessagePager");
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
                            String alramList = object.getString("Content");
                            JSONArray array = new JSONArray(alramList);
                            if (array.length() > 0) {
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject obj = array.getJSONObject(i);
                                    CarAlarmInfo mInfo = new CarAlarmInfo();
                                    mInfo.setListId(Utils.initNullStr(obj.getString("LISTID")));
                                    mInfo.setPlateNumber(Utils.initNullStr(obj.getString("PLATENUMBER")));
                                    mInfo.setMonitorTime(Utils.initNullStr(obj.getString("MONITORTIME")));
                                    mInfo.setMessage(Utils.initNullStr(obj.getString("MESSAGE")));
                                    mInfo.setIsRead(Utils.initNullStr(obj.getString("IsRead")));
                                    listCarAlarm.add(mInfo);
                                }
                                mProgressHUD.dismiss();
                                mCarAlarmAdapter.notifyDataSetChanged();
                            } else {

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
