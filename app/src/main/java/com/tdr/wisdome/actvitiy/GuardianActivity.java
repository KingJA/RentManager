package com.tdr.wisdome.actvitiy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdr.wisdome.R;
import com.tdr.wisdome.util.Constants;
import com.tdr.wisdome.util.TextCountUtil;
import com.tdr.wisdome.util.Utils;
import com.tdr.wisdome.util.WebServiceUtils;
import com.tdr.wisdome.view.ZProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * 新增监护人
 * Created by Linus_Xie on 2016/8/23.
 */
public class GuardianActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "GuardianActivity";

    private String smartcareId = "";

    private Context mContext;

    private ZProgressHUD mProgressHUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_guardian);

        mContext = this;
        Intent intent = getIntent();
        smartcareId = intent.getStringExtra("smartcareId");
        initView();
        mProgressHUD = new ZProgressHUD(GuardianActivity.this);
        mProgressHUD.setMessage("新增监护人...");
        mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
    }

    private ImageView image_back;
    private TextView text_title;
    private TextView text_deal;

    private TextView text_guardianNum, text_delGuardian, text_code;
    private EditText edit_guardianName, edit_guardianIdentity, edit_phone, edit_guardianCode, edit_guardianAddress, edit_guardianAlternatePhone;

    private void initView() {
        image_back = (ImageView) findViewById(R.id.image_back);
        image_back.setOnClickListener(this);
        text_title = (TextView) findViewById(R.id.text_title);
        text_title.setText("添加监护人");
        text_deal = (TextView) findViewById(R.id.text_deal);
        text_deal.setText("完成");
        text_deal.setVisibility(View.VISIBLE);
        text_deal.setOnClickListener(this);

        text_guardianNum = (TextView) findViewById(R.id.text_guardianNum);
        text_guardianNum.setVisibility(View.GONE);
        text_delGuardian = (TextView) findViewById(R.id.text_delGuardian);
        text_delGuardian.setVisibility(View.GONE);

        edit_guardianName = (EditText) findViewById(R.id.edit_guardianName);
        edit_guardianIdentity = (EditText) findViewById(R.id.edit_guardianIdentity);
        edit_phone = (EditText) findViewById(R.id.edit_phone);
        text_code = (TextView) findViewById(R.id.text_code);
        text_code.setOnClickListener(this);
        edit_guardianCode = (EditText) findViewById(R.id.edit_guardianCode);
        edit_guardianAddress = (EditText) findViewById(R.id.edit_guardianAddress);
        edit_guardianAlternatePhone = (EditText) findViewById(R.id.edit_guardianAlternatePhone);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_back:
                finish();
                break;

            case R.id.text_deal:
                final String guardianName = edit_guardianName.getText().toString().trim();
                if (guardianName.equals("")) {
                    Utils.myToast(mContext, "请输入监护人姓名");
                    break;
                }
                final String guardianIdentity = edit_guardianIdentity.getText().toString().trim();
                if (guardianIdentity.equals("")) {
                    Utils.myToast(mContext, "请输入监护人身份证");
                    break;
                }
                final String phone = edit_phone.getText().toString().trim();
                if (phone.equals("")) {
                    Utils.myToast(mContext, "请输入监护人手机号码");
                    break;
                }
                final String guardianCode = edit_guardianCode.getText().toString().trim();
                if (guardianCode.equals("")) {
                    Utils.myToast(mContext, "请输入验证码");
                    break;
                }
                final String guardianAddress = edit_guardianAddress.getText().toString().trim();
                if (guardianAddress.equals("")) {
                    Utils.myToast(mContext, "请输入监护人联系地址");
                    break;
                }

                JSONObject json = new JSONObject();
                JSONObject jsonObject = new JSONObject();
                try {
                    json.put("GUARDIANNAME", guardianName);
                    json.put("GUARDIANIDCARD", guardianIdentity);
                    json.put("GUARDIANMOBILE", phone);
                    json.put("GUARDIANADDRESS", guardianAddress);
                    json.put("ENMERGENCYCALL", edit_guardianAlternatePhone.getText().toString().trim());

                    jsonObject.put("SMARTCAREID", smartcareId);
                    jsonObject.put("GUDIANINFO", json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mProgressHUD.show();
                HashMap<String, String> map = new HashMap<>();
                map.put("token", Constants.getToken());
                map.put("cardType", "1005");
                map.put("taskId", "");
                map.put("DataTypeCode", "AddGuardian");
                map.put("content", jsonObject.toString());

                WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map, new WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {
                        if (result != null) {
                            Log.e(TAG, result);
                            JSONObject json = null;
                            try {
                                json = new JSONObject(result);
                                int resultCode = json.getInt("ResultCode");
                                String resultText = Utils.initNullStr(json.getString("ResultText"));
                                if (resultCode == 0) {
                                    String guardianAlternatePhone = edit_guardianAlternatePhone.getText().toString().trim();
                                    Intent intent = new Intent();
                                    intent.setClass(GuardianActivity.this, OlderShareActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("activity", "guardianActivity");
                                    bundle.putString("result", guardianName + "," + phone + "," + guardianAddress);
                                    intent.putExtras(bundle);
                                    setResult(RESULT_OK, intent);
                                    finish();
                                } else {
                                    mProgressHUD.dismiss();
                                    Utils.myToast(mContext, resultText);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Utils.myToast(mContext, "JSON解析出错");
                            }

                        } else {
                            mProgressHUD.dismiss();
                            Utils.myToast(mContext, "获取数据错误，请稍后重试！");
                        }
                    }
                });


                break;

            case R.id.text_code:
                String phone1 = edit_phone.getText().toString();
                if (phone1.equals("")) {
                    Utils.myToast(GuardianActivity.this, "监护人1手机号码不可为空");
                    break;
                }
                TextCountUtil textCountUtilt1 = new TextCountUtil(GuardianActivity.this, 60000, 1000, text_code);
                textCountUtilt1.start();

                JSONObject jsonObject1 = new JSONObject();
                try {
                    jsonObject1.put("MOBILEPHONE", phone1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                HashMap<String, String> map1 = new HashMap<String, String>();
                map1.put("token", Constants.getToken());
                map1.put("cardType", "1005");
                map1.put("taskId", "");
                map1.put("DataTypeCode", "GET_SMS");
                map1.put("content", jsonObject1.toString());

                WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map1, new WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {
                        if (result != null) {
                            Log.e(TAG, result);
                            if (result != null) {
                                JSONObject object = null;
                                try {
                                    object = new JSONObject(result);
                                    int resultCode = object.getInt("ResultCode");
                                    String resultText = Utils.initNullStr(object.getString("ResultText"));
                                    if (resultCode == 0) {
                                        Utils.myToast(GuardianActivity.this, "获取验证码成功");
                                        String content = object.getString("Content");
                                        JSONObject json = new JSONObject(content);
                                        //SMSVerify1 = json.getString("SMSVERIFY");
                                        //phoneVerify1 = json.getString("MOBILEPHONE");
                                    } else {
                                        Utils.myToast(GuardianActivity.this, resultText);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Utils.myToast(GuardianActivity.this, "JSON解析出错");
                                }

                            } else {
                                Utils.myToast(GuardianActivity.this, "获取数据错误，请稍后重试！");
                            }
                        }
                    }
                });
                break;
        }
    }
}
