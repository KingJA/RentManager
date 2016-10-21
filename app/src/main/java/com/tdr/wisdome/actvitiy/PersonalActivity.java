package com.tdr.wisdome.actvitiy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tdr.wisdome.R;
import com.tdr.wisdome.util.Constants;
import com.tdr.wisdome.util.Utils;
import com.tdr.wisdome.util.WebServiceUtils;
import com.tdr.wisdome.view.niftydialog.NiftyDialogBuilder;

import java.util.HashMap;

/**
 * 个人中心
 * Created by Linus_Xie on 2016/8/6.
 */
public class PersonalActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "PersonalActivity";

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        mContext = this;

        initView();
    }

    private ImageView image_back;
    private TextView text_title;

    private RelativeLayout relative_userPersonal, relative_coinStore, relative_perfectInfo, relative_realName, relative_myMessage, relative_myPackage, relative_setting;
    private ImageView image_userPhoto, image_state;
    private TextView text_realName, text_userName, text_userLoc, text_coinNum;

    private Button btn_quit;

    private void initView() {
        image_back = (ImageView) findViewById(R.id.image_back);
        image_back.setOnClickListener(this);
        text_title = (TextView) findViewById(R.id.text_title);
        text_title.setText("用户中心");

        relative_userPersonal = (RelativeLayout) findViewById(R.id.relative_userPersonal);
        relative_userPersonal.setOnClickListener(this);
        image_userPhoto = (ImageView) findViewById(R.id.image_userPhoto);
        text_userName = (TextView) findViewById(R.id.text_userName);
        image_state = (ImageView) findViewById(R.id.image_state);
        text_userLoc = (TextView) findViewById(R.id.text_userLoc);
        text_userName = (TextView) findViewById(R.id.text_userName);
        text_coinNum = (TextView) findViewById(R.id.text_coinNum);
        relative_coinStore = (RelativeLayout) findViewById(R.id.relative_coinStore);
        relative_coinStore.setOnClickListener(this);
        relative_perfectInfo = (RelativeLayout) findViewById(R.id.relative_perfectInfo);
        relative_perfectInfo.setOnClickListener(this);
        relative_realName = (RelativeLayout) findViewById(R.id.relative_realName);
        relative_realName.setOnClickListener(this);
        text_realName = (TextView) findViewById(R.id.text_realName);
        relative_myMessage = (RelativeLayout) findViewById(R.id.relative_myMessage);
        relative_myMessage.setOnClickListener(this);
        relative_myPackage = (RelativeLayout) findViewById(R.id.relative_myPackage);
        relative_myPackage.setOnClickListener(this);
        relative_setting = (RelativeLayout) findViewById(R.id.relative_setting);
        relative_setting.setOnClickListener(this);

        btn_quit = (Button) findViewById(R.id.btn_quit);
        btn_quit.setOnClickListener(this);
    }

    private void initData() {

        if (Constants.getRealName().equals("")) {
            //弹出对话框，完善资料
            dialogShow(0, "资料不完整，是否前去完善资料");
        }

        if (Constants.getRealName().equals("")) {
            text_userName.setText(Constants.getUserPhone());
        } else {
            text_userName.setText(Constants.getRealName());
        }
        if (Constants.getCertification().equals("0")) {//未认证
            image_state.setImageResource(R.mipmap.image_unauthorized);
        } else if (Constants.getCertification().equals("1")) {
            image_state.setImageResource(R.mipmap.image_authorized);
            text_realName.setText("实名认证（已认证）");
            relative_realName.setClickable(false);
        } else if (Constants.getCertification().equals("2")) {
            image_state.setImageResource(R.mipmap.image_authorizeding);
        }
        text_userLoc.setText(Constants.getCityName());

        image_userPhoto.setImageBitmap(Utils.stringtoBitmap(Constants.getFaceBase()));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_back:
                Intent intent0 = new Intent(mContext, MainActivity.class);
                startActivity(intent0);
                finish();
                break;

            case R.id.relative_userPersonal:
                Intent intent1 = new Intent(mContext, PersonInfoActivity.class);
                startActivity(intent1);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;

            case R.id.relative_coinStore:

                break;

            case R.id.relative_perfectInfo:
                Intent intent3 = new Intent();
                intent3.setClass(mContext, PerfectActivity.class);
                startActivity(intent3);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;

            case R.id.relative_realName:
                Intent intent4 = new Intent(mContext, RealNameActivity.class);
                startActivity(intent4);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;

            case R.id.relative_myMessage:
                Intent intent5 = new Intent(mContext, MsgActivity.class);
                startActivity(intent5);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                finish();
                break;

            case R.id.relative_myPackage:
                Intent intent6 = new Intent(mContext, AddManagerActivity.class);
                startActivity(intent6);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                finish();
                break;

            case R.id.relative_setting:

                break;

            case R.id.btn_quit:
                dialogShow(1, "是否退出当前帐号？");
                break;
        }
    }

    private NiftyDialogBuilder dialogBuilder;
    private NiftyDialogBuilder.Effectstype effectstype;

    private void dialogShow(int flag, String message) {
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
                }
            }).setButton2Click(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogBuilder.dismiss();
                    Intent intent = new Intent();
                    intent.setClass(mContext, PerfectActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                }
            }).show();

        } else if (flag == 1) {
            effectstype = NiftyDialogBuilder.Effectstype.Fadein;
            dialogBuilder.withTitle("提示").withTitleColor("#333333").withMessage(message)
                    .isCancelableOnTouchOutside(false).withEffect(effectstype).withButton1Text("取消")
                    .setCustomView(R.layout.custom_view, mContext).withButton2Text("确认").setButton1Click(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogBuilder.dismiss();
                }
            }).setButton2Click(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loginOut();
                    Constants.setToken("");
                    Constants.setRealName("");
                    Constants.setUserIdentitycard("");
                    Constants.setUserName("");
                    Constants.setUserPhone("");
                    Constants.setCardCode("");
                    Intent intent = new Intent(mContext, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    finish();

                }
            }).show();
        }
    }

    private void loginOut() {
        HashMap<String, String> map = new HashMap<>();
        map.put("token", Constants.getToken());
        map.put("cardType", "");
        map.put("taskId", "");
        map.put("DataTypeCode", "LoginOut");
        map.put("content", "");

        WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map, new WebServiceUtils.WebServiceCallBack() {
            @Override
            public void callBack(String result) {
                Log.e(TAG, "不作处理");
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            Intent intent0 = new Intent(mContext, MainActivity.class);
            startActivity(intent0);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}
