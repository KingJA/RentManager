package com.tdr.wisdome.actvitiy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tdr.wisdome.R;
import com.tdr.wisdome.model.GuardianInfo;
import com.tdr.wisdome.model.OlderInfo;
import com.tdr.wisdome.util.Constants;
import com.tdr.wisdome.util.TextCountUtil;
import com.tdr.wisdome.util.Utils;
import com.tdr.wisdome.util.WebServiceUtils;
import com.tdr.wisdome.view.ZProgressHUD;
import com.tdr.wisdome.view.niftydialog.NiftyDialogBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * 添加监护人信息
 * Created by Linus_Xie on 2016/8/16.
 */
public class AddGuardianActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "AddGuardianActivity";

    private Context mContext;

    private OlderInfo mInfo = new OlderInfo();
    private GuardianInfo mGuardianInfo = new GuardianInfo();

    private int index = 1;

    //验证码MD5
    private String SMSVerify1 = "";
    private String phoneVerify1 = "";
    private String SMSVerify2 = "";
    private String phoneVerify2 = "";
    private String SMSVerify3 = "";
    private String phoneVerify3 = "";

    private ZProgressHUD mProgressHUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addguardian);

        mContext = this;

        initView();

        Bundle bundle = (Bundle) getIntent().getExtras().get("bundle");
        if (bundle != null) {
            mInfo = (OlderInfo) bundle.getSerializable("olderInfo");
            mGuardianInfo = (GuardianInfo) bundle.getSerializable("guardianInfo");
        }
        initData();
    }

    private void initData() {
        if (!mGuardianInfo.equals("")) {
            edit_guardianName1.setText(mGuardianInfo.getGuardianName());
            edit_guardianIdentity1.setText(mGuardianInfo.getGuardianIdCard());
            edit_phone1.setText(mGuardianInfo.getGuardianMobile());
            edit_guardianAddress1.setText(mGuardianInfo.getGuardianAddress());
            edit_guardianAlternatePhone1.setText(mGuardianInfo.getEnmergencyCall());
        }
    }

    private ImageView image_back;
    private TextView text_title;
    private TextView text_deal;

    private LinearLayout linear_guardian1, linear_guardian2, linear_guardian3;
    private TextView text_delGuardian1, text_code1, text_delGuardian2, text_code2, text_delGuardian3, text_code3;
    private EditText edit_guardianName1, edit_guardianIdentity1, edit_phone1, edit_guardianCode1, edit_guardianAddress1, edit_guardianAlternatePhone1,
            edit_guardianName2, edit_guardianIdentity2, edit_phone2, edit_guardianCode2, edit_guardianAddress2, edit_guardianAlternatePhone2,
            edit_guardianName3, edit_guardianIdentity3, edit_phone3, edit_guardianCode3, edit_guardianAddress3, edit_guardianAlternatePhone3;
    private RelativeLayout relative_addMoreGuardian;

    private void initView() {
        image_back = (ImageView) findViewById(R.id.image_back);
        image_back.setOnClickListener(this);
        text_title = (TextView) findViewById(R.id.text_title);
        text_title.setText("添加监护人");
        text_deal = (TextView) findViewById(R.id.text_deal);
        text_deal.setText("完成");
        text_deal.setVisibility(View.VISIBLE);
        text_deal.setOnClickListener(this);

        linear_guardian1 = (LinearLayout) findViewById(R.id.linear_guardian1);
        text_delGuardian1 = (TextView) findViewById(R.id.text_delGuardian1);
        text_delGuardian1.setOnClickListener(this);
        edit_guardianName1 = (EditText) findViewById(R.id.edit_guardianName1);
        edit_guardianIdentity1 = (EditText) findViewById(R.id.edit_guardianIdentity1);
        edit_phone1 = (EditText) findViewById(R.id.edit_phone1);
        text_code1 = (TextView) findViewById(R.id.text_code1);
        text_code1.setOnClickListener(this);
        edit_guardianCode1 = (EditText) findViewById(R.id.edit_guardianCode1);
        edit_guardianAddress1 = (EditText) findViewById(R.id.edit_guardianAddress1);
        edit_guardianAlternatePhone1 = (EditText) findViewById(R.id.edit_guardianAlternatePhone1);

        linear_guardian2 = (LinearLayout) findViewById(R.id.linear_guardian2);
        text_delGuardian2 = (TextView) findViewById(R.id.text_delGuardian2);
        text_delGuardian2.setOnClickListener(this);
        edit_guardianName2 = (EditText) findViewById(R.id.edit_guardianName2);
        edit_guardianIdentity2 = (EditText) findViewById(R.id.edit_guardianIdentity2);
        edit_phone2 = (EditText) findViewById(R.id.edit_phone2);
        text_code2 = (TextView) findViewById(R.id.text_code2);
        text_code2.setOnClickListener(this);
        edit_guardianCode2 = (EditText) findViewById(R.id.edit_guardianCode2);
        edit_guardianAddress2 = (EditText) findViewById(R.id.edit_guardianAddress2);
        edit_guardianAlternatePhone2 = (EditText) findViewById(R.id.edit_guardianAlternatePhone2);

        linear_guardian3 = (LinearLayout) findViewById(R.id.linear_guardian3);
        text_delGuardian3 = (TextView) findViewById(R.id.text_delGuardian3);
        text_delGuardian3.setOnClickListener(this);
        edit_guardianName3 = (EditText) findViewById(R.id.edit_guardianName3);
        edit_guardianIdentity3 = (EditText) findViewById(R.id.edit_guardianIdentity3);
        edit_phone3 = (EditText) findViewById(R.id.edit_phone3);
        text_code3 = (TextView) findViewById(R.id.text_code3);
        text_code3.setOnClickListener(this);
        edit_guardianCode3 = (EditText) findViewById(R.id.edit_guardianCode3);
        edit_guardianAddress3 = (EditText) findViewById(R.id.edit_guardianAddress3);
        edit_guardianAlternatePhone3 = (EditText) findViewById(R.id.edit_guardianAlternatePhone3);

        relative_addMoreGuardian = (RelativeLayout) findViewById(R.id.relative_addMoreGuardian);
        relative_addMoreGuardian.setOnClickListener(this);

        mProgressHUD = new ZProgressHUD(mContext);
        mProgressHUD.setMessage("关爱中...");
        mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_back:
                finish();
                break;

            case R.id.text_deal:
                String guardianName1 = edit_guardianName1.getText().toString().trim();
                if (guardianName1.equals("")) {
                    Utils.myToast(mContext, "请输入监护人1姓名");
                    break;
                }
                String guardianIdentity1 = edit_guardianIdentity1.getText().toString().toUpperCase().trim();
                if (guardianIdentity1.equals("")) {
                    Utils.myToast(mContext, "请输入监护人1身份证号");
                    break;
                }
                if (!Utils.isIDCard18(guardianIdentity1)) {
                    Utils.myToast(mContext, "请输入监护人1正确的身份证号");
                    break;
                }
                String uploadPhone1 = edit_phone1.getText().toString().trim();
                if (uploadPhone1.equals("")) {
                    Utils.myToast(mContext, "请输入监护人1手机号码");
                    break;
                }
                String guardianCode1 = edit_guardianCode1.getText().toString().trim();
                if (guardianCode1.equals("")) {
                    Utils.myToast(mContext, "请输入监护人1的验证码");
                    break;
                }
                String guardianAddress1 = edit_guardianAddress1.getText().toString().trim();
                if (guardianAddress1.equals("")) {
                    Utils.myToast(mContext, "请输入监护人1的联系地址");
                    break;
                }

                if (linear_guardian2.getVisibility() == 0) {
                    String guardianName2 = edit_guardianName2.getText().toString().trim();
                    if (guardianName2.equals("")) {
                        Utils.myToast(mContext, "请输入监护人2姓名");
                        break;
                    }
                    String guardianIdentity2 = edit_guardianIdentity2.getText().toString().toUpperCase().trim();
                    if (guardianIdentity2.equals("")) {
                        Utils.myToast(mContext, "请输入监护人2身份证号");
                        break;
                    }
                    if (!Utils.isIDCard18(guardianIdentity2)) {
                        Utils.myToast(mContext, "请输入监护人2正确的身份证号");
                        break;
                    }
                    String uploadPhone2 = edit_phone2.getText().toString().trim();
                    if (uploadPhone2.equals("")) {
                        Utils.myToast(mContext, "请输入监护人2手机号码");
                        break;
                    }
                    String guardianCode2 = edit_guardianCode2.getText().toString().trim();
                    if (guardianCode2.equals("")) {
                        Utils.myToast(mContext, "请输入监护人2的验证码");
                        break;
                    }
                    String guardianAddress2 = edit_guardianAddress2.getText().toString().trim();
                    if (guardianAddress2.equals("")) {
                        Utils.myToast(mContext, "请输入监护人2的联系地址");
                        break;
                    }
                }

                if (linear_guardian3.getVisibility() == 0) {
                    String guardianName3 = edit_guardianName3.getText().toString().trim();
                    if (guardianName3.equals("")) {
                        Utils.myToast(mContext, "请输入监护人3姓名");
                        break;
                    }
                    String guardianIdentity3 = edit_guardianIdentity3.getText().toString().toUpperCase().trim();
                    if (guardianIdentity3.equals("")) {
                        Utils.myToast(mContext, "请输入监护人3身份证号");
                        break;
                    }
                    if (!Utils.isIDCard18(guardianIdentity3)) {
                        Utils.myToast(mContext, "请输入监护人3正确的身份证号");
                        break;
                    }
                    String uploadPhone3 = edit_phone3.getText().toString().trim();
                    if (uploadPhone3.equals("")) {
                        Utils.myToast(mContext, "请输入监护人3手机号码");
                        break;
                    }
                    String guardianCode3 = edit_guardianCode3.getText().toString().trim();
                    if (guardianCode3.equals("")) {
                        Utils.myToast(mContext, "请输入监护人3的验证码");
                        break;
                    }
                    String guardianAddress3 = edit_guardianAddress3.getText().toString().trim();
                    if (guardianAddress3.equals("")) {
                        Utils.myToast(mContext, "请输入监护人3的联系地址");
                        break;
                    }
                }

                mProgressHUD.show();

                JSONObject jsonObject = new JSONObject();
                try {
                    JSONObject lrInfoObj = new JSONObject();
                    lrInfoObj.put("CUSTOMERNAME", mInfo.getCustomerName());
                    lrInfoObj.put("CUSTOMERIDCARD", mInfo.getCustomerIdCard());
                    lrInfoObj.put("CUSTOMMOBILE", mInfo.getCustomMobile());
                    lrInfoObj.put("CUSTOMERADDRESS", mInfo.getCustomerAddress());
                    lrInfoObj.put("TARGETTYPE", "0");

                    JSONObject lrParamObj = new JSONObject();
                    lrParamObj.put("CENTREPOINTLNG", mInfo.getCentrePointLng());
                    lrParamObj.put("CENTREPOINTLAT", mInfo.getCentrePointLat());
                    lrParamObj.put("RADIUS", mInfo.getRadius());

                    JSONObject photoObj = new JSONObject();
                    photoObj.put("CUSTOMERPHOTO", Constants.getBodyPhoto());

                    JSONObject healthObj = new JSONObject();
                    healthObj.put("HEALTHCONDITION", mInfo.getHealthCondition());
                    healthObj.put("EMTNOTICE", mInfo.getEmtnotice());

                    JSONObject registerInfoObj = new JSONObject();
                    registerInfoObj.put("REGERID", Constants.getUserId().replace("-", ""));
                    registerInfoObj.put("REGERMOBILE", Constants.getUserPhone());

                    JSONArray guarderListArray = new JSONArray();
                    JSONObject guarderListObj1 = new JSONObject();
                    guarderListObj1.put("GUARDIANNAME", edit_guardianName1.getText().toString().trim());
                    guarderListObj1.put("GUARDIANIDCARD", edit_guardianIdentity1.getText().toString().toUpperCase().trim());
                    guarderListObj1.put("GUARDIANMOBILE", edit_phone1.getText().toString().trim());
                    guarderListObj1.put("GUARDIANADDRESS", edit_guardianAddress1.getText().toString().trim());
                    guarderListObj1.put("ENMERGENCYCALL", edit_guardianAlternatePhone1.getText().toString().trim());
                    guarderListArray.put(guarderListObj1);

                    if (linear_guardian2.getVisibility() == 0) {
                        JSONObject guarderListObj2 = new JSONObject();
                        guarderListObj2.put("GUARDIANNAME", edit_guardianName2.getText().toString().trim());
                        guarderListObj2.put("GUARDIANIDCARD", edit_guardianIdentity2.getText().toString().toUpperCase().trim());
                        guarderListObj2.put("GUARDIANMOBILE", edit_phone2.getText().toString().trim());
                        guarderListObj2.put("GUARDIANADDRESS", edit_guardianAddress2.getText().toString().trim());
                        guarderListObj2.put("ENMERGENCYCALL", edit_guardianAlternatePhone2.getText().toString().trim());
                        guarderListArray.put(guarderListObj2);
                    }
                    if (linear_guardian3.getVisibility() == 0) {
                        JSONObject guarderListObj3 = new JSONObject();
                        guarderListObj3.put("GUARDIANNAME", edit_guardianName3.getText().toString().trim());
                        guarderListObj3.put("GUARDIANIDCARD", edit_guardianIdentity3.getText().toString().toUpperCase().trim());
                        guarderListObj3.put("GUARDIANMOBILE", edit_phone3.getText().toString().trim());
                        guarderListObj3.put("GUARDIANADDRESS", edit_guardianAddress3.getText().toString().trim());
                        guarderListObj3.put("ENMERGENCYCALL", edit_guardianAlternatePhone3.getText().toString().trim());
                        guarderListArray.put(guarderListObj3);
                    }
                    jsonObject.put("CARENUMBER", mInfo.getCareNumber());
                    jsonObject.put("LRINFO", lrInfoObj);
                    jsonObject.put("LRPARAM", lrParamObj);
                    jsonObject.put("PHOTOINFO", photoObj);
                    jsonObject.put("CUSTMERHEALTHINFO", healthObj);
                    jsonObject.put("REGISTERINFO", registerInfoObj);
                    jsonObject.put("GUARDERLIST", guarderListArray);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                HashMap<String, String> map = new HashMap<>();
                map.put("token", Constants.getToken());
                map.put("cardType", "1005");
                map.put("taskId", "");
                map.put("DataTypeCode", "AddElder");
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
                                    dialogShow(mInfo.getCustomerName());
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

                break;

            case R.id.text_code1:
                String phone1 = edit_phone1.getText().toString();
                if (phone1.equals("")) {
                    Utils.myToast(mContext, "监护人1手机号码不可为空");
                    break;
                }
                TextCountUtil textCountUtilt1 = new TextCountUtil(AddGuardianActivity.this, 60000, 1000, text_code1);
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
                                        Utils.myToast(mContext, "获取验证码成功");
                                        String content = object.getString("Content");
                                        JSONObject json = new JSONObject(content);
                                        SMSVerify1 = json.getString("SMSVERIFY");
                                        phoneVerify1 = json.getString("MOBILEPHONE");
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
                    }
                });

                break;

            case R.id.text_delGuardian2:
                linear_guardian2.setVisibility(View.GONE);
                break;

            case R.id.text_code2:
                String phone2 = edit_phone2.getText().toString();
                if (phone2.equals("")) {
                    Utils.myToast(mContext, "监护人2手机号码不可为空");
                    break;
                }
                TextCountUtil textCountUtilt2 = new TextCountUtil(AddGuardianActivity.this, 60000, 1000, text_code2);
                textCountUtilt2.start();
                JSONObject jsonObject2 = new JSONObject();
                try {
                    jsonObject2.put("MOBILEPHONE", phone2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                HashMap<String, String> map2 = new HashMap<String, String>();
                map2.put("token", Constants.getToken());
                map2.put("cardType", "1005");
                map2.put("taskId", "");
                map2.put("DataTypeCode", "GET_SMS");
                map2.put("content", jsonObject2.toString());

                WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map2, new WebServiceUtils.WebServiceCallBack() {
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
                                        Utils.myToast(mContext, "获取验证码成功");
                                        String content = object.getString("Content");
                                        JSONObject json = new JSONObject(content);
                                        SMSVerify1 = json.getString("SMSVERIFY");
                                        phoneVerify1 = json.getString("MOBILEPHONE");
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
                    }
                });
                break;

            case R.id.text_delGuardian3:
                linear_guardian3.setVisibility(View.GONE);
                break;

            case R.id.text_code3:
                String phone3 = edit_phone1.getText().toString();
                if (phone3.equals("")) {
                    Utils.myToast(mContext, "监护人3手机号码不可为空");
                    break;
                }
                TextCountUtil textCountUtilt3 = new TextCountUtil(AddGuardianActivity.this, 60000, 1000, text_code3);
                textCountUtilt3.start();

                JSONObject jsonObject3 = new JSONObject();
                try {
                    jsonObject3.put("MOBILEPHONE", phone3);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                HashMap<String, String> map3 = new HashMap<String, String>();
                map3.put("token", Constants.getToken());
                map3.put("cardType", "1005");
                map3.put("taskId", "");
                map3.put("DataTypeCode", "GET_SMS");
                map3.put("content", jsonObject3.toString());

                WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map3, new WebServiceUtils.WebServiceCallBack() {
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
                                        Utils.myToast(mContext, "获取验证码成功");
                                        String content = object.getString("Content");
                                        JSONObject json = new JSONObject(content);
                                        SMSVerify1 = json.getString("SMSVERIFY");
                                        phoneVerify1 = json.getString("MOBILEPHONE");
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
                    }
                });
                break;

            case R.id.relative_addMoreGuardian:
                if (linear_guardian2.getVisibility() == 0 && linear_guardian3.getVisibility() == 0) {
                    Utils.myToast(mContext, "最多允许添加三个监护人");
                    break;
                }
                if (linear_guardian2.getVisibility() == 0) {
                    linear_guardian3.setVisibility(View.VISIBLE);
                } else {
                    linear_guardian2.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private NiftyDialogBuilder dialogBuilder;
    private NiftyDialogBuilder.Effectstype effectstype;

    private void dialogShow(String guid) {
        if (dialogBuilder != null && dialogBuilder.isShowing())
            return;

        dialogBuilder = NiftyDialogBuilder.getInstance(this);

        effectstype = NiftyDialogBuilder.Effectstype.Fadein;
        dialogBuilder.withTitle("提示").withTitleColor("#333333").withMessage("添加关爱人成功")
                .isCancelableOnTouchOutside(false).withEffect(effectstype).withButton1Text("取消")
                .setCustomView(R.layout.custom_view, mContext).withButton2Text("确认").setButton1Click(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.dismiss();
            }
        }).setButton2Click(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.dismiss();
                Intent intent = new Intent();
                intent.setClass(mContext, MainCareActivity.class);
                startActivity(intent);
                finish();
            }
        }).show();

    }

}
