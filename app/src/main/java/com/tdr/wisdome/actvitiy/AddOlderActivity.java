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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.tdr.wisdome.R;
import com.tdr.wisdome.amap.MapActivity;
import com.tdr.wisdome.model.GuardianInfo;
import com.tdr.wisdome.model.OlderInfo;
import com.tdr.wisdome.util.Constants;
import com.tdr.wisdome.util.Utils;
import com.tdr.wisdome.util.WebServiceUtils;
import com.tdr.wisdome.view.SelectPicPopupWindow;
import com.tdr.wisdome.view.ZProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 添加老人
 * Created by Linus_Xie on 2016/8/16.
 */
public class AddOlderActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "AddOlderActivity";

    private Context mContext;

    private SelectPicPopupWindow mSelectPicPopupWindow;

    /* 头像名称 */
    private static final String IMAGE_FILE_NAME = "faceImage.jpg";
    /* 请求码 */
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;

    private String alarmDistance = "";//预警距离
    private String hypertension = " ";//高血压
    private String diabetes = " ";//糖尿病
    private String heartdisease = " ";//心脏病

    //获取老人活动中心点的回调值
    private final static int LOCPOSITION = 1991;

    private String address = "";
    private double lat = 0.0;//纬度
    private double lng = 0.0;//经度

    private OlderInfo mInfo = new OlderInfo();
    private GuardianInfo mGuardianInfo = new GuardianInfo();

    private List<String> disease = new ArrayList<>();

    private String SmartId = "";
    private ZProgressHUD mProgressHUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addoler);

        mContext = this;

        initView();
        String code = getIntent().getStringExtra("code");
        SmartId = getIntent().getStringExtra("SmartId");
        text_code.setText(code);
        initData();
    }

    private void initData() {
        if (SmartId.equals("")) {
            Log.e(TAG, "不处理");
        } else {
            mProgressHUD.show();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("SMARTCAREID", SmartId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HashMap<String, String> map = new HashMap<>();
            map.put("token", Constants.getToken());
            map.put("cardType", "1005");
            map.put("taskId", "");
            map.put("DataTypeCode", "CheckElderUnregedit");
            map.put("content", jsonObject.toString());

            WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map, new WebServiceUtils.WebServiceCallBack() {
                @Override
                public void callBack(String result) {
                    if (result != null) {
                        Log.e(TAG + "对象列表：", result);
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            int resultCode = jsonObject.getInt("ResultCode");
                            String resultText = Utils.initNullStr(jsonObject.getString("ResultText"));
                            if (resultCode == 0) {
                                String content = jsonObject.getString("Content");
                                JSONObject json = new JSONObject(content);
                                String lrInfo = json.getString("LRINFO");
                                JSONObject lrObj = new JSONObject(lrInfo);
                                edit_lovedName.setText(Utils.initNullStr(lrObj.getString("CUSTOMERNAME")));
                                edit_lovedIdentity.setText(Utils.initNullStr(lrObj.getString("CUSTOMERIDCARD")));
                                edit_lovedPhone.setText(Utils.initNullStr(lrObj.getString("CUSTOMMOBILE")));
                                edit_lovedAddress.setText(Utils.initNullStr(lrObj.getString("CUSTOMERADDRESS")));

                                String guardianInfo = json.getString("GUARDIANINFO");
                                JSONArray array = new JSONArray(guardianInfo);
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.getJSONObject(0);
                                    mGuardianInfo.setGuardianName(Utils.initNullStr(object.getString("GUARDIANNAME")));
                                    mGuardianInfo.setGuardianIdCard(Utils.initNullStr(object.getString("GUARDIANIDCARD")));
                                    mGuardianInfo.setGuardianMobile(Utils.initNullStr(object.getString("GUARDIANMOBILE")));
                                    mGuardianInfo.setGuardianAddress(Utils.initNullStr(object.getString("GUARDIANADDRESS")));
                                    mGuardianInfo.setEnmergencyCall(Utils.initNullStr(object.getString("ENMERGENCYCALL")));
                                }
                                mProgressHUD.dismiss();
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
    }

    private ImageView image_back;
    private TextView text_title;
    private TextView text_deal;

    private TextView text_code, text_lovedMovementArea;
    private EditText edit_lovedName, edit_lovedIdentity, edit_lovedPhone, edit_lovedAddress, edit_remarks;
    private RadioGroup radio_alarmDistance, radio_illnessDesc;
    private RadioButton radio_oneKM, radio_twoKM, radio_threeKM;
    private ImageView image_loc, image_bodyphoto;
    private CheckBox check_hypertension, check_diabetes, check_heartdisease;

    private void initView() {
        image_back = (ImageView) findViewById(R.id.image_back);
        image_back.setOnClickListener(this);
        text_title = (TextView) findViewById(R.id.text_title);
        text_title.setText("添加老人");
        text_deal = (TextView) findViewById(R.id.text_deal);
        text_deal.setOnClickListener(this);
        text_deal.setVisibility(View.VISIBLE);

        text_code = (TextView) findViewById(R.id.text_code);
        text_lovedMovementArea = (TextView) findViewById(R.id.text_lovedMovementArea);
        edit_lovedName = (EditText) findViewById(R.id.edit_lovedName);
        edit_lovedIdentity = (EditText) findViewById(R.id.edit_lovedIdentity);
        edit_lovedPhone = (EditText) findViewById(R.id.edit_lovedPhone);
        edit_lovedAddress = (EditText) findViewById(R.id.edit_lovedAddress);
        edit_remarks = (EditText) findViewById(R.id.edit_remarks);

        image_loc = (ImageView) findViewById(R.id.image_loc);
        image_loc.setOnClickListener(this);
        radio_alarmDistance = (RadioGroup) findViewById(R.id.radio_alarmDistance);
        radio_oneKM = (RadioButton) findViewById(R.id.radio_oneKM);
        radio_oneKM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    alarmDistance = "1";
                }
            }
        });
        radio_twoKM = (RadioButton) findViewById(R.id.radio_twoKM);
        radio_twoKM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    alarmDistance = "2";
                }
            }
        });
        radio_threeKM = (RadioButton) findViewById(R.id.radio_threeKM);
        radio_threeKM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    alarmDistance = "3";
                }
            }
        });
        image_bodyphoto = (ImageView) findViewById(R.id.image_bodyphoto);
        image_bodyphoto.setOnClickListener(this);

        radio_illnessDesc = (RadioGroup) findViewById(R.id.radio_illnessDesc);
        check_hypertension = (CheckBox) findViewById(R.id.check_hypertension);
        check_hypertension.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    hypertension = "高血压";
                    disease.add(hypertension);
                } else {
                    hypertension = "";
                    disease.remove("高血压");
                }
            }
        });
        check_diabetes = (CheckBox) findViewById(R.id.check_diabetes);
        check_diabetes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    diabetes = "糖尿病";
                    disease.add(diabetes);
                } else {
                    diabetes = "";
                    disease.remove("糖尿病");
                }
            }
        });
        check_heartdisease = (CheckBox) findViewById(R.id.check_heartdisease);
        check_heartdisease.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    heartdisease = "心脏病";
                    disease.add(heartdisease);
                } else {
                    heartdisease = "";
                    disease.add("心脏病");
                }
            }
        });

        mProgressHUD = new ZProgressHUD(mContext);
        mProgressHUD.setMessage("请求数据中...");
        mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_back:
                finish();
                break;

            case R.id.text_deal:
                String lovedName = edit_lovedName.getText().toString().trim();
                if (lovedName.equals("")) {
                    Utils.myToast(mContext, "请输入被关爱人姓名");
                    break;
                }
                String lovedIdentity = edit_lovedIdentity.getText().toString().toUpperCase().trim();
                int length = lovedIdentity.length();
                if (length == 18) {//出现15位身份证就直接过去
                    if (lovedIdentity.equals("")) {
                        Utils.myToast(mContext, "请输入身份证号码");
                        break;
                    }
                    if (!Utils.isIDCard18(lovedIdentity)) {
                        Utils.myToast(mContext, "请输入正确身份证号");
                        break;
                    }
                }

                String lovedAddress = edit_lovedAddress.getText().toString().trim();
                if (lovedAddress.equals("")) {
                    Utils.myToast(mContext, "请输入联系地址");
                    break;
                }

                String lovedMovementArea = text_lovedMovementArea.getText().toString().trim();
                if (lovedMovementArea.equals("")) {
                    Utils.myToast(mContext, "请选择关爱人活动中心点");
                    break;
                }

                if (alarmDistance.equals("")) {
                    Utils.myToast(mContext, "请选择关爱人预警距离");
                    break;
                }

                String bodyPhoto = Constants.getBodyPhoto();
                if (bodyPhoto.equals("")) {
                    Utils.myToast(mContext, "请拍摄被关爱人半身照");
                    break;
                }

                mInfo.setCareNumber(text_code.getText().toString().trim());
                mInfo.setCustomerName(edit_lovedName.getText().toString().trim());
                mInfo.setCustomerIdCard(edit_lovedIdentity.getText().toString().trim());
                mInfo.setCustomMobile(edit_lovedPhone.getText().toString().trim());
                mInfo.setCustomerAddress(edit_lovedAddress.getText().toString().trim());
                mInfo.setMovementArea(text_lovedMovementArea.getText().toString());
                mInfo.setCentrePointLng(String.valueOf(lng));
                mInfo.setCentrePointLat(String.valueOf(lat));
                mInfo.setRadius(alarmDistance);
                mInfo.setHealthCondition(Utils.listToString(disease));
                mInfo.setEmtnotice(edit_remarks.getText().toString().trim());

                Intent intent1 = new Intent(AddOlderActivity.this, AddGuardianActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("olderInfo", (Serializable) mInfo);
                bundle.putSerializable("guardianInfo", (Serializable) mGuardianInfo);
                intent1.putExtra("bundle", bundle);
                startActivity(intent1);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;

            case R.id.image_loc:
                Intent intent = new Intent(mContext, MapActivity.class);
                startActivityForResult(intent, LOCPOSITION);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;

            case R.id.image_bodyphoto:
                // 实例化SelectPicPopupWindow
                mSelectPicPopupWindow = new SelectPicPopupWindow(AddOlderActivity.this, this);
                // 显示窗口
                mSelectPicPopupWindow.showAtLocation(AddOlderActivity.this.findViewById(R.id.linear_pop),
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
        }
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

            case LOCPOSITION:
                if (resultCode == RESULT_OK) {
                    address = data.getStringExtra("address");
                    text_lovedMovementArea.setText(address);
                    lat = data.getDoubleExtra("lat", 0.0);
                    lng = data.getDoubleExtra("lng", 0.0);
                    Log.e(TAG + "取到坐标：", address + lat + lng);
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
            image_bodyphoto.setImageDrawable(drawable);
            // facePhoto = drawable.toString();
            // 保存新头像
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
