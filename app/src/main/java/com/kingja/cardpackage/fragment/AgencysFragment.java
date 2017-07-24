package com.kingja.cardpackage.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;

import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.kingja.cardpackage.adapter.AgencySearchAdapter;
import com.kingja.cardpackage.base.BaseFragment;
import com.kingja.cardpackage.entiy.Agency_List;
import com.kingja.cardpackage.entiy.ErrorResult;
import com.kingja.cardpackage.entiy.RentBean;
import com.kingja.cardpackage.entiy.User_AgencyBung;
import com.kingja.cardpackage.net.ThreadPoolTask;
import com.kingja.cardpackage.net.WebServiceCallBack;
import com.kingja.cardpackage.ui.PullToBottomRecyclerView;
import com.kingja.cardpackage.util.Constants;
import com.kingja.cardpackage.util.DataManager;
import com.kingja.cardpackage.util.DialogUtil;
import com.kingja.cardpackage.util.TempConstants;
import com.kingja.cardpackage.util.ToastUtil;
import com.kingja.recyclerviewhelper.LayoutHelper;
import com.kingja.recyclerviewhelper.RecyclerViewHelper;
import com.tdr.wisdome.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:TODO
 * Create Time:2017/7/24 15:14
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class AgencysFragment extends BaseFragment implements AgencySearchAdapter.OnSetBindListener {
    private LinearLayout mLlEmpty;
    private SwipeRefreshLayout mSrl;
    private PullToBottomRecyclerView mRv;
    private int isBind;
    private String xqcode;
    private String pcscode;
    private String agencyName;
    private List<Agency_List.ContentBean> agencys=new ArrayList<>();
    private AgencySearchAdapter mAgencySearchAdapter;
    private int currentPage;
    private boolean hasMore;

    public static AgencysFragment newInstance(int isBind) {
        AgencysFragment mAgencysFragment = new AgencysFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("isBind", isBind);
        mAgencysFragment.setArguments(bundle);
        return mAgencysFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.single_rv;
    }

    @Override
    public void initFragmentView(View view, Bundle savedInstanceState) {
        mLlEmpty = (LinearLayout) view.findViewById(R.id.ll_empty);
        mSrl = (SwipeRefreshLayout) view.findViewById(R.id.srl);
        mRv = (PullToBottomRecyclerView) view.findViewById(R.id.rv);
        mAgencySearchAdapter = new AgencySearchAdapter(getActivity(), agencys);
    }

    @Override
    public void initFragmentVariables() {
        isBind = getArguments().getInt("isBind");
    }

    @Override
    public void initFragmentNet() {

    }

    @Override
    public void initFragmentData() {
        mRv.setOnPullToBottomListener(new PullToBottomRecyclerView.OnPullToBottomListener() {
            @Override
            public void onPullToBottom() {
                if (mSrl.isRefreshing()) {
                    return;
                }
                if (hasMore) {
                    currentPage++;
                    getAgencys(currentPage);
                } else {
                    ToastUtil.showToast("到底啦");
                }
            }
        });
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSrl.setRefreshing(false);
            }
        });
        mAgencySearchAdapter.setOnSetBindListener(this);
        new RecyclerViewHelper.Builder(getActivity())
                .setAdapter(mAgencySearchAdapter)
                .setLayoutStyle(LayoutHelper.LayoutStyle.VERTICAL_LIST)
                .setDividerHeight(1)
                .setDividerColor(0xffbebebe)
                .build()
                .attachToRecyclerView(mRv);
    }

    @Override
    public void setFragmentData() {

    }

    public void searchAgencys(String xqcode, String pcscode, String agencyName) {
        this.xqcode = xqcode;
        this.pcscode = pcscode;
        this.agencyName = agencyName;
        getAgencys(0);
    }

    private void getAgencys(final int pageIndex) {
        mSrl.setRefreshing(true);
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.TaskID, TempConstants.DEFAULT_TASK_ID);
        param.put("XQCODE", xqcode);
        param.put("PCSCODE", pcscode);
        param.put("AGENCYNAME", agencyName);
        param.put("ISBUNG", isBind);
        param.put("PAGESIZE", 50);
        param.put("PAGEINDEX", pageIndex);
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_POLICE_SEARCH, Constants.Agency_List,
                        param)
                .setBeanType(Agency_List.class)
                .setCallBack(new WebServiceCallBack<Agency_List>() {
                    @Override
                    public void onSuccess(Agency_List bean) {
                        mSrl.setRefreshing(false);
                        agencys = bean.getContent();

                        if (pageIndex == 0) {
                            mAgencySearchAdapter.reset();
                        }
                        hasMore = agencys.size() == Constants.PAGE_SIZE;
                        mLlEmpty.setVisibility(agencys.size() > 0 ? View.GONE : View.VISIBLE);
                        mAgencySearchAdapter.addData(agencys);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        mSrl.setRefreshing(false);
                    }
                }).build().execute();
    }

    @Override
    public void setBind(final int position, int isBind, final String agencyId) {
        final int bindStatus=isBind==1?0:1;
        final NormalDialog setBindDialog = DialogUtil.getDoubleDialog(getActivity(), "确定要进行" + (bindStatus == 1 ? "绑定?" :
                "解绑?"), "取消", "确定");
        setBindDialog.setOnBtnClickL(new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                setBindDialog.dismiss();
            }
        }, new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                setBindDialog.dismiss();
                doSetBind(position, agencyId, bindStatus);
            }
        });
        setBindDialog.show();
    }

    private void doSetBind(final int position, String agencyId, final int bindStatus) {
        mSrl.setRefreshing(true);
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.TaskID, TempConstants.DEFAULT_TASK_ID);
        param.put("AGENCYID", agencyId);
        param.put("BUNGSTATE", bindStatus);
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_EMPTY, Constants.User_AgencyBung,
                        param)
                .setBeanType(User_AgencyBung.class)
                .setCallBack(new WebServiceCallBack<User_AgencyBung>() {
                    @Override
                    public void onSuccess(User_AgencyBung bean) {
                        mSrl.setRefreshing(false);
                        ToastUtil.showToast(bindStatus==1?"绑定成功":"解绑成功");
                        mAgencySearchAdapter.removeItem(position);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        mSrl.setRefreshing(false);
                    }
                }).build().execute();
    }
}
