package com.kingja.cardpackage.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.kingja.cardpackage.adapter.DeviceInfoAdapter;
import com.kingja.cardpackage.adapter.DeviceRoomAdapter;
import com.kingja.cardpackage.entiy.ChuZuWu_DeviceLists;
import com.kingja.cardpackage.entiy.ErrorResult;
import com.kingja.cardpackage.entiy.RentBean;
import com.kingja.cardpackage.net.ThreadPoolTask;
import com.kingja.cardpackage.net.WebServiceCallBack;
import com.kingja.cardpackage.util.AppUtil;
import com.kingja.cardpackage.util.Constants;
import com.kingja.cardpackage.util.DataManager;
import com.kingja.cardpackage.util.ToastUtil;
import com.tdr.wisdome.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description：设备信息
 * Create Time：2016/8/18 14:35
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class RentDeviceInfoActivity extends BackTitleActivity implements AdapterView.OnItemClickListener, DeviceRoomAdapter.OnExplandListener {
    private RentBean entiy;
    //    private SwipeRefreshLayout mSrlTopContent;
    private ListView mLvTopContent;
    private List<RentBean.RoomListBean> roomList = new ArrayList<>();
    private DeviceRoomAdapter mDeviceRoomAdapter;
    private List<ChuZuWu_DeviceLists.ContentBean> mDeviceList;
    private DeviceInfoAdapter mDeviceInfoAdapter;
    private LinearLayout ll_root;
    private LinearLayout ll_empty;


    @Override
    protected void initVariables() {
        entiy = (RentBean) getIntent().getSerializableExtra("ENTIY");
        roomList = entiy.getRoomList();
    }

    @Override
    protected void initContentView() {
        ll_root = (LinearLayout) findViewById(R.id.ll_root);
//        mSrlTopContent = (SwipeRefreshLayout) findViewById(R.id.srl);
        mLvTopContent = (ListView) findViewById(R.id.lv);
        ll_empty = (LinearLayout) findViewById(R.id.ll_empty);

        mDeviceRoomAdapter = new DeviceRoomAdapter(this, roomList);

        ll_empty.setVisibility(roomList.size()>0?View.GONE:View.VISIBLE);
        mLvTopContent.setAdapter(mDeviceRoomAdapter);
        mDeviceRoomAdapter.setOnExplandListener(this);

//        mSrlTopContent.setColorSchemeResources(R.color.bg_black);
//        mSrlTopContent.setProgressViewOffset(false, 0, AppUtil.dp2px(24));
    }

    @Override
    protected int getBackContentView() {
        return R.layout.lv;
    }

    @Override
    protected void initNet() {

    }

    @Override
    protected void initData() {
        mLvTopContent.setOnItemClickListener(this);

    }

    @Override
    protected void setData() {
        setTitle("设备信息");
        setTopColor(TopColor.WHITE);
    }


    public static void goActivity(Context context, RentBean entiy) {
        Intent intent = new Intent(context, RentDeviceInfoActivity.class);
        intent.putExtra("ENTIY", entiy);
        context.startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Log.e(TAG, "onItemClick: ");
        ListView lv_device = (ListView) view.findViewById(R.id.lv_device);
        Log.e(TAG, "lv_device: "+lv_device.getVisibility());
        ImageView ivdevicearrow = (ImageView) view.findViewById(R.id.iv_device_arrow);
        RentBean.RoomListBean bean = (RentBean.RoomListBean) parent.getItemAtPosition(position);
        if (bean.isExpland()) {
            Log.e(TAG, "if: ");
            mDeviceRoomAdapter.setVisibility(false, position);
        } else {
            if (mDeviceRoomAdapter.getAdapter(position) != null) {
                mDeviceRoomAdapter.setVisibility(!bean.isExpland(), position);
                Log.e(TAG, "else if: ");
                return;
            }
            onExpland(bean.getROOMID() + "", bean.getROOMNO() + "", lv_device, ivdevicearrow, position, false);
            Log.e(TAG, "else: ");
        }

    }

    @Override
    public void onExpland(final String roomid, final String roomno, final ListView lv, ImageView iv, final int position, final boolean expland) {
        setProgressDialog(true);
        Map<String, Object> param = new HashMap<>();
        param.put("TaskID", "1");
        param.put("RoomID", roomid);
        param.put("PageSize", 20);
        param.put("PageIndex", 0);

        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_RENT, Constants.ChuZuWu_DeviceLists, param)
                .setBeanType(ChuZuWu_DeviceLists.class)
                .setCallBack(new WebServiceCallBack<ChuZuWu_DeviceLists>() {
                    @Override
                    public void onSuccess(ChuZuWu_DeviceLists bean) {
                        setProgressDialog(false);
                        mDeviceList = bean.getContent();

                        if (mDeviceList.size() == 0) {
                            ToastUtil.showToast(roomno + "房间没有设备");
                            return;
                        }
                        mDeviceInfoAdapter = new DeviceInfoAdapter(RentDeviceInfoActivity.this, mDeviceList);
                        mDeviceRoomAdapter.saveAdapter(position, mDeviceInfoAdapter);
//                        lv.setAdapter(mDeviceInfoAdapter);
                        Log.e(TAG, "lv: " + lv.hashCode());
                        mDeviceRoomAdapter.setVisibility(true, position);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build().execute();
    }


//    @Override
//    public void onRefresh() {
////        mSrlTopContent.setRefreshing(false);
//    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "onTouchEvent: ");
        return super.onTouchEvent(event);
    }
}
