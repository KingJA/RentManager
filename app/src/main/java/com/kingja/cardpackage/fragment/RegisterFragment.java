package com.kingja.cardpackage.fragment;

import android.os.Bundle;
import android.view.View;

import com.kingja.cardpackage.activity.RegisterActivity;
import com.kingja.cardpackage.base.BaseFragment;
import com.kingja.cardpackage.entiy.Common_SendValidCode;
import com.kingja.cardpackage.entiy.ErrorResult;
import com.kingja.cardpackage.net.ThreadPoolTask;
import com.kingja.cardpackage.net.WebServiceCallBack;
import com.kingja.cardpackage.util.CheckUtil;
import com.kingja.cardpackage.util.Constants;
import com.kingja.cardpackage.util.DataManager;
import com.kingja.cardpackage.util.NoDoubleClickListener;
import com.kingja.cardpackage.util.TempConstants;
import com.kingja.supershapeview.SuperShapeTextView;
import com.tdr.wisdome.R;
import com.tdr.wisdome.view.material.MaterialEditText;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:TODO
 * Create Time:2017/7/22 16:48
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class RegisterFragment extends BaseFragment {
    private MaterialEditText mMetLoginPhone;
    private SuperShapeTextView mStvLoginGetcode;
    private String phone;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_register_k;
    }

    @Override
    public void initFragmentView(View view, Bundle savedInstanceState) {
        mMetLoginPhone = (MaterialEditText) view.findViewById(R.id.met_login_phone);
        mStvLoginGetcode = (SuperShapeTextView) view.findViewById(R.id.stv_login_getcode);
    }

    @Override
    public void initFragmentVariables() {

    }

    @Override
    public void initFragmentNet() {

    }

    @Override
    public void initFragmentData() {
        mStvLoginGetcode.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                phone = mMetLoginPhone.getText().toString().trim();
                if (CheckUtil.checkPhoneFormat(phone)) {
                    getCode();
                }
            }
        });

    }

    private void getCode() {
        setProgressDialog(true);
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
                        setProgressDialog(false);
                        RegisterActivity.goActivity(getActivity(), phone, bean.getContent().getValidCodeSN());
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build().execute();
    }

    @Override
    public void setFragmentData() {

    }
}
