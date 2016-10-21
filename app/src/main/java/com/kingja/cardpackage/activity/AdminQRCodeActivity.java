package com.kingja.cardpackage.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.kingja.cardpackage.entiy.ChuZuWu_AddAdmin;
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
 * Description：店铺二维码
 * Create Time：2016/9/1 11:15
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class AdminQRCodeActivity extends BackTitleActivity {

    private String mHouseId;
    private String mHousepName;
    private TextView mTvHouseName;
    private ImageView mIvHouseCode;


    @Override
    protected void initVariables() {
        mHouseId = getIntent().getStringExtra(TempConstants.HOUSEID);
        mHousepName = getIntent().getStringExtra("HOUSE_NAME");
    }

    @Override
    protected void initContentView() {
        mTvHouseName = (TextView) findViewById(R.id.tv_houseName);
        mIvHouseCode = (ImageView) findViewById(R.id.iv_houseCode);
    }

    @Override
    protected int getBackContentView() {
        return R.layout.activity_admin_qrcode;
    }

    @Override
    protected void initNet() {
        setProgressDialog(true);
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.TaskID, TempConstants.DEFAULT_TASK_ID);
        param.put(TempConstants.HOUSEID, mHouseId);
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_RENT, Constants.ChuZuWu_AddAdmin, param)
                .setBeanType(ChuZuWu_AddAdmin.class)
                .setCallBack(new WebServiceCallBack<ChuZuWu_AddAdmin>() {
                    @Override
                    public void onSuccess(ChuZuWu_AddAdmin bean) {
                        setProgressDialog(false);
                        String code = "http://v.iotone.cn/?a1" + JoinAdd.base64("02" + "02" + bean.getContent().getKEY());
                        try {
                            Bitmap bitmap = QRCodeUtil.createQRCode(code);
                            mIvHouseCode.setImageBitmap(bitmap);
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
        mTvHouseName.setText(mHousepName);
    }

    @Override
    protected void setData() {
        setTitle("请扫二维码");
    }

    public static void goActivity(Context context, String houseId, String houseName) {
        Intent intent = new Intent(context, AdminQRCodeActivity.class);
        intent.putExtra(TempConstants.HOUSEID, houseId);
        intent.putExtra("HOUSE_NAME", houseName);
        context.startActivity(intent);
    }
}
