package com.kingja.cardpackage.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.kingja.cardpackage.adapter.AdminRvAdapter;
import com.kingja.cardpackage.adapter.DividerItemDecoration;
import com.kingja.cardpackage.entiy.ChuZuWu_AdminList;
import com.kingja.cardpackage.entiy.ChuZuWu_RemoveAdmin;
import com.kingja.cardpackage.entiy.ErrorResult;
import com.kingja.cardpackage.net.ThreadPoolTask;
import com.kingja.cardpackage.net.WebServiceCallBack;
import com.kingja.cardpackage.util.AppUtil;
import com.kingja.cardpackage.util.Constants;
import com.kingja.cardpackage.util.DataManager;
import com.kingja.cardpackage.util.DialogUtil;
import com.kingja.cardpackage.util.TempConstants;
import com.kingja.cardpackage.util.ToastUtil;
import com.tdr.wisdome.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description：TODO
 * Create Time：2016/9/18 9:29
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class RentAdminActivity extends BackTitleActivity implements BackTitleActivity.OnRightClickListener,AdminRvAdapter.OnDeliteItemListener,SwipeRefreshLayout.OnRefreshListener{
    private LinearLayout mLlEmpty;
    private SwipeRefreshLayout mSrl;
    private RecyclerView mRv;
    private String houseId;
    private String houseName;
    private List<ChuZuWu_AdminList.ContentBean.AdminListBean> adminList=new ArrayList<>();
    private AdminRvAdapter mAdminRvAdapter;

    @Override
    protected void initVariables() {
        houseId = getIntent().getStringExtra(TempConstants.HOUSEID);
        houseName = getIntent().getStringExtra("HOUSE_NAME");
    }

    @Override
    protected void initContentView() {
        mLlEmpty = (LinearLayout) findViewById(R.id.ll_empty);
        mSrl = (SwipeRefreshLayout) findViewById(R.id.srl);
        mRv = (RecyclerView) findViewById(R.id.rv);

        mAdminRvAdapter = new AdminRvAdapter(this, adminList);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        mRv.setHasFixedSize(true);
        mRv.setAdapter(mAdminRvAdapter);
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
        param.put(TempConstants.TaskID, TempConstants.DEFAULT_TASK_ID);
        param.put(TempConstants.HOUSEID, houseId);
        param.put(TempConstants.PageSize, TempConstants.DEFAULT_PAGE_SIZE);
        param.put(TempConstants.PageIndex, TempConstants.DEFAULT_PAGE_INDEX);
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_RENT, Constants.ChuZuWu_AdminList, param)
                .setBeanType(ChuZuWu_AdminList.class)
                .setCallBack(new WebServiceCallBack<ChuZuWu_AdminList>() {
                    @Override
                    public void onSuccess(ChuZuWu_AdminList bean) {
                        mSrl.setRefreshing(false);
                        adminList = bean.getContent().getAdminList();
                        mLlEmpty.setVisibility(adminList.size() > 0 ? View.GONE : View.VISIBLE);
                        mAdminRvAdapter.setData(adminList);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        mSrl.setRefreshing(false);
                    }
                }).build().execute();
    }

    @Override
    protected void initData() {
        mAdminRvAdapter.setOnDeliteItemListener(this);
        mSrl.setOnRefreshListener(this);
    }

    @Override
    protected void setData() {
        setTitle("管理员管理");
        setOnRightClickListener(this,"添加");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initNet();
    }

    @Override
    public void onRightClick() {
      AdminQRCodeActivity.goActivity(this,houseId,houseName);
    }

    public static void goActivity(Activity activity, String houseId, String houseName) {
        Intent intent = new Intent(activity, RentAdminActivity.class);
        intent.putExtra(TempConstants.HOUSEID, houseId);
        intent.putExtra("HOUSE_NAME", houseName);
        activity.startActivity(intent);

    }

    @Override
    public void onDeliteItem(final String cardId, final int position) {
        final NormalDialog deleteAdminDialog = DialogUtil.getDoubleDialog(this, "是否确定删除该管理员", "取消", "确定");
        deleteAdminDialog.setOnBtnClickL(new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                deleteAdminDialog.dismiss();
            }
        }, new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                deleteAdminDialog.dismiss();
                uploadDelete(cardId, position);
            }
        });
        deleteAdminDialog.show();
    }

    private void uploadDelete(String cardId, final int position) {
        mSrl.setRefreshing(true);
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.TaskID, TempConstants.DEFAULT_TASK_ID);
        param.put(TempConstants.HOUSEID, houseId);
        param.put("IDENTITYCARD", cardId);
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_RENT, Constants.ChuZuWu_RemoveAdmin, param)
                .setBeanType(ChuZuWu_RemoveAdmin.class)
                .setCallBack(new WebServiceCallBack<ChuZuWu_RemoveAdmin>() {
                    @Override
                    public void onSuccess(ChuZuWu_RemoveAdmin bean) {
                        mSrl.setRefreshing(false);
                        mAdminRvAdapter.deleteItem(position);
                        ToastUtil.showToast("成功删除管理员");
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        mSrl.setRefreshing(false);
                    }
                }).build().execute();
    }

    @Override
    public void onRefresh() {
        mSrl.setRefreshing(false);
    }
}
