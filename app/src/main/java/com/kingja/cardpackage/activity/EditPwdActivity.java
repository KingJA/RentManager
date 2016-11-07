package com.kingja.cardpackage.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingja.cardpackage.entiy.CheckElder;
import com.kingja.cardpackage.entiy.ErrorResult;
import com.kingja.cardpackage.net.ThreadPoolTask;
import com.kingja.cardpackage.net.WebServiceCallBack;
import com.kingja.cardpackage.util.CheckUtil;
import com.kingja.cardpackage.util.DataManager;
import com.kingja.cardpackage.util.ToastUtil;
import com.tdr.wisdome.R;
import com.tdr.wisdome.util.MD5;
import com.tdr.wisdome.view.ZProgressHUD;
import com.tdr.wisdome.view.material.MaterialEditText;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * Description：TODO
 * Create Time：2016/10/21 10:19
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class EditPwdActivity extends Activity {
    private ZProgressHUD mProgressHUD;
    private MaterialEditText mEtOldPwd;
    private MaterialEditText mEtNewPwd;
    private MaterialEditText mEtRepeatPwd;
    private Button mBtnConfirm;
    private ImageView fl_menu;
    private TextView text_title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpwd);
        initView();
    }


    private void initView() {
        mProgressHUD = new ZProgressHUD(this);
        text_title = (TextView) findViewById(R.id.text_title);
        fl_menu = (ImageView) findViewById(R.id.image_back);
        mEtOldPwd = (MaterialEditText) findViewById(R.id.et_oldPwd);
        mEtNewPwd = (MaterialEditText) findViewById(R.id.et_newPwd);
        mEtRepeatPwd = (MaterialEditText) findViewById(R.id.et_repeatPwd);
        mBtnConfirm = (Button) findViewById(R.id.btn_confirm);
        mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkData();
            }
        });
        fl_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        text_title.setText("修改密码");
    }

    /**
     * 密码校验
     */
    private void checkData() {
        String oldPwd = mEtOldPwd.getText().toString().trim();
        String newPwd = mEtNewPwd.getText().toString().trim();
        String repeatPwd = mEtRepeatPwd.getText().toString().trim();
        if (CheckUtil.checkPasswordFormat(oldPwd,6,16,"原")
                &&CheckUtil.checkPasswordFormat(newPwd,6,16,"新")
                &&CheckUtil.checkPasswordFormat(repeatPwd,6,16,"重复")
                &&CheckUtil.checkEquil(newPwd,repeatPwd,"重复密码不相同，请重新输入")){
            try {
                oldPwd= MD5.getMD5(oldPwd);
                newPwd= MD5.getMD5(newPwd);
                upLoad(oldPwd,newPwd);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 密码修改
     * @param oldPwd
     * @param newPwd
     */
    private void upLoad(String oldPwd, String newPwd) {
        mProgressHUD.show();
        Map<String, Object> param = new HashMap<>();
        param.put("Phone", DataManager.getUserPhone());
        param.put("OldPassword",oldPwd);
        param.put("UserPassword", newPwd);
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), "", "ChangePasswordNoVerify", param)
                .setBeanType(CheckElder.class)
                .setCallBack(new WebServiceCallBack<CheckElder>() {
                    @Override
                    public void onSuccess(CheckElder bean) {
                        mProgressHUD.dismiss();
                        ToastUtil.showToast("修改密码成功");
                        finish();
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        ToastUtil.showToast(errorResult.getResultText());
                        mProgressHUD.dismiss();
                    }
                }).build().execute();
    }

}
