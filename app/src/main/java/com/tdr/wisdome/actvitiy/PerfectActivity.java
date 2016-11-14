package com.tdr.wisdome.actvitiy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingja.cardpackage.activity.HomeActivity;
import com.kingja.cardpackage.util.StringUtil;
import com.tdr.wisdome.R;
import com.tdr.wisdome.util.CloseActivityUtil;
import com.tdr.wisdome.util.Constants;
import com.tdr.wisdome.util.Utils;
import com.tdr.wisdome.util.WebServiceUtils;
import com.tdr.wisdome.view.ZProgressHUD;
import com.tdr.wisdome.view.material.MaterialEditText;
import com.yunmai.android.idcard.ACamera;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * 完善资料
 * Created by Linus_Xie on 2016/8/5.
 */
public class PerfectActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "PerfectActivity";

    private Context mContext;

    /* 头像名称 */
    private static final String IMAGE_FILE_NAME = "faceImage.jpg";
    /* 请求码 */
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;
    /*OCRCode*/
    private final static int CAMERA = 2001;

    //private AMapLocationClient mLocationClient;

    private String locCity = "";

    private ZProgressHUD mProgressHUD;

    private String activity = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfect);

        mContext = this;

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            activity = bundle.getString("activity");
        }

        initView();
        //initLoc();
    }

    private ImageView image_back;
    private TextView text_title;

    private MaterialEditText material_identity, material_birthday, material_realName, material_sex, material_permanentAddress;
    private ImageView image_identity;
    private Button btn_complete;

    private void initView() {
        image_back = (ImageView) findViewById(R.id.image_back);
        image_back.setOnClickListener(this);
        text_title = (TextView) findViewById(R.id.text_title);
        text_title.setText("完善资料");

        material_identity = (MaterialEditText) findViewById(R.id.material_identity);
        material_identity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (material_identity.length() == 18) {
                    material_birthday.setText(Utils.identityToBirthday(material_identity.getText().toString().toUpperCase().trim()));
                    material_sex.setText(Utils.maleOrFemale(material_identity.getText().toString().toUpperCase().trim()));
                }
            }
        });
        material_birthday = (MaterialEditText) findViewById(R.id.material_birthday);
        material_realName = (MaterialEditText) findViewById(R.id.material_realName);
        material_sex = (MaterialEditText) findViewById(R.id.material_sex);
        material_permanentAddress = (MaterialEditText) findViewById(R.id.material_permanentAddress);
        image_identity = (ImageView) findViewById(R.id.image_identity);
        image_identity.setOnClickListener(this);
        btn_complete = (Button) findViewById(R.id.btn_complete);
        btn_complete.setOnClickListener(this);

        mProgressHUD = new ZProgressHUD(this);
        mProgressHUD.setMessage("信息提交中...");
        mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        if (!(Constants.getUserIdentitycard()).equals("")) {
            //控件不可操作
            material_identity.setFocusable(false);
            material_identity.setText(StringUtil.hideID(Constants.getUserIdentitycard()));
            material_birthday.setFocusable(false);
            material_birthday.setText(Utils.identityToBirthday(Constants.getUserIdentitycard()));
            material_sex.setText(Utils.maleOrFemale(Constants.getUserIdentitycard()));
            material_realName.setFocusable(false);
            material_realName.setText(Constants.getRealName());
            material_sex.setFocusable(false);
            material_permanentAddress.setFocusable(false);
            material_permanentAddress.setText(Constants.getPermanentAddr());
            image_identity.setClickable(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_back:
                finish();
                break;

            case R.id.image_identity:
                //身份证识别
                Intent intent = new Intent(mContext, ACamera.class);
                startActivityForResult(intent, CAMERA);
                break;

            case R.id.btn_complete:
                //if (Constants.getRealName().equals("")) {
                String identity = material_identity.getText().toString().toUpperCase().trim();
                if (identity.equals("")) {
                    Utils.myToast(mContext, "请输入身份证号码");
                    break;
                }
                if (!Utils.isIDCard18(identity)) {
                    Utils.myToast(mContext, "请输入正确身份证号");
                    break;
                }

                String realName = material_realName.getText().toString().trim();
                if (realName.equals("")) {
                    Utils.myToast(mContext, "请输入真实姓名");
                    break;
                }
                String permanentAddress = material_permanentAddress.getText().toString().trim();
                if (permanentAddress.equals("")) {
                    Utils.myToast(mContext, "请输入户籍地址");
                    break;
                }

                mProgressHUD.show();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("Realname", material_realName.getText().toString().trim());
                    jsonObject.put("idcard", material_identity.getText().toString().trim());
                    jsonObject.put("sex", material_sex.getText().toString().trim());
                    jsonObject.put("birthday", material_birthday.getText().toString().trim());
                    jsonObject.put("nationality", "");
                    jsonObject.put("address", material_permanentAddress.getText().toString().trim());
                    jsonObject.put("phone2", "");
                    jsonObject.put("Remark", "");
                    //jsonObject.put("createtime", "");
                    jsonObject.put("UserName", "");
                    jsonObject.put("FaceBase", Constants.getFaceBase());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                HashMap<String, String> map = new HashMap<>();
                map.put("token", Constants.getToken());
                map.put("cardType", "");
                map.put("taskId", "");
                map.put("DataTypeCode", "AddUserDetails");
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
                                    mProgressHUD.dismiss();
                                    if (activity.equals("SetPwdActivity")) {
                                        Intent intent = new Intent(mContext, LoginActivity.class);
                                        startActivity(intent);
                                        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                                        CloseActivityUtil.activityFinish(PerfectActivity.this);
                                    } else {
                                        Constants.setUserIdentitycard(material_identity.getText().toString().trim());
                                        Constants.setRealName(material_realName.getText().toString().trim());
                                        Constants.setPermanentAddr(material_permanentAddress.getText().toString().trim());
                                        Intent intent = new Intent(mContext, HomeActivity.class);
                                        startActivity(intent);
                                        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                                        CloseActivityUtil.activityFinish(PerfectActivity.this);
                                    }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAMERA:
                if (RESULT_OK == resultCode) {
                    material_identity.setText(data.getStringExtra("card"));
                    material_birthday.setText(data.getStringExtra("birth"));
                    material_realName.setText(data.getStringExtra("name"));
                    material_sex.setText(data.getStringExtra("sex"));
                    material_permanentAddress.setText(data.getStringExtra("address"));
                    Bitmap bitmap = BitmapFactory.decodeFile(data.getStringExtra("img"));
                    if (bitmap == null) {
                        Utils.myToast(mContext, "SD写入问题无法获取图片");
                    } else {
                        bitmap = Utils.thumbnailBitmap(bitmap);
                    }

                }
                break;
        }
    }

}
