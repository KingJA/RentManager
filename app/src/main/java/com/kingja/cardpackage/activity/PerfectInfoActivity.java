package com.kingja.cardpackage.activity;

import android.text.TextUtils;
import android.view.View;

import com.kingja.cardpackage.entiy.ErrorResult;
import com.kingja.cardpackage.entiy.User_AddDetailForShiMing;
import com.kingja.cardpackage.entiy.User_LogInForShiMing;
import com.kingja.cardpackage.net.ThreadPoolTask;
import com.kingja.cardpackage.net.WebServiceCallBack;
import com.kingja.cardpackage.util.CheckUtil;
import com.kingja.cardpackage.util.Constants;
import com.kingja.cardpackage.util.DataManager;
import com.kingja.cardpackage.util.GoUtil;
import com.kingja.cardpackage.util.NoDoubleClickListener;
import com.kingja.cardpackage.util.TempConstants;
import com.kingja.cardpackage.util.ToastUtil;
import com.kingja.supershapeview.SuperShapeTextView;
import com.tdr.wisdome.R;
import com.tdr.wisdome.view.material.MaterialEditText;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:TODO
 * Create Time:2017/7/22 13:49
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class PerfectInfoActivity extends BackTitleActivity {
    private MaterialEditText mMetUserinfoIdcard;
    private MaterialEditText mMetUserinfoBirthday;
    private MaterialEditText mMetUserinfoRealname;
    private MaterialEditText mMetUserinfoSex;
    private MaterialEditText mMetUserinfoAddress;
    private SuperShapeTextView mStvUserinfoConfirm;
    private String idCard;
    private String birthday;
    private String realName;
    private String sex;
    private String address;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initContentView() {
        mMetUserinfoIdcard = (MaterialEditText) findViewById(R.id.met_userinfo_idcard);
        mMetUserinfoBirthday = (MaterialEditText) findViewById(R.id.met_userinfo_birthday);
        mMetUserinfoRealname = (MaterialEditText) findViewById(R.id.met_userinfo_realname);
        mMetUserinfoSex = (MaterialEditText) findViewById(R.id.met_userinfo_sex);
        mMetUserinfoAddress = (MaterialEditText) findViewById(R.id.met_userinfo_address);
        mStvUserinfoConfirm = (SuperShapeTextView) findViewById(R.id.stv_userinfo_confirm);
    }

    @Override
    protected int getBackContentView() {
        return R.layout.activity_perfect_info;
    }

    @Override
    protected void initNet() {

    }

    @Override
    protected void initData() {
        mStvUserinfoConfirm.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                idCard = mMetUserinfoIdcard.getText().toString().trim();
                birthday = mMetUserinfoBirthday.getText().toString().trim();
                realName = mMetUserinfoRealname.getText().toString().trim();
                sex = mMetUserinfoSex.getText().toString().trim();
                address = mMetUserinfoAddress.getText().toString().trim();
                if (CheckUtil.checkIdCard(idCard, "身份证号格式错误")
                        && CheckUtil.checkEmpty(birthday, "请输入出生年月")
                        && CheckUtil.checkEmpty(realName, "请输入真实姓名")
                        && CheckUtil.checkEmpty(sex, "请选择性别")
                        && CheckUtil.checkEmpty(address, "请输入详细地址")) {
                    perfectUserInfo();
                }

            }
        });

    }

    private void perfectUserInfo() {
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.TaskID, TempConstants.DEFAULT_TASK_ID);
        param.put("IDENTITYCARD", idCard);
        param.put("RENALNAME", realName);
        param.put("PHONE", DataManager.getPhone());
        param.put("SEX", sex);
        param.put("BIRTHDAY", birthday);
        param.put("HJADDRESS", address);
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_EMPTY, Constants
                        .User_AddDetailForShiMing, param)
                .setBeanType(User_AddDetailForShiMing.class)
                .setCallBack(new WebServiceCallBack<User_AddDetailForShiMing>() {
                    @Override
                    public void onSuccess(User_AddDetailForShiMing bean) {
                        setProgressDialog(false);
                        ToastUtil.showToast("完善用户资料成功");
                        save2Local();
                        finish();
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build().execute();
    }
    private void save2Local() {
        DataManager.putIdCard(idCard);
        DataManager.putRealName(realName);
        DataManager.putSex(sex);
        DataManager.putBirthday(birthday);
    }
    @Override
    protected void setData() {
        setTitle("用户资料");
        if (!TextUtils.isEmpty(DataManager.getIdentitycard())) {
            mMetUserinfoIdcard.setText(DataManager.getIdentitycard());
            mMetUserinfoBirthday.setText(DataManager.getBirthday());
            mMetUserinfoRealname.setText(DataManager.getRealName());
            mMetUserinfoSex.setText(DataManager.getSex());
            mMetUserinfoAddress.setText(DataManager.getAddress());
            mMetUserinfoIdcard.setEnabled(false);
            mMetUserinfoBirthday.setEnabled(false);
            mMetUserinfoRealname.setEnabled(false);
            mMetUserinfoSex.setEnabled(false);
            mMetUserinfoAddress.setEnabled(false);
        } else {
            mStvUserinfoConfirm.setVisibility(View.VISIBLE);
        }
    }
}
