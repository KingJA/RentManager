package com.kingja.cardpackage.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.kingja.cardpackage.Event.RefreshUnregisteredList;
import com.kingja.cardpackage.adapter.DividerItemDecoration;
import com.kingja.cardpackage.adapter.UnregisteredApplyRvAdapter;
import com.kingja.cardpackage.base.BaseFragment;
import com.kingja.cardpackage.entiy.ChuZuWu_AgencySelfReportingList;
import com.kingja.cardpackage.entiy.ChuZuWu_LKSelfReportingOut;
import com.kingja.cardpackage.entiy.ErrorResult;
import com.kingja.cardpackage.net.ThreadPoolTask;
import com.kingja.cardpackage.net.WebServiceCallBack;
import com.kingja.cardpackage.util.AppUtil;
import com.kingja.cardpackage.util.Constants;
import com.kingja.cardpackage.util.DataManager;
import com.kingja.cardpackage.util.DialogUtil;
import com.kingja.cardpackage.util.TempConstants;
import com.tdr.wisdome.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description：申报列表
 * Create Time：2016/8/19 14:20
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */

public class UnregisteredApplyListFragment extends BaseFragment implements SwipeRefreshLayout
        .OnRefreshListener, UnregisteredApplyRvAdapter.OnDeliteItemListener {
    private SwipeRefreshLayout mSrlApplyList;
    private RecyclerView mRvApplyList;
    private UnregisteredApplyRvAdapter mUnregisteredApplyRvAdapter;
    private LinearLayout mLlEmpty;
    private NormalDialog makeSureDeleteDialog;
    private List<ChuZuWu_AgencySelfReportingList.ContentBean> mApplyList = new ArrayList<>();
    private String agencyId;

    public static UnregisteredApplyListFragment newInstance(String  agencyId) {
        UnregisteredApplyListFragment mUnregisteredApplyListFragment = new UnregisteredApplyListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("agencyId", agencyId);
        mUnregisteredApplyListFragment.setArguments(bundle);
        return mUnregisteredApplyListFragment;
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_unregistered_apply_list;
    }

    @Override
    public void initFragmentView(View view, Bundle savedInstanceState) {
        mLlEmpty = (LinearLayout) view.findViewById(R.id.ll_empty);
        mSrlApplyList = (SwipeRefreshLayout) view.findViewById(R.id.srl_apply_list);
        mRvApplyList = (RecyclerView) view.findViewById(R.id.rv_apply_list);

        mUnregisteredApplyRvAdapter = new UnregisteredApplyRvAdapter(getActivity(), mApplyList);
        mUnregisteredApplyRvAdapter.setOnDeliteItemListener(this);
        mRvApplyList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvApplyList.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        mRvApplyList.setHasFixedSize(true);
        mRvApplyList.setAdapter(mUnregisteredApplyRvAdapter);

        mSrlApplyList.setColorSchemeResources(R.color.bg_black);
        mSrlApplyList.setProgressViewOffset(false, 0, AppUtil.dp2px(24));
    }

    @Override
    public void initFragmentVariables() {
        agencyId = getArguments().getString("agencyId");
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void initFragmentNet() {
        mSrlApplyList.setRefreshing(true);
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.TaskID, "1");
        param.put(TempConstants.PageSize, "100");
        param.put(TempConstants.PageIndex, "0");
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_INTERMEDIARY, "ChuZuWu_AgencySelfReportingList", param)
                .setBeanType(ChuZuWu_AgencySelfReportingList.class)
                .setCallBack(new WebServiceCallBack<ChuZuWu_AgencySelfReportingList>() {
                    @Override
                    public void onSuccess(ChuZuWu_AgencySelfReportingList bean) {
                        mSrlApplyList.setRefreshing(false);
                        mApplyList = bean.getContent();
                        mLlEmpty.setVisibility(mApplyList.size() > 0 ? View.GONE : View.VISIBLE);
                        mUnregisteredApplyRvAdapter.setData(mApplyList);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        mSrlApplyList.setRefreshing(false);
                    }
                }).build().execute();
    }


    @Override
    public void initFragmentData() {
        mSrlApplyList.setOnRefreshListener(UnregisteredApplyListFragment.this);
    }

    @Override
    public void setFragmentData() {

    }


    @Override
    public void onRefresh() {
        initFragmentNet();
    }

    @Override
    public void onDeliteItem(final String listId, final int position) {
        makeSureDeleteDialog = DialogUtil.getDoubleDialog(getActivity(), "确定要删除该项？", "取消", "确定");
        makeSureDeleteDialog.setOnBtnClickL(new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                makeSureDeleteDialog.dismiss();
            }
        }, new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                makeSureDeleteDialog.dismiss();
                uploadDelete(listId, position);
            }
        });
        makeSureDeleteDialog.show();


    }

    private void uploadDelete(String listId, final int position) {
        setProgressDialog(true);
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.TaskID, "1");
        param.put("LISTID", listId);
        param.put("OUTREPORTERROLE", "1");
        param.put("OUTOPERATOR", DataManager.getUserId());
        param.put("OUTOPERATORPHONE", DataManager.getPhone());
        param.put("OUTOPERATUNIT", agencyId);

        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_INTERMEDIARY, "ChuZuWu_AgencySelfReportingOut", param)
                .setBeanType(ChuZuWu_LKSelfReportingOut.class)
                .setCallBack(new WebServiceCallBack<ChuZuWu_LKSelfReportingOut>() {
                    @Override
                    public void onSuccess(ChuZuWu_LKSelfReportingOut bean) {
                        setProgressDialog(false);
                        mUnregisteredApplyRvAdapter.deleteItem(position);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build().execute();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshUnregisteredList(RefreshUnregisteredList event) {
        initFragmentNet();
    }

}
