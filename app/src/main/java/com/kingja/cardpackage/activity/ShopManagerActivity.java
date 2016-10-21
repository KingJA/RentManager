package com.kingja.cardpackage.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kingja.cardpackage.db.DbDaoXutils3;
import com.kingja.cardpackage.entiy.Basic_Dictionary_Kj;
import com.kingja.cardpackage.entiy.ErrorResult;
import com.kingja.cardpackage.entiy.ShangPu_Modify;
import com.kingja.cardpackage.entiy.ShangPu_ViewInfo;
import com.kingja.cardpackage.entiy.ShopBean;
import com.kingja.cardpackage.net.ThreadPoolTask;
import com.kingja.cardpackage.net.WebServiceCallBack;
import com.kingja.cardpackage.util.Constants;
import com.kingja.cardpackage.util.DataManager;
import com.kingja.cardpackage.util.TempConstants;
import com.kingja.cardpackage.util.ToastUtil;
import com.tdr.wisdome.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Description：TODO
 * Create Time：2016/8/31 9:28
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ShopManagerActivity extends BackTitleActivity implements BackTitleActivity.OnRightClickListener {

    private String mShopId;
    private RelativeLayout mRlShopName;
    private TextView mTvShopName;
    private RelativeLayout mRlShopType;
    private TextView mTvShopType;
    private String mShopType;
    private String mShopName;
    private String mShopTypeCode;
    private String mAddress;
    private String mSTANDARDADDRCODE;
    private String mJWHCODE;
    private String mPhone;


    @Override
    protected void initVariables() {
        mShopId = getIntent().getStringExtra(TempConstants.SHOPID);


    }

    @Override
    protected void initContentView() {
        mRlShopName = (RelativeLayout) findViewById(R.id.rl_shop_name);
        mRlShopType = (RelativeLayout) findViewById(R.id.rl_shop_type);
        mTvShopName = (TextView) findViewById(R.id.tv_shop_name);
        mTvShopType = (TextView) findViewById(R.id.tv_shop_type);
    }

    @Override
    protected int getBackContentView() {
        return R.layout.activity_shop_manager;
    }

    @Override
    protected void initNet() {
        setProgressDialog(true);
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.TaskID, TempConstants.DEFAULT_TASK_ID);
        param.put(TempConstants.SHOPID, mShopId);
        param.put(TempConstants.PageIndex, TempConstants.DEFAULT_PAGE_INDEX);
        param.put(TempConstants.PageSize, TempConstants.DEFAULT_PAGE_SIZE);

        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_SHOP, Constants.ShangPu_ViewInfo, param)
                .setBeanType(ShangPu_ViewInfo.class)
                .setCallBack(new WebServiceCallBack<ShangPu_ViewInfo>() {
                    @Override
                    public void onSuccess(ShangPu_ViewInfo bean) {
                        ShopBean content = bean.getContent();
                        setProgressDialog(false);
                        fillData(content);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build().execute();
    }

    private void fillData(ShopBean content) {
        mShopTypeCode = content.getSHOPTYPE();
        Basic_Dictionary_Kj shopType = (Basic_Dictionary_Kj) DbDaoXutils3.getInstance().sleectFirst(Basic_Dictionary_Kj.class, "COLUMNCODE", "SHOPTYPE", "COLUMNVALUE", mShopTypeCode);
        if (shopType != null) {
            mShopType = shopType.getCOLUMNCOMMENT();
        }
        mShopName = content.getSHOPNAME();
        mAddress = content.getADDRESS();
        mSTANDARDADDRCODE = content.getSTANDARDADDRCODE();
        mJWHCODE = content.getJWHCODE();
        mPhone = content.getPHONE();
        mTvShopType.setText(mShopType);
        mTvShopName.setText(mShopName);
    }

    @Override
    protected void initData() {
        mRlShopName.setOnClickListener(this);
        mRlShopType.setOnClickListener(this);
        setOnRightClickListener(this,"保存");


    }

    @Override
    protected void setData() {
        setTitle("店铺管理");
    }

    public static void goActivity(Activity activity, String shopId) {
        Intent intent = new Intent(activity, ShopManagerActivity.class);
        intent.putExtra(TempConstants.SHOPID, shopId);
        activity.startActivityForResult(intent,100);
    }
    public static final int SHOP_NAME  = 1;
    public static final int SHOP_TYPE = 2;
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rl_shop_name:
                EditTextActivity.goActivity(this, "店铺名称", mShopName , "", SHOP_NAME);
                break;
            case R.id.rl_shop_type:
                EditLvActivity.goActivity(this, "SHOPTYPE", mShopTypeCode , SHOP_TYPE);
                break;

        }
    }

    /**
     * 保存店铺信息
     */

    @Override
    public void onRightClick() {
        setProgressDialog(true);
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.TaskID, TempConstants.DEFAULT_TASK_ID);
        param.put(TempConstants.SHOPID, mShopId);
        param.put("SHOPTYPE", mShopTypeCode);
        param.put("SHOPNAME", mShopName);
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_SHOP, Constants.ShangPu_Modify, param)
                .setBeanType(ShangPu_Modify.class)
                .setCallBack(new WebServiceCallBack<ShangPu_Modify>() {
                    @Override
                    public void onSuccess(ShangPu_Modify bean) {
                        setProgressDialog(false);
                        ToastUtil.showToast("修改店铺成功");
                        Intent intent = new Intent();
                        intent.putExtra("SHOP_NAME",mShopName);
                        setResult(RESULT_OK,intent);
                        finish();
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build().execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case 1:
                    mShopName=data.getStringExtra("VALUE");
                    mTvShopName.setText(mShopName);
                    break;
                case 2:
                    mShopType = data.getStringExtra("COMUMNMENT");
                    mShopTypeCode = data.getStringExtra("COLUMNVALUE");
                    mTvShopType.setText(mShopType);
                    break;

            }
        }
    }

    @Override
    protected void onClickBack() {
        showQuitDialog();
    }
}
