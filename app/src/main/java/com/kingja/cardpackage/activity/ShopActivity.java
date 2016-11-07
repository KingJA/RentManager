package com.kingja.cardpackage.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.kingja.cardpackage.Event.ShopDialogEvent;
import com.kingja.cardpackage.Event.ShopRefreshEvent;
import com.kingja.cardpackage.adapter.ShopAdapter;
import com.kingja.cardpackage.entiy.ErrorResult;
import com.kingja.cardpackage.entiy.LoginInfo;
import com.kingja.cardpackage.entiy.PhoneInfo;
import com.kingja.cardpackage.entiy.ShangPu_JoinShangPu;
import com.kingja.cardpackage.entiy.ShangPu_List;
import com.kingja.cardpackage.entiy.ShangPu_TakeOver;
import com.kingja.cardpackage.entiy.ShopBean;
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
import com.kingja.ui.popupwindow.ListPop;
import com.tdr.wisdome.R;
import com.tdr.wisdome.zbar.CaptureActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description：店铺列表
 * Create Time：2016/8/4 16:20
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ShopActivity extends BackTitleActivity implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener, BackTitleActivity.OnMenuClickListener, ListPop.OnPopItemClickListener {
    private SwipeRefreshLayout mSrlTopContent;
    private ListView mLvTopContent;
    private ShopAdapter mShopAdapter;
    private List<ShopBean> mShopList = new ArrayList<>();
    private LinearLayout mLlEmpty;
    private ListPop mListPop;


    @Override
    protected void initVariables() {
        mZeusManager.checkPermissions(permissionArr, true);
    }


    @Override
    protected void initContentView() {
        EventBus.getDefault().register(this);
        mLlEmpty = (LinearLayout) findViewById(R.id.ll_empty);
        mSrlTopContent = (SwipeRefreshLayout) findViewById(R.id.srl);
        mLvTopContent = (ListView) findViewById(R.id.lv);

        mShopAdapter = new ShopAdapter(this, mShopList);
        mLvTopContent.setAdapter(mShopAdapter);

        mSrlTopContent.setColorSchemeResources(R.color.bg_black);
        mSrlTopContent.setProgressViewOffset(false, 0, AppUtil.dp2px(24));
        mListPop = new ListPop(mRlTopMenu, this, Arrays.asList("添加店铺", "加入店铺", "已加店铺", "接收店铺"));


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
    protected void initData() {
        mLvTopContent.setOnItemClickListener(this);
        mSrlTopContent.setOnRefreshListener(this);
        setOnMenuClickListener(this, R.drawable.bg_add);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void setData() {
        setTitle("我的店");
        setTopColor(TopColor.WHITE);
        mListPop.setOnPopItemClickListener(this);
    }


    @Override
    public void onRefresh() {
//        mSrlTopContent.setRefreshing(false);
        doNet();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ShopBean bean = (ShopBean) parent.getItemAtPosition(position);
        DetailShopActivity.goActivity(this, bean);
    }

    @Override
    public void onMenuClick() {
        mListPop.showPopupWindowDown();
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
        mInfo.setCARDTYPE(Constants.CARD_TYPE_SHOP);
        mInfo.setPHONEINFO(phoneInfo);
        mInfo.setSOFTVERSION(AppInfoUtil.getVersionName());
        mInfo.setTaskID("1");
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_SHOP, Constants.User_LogInForKaBao, mInfo)
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

    private void doNet() {
        mSrlTopContent.setRefreshing(true);
        Map<String, Object> param = new HashMap<>();
        param.put("TaskID", "1");
        param.put("PageSize", "100");
        param.put("PageIndex", "0");
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_SHOP, Constants.ShangPu_List, param)
                .setBeanType(ShangPu_List.class)
                .setCallBack(new WebServiceCallBack<ShangPu_List>() {
                    @Override
                    public void onSuccess(ShangPu_List bean) {
                        mSrlTopContent.setRefreshing(false);
                        mShopList = bean.getContent();
                        mLlEmpty.setVisibility(mShopList.size() > 0 ? View.GONE : View.VISIBLE);
                        mShopAdapter.setData(mShopList);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        mSrlTopContent.setRefreshing(false);
                    }
                }).build().execute();
    }

    @Override
    public void onPopItemClick(int position, String tag) {
        switch (position) {
            case 0://添加店铺
                GoUtil.goActivity(this, AddShopActivity.class);
                break;
            case 1://加入店铺
                mZeusManager.checkPermission(Manifest.permission.CAMERA, true);
                break;
            case 2://已加店铺
                GoUtil.goActivity(this, AddedShopActivity.class);
                break;
            case 3://接收店铺
                GoUtil.goActivityForResult(this, CaptureActivity.class, 200);
                break;
            default:
                break;
        }
    }

    @Override
    public void onAllow() {
        GoUtil.goActivityForResult(this, CaptureActivity.class, 100);
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
                        Log.e(TAG, "key: " + key);
                        if (requestCode == 100) {
                            addEmployee(key);
                        } else {
                            acceptShop(key);
                        }

                    }
                } else {
                    ToastUtil.showToast("二维码无法识别");
                }
            } catch (StringIndexOutOfBoundsException e) {
                ToastUtil.showToast("二维码异常请重新生成");
            }
        }
    }

    /**
     * 加入店铺
     * @param key
     */
    private void addEmployee(String key) {
        setProgressDialog(true);
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.TaskID, TempConstants.DEFAULT_TASK_ID);
        param.put("KEY", key);
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_SHOP, Constants.ShangPu_JoinShangPu, param)
                .setBeanType(ShangPu_JoinShangPu.class)
                .setCallBack(new WebServiceCallBack<ShangPu_JoinShangPu>() {
                    @Override
                    public void onSuccess(ShangPu_JoinShangPu bean) {
                        setProgressDialog(false);
                        ToastUtil.showToast("成功加入店铺");
                        GoUtil.goActivity(ShopActivity.this,AddedShopActivity.class);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build().execute();
    }

    /**
     * 接收店铺
     * @param key
     */
    private void acceptShop(String key) {
        setProgressDialog(true);
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.TaskID, TempConstants.DEFAULT_TASK_ID);
        param.put("KEY", key);
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_SHOP, Constants.ShangPu_TakeOver, param)
                .setBeanType(ShangPu_TakeOver.class)
                .setCallBack(new WebServiceCallBack<ShangPu_TakeOver>() {
                    @Override
                    public void onSuccess(ShangPu_TakeOver bean) {
                        setProgressDialog(false);
                        ToastUtil.showToast("成功接收店铺");
                        doNet();
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build().execute();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ShopRefreshEvent messageEvent) {
        doNet();
    }
}
