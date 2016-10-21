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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tdr.wisdome.R;
import com.tdr.wisdome.model.CityInfo;
import com.tdr.wisdome.util.Constants;
import com.tdr.wisdome.util.Utils;
import com.tdr.wisdome.util.WebServiceUtils;
import com.tdr.wisdome.view.SelectPicPopupWindow;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

/**
 * 个人信息
 * Created by Linus_Xie on 2016/8/6.
 */
public class PersonInfoActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "PersonInfoActivity";

    private Context mContext;

    private SelectPicPopupWindow mSelectPicPopupWindow;

    /* 头像名称 */
    private static final String IMAGE_FILE_NAME = "faceImage.jpg";
    /* 请求码 */
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;

    //获取城市的回调值
    private final static int LOCKEY = 2003;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personinfo);

        mContext = this;

        initView();
    }

    private ImageView image_back;
    private TextView text_title;

    private RelativeLayout relative_userPhoto, relative_nickName, relative_realName, relative_locCity, relative_identity, relative_permanentAddr;
    private ImageView image_userPhoto;
    private TextView text_nickName, text_realName, text_locCity, text_identity, text_permanentAddr;

    private void initView() {
        image_back = (ImageView) findViewById(R.id.image_back);
        image_back.setOnClickListener(this);
        text_title = (TextView) findViewById(R.id.text_title);
        text_title.setText("个人信息");

        relative_userPhoto = (RelativeLayout) findViewById(R.id.relative_userPhoto);
        relative_userPhoto.setOnClickListener(this);
        image_userPhoto = (ImageView) findViewById(R.id.image_userPhoto);
        relative_nickName = (RelativeLayout) findViewById(R.id.relative_nickName);
        if (Constants.getUserName().equals("")) {
            relative_nickName.setOnClickListener(this);
        }
        text_nickName = (TextView) findViewById(R.id.text_nickName);
        relative_realName = (RelativeLayout) findViewById(R.id.relative_realName);
        text_realName = (TextView) findViewById(R.id.text_realName);
        relative_locCity = (RelativeLayout) findViewById(R.id.relative_locCity);
        relative_locCity.setOnClickListener(this);
        text_locCity = (TextView) findViewById(R.id.text_locCity);
        relative_identity = (RelativeLayout) findViewById(R.id.relative_identity);
        text_identity = (TextView) findViewById(R.id.text_identity);
        relative_permanentAddr = (RelativeLayout) findViewById(R.id.relative_permanentAddr);
        text_permanentAddr = (TextView) findViewById(R.id.text_permanentAddr);

        if (!Constants.getUserName().equals("")) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        image_userPhoto.setImageBitmap(Utils.stringtoBitmap(Constants.getFaceBase()));
        text_nickName.setText(Constants.getUserName());
        text_realName.setText(Constants.getRealName());
        text_locCity.setText(Constants.getCityName());
        if (Constants.getUserIdentitycard().equals("")) {
            Log.e(TAG, "身份证无");
        } else {
            text_identity.setText(Utils.hideID(Constants.getUserIdentitycard()));
        }
        text_permanentAddr.setText(Constants.getPermanentAddr());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_back:
                finish();
                break;

            case R.id.relative_userPhoto:
                mSelectPicPopupWindow = new SelectPicPopupWindow(PersonInfoActivity.this, this);
                // 显示窗口
                mSelectPicPopupWindow.showAtLocation(PersonInfoActivity.this.findViewById(R.id.linear_pop),
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

            case R.id.relative_nickName:
                if (Constants.getUserName().equals("")){
                    Intent intent0 = new Intent(mContext, SetNickNameActivity.class);
                    startActivityForResult(intent0, 1991);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                } else {

                }

                break;

            case R.id.relative_locCity:
                Intent intent = new Intent(mContext, CityPickerActivity.class);
                intent.putExtra("activity", "PersonInfo");
                startActivityForResult(intent, LOCKEY);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
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

            case LOCKEY:
                if (resultCode == RESULT_OK) {
                    CityInfo mInfo = (CityInfo) data.getSerializableExtra("city");
                    String cityCode = mInfo.getCityCode();
                    //Constants.setCityCode(cityCode);
                    //Constants.setCityName(mInfo.getCityName());
                    String cityName = mInfo.getCityName();
                    text_locCity.setText(cityName);
                    sendCurrentCityCode(cityCode, cityName);
                }

                break;

            case 1991:
                if (resultCode == RESULT_OK) {
                    if (data == null) {
                        Utils.myToast(mContext, "没有取到昵称");
                    } else {
                        String nickName = data.getStringExtra("nickName");
                        text_nickName.setText(nickName);
                    }

                }
                break;


        }
    }

    private void sendCurrentCityCode(final String cityCode, final String cityName) {
        JSONObject json = new JSONObject();
        try {
            json.put("CityCode", cityCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("token", Constants.getToken());
        map.put("cardType", "");
        map.put("taskId", "");
        map.put("DataTypeCode", "EditCurrentCity");
        map.put("content", json.toString());
        WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map, new WebServiceUtils.WebServiceCallBack() {
            @Override
            public void callBack(String result) {
                if (result != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int resultCode = jsonObject.getInt("ResultCode");
                        String resultText = Utils.initNullStr(jsonObject.getString("ResultText"));
                        if (resultCode == 0) {
                            Constants.setCityCode(cityCode);
                            Constants.setCityName(cityName);
                            text_locCity.setText(cityName);
                        } else {
                            Log.e(TAG, resultText);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e(TAG, "设置当前城市JSON解析出错");
                    }
                } else {
                    Log.e(TAG, "获取数据错误，请稍后重试！");
                }
            }
        });
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
            image_userPhoto.setImageDrawable(drawable);
            // facePhoto = drawable.toString();
            // 保存新头像
            Constants.setFaceBase(Utils.Byte2Str(Utils.Bitmap2Bytes(photo)));

            uploadFace(Utils.Byte2Str(Utils.Bitmap2Bytes(photo)));

        }
    }

    private void uploadFace(String str) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Facebase", str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("token", Constants.getToken());
        map.put("cardType", "");
        map.put("taskId", "");
        map.put("encryption", "");
        map.put("DataTypeCode", "EditUserFace");
        map.put("content", jsonObject.toString());
        WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map, new WebServiceUtils.WebServiceCallBack() {
            @Override
            public void callBack(String result) {
                if (result != null) {
                    try {
                        JSONObject object = new JSONObject(result);
                        int resultCode = object.getInt("ResultCode");
                        String resultText = Utils.initNullStr(object.getString("ResultText"));
                        if (resultCode == 0) {

                        } else {
                            Utils.myToast(mContext, resultText);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Utils.myToast(mContext, "请确认网络正常");
                }
            }
        });
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
