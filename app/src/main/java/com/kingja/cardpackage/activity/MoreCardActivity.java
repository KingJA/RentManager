package com.kingja.cardpackage.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kingja.cardpackage.adapter.HomeCardIconAdapter;
import com.kingja.cardpackage.adapter.HomeCardSelectedAdapter;
import com.kingja.cardpackage.adapter.HomeCardSettingAdapter;
import com.kingja.cardpackage.entiy.Application_List;
import com.kingja.cardpackage.entiy.ErrorResult;
import com.kingja.cardpackage.entiy.User_HomePageApplication;
import com.kingja.cardpackage.net.ThreadPoolTask;
import com.kingja.cardpackage.net.WebServiceCallBack;
import com.kingja.cardpackage.ui.SystemBarTint.FixedGridView;
import com.kingja.cardpackage.util.Constants;
import com.kingja.cardpackage.util.DataManager;
import com.kingja.cardpackage.util.TempConstants;
import com.kingja.recyclerviewhelper.LayoutHelper;
import com.kingja.recyclerviewhelper.RecyclerViewHelper;
import com.tdr.wisdome.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:卡片编辑页面
 * Create Time:2017/7/6 13:28
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class MoreCardActivity extends BackTitleActivity implements HomeCardSelectedAdapter.OnRemoveCardListener,
        HomeCardSettingAdapter.OnAddHomeCardListener {

    private List<User_HomePageApplication.ContentBean> cards;
    private HomeCardIconAdapter mHomeCardIconAdapter;
    private FixedGridView mFgvEditHomeIcons;
    private TextView mTvCardEdit;
    private FixedGridView mFgvServicePerson;
    private FixedGridView mFgvServiceThings;
    private FixedGridView mFgvServicePolice;
    private LinearLayout mLlCardEdit;
    private RecyclerView mRvCardEdit;
    private HomeCardSelectedAdapter mHomeCardSelectedAdapter;
    private LinearLayout mLlCardIcon_edit;
    private HomeCardSettingAdapter mPersonSettingAdapter;
    private HomeCardSettingAdapter mThingsSettingAdapter;
    private HomeCardSettingAdapter mPoliceSettingAdapter;
    private List<Application_List.ContentBean.CARDPROPERTYBean> personCards = new ArrayList<>();
    private List<Application_List.ContentBean.CARDPROPERTYBean> thingsCards = new ArrayList<>();
    private List<Application_List.ContentBean.CARDPROPERTYBean> policeCards = new ArrayList<>();


    @Override
    protected void initVariables() {
        User_HomePageApplication cardResult = (User_HomePageApplication) getIntent().getSerializableExtra
                ("User_HomePageApplication");
        cards = cardResult.getContent();

    }

    @Override
    protected void initContentView() {
        mFgvEditHomeIcons = (FixedGridView) findViewById(R.id.fgv_edit_home_icons);
        mTvCardEdit = (TextView) findViewById(R.id.tv_card_edit);
        mFgvServicePerson = (FixedGridView) findViewById(R.id.fgv_service_person);
        mFgvServiceThings = (FixedGridView) findViewById(R.id.fgv_service_things);
        mFgvServicePolice = (FixedGridView) findViewById(R.id.fgv_service_police);
        mLlCardEdit = (LinearLayout) findViewById(R.id.ll_card_edit);
        mLlCardIcon_edit = (LinearLayout) findViewById(R.id.ll_card_icon_edit);
        mRvCardEdit = (RecyclerView) findViewById(R.id.rv_card_edit);

        mPersonSettingAdapter = new HomeCardSettingAdapter(this, personCards);
        mThingsSettingAdapter = new HomeCardSettingAdapter(this, thingsCards);
        mPoliceSettingAdapter = new HomeCardSettingAdapter(this, policeCards);

        mFgvServicePerson.setAdapter(mPersonSettingAdapter);
        mFgvServiceThings.setAdapter(mThingsSettingAdapter);
        mFgvServicePolice.setAdapter(mPoliceSettingAdapter);
    }

    @Override
    protected int getBackContentView() {
        return R.layout.activity_more_card;
    }

    @Override
    protected void initNet() {
        setProgressDialog(true);
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.TaskID, TempConstants.DEFAULT_TASK_ID);
        param.put(TempConstants.CITYCODE, TempConstants.CURRENT_CITY_CODE);

        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_EMPTY, Constants.Application_List, param)
                .setBeanType(Application_List.class)
                .setCallBack(new WebServiceCallBack<Application_List>() {
                    @Override
                    public void onSuccess(Application_List bean) {
                        setProgressDialog(false);
                        personCards = bean.getContent().get(0)
                                .getCARDPROPERTY();
                        thingsCards = bean.getContent().get(0)
                                .getCARDPROPERTY();
                        policeCards = bean.getContent().get(0)
                                .getCARDPROPERTY();

                        mPersonSettingAdapter.setData(personCards);
                        mThingsSettingAdapter.setData(thingsCards);
                        mPoliceSettingAdapter.setData(policeCards);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build().execute();
    }

    @Override
    protected void initData() {
        mPersonSettingAdapter.setOnAddHomeCardListener(this);
        mThingsSettingAdapter.setOnAddHomeCardListener(this);
        mPoliceSettingAdapter.setOnAddHomeCardListener(this);
        mHomeCardIconAdapter = new HomeCardIconAdapter(this, cards);
        mFgvEditHomeIcons.setAdapter(mHomeCardIconAdapter);
        mTvCardEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTitle("我的应用编辑");
                mLlCardIcon_edit.setVisibility(View.GONE);
                mLlCardEdit.setVisibility(View.VISIBLE);
                setOnRightClickListener(new OnRightClickListener() {
                    @Override
                    public void onRightClick() {
                        mLlCardIcon_edit.setVisibility(View.VISIBLE);
                        mLlCardEdit.setVisibility(View.GONE);
                        setTitle("更多");
                    }
                }, "完成");
            }
        });

        mHomeCardSelectedAdapter = new HomeCardSelectedAdapter(this, cards);
        mHomeCardSelectedAdapter.setOnRemoveCardListener(this);
        new RecyclerViewHelper.Builder(this)
                .setAdapter(mHomeCardSelectedAdapter)
                .setLayoutStyle(LayoutHelper.LayoutStyle.GRID)
                .setColumns(4)
                .setDividerHeight(4)
                .setDividerColor(0xffffffff)
                .setDragable(true)
                .build()
                .attachToRecyclerView(mRvCardEdit);

    }

    @Override
    protected void setData() {
        setTitle("更多");
    }

    public static void goActivity(Context context, User_HomePageApplication bean) {
        Intent intent = new Intent(context, MoreCardActivity.class);
        intent.putExtra("User_HomePageApplication", bean);
        context.startActivity(intent);
    }

    @Override
    public void onRemove(int position, String cardCode) {
        mHomeCardSelectedAdapter.onSwipe(position);
        //刷新三个ListView的状态
        refreshStatus(cardCode, 0);
    }

    private void refreshStatus(String cardCode, int status) {
        switch (cardCode) {
            case "1001":
            case "1002":
            case "1003":
            case "1004":
            case "1005":
            case "1006":
                mPersonSettingAdapter.setHomeCardStatus(cardCode, status);
                break;
            case "2001":
            case "2002":
            case "2003":
            case "2004":
                mThingsSettingAdapter.setHomeCardStatus(cardCode, status);
                break;
            case "3001":
            case "3002":
            case "3003":
            case "3004":
            case "3005":
            case "3006":
                mPoliceSettingAdapter.setHomeCardStatus(cardCode, status);
                break;

        }
    }

    @Override
    public void onAddHomeCard(String cardCode, String cardName) {
        //增加SelectedList
        mHomeCardSelectedAdapter.addCard(cardCode, cardName);
        //刷新三个ListView
        refreshStatus(cardCode, 1);
    }
}
