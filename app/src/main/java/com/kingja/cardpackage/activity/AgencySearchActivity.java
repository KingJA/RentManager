package com.kingja.cardpackage.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kingja.cardpackage.Event.GetBindAgencysEvent;
import com.kingja.cardpackage.Event.GetUnbindAgencysEvent;
import com.kingja.cardpackage.base.BaseActivity;
import com.kingja.cardpackage.db.DbDaoXutils3;
import com.kingja.cardpackage.entiy.Agency_List;
import com.kingja.cardpackage.entiy.Basic_PaiChuSuo_Kj;
import com.kingja.cardpackage.entiy.Basic_XingZhengQuHua_Kj;
import com.kingja.cardpackage.fragment.AgencysFragment;
import com.kingja.cardpackage.util.GoUtil;
import com.kingja.cardpackage.util.ToastUtil;
import com.kingja.ui.popupwindow.BaseTopPop;
import com.tdr.wisdome.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:中介查询
 * Create Time:2017/4/11 9:16
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class AgencySearchActivity extends BaseActivity implements View.OnClickListener, CompoundButton
        .OnCheckedChangeListener {

    private List<Basic_XingZhengQuHua_Kj> areas = new ArrayList<>();
    private List<Basic_PaiChuSuo_Kj> policeStations = new ArrayList<>();
    private BaseTopPop areasPop;
    private BaseTopPop policeStationsPop;
    private String xqcode;
    private String pcscode;
    private String keyword;
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
    private AgencysFragment bindedAgencysFragment;
    private AgencysFragment unBindedAgencysFragment;
    private FragmentManager mFragmentManager;


    @Override
    protected void initVariables() {
        EventBus.getDefault().register(this);
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
        return R.layout.activity_agency_search;
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
        mFragmentManager = getSupportFragmentManager();

    }

    @Override
    protected void initNet() {
    }

    @Override
    protected void initData() {
        mRlPoliceBack.setOnClickListener(this);
        mTvPoliceSearch.setOnClickListener(this);
        mLlPoliceXq.setOnClickListener(this);
        mLlPolicePcs.setOnClickListener(this);
        initAreaPop();
        initPoliceStationPop();


        mCbBind.setOnCheckedChangeListener(this);
    }

    @Override
    protected void setData() {

    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        mCbBind.setChecked(true);
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
                if (mCbBind.isChecked()) {
                    bindedAgencysFragment.searchAgencys(xqcode, pcscode, keyword);
                } else {
                    unBindedAgencysFragment.searchAgencys(xqcode, pcscode, keyword);
                }

                break;

        }
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


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int isBind = isChecked ? 1 : 0;
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (isChecked) {
            if (bindedAgencysFragment == null) {
                bindedAgencysFragment = AgencysFragment.newInstance(isBind);
                fragmentTransaction.add(R.id.fl_angency_search, bindedAgencysFragment);
            }
            if (unBindedAgencysFragment != null) {
                fragmentTransaction.hide(unBindedAgencysFragment);
            }
            fragmentTransaction.show(bindedAgencysFragment);


        } else {
            if (unBindedAgencysFragment == null) {
                unBindedAgencysFragment = AgencysFragment.newInstance(isBind);
                fragmentTransaction.add(R.id.fl_angency_search, unBindedAgencysFragment);
            }
            if (bindedAgencysFragment != null) {
                fragmentTransaction.hide(bindedAgencysFragment);
            }
            fragmentTransaction.show(unBindedAgencysFragment);
        }
        fragmentTransaction.commit();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void getBindAgencys(GetBindAgencysEvent event) {
        if (bindedAgencysFragment != null) {
            bindedAgencysFragment.getAgencys(0);
        }
    }

    @Subscribe
    public void getUnbindAgencys(GetUnbindAgencysEvent event) {
        if (unBindedAgencysFragment != null) {
            unBindedAgencysFragment.getAgencys(0);
        }
    }
}
