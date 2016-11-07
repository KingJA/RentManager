package com.kingja.cardpackage.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.kingja.cardpackage.Event.ClearMsgEvent;
import com.kingja.cardpackage.adapter.AlarmMineAdapter;
import com.kingja.cardpackage.entiy.ErrorResult;
import com.kingja.cardpackage.entiy.GetUserMessage;
import com.kingja.cardpackage.entiy.SetUserMessageAll;
import com.kingja.cardpackage.net.ThreadPoolTask;
import com.kingja.cardpackage.net.WebServiceCallBack;
import com.kingja.cardpackage.util.AppUtil;
import com.kingja.cardpackage.util.Constants;
import com.kingja.cardpackage.util.DataManager;
import com.kingja.cardpackage.util.TempConstants;
import com.kingja.cardpackage.util.ToastUtil;
import com.tdr.wisdome.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description：我的消息
 * Create Time：2016/9/3 10:56
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class AlarmMineActivity extends BackTitleActivity implements SwipeRefreshLayout.OnRefreshListener {

    private LinearLayout mLlEmpty;
    private SwipeRefreshLayout mSrlTopContent;
    private ListView mLvTopContent;
    private List<GetUserMessage.ContentBean> mAlarmList = new ArrayList<>();
    private AlarmMineAdapter mAlarmAdapter;
    private int LOADSIZE = 200;
    private int loadIndex = 0;
    private boolean hasMore;


    @Override
    protected void initVariables() {
    }

    @Override
    protected void initContentView() {
        mLlEmpty = (LinearLayout) findViewById(R.id.ll_empty);
        mSrlTopContent = (SwipeRefreshLayout) findViewById(R.id.srl);
        mLvTopContent = (ListView) findViewById(R.id.lv);

        mAlarmAdapter = new AlarmMineAdapter(this, mAlarmList, "出租房");
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
        param.put(TempConstants.PageIndex, index);
        param.put(TempConstants.PageSize, TempConstants.DEFAULT_PAGE_SIZE);
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), "", Constants.GetUserMessage, param)
                .setBeanType(GetUserMessage.class)
                .setCallBack(new WebServiceCallBack<GetUserMessage>() {
                    @Override
                    public void onSuccess(GetUserMessage bean) {
                        mSrlTopContent.setRefreshing(false);
                        mAlarmList = bean.getContent();
                        mLlEmpty.setVisibility(mAlarmList.size() > 0 ? View.GONE : View.VISIBLE);
                        mAlarmAdapter.setData(filter(mAlarmList));
                        Log.e(TAG, "mAlarmList.size: " + mAlarmList.size());
                        hasMore = mAlarmList.size() == LOADSIZE;
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        mSrlTopContent.setRefreshing(false);
                    }
                }).build().execute();
    }

    private List<GetUserMessage.ContentBean> filter(List<GetUserMessage.ContentBean> mAlarmList) {
         List<GetUserMessage.ContentBean> newList=new ArrayList<>();
        for (GetUserMessage.ContentBean contentBean : mAlarmList) {
            if ("1001".equals(contentBean.getCardCode())||"1002".equals(contentBean.getCardCode())||"1007".equals(contentBean.getCardCode())){
                newList.add(contentBean);
            }
        }
        return newList;
    }

    @Override
    protected void initData() {
        mSrlTopContent.setOnRefreshListener(this);

    }

    @Override
    protected void setData() {
        setTitle("我的消息");
    }


    @Override
    public void onRefresh() {
        loadNet(0);
    }

    private void readAll() {
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.TaskID, TempConstants.DEFAULT_TASK_ID);
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), "", Constants.SetUserMessageAll, param)
                .setBeanType(SetUserMessageAll.class)
                .setCallBack(new WebServiceCallBack<SetUserMessageAll>() {
                    @Override
                    public void onSuccess(SetUserMessageAll bean) {
                        Log.e(TAG, "onSuccess: "+"信息全读" );

                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                    }
                }).build().execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().post(new ClearMsgEvent());
        readAll();
    }
}
