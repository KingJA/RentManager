package com.kingja.cardpackage.activity;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.kingja.cardpackage.entiy.Common_SendValidCode;
import com.kingja.cardpackage.entiy.ErrorResult;
import com.kingja.cardpackage.net.ThreadPoolTask;
import com.kingja.cardpackage.net.WebServiceCallBack;
import com.kingja.cardpackage.util.CheckUtil;
import com.kingja.cardpackage.util.Constants;
import com.kingja.cardpackage.util.DataManager;
import com.kingja.cardpackage.util.NoDoubleClickListener;
import com.kingja.cardpackage.util.TempConstants;
import com.kingja.cardpackage.util.ToastUtil;
import com.kingja.supershapeview.SuperShapeTextView;
import com.tdr.wisdome.R;
import com.tdr.wisdome.util.MD5;
import com.tdr.wisdome.view.material.MaterialEditText;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:忘记密码
 * Create Time:2017/7/24 10:57
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ForgetPasswordActivity extends BackTitleActivity {
    private String validCode;
    private String password;
    private String phone;
    private String validCodeSN;
    private CountDownTimer timer;
    private MaterialEditText mMetSetPasswordPhone;
    private MaterialEditText mMetSetPasswordCode;
    private TextView mTvSetPasswordGetCode;
    private MaterialEditText mMetSetPasswordPassword;
    private MaterialEditText mMetSetPasswordRepeatPassword;
    private SuperShapeTextView mStvSetPasswordConfirm;

    @Override
    protected void initVariables() {
    }

    @Override
    protected void initContentView() {
        mMetSetPasswordPhone = (MaterialEditText) findViewById(R.id.met_setPassword_phone);
        mMetSetPasswordCode = (MaterialEditText) findViewById(R.id.met_setPassword_code);
        mTvSetPasswordGetCode = (TextView) findViewById(R.id.tv_setPassword_getCode);
        mMetSetPasswordPassword = (MaterialEditText) findViewById(R.id.met_setPassword_password);
        mMetSetPasswordRepeatPassword = (MaterialEditText) findViewById(R.id.met_setPassword_repeatPassword);
        mStvSetPasswordConfirm = (SuperShapeTextView) findViewById(R.id.stv_setPassword_confirm);
    }

    @Override
    protected int getBackContentView() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void initNet() {

    }

    @Override
    protected void initData() {
        mStvSetPasswordConfirm.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                checkData();
            }
        });
        mTvSetPasswordGetCode.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                phone = mMetSetPasswordPhone.getText().toString().trim();
                if (CheckUtil.checkPhoneFormat(phone)) {
                    startCountDownTime(5);
                    getCode();
                }
            }
        });
    }

    @Override
    protected void setData() {
        setTitle("忘记密码");
    }

    private void checkData() {
        phone = mMetSetPasswordPhone.getText().toString().trim();
        validCode = mMetSetPasswordCode.getText().toString().trim();
        password = mMetSetPasswordPassword.getText().toString().trim();
        String repeatPassword = mMetSetPasswordRepeatPassword.getText().toString().trim();
        if (CheckUtil.checkEmpty(validCode, "请输入验证码")
                && CheckUtil.checkPasswordFormat(password, 6, 16, "新")
                && CheckUtil.checkPasswordFormat(repeatPassword, 6, 16, "重复")
                && CheckUtil.checkEquil(password, repeatPassword, "重复密码不相同，请重新输入")) {
            password = MD5.createMd5(password);
            modifyPassword();

        }
    }

    private void modifyPassword() {
        setProgressDialog(true);
        Map<String, Object> param = new HashMap<>();
        param.put("TaskID", "1");
        param.put("NEWPASSWORD", password);
        param.put("PHONE", phone);
        param.put("ValidCode", validCode);
        param.put("ValidCodeSN", validCodeSN);
        new ThreadPoolTask.Builder()
                .setGeneralParam(Constants.EMPTY_STRING, Constants.CARD_TYPE_EMPTY, Constants
                                .User_ResetPasswordForShiMing,
                        param)
                .setBeanType(Object.class)
                .setCallBack(new WebServiceCallBack<Object>() {
                    @Override
                    public void onSuccess(Object bean) {
                        setProgressDialog(false);
                        ToastUtil.showToast("密码重置成功");
                        finish();
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build().execute();

    }


    private void startCountDownTime(long time) {
        mTvSetPasswordGetCode.setEnabled(false);
        timer = new CountDownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTvSetPasswordGetCode.setText("获取中("+millisUntilFinished / 1000 + "秒)");
            }

            @Override
            public void onFinish() {
                mTvSetPasswordGetCode.setText("重新获取");
                mTvSetPasswordGetCode.setEnabled(true);
            }
        };
        timer.start();
    }

    private void getCode() {
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.TaskID, TempConstants.DEFAULT_TASK_ID);
        param.put("PHONENUM", phone);
        param.put("USINGFOR", Constants.User_ResetPasswordForShiMing);
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
