package com.kingja.cardpackage.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kingja.cardpackage.adapter.IntermediaryAdapter;
import com.kingja.cardpackage.adapter.RvAdaper;
import com.kingja.cardpackage.base.BaseActivity;
import com.kingja.cardpackage.db.DbDaoXutils3;
import com.kingja.cardpackage.entiy.Basic_JuWeiHui_Kj;
import com.kingja.cardpackage.entiy.Basic_PaiChuSuo_Kj;
import com.kingja.cardpackage.entiy.Basic_XingZhengQuHua_Kj;
import com.kingja.cardpackage.entiy.ChuZuWu_AgencyInquire;
import com.kingja.cardpackage.entiy.ErrorResult;
import com.kingja.cardpackage.entiy.LoginInfo;
import com.kingja.cardpackage.entiy.PhoneInfo;
import com.kingja.cardpackage.entiy.RentBean;
import com.kingja.cardpackage.entiy.User_LogInForKaBao;
import com.kingja.cardpackage.net.ThreadPoolTask;
import com.kingja.cardpackage.net.WebServiceCallBack;
import com.kingja.cardpackage.ui.PullToBottomRecyclerView;
import com.kingja.cardpackage.util.AppInfoUtil;
import com.kingja.cardpackage.util.Constants;
import com.kingja.cardpackage.util.DataManager;
import com.kingja.cardpackage.util.GoUtil;
import com.kingja.cardpackage.util.PhoneUtil;
import com.kingja.cardpackage.util.TempConstants;
import com.kingja.cardpackage.util.ToastUtil;
import com.kingja.ui.popupwindow.BaseTopPop;
import com.tdr.wisdome.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:中介
 * Create Time:2017/4/11 9:16
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class AgencyActivity extends BaseActivity implements View.OnClickListener {

    private List<Basic_XingZhengQuHua_Kj> areas = new ArrayList<>();
    private List<Basic_PaiChuSuo_Kj> policeStations = new ArrayList<>();
    private List<Basic_JuWeiHui_Kj> juweihuis = new ArrayList<>();
    private BaseTopPop areasPop;
    private BaseTopPop policeStationsPop;
    private BaseTopPop juweihuisPop;
    private RelativeLayout mRlIntermediaryBack;
    private ImageView mIvTopBack;
    private EditText mEtIntermediaryKeyword;
    private TextView mTvIntermediarySearch;
    private LinearLayout mLlIntermediaryRoot;
    private LinearLayout mLlIntermediaryXq;
    private TextView mTvIntermediaryXq;
    private ImageView mIvIntermediaryXq;
    private LinearLayout mLlIntermediaryPcs;
    private TextView mTvIntermediaryPcs;
    private ImageView mIvIntermediaryPcs;
    private LinearLayout mLlIntermediaryJwh;
    private TextView mTvIntermediaryJwh;
    private ImageView mIvIntermediaryJwh;
    private String xqcode;
    private String pcscode;
    private String jwhcode;
    private int currentPage;
    private String address;

    private String xqcodeMore;
    private String pcscodeMore;
    private String jwhcodeMore;
    private String addressMore;


    private LinearLayout mLlEmpty;
    private SwipeRefreshLayout mSrl;
    private PullToBottomRecyclerView mRv;
    private IntermediaryAdapter mIntermediaryAdapter;
    private boolean hasMore;
    private List<RentBean> houses = new ArrayList<>();
    private ImageView mIvUnregistered;


    @Override
    protected void initVariables() {
        areas = DbDaoXutils3.getInstance().selectAll
                (Basic_XingZhengQuHua_Kj.class);
        for (int i = 0; i < areas.size(); i++) {
            if ("330300".equals(areas.get(i).getDMZM())) {
                areas.remove(i);
                break;
            }
        }
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_intermediary;
    }

    @Override
    protected void initView() {
        mRlIntermediaryBack = (RelativeLayout) findViewById(R.id.rl_intermediary_back);
        mIvTopBack = (ImageView) findViewById(R.id.iv_top_back);
        mEtIntermediaryKeyword = (EditText) findViewById(R.id.et_intermediary_keyword);
        mTvIntermediarySearch = (TextView) findViewById(R.id.tv_intermediary_search);
        mLlIntermediaryRoot = (LinearLayout) findViewById(R.id.ll_intermediary_root);
        mLlIntermediaryXq = (LinearLayout) findViewById(R.id.ll_intermediary_xq);
        mTvIntermediaryXq = (TextView) findViewById(R.id.tv_intermediary_xq);
        mIvIntermediaryXq = (ImageView) findViewById(R.id.iv_intermediary_xq);
        mLlIntermediaryPcs = (LinearLayout) findViewById(R.id.ll_intermediary_pcs);
        mTvIntermediaryPcs = (TextView) findViewById(R.id.tv_intermediary_pcs);
        mIvIntermediaryPcs = (ImageView) findViewById(R.id.iv_intermediary_pcs);
        mLlIntermediaryJwh = (LinearLayout) findViewById(R.id.ll_intermediary_jwh);
        mTvIntermediaryJwh = (TextView) findViewById(R.id.tv_intermediary_jwh);
        mIvIntermediaryJwh = (ImageView) findViewById(R.id.iv_intermediary_jwh);
        mIvUnregistered = (ImageView) findViewById(R.id.iv_unregistered);

        mLlEmpty = (LinearLayout) findViewById(R.id.ll_empty);
        mSrl = (SwipeRefreshLayout) findViewById(R.id.srl);
        mRv = (PullToBottomRecyclerView) findViewById(R.id.rv);


        mIntermediaryAdapter = new IntermediaryAdapter(this, houses);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setHasFixedSize(true);
        mRv.setAdapter(mIntermediaryAdapter);


    }

    @Override
    protected void initNet() {
        cardLogin();
    }

    @Override
    protected void initData() {
        mRlIntermediaryBack.setOnClickListener(this);
        mTvIntermediarySearch.setOnClickListener(this);
        mLlIntermediaryXq.setOnClickListener(this);
        mLlIntermediaryPcs.setOnClickListener(this);
        mLlIntermediaryJwh.setOnClickListener(this);
        mIvUnregistered.setOnClickListener(this);
        initAreaPop();
        initPoliceStationPop();
        initJuweihuiPop();

        mRv.setOnPullToBottomListener(new PullToBottomRecyclerView.OnPullToBottomListener() {
            @Override
            public void onPullToBottom() {
                if (mSrl.isRefreshing()) {
                    return;
                }
                if (hasMore) {
                    currentPage++;
                    loadAddress(currentPage);
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
        mIntermediaryAdapter.setOnItemClickListener(new RvAdaper.OnItemClickListener<RentBean>() {
            @Override
            public void onItemClick(RentBean rentBean, int position) {
                PersonApplyActivity.goActivity(AgencyActivity.this, rentBean, Constants.CARD_TYPE_INTERMEDIARY,
                        Constants.ROLE_INTERMEDIARY);
            }
        });
    }

    @Override
    protected void setData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_intermediary_xq:
                mIvIntermediaryXq.setBackgroundResource(R.drawable.spinner_arow_sel);
                areasPop.showPopAsDropDown(mLlIntermediaryRoot);
                break;
            case R.id.iv_unregistered:
                GoUtil.goActivity(this,UnregisteredApplyActivity.class);
                break;
            case R.id.ll_intermediary_pcs:
                mIvIntermediaryPcs.setBackgroundResource(R.drawable.spinner_arow_sel);
                if (TextUtils.isEmpty(xqcode)) {
                    ToastUtil.showToast("请先选择分局");
                } else if (policeStations.size() == 0) {
                    ToastUtil.showToast("没找到对应派出所数据");
                } else {
                    policeStationsPop.showPopAsDropDown(mLlIntermediaryRoot);
                }
                break;
            case R.id.ll_intermediary_jwh:
                mIvIntermediaryJwh.setBackgroundResource(R.drawable.spinner_arow_sel);
                if (TextUtils.isEmpty(pcscode)) {
                    ToastUtil.showToast("请先选择派出所");
                } else if (juweihuis.size() == 0) {
                    ToastUtil.showToast("没找到对应居委会数据");
                } else {
                    juweihuisPop.showPopAsDropDown(mLlIntermediaryRoot);
                }
                break;
            case R.id.rl_intermediary_back:
                finish();
                break;
            case R.id.tv_intermediary_search:
                address = mEtIntermediaryKeyword.getText().toString().trim();
                currentPage = 0;
                xqcodeMore = xqcode;
                pcscodeMore = pcscode;
                jwhcodeMore = jwhcode;
                addressMore = address;
                loadAddress(0);
                break;

        }
    }

    private void loadAddress(final int page) {
        mSrl.setRefreshing(true);
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.TaskID, TempConstants.DEFAULT_TASK_ID);
        param.put(TempConstants.PageIndex, page);
        param.put(TempConstants.PageSize, 20);
        param.put("Address", addressMore);
        param.put("XQCODE", xqcode);
        param.put("PCSCODE", pcscodeMore);
        param.put("JWHCODE", jwhcodeMore);
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_INTERMEDIARY, "ChuZuWu_AgencyInquire",
                        param)
                .setBeanType(ChuZuWu_AgencyInquire.class)
                .setCallBack(new WebServiceCallBack<ChuZuWu_AgencyInquire>() {
                    @Override
                    public void onSuccess(ChuZuWu_AgencyInquire bean) {
                        mSrl.setRefreshing(false);
                        houses = bean.getContent();
                        if (page == 0) {
                            mIntermediaryAdapter.reset();
                        }
                        hasMore = houses.size() == 20;
                        mLlEmpty.setVisibility(houses.size() > 0 ? View.GONE : View.VISIBLE);
                        mIntermediaryAdapter.addData(houses);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        mSrl.setRefreshing(false);
                    }
                }).build().execute();
    }

    private void initAreaPop() {
        areasPop = new BaseTopPop<Basic_XingZhengQuHua_Kj>(this, areas) {
            @Override
            protected void fillLvData(List<Basic_XingZhengQuHua_Kj> list, int position, TextView tv) {
                tv.setText(list.get(position).getDMMC());
            }

            @Override
            protected void onItemSelect(Basic_XingZhengQuHua_Kj basic_xingZhengQuHua_kj) {
                mTvIntermediaryPcs.setText("派出所");
                pcscode = "";
                mTvIntermediaryJwh.setText("居委会");
                jwhcode = "";
                xqcode = basic_xingZhengQuHua_kj.getDMZM();
                mTvIntermediaryXq.setText(basic_xingZhengQuHua_kj.getDMMC());

            }
        };
        areasPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mIvIntermediaryXq.setBackgroundResource(R.drawable.spinner_arow_nor);
                policeStations = DbDaoXutils3.getInstance().selectAllWhereLike(Basic_PaiChuSuo_Kj
                        .class, "SANSHIYOUDMZM", xqcode + "%");
                Log.e(TAG, "policeStations: " + policeStations.size());
                initPoliceStationPop();
            }
        });
    }

    private void initPoliceStationPop() {
        policeStationsPop = new BaseTopPop<Basic_PaiChuSuo_Kj>(this, policeStations) {
            @Override
            protected void fillLvData(List<Basic_PaiChuSuo_Kj> list, int position, TextView tv) {
                tv.setText(list.get(position).getDMMC());
            }

            @Override
            protected void onItemSelect(Basic_PaiChuSuo_Kj basic_PaiChuSuo_Kj) {
                jwhcode = "";
                mTvIntermediaryJwh.setText("居委会");
                pcscode = basic_PaiChuSuo_Kj.getDMZM();
                mTvIntermediaryPcs.setText(basic_PaiChuSuo_Kj.getDMMC());
                juweihuis = DbDaoXutils3.getInstance().selectAllWhere(Basic_JuWeiHui_Kj.class,
                        "FDMZM", pcscode);
                initJuweihuiPop();
            }
        };
        policeStationsPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mIvIntermediaryPcs.setBackgroundResource(R.drawable.spinner_arow_nor);
            }
        });
    }

    private void initJuweihuiPop() {
        juweihuisPop = new BaseTopPop<Basic_JuWeiHui_Kj>(this, juweihuis) {
            @Override
            protected void fillLvData(List<Basic_JuWeiHui_Kj> list, int position, TextView tv) {

                tv.setText(list.get(position).getDMMC());
            }

            @Override
            protected void onItemSelect(Basic_JuWeiHui_Kj basic_JuWeiHui_Kj) {
                jwhcode = basic_JuWeiHui_Kj.getDMZM();
                mTvIntermediaryJwh.setText(basic_JuWeiHui_Kj.getDMMC());
            }
        };
        juweihuisPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mIvIntermediaryJwh.setBackgroundResource(R.drawable.spinner_arow_nor);
            }
        });
    }

    private void cardLogin() {
        setProgressDialog(true);
        LoginInfo mInfo = new LoginInfo();
        PhoneInfo phoneInfo = new PhoneUtil(this).getInfo();
        mInfo.setTaskID("1");
        mInfo.setREALNAME(DataManager.getRealName());
        mInfo.setIDENTITYCARD(DataManager.getIdentitycard());
        mInfo.setPHONENUM(DataManager.getPhone());
        mInfo.setSOFTVERSION(AppInfoUtil.getVersionName());
        mInfo.setSOFTTYPE(4);
        mInfo.setCARDTYPE(Constants.CARD_TYPE_INTERMEDIARY);
        mInfo.setPHONEINFO(phoneInfo);
        mInfo.setSOFTVERSION(AppInfoUtil.getVersionName());
        mInfo.setTaskID("1");
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_INTERMEDIARY, Constants
                        .User_LogInForKaBao, mInfo)
                .setBeanType(User_LogInForKaBao.class)
                .setCallBack(new WebServiceCallBack<User_LogInForKaBao>() {
                    @Override
                    public void onSuccess(User_LogInForKaBao bean) {
                        setProgressDialog(false);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                        finish();
                        ToastUtil.showToast("卡包登录失败");
                    }
                }).build().execute();
    }

}
