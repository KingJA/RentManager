package com.kingja.cardpackage.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.kingja.cardpackage.adapter.HouseAdapter;
import com.kingja.cardpackage.entiy.ChuZuWu_List;
import com.kingja.cardpackage.entiy.ErrorResult;
import com.kingja.cardpackage.entiy.LoginInfo;
import com.kingja.cardpackage.entiy.PhoneInfo;
import com.kingja.cardpackage.entiy.RentBean;
import com.kingja.cardpackage.entiy.User_LogInForKaBao;
import com.kingja.cardpackage.net.ThreadPoolTask;
import com.kingja.cardpackage.net.WebServiceCallBack;
import com.kingja.cardpackage.util.AppInfoUtil;
import com.kingja.cardpackage.util.AppUtil;
import com.kingja.cardpackage.util.Constants;
import com.kingja.cardpackage.util.DataManager;
import com.kingja.cardpackage.util.PhoneUtil;
import com.kingja.cardpackage.util.TempConstants;
import com.kingja.cardpackage.util.ToastUtil;
import com.tdr.wisdome.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description：TODO
 * Create Time：2016/8/4 16:20
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class HouseActivity extends BackTitleActivity implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {
    private SwipeRefreshLayout mSrlTopContent;
    private ListView mLvTopContent;
    private List<RentBean> mHouseList = new ArrayList<>();
    private HouseAdapter mHouseAdapter;
    private LinearLayout mLlEmpty;
    private int LOADSIZE = 50;
    private int loadIndex = 0;
    private boolean hasMore;

    @Override
    protected void initVariables() {
        mZeusManager.checkPermissions(permissionArr, true);
    }

    @Override
    protected void initContentView() {
        mLlEmpty = (LinearLayout) findViewById(R.id.ll_empty);
        mSrlTopContent = (SwipeRefreshLayout) findViewById(R.id.srl);
        mLvTopContent = (ListView) findViewById(R.id.lv);

        mHouseAdapter = new HouseAdapter(this, mHouseList);
        mLvTopContent.setAdapter(mHouseAdapter);
        mSrlTopContent.setColorSchemeResources(R.color.bg_black);
        mSrlTopContent.setProgressViewOffset(false, 0, AppUtil.dp2px(24));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected int getBackContentView() {
        return R.layout.single_lv;
    }

    @Override
    protected void initNet() {
        cardLogin();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        doNet(0);
    }

    private void doNet(final int index) {
        mSrlTopContent.setRefreshing(true);
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.TaskID, TempConstants.DEFAULT_TASK_ID);
        param.put(TempConstants.PageSize, LOADSIZE);
        param.put(TempConstants.PageIndex, index);
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_HOUSE, Constants.ChuZuWu_ListByRenter, param)
                .setBeanType(ChuZuWu_List.class)
                .setCallBack(new WebServiceCallBack<ChuZuWu_List>() {
                    @Override
                    public void onSuccess(ChuZuWu_List bean) {
                        mSrlTopContent.setRefreshing(false);
                        mHouseList = bean.getContent();
                        if (index == 0) {
                            mHouseAdapter.reset();
                        }
                        hasMore = mHouseList.size() == LOADSIZE;
                        Log.e(TAG, "hasMore" + hasMore);
                        Log.e(TAG, "加载数据条数" + mHouseList.size());
                        mLlEmpty.setVisibility(mHouseList.size() > 0 ? View.GONE : View.VISIBLE);
                        mHouseAdapter.addData(mHouseList);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        mSrlTopContent.setRefreshing(false);
                    }
                }).build().execute();
    }

    private AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            switch (scrollState) {
                case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                    if (mLvTopContent.getLastVisiblePosition() == (mLvTopContent.getCount() - 1)) {
                        Log.e("log", "滑到底部");
                        if (mSrlTopContent.isRefreshing()) {
                            return;
                        }
                        if (hasMore) {
                            doNet(++loadIndex);
                        } else {
                            ToastUtil.showToast("已经没有更多数据");
                        }
                    }
                    break;
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        }
    };

    @Override
    protected void initData() {
        mLvTopContent.setOnItemClickListener(this);
        mSrlTopContent.setOnRefreshListener(this);
        mLvTopContent.setOnScrollListener(onScrollListener);
    }

    @Override
    protected void setData() {
        setTitle("我的住房");
        setTopColor(TopColor.WHITE);
    }


    @Override
    public void onRefresh() {
        loadIndex = 0;
        doNet(loadIndex);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RentBean bean = (RentBean) parent.getItemAtPosition(position);
        DetailHouseActivity.goActivity(this, bean, bean.getRoomList().get(0));
    }

    private void cardLogin() {
        setProgressDialog(true);
        LoginInfo mInfo = new LoginInfo();
        PhoneInfo phoneInfo = new PhoneUtil(this).getInfo();
        mInfo.setTaskID("1");
        mInfo.setREALNAME(DataManager.getRealName());
        mInfo.setIDENTITYCARD(DataManager.getIdCard());
        mInfo.setPHONENUM(DataManager.getUserPhone());
        mInfo.setSOFTVERSION(AppInfoUtil.getVersionName());
        mInfo.setSOFTTYPE(Constants.SOFTTYPE);
        mInfo.setCARDTYPE(Constants.CARD_TYPE_HOUSE);
        mInfo.setPHONEINFO(phoneInfo);
        mInfo.setSOFTVERSION(AppInfoUtil.getVersionName());
        mInfo.setTaskID("1");
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_HOUSE, Constants.User_LogInForKaBao, mInfo)
                .setBeanType(User_LogInForKaBao.class)
                .setCallBack(new WebServiceCallBack<User_LogInForKaBao>() {
                    @Override
                    public void onSuccess(User_LogInForKaBao bean) {
                        setProgressDialog(false);
                        doNet(0);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                        finish();
                        ToastUtil.showToast("卡包登录失败");
                    }
                }).build().execute();
    }
}
