package com.kingja.cardpackage.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kingja.cardpackage.activity.ForgetPasswordActivity;
import com.kingja.cardpackage.activity.NewHomeActivity;
import com.kingja.cardpackage.activity.PerfectInfoActivity;
import com.kingja.cardpackage.base.BaseFragment;
import com.kingja.cardpackage.entiy.ErrorResult;
import com.kingja.cardpackage.entiy.LogInForShiMing;
import com.kingja.cardpackage.entiy.User_LogInForShiMing;
import com.kingja.cardpackage.net.ThreadPoolTask;
import com.kingja.cardpackage.net.WebServiceCallBack;
import com.kingja.cardpackage.util.CheckUtil;
import com.kingja.cardpackage.util.Constants;
import com.kingja.cardpackage.util.DataManager;
import com.kingja.cardpackage.util.GoUtil;
import com.kingja.cardpackage.util.MD5Util;
import com.kingja.cardpackage.util.PhoneManager;
import com.kingja.supershapeview.SuperShapeTextView;
import com.tdr.wisdome.R;
import com.tdr.wisdome.actvitiy.ForgetPwdActivity;
import com.tdr.wisdome.view.material.MaterialEditText;

/**
 * Description:TODO
 * Create Time:2017/7/18 15:00
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener {
    private MaterialEditText mMetLoignUsername;
    private MaterialEditText mMetLoginPassword;
    private SuperShapeTextView mStvLoginConfirm;
    private String username;
    private String password;
    private TextView mTvLoginForgetPassword;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_login_k;
    }

    @Override
    public void initFragmentView(View view, Bundle savedInstanceState) {
        mMetLoignUsername = (MaterialEditText) view.findViewById(R.id.met_loign_username);
        mMetLoginPassword = (MaterialEditText) view.findViewById(R.id.met_login_password);
        mStvLoginConfirm = (SuperShapeTextView) view.findViewById(R.id.stv_login_confirm);
        mTvLoginForgetPassword = (TextView) view.findViewById(R.id.tv_login_forget_password);
    }

    @Override
    public void initFragmentVariables() {

    }

    @Override
    public void initFragmentNet() {

    }

    @Override
    public void initFragmentData() {
        mStvLoginConfirm.setOnClickListener(this);
        mStvLoginConfirm.setOnClickListener(this);
        mTvLoginForgetPassword.setOnClickListener(this);
    }

    @Override
    public void setFragmentData() {

    }

    private void doLogin() {
        setProgressDialog(true);
        LogInForShiMing param = new LogInForShiMing();
        param.setTaskID("1");
        param.setCityCode("3303");
        param.setPHONE(username);
        param.setPASSWORD(MD5Util.getMD5(password));
        param.setSOFTTYPE(5);
        param.setSOFTVERSION(1.5);
        param.setPHONEINFO(PhoneManager.getInfo(getActivity()));

        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_EMPTY, Constants
                        .User_LogInForShiMing, param)
                .setBeanType(User_LogInForShiMing.class)
                .setCallBack(new WebServiceCallBack<User_LogInForShiMing>() {
                    @Override
                    public void onSuccess(User_LogInForShiMing bean) {
                        setProgressDialog(false);
                        GoUtil.goActivityAndFinish(getActivity(), NewHomeActivity.class);
                        save2Local(bean.getContent());
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build().execute();
    }

    private void save2Local(User_LogInForShiMing.ContentBean content) {
        DataManager.putToken(content.getTOKEN());
        DataManager.putIdCard(content.getIDENTITYCARD());
        DataManager.putRealName(content.getRENALNAME());
        DataManager.putSex(content.getSEX());
        DataManager.putBirthday(content.getBIRTHDAY());
        DataManager.putPhone(content.getPHONE());
        DataManager.putUserId(content.getUSERID());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login_forget_password:
                GoUtil.goActivity(getActivity(), ForgetPasswordActivity.class);
                break;
            case R.id.stv_login_confirm:
                username = mMetLoignUsername.getText().toString().trim();
                password = mMetLoginPassword.getText().toString().trim();
                if (CheckUtil.checkEmpty(username, "请输入用户名") && CheckUtil.checkEmpty(password, "请输入密码")) {
                    doLogin();
                }
                break;

        }
    }
}