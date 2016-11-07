package com.tdr.wisdome.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kingja.cardpackage.activity.HomeActivity;
import com.tdr.wisdome.R;
import com.tdr.wisdome.actvitiy.ForgetPwdActivity;
import com.tdr.wisdome.actvitiy.MainActivity;
import com.tdr.wisdome.base.BaseFragment;
import com.tdr.wisdome.jpush.ExampleUtil;
import com.tdr.wisdome.model.PhoneInfo;
import com.tdr.wisdome.util.CloseActivityUtil;
import com.tdr.wisdome.util.Constants;
import com.tdr.wisdome.util.MD5;
import com.tdr.wisdome.util.PhoneUtil;
import com.tdr.wisdome.util.Utils;
import com.tdr.wisdome.util.WebServiceUtils;
import com.tdr.wisdome.view.ZProgressHUD;
import com.tdr.wisdome.view.material.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import cn.jpush.android.api.JPushInterface;

/**
 * 登录
 * Created by Linus_Xie on 2016/8/2.
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener, Handler.Callback {

    private static final String TAG = "LoginFragment";

    private MaterialEditText material_loginName, material_loginPwd;
    private TextView text_forgetPwd;
    private Button btn_login;

    private ZProgressHUD mProgressHUD;

    private String imei = "";

    private static final int READ_PHONE_PERM = 122;

    @Override
    public View initView() {
        initProgressHUD();
        final View view = View.inflate(mActivity, R.layout.fragment_login, null);
        material_loginName = (MaterialEditText) view.findViewById(R.id.material_loginName);
        material_loginPwd = (MaterialEditText) view.findViewById(R.id.material_loginPwd);
        text_forgetPwd = (TextView) view.findViewById(R.id.text_forgetPwd);
        text_forgetPwd.setOnClickListener(this);
        btn_login = (Button) view.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        return view;
    }

    @Override
    public void initData() {
        PhoneInfo phoneInfo = new PhoneUtil(mActivity).getInfo();
        imei = phoneInfo.getIMEI();
        super.initData();
    }

    private void initProgressHUD() {
        mProgressHUD = new ZProgressHUD(mActivity);
        mProgressHUD.setMessage("登录中...");
        mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String userName = material_loginName.getText().toString();
                if (userName.equals("")) {
                    Utils.myToast(mActivity, "请输入用户名");
                    break;
                }
                String userPwd = material_loginPwd.getText().toString();
                if (userPwd.equals("")) {
                    Utils.myToast(mActivity, "请输入密码");
                    break;
                }

                mProgressHUD.show();

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("Phone", material_loginName.getText().toString());
                    jsonObject.put("UserPassword", MD5.getMD5(material_loginPwd.getText().toString()));
                    jsonObject.put("channelid", JPushInterface.getRegistrationID(mActivity));
                    Log.e("channelid", ExampleUtil.getAppKey(mActivity));
                    jsonObject.put("channeltype", "1");
                    jsonObject.put("LoginIP", "");
                    jsonObject.put("IMEI", imei);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                HashMap<String, String> map = new HashMap<>();
                map.put("token", "");
                map.put("cardType", "");
                map.put("taskId", "");
                map.put("DataTypeCode", "Login");
                map.put("content", jsonObject.toString());

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
                                    Constants.setUserId(Utils.initNullStr(obj.getString("UserID")));
                                    Constants.setUserPhone(Utils.initNullStr(obj.getString("Phone")));
                                    Constants.setUserName(Utils.initNullStr(obj.getString("UserName")));
                                    Constants.setUserIdentitycard(Utils.initNullStr(obj.getString("IDCard")));
                                    Constants.setFaceId(Utils.initNullStr(obj.getString("FaceID")));
                                    Constants.setFaceBase(Utils.initNullStr(obj.getString("FaceBase")));
                                    Constants.setToken(Utils.initNullStr(obj.getString("token")));
                                    Constants.setCertification(Utils.initNullStr(obj.getString("Certification")));
                                    Constants.setRealName(Utils.initNullStr(obj.getString("Realname")));
                                    Constants.setPermanentAddr(Utils.initNullStr(obj.getString("Address")));
                                    String City = obj.getString("City");
                                    if (City != null) {
                                        JSONObject json = new JSONObject(City);
                                        Constants.setCityName(Utils.initNullStr(json.getString("CityName")));
                                        Constants.setCityCode(Utils.initNullStr(json.getString("CityCode")));
                                    }
                                    mProgressHUD.dismiss();
                                    Intent intent = new Intent(mActivity, HomeActivity.class);
                                    startActivity(intent);
                                    mActivity.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                                    CloseActivityUtil.activityFinish(mActivity);

                                } else {
                                    mProgressHUD.dismiss();
                                    Utils.myToast(mActivity, resultText);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                mProgressHUD.dismiss();
                                Utils.myToast(mActivity, "JSON解析出错");
                            }
                        } else {
                            mProgressHUD.dismiss();
                            Utils.myToast(mActivity, "获取数据错误，请稍后重试！");
                        }
                    }
                });
                break;

            case R.id.text_forgetPwd:
                Intent intent = new Intent(mActivity, ForgetPwdActivity.class);
                startActivity(intent);
                mActivity.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
        }


    }


    @Override
    public boolean handleMessage(Message message) {
        return false;
    }
}
