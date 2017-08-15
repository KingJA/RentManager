package com.kingja.cardpackage.activity;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.kingja.cardpackage.Event.SetLoginTab;
import com.kingja.cardpackage.entiy.Common_SendValidCode;
import com.kingja.cardpackage.entiy.ErrorResult;
import com.kingja.cardpackage.entiy.RegionForShiMing;
import com.kingja.cardpackage.entiy.User_RegionForShiMing;
import com.kingja.cardpackage.net.ThreadPoolTask;
import com.kingja.cardpackage.net.WebServiceCallBack;
import com.kingja.cardpackage.util.CheckUtil;
import com.kingja.cardpackage.util.Constants;
import com.kingja.cardpackage.util.DataManager;
import com.kingja.cardpackage.util.NoDoubleClickListener;
import com.kingja.cardpackage.util.PhoneManager;
import com.kingja.cardpackage.util.TempConstants;
import com.kingja.cardpackage.util.ToastUtil;
import com.kingja.supershapeview.SuperShapeTextView;
import com.tdr.wisdome.R;
import com.tdr.wisdome.util.MD5;
import com.tdr.wisdome.view.material.MaterialEditText;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:注册
 * Create Time:2017/7/24 10:57
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class RegisterActivity extends BackTitleActivity {
    private MaterialEditText mMetRegisterCode;
    private MaterialEditText mMetRegisterPassword;
    private MaterialEditText mMetRegisterRepeatPassword;
    private SuperShapeTextView mStvRegisterRegister;
    private String validCode;
    private String password;
    private String phone;
    private String validCodeSN;
    private CountDownTimer timer;
    private TextView mTvRegisterGetCode;


    @Override
    protected void initVariables() {
        phone = getIntent().getStringExtra("phone");
        validCodeSN = getIntent().getStringExtra("validCodeSN");
    }

    @Override
    protected void initContentView() {
        mMetRegisterCode = (MaterialEditText) findViewById(R.id.met_register_code);
        mMetRegisterPassword = (MaterialEditText) findViewById(R.id.met_register_password);
        mMetRegisterRepeatPassword = (MaterialEditText) findViewById(R.id.met_register_repeatPassword);
        mStvRegisterRegister = (SuperShapeTextView) findViewById(R.id.stv_register_register);
        mTvRegisterGetCode = (TextView) findViewById(R.id.tv_register_getCode);
    }

    @Override
    protected int getBackContentView() {
        return R.layout.activity_register;
    }

    @Override
    protected void initNet() {

    }

    @Override
    protected void initData() {
        mStvRegisterRegister.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                checkData();
            }
        });
        mTvRegisterGetCode.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
               startCountDownTime(Constants.COUNT_TIME_DOWN);
                getCode();
            }
        });
    }

    @Override
    protected void setData() {
        setTitle("注册");
        startCountDownTime(Constants.COUNT_TIME_DOWN);
    }

    private void checkData() {
        validCode = mMetRegisterCode.getText().toString().trim();
        password = mMetRegisterPassword.getText().toString().trim();
        String repeatPassword = mMetRegisterRepeatPassword.getText().toString().trim();
        if (CheckUtil.checkEmpty(validCode, "请输入验证码")
                && CheckUtil.checkPasswordFormat(password, 6, 16, "新")
                && CheckUtil.checkPasswordFormat(repeatPassword, 6, 16, "重复")
                && CheckUtil.checkEquil(password, repeatPassword, "重复密码不相同，请重新输入")) {
            password = MD5.createMd5(password);
            doRegister();

        }
    }

    private void doRegister() {
        setProgressDialog(true);
        RegionForShiMing param = new RegionForShiMing();
        param.setTaskID("1");
        param.setPHONE(phone);
        param.setPASSWORD(password);
        param.setValidCode(validCode);
        param.setValidCodeSN(validCodeSN);
        param.setSOFTVERSION(1.5);
        param.setSOFTTYPE(5);
        param.setPHONEINFO(PhoneManager.getInfo(this));
        new ThreadPoolTask.Builder()
                .setGeneralParam(Constants.EMPTY_STRING, Constants.CARD_TYPE_EMPTY, Constants
                                .User_RegionForShiMing,
                        param)
                .setBeanType(User_RegionForShiMing.class)
                .setCallBack(new WebServiceCallBack<User_RegionForShiMing>() {
                    @Override
                    public void onSuccess(User_RegionForShiMing bean) {
                        setProgressDialog(false);
                        EventBus.getDefault().post(new SetLoginTab());
                        ToastUtil.showToast("注册成功");
                        finish();
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build().execute();

    }

    public static void goActivity(Context context, String phone, String validCodeSN) {
        Intent intent = new Intent(context, RegisterActivity.class);
        intent.putExtra("phone", phone);
        intent.putExtra("validCodeSN", validCodeSN);
        context.startActivity(intent);
    }

    private void startCountDownTime(long time) {
        mTvRegisterGetCode.setEnabled(false);
        timer = new CountDownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTvRegisterGetCode.setText("获取中(" + millisUntilFinished / 1000 + "秒)");
            }

            @Override
            public void onFinish() {
                mTvRegisterGetCode.setText("重新获取");
                mTvRegisterGetCode.setEnabled(true);
            }
        };
        timer.start();
    }

    private void getCode() {
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.TaskID, TempConstants.DEFAULT_TASK_ID);
        param.put("PHONENUM", phone);
        param.put("USINGFOR", Constants.User_RegionForShiMing);
        param.put("ValidCodeType", 0);
        param.put("ValidCodeLength", 4);
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_EMPTY, Constants
                                .Common_SendValidCode,
                        param)
                .setBeanType(Common_SendValidCode.class)
                .setCallBack(new WebServiceCallBack<Common_SendValidCode>() {
                    @Override
                    public void onSuccess(Common_SendValidCode bean) {
                        validCodeSN = bean.getContent().getValidCodeSN();
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                    }
                }).build().execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}
