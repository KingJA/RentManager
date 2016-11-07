package com.kingja.cardpackage.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.kingja.cardpackage.Event.HouseDialogEvent;
import com.kingja.cardpackage.Event.RentDialogEvent;
import com.kingja.cardpackage.adapter.RentAdapter;
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
import com.kingja.cardpackage.util.ToastUtil;
import com.tdr.wisdome.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description：我的出租房
 * Create Time：2016/8/4 16:20
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class RentActivity extends BackTitleActivity implements SwipeRefreshLayout.OnRefreshListener,AdapterView.OnItemClickListener{
    private SwipeRefreshLayout mSrlTopContent;
    private ListView mLvTopContent;
    private List<RentBean> mChuZuWuList=new ArrayList<>();
    private RentAdapter mRentAdapter;
    private LinearLayout mLlEmpty;


    @Override
    protected void initVariables() {
        mZeusManager.checkPermissions(permissionArr,true);
    }


    @Override
    protected void initContentView() {
        mLlEmpty = (LinearLayout) findViewById(R.id.ll_empty);
        mSrlTopContent = (SwipeRefreshLayout) findViewById(R.id.srl);
        mLvTopContent = (ListView) findViewById(R.id.lv);

        mRentAdapter = new RentAdapter(this, mChuZuWuList);
        mLvTopContent.setAdapter(mRentAdapter);

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

    private void doNet() {
        mSrlTopContent.setRefreshing(true);
        Map<String, Object> param = new HashMap<>();
        param.put("TaskID", "1");
        param.put("PageSize", "100");
        param.put("PageIndex", "0");
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_RENT, Constants.ChuZuWu_List, param)
                .setBeanType(ChuZuWu_List.class)
                .setCallBack(new WebServiceCallBack<ChuZuWu_List>() {
                    @Override
                    public void onSuccess(ChuZuWu_List bean) {
                        mSrlTopContent.setRefreshing(false);
                        mChuZuWuList = bean.getContent();
                        mLlEmpty.setVisibility(mChuZuWuList.size()>0? View.GONE:View.VISIBLE);
                        mRentAdapter.setData(mChuZuWuList);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        mSrlTopContent.setRefreshing(false);
                    }
                }).build().execute();
    }

    @Override
    protected void initData() {
        mLvTopContent.setOnItemClickListener(this);
        mSrlTopContent.setOnRefreshListener(this);
    }

    @Override
    protected void setData() {
        setTitle("我的出租房");
        setTopColor(TopColor.WHITE);
    }


    @Override
    public void onRefresh() {
        doNet();
//        mSrlTopContent.setRefreshing(false);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RentBean bean= (RentBean) parent.getItemAtPosition(position);
        DetailRentActivity.goActivity(this,bean);
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
        mInfo.setSOFTTYPE(1);
        mInfo.setCARDTYPE(Constants.CARD_TYPE_RENT);
        mInfo.setPHONEINFO(phoneInfo);
        mInfo.setSOFTVERSION(AppInfoUtil.getVersionName());
        mInfo.setTaskID("1");
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_RENT, Constants.User_LogInForKaBao, mInfo)
                .setBeanType(User_LogInForKaBao.class)
                .setCallBack(new WebServiceCallBack<User_LogInForKaBao>() {
                    @Override
                    public void onSuccess(User_LogInForKaBao bean) {
                        setProgressDialog(false);
                        doNet();
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                        finish();
                        ToastUtil.showToast("登录失败");
                    }
                }).build().execute();
    }
}
