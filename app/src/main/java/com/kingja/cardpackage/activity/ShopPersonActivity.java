package com.kingja.cardpackage.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.kingja.cardpackage.adapter.DividerItemDecoration;
import com.kingja.cardpackage.adapter.ShopPersonAdapter;
import com.kingja.cardpackage.entiy.ErrorResult;
import com.kingja.cardpackage.entiy.ShangPu_DismissEmployee;
import com.kingja.cardpackage.entiy.ShangPu_EmployeeList;
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
 * Create Time：2016/8/30 16:20
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ShopPersonActivity extends BackTitleActivity implements BackTitleActivity.OnMenuClickListener,ShopPersonAdapter.OnShopPersonDeliteListener,SwipeRefreshLayout.OnRefreshListener{

    private String mShopId;
    private String mShopName;
    private LinearLayout mLlEmpty;
    private SwipeRefreshLayout mSrl;
    private RecyclerView mRv;
    private List<ShangPu_EmployeeList.ContentBean.PERSONNELINFOLISTBean> mPersonnelinfolist=new ArrayList<>();
    private ShopPersonAdapter mShopPersonAdapter;


    @Override
    protected void initVariables() {
        mShopId = getIntent().getStringExtra(TempConstants.SHOPID);
        mShopName = getIntent().getStringExtra("SHOP_NAME");
    }

    @Override
    protected void initContentView() {
        mLlEmpty = (LinearLayout) findViewById(R.id.ll_empty);
        mSrl = (SwipeRefreshLayout) findViewById(R.id.srl);
        mRv = (RecyclerView) findViewById(R.id.rv);

        mShopPersonAdapter = new ShopPersonAdapter(this, mPersonnelinfolist);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        mRv.setHasFixedSize(true);
        mRv.setAdapter(mShopPersonAdapter);

        mSrl.setColorSchemeResources(R.color.bg_black);
        mSrl.setProgressViewOffset(false, 0, AppUtil.dp2px(24));
    }

    @Override
    protected int getBackContentView() {
        return R.layout.single_rv;
    }

    @Override
    protected void initNet() {
        doNet();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        doNet();
    }

    private void doNet() {
        setProgressDialog(true);
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.TaskID, "1");
        param.put(TempConstants.SHOPID, mShopId);
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_HOUSE, Constants.ShangPu_EmployeeList, param)
                .setBeanType(ShangPu_EmployeeList.class)
                .setCallBack(new WebServiceCallBack<ShangPu_EmployeeList>() {
                    @Override
                    public void onSuccess(ShangPu_EmployeeList bean) {
                        setProgressDialog(false);
                        mPersonnelinfolist = bean.getContent().getPERSONNELINFOLIST();
                        mLlEmpty.setVisibility(mPersonnelinfolist.size()>0? View.GONE:View.VISIBLE);
                        mShopPersonAdapter.setData(mPersonnelinfolist);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build().execute();
    }

    @Override
    protected void initData() {
        setOnMenuClickListener(this, R.drawable.bg_add);
        mShopPersonAdapter.setOnShopPersonDeliteListener(this);
        mSrl.setOnRefreshListener(this);
    }

    @Override
    protected void setData() {
        setTitle("员工管理");
    }

    @Override
    public void onMenuClick() {
       ShopPersonQRCodeActivity.goActivity(this,mShopId,mShopName);
    }

    public static void goActivity(Context context, String shopId, String shopName) {
        Intent intent = new Intent(context, ShopPersonActivity.class);
        intent.putExtra(TempConstants.SHOPID, shopId);
        intent.putExtra("SHOP_NAME", shopName);
        context.startActivity(intent);
    }

    /**
     * 员工删除
     * @param listId
     * @param position
     */
    @Override
    public void OnShopPersonDelite(String listId, final int position) {
        mSrl.setRefreshing(true);
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.TaskID, "1");
        param.put(TempConstants.SHOPID, mShopId);
        param.put("LISTID", listId);

        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_SHOP, Constants.ShangPu_DismissEmployee, param)
                .setBeanType(ShangPu_DismissEmployee.class)
                .setCallBack(new WebServiceCallBack<ShangPu_DismissEmployee>() {
                    @Override
                    public void onSuccess(ShangPu_DismissEmployee bean) {
                        mSrl.setRefreshing(false);
                        mShopPersonAdapter.deleteItem(position);

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
