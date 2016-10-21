package com.tdr.wisdome.actvitiy;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tdr.wisdome.R;
import com.tdr.wisdome.util.Constants;
import com.tdr.wisdome.util.Utils;
import com.tdr.wisdome.util.WebServiceUtils;
import com.tdr.wisdome.view.ZProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

/**
 * 上传身份证照片
 * Created by Linus_Xie on 2016/9/1.
 */
public class UploadIdentityActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "UploadIdentityActivity";

    private Context mContext;

    private Bitmap bitmapIdentity;// 身份证照片

    private Resources resources;
    private String baseIdentity = "";

    private ZProgressHUD mProgressHUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadidentity);
        mContext = this;
        this.resources = getResources();
        initView();
        initData();
    }

    private ImageView image_back;
    private TextView text_title;
    private TextView text_deal;

    private RelativeLayout relative_Identity;
    private TextView text_realName, text_identity;
    private ImageView image_identity, image_identityCamera;

    private void initView() {
        image_back = (ImageView) findViewById(R.id.image_back);
        image_back.setOnClickListener(this);
        text_title = (TextView) findViewById(R.id.text_title);
        text_title.setText("上传证件照");
        text_deal = (TextView) findViewById(R.id.text_deal);
        text_deal.setText("提交");
        text_deal.setOnClickListener(this);
        text_deal.setVisibility(View.VISIBLE);

        relative_Identity = (RelativeLayout) findViewById(R.id.relative_Identity);
        text_realName = (TextView) findViewById(R.id.text_realName);
        text_identity = (TextView) findViewById(R.id.text_identity);

        image_identity = (ImageView) findViewById(R.id.image_identity);
        image_identity.setOnClickListener(this);
        image_identityCamera = (ImageView) findViewById(R.id.image_identityCamera);
        image_identityCamera.setOnClickListener(this);

        mProgressHUD = new ZProgressHUD(this);
        mProgressHUD.setMessage("提交中...");
        mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
    }

    private void initData() {
        if (Constants.getUserIdentitycard().equals("")) {
            Utils.myToast(mContext, "请先去完善资料页面完善个人信息");
            return;
        } else {
            text_realName.setText(Constants.getRealName());
            text_identity.setText(Constants.getUserIdentitycard());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_back:
                finish();
                break;

            case R.id.text_deal:
                if (baseIdentity.equals("")){
                    Utils.myToast(mContext,"请拍摄身份证正面照");
                    break;
                }
                mProgressHUD.show();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("idcardphotobase64", baseIdentity);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                HashMap<String, String> map = new HashMap<>();
                map.put("token", Constants.getToken());
                map.put("cardType", "");
                map.put("taskId", "");
                map.put("Encryption", "");
                map.put("DataTypeCode", "EditIDCardPhoto");
                map.put("content", jsonObject.toString());
                WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map, new WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {
                        if (result != null){
                            try {
                                JSONObject object = new JSONObject(result);
                                int resultCode = object.getInt("ResultCode");
                                String resultText = Utils.initNullStr(object.getString("ResultText"));
                                if (resultCode == 0) {
                                    Utils.myToast(mContext,"认证中...");
                                    Constants.setCertification("2");
                                    Intent intent = new Intent(UploadIdentityActivity.this,PersonalActivity.class);
                                    startActivity(intent);
                                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                mProgressHUD.dismiss();
                                Utils.myToast(mContext,"JSON解析出错");
                            }
                        } else {
                            mProgressHUD.dismiss();
                            Utils.myToast(mContext, "获取数据错误，请稍后重试！");
                        }
                    }
                });
                break;

            case R.id.image_identity:
            case R.id.image_identityCamera:
                Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri headuri = Uri.fromFile(new File(Environment
                        .getExternalStorageDirectory(), "imageIdentity.jpg"));
                intent1.putExtra(MediaStore.EXTRA_OUTPUT, headuri);
                startActivityForResult(intent1, 1);
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            String sdStatus = Environment.getExternalStorageState();
            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
                Utils.myToast(this, "SD卡不可用，请检查您的SD卡！");
                return;
            }
            if (requestCode == 1) {
                File file1 = new File(Environment.getExternalStorageDirectory()
                        + "/imageIdentity.jpg");
                int degree1 = Utils.readPictureDegree(file1
                        .getAbsolutePath());
                this.bitmapIdentity = Utils.rotaingImageView(degree1,
                        Utils.getBitmapFromFile(file1, 400, 600));
                Drawable bd = new BitmapDrawable(resources, bitmapIdentity);
                relative_Identity.setBackground(bd);
                image_identity.setVisibility(View.GONE);
                baseIdentity = Utils.Byte2Str(Utils.Bitmap2Bytes(bitmapIdentity));
            }
        }
    }
}
