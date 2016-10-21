package com.kingja.cardpackage.activity;

import android.app.Activity;
import android.content.Intent;
import android.widget.EditText;

import com.kingja.cardpackage.util.CheckUtil;
import com.tdr.wisdome.R;

/**
 * Description：TODO
 * Create Time：2016/8/17 13:23
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class EditRoomActivity extends BackTitleActivity implements BackTitleActivity.OnRightClickListener {

    private String mShi;
    private String mTing;
    private String mWei;
    private String mYangtai;

    private EditText mEtShi;
    private EditText mEtTing;
    private EditText mEtWei;
    private EditText mEtYangtai;


    public static final String SHI="SHI";
    public static final String TING="TING";
    public static final String WEI="WEI";
    public static final String YANGTAI="YANGTAI";

    @Override
    protected void initVariables() {
        mShi = getIntent().getStringExtra(SHI);
        mTing = getIntent().getStringExtra(TING);
        mWei = getIntent().getStringExtra(WEI);
        mYangtai = getIntent().getStringExtra(YANGTAI);
    }

    @Override
    protected void initContentView() {
        mEtShi = (EditText) findViewById(R.id.et_shi);
        mEtTing = (EditText) findViewById(R.id.et_ting);
        mEtWei = (EditText) findViewById(R.id.et_wei);
        mEtYangtai = (EditText) findViewById(R.id.et_yangtai);
    }

    @Override
    protected int getBackContentView() {
        return R.layout.activity_edit_room;
    }

    @Override
    protected void initNet() {

    }

    @Override
    protected void initData() {
    }

    @Override
    protected void setData() {
        setTopColor(TopColor.WHITE);
        setOnRightClickListener(this, "保存");
        setTitle("户型");
        mEtShi.setText(mShi);
        mEtTing.setText(mTing);
        mEtWei.setText(mWei);
        mEtYangtai.setText(mYangtai);

    }


    public static void goActivity(Activity activity, String shi, String ting, String wei, String yangtai, int requestCode) {
        Intent intent = new Intent(activity, EditRoomActivity.class);
        intent.putExtra(SHI, shi);
        intent.putExtra(TING, ting);
        intent.putExtra(WEI, wei);
        intent.putExtra(YANGTAI, yangtai);
        activity.startActivityForResult(intent, requestCode);

    }

    @Override
    public void onRightClick() {
        mShi = mEtShi.getText().toString().trim();
        mTing = mEtTing.getText().toString().trim();
        mWei = mEtWei.getText().toString().trim();
        mYangtai = mEtYangtai.getText().toString().trim();
        if (CheckUtil.checkEmpty(mShi, "请输入卧室数") &&
                CheckUtil.checkEmpty(mTing, "请输入客厅数") &&
                CheckUtil.checkEmpty(mWei, "请输入卫生间数") &&
                CheckUtil.checkEmpty(mYangtai, "请输入阳台数") ) {
            Intent intent = new Intent();
            intent.putExtra(SHI, mShi);
            intent.putExtra(TING, mTing);
            intent.putExtra(WEI, mWei);
            intent.putExtra(YANGTAI, mYangtai);
            setResult(RESULT_OK, intent);
            finish();
        }

    }
}
