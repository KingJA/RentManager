package com.kingja.cardpackage.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kingja.cardpackage.adapter.SelectPicAdapter;
import com.kingja.cardpackage.entiy.Common_AddDevice;
import com.kingja.cardpackage.entiy.Common_AddDevice_Param;
import com.kingja.cardpackage.entiy.ErrorResult;
import com.kingja.cardpackage.net.PoolManager;
import com.kingja.cardpackage.net.ThreadPoolTask;
import com.kingja.cardpackage.net.WebServiceCallBack;
import com.kingja.cardpackage.util.CheckUtil;
import com.kingja.cardpackage.util.Constants;
import com.kingja.cardpackage.util.DataManager;
import com.kingja.cardpackage.util.DeviceTypeUtil;
import com.kingja.cardpackage.util.StringUtil;
import com.kingja.cardpackage.util.TempConstants;
import com.kingja.cardpackage.util.ToastUtil;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.ImageUtil;
import com.pizidea.imagepicker.bean.ImageItem;
import com.tdr.wisdome.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Description：TODO
 * Create Time：2016/9/1 15:31
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ShopDeviceBindActivity extends BackTitleActivity implements BackTitleActivity.OnRightClickListener,SelectPicAdapter.OnAddPicListener {

    private String mShopId;
    private String mShopName;
    private String mDeviceCode;
    private String mDeviceType;
    private TextView mTvShopName;
    private TextView mTvDeviceCode;
    private TextView mTvDeviceType;
    private LinearLayout mLlDeviceName;
    private TextView mTvDeviceName;
    private String mDevicepName;
    private GridView mGvBindDevice;
    private List<ImageItem> itemList=new ArrayList<>();
    private SelectPicAdapter mSelectPicAdapter;
    private  List<Common_AddDevice_Param.PHOTOLISTBean> mPhotolist=new ArrayList<>();

    @Override
    protected void initVariables() {
        mShopId = getIntent().getStringExtra(TempConstants.SHOPID);
        mShopName = getIntent().getStringExtra("SHOP_NAME");
        mDeviceCode = getIntent().getStringExtra("DEVICE_CODE");
        mDeviceType = getIntent().getStringExtra("DEVICE_TYPE");
    }

    @Override
    protected void initContentView() {
        mTvShopName = (TextView) findViewById(R.id.tv_houseName);
        mTvDeviceCode = (TextView) findViewById(R.id.tv_deviceCode);
        mTvDeviceType = (TextView) findViewById(R.id.tv_deviceType);
        mLlDeviceName = (LinearLayout) findViewById(R.id.ll_deviceName);
        mTvDeviceName = (TextView) findViewById(R.id.tv_deviceName);
        mGvBindDevice = (GridView) findViewById(R.id.gv_bindDevice);
        mSelectPicAdapter = new SelectPicAdapter(this, itemList);
        mSelectPicAdapter.setOnAddPicListener(this);
        mGvBindDevice.setAdapter(mSelectPicAdapter);
    }

    @Override
    protected int getBackContentView() {
        return R.layout.activity_shop_device_bind;
    }

    @Override
    protected void initNet() {

    }

    @Override
    protected void initData() {
        mZeusManager.checkPermission(Manifest.permission.CAMERA,false);
        setOnRightClickListener(this, "完成");
        mLlDeviceName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDevicepName = mTvDeviceName.getText().toString().trim();
                EditTextActivity.goActivity(ShopDeviceBindActivity.this, "设备点位", mDevicepName, "", 100);
            }
        });

    }

    @Override
    protected void setData() {
        setTitle("绑定设备");
        mTvShopName.setText(mShopName);
        mTvDeviceCode.setText(mDeviceCode);
        mTvDeviceType.setText(DeviceTypeUtil.getColumComment(TempConstants.DEVICETYPE,mDeviceType));
    }

    /**
     * 设备绑定
     */
    @Override
    public void onRightClick() {
        mDevicepName = mTvDeviceName.getText().toString().trim();
        if (!CheckUtil.checkEmpty(mDevicepName, "请输入设备点位")) {
            return;
        }
        if (mPhotolist.size() == 0) {
            ToastUtil.showToast("请添加设备图片");
            return;
        }
        setProgressDialog(true);
        Common_AddDevice_Param param = new Common_AddDevice_Param();
        param.setTaskID(TempConstants.DEFAULT_TASK_ID);
        param.setDEVICEID(StringUtil.getUUID());
        param.setDEVICECODE(Long.parseLong(mDeviceCode));
        param.setDEVICENAME(mDevicepName);
        param.setDEVICETYPE(Long.parseLong(mDeviceType));
        param.setOTHERTYPE(TempConstants.OTHERTYPE_SHOP);
        param.setOTHERID(mShopId);
        param.setPHOTOCOUNT(mPhotolist.size());
        param.setPHOTOLIST(mPhotolist);
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_SHOP, Constants.Common_AddDevice, param)
                .setBeanType(Common_AddDevice.class)
                .setCallBack(new WebServiceCallBack<Common_AddDevice>() {
                    @Override
                    public void onSuccess(Common_AddDevice bean) {
                        setProgressDialog(false);
                        ToastUtil.showToast("设备添加成功");
                        finish();
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build().execute();
    }

    public static void goActivity(Context context, String shopId, String shopName, String deviceCode, String deviceType) {
        Intent intent = new Intent(context, ShopDeviceBindActivity.class);
        intent.putExtra(TempConstants.SHOPID, shopId);
        intent.putExtra("SHOP_NAME", shopName);
        intent.putExtra("DEVICE_CODE", deviceCode);
        intent.putExtra("DEVICE_TYPE", deviceType);
        context.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            mDevicepName = data.getStringExtra("VALUE");
            mTvDeviceName.setText(mDevicepName);
        }

    }

    @Override
    public void onAddPic() {
        AndroidImagePicker.getInstance().pickMulti(this, AndroidImagePicker.MAX_PIC_NUM-itemList.size(), true, new AndroidImagePicker.OnImagePickCompleteListener() {
            @Override
            public void onImagePickComplete(List<ImageItem> items) {
                itemList.addAll(items);
                mSelectPicAdapter.setRefresh(itemList);
                getPicData();
            }
        });
    }

    private void getPicData() {
        List<ImageItem> mSelectItems =  mSelectPicAdapter.getItems();
        mPhotolist = new ArrayList<>();

        for (final ImageItem item :mSelectItems ) {
            PoolManager.getInstance().execute(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = ImageUtil.compressScaleFromF2B(item.path);
                    boolean raphael = ImageUtil.saveBitmap2file(ShopDeviceBindActivity.this,bitmap, "CardPackage", StringUtil.getUUID());
                    Log.e(TAG, "raphael: "+raphael);
                    String base64 = ImageUtil.bitmapToBase64(bitmap);
                    Common_AddDevice_Param.PHOTOLISTBean photolistBean = new Common_AddDevice_Param.PHOTOLISTBean();
                    photolistBean.setIMAGE(base64);
                    photolistBean.setLISTID(StringUtil.getUUID());
                    photolistBean.setTAG("TAG");
                    mPhotolist.add(photolistBean);
                }
            });
        }
    }
}
