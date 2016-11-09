package com.kingja.cardpackage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.kingja.cardpackage.Event.AgentDialogEvent;
import com.kingja.cardpackage.Event.ShopDialogEvent;
import com.kingja.cardpackage.adapter.RentAdapter;
import com.kingja.cardpackage.entiy.ChuZuWu_JoinManage;
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
import com.kingja.cardpackage.util.GoUtil;
import com.kingja.cardpackage.util.JoinAdd;
import com.kingja.cardpackage.util.PhoneUtil;
import com.kingja.cardpackage.util.TempConstants;
import com.kingja.cardpackage.util.ToastUtil;
import com.tdr.wisdome.R;
import com.tdr.wisdome.zbar.CaptureActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description：出租房代管
 * Create Time：2016/8/4 16:20
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class AgentActivity extends BackTitleActivity implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener, BackTitleActivity.OnRightClickListener {
    private SwipeRefreshLayout mSrlTopContent;
    private ListView mLvTopContent;
    private List<RentBean> mChuZuWuList = new ArrayList<>();
    private RentAdapter mRentAdapter;
    private LinearLayout mLlEmpty;

    @Override
    protected void initVariables() {
        mZeusManager.checkPermissions(permissionArr, true);
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
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_AGENT, Constants.ChuZuWu_ListByManager, param)
                .setBeanType(ChuZuWu_List.class)
                .setCallBack(new WebServiceCallBack<ChuZuWu_List>() {
                    @Override
                    public void onSuccess(ChuZuWu_List bean) {
                        mSrlTopContent.setRefreshing(false);
                        mChuZuWuList = bean.getContent();
                        mLlEmpty.setVisibility(mChuZuWuList.size() > 0 ? View.GONE : View.VISIBLE);
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
        setTitle("出租房代管");
        setTopColor(TopColor.WHITE);
        setOnRightClickListener(this, "加入");
        DataManager.putLastPage(2);
    }

    @Override
    public void onRefresh() {
        doNet();
//        mSrlTopContent.setRefreshing(false);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RentBean bean = (RentBean) parent.getItemAtPosition(position);
        DetailAgentActivity.goActivity(this, bean);
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
        mInfo.setCARDTYPE(Constants.CARD_TYPE_AGENT);
        mInfo.setPHONEINFO(phoneInfo);
        mInfo.setSOFTVERSION(AppInfoUtil.getVersionName());
        mInfo.setTaskID("1");
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_AGENT, Constants.User_LogInForKaBao, mInfo)
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

    @Override
    public void onRightClick() {
        GoUtil.goActivityForResult(this, CaptureActivity.class, 100);
    }

    private void addAdmin(String key) {
        setProgressDialog(true);
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.TaskID, TempConstants.DEFAULT_TASK_ID);
        param.put("KEY", key);
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_AGENT, Constants.ChuZuWu_JoinManage, param)
                .setBeanType(ChuZuWu_JoinManage.class)
                .setCallBack(new WebServiceCallBack<ChuZuWu_JoinManage>() {
                    @Override
                    public void onSuccess(ChuZuWu_JoinManage bean) {
                        setProgressDialog(false);
                        ToastUtil.showToast("成功添加管理员");
                        initNet();
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build().execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Bundle bundle = data.getExtras();
            String url = bundle.getString("result");
            Log.e(TAG, "url: " + url);
            String result = url.substring(url.indexOf("?") + 1);
            try {
                String type = result.substring(0, 2);
                if (type.equals("a1")) {
                    String base = result.substring(2);
                    String key = JoinAdd.isAdd(base).substring(4);
                    if (!key.equals("")) {
                        addAdmin(key);
                    }
                } else {
                    ToastUtil.showToast("二维码无法识别");
                }
            } catch (StringIndexOutOfBoundsException e) {
                ToastUtil.showToast("二维码异常请重新生成");
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        DataManager.putLastPage(-1);
    }
}
