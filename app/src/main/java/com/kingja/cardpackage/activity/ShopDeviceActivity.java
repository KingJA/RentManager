package com.kingja.cardpackage.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.kingja.cardpackage.adapter.DividerItemDecoration;
import com.kingja.cardpackage.adapter.ShopDeviceAdapter;
import com.kingja.cardpackage.entiy.Common_RemoveDevice;
import com.kingja.cardpackage.entiy.ErrorResult;
import com.kingja.cardpackage.entiy.ShangPu_DeviceLists;
import com.kingja.cardpackage.net.ThreadPoolTask;
import com.kingja.cardpackage.net.WebServiceCallBack;
import com.kingja.cardpackage.util.AppUtil;
import com.kingja.cardpackage.util.Constants;
import com.kingja.cardpackage.util.DataManager;
import com.kingja.cardpackage.util.DeviceTypeUtil;
import com.kingja.cardpackage.util.GoUtil;
import com.kingja.cardpackage.util.TempConstants;
import com.kingja.cardpackage.util.ToastUtil;
import com.kingja.cardpackage.util.VerifyCode;
import com.tdr.wisdome.R;
import com.tdr.wisdome.zbar.CaptureActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description：店铺设备管理
 * Create Time：2016/8/31 14:22
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ShopDeviceActivity extends BackTitleActivity implements BackTitleActivity.OnMenuClickListener, ShopDeviceAdapter.OnShopDeviceDeliteListener, SwipeRefreshLayout.OnRefreshListener {
    private LinearLayout mLlEmpty;
    private SwipeRefreshLayout mSrl;
    private RecyclerView mRv;
    private String mShopId;
    private List<ShangPu_DeviceLists.ContentBean> mDeviceList = new ArrayList<>();
    private ShopDeviceAdapter mShopDeviceAdapter;
    private String mShopName;
    private List<String> mShopDeviceTypeList;

    @Override
    protected void initVariables() {
        mShopId = getIntent().getStringExtra(TempConstants.SHOPID);
        mShopName = getIntent().getStringExtra("SHOP_NAME");
        mShopDeviceTypeList = DeviceTypeUtil.getDeviceTypes("1");

    }

    @Override
    protected void initContentView() {
        mLlEmpty = (LinearLayout) findViewById(R.id.ll_empty);
        mSrl = (SwipeRefreshLayout) findViewById(R.id.srl);
        mRv = (RecyclerView) findViewById(R.id.rv);

        mShopDeviceAdapter = new ShopDeviceAdapter(this, mDeviceList);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        mRv.setHasFixedSize(true);
        mRv.setAdapter(mShopDeviceAdapter);

        mSrl.setColorSchemeResources(R.color.bg_black);
        mSrl.setProgressViewOffset(false, 0, AppUtil.dp2px(24));
    }

    @Override
    protected int getBackContentView() {
        return R.layout.single_rv;
    }

    @Override
    protected void initNet() {
        doNet();
    }

    private void doNet() {
        mSrl.setRefreshing(true);
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.TaskID, TempConstants.DEFAULT_TASK_ID);
        param.put(TempConstants.SHOPID, mShopId);
        param.put(TempConstants.PageSize, TempConstants.DEFAULT_PAGE_SIZE);
        param.put(TempConstants.PageIndex, TempConstants.DEFAULT_PAGE_INDEX);
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_SHOP, Constants.ShangPu_DeviceLists, param)
                .setBeanType(ShangPu_DeviceLists.class)
                .setCallBack(new WebServiceCallBack<ShangPu_DeviceLists>() {
                    @Override
                    public void onSuccess(ShangPu_DeviceLists bean) {
                        mSrl.setRefreshing(false);
                        mDeviceList = bean.getContent();
                        mLlEmpty.setVisibility(mDeviceList.size() > 0 ? View.GONE : View.VISIBLE);
                        mShopDeviceAdapter.setData(mDeviceList);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        mSrl.setRefreshing(false);
                    }
                }).build().execute();
    }

    @Override
    protected void initData() {
        setOnMenuClickListener(this, R.drawable.bg_scan);
        mShopDeviceAdapter.setOnShopDeviceDeliteListener(this);
        mSrl.setOnRefreshListener(this);
    }

    @Override
    protected void setData() {
        setTitle("设备管理");
    }

    @Override
    public void onMenuClick() {
        mZeusManager.checkPermission(Manifest.permission.CAMERA,true);
    }

    @Override
    public void onAllow() {
        GoUtil.goActivityForResult(this, CaptureActivity.class, 100);
    }

    public static void goActivity(Context context, String shopId, String shopName) {
        Intent intent = new Intent(context, ShopDeviceActivity.class);
        intent.putExtra(TempConstants.SHOPID, shopId);
        intent.putExtra("SHOP_NAME", shopName);
        context.startActivity(intent);
    }

    @Override
    public void onShopDeviceDelite(final int position, String deviceId, String deviceType, String deviceCode) {
        mSrl.setRefreshing(true);
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.TaskID, TempConstants.DEFAULT_TASK_ID);
        param.put("DEVICEID", deviceId);
        param.put("DEVICETYPE", deviceType);
        param.put("DEVICECODE", deviceCode);
        param.put("OTHERTYPE", "1");
        param.put("OTHERID", mShopId);
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_SHOP, Constants.Common_RemoveDevice, param)
                .setBeanType(Common_RemoveDevice.class)
                .setCallBack(new WebServiceCallBack<Common_RemoveDevice>() {
                    @Override
                    public void onSuccess(Common_RemoveDevice bean) {
                        mSrl.setRefreshing(false);
                        mShopDeviceAdapter.deleteItem(position);
                        ToastUtil.showToast("设备解绑成功");
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        mSrl.setRefreshing(false);
                    }
                }).build().execute();
    }

    @Override
    public void onRefresh() {
        mSrl.setRefreshing(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            decodeDevice(data);

        }
    }

    private void decodeDevice(Intent data) {
        String result = data.getExtras().getString("result");
        result = result.substring(result.indexOf("?") + 1);
        String type = result.substring(0, 2);
        if ("AB".equals(type)) {
            result = result.substring(2);
            result = VerifyCode.checkDeviceCode(result);
            if (TextUtils.isEmpty(result)) {
                ToastUtil.showToast("可疑数据！");
                return;
            }
            long deviceType = Long.valueOf(result.substring(0, 4), 16);
            long deviceNO = Long.valueOf(result.substring(4), 16);
            if (!mShopDeviceTypeList.contains(deviceType+"")) {
                ToastUtil.showToast("未识别设备类型");
                Log.i(TAG, deviceType+"不是店铺设备类型");
                return;
            }
            Log.i(TAG, "设备类型: " + deviceType);
            Log.i(TAG, "设备编号: " + deviceNO);
            ShopDeviceBindActivity.goActivity(this,mShopId,mShopName,deviceNO+"",deviceType+"");
        } else {
            ToastUtil.showToast("不是要求的二维码对象");
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        doNet();
    }
}
