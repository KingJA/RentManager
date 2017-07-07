package com.kingja.cardpackage.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatCheckBox;
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

import com.kingja.cardpackage.adapter.IntermediarySearchAdapter;
import com.kingja.cardpackage.base.BaseActivity;
import com.kingja.cardpackage.db.DbDaoXutils3;
import com.kingja.cardpackage.entiy.Agency_List;
import com.kingja.cardpackage.entiy.Basic_PaiChuSuo_Kj;
import com.kingja.cardpackage.entiy.Basic_XingZhengQuHua_Kj;
import com.kingja.cardpackage.entiy.ErrorResult;
import com.kingja.cardpackage.entiy.LoginInfo;
import com.kingja.cardpackage.entiy.PhoneInfo;
import com.kingja.cardpackage.entiy.User_LogInForKaBao;
import com.kingja.cardpackage.net.ThreadPoolTask;
import com.kingja.cardpackage.net.WebServiceCallBack;
import com.kingja.cardpackage.ui.PullToBottomRecyclerView;
import com.kingja.cardpackage.util.AppInfoUtil;
import com.kingja.cardpackage.util.CheckUtil;
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
 * Description:中介查询
 * Create Time:2017/4/11 9:16
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class IntermediarySearchActivity extends BaseActivity implements View.OnClickListener {

    private List<Basic_XingZhengQuHua_Kj> areas = new ArrayList<>();
    private List<Basic_PaiChuSuo_Kj> policeStations = new ArrayList<>();
    private BaseTopPop areasPop;
    private BaseTopPop policeStationsPop;
    private String xqcode;
    private String pcscode;
    private String keyword;
    private LinearLayout mLlEmpty;
    private SwipeRefreshLayout mSrl;
    private PullToBottomRecyclerView mRv;
    private IntermediarySearchAdapter mIntermediarySearchAdapter;
    private List<Agency_List.ContentBean> polices = new ArrayList<>();

    private RelativeLayout mRlPoliceBack;
    private ImageView mIvTopBack;
    private EditText mEtPoliceKeyword;
    private TextView mTvPoliceSearch;
    private LinearLayout mLlPoliceRoot;
    private LinearLayout mLlPoliceXq;
    private TextView mTvPoliceXq;
    private ImageView mIvPoliceXq;
    private LinearLayout mLlPolicePcs;
    private TextView mTvPolicePcs;
    private ImageView mIvPolicePcs;
    private AppCompatCheckBox mCbBind;


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
        return R.layout.activity_intermediary_search;
    }

    @Override
    protected void initView() {
        mRlPoliceBack = (RelativeLayout) findViewById(R.id.rl_police_back);
        mIvTopBack = (ImageView) findViewById(R.id.iv_top_back);
        mEtPoliceKeyword = (EditText) findViewById(R.id.et_police_keyword);
        mTvPoliceSearch = (TextView) findViewById(R.id.tv_police_search);
        mLlPoliceRoot = (LinearLayout) findViewById(R.id.ll_police_root);
        mLlPoliceXq = (LinearLayout) findViewById(R.id.ll_police_xq);
        mTvPoliceXq = (TextView) findViewById(R.id.tv_police_xq);
        mIvPoliceXq = (ImageView) findViewById(R.id.iv_police_xq);
        mLlPolicePcs = (LinearLayout) findViewById(R.id.ll_police_pcs);
        mTvPolicePcs = (TextView) findViewById(R.id.tv_police_pcs);
        mIvPolicePcs = (ImageView) findViewById(R.id.iv_police_pcs);
        mCbBind = (AppCompatCheckBox) findViewById(R.id.cb_bind);

        mLlEmpty = (LinearLayout) findViewById(R.id.ll_empty);
        mSrl = (SwipeRefreshLayout) findViewById(R.id.srl);
        mRv = (PullToBottomRecyclerView) findViewById(R.id.rv);


        mIntermediarySearchAdapter = new IntermediarySearchAdapter(this, polices);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setHasFixedSize(true);
        mRv.setAdapter(mIntermediarySearchAdapter);


    }

    @Override
    protected void initNet() {
//        cardLogin();
    }

    @Override
    protected void initData() {
        mRlPoliceBack.setOnClickListener(this);
        mTvPoliceSearch.setOnClickListener(this);
        mLlPoliceXq.setOnClickListener(this);
        mLlPolicePcs.setOnClickListener(this);
        initAreaPop();
        initPoliceStationPop();

        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSrl.setRefreshing(false);
            }
        });
    }

    @Override
    protected void setData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_police_xq:
                mIvPoliceXq.setBackgroundResource(R.drawable.spinner_arow_sel);
                areasPop.showPopAsDropDown(mLlPoliceRoot);
                break;
            case R.id.iv_unregistered:
                GoUtil.goActivity(this, UnregisteredApplyActivity.class);
                break;
            case R.id.ll_police_pcs:
                mIvPolicePcs.setBackgroundResource(R.drawable.spinner_arow_sel);
                if (TextUtils.isEmpty(xqcode)) {
                    ToastUtil.showToast("请先选择分局");
                } else if (policeStations.size() == 0) {
                    ToastUtil.showToast("没找到对应派出所数据");
                } else {
                    policeStationsPop.showPopAsDropDown(mLlPoliceRoot);
                }
                break;
            case R.id.rl_police_back:
                finish();
                break;
            case R.id.tv_police_search:
                keyword = mEtPoliceKeyword.getText().toString().trim();
                if (CheckUtil.checkEmpty(keyword, "请输入中介名称")
                        && CheckUtil.checkEmpty(xqcode, "请选择所属辖区")
                        && CheckUtil.checkEmpty(pcscode, "请选择派出所")) {
                    loadIntermediarys();
                }
                break;

        }
    }

    private int isBind;

    private void loadIntermediarys() {
        mSrl.setRefreshing(true);
        isBind = mCbBind.isChecked() ? 1 : 0;
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.TaskID, TempConstants.DEFAULT_TASK_ID);
        param.put("XQCODE", xqcode);
        param.put("PCSCODE", pcscode);
        param.put("ADDRESS", keyword);
        param.put("ISBUNG", isBind);
        param.put("PAGESIZE", 100);
        param.put("PAGEINDEX", 0);
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_POLICE_SEARCH, Constants.Agency_List,
                        param)
                .setBeanType(Agency_List.class)
                .setCallBack(new WebServiceCallBack<Agency_List>() {
                    @Override
                    public void onSuccess(Agency_List bean) {
                        mSrl.setRefreshing(false);
                        polices = bean.getContent();
                        mLlEmpty.setVisibility(polices.size() > 0 ? View.GONE : View.VISIBLE);
                        mIntermediarySearchAdapter.addData(polices);
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
                mTvPolicePcs.setText("派出所");
                pcscode = "";
                xqcode = basic_xingZhengQuHua_kj.getDMZM();
                mTvPoliceXq.setText(basic_xingZhengQuHua_kj.getDMMC());

            }
        };
        areasPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mIvPoliceXq.setBackgroundResource(R.drawable.spinner_arow_nor);
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
                pcscode = basic_PaiChuSuo_Kj.getDMZM();
                mTvPolicePcs.setText(basic_PaiChuSuo_Kj.getDMMC());
            }
        };
        policeStationsPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mIvPolicePcs.setBackgroundResource(R.drawable.spinner_arow_nor);
            }
        });
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
        mInfo.setSOFTTYPE(4);
        mInfo.setCARDTYPE(Constants.CARD_TYPE_POLICE_SEARCH);
        mInfo.setPHONEINFO(phoneInfo);
        mInfo.setSOFTVERSION(AppInfoUtil.getVersionName());
        mInfo.setTaskID("1");
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_POLICE_SEARCH, Constants
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
