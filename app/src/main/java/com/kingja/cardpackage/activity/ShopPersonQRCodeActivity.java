package com.kingja.cardpackage.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.kingja.cardpackage.entiy.ErrorResult;
import com.kingja.cardpackage.entiy.ShangPu_AddEmployee;
import com.kingja.cardpackage.net.ThreadPoolTask;
import com.kingja.cardpackage.net.WebServiceCallBack;
import com.kingja.cardpackage.util.Constants;
import com.kingja.cardpackage.util.DataManager;
import com.kingja.cardpackage.util.JoinAdd;
import com.kingja.cardpackage.util.QRCodeUtil;
import com.kingja.cardpackage.util.TempConstants;
import com.tdr.wisdome.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Description：加入店铺二维码
 * Create Time：2016/9/1 11:15
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ShopPersonQRCodeActivity extends BackTitleActivity {

    private String mShopId;
    private String mShopName;
    private TextView mTvShopName;
    private ImageView mIvShopCode;


    @Override
    protected void initVariables() {
        mShopId = getIntent().getStringExtra(TempConstants.SHOPID);
        mShopName = getIntent().getStringExtra("SHOP_NAME");
    }

    @Override
    protected void initContentView() {
        mTvShopName = (TextView) findViewById(R.id.tv_houseName);
        mIvShopCode = (ImageView) findViewById(R.id.iv_houseCode);
    }

    @Override
    protected int getBackContentView() {
        return R.layout.activity_shop_qrcode;
    }

    @Override
    protected void initNet() {
        setProgressDialog(true);
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.TaskID, TempConstants.DEFAULT_TASK_ID);
        param.put(TempConstants.SHOPID, mShopId);
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_SHOP, Constants.ShangPu_AddEmployee, param)
                .setBeanType(ShangPu_AddEmployee.class)
                .setCallBack(new WebServiceCallBack<ShangPu_AddEmployee>() {
                    @Override
                    public void onSuccess(ShangPu_AddEmployee bean) {
                        setProgressDialog(false);
                        String code = "http://v.iotone.cn/?a1" + JoinAdd.base64("01" + "01" + bean.getContent().getKEY());
                        try {
                            Bitmap bitmap = QRCodeUtil.createQRCode(code);
                            mIvShopCode.setImageBitmap(bitmap);
                        } catch (WriterException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build().execute();
    }

    @Override
    protected void initData() {
        mTvShopName.setText(mShopName);
    }

    @Override
    protected void setData() {
        setTitle("请扫二维码");
    }

    public static void goActivity(Context context, String shopId, String shopName) {
        Intent intent = new Intent(context, ShopPersonQRCodeActivity.class);
        intent.putExtra(TempConstants.SHOPID, shopId);
        intent.putExtra("SHOP_NAME", shopName);
        context.startActivity(intent);
    }
}
