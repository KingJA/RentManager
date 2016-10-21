package com.kingja.cardpackage.activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kingja.cardpackage.entiy.ErrorResult;
import com.kingja.cardpackage.entiy.ShangPu_SetDeployStatus;
import com.kingja.cardpackage.entiy.ShangPu_WorkTimeModify;
import com.kingja.cardpackage.entiy.ShopBean;
import com.kingja.cardpackage.net.ThreadPoolTask;
import com.kingja.cardpackage.net.WebServiceCallBack;
import com.kingja.cardpackage.util.Constants;
import com.kingja.cardpackage.util.DataManager;
import com.kingja.cardpackage.util.TempConstants;
import com.kingja.cardpackage.util.ToastUtil;
import com.kingja.ui.wheelview.WorkTimeSelector;
import com.tdr.wisdome.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Description：店铺详情页
 * Create Time：2016/8/6 15:42
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class DetailShopActivity extends BackTitleActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, WorkTimeSelector.OnTimeSelectListener {

    private TextView mTvShopName;
    private TextView mTvShopAddress;
    private CheckBox mCbShop;
    private RelativeLayout mRlShopTime;
    private RelativeLayout mRlShopPerson;
    private RelativeLayout mRlShopManager;
    private RelativeLayout mRlShopDevice;
    private ShopBean entiy;
    private WorkTimeSelector mWorkTimeSelector;
    private String upTime;
    private String downTime;
    private int checkCode;
    private String mStartTime;
    private String mEndTime;
    private boolean isStatusChecked;
    private RelativeLayout mRlAlarm;
    private String shopName;
    private String shopAddress;
    private RelativeLayout mRlShopTransfer;


    @Override
    protected void initVariables() {
        entiy = (ShopBean) getIntent().getSerializableExtra("ENTIY");
        mStartTime = entiy.getSTARTWORKTIME();
        mEndTime = entiy.getENDWORKTIME();
        isStatusChecked = entiy.getSTATUS() == 1 ? true : false;
    }

    @Override
    protected void initContentView() {
        mTvShopName = (TextView) findViewById(R.id.tv_shop_name);
        mTvShopAddress = (TextView) findViewById(R.id.tv_shop_address);
        mCbShop = (CheckBox) findViewById(R.id.cb_shop);
        mRlShopTransfer = (RelativeLayout) findViewById(R.id.rl_shop_transfer);
        mRlShopTime = (RelativeLayout) findViewById(R.id.rl_shop_time);
        mRlShopPerson = (RelativeLayout) findViewById(R.id.rl_shop_person);
        mRlShopManager = (RelativeLayout) findViewById(R.id.rl_shop_manager);
        mRlShopDevice = (RelativeLayout) findViewById(R.id.rl_shop_device);
        mRlAlarm = (RelativeLayout) findViewById(R.id.rl_alarm);
    }

    @Override
    protected int getBackContentView() {
        return R.layout.activity_shop;
    }

    @Override
    protected void initNet() {

    }

    @Override
    protected void initData() {
        setTopColor(TopColor.BLUE);
        mRlAlarm.setOnClickListener(this);
        mRlShopTime.setOnClickListener(this);
        mRlShopPerson.setOnClickListener(this);
        mRlShopManager.setOnClickListener(this);
        mRlShopDevice.setOnClickListener(this);
        mRlShopTransfer.setOnClickListener(this);
        mCbShop.setOnCheckedChangeListener(this);

    }

    @Override
    protected void setData() {
        shopName = entiy.getSHOPNAME();
        shopAddress = entiy.getADDRESS();
        mTvShopName.setText(shopName);
        mTvShopAddress.setText(shopAddress);
        mCbShop.setChecked(entiy.getDEPLOYSTATUS() == 1 ? true : false);
    }

    public static void goActivity(Context context, ShopBean entiy) {
        Intent intent = new Intent(context, DetailShopActivity.class);
        intent.putExtra("ENTIY", entiy);
        context.startActivity(intent);
    }

     public static void goActivityInReceiver(Context context, ShopBean entiy) {
        Intent intent = new Intent(context, DetailShopActivity.class);
         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("ENTIY", entiy);
        context.startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rl_shop_time:
                mWorkTimeSelector = new WorkTimeSelector(this, mStartTime, mEndTime, isStatusChecked);
                mWorkTimeSelector.setOnTimeSelectListener(this);
                mWorkTimeSelector.show();
                break;
            case R.id.rl_shop_person:
                ShopPersonActivity.goActivity(this, entiy.getSHOPID(),shopName);
                break;
            case R.id.rl_shop_manager:
                ShopManagerActivity.goActivity(this, entiy.getSHOPID());
                break;
            case R.id.rl_shop_device:
                ShopDeviceActivity.goActivity(this, entiy.getSHOPID(), shopName);
                break;
            case R.id.rl_alarm:
                AlarmShopActivity.goActivity(this, entiy.getSHOPID());
                break;
            case R.id.rl_shop_transfer:
                TransferShopQRCodeActivity.goActivity(this, entiy.getSHOPID(),entiy.getSHOPNAME());
                break;

            default:
                break;

        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!buttonView.isPressed())
            return;
        deployStatus(isChecked ? 1 : 2);
    }

    private void deployStatus(int status) {
        setProgressDialog(true);
        final String statusText = status == 1 ? "布防成功" : "撤防成功";
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.TaskID, "1");
        param.put("SHOPID", entiy.getSHOPID());
        param.put("DEPLOYSTATUS", status);

        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_SHOP, Constants.ShangPu_SetDeployStatus, param)
                .setBeanType(ShangPu_SetDeployStatus.class)
                .setCallBack(new WebServiceCallBack<ShangPu_SetDeployStatus>() {
                    @Override
                    public void onSuccess(ShangPu_SetDeployStatus bean) {
                        setProgressDialog(false);
                        ToastUtil.showToast(statusText);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build().execute();
    }

    @Override
    public void onTimeSelect(String upHour, String upSecond, String downHour, String downSecond, boolean isChecked) {
        isStatusChecked = isChecked;
        mStartTime = upHour + ":" + upSecond;
        mEndTime = downHour + ":" + downSecond;
        upTime = upHour + ":" + upSecond + ":00";
        downTime = downHour + ":" + downSecond + ":00";
        checkCode = isChecked ? 1 : 0;
        Log.e(TAG, "upTime: " + upTime + "downTime: " + downTime);
        mWorkTimeSelector.dismiss();
        setWordTime();

    }

    private void setWordTime() {
        setProgressDialog(true);
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.TaskID, "1");
        param.put("SHOPID", entiy.getSHOPID());
        param.put("STATUS", checkCode);
        param.put("STARTWORKTIME", upTime);
        param.put("ENDWORKTIME", downTime);

        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_SHOP, Constants.ShangPu_WorkTimeModify, param)
                .setBeanType(ShangPu_WorkTimeModify.class)
                .setCallBack(new WebServiceCallBack<ShangPu_WorkTimeModify>() {
                    @Override
                    public void onSuccess(ShangPu_WorkTimeModify bean) {
                        setProgressDialog(false);
                        ToastUtil.showToast("上下班时间设置成功");
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
            shopName=data.getStringExtra("SHOP_NAME");
            mTvShopName.setText(shopName);
        }
    }
}
