package com.kingja.cardpackage.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.kingja.cardpackage.adapter.RoomListAdapter;
import com.kingja.cardpackage.entiy.RentBean;
import com.kingja.cardpackage.util.AppUtil;
import com.tdr.wisdome.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Description：TODO
 * Create Time：2016/8/15 14:35
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class RoomListActivity extends BackTitleActivity implements AdapterView.OnItemClickListener ,SwipeRefreshLayout.OnRefreshListener{
    private RentBean entiy;
    private SwipeRefreshLayout mSrlTopContent;
    private ListView mLvTopContent;
    private List<RentBean.RoomListBean> roomList = new ArrayList<>();
    private RoomListAdapter mRoomListAdapter;
    private int mRequestCode;
    private LinearLayout mLlEmpty;


    @Override
    protected void initVariables() {
        entiy = (RentBean) getIntent().getSerializableExtra("ENTIY");
        mRequestCode = getIntent().getIntExtra("REQUEST_CODE", 0);
        roomList = entiy.getRoomList();

    }

    @Override
    protected void initContentView() {
        mLlEmpty = (LinearLayout) findViewById(R.id.ll_empty);
        mSrlTopContent = (SwipeRefreshLayout) findViewById(R.id.srl);
        mLvTopContent = (ListView) findViewById(R.id.lv);
        mLlEmpty.setVisibility(roomList.size()>0?View.GONE:View.VISIBLE);
        mRoomListAdapter = new RoomListAdapter(this, roomList);
        mLvTopContent.setAdapter(mRoomListAdapter);

        mSrlTopContent.setColorSchemeResources(R.color.bg_black);
        mSrlTopContent.setProgressViewOffset(false, 0, AppUtil.dp2px(24));
    }

    @Override
    protected int getBackContentView() {
        return R.layout.single_lv;
    }

    @Override
    protected void initNet() {

    }

    @Override
    protected void initData() {
        mLvTopContent.setOnItemClickListener(this);
        mSrlTopContent.setOnRefreshListener(this);
    }

    @Override
    protected void setData() {
        setTitle("房间号");
        setTopColor(TopColor.WHITE);
    }


    public static void goActivity(Context context, RentBean entiy, int requestCode) {
        Intent intent = new Intent(context, RoomListActivity.class);
        intent.putExtra("ENTIY", entiy);
        intent.putExtra("REQUEST_CODE", requestCode);
        context.startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RentBean.RoomListBean roomBean = (RentBean.RoomListBean) parent.getItemAtPosition(position);
        if (mRequestCode == 0) {
            //跳转人员管理界面
            PersonManagerActivity.goActivity(this, entiy.getHOUSEID(), roomBean.getROOMID(), roomBean.getROOMNO() + "");
        }else{
            //跳转房间管理界面
            RoomManagerActivity.goActivity(this, entiy.getHOUSEID(), roomBean.getROOMID(), roomBean.getROOMNO() + "");
        }
    }

    @Override
    public void onRefresh() {
        mSrlTopContent.setRefreshing(false);
    }
}
