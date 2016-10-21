package com.tdr.wisdome.actvitiy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tdr.wisdome.R;
import com.tdr.wisdome.util.Constants;
import com.tdr.wisdome.util.Utils;
import com.tdr.wisdome.util.WebServiceUtils;
import com.tdr.wisdome.view.SelectPicPopupWindow;
import com.tdr.wisdome.view.ZProgressHUD;
import com.tdr.wisdome.view.niftydialog.NiftyDialogBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 人员配置
 * Created by Linus_Xie on 2016/8/19.
 */
public class OlderShareActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "OlderShareActivity";

    private Context mContext;

    private List<HashMap<String, String>> listGuardian = new ArrayList<>();
    private HashMap<String, String> mapGuardian = new HashMap<>();

    private ZProgressHUD mProgressHUD;

    private String strPhoto = "";

    //分享老人需要的字段
    private String personType = "";//当前用户类型，0是登记人有编辑权，1是关联人无编辑权
    private String smartcareId = "";//对象ID
    private String targetType = "";//对象类型
    private String olderBase = "";//老人头像
    private String operatorName = "";//分享人姓名（=当前用户名）
    private String shareTime = "";//申报时间

    private SelectPicPopupWindow mSelectPicPopupWindow;

    /* 头像名称 */
    private static final String IMAGE_FILE_NAME = "faceImage.jpg";
    /* 请求码 */
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;

    private final static int KEY = 0316;

    private boolean isChanged = false;//是否修改

    private String hideIdentity = "";
    private String identity = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oldershare);
        Log.e(TAG,"1111");

        mContext = this;

        Bundle bundle = this.getIntent().getExtras();
        listGuardian = (List<HashMap<String, String>>) bundle.getSerializable("listGuardian");
        smartcareId = listGuardian.get(0).get("smartcareId");
        personType = listGuardian.get(0).get("personType");
        targetType = bundle.getString("targetType");
        //olderBase = bundle.getString("olderBase");
        operatorName = Constants.getUserName();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        shareTime = sdf.format(new java.util.Date());

        initView();
        initData();
    }

    private ImageView image_back;
    private TextView text_title;
    private TextView text_deal;

    private RelativeLayout relative_olderName, relative_bodyPhoto, relative_olderPhone, relative_olderIdentity, relative_olderAddress, relative_olderHealth, relative_olderRemarks;
    private TextView text_olderName, text_olderPhone, text_odlerIdentity, text_olderAddress, text_olderHealth, text_olderRemarks;
    private ImageView image_bodyPhoto;
    private TextView text_delGuardian1, text_guardianName1, text_guardianPhone1, text_guardianAddress1;
    private TextView text_delGuardian2, text_guardianName2, text_guardianPhone2, text_guardianAddress2;
    private TextView text_delGuardian3, text_guardianName3, text_guardianPhone3, text_guardianAddress3;
    private LinearLayout linear_guardian1, linear_guardian2, linear_guardian3;
    private RelativeLayout relative_addMoreGuardian;
    private TextView text_addMoreGuardian;

    private void initView() {
        image_back = (ImageView) findViewById(R.id.image_back);
        image_back.setOnClickListener(this);
        text_title = (TextView) findViewById(R.id.text_title);
        text_title.setText("人员配置");
        text_deal = (TextView) findViewById(R.id.text_deal);
        text_deal.setText("分享");
        text_deal.setVisibility(View.VISIBLE);
        text_deal.setOnClickListener(this);

        relative_olderName = (RelativeLayout) findViewById(R.id.relative_olderName);
        relative_olderName.setOnClickListener(this);
        text_olderName = (TextView) findViewById(R.id.text_olderName);

        relative_bodyPhoto = (RelativeLayout) findViewById(R.id.relative_bodyPhoto);
        relative_bodyPhoto.setOnClickListener(this);
        image_bodyPhoto = (ImageView) findViewById(R.id.image_bodyPhoto);

        relative_olderPhone = (RelativeLayout) findViewById(R.id.relative_olderPhone);
        relative_olderPhone.setOnClickListener(this);
        text_olderPhone = (TextView) findViewById(R.id.text_olderPhone);

        relative_olderIdentity = (RelativeLayout) findViewById(R.id.relative_olderIdentity);
        relative_olderIdentity.setOnClickListener(this);
        text_odlerIdentity = (TextView) findViewById(R.id.text_odlerIdentity);

        relative_olderAddress = (RelativeLayout) findViewById(R.id.relative_olderAddress);
        relative_olderAddress.setOnClickListener(this);
        text_olderAddress = (TextView) findViewById(R.id.text_olderAddress);

        relative_olderHealth = (RelativeLayout) findViewById(R.id.relative_olderHealth);
        relative_olderHealth.setOnClickListener(this);
        text_olderHealth = (TextView) findViewById(R.id.text_olderHealth);

        relative_olderRemarks = (RelativeLayout) findViewById(R.id.relative_olderRemarks);
        relative_olderRemarks.setOnClickListener(this);
        text_olderRemarks = (TextView) findViewById(R.id.text_olderRemarks);

        linear_guardian1 = (LinearLayout) findViewById(R.id.linear_guardian1);
        text_delGuardian1 = (TextView) findViewById(R.id.text_delGuardian1);
        text_delGuardian1.setOnClickListener(this);
        text_guardianName1 = (TextView) findViewById(R.id.text_guardianName1);
        text_guardianPhone1 = (TextView) findViewById(R.id.text_guardianPhone1);
        text_guardianAddress1 = (TextView) findViewById(R.id.text_guardianAddress1);
        linear_guardian2 = (LinearLayout) findViewById(R.id.linear_guardian2);
        text_delGuardian2 = (TextView) findViewById(R.id.text_delGuardian2);
        text_delGuardian2.setOnClickListener(this);
        text_guardianName2 = (TextView) findViewById(R.id.text_guardianName2);
        text_guardianPhone2 = (TextView) findViewById(R.id.text_guardianPhone2);
        text_guardianAddress2 = (TextView) findViewById(R.id.text_guardianAddress2);
        linear_guardian3 = (LinearLayout) findViewById(R.id.linear_guardian3);
        text_delGuardian3 = (TextView) findViewById(R.id.text_delGuardian3);
        text_delGuardian3.setOnClickListener(this);
        text_guardianName3 = (TextView) findViewById(R.id.text_guardianName3);
        text_guardianPhone3 = (TextView) findViewById(R.id.text_guardianPhone3);
        text_guardianAddress3 = (TextView) findViewById(R.id.text_guardianAddress3);

        relative_addMoreGuardian = (RelativeLayout) findViewById(R.id.relative_addMoreGuardian);
        relative_addMoreGuardian.setOnClickListener(this);
        //text_addMoreGuardian = (TextView) findViewById(R.id.text_addMoreGuardian);
        //text_addMoreGuardian.setOnClickListener(this);

        mProgressHUD = new ZProgressHUD(OlderShareActivity.this);
        //mProgressHUD.setMessage("分享中...");
        //mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);

    }

    private void initData() {
        text_olderName.setText((String) listGuardian.get(0).get("customerName"));
        image_bodyPhoto.setImageBitmap(Utils.stringtoBitmap(Constants.getBodyPhoto()));
        text_olderPhone.setText((String) listGuardian.get(0).get("customMobile"));
        identity = (String) listGuardian.get(0).get("customerIdCard");
        hideIdentity = Utils.hideID(identity);
        text_odlerIdentity.setText(hideIdentity);
        text_olderAddress.setText((String) listGuardian.get(0).get("customerAddress"));
        text_olderHealth.setText((String) listGuardian.get(0).get("healthCondition"));
        text_olderRemarks.setText((String) listGuardian.get(0).get("emtnotice"));

        int size = listGuardian.size();
        if (size == 2) {
            text_delGuardian1.setVisibility(View.GONE);
            linear_guardian2.setVisibility(View.GONE);
            linear_guardian2.setVisibility(View.GONE);
            text_guardianName1.setText((String) listGuardian.get(1).get("guardianName"));
            text_guardianPhone1.setText((String) listGuardian.get(1).get("guardianMobile"));
            text_guardianAddress1.setText((String) listGuardian.get(1).get("guardianAddress"));
        } else if (size == 3) {
            linear_guardian2.setVisibility(View.VISIBLE);
            linear_guardian3.setVisibility(View.GONE);
            text_guardianName1.setText((String) listGuardian.get(1).get("guardianName"));
            text_guardianPhone1.setText((String) listGuardian.get(1).get("guardianMobile"));
            text_guardianAddress1.setText((String) listGuardian.get(1).get("guardianAddress"));
            text_guardianName2.setText((String) listGuardian.get(2).get("guardianName"));
            text_guardianPhone2.setText((String) listGuardian.get(2).get("guardianMobile"));
            text_guardianAddress2.setText((String) listGuardian.get(2).get("guardianAddress"));
        } else {
            linear_guardian2.setVisibility(View.VISIBLE);
            linear_guardian3.setVisibility(View.VISIBLE);
            relative_addMoreGuardian.setVisibility(View.GONE);
            text_guardianName1.setText((String) listGuardian.get(1).get("guardianName"));
            text_guardianPhone1.setText((String) listGuardian.get(1).get("guardianMobile"));
            text_guardianAddress1.setText((String) listGuardian.get(1).get("guardianAddress"));
            text_guardianName2.setText((String) listGuardian.get(2).get("guardianName"));
            text_guardianPhone2.setText((String) listGuardian.get(2).get("guardianMobile"));
            text_guardianAddress2.setText((String) listGuardian.get(2).get("guardianAddress"));
            text_guardianName3.setText((String) listGuardian.get(3).get("guardianName"));
            text_guardianPhone3.setText((String) listGuardian.get(3).get("guardianMobile"));
            text_guardianAddress3.setText((String) listGuardian.get(3).get("guardianAddress"));
        }

        if (personType.equals("1")) {
            relative_olderName.setClickable(false);
            relative_bodyPhoto.setClickable(false);
            relative_olderPhone.setClickable(false);
            relative_olderIdentity.setClickable(false);
            relative_olderAddress.setClickable(false);
            relative_olderHealth.setClickable(false);
            relative_olderRemarks.setClickable(false);

            text_delGuardian1.setClickable(false);
            text_delGuardian2.setClickable(false);
            text_delGuardian3.setClickable(false);
            relative_addMoreGuardian.setClickable(false);
            //text_addMoreGuardian.setClickable(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_back:
                //finish();
                if (isChanged) {
                    mProgressHUD.setMessage("提交中...");
                    mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
                    JSONObject jsonObject = new JSONObject();
                    try {
                        JSONObject jsonLrInfo = new JSONObject();
                        jsonLrInfo.put("CUSTOMERNAME", text_olderName.getText().toString().trim());
                        jsonLrInfo.put("CUSTOMERIDCARD", identity);
                        jsonLrInfo.put("CUSTOMMOBILE", text_olderPhone.getText().toString().trim());
                        jsonLrInfo.put("CUSTOMERADDRESS", text_olderAddress.getText().toString().trim());

                        JSONObject jsonPhoto = new JSONObject();
                        jsonPhoto.put("PHOTOID", "CHANGED");
                        jsonPhoto.put("CUSTOMERPHOTO", Constants.getBodyPhoto());

                        JSONObject jsonHealth = new JSONObject();
                        jsonHealth.put("HEALTHCONDITION", text_olderHealth.getText().toString().trim());
                        jsonHealth.put("EMTNOTICE", text_olderRemarks.getText().toString().trim());

                        jsonObject.put("SMARTCAREID", smartcareId);
                        jsonObject.put("LRINFO", jsonLrInfo);
                        jsonObject.put("PHOTOINFO", jsonPhoto);
                        jsonObject.put("CUSTMERHEALTHINFO", jsonHealth);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    mProgressHUD.show();
                    HashMap<String, String> map = new HashMap<>();
                    map.put("token", Constants.getToken());
                    map.put("cardType", "1005");
                    map.put("taskId", "");
                    map.put("DataTypeCode", "ModifyElder");
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
                                        Intent intent = new Intent(OlderShareActivity.this, OlderSelectActivity.class);
                                        intent.putExtra("smartcareId", smartcareId);
                                        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        mProgressHUD.dismiss();
                                        Utils.myToast(mContext, result);
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

                } else {
                    finish();
                }

                break;

            case R.id.text_deal:
                mProgressHUD.setMessage("分享中...");
                mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
                mProgressHUD.show();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("SMARTCAREID", smartcareId);
                    jsonObject.put("TARGETTYPE", "1");
                    jsonObject.put("OPERATORNAME", operatorName);
                    jsonObject.put("SHARETIME", shareTime);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                HashMap<String, String> map = new HashMap<>();
                map.put("token", Constants.getToken());
                map.put("cardType", "1005");
                map.put("taskId", "");
                map.put("DataTypeCode", "Share_Target");
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
                                    String content = json.getString("Content");
                                    JSONObject object = new JSONObject(content);
                                    String guid = object.getString("SHAREID");
                                    mProgressHUD.dismiss();
                                    generateQRcode("Q3", targetType, guid);
                                    //dialogShow(0, guid, "");
                                } else {
                                    mProgressHUD.dismiss();
                                    Utils.myToast(mContext, result);
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
            case R.id.relative_olderName:
                Intent intentOlderName = new Intent(mContext, ModifyActivity.class);
                intentOlderName.putExtra("activityName", "olderName");
                intentOlderName.putExtra("value", (String) listGuardian.get(0).get("customerName"));
                startActivityForResult(intentOlderName, KEY);
                break;

            case R.id.relative_bodyPhoto:
                //实例化SelectPicPopupWindow
                mSelectPicPopupWindow = new SelectPicPopupWindow(OlderShareActivity.this, this);
                // 显示窗口
                mSelectPicPopupWindow.showAtLocation(OlderShareActivity.this.findViewById(R.id.linear_pop),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
                break;

            case R.id.btn_takephoto:
                Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // 判断存储卡是否可以用，可用进行存储
                if (Utils.hasSdcard()) {
                    intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
                }

                startActivityForResult(intentFromCapture, CAMERA_REQUEST_CODE);
                mSelectPicPopupWindow.dismiss();
                break;

            case R.id.btn_pickphoto:
                Intent intentFromGallery = new Intent();
                intentFromGallery.setType("image/*"); // 设置文件类型
                intentFromGallery.setAction(Intent.ACTION_PICK);
                startActivityForResult(intentFromGallery, IMAGE_REQUEST_CODE);
                mSelectPicPopupWindow.dismiss();
                break;

            case R.id.relative_olderPhone:
                Intent intentOlderPhone = new Intent(mContext, ModifyActivity.class);
                intentOlderPhone.putExtra("activityName", "olderPhone");
                intentOlderPhone.putExtra("value", (String) listGuardian.get(0).get("customMobile"));
                startActivityForResult(intentOlderPhone, KEY);
                break;

            case R.id.relative_olderIdentity:
                Intent intentOlderIdentity = new Intent(mContext, ModifyActivity.class);
                intentOlderIdentity.putExtra("activityName", "olderIdentity");
                intentOlderIdentity.putExtra("value", (String) listGuardian.get(0).get("customerIdCard"));
                startActivityForResult(intentOlderIdentity, KEY);
                break;

            case R.id.relative_olderAddress:
                Intent intentOlderAddress = new Intent(mContext, ModifyActivity.class);
                intentOlderAddress.putExtra("activityName", "olderAddress");
                intentOlderAddress.putExtra("value", (String) listGuardian.get(0).get("customerAddress"));
                startActivityForResult(intentOlderAddress, KEY);
                break;

            case R.id.relative_olderHealth:
                Intent intentOlderHealth = new Intent(mContext, HealthActivity.class);
                intentOlderHealth.putExtra("value", (String) listGuardian.get(0).get("healthCondition"));
                startActivityForResult(intentOlderHealth, KEY);
                break;
            case R.id.relative_olderRemarks:
                Intent intentOlderRemarks = new Intent(mContext, ModifyActivity.class);
                intentOlderRemarks.putExtra("activityName", "olderRemarks");
                intentOlderRemarks.putExtra("value", (String) listGuardian.get(0).get("emtnotice"));
                startActivityForResult(intentOlderRemarks, KEY);
                break;

            case R.id.text_delGuardian1:
                //linear_guardian1.setVisibility(View.GONE);
                dialogShow(1, (String) listGuardian.get(1).get("guardianId"), "a");
                break;

            case R.id.text_delGuardian2:
                // linear_guardian2.setVisibility(View.GONE);
                dialogShow(1, (String) listGuardian.get(2).get("guardianId"), "b");
                break;

            case R.id.text_delGuardian3:
                //linear_guardian3.setVisibility(View.GONE);
                dialogShow(1, (String) listGuardian.get(3).get("guardianId"), "c");
                break;

            case R.id.relative_addMoreGuardian:
           // case R.id.text_addMoreGuardian:
                Intent intentGuardian1 = new Intent(mContext, GuardianActivity.class);
                intentGuardian1.putExtra("smartcareId", (String) listGuardian.get(0).get("smartcareId"));
                startActivityForResult(intentGuardian1, KEY);

                break;
        }
    }

    private void generateQRcode(String q3, String targetType, String shareId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("QRType", q3);
            jsonObject.put("Type", targetType);
            jsonObject.put("ID", shareId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("token", Constants.getToken());
        map.put("cardType", "1005");
        map.put("taskId", "");
        map.put("DataTypeCode", "GenerateQRcode");
        map.put("content", jsonObject.toString());
        WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map, new WebServiceUtils.WebServiceCallBack() {
            @Override
            public void callBack(String result) {
                if (result != null) {
                    try {
                        JSONObject json = new JSONObject(result);
                        int resultCode = json.getInt("ResultCode");
                        String resultText = Utils.initNullStr(json.getString("ResultText"));
                        if (resultCode == 0) {
                            mProgressHUD.dismiss();
                            String content = json.getString("Content");
                            JSONObject object = new JSONObject(content);
                            String QRCode = object.getString("QRCode");
                            Intent intent = new Intent(mContext, QrCodeActivity.class);
                            intent.putExtra("code", QRCode);
                            mContext.startActivity(intent);
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

    }

    private NiftyDialogBuilder dialogBuilder;
    private NiftyDialogBuilder.Effectstype effectstype;

    private void dialogShow(final int flag, final String guid, final String tag) {
        if (dialogBuilder != null && dialogBuilder.isShowing())
            return;

        dialogBuilder = NiftyDialogBuilder.getInstance(this);

        if (flag == 0) {
            effectstype = NiftyDialogBuilder.Effectstype.Fadein;
            dialogBuilder.withTitle("提示").withTitleColor("#333333").withMessage("点击确认分享至微信")
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
                    intent.setClass(mContext, PerfectActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("activity", TAG);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                }
            }).show();
        } else if (flag == 1) {
            effectstype = NiftyDialogBuilder.Effectstype.Fadein;
            dialogBuilder.withTitle("提示").withTitleColor("#333333").withMessage("是否删除此监护人")
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
                    delGuardian(guid, tag);
                }
            }).show();
        }
    }

    private void delGuardian(String guid, final String tag) {
        mProgressHUD.setMessage("删除中...");
        mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
        mProgressHUD.show();
        JSONObject json = new JSONObject();
        try {
            json.put("GUARDIANID", guid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("token", Constants.getToken());
        map.put("cardType", "1005");
        map.put("taskId", "");
        map.put("DataTypeCode", "DelGuardian");
        map.put("content", json.toString());

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
                            if (tag.equals("a")) {
                                linear_guardian1.setVisibility(View.GONE);
                            } else if (tag.equals("b")) {
                                linear_guardian2.setVisibility(View.GONE);
                            } else {
                                linear_guardian3.setVisibility(View.GONE);
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
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IMAGE_REQUEST_CODE:
                if (data == null) {
                    Utils.myToast(mContext, "没有取到图片");
                    return;
                }
                startPhotoZoom(data.getData());
                break;
            case CAMERA_REQUEST_CODE:
                if (Utils.hasSdcard()) {
                    File tempFile = new File(Environment.getExternalStorageDirectory() + "/" + IMAGE_FILE_NAME);
                    startPhotoZoom(Uri.fromFile(tempFile));
                } else {
                    Utils.myToast(mContext, "未找到存储卡，无法存储照片！");
                }

                break;
            case RESULT_REQUEST_CODE:
                if (data != null) {
                    getImageToView(data);
                }
                break;
            case KEY:
                if (resultCode == RESULT_OK) {
                    String activity = data.getStringExtra("activity");
                    String result = data.getStringExtra("result");
                    if (activity.equals("olderName")) {
                        if (!result.equals(text_olderName.getText().toString().trim())) {
                            isChanged = true;
                            text_olderName.setText(result);
                        }
                    } else if (activity.equals("olderPhone")) {
                        if (!result.equals(text_olderPhone.getText().toString().trim())) {
                            isChanged = true;
                            text_olderPhone.setText(result);
                        }
                    } else if (activity.equals("olderIdentity")) {
                        if (!result.toUpperCase().equals(identity)) {
                            isChanged = true;
                            identity = result.toUpperCase();
                            text_odlerIdentity.setText(Utils.hideID(result));
                        }
                    } else if (activity.equals("olderAddress")) {
                        if (!result.equals(text_olderAddress.getText().toString().trim())) {
                            isChanged = true;
                            text_olderAddress.setText(result);
                        }
                    } else if (activity.equals("olderRemarks")) {
                        if (!result.equals(text_olderRemarks.getText().toString().trim())) {
                            isChanged = true;
                            text_olderRemarks.setText(result);
                        }
                    } else if (activity.equals("healthActivity")) {
                        if (!result.equals(text_olderHealth.getText().toString().trim())) {
                            isChanged = true;
                            text_olderHealth.setText(result);
                        }
                    } else if (activity.equals("guardianActivity")) {
                        String[] result1 = result.split(",");
                        if (linear_guardian1.getVisibility() == 8) {
                            linear_guardian1.setVisibility(View.VISIBLE);
                            text_guardianName1.setText(result1[0]);
                            text_guardianPhone1.setText(result1[1]);
                            text_guardianAddress1.setText(result1[2]);
                        } else if (linear_guardian2.getVisibility() == 8) {
                            linear_guardian2.setVisibility(View.VISIBLE);
                            text_guardianName2.setText(result1[0]);
                            text_guardianPhone2.setText(result1[1]);
                            text_guardianAddress2.setText(result1[2]);
                        } else if (linear_guardian3.getVisibility() == 8) {
                            linear_guardian3.setVisibility(View.VISIBLE);
                            text_guardianName3.setText(result1[0]);
                            text_guardianPhone3.setText(result1[1]);
                            text_guardianAddress3.setText(result1[2]);
                        }
                    }
                }
                break;
        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        // intent.setDataAndType(uri, "image/*");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            String url = getPath(mContext, uri);
            intent.setDataAndType(Uri.fromFile(new File(url)), "image/*");
        } else {
            intent.setDataAndType(uri, "image/*");
        }
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, 2);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param data
     */
    private void getImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(photo);
            image_bodyPhoto.setImageDrawable(drawable);
            // facePhoto = drawable.toString();
            // 保存新头像
            isChanged = true;
            strPhoto = Utils.Byte2Str(Utils.Bitmap2Bytes(photo));
            Constants.setBodyPhoto(Utils.Byte2Str(Utils.Bitmap2Bytes(photo)));

        }
    }

    // 以下是关键，原本uri返回的是file:///...来着的，android4.4返回的是content:///...
    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}
