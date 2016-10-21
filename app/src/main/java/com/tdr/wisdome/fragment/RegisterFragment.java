package com.tdr.wisdome.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.tdr.wisdome.R;
import com.tdr.wisdome.actvitiy.SetPwdActivity;
import com.tdr.wisdome.base.BaseFragment;
import com.tdr.wisdome.util.Constants;
import com.tdr.wisdome.util.TimeCountUtil;
import com.tdr.wisdome.util.Utils;
import com.tdr.wisdome.util.WebServiceUtils;
import com.tdr.wisdome.view.material.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * 注册
 * Created by Linus_Xie on 2016/8/2.
 */
public class RegisterFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "LoginFragment";

    private MaterialEditText material_registerPhoone;
    private Button btn_code;

    private String phoneNum = "";

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_register, null);
        material_registerPhoone = (MaterialEditText) view.findViewById(R.id.material_registerPhoone);
        btn_code = (Button) view.findViewById(R.id.btn_code);
        btn_code.setOnClickListener(this);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_code:
                phoneNum = material_registerPhoone.getText().toString().trim();
                if (phoneNum.equals("")) {
                    Utils.myToast(mActivity, "请输入手机号码");
                    break;
                }

                if (!Utils.isPhone(phoneNum)) {
                    Utils.myToast(mActivity, "请输入正确的手机号");
                    break;
                }

                TimeCountUtil timeCountUtil = new TimeCountUtil(mActivity, 60000, 1000, btn_code);
                timeCountUtil.start();

                HashMap<String, String> map = new HashMap<>();
                JSONObject obj = new JSONObject();
                try {
                    obj.put("phone", phoneNum);
                    obj.put("CodeType", "1");
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
                                    Utils.myToast(mActivity, "获取验证码成功");
                                    Intent intent = new Intent();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("phoneNum", material_registerPhoone.getText().toString().trim());
                                    bundle.putString("activity", "register");
                                    bundle.putString("VerificationCodeID", VerificationCodeID);
                                    bundle.putString("VerificationCode", VerificationCode);
                                    intent.setClass(mActivity, SetPwdActivity.class);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    mActivity.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                                    // CloseActivityUtil.activityFinish(mActivity);
                                } else {
                                    Utils.myToast(mActivity, resultText);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Utils.myToast(mActivity, "JSON解析出错");
                            }

                        } else {
                            Utils.myToast(mActivity, "获取数据错误，请稍后重试！");
                        }
                    }
                });

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
