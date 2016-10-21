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
import com.tdr.wisdome.util.CloseActivityUtil;
import com.tdr.wisdome.util.Constants;
import com.tdr.wisdome.util.MD5;
import com.tdr.wisdome.util.TimeCountUtil;
import com.tdr.wisdome.util.Utils;
import com.tdr.wisdome.util.WebServiceUtils;
import com.tdr.wisdome.view.ZProgressHUD;
import com.tdr.wisdome.view.material.MaterialEditText;
import com.tdr.wisdome.view.niftydialog.NiftyDialogBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 * 注册--设置密码
 * Created by Linus_Xie on 2016/8/4.
 */
public class SetPwdActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "SetPwdActivity";

    private Context mContext;

    private String activity = "";
    private String phoneNum = "";
    private String VerificationCodeID = "";
    private String VerificationCode = "";

    private ZProgressHUD mProgressHUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setpwd);

        mContext = this;

        Bundle bundle = this.getIntent().getExtras();
        activity = bundle.getString("activity");
        phoneNum = bundle.getString("phoneNum");
        VerificationCodeID = bundle.getString("VerificationCodeID");
        VerificationCode = bundle.getString("VerificationCode");

        initView();

        if (!phoneNum.equals("")) {
            TimeCountUtil timeCountUtil = new TimeCountUtil(SetPwdActivity.this, 60000, 1000, btn_waiting);
            timeCountUtil.start();
        }

    }

    private RelativeLayout relative_title;
    private ImageView image_back;
    private TextView text_title;

    private MaterialEditText material_code, material_pwd, material_confirmPwd;
    private Button btn_complete, btn_waiting;

    private void initView() {
        relative_title = (RelativeLayout) findViewById(R.id.relative_title);
        relative_title.setBackgroundColor(getResources().getColor(R.color.colorStatus));

        image_back = (ImageView) findViewById(R.id.image_back);
        image_back.setBackgroundResource(R.drawable.imageback_whitechange);
        image_back.setOnClickListener(this);
        text_title = (TextView) findViewById(R.id.text_title);
        text_title.setTextColor(getResources().getColor(R.color.white));
        text_title.setText("设置密码");

        material_code = (MaterialEditText) findViewById(R.id.material_code);
        material_pwd = (MaterialEditText) findViewById(R.id.material_pwd);
        material_confirmPwd = (MaterialEditText) findViewById(R.id.material_confirmPwd);

        btn_complete = (Button) findViewById(R.id.btn_complete);
        btn_complete.setOnClickListener(this);
        btn_waiting = (Button) findViewById(R.id.btn_waiting);
        btn_waiting.setOnClickListener(this);

        mProgressHUD = new ZProgressHUD(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_back:
                finish();
                break;

            case R.id.btn_complete:
                String code = material_code.getText().toString().trim();
                if (code.equals("")) {
                    Utils.myToast(mContext, "请输入验证码");
                    break;
                }
                String pwd = material_pwd.getText().toString();
                String confirmPwd = material_confirmPwd.getText().toString();
                if ((!pwd.equals(confirmPwd)) || pwd.equals("") || pwd.length() < 6 || pwd.length() > 16) {
                    Utils.myToast(mContext, "密码长度应为6-16位或两次密码不一致");
                    break;
                }

                HashMap<String, String> map = new HashMap<>();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("Phone", phoneNum);
                    jsonObject.put("UserName", "");
                    jsonObject.put("UserPassword", MD5.getMD5(pwd));
                    jsonObject.put("VerificationCode", code);
                    jsonObject.put("VerificationCodeID", VerificationCodeID);
                    jsonObject.put("IsValid", "1");
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                map.put("token", "");
                map.put("cardType", "");
                map.put("taskId", "");
                if (activity.equals("register")) {
                    mProgressHUD.setMessage("注册中...");
                    mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
                    mProgressHUD.show();
                    map.put("DataTypeCode", "AddUsers");
                } else if (activity.equals("forgetPwd")) {
                    mProgressHUD.setMessage("密码重置中...");
                    mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
                    mProgressHUD.show();
                    map.put("DataTypeCode", "ChangePassword");
                }

                map.put("content", jsonObject.toString());

                WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map, new WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {
                        if (result != null) {
                            Log.e(TAG, result);
                            try {
                                JSONObject json = new JSONObject(result);
                                int resultCode = json.getInt("ResultCode");
                                String resultText = Utils.initNullStr(json.getString("ResultText"));
                                if (resultCode == 0) {
                                    mProgressHUD.dismiss();
                                    String content = json.getString("Content");
                                    Utils.myToast(mContext, resultText);
                                    nextStep(content);
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

            case R.id.btn_waiting:

                TimeCountUtil timeCountUtil = new TimeCountUtil(SetPwdActivity.this, 60000, 1000, btn_waiting);
                timeCountUtil.start();

                String codeType = "";
                if (activity.equals("register")) {
                    codeType = "1";
                } else {
                    codeType = "2";
                }

                HashMap<String, String> map1 = new HashMap<>();
                JSONObject obj = new JSONObject();
                try {
                    obj.put("phone", phoneNum);
                    obj.put("CodeType", codeType);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                map1.put("token", "");
                map1.put("cardType", "");
                map1.put("taskId", "");
                map1.put("DataTypeCode", "SendCodeSms");
                map1.put("content", obj.toString());

                WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map1, new WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {
                        if (result != null) {
                            Log.e(TAG, result);
                            try {
                                JSONObject object = new JSONObject(result);
                                int resultCode = object.getInt("ResultCode");
                                String resultText = Utils.initNullStr(object.getString("ResultText"));
                                if (resultCode == 0) {
                                    Utils.myToast(mContext, "获取验证码成功");
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

    private void nextStep(String content) {
        if (activity.equals("register")) {
            Intent intent = new Intent(SetPwdActivity.this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);
            finish();
           /* try {
                JSONObject json = new JSONObject(content);
                Constants.setToken(Utils.initNullStr(json.getString("UserID")));
            } catch (JSONException e) {
                e.printStackTrace();
            }*/

            //弹出对话框，完善资料
           // dialogShow(0, "完善资料，以获得更多的权限", content);
        } else if (activity.equals("forgetPwd")) {
            Intent intent = new Intent(mContext, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            CloseActivityUtil.activityFinish(SetPwdActivity.this);
        }
    }

    private NiftyDialogBuilder dialogBuilder;
    private NiftyDialogBuilder.Effectstype effectstype;

    private void dialogShow(final int flag, String message, String content) {
        if (dialogBuilder != null && dialogBuilder.isShowing())
            return;

        dialogBuilder = NiftyDialogBuilder.getInstance(this);

        if (flag == 0) {
            effectstype = NiftyDialogBuilder.Effectstype.Fadein;
            dialogBuilder.withTitle("提示").withTitleColor("#333333").withMessage(message)
                    .isCancelableOnTouchOutside(false).withEffect(effectstype).withButton1Text("取消")
                    .setCustomView(R.layout.custom_view, mContext).withButton2Text("确认").setButton1Click(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogBuilder.dismiss();
                    Intent intent = new Intent(SetPwdActivity.this, LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.slide_in_left,
                            android.R.anim.slide_out_right);
                    finish();
                }
            }).setButton2Click(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogBuilder.dismiss();
                    Intent intent = new Intent();
                    intent.setClass(mContext, PerfectActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("activity", TAG);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    CloseActivityUtil.activityFinish(SetPwdActivity.this);
                }
            }).show();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
