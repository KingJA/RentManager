package com.kingja.cardpackage.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kingja.cardpackage.entiy.RentBean;
import com.kingja.cardpackage.util.ToastUtil;
import com.tdr.wisdome.R;

/**
 * Description：TODO
 * Create Time：2016/8/5 16:34
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class DetailRentActivity extends BackTitleActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private TextView mTvHouseName;
    private TextView mTvAddress;
    private ImageView mIvSign;
    private ImageView mIvClose;
    private TextView mTvRead;
    private TextView mTvMsg;
    private CheckBox mCbHouse;
    private CheckBox mCbWarmTip;
    private TextView mTvCount;
    private RelativeLayout mRlMessage;
    private RelativeLayout mRlApply;
    private RelativeLayout mRlInfo;
    private RelativeLayout mRlPeople;
    private RelativeLayout mRlRoom;
    private RelativeLayout mRlFangdao;
    private RelativeLayout mRlDeviceInfo;
    private RentBean entiy;

    private final int PERSON_MANAGER = 0;
    private final int ROOM_MANAGER = 1;
    private RelativeLayout mRlAdmin;
    private int roomCount;

    @Override
    protected void initVariables() {
        entiy = (RentBean) getIntent().getSerializableExtra("ENTIY");
        roomCount = entiy.getRoomList().size();
    }

    @Override
    protected void initContentView() {
        mTvHouseName = (TextView) findViewById(R.id.tv_houseName);
        mTvAddress = (TextView) findViewById(R.id.tv_address);
        mIvSign = (ImageView) findViewById(R.id.iv_sign);
        mRlMessage = (RelativeLayout) findViewById(R.id.rl_message);
        mIvClose = (ImageView) findViewById(R.id.iv_close);
        mTvRead = (TextView) findViewById(R.id.tv_read);
        mTvMsg = (TextView) findViewById(R.id.tv_msg);
        mCbHouse = (CheckBox) findViewById(R.id.cb_house);
        mCbWarmTip = (CheckBox) findViewById(R.id.cb_warm_tip);
        mRlInfo = (RelativeLayout) findViewById(R.id.rl_info);
        mTvCount = (TextView) findViewById(R.id.tv_count);
        mRlFangdao = (RelativeLayout) findViewById(R.id.rl_fangdao);
        mRlDeviceInfo = (RelativeLayout) findViewById(R.id.rl_deviceInfo);
        mRlPeople = (RelativeLayout) findViewById(R.id.rl_people);
        mRlRoom = (RelativeLayout) findViewById(R.id.rl_room);
        mRlApply = (RelativeLayout) findViewById(R.id.rl_apply);
        mRlAdmin = (RelativeLayout) findViewById(R.id.rl_admin);
    }

    @Override
    protected int getBackContentView() {
        return R.layout.activity_rent;
    }

    @Override
    protected void initNet() {

    }

    @Override
    protected void initData() {
        mCbHouse.setOnCheckedChangeListener(this);
        mCbWarmTip.setOnCheckedChangeListener(this);
        mRlDeviceInfo.setOnClickListener(this);
        mRlFangdao.setOnClickListener(this);
        mIvSign.setOnClickListener(this);
        mTvRead.setOnClickListener(this);
        mRlInfo.setOnClickListener(this);
        mIvClose.setOnClickListener(this);
        mRlPeople.setOnClickListener(this);
        mRlApply.setOnClickListener(this);
        mRlRoom.setOnClickListener(this);
        mRlAdmin.setOnClickListener(this);
    }

    @Override
    protected void setData() {
        setTopColor(TopColor.BLUE);
        mTvHouseName.setText(entiy.getHOUSENAME());
        mTvAddress.setText(entiy.getADDRESS());
    }

    public static void goActivity(Context context, RentBean entiy) {
        Intent intent = new Intent(context, DetailRentActivity.class);
        intent.putExtra("ENTIY", entiy);
        context.startActivity(intent);
    }

    public static void goActivityInReceiver(Context context, RentBean entiy) {
        Intent intent = new Intent(context, DetailRentActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("ENTIY", entiy);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            //设备信息
            case R.id.rl_deviceInfo:
                if (checkRoomEmpty()) break;
                RentDeviceInfoActivity.goActivity(this, entiy);
                break;
            //居家防盗
            case R.id.rl_fangdao:
                ToastUtil.showToast("居家防盗");
                break;
            //签到
            case R.id.iv_sign:
                ToastUtil.showToast("签到");
                break;
            //点击查看
            case R.id.tv_read:
                ToastUtil.showToast("点击查看");
                break;
            //预警信息
            case R.id.rl_info:
                AlarmRentActivity.goActivity(this, entiy.getHOUSEID());
                break;
            //点击关闭
            case R.id.iv_close:
                ToastUtil.showToast("点击关闭");
                break;
            //人员管理
            case R.id.rl_people:
                if (checkRoomEmpty()) break;
                RoomListActivity.goActivity(this, entiy, PERSON_MANAGER);
                break;
            //房间管理
            case R.id.rl_room:
                if (checkRoomEmpty()) break;
                RoomListActivity.goActivity(this, entiy, ROOM_MANAGER);
                break;
            //人员申报
            case R.id.rl_apply:
                if (checkRoomEmpty()) break;
                PersonApplyActivity.goActivity(this, entiy);
                break;
            //管理员管理
            case R.id.rl_admin:
                RentAdminActivity.goActivity(this,entiy.getHOUSEID(),entiy.getHOUSENAME());
                break;
            default:
                break;

        }
    }

    private boolean checkRoomEmpty() {
        if (roomCount == 0) {
            ToastUtil.showToast("未查到房间信息");
            return true;
        }
        return false;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb_house:
                ToastUtil.showToast(isChecked + "房屋撤布防");
                break;
            case R.id.cb_warm_tip:
                ToastUtil.showToast(isChecked + "预警信息提示");
                break;
            default:
                break;

        }
    }

}
