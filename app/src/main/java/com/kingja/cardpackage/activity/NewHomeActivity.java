package com.kingja.cardpackage.activity;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.kingja.cardpackage.Event.GetCards;
import com.kingja.cardpackage.Event.ReadOneMsg;
import com.kingja.cardpackage.Event.RefreshMsgEvent;
import com.kingja.cardpackage.adapter.HomeCardAdapter;
import com.kingja.cardpackage.base.BaseActivity;
import com.kingja.cardpackage.camera.CustomCameraActivity;
import com.kingja.cardpackage.entiy.ErrorResult;
import com.kingja.cardpackage.entiy.User_HomePageApplication;
import com.kingja.cardpackage.entiy.User_MessageCountForShiMing;
import com.kingja.cardpackage.net.ThreadPoolTask;
import com.kingja.cardpackage.net.WebServiceCallBack;
import com.kingja.cardpackage.ui.SystemBarTint.FixedGridView;
import com.kingja.cardpackage.util.Constants;
import com.kingja.cardpackage.util.DataManager;
import com.kingja.cardpackage.util.DialogUtil;
import com.kingja.cardpackage.util.GoUtil;
import com.kingja.cardpackage.util.TempConstants;
import com.kingja.cardpackage.util.ToastUtil;
import com.kingja.ui.popupwindow.BottomListPop;
import com.tdr.wisdome.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:TODO
 * Create Time:2017/7/6 9:36
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class NewHomeActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener,
        BottomListPop.OnPopItemClickListener {

    private ImageView mIvHomeMenu;
    private ImageView mIvHomeMsg;
    private TextView mTvHomeMsgCount;
    private FixedGridView mFgvHomeCard;
    private HomeCardAdapter mHomeCardAdapter;
    private List<User_HomePageApplication.ContentBean> cards = new ArrayList<>();
    private long firstTime;
    private NormalDialog mAddInfoDialog;

    @Override
    protected void initVariables() {
        EventBus.getDefault().register(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_new_home;
    }

    @Override
    protected void initView() {
        mIvHomeMenu = (ImageView) findViewById(R.id.iv_home_menu);
        mIvHomeMsg = (ImageView) findViewById(R.id.iv_home_msg);
        mTvHomeMsgCount = (TextView) findViewById(R.id.tv_home_msgCount);
        mFgvHomeCard = (FixedGridView) findViewById(R.id.fgv_home_card);
        mHomeCardAdapter = new HomeCardAdapter(this, cards);
        mFgvHomeCard.setAdapter(mHomeCardAdapter);
    }

    @Override
    protected void initNet() {
        getCards();
        getMsg();
    }

    private void getCards() {
        setProgressDialog(true);
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.TaskID, TempConstants.DEFAULT_TASK_ID);
        param.put(TempConstants.CITYCODE, TempConstants.CURRENT_CITY_CODE);

        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_EMPTY, Constants
                        .User_HomePageApplication, param)
                .setBeanType(User_HomePageApplication.class)
                .setCallBack(new WebServiceCallBack<User_HomePageApplication>() {


                    @Override
                    public void onSuccess(User_HomePageApplication bean) {
                        setProgressDialog(false);
                        cards = bean.getContent();
                        mHomeCardAdapter.setData(cards);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build().execute();
    }

    @Override
    protected void initData() {
        mFgvHomeCard.setOnItemClickListener(this);
        mIvHomeMenu.setOnClickListener(this);
        mIvHomeMsg.setOnClickListener(this);
        initBottomListPop();
        initAddInfoDialog();
    }

    @Override
    protected void setData() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!checkInfoCompletely()) {
            return;
        }
        if (id == HomeCardAdapter.LAST_POSITION) {
            MoreCardActivity.goActivity(this, cards);

            return;
        }
        User_HomePageApplication.ContentBean card = (User_HomePageApplication.ContentBean) parent.getItemAtPosition
                (position);
        goCard(this, card.getCARDCODE());
    }

    public static void goCard(Activity context, String cardCode) {
        switch (cardCode) {
            case "1001"://房东申报
                GoUtil.goActivity(context, RentActivity.class);
                break;
            case "1002"://中介申报
                GoUtil.goActivity(context, AgencySearchActivity.class);
                break;
            case "1003"://物业申报
                GoUtil.goActivity(context, PropertyActivity.class);
                break;
            case "1004"://企业申报
                ToastUtil.showToast("企业申报未开放");
                break;
            case "1005"://委托申报
                GoUtil.goActivity(context, AgentActivity.class);
                break;
            case "1006"://我的住房
                GoUtil.goActivity(context, HouseActivity.class);
                break;
            case "2001"://手环申领
                ToastUtil.showToast("手环申领未开放");
                break;
            case "2002"://电动车预登记
                ToastUtil.showToast("电动车预登记未开放");
                break;
            case "2003"://防盗芯片更新
                ToastUtil.showToast("防盗芯片更新未开放");
                break;
            case "2004"://房源登记
                ToastUtil.showToast("房源登记未开放");
                break;
            case "3001"://民警查询
                GoUtil.goActivity(context, PoliceSearchActivity.class);
                break;
            case "3002"://预约办证
                ToastUtil.showToast("预约办证未开放");
                break;
            case "3003"://在线咨询
                ToastUtil.showToast("在线咨询未开放");
                break;
            case "3004"://线索举报
                ToastUtil.showToast("线索举报未开放");
                break;
            case "3005"://网上通报
                ToastUtil.showToast("网上通报未开放");
                break;
            case "3006"://防范宣传
                ToastUtil.showToast("防范宣传未开放");
                break;

        }
    }

    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if ((secondTime - firstTime) > 2000) {
            Toast.makeText(this, "长按两次退出", Toast.LENGTH_SHORT).show();
            firstTime = secondTime;
        } else {
            finish();
            System.exit(0);
        }
    }

    private void initBottomListPop() {
        mBottomListPop = new BottomListPop(mIvHomeMenu, this, Arrays.asList("完善资料", "修改密码", "退出登录"));
        mBottomListPop.setOnPopItemClickListener(this);
    }

    private BottomListPop mBottomListPop;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_home_msg:
                if (!checkInfoCompletely()) {
                    return;
                }
                GoUtil.goActivity(this, MsgActivity.class);
                break;
            case R.id.iv_home_menu:
                mBottomListPop.showPopupWindow();
                break;

        }
    }

    @Override
    public void onPopItemClick(int position, String tag) {
        switch (position) {
            case 0://完善资料
                if (TextUtils.isEmpty(DataManager.getToken())) {
                    GoUtil.goActivityAndFinish(this, LoginActivity.class);
                    break;
                }
                GoUtil.goActivity(NewHomeActivity.this, PerfectInfoActivity.class);
                break;
            case 1://修改密码
                if (TextUtils.isEmpty(DataManager.getToken())) {
                    GoUtil.goActivityAndFinish(this, LoginActivity.class);
                    break;
                }
                GoUtil.goActivity(NewHomeActivity.this, ModifyPasswordActivity.class);
                break;
            case 2://退出登录
                makeSureQuit();
                break;

        }
    }

    private void makeSureQuit() {
        final NormalDialog quitDialog = DialogUtil.getDoubleDialog(this, "确定要退出应用？", "取消", "确定");
        quitDialog.setOnBtnClickL(new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                quitDialog.dismiss();
            }
        }, new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                quitDialog.dismiss();
                GoUtil.goActivityAndFinish(NewHomeActivity.this, LoginActivity.class);
                loginout();
                DataManager.putToken("");
                DataManager.putIdCard("");
                DataManager.putLastPage(-1);
            }
        });
        quitDialog.show();
    }

    private boolean checkInfoCompletely() {
        if (TextUtils.isEmpty(DataManager.getToken())) {
            GoUtil.goActivityAndFinish(this, LoginActivity.class);
            return false;
        }
        if (TextUtils.isEmpty(DataManager.getIdentitycard())) {
            mAddInfoDialog.show();
            return false;
        }
        return true;
    }

    private void initAddInfoDialog() {
        mAddInfoDialog = DialogUtil.getDoubleDialog(NewHomeActivity.this, "资料不完整，前去完善资料", "取消", "确定");
        mAddInfoDialog.setOnBtnClickL(new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                mAddInfoDialog.dismiss();
            }
        }, new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                mAddInfoDialog.dismiss();
                GoUtil.goActivity(NewHomeActivity.this, PerfectInfoActivity.class);
            }
        });
    }

    private void loginout() {
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.TaskID, TempConstants.DEFAULT_TASK_ID);
        param.put("USERSID", DataManager.getUserId());
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.User_LogoffForShiMing, Constants
                        .User_LogoffForShiMing, param)
                .setBeanType(Object.class)
                .setCallBack(new WebServiceCallBack<Object>() {
                    @Override
                    public void onSuccess(Object bean) {
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                    }
                }).build().execute();
    }

    private void getMsg() {
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.TaskID, TempConstants.DEFAULT_TASK_ID);
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), "", Constants.User_MessageCountForShiMing, param)
                .setBeanType(User_MessageCountForShiMing.class)
                .setCallBack(new WebServiceCallBack<User_MessageCountForShiMing>() {
                    @Override
                    public void onSuccess(User_MessageCountForShiMing bean) {
                        int unReadCount = bean.getContent().getUnReadCount();
                        mTvHomeMsgCount.setVisibility(unReadCount > 0 ? View.VISIBLE : View.GONE);
                        mTvHomeMsgCount.setText(String.valueOf(unReadCount));
                        if (unReadCount > 99) {
                            mTvHomeMsgCount.setText("..");
                        }
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                    }
                }).build().execute();
    }


    @Subscribe
    public void reGetCards(GetCards bean) {
        getCards();
    }

    @Subscribe
    public void reGetMsgCount(RefreshMsgEvent bean) {
        getMsg();
    }

    @Subscribe
    public void readOneMsg(ReadOneMsg bean) {
        String msgCountStr = mTvHomeMsgCount.getText().toString().trim();
        if ("..".equals(msgCountStr)) {
            getMsg();
            return;
        }
        int msgCount = Integer.valueOf(msgCountStr);
        if (msgCount > 1) {
            mTvHomeMsgCount.setText(String.valueOf(--msgCount));
        }
        if (msgCount == 0) {
            mTvHomeMsgCount.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
