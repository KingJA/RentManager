package com.tdr.wisdome.actvitiy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tdr.wisdome.R;
import com.tdr.wisdome.util.Constants;
import com.tdr.wisdome.util.TimeCountUtil;
import com.tdr.wisdome.util.Utils;
import com.tdr.wisdome.util.WebServiceUtils;
import com.tdr.wisdome.view.material.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * 忘记密码
 * Created by Linus_Xie on 2016/8/4.
 */
public class ForgetPwdActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "ForgetPwdActivity";

    private Context mContext;

    private String phoneNum = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpwd);

        mContext = this;

        initView();
    }

    private RelativeLayout relative_title;
    private ImageView image_back;
    private TextView text_title;

    private MaterialEditText material_forgetPhoone;
    private Button btn_code;

    private void initView() {
        //StatusBarUtils.compat(this, getResources().getColor(R.color.colorStatus));
        relative_title = (RelativeLayout) findViewById(R.id.relative_title);
        relative_title.setBackgroundColor(getResources().getColor(R.color.colorStatus));
        image_back = (ImageView) findViewById(R.id.image_back);
        image_back.setBackgroundResource(R.drawable.imageback_whitechange);
        image_back.setOnClickListener(this);
        text_title = (TextView) findViewById(R.id.text_title);
        text_title.setTextColor(getResources().getColor(R.color.white));
        text_title.setText("忘记密码");

        material_forgetPhoone = (MaterialEditText) findViewById(R.id.material_forgetPhoone);

        btn_code = (Button) findViewById(R.id.btn_code);
        btn_code.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_back:
                finish();
                break;

            case R.id.btn_code:
                phoneNum = material_forgetPhoone.getText().toString().trim();
                if (phoneNum.equals("")) {
                    Utils.myToast(mContext, "请输入手机号码");
                    break;
                }

                if (!Utils.isPhone(phoneNum)) {
                    Utils.myToast(mContext, "请输入正确的手机号");
                    break;
                }

                TimeCountUtil timeCountUtil = new TimeCountUtil(ForgetPwdActivity.this, 60000, 1000, btn_code);
                timeCountUtil.start();

                HashMap<String, String> map = new HashMap<>();
                JSONObject obj = new JSONObject();
                try {
                    obj.put("phone", phoneNum);
                    obj.put("CodeType", "2");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                map.put("token", "");
                map.put("cardType", "");
                map.put("taskId", "");
                map.put("DataTypeCode", "SendCodeSms");
                map.put("content", obj.toString());

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
                                    String content = object.getString("Content");
                                    JSONObject obj = new JSONObject(content);
                                    String VerificationCodeID = obj.getString("VerificationCodeID");
                                    String VerificationCode = obj.getString("VerificationCode");
                                    Utils.myToast(mContext, "获取验证码成功");
                                    Intent intent = new Intent();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("phoneNum", material_forgetPhoone.getText().toString().trim());
                                    bundle.putString("activity", "forgetPwd");
                                    bundle.putString("VerificationCodeID", VerificationCodeID);
                                    bundle.putString("VerificationCode", VerificationCode);
                                    intent.setClass(mContext, SetPwdActivity.class);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                                    // CloseActivityUtil.activityFinish(mActivity);
                                    finish();
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

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
