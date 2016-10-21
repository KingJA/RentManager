package com.kingja.cardpackage.activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kingja.cardpackage.db.DbDaoXutils3;
import com.kingja.cardpackage.entiy.Basic_Dictionary_Kj;
import com.kingja.cardpackage.entiy.ChuZuWu_ModifyRoom;
import com.kingja.cardpackage.entiy.ChuZuWu_RoomInfo;
import com.kingja.cardpackage.entiy.ErrorResult;
import com.kingja.cardpackage.net.ThreadPoolTask;
import com.kingja.cardpackage.net.WebServiceCallBack;
import com.kingja.cardpackage.util.Constants;
import com.kingja.cardpackage.util.DataManager;
import com.kingja.cardpackage.util.TempConstants;
import com.tdr.wisdome.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Description：TODO
 * Create Time：2016/8/16 16:17
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class RoomManagerActivity extends BackTitleActivity implements BackTitleActivity.OnRightClickListener, View.OnClickListener {

    private String mHouseId;
    private String mRoomId;
    private String mRoomNum;

    private RelativeLayout rlHouseType;
    private RelativeLayout rlHouseArea;
    private RelativeLayout rlHouseDecorate;
    private RelativeLayout rlHousePrice;
    private RelativeLayout rlHousePay;
    private RelativeLayout rlHousePersons;
    private RelativeLayout rlHouseConfig;
    private RelativeLayout rlHouseTitle;
    private RelativeLayout rlHousePs;

    private TextView tvHouseType;
    private TextView tvHouseArea;
    private TextView tvHouseDecorate;
    private TextView tvHousePrice;
    private TextView tvHousePay;
    private TextView tvHousePersons;
    private TextView tvHouseConfig;
    private CheckBox cbHouseAuto;
    private TextView tvHouseTitle;
    private TextView tvHousePs;
    private ChuZuWu_RoomInfo.ContentBean content;
    private int mPayCode;
    private int mDecorateCode;
    private String mSquare;
    private String mPrice;
    private String mTitle;
    private String mPersons;
    private String mShi;
    private String mTing;
    private String mWei;
    private String mYangtai;
    private String mConfig;
    private String mPs;


    @Override
    protected void initVariables() {
        mHouseId = getIntent().getStringExtra(TempConstants.HOUSEID);
        mRoomId = getIntent().getStringExtra(TempConstants.ROOMID);
        mRoomNum = getIntent().getStringExtra(TempConstants.ROOMNUM);
    }

    @Override
    protected void initContentView() {
        rlHouseType = (RelativeLayout) findViewById(R.id.rl_houseType);
        rlHouseArea = (RelativeLayout) findViewById(R.id.rl_houseArea);
        rlHouseDecorate = (RelativeLayout) findViewById(R.id.rl_houseDecorate);
        rlHousePay = (RelativeLayout) findViewById(R.id.rl_housePay);
        rlHousePersons = (RelativeLayout) findViewById(R.id.rl_housePersons);
        rlHouseConfig = (RelativeLayout) findViewById(R.id.rl_houseConfig);
        rlHouseTitle = (RelativeLayout) findViewById(R.id.rl_houseTitle);
        rlHousePs = (RelativeLayout) findViewById(R.id.rl_housePs);
        rlHousePrice = (RelativeLayout) findViewById(R.id.rl_housePrice);


        tvHouseType = (TextView) findViewById(R.id.tv_houseType);
        tvHouseArea = (TextView) findViewById(R.id.tv_houseArea);
        tvHouseDecorate = (TextView) findViewById(R.id.tv_houseDecorate);
        tvHousePrice = (TextView) findViewById(R.id.tv_housePrice);
        tvHousePay = (TextView) findViewById(R.id.tv_housePay);
        tvHousePersons = (TextView) findViewById(R.id.tv_housePersons);
        tvHouseConfig = (TextView) findViewById(R.id.tv_houseConfig);
        cbHouseAuto = (CheckBox) findViewById(R.id.cb_houseAuto);
        tvHouseTitle = (TextView) findViewById(R.id.tv_houseTitle);
        tvHousePs = (TextView) findViewById(R.id.tv_housePs);
    }

    @Override
    protected int getBackContentView() {
        return R.layout.activity_room_manager;
    }

    @Override
    protected void initNet() {
        setProgressDialog(true);
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.TaskID, "1");
        param.put(TempConstants.ROOMID, mRoomId);
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_RENT, Constants.ChuZuWu_RoomInfo, param)
                .setBeanType(ChuZuWu_RoomInfo.class)
                .setCallBack(new WebServiceCallBack<ChuZuWu_RoomInfo>() {
                    @Override
                    public void onSuccess(ChuZuWu_RoomInfo bean) {
                        setProgressDialog(false);
                        content = bean.getContent();
                        fillData(content);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build().execute();

    }

    private void fillData(ChuZuWu_RoomInfo.ContentBean content) {
        mPayCode = content.getDEPOSIT();
        mDecorateCode = content.getFIXTURE();
        mSquare = content.getSQUARE()+"";
        mPrice = content.getPRICE()+"";
        mTitle = content.getTITLE();
        mPersons = content.getGALLERYFUL()+"";
        mShi = content.getSHI()+"";
        mTing = content.getTING()+"";
        mWei = content.getWEI()+"";
        mYangtai = content.getYANGTAI()+"";
        Basic_Dictionary_Kj paymentBean =  (Basic_Dictionary_Kj) DbDaoXutils3.getInstance().sleectFirst(Basic_Dictionary_Kj.class, "COLUMNCODE", "DEPOSIT", "COLUMNVALUE", mPayCode + "");
        Basic_Dictionary_Kj decorateBean = (Basic_Dictionary_Kj) DbDaoXutils3.getInstance().sleectFirst(Basic_Dictionary_Kj.class, "COLUMNCODE", "FIXTURE", "COLUMNVALUE", mDecorateCode + "");
        tvHouseType.setText(mShi + "室" + mTing + "厅" + mWei + "卫" + mYangtai + "阳台");
        tvHouseArea.setText(mSquare + TempConstants.UNIT_SQUARE);
        tvHouseDecorate.setText(decorateBean.getCOLUMNCOMMENT());
        tvHousePay.setText(paymentBean.getCOLUMNCOMMENT());
        tvHousePrice.setText(mPrice + TempConstants.UNIT_MOUTH);
        tvHousePersons.setText(mPersons + TempConstants.UNIT_PERSON);
        cbHouseAuto.setChecked(content.getISAUTOPUBLISH() == 1);
        tvHouseConfig.setText("");
        tvHouseTitle.setText(mTitle);
        tvHousePs.setText("");
    }

    @Override
    protected void initData() {
        rlHouseType.setOnClickListener(this);
        rlHouseArea.setOnClickListener(this);
        rlHouseDecorate.setOnClickListener(this);
        rlHousePrice.setOnClickListener(this);
        rlHousePay.setOnClickListener(this);
        rlHousePersons.setOnClickListener(this);
        rlHouseConfig.setOnClickListener(this);
        rlHouseTitle.setOnClickListener(this);
        rlHousePs.setOnClickListener(this);


    }

    @Override
    protected void setData() {
        setTopColor(TopColor.WHITE);
        setOnRightClickListener(this, "保存");
        setTitle("房间" + mRoomNum);
    }



    public static void goActivity(Context context, String houseId,String roomId, String roomNum) {
        Intent intent = new Intent(context, RoomManagerActivity.class);
        intent.putExtra(TempConstants.HOUSEID, houseId);
        intent.putExtra(TempConstants.ROOMID, roomId);
        intent.putExtra(TempConstants.ROOMNUM, roomNum);
        context.startActivity(intent);
    }

    public static final int HOUSE_TYPE = 1;
    public static final int HOUSE_AREA = 2;
    public static final int HOUSE_DECORATE = 3;
    public static final int HOUSE_PRICE = 4;
    public static final int HOUSE_PAY = 5;
    public static final int HOUSE_PERSONS = 6;
    public static final int HOUSE_CONFIG = 7;
    public static final int HOUSE_TITLE = 8;
    public static final int HOUSE_PS = 9;

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rl_houseType://房型,室厅卫阳1
                EditRoomActivity.goActivity(this, mShi+"", mTing+"" , mWei+"", mYangtai+"", HOUSE_TYPE);
                break;
            case R.id.rl_houseArea://面积2

                EditNumberActivity.goActivity(this, "面积", mSquare + "", "平方米", HOUSE_AREA);
                break;
            case R.id.rl_houseDecorate://装修程度3
                EditGvActivity.goActivity(this, "装修程度", mDecorateCode + "", HOUSE_DECORATE);
                break;
            case R.id.rl_housePrice://房租4
                EditNumberActivity.goActivity(this, "房租", mPrice + "", "元/月", HOUSE_PRICE);
                break;
            case R.id.rl_housePay://支付方式5
                EditGvActivity.goActivity(this, "房租支付方式", mPayCode + "", HOUSE_PAY);
                break;
            case R.id.rl_housePersons://容纳人数6
                EditNumberActivity.goActivity(this, "容纳人数", mPersons + "", "人", HOUSE_PERSONS);
                break;
            case R.id.rl_houseConfig://房间配置7
                EditTextAreaActivity.goActivity(this, "房间配置", mConfig , HOUSE_CONFIG);
                break;
            case R.id.rl_houseTitle://发布标题8
                EditTextAreaActivity.goActivity(this, "发布标题", mTitle , HOUSE_TITLE);
                break;
            case R.id.rl_housePs://备注9
                EditTextAreaActivity.goActivity(this, "备注", mPs , HOUSE_PS);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult: ");
        if (resultCode == RESULT_OK && data != null) {
            Log.e(TAG, "RESULT_OK: ");
            switch (requestCode) {
                case 1:
                    mShi = data.getStringExtra(EditRoomActivity.SHI)+"";
                    mTing = data.getStringExtra(EditRoomActivity.TING)+"";
                    mWei = data.getStringExtra(EditRoomActivity.WEI)+"";
                    mYangtai =data.getStringExtra(EditRoomActivity.YANGTAI) +"";
                    tvHouseType.setText(mShi + "室"+ mTing + "厅"+mWei + "卫"+ mYangtai + "阳台");
                    break;
                case HOUSE_AREA:
                    mSquare=data.getStringExtra("VALUE");
                    tvHouseArea.setText(mSquare+ TempConstants.UNIT_SQUARE);
                    break;
                case 3:
                    mDecorateCode=Integer.valueOf(data.getStringExtra("CODE"));
                    tvHouseDecorate.setText(data.getStringExtra("VALUE"));
                    break;
                case 4:
                    mPrice=data.getStringExtra("VALUE");
                    tvHousePrice.setText(mPrice+ TempConstants.UNIT_MOUTH);
                    break;
                case 5:
                    mPayCode=Integer.valueOf(data.getStringExtra("CODE"));
                    tvHousePay.setText(data.getStringExtra("VALUE"));
                    break;
                case 6:
                    mPersons=data.getStringExtra("VALUE");
                    tvHousePersons.setText(mPersons+ TempConstants.UNIT_PERSON);
                    break;
                case 7:
                    mConfig=data.getStringExtra("VALUE");
                    tvHouseConfig.setText(mConfig);
                    break;
                case 8:
                    mTitle=data.getStringExtra("VALUE");
                    tvHouseTitle.setText(mTitle);
                    break;
                case 9:
                    mPs=data.getStringExtra("VALUE");
                    tvHousePs.setText(mPs);
                    break;

            }
        }
    }

    @Override
    public void onRightClick() {
        setProgressDialog(true);
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.TaskID, "1");
        param.put("HOUSEID", mHouseId);
        param.put("ROOMID", mRoomId);
        param.put("FIXTURE", mDecorateCode);
        param.put("SQUARE", mSquare);
        param.put("PRICE", mPrice);
        param.put("SHI", mShi);
        param.put("TING", mTing);
        param.put("WEI", mWei);
        param.put("YANGTAI", mYangtai);
        param.put("GALLERYFUL", mPersons);
        param.put("DEPOSIT", mPayCode);
        param.put("ISAUTOPUBLISH", cbHouseAuto.isChecked()?"1":"0");
        param.put("TITLE", mTitle);
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_RENT, Constants.ChuZuWu_ModifyRoom, param)
                .setBeanType(ChuZuWu_ModifyRoom.class)
                .setCallBack(new WebServiceCallBack<ChuZuWu_ModifyRoom>() {
                    @Override
                    public void onSuccess(ChuZuWu_ModifyRoom bean) {
                        setProgressDialog(false);
                        RoomManagerActivity.this.finish();
                    }
                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build().execute();

    }

    @Override
    protected void onClickBack() {
        showQuitDialog();
    }
}
