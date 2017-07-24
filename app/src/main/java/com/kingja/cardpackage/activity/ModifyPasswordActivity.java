package com.kingja.cardpackage.activity;

import android.view.View;

import com.kingja.cardpackage.entiy.CheckElder;
import com.kingja.cardpackage.entiy.ErrorResult;
import com.kingja.cardpackage.net.ThreadPoolTask;
import com.kingja.cardpackage.net.WebServiceCallBack;
import com.kingja.cardpackage.util.CheckUtil;
import com.kingja.cardpackage.util.Constants;
import com.kingja.cardpackage.util.DataManager;
import com.kingja.cardpackage.util.ToastUtil;
import com.kingja.supershapeview.SuperShapeTextView;
import com.tdr.wisdome.R;
import com.tdr.wisdome.util.MD5;
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
public class ModifyPasswordActivity extends BackTitleActivity {
    private MaterialEditText mEtOldPwd;
    private MaterialEditText mEtNewPwd;
    private MaterialEditText mEtRepeatPwd;
    private SuperShapeTextView mStvEditpwdEdit;


    @Override
    protected void initVariables() {

    }

    @Override
    protected void initContentView() {
        mEtOldPwd = (MaterialEditText) findViewById(R.id.et_oldPwd);
        mEtNewPwd = (MaterialEditText) findViewById(R.id.et_newPwd);
        mEtRepeatPwd = (MaterialEditText) findViewById(R.id.et_repeatPwd);
        mStvEditpwdEdit = (SuperShapeTextView) findViewById(R.id.stv_editpwd_edit);
    }

    @Override
    protected int getBackContentView() {
        return R.layout.activity_editpwd;
    }

    @Override
    protected void initNet() {

    }

    @Override
    protected void initData() {
        mStvEditpwdEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkData();
            }
        });
    }

    @Override
    protected void setData() {
        setTitle("修改密码");
    }

    private void checkData() {
        String oldPwd = mEtOldPwd.getText().toString().trim();
        String newPwd = mEtNewPwd.getText().toString().trim();
        String repeatPwd = mEtRepeatPwd.getText().toString().trim();
        if (CheckUtil.checkPasswordFormat(oldPwd, 6, 16, "原")
                && CheckUtil.checkPasswordFormat(newPwd, 6, 16, "新")
                && CheckUtil.checkPasswordFormat(repeatPwd, 6, 16, "重复")
                && CheckUtil.checkEquil(newPwd, repeatPwd, "重复密码不相同，请重新输入")) {
            try {
                oldPwd = MD5.getMD5(oldPwd);
                newPwd = MD5.getMD5(newPwd);
                modifyPassword(oldPwd, newPwd);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

        }
    }

    private void modifyPassword(String oldPwd, String newPwd) {
        setProgressDialog(true);
        Map<String, Object> param = new HashMap<>();
        param.put("OLDPASSWORD", oldPwd);
        param.put("NEWPASSWORD", newPwd);
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_EMPTY, Constants
                        .User_PasswordModifyForShiMing, param)
                .setBeanType(Object.class)
                .setCallBack(new WebServiceCallBack<Object>() {
                    @Override
                    public void onSuccess(Object bean) {
                        setProgressDialog(false);
                        ToastUtil.showToast("修改密码成功");
                        finish();
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build().execute();
    }

}
