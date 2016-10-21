package com.kingja.cardpackage.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.kingja.cardpackage.adapter.AddedShopAdapter;
import com.kingja.cardpackage.adapter.DividerItemDecoration;
import com.kingja.cardpackage.adapter.ShopDeviceAdapter;
import com.kingja.cardpackage.entiy.Common_RemoveDevice;
import com.kingja.cardpackage.entiy.ErrorResult;
import com.kingja.cardpackage.entiy.ShangPu_DeviceLists;
import com.kingja.cardpackage.entiy.ShangPu_ListByEmp;
import com.kingja.cardpackage.entiy.ShangPu_UserOut;
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
 * Create Time：2016/10/18 10:05
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class AddedShopActivity extends BackTitleActivity implements AddedShopAdapter.OnShopDeliteListener,SwipeRefreshLayout.OnRefreshListener{
    private LinearLayout mLlEmpty;
    private SwipeRefreshLayout mSrl;
    private RecyclerView mRv;
    private List<ShangPu_ListByEmp.ContentBean> mAddedShopList=new ArrayList<>();
    private AddedShopAdapter mAddedShopAdapter;
    private NormalDialog deleteDialog;


    @Override
    protected void initVariables() {

    }

    @Override
    protected void initContentView() {
        mLlEmpty = (LinearLayout) findViewById(R.id.ll_empty);
        mSrl = (SwipeRefreshLayout) findViewById(R.id.srl);
        mRv = (RecyclerView) findViewById(R.id.rv);

        mAddedShopAdapter = new AddedShopAdapter(this, mAddedShopList);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        mRv.setHasFixedSize(true);
        mRv.setAdapter(mAddedShopAdapter);

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
        param.put(TempConstants.PageSize, TempConstants.DEFAULT_PAGE_SIZE);
        param.put(TempConstants.PageIndex, TempConstants.DEFAULT_PAGE_INDEX);
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_SHOP, Constants.ShangPu_ListByEmp, param)
                .setBeanType(ShangPu_ListByEmp.class)
                .setCallBack(new WebServiceCallBack<ShangPu_ListByEmp>() {
                    @Override
                    public void onSuccess(ShangPu_ListByEmp bean) {
                        mSrl.setRefreshing(false);
                        mAddedShopList = bean.getContent();
                        mLlEmpty.setVisibility(mAddedShopList.size() > 0 ? View.GONE : View.VISIBLE);
                        mAddedShopAdapter.setData(mAddedShopList);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        mSrl.setRefreshing(false);
                    }
                }).build().execute();
    }

    @Override
    protected void initData() {
        mAddedShopAdapter.setOnShopDeliteListener(this);
        mSrl.setOnRefreshListener(this);
    }

    @Override
    protected void setData() {
        setTitle("已加店铺");
    }

    @Override
    public void onShopDelite(final int position, final String shopId) {
        deleteDialog = DialogUtil.getDoubleDialog(this, "确定要退出店铺", "取消", "确定");
        deleteDialog.setOnBtnClickL(new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                deleteDialog.dismiss();
            }
        }, new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                deleteDialog.dismiss();
                uploadDeleteShop(position, shopId);
            }
        });
        deleteDialog.show();

    }

    private void uploadDeleteShop(final int position, String shopId) {
        mSrl.setRefreshing(true);
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.TaskID, TempConstants.DEFAULT_TASK_ID);
        param.put(TempConstants.TaskID, TempConstants.DEFAULT_TASK_ID);
        param.put(TempConstants.SHOPID, shopId);
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_SHOP, Constants.ShangPu_UserOut, param)
                .setBeanType(ShangPu_UserOut.class)
                .setCallBack(new WebServiceCallBack<ShangPu_UserOut>() {
                    @Override
                    public void onSuccess(ShangPu_UserOut bean) {
                        mSrl.setRefreshing(false);
                        mAddedShopAdapter.deleteItem(position);
                        ToastUtil.showToast("退出店铺成功");
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
