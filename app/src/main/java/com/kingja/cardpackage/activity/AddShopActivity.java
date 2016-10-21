package com.kingja.cardpackage.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kingja.cardpackage.Event.ShopRefreshEvent;
import com.kingja.cardpackage.adapter.SelectPicAdapter;
import com.kingja.cardpackage.db.DbDaoXutils3;
import com.kingja.cardpackage.entiy.Basic_JuWeiHui_Kj;
import com.kingja.cardpackage.entiy.Basic_PaiChuSuoOfStandardAddress;
import com.kingja.cardpackage.entiy.Basic_PaiChuSuo_Kj;
import com.kingja.cardpackage.entiy.Basic_StandardAddressCodeByKey;
import com.kingja.cardpackage.entiy.ChuZuWu_GetSSYByStandAddressCode;
import com.kingja.cardpackage.entiy.ErrorResult;
import com.kingja.cardpackage.entiy.ShangPu_Add;
import com.kingja.cardpackage.entiy.ShangPu_Add_Param;
import com.kingja.cardpackage.net.PoolManager;
import com.kingja.cardpackage.net.ThreadPoolTask;
import com.kingja.cardpackage.net.WebServiceCallBack;
import com.kingja.cardpackage.ui.DialogAddress;
import com.kingja.cardpackage.util.CheckUtil;
import com.kingja.cardpackage.util.Constants;
import com.kingja.cardpackage.util.DataManager;
import com.kingja.cardpackage.util.StringUtil;
import com.kingja.cardpackage.util.TempConstants;
import com.kingja.cardpackage.util.ToastUtil;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.ImageUtil;
import com.pizidea.imagepicker.bean.ImageItem;
import com.tdr.wisdome.R;
import com.tdr.wisdome.amap.LocationTask;
import com.tdr.wisdome.amap.OnLocationGetListener;
import com.tdr.wisdome.amap.PositionEntity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description：TODO
 * Create Time：2016/8/30 15:29
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class AddShopActivity extends BackTitleActivity implements BackTitleActivity.OnRightClickListener, OnLocationGetListener, DialogAddress.OnSearchListener, SelectPicAdapter.OnAddPicListener {
    private EditText mEtAddShopShopName;
    private LinearLayout mLlAddShopShopType;
    private TextView mTvAddShopShopType;
    private LinearLayout mLlAddShopShopAddress;
    private TextView mTvAddShopShopAddress;
    private TextView mTvAddShopName;
    private TextView mTvAddShopIdcard;
    private TextView mTvAddShopPhone;
    private TextView mTvAddShopLng;
    private TextView mTvAddShopLat;
    private LocationTask mLocationTask;

    private double mLNG = -1;
    private double mLAT = -1;
    private String mShopType;
    private int mShopTypeCode = -1;
    private DialogAddress mDialogAddress;
    private String mJWHCODE;
    private Basic_JuWeiHui_Kj mJuWeiHui;
    private Basic_PaiChuSuo_Kj mPaiChuSuo;
    private String mGeocode;
    private GridView mGvAddShop;

    private List<ImageItem> itemList = new ArrayList<>();
    private SelectPicAdapter selectPicAdapter;
    private List<ShangPu_Add_Param.PHOTOLISTBean> photolist = new ArrayList<>();
    private String xqCode;
    private String policeCode;

    @Override
    protected void initVariables() {
    }

    @Override
    protected void initContentView() {
        mEtAddShopShopName = (EditText) findViewById(R.id.et_addShop_shopName);
        mLlAddShopShopType = (LinearLayout) findViewById(R.id.ll_addShop_shopType);
        mLlAddShopShopAddress = (LinearLayout) findViewById(R.id.ll_addShop_shopAddress);

        mTvAddShopShopType = (TextView) findViewById(R.id.tv_addShop_shopType);
        mTvAddShopShopAddress = (TextView) findViewById(R.id.tv_addShop_shopAddress);
        mTvAddShopName = (TextView) findViewById(R.id.tv_addShop_name);
        mTvAddShopIdcard = (TextView) findViewById(R.id.tv_addShop_idcard);
        mTvAddShopPhone = (TextView) findViewById(R.id.tv_addShop_phone);
        mTvAddShopLng = (TextView) findViewById(R.id.tv_addShop_lng);
        mTvAddShopLat = (TextView) findViewById(R.id.tv_addShop_lat);
        mGvAddShop = (GridView) findViewById(R.id.gv_addShop);
        mDialogAddress = new DialogAddress(this);

        selectPicAdapter = new SelectPicAdapter(this, itemList);
        selectPicAdapter.setOnAddPicListener(this);
        mGvAddShop.setAdapter(selectPicAdapter);
    }

    @Override
    protected int getBackContentView() {
        return R.layout.activity_add_shop;
    }

    @Override
    protected void initNet() {

    }

    @Override
    protected void initData() {
        mZeusManager.checkPermission(Manifest.permission.CAMERA, false);
        setOnRightClickListener(this, "完成");
        mLlAddShopShopType.setOnClickListener(this);
        mLlAddShopShopAddress.setOnClickListener(this);
        mLocationTask = LocationTask.getInstance(getApplicationContext());
        mLocationTask.setOnLocationGetListener(this);
        mLocationTask.startSingleLocate();
        mDialogAddress.setOnSearchListener(this);
    }

    @Override
    protected void setData() {
        setTitle("添加店铺");
        mTvAddShopName.setText(DataManager.getRealName());
        mTvAddShopIdcard.setText(DataManager.getIdCard());
        mTvAddShopPhone.setText(DataManager.getUserPhone());
    }

    @Override
    public void onRightClick() {
        String mShopName = mEtAddShopShopName.getText().toString().trim();
        String mShopAddress = mTvAddShopShopAddress.getText().toString().trim();

        if (CheckUtil.checkEmpty(mShopName, "请输入店铺名称")
                && CheckUtil.checkEmpty(mShopAddress, "请输入店铺地址")
                && CheckUtil.checkIntDefalut(mShopTypeCode, "请选择店铺类型")
                && CheckUtil.checkZero(mLNG, "未获取经纬度信息")
                && CheckUtil.checkZero(mLAT, "未获取经纬度信息")) {
            doAddShop(mShopName, mShopAddress);
        }
    }

    /**
     * 添加店铺
     *
     * @param mShopName
     * @param mShopAddress
     */

    private void doAddShop(String mShopName, String mShopAddress) {
        setProgressDialog(true);
        ShangPu_Add_Param shopParam = new ShangPu_Add_Param();
        shopParam.setTaskID(TempConstants.DEFAULT_TASK_ID);
        shopParam.setSHOPID(StringUtil.getUUID());
        shopParam.setSHOPTYPE(mShopTypeCode);
        shopParam.setSHOPNAME(mShopName);
        shopParam.setSTANDARDADDRCODE(mGeocode);
        shopParam.setADDRESS(mShopAddress);
        shopParam.setIDENTITYCARD(DataManager.getIdCard());
        shopParam.setOWNERNAME(DataManager.getUserName());
        shopParam.setOWNERNEWUSERID(StringUtil.getUUID());
        shopParam.setPHONE(DataManager.getUserPhone());
        shopParam.setPCSCODE(policeCode);
        shopParam.setJWHCODE("");
        shopParam.setXQCODE(xqCode);
        shopParam.setLNG(mLNG);
        shopParam.setLAT(mLAT);

        shopParam.setPHOTOCOUNT(photolist.size());
        shopParam.setPHOTOLIST(photolist);
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_SHOP, Constants.ShangPu_Add, shopParam)
                .setBeanType(ShangPu_Add.class)
                .setCallBack(new WebServiceCallBack<ShangPu_Add>() {
                    @Override
                    public void onSuccess(ShangPu_Add bean) {
                        EventBus.getDefault().post(new ShopRefreshEvent());
                        setProgressDialog(false);
                        ToastUtil.showToast("店铺添加成功");
                        finish();
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build().execute();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_addShop_shopType:
                EditLvActivity.goActivity(this, "SHOPTYPE", mShopTypeCode + "", 100);
                break;
            case R.id.ll_addShop_shopAddress:
                mDialogAddress.showAndReset();
                break;

            default:
                break;
        }
    }

    @Override
    public void onLocationGet(PositionEntity entity) {
        mLNG = entity.longitude;
        mLAT = entity.latitue;
        mTvAddShopLng.setText(mLNG + "");
        mTvAddShopLat.setText(mLAT + "");

    }

    @Override
    public void onRegecodeGet(PositionEntity entity) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            mShopType = data.getStringExtra("COMUMNMENT");
            mShopTypeCode = Integer.valueOf(data.getStringExtra("COLUMNVALUE"));
            mTvAddShopShopType.setText(mShopType);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationTask.onDestroy();
    }

    @Override
    public void onConfirm(Basic_StandardAddressCodeByKey.ContentBean bean) {
        setProgressDialog(true);
        mGeocode = bean.getGeocode();
        mTvAddShopShopAddress.setText(bean.getAddress().substring(6));

        Map mParam = new HashMap<>();
        mParam.put(TempConstants.TaskID, TempConstants.DEFAULT_TASK_ID);
        mParam.put("geocode", bean.getId());

        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_SHOP, Constants.Basic_PaiChuSuoOfStandardAddress, mParam)
                .setBeanType(Basic_PaiChuSuoOfStandardAddress.class)
                .setCallBack(new WebServiceCallBack<Basic_PaiChuSuoOfStandardAddress>() {
                    @Override
                    public void onSuccess(Basic_PaiChuSuoOfStandardAddress bean
                    ) {
                        setProgressDialog(false);
                        xqCode = bean.getContent().getSszdyjxzqh().substring(0, 6);
                        policeCode = bean.getContent().getSszdyjgazzjg();
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build().execute();
    }

    @Override
    public void onAddPic() {
        AndroidImagePicker.getInstance().pickMulti(this, AndroidImagePicker.MAX_PIC_NUM - itemList.size(), true, new AndroidImagePicker.OnImagePickCompleteListener() {
            @Override
            public void onImagePickComplete(List<ImageItem> items) {
                itemList.addAll(items);
                selectPicAdapter.setRefresh(itemList);
                getPicData();
            }
        });
    }

    private void getPicData() {
        List<ImageItem> mSelectItems = selectPicAdapter.getItems();
        photolist = new ArrayList<>();

        for (final ImageItem item : mSelectItems) {
            PoolManager.getInstance().execute(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = ImageUtil.compressScaleFromF2B(item.path);
                    boolean raphael = ImageUtil.saveBitmap2file(AddShopActivity.this, bitmap, "CardPackage", StringUtil.getUUID());
                    Log.e(TAG, "raphael: " + raphael);
                    String base64 = ImageUtil.bitmapToBase64(bitmap);
                    ShangPu_Add_Param.PHOTOLISTBean photolistBean = new ShangPu_Add_Param.PHOTOLISTBean();
                    photolistBean.setIMAGE(base64);
                    photolistBean.setLISTID(StringUtil.getUUID());
                    photolistBean.setTAG("TAG");
                    photolist.add(photolistBean);
                }
            });
        }
    }
}
