package com.kingja.cardpackage.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.kingja.cardpackage.adapter.PersonManagerLvAdapter;
import com.kingja.cardpackage.entiy.ChuZuWu_MenPaiAuthorizationList;
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
 * Create Time：2016/8/15 16:18
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class PersonManagerActivity extends BackTitleActivity {
    private String mHouseId;
    private String mRoomId;
    private String mRoomNum;

    private SwipeRefreshLayout mSrlTopContent;
    private ListView mLvTopContent;
    private List<ChuZuWu_MenPaiAuthorizationList.ContentBean.PERSONNELINFOLISTBean> personList = new ArrayList<>();
    private PersonManagerLvAdapter mPersonManagerAdapter;
    private LinearLayout mLlEmpty;

    @Override
    protected void initVariables() {
        mHouseId = getIntent().getStringExtra(TempConstants.HOUSEID);
        mRoomId = getIntent().getStringExtra(TempConstants.ROOMID);
        mRoomNum = getIntent().getStringExtra(TempConstants.ROOMNUM);
    }

    @Override
    protected void initContentView() {
        mLlEmpty = (LinearLayout) findViewById(R.id.ll_empty);
        mSrlTopContent = (SwipeRefreshLayout) findViewById(R.id.srl);
        mLvTopContent = (ListView) findViewById(R.id.lv);

        mPersonManagerAdapter = new PersonManagerLvAdapter(this, personList);
        mLvTopContent.setAdapter(mPersonManagerAdapter);

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
        param.put(TempConstants.HOUSEID, mHouseId);
        param.put(TempConstants.ROOMID, mRoomId);
        param.put(TempConstants.TaskID, "1");
        param.put(TempConstants.PageSize, "100");
        param.put(TempConstants.PageIndex, "0");
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_HOUSE, Constants.ChuZuWu_MenPaiAuthorizationList, param)
                .setBeanType(ChuZuWu_MenPaiAuthorizationList.class)
                .setCallBack(new WebServiceCallBack<ChuZuWu_MenPaiAuthorizationList>() {
                    @Override
                    public void onSuccess(ChuZuWu_MenPaiAuthorizationList bean) {
                        mSrlTopContent.setRefreshing(false);
                        personList = bean.getContent().getPERSONNELINFOLIST();
                        mLlEmpty.setVisibility(personList.size()>0? View.GONE:View.VISIBLE);
                        mPersonManagerAdapter.setData(personList);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        mSrlTopContent.setRefreshing(false);
                    }
                }).build().execute();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setData() {
        setTitle("房间" + mRoomNum);

    }

    public static void goActivity(Context context, String houseId, String roomId, String roomNum) {
        Intent intent = new Intent(context, PersonManagerActivity2.class);
        intent.putExtra(TempConstants.HOUSEID, houseId);
        intent.putExtra(TempConstants.ROOMID, roomId);
        intent.putExtra(TempConstants.ROOMNUM, roomNum);
        context.startActivity(intent);
    }
}
