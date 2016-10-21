package com.kingja.cardpackage.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.kingja.cardpackage.adapter.DividerItemDecoration;
import com.kingja.cardpackage.adapter.PersonManagerRvAdapter;
import com.kingja.cardpackage.entiy.ChuZuWu_MenPaiAuthorizationList;
import com.kingja.cardpackage.entiy.ErrorResult;
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
 * Description：TODO
 * Create Time：2016/8/15 16:18
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class PersonManagerActivity2 extends BackTitleActivity implements SwipeRefreshLayout.OnRefreshListener,PersonManagerRvAdapter.OnDeliteItemListener{
    public static String HOUSE_ID="HOUSEID";
    public static String ROOM_ID="ROOMID";
    public static String ROOM_NUM="ROOMNUM";
    private String mHouseId;
    private String mRoomId;
    private String mRoomNum;

    private List<ChuZuWu_MenPaiAuthorizationList.ContentBean.PERSONNELINFOLISTBean> personList=new ArrayList<>();
    private PersonManagerRvAdapter mPersonManagerAdapter;
    private LinearLayout mLlEmpty;
    private SwipeRefreshLayout mSrl;
    private RecyclerView mRv;


    @Override
    protected void initVariables() {
        mHouseId = getIntent().getStringExtra(HOUSE_ID);
        mRoomId = getIntent().getStringExtra(ROOM_ID);
        mRoomNum = getIntent().getStringExtra(ROOM_NUM);
    }

    @Override
    protected void initContentView() {
        mLlEmpty = (LinearLayout) findViewById(R.id.ll_empty);
        mSrl = (SwipeRefreshLayout) findViewById(R.id.srl);
        mRv = (RecyclerView) findViewById(R.id.rv);
        mPersonManagerAdapter = new PersonManagerRvAdapter(this, personList);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        mRv.setHasFixedSize(true);
        mRv.setAdapter(mPersonManagerAdapter);

        mSrl.setColorSchemeResources(R.color.bg_black);
        mSrl.setProgressViewOffset(false, 0, AppUtil.dp2px(24));
    }

    @Override
    protected int getBackContentView() {
        return R.layout.single_rv;
    }

    @Override
    protected void initNet() {
        mSrl.setRefreshing(true);
        Map<String, Object> param = new HashMap<>();
        param.put("HOUSEID", mHouseId);
        param.put("ROOMID", mRoomId);
        param.put("TaskID", "1");
        param.put("PageSize", "100");
        param.put("PageIndex", "0");
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_RENT,Constants.ChuZuWu_MenPaiAuthorizationList, param)
                .setBeanType(ChuZuWu_MenPaiAuthorizationList.class)
                .setCallBack(new WebServiceCallBack<ChuZuWu_MenPaiAuthorizationList>() {
                    @Override
                    public void onSuccess(ChuZuWu_MenPaiAuthorizationList bean) {
                        mSrl.setRefreshing(false);
                        personList = bean.getContent().getPERSONNELINFOLIST();
                        mLlEmpty.setVisibility(personList.size()>0? View.GONE:View.VISIBLE);
                        mPersonManagerAdapter.setData(personList);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        mSrl.setRefreshing(false);
                    }
                }).build().execute();
    }

    @Override
    protected void initData() {
        mPersonManagerAdapter.setOnDeliteItemListener(this);
        mSrl.setOnRefreshListener(this);
    }

    @Override
    protected void setData() {
        setTitle("房间"+mRoomNum);

    }

    public static void goActivity(Context context, String houseId,String roomId,String roomNum) {
        Intent intent = new Intent(context, PersonManagerActivity2.class);
        intent.putExtra(HOUSE_ID, houseId);
        intent.putExtra(ROOM_ID, roomId);
        intent.putExtra(ROOM_NUM, roomNum);
        context.startActivity(intent);
    }

    @Override
    public void onRefresh() {
        mSrl.setRefreshing(false);
    }

    @Override
    public void onDeliteItem(String listId, int position) {
        ToastUtil.showToast("您好，该功能暂未开放");
    }
}
