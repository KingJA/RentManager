package com.kingja.cardpackage.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.kingja.cardpackage.adapter.AlarmAdapter;
import com.kingja.cardpackage.entiy.AlarmList;
import com.kingja.cardpackage.entiy.ErrorResult;
import com.kingja.cardpackage.net.ThreadPoolTask;
import com.kingja.cardpackage.net.WebServiceCallBack;
import com.kingja.cardpackage.util.AppUtil;
import com.kingja.cardpackage.util.Constants;
import com.kingja.cardpackage.util.DataManager;
import com.kingja.cardpackage.util.TempConstants;
import com.kingja.cardpackage.util.ToastUtil;
import com.tdr.wisdome.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description：出租房预警信息
 * Create Time：2016/9/3 10:56
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class AlarmRentActivity extends BackTitleActivity implements SwipeRefreshLayout.OnRefreshListener{

    private String mHouseId;
    private LinearLayout mLlEmpty;
    private SwipeRefreshLayout mSrlTopContent;
    private ListView mLvTopContent;
    private List<AlarmList.ContentBean> mAlarmList=new ArrayList<>();
    private AlarmAdapter mAlarmAdapter;
    private  int LOADSIZE=200;
    private  int loadIndex=0;
    private boolean hasMore;


    @Override
    protected void initVariables() {
        mHouseId = getIntent().getStringExtra(TempConstants.HOUSEID);

    }

    @Override
    protected void initContentView() {
        mLlEmpty = (LinearLayout) findViewById(R.id.ll_empty);
        mSrlTopContent = (SwipeRefreshLayout) findViewById(R.id.srl);
        mLvTopContent = (ListView) findViewById(R.id.lv);

        mAlarmAdapter = new AlarmAdapter(this, mAlarmList,"出租房");
        mLvTopContent.setAdapter(mAlarmAdapter);

        mSrlTopContent.setColorSchemeResources(R.color.bg_black);
        mSrlTopContent.setProgressViewOffset(false, 0, AppUtil.dp2px(24));

    }

    @Override
    protected int getBackContentView() {
        return R.layout.single_lv;
    }

    @Override
    protected void initNet() {
        loadNet(loadIndex);
    }

    private void loadNet(int index) {
        mSrlTopContent.setRefreshing(true);
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.TaskID, TempConstants.DEFAULT_TASK_ID);
        param.put(TempConstants.HOUSEID, mHouseId);
        param.put(TempConstants.PageIndex, index);
        param.put(TempConstants.PageSize, TempConstants.DEFAULT_PAGE_SIZE);
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_AGENT, Constants.ChuZuWu_MessageList, param)
                .setBeanType(AlarmList.class)
                .setCallBack(new WebServiceCallBack<AlarmList>() {
                    @Override
                    public void onSuccess(AlarmList bean) {
                        mSrlTopContent.setRefreshing(false);
                        mAlarmList = bean.getContent();
                        mLlEmpty.setVisibility(mAlarmList.size()>0? View.GONE:View.VISIBLE);
                        mAlarmAdapter.addData(mAlarmList);
                        Log.e(TAG, "mAlarmList.size: "+mAlarmList.size());
                        hasMore=mAlarmList.size()==LOADSIZE;

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
        mLvTopContent.setOnScrollListener(onScrollListener);

    }

    @Override
    protected void setData() {
        setTitle("预警信息");
    }

    public static void goActivity(Activity activity, String houseId) {
        Intent intent = new Intent(activity, AlarmRentActivity.class);
        intent.putExtra(TempConstants.HOUSEID, houseId);
        activity.startActivity(intent);

    }

    @Override
    public void onRefresh() {
        mAlarmAdapter.reset();
        loadNet(0);
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
                            loadNet(++loadIndex);
                        }else{
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

}
