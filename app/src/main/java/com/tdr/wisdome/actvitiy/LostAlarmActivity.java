package com.tdr.wisdome.actvitiy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdr.wisdome.R;
import com.tdr.wisdome.util.Constants;
import com.tdr.wisdome.util.Utils;
import com.tdr.wisdome.util.WebServiceUtils;
import com.tdr.wisdome.view.ZProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Linus_Xie on 2016/8/27.
 */
public class LostAlarmActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "LostAlarmActivity";

    private Context mContext;

    private ZProgressHUD mProgressHUD;

    private String smartcareId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lostalarm);
        smartcareId = getIntent().getStringExtra("smartcareId");
        mContext = this;
        initView();
    }

    private ImageView image_back;
    private TextView text_title;
    private TextView text_deal;

    private EditText edit_lostAlarm;

    private void initView() {
        image_back = (ImageView) findViewById(R.id.image_back);
        image_back.setOnClickListener(this);
        text_title = (TextView) findViewById(R.id.text_title);
        text_title.setText("丢失报警");
        text_deal = (TextView) findViewById(R.id.text_deal);
        text_deal.setText("报警");
        text_deal.setVisibility(View.VISIBLE);
        text_deal.setOnClickListener(this);

        edit_lostAlarm = (EditText) findViewById(R.id.edit_lostAlarm);

        mProgressHUD = new ZProgressHUD(this);
        mProgressHUD.setMessage("报警中...");
        mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_back:
                finish();
                break;

            case R.id.text_deal:
                mProgressHUD.show();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("SMARTCAREID", smartcareId);
                    jsonObject.put("REPORTERID", Constants.getUserId());
                    jsonObject.put("REPORTERNAME", "");
                    jsonObject.put("REPORTMOBILE", "");
                    jsonObject.put("CAUTIONTIME", "");
                    jsonObject.put("LASTSHOWUP", "");
                    jsonObject.put("SIGNALMENT", edit_lostAlarm.getText().toString().trim());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                HashMap<String, String> map = new HashMap<>();
                map.put("token", Constants.getToken());
                map.put("cardType", "1005");
                map.put("taskId", "");
                map.put("DataTypeCode", "LostAlarm");
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
                                    mProgressHUD.dismiss();
                                    Intent intent = new Intent(LostAlarmActivity.this, MainCareActivity.class);
                                    startActivity(intent);
                                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                                    finish();
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

                break;
        }

    }
}
