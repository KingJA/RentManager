package com.kingja.cardpackage.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;
import com.google.zxing.WriterException;
import com.kingja.cardpackage.adapter.AdminTypeAdapter;
import com.kingja.cardpackage.db.DbDaoXutils3;
import com.kingja.cardpackage.entiy.Basic_Dictionary_Kj;
import com.kingja.cardpackage.entiy.ChuZuWu_AddAdmin;
import com.kingja.cardpackage.entiy.ErrorResult;
import com.kingja.cardpackage.net.ThreadPoolTask;
import com.kingja.cardpackage.net.WebServiceCallBack;
import com.kingja.cardpackage.util.Constants;
import com.kingja.cardpackage.util.DataManager;
import com.kingja.cardpackage.util.DialogUtil;
import com.kingja.cardpackage.util.JoinAdd;
import com.kingja.cardpackage.util.QRCodeUtil;
import com.kingja.cardpackage.util.TempConstants;
import com.tdr.wisdome.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description：店铺二维码
 * Create Time：2016/9/1 11:15
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class AdminQRCodeActivity extends BackTitleActivity implements View.OnClickListener,OnOperItemClickL{

    private String mHouseId;
    private String mHousepName;
    private TextView mTvHouseName;
    private ImageView mIvHouseCode;
    private LinearLayout mLlSelectAdminType;
    private int adminType=-1;
    private List<Basic_Dictionary_Kj> mAdminTypeList;
    private TextView mTvAdminType;
    private NormalListDialog mAdminTypeSelector;


    @Override
    protected void initVariables() {
        mHouseId = getIntent().getStringExtra(TempConstants.HOUSEID);
        mHousepName = getIntent().getStringExtra("HOUSE_NAME");
        mAdminTypeList = (List<Basic_Dictionary_Kj>) DbDaoXutils3.getInstance().selectAllWhere(Basic_Dictionary_Kj.class, "COLUMNCODE", "ADMINTYPE");
        Log.e(TAG, "mAdminTypeList: "+mAdminTypeList.size() );
    }

    @Override
    protected void initContentView() {
        mTvAdminType = (TextView) findViewById(R.id.tv_admin_type);
        mTvHouseName = (TextView) findViewById(R.id.tv_houseName);
        mIvHouseCode = (ImageView) findViewById(R.id.iv_houseCode);
        mLlSelectAdminType = (LinearLayout) findViewById(R.id.ll_selectAdminType);
        mAdminTypeSelector = DialogUtil.getListDialog(this, "管理员类型", new AdminTypeAdapter(this, mAdminTypeList));
        mAdminTypeSelector.setOnOperItemClickL(this);
    }

    @Override
    protected int getBackContentView() {
        return R.layout.activity_admin_qrcode;
    }

    @Override
    protected void initNet() {

    }

    private void makeAdminCode(int adminType) {
        setProgressDialog(true);
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.TaskID, TempConstants.DEFAULT_TASK_ID);
        param.put(TempConstants.HOUSEID, mHouseId);
        param.put("ADMINTYPE", adminType);
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
        mLlSelectAdminType.setOnClickListener(this);
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
  /*  ADMINTYPE  管理员类型(2001亲属，2002中介，2003二房东，2004警务人员，2其他)*/

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_selectAdminType:
                mAdminTypeSelector.show();
                break;
            default:
                break;

        }
    }

    @Override
    public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
        Basic_Dictionary_Kj bean = (Basic_Dictionary_Kj) parent.getItemAtPosition(position);
        mTvAdminType.setText(bean.getCOLUMNCOMMENT());
        adminType=Integer.valueOf(bean.getCOLUMNVALUE());
        mAdminTypeSelector.dismiss();
        makeAdminCode(adminType);
    }
}
