package com.tdr.wisdome.actvitiy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdr.wisdome.R;
import com.tdr.wisdome.util.Constants;
import com.tdr.wisdome.util.Utils;
import com.tdr.wisdome.view.niftydialog.NiftyDialogBuilder;

/**
 * 实名认证
 * Created by Linus_Xie on 2016/8/6.
 */
public class RealNameActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "RealNameActivity";

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realname);

        mContext = this;

        initView();
        initData();
    }

    private ImageView image_back;
    private TextView text_title;

    private ImageView image_userPhoto;
    private TextView text_userName, text_userIdentity;
    private ImageView image_state, image_alipay, image_uploadidentity;

    private void initView() {
        image_back = (ImageView) findViewById(R.id.image_back);
        image_back.setOnClickListener(this);
        text_title = (TextView) findViewById(R.id.text_title);
        text_title.setText("实名认证");

        image_userPhoto = (ImageView) findViewById(R.id.image_userPhoto);
        text_userName = (TextView) findViewById(R.id.text_userName);
        image_state = (ImageView) findViewById(R.id.image_state);
        text_userIdentity = (TextView) findViewById(R.id.text_userIdentity);
        image_alipay = (ImageView) findViewById(R.id.image_alipay);
        image_alipay.setOnClickListener(this);
        image_uploadidentity = (ImageView) findViewById(R.id.image_uploadidentity);
        image_uploadidentity.setOnClickListener(this);
    }

    private void initData() {
        image_userPhoto.setImageBitmap(Utils.stringtoBitmap(Constants.getFaceBase()));
        if (Constants.getCertification().equals("0")) {//未认证
            image_state.setImageResource(R.mipmap.image_unauthorized);
        } else if (Constants.getCertification().equals("1")) {
            image_state.setImageResource(R.mipmap.image_authorized);
        } else if (Constants.getCertification().equals("2")) {
            image_state.setImageResource(R.mipmap.image_authorizeding);
        }
        text_userName.setText(Constants.getRealName());
        text_userIdentity.setText(Utils.hideID(Utils.initNullStr(Constants.getUserIdentitycard())));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_back:
                finish();
                break;

            case R.id.image_alipay:
                if (Constants.getCertification().equals("2")) {
                    dialogShow();
                } else {
                    Utils.myToast(mContext,"支付宝实名认证即将开放，请先使用人工认证方式");
                }

                break;

            case R.id.image_uploadidentity:
                if (Constants.getCertification().equals("2")) {
                    dialogShow();
                } else {
                    Intent intent = new Intent(RealNameActivity.this, UploadIdentityActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    finish();
                }
                break;
        }
    }

    private NiftyDialogBuilder dialogBuilder;
    private NiftyDialogBuilder.Effectstype effectstype;

    private void dialogShow() {
        if (dialogBuilder != null && dialogBuilder.isShowing())
            return;

        dialogBuilder = NiftyDialogBuilder.getInstance(this);

        effectstype = NiftyDialogBuilder.Effectstype.Fadein;
        dialogBuilder.withTitle("提示").withTitleColor("#333333").withMessage("实名认证中...")
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
            }
        }).show();

    }
}
