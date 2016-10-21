package com.kingja.cardpackage.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.kingja.cardpackage.adapter.DeviceInfoAdapter;
import com.kingja.cardpackage.entiy.ChuZuWu_DeviceLists;
import com.kingja.cardpackage.entiy.ErrorResult;
import com.kingja.cardpackage.net.ThreadPoolTask;
import com.kingja.cardpackage.net.WebServiceCallBack;
import com.kingja.cardpackage.util.AppUtil;
import com.kingja.cardpackage.util.Constants;
import com.kingja.cardpackage.util.DataManager;
import com.kingja.cardpackage.util.TempConstants;
import com.tdr.wisdome.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description：TODO
 * Create Time：2016/8/23 9:25
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class HouseDeviceInfoActivity extends BackTitleActivity implements SwipeRefreshLayout.OnRefreshListener{
    private SwipeRefreshLayout mSrlTopContent;
    private ListView mLvTopContent;
    private List<ChuZuWu_DeviceLists.ContentBean> mDeviceList=new ArrayList<>();
    private DeviceInfoAdapter mDeviceInfoAdapter;
    private String mRoomId;
    private LinearLayout mLlEmpty;


    @Override
    protected void initVariables() {
        mRoomId = getIntent().getStringExtra(TempConstants.ROOMID);
    }

    @Override
    protected void initContentView() {
        mLlEmpty = (LinearLayout) findViewById(R.id.ll_empty);
        mSrlTopContent = (SwipeRefreshLayout) findViewById(R.id.srl);
        mLvTopContent = (ListView) findViewById(R.id.lv);

        mDeviceInfoAdapter = new DeviceInfoAdapter(this, mDeviceList);
        mLvTopContent.setAdapter(mDeviceInfoAdapter);

        mSrlTopContent.setColorSchemeResources(R.color.bg_black);
        mSrlTopContent.setProgressViewOffset(false, 0, AppUtil.dp2px(24));
    }

    @Override
    protected int getBackContentView() {
        return R.layout.single_lv;
    }

    @Override
    protected void initNet() {
        mSrlTopContent.setRefreshing(true);
        Map<String, Object> param = new HashMap<>();
        param.put("TaskID", "1");
        param.put("RoomID", mRoomId);
        param.put("PageSize", 20);
        param.put("PageIndex", 0);

        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_HOUSE, Constants.ChuZuWu_DeviceLists, param)
                .setBeanType(ChuZuWu_DeviceLists.class)
                .setCallBack(new WebServiceCallBack<ChuZuWu_DeviceLists>() {
                    @Override
                    public void onSuccess(ChuZuWu_DeviceLists bean) {
                        mSrlTopContent.setRefreshing(false);
                        mDeviceList = bean.getContent();
                        mDeviceInfoAdapter.setData(mDeviceList);
                        mLlEmpty.setVisibility(mDeviceList.size()>0? View.GONE:View.VISIBLE);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        mSrlTopContent.setRefreshing(false);
                    }
                }).build().execute();
    }

    @Override
    protected void initData() {
        mSrlTopContent.setOnRefreshListener(this);
    }

    @Override
    protected void setData() {
        setTitle("设备信息");
    }

    @Override
    public void onRefresh() {
        mSrlTopContent.setRefreshing(false);
    }

    public static void goActivity(Activity activity, String roomId) {
        Intent intent = new Intent(activity, HouseDeviceInfoActivity.class);
        intent.putExtra(TempConstants.ROOMID, roomId);
        activity.startActivity(intent);

    }
}
