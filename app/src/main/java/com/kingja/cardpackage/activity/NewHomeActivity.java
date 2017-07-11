package com.kingja.cardpackage.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.kingja.cardpackage.adapter.HomeCardAdapter;
import com.kingja.cardpackage.base.BaseActivity;
import com.kingja.cardpackage.entiy.ErrorResult;
import com.kingja.cardpackage.entiy.GetUserMessage;
import com.kingja.cardpackage.entiy.User_HomePageApplication;
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
import com.tdr.wisdome.actvitiy.LoginActivity;
import com.tdr.wisdome.actvitiy.PerfectActivity;

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
    private User_HomePageApplication user_homePageApplication;
    private long firstTime;
    private NormalDialog mAddInfoDialog;

    @Override
    protected void initVariables() {

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
        user_homePageApplication = new User_HomePageApplication();
        user_homePageApplication.setContent(cards);

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
            ToastUtil.showToast("更多");
            user_homePageApplication.setContent(mHomeCardAdapter.getData());
            MoreCardActivity.goActivity(this, user_homePageApplication);

            return;
        }
        User_HomePageApplication.ContentBean card = (User_HomePageApplication.ContentBean) parent.getItemAtPosition
                (position);
        switch (card.getCARDCODE()) {
            case "1001"://房东申报
//                GoUtil.goActivity(this,RentActivity.class);
                GoUtil.goActivity(this, PoliceSearchActivity.class);
//                GoUtil.goActivity(this, IntermediarySearchActivity.class);
                break;
            case "1002"://中介申报
                GoUtil.goActivity(this, IntermediaryActivity.class);
                break;
            case "1003"://物业申报
                ToastUtil.showToast("物业申报未开放");
                break;
            case "1004"://企业申报
                ToastUtil.showToast("企业申报未开放");
                break;
            case "1005"://委托申报
                GoUtil.goActivity(this, AgentActivity.class);
                break;
            case "1006"://我的住房
                GoUtil.goActivity(this, HouseActivity.class);
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
                ToastUtil.showToast("民警查询未开放");
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
                GoUtil.goActivity(this, AlarmMineActivity.class);
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
                GoUtil.goActivity(NewHomeActivity.this, PerfectActivity.class);
                break;
            case 1://修改密码
                if (TextUtils.isEmpty(DataManager.getToken())) {
                    GoUtil.goActivityAndFinish(this, LoginActivity.class);
                    break;
                }
                GoUtil.goActivity(NewHomeActivity.this, EditPwdActivity.class);
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
                DataManager.putToken("");
                DataManager.putLastPage(-1);
                GoUtil.goActivityAndFinish(NewHomeActivity.this, LoginActivity.class);
            }
        });
        quitDialog.show();
    }

    private boolean checkInfoCompletely() {
        if (TextUtils.isEmpty(DataManager.getToken())) {
            GoUtil.goActivityAndFinish(this, LoginActivity.class);
            return false;
        }
        if (TextUtils.isEmpty(DataManager.getIdCard())) {
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
                GoUtil.goActivity(NewHomeActivity.this, PerfectActivity.class);
                mAddInfoDialog.dismiss();
            }
        });
    }

    private void getMsg() {
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.TaskID, TempConstants.DEFAULT_TASK_ID);
        param.put(TempConstants.PageIndex, 0);
        param.put(TempConstants.PageSize, TempConstants.DEFAULT_PAGE_SIZE);
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), "", Constants.GetUserMessage, param)
                .setBeanType(GetUserMessage.class)
                .setCallBack(new WebServiceCallBack<GetUserMessage>() {
                    @Override
                    public void onSuccess(GetUserMessage bean) {
                        mTvHomeMsgCount.setVisibility(isHasNewMsg(bean) ? View.VISIBLE : View.GONE);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                    }
                }).build().execute();
    }

    private boolean isHasNewMsg(GetUserMessage bean) {
        List<GetUserMessage.ContentBean> msgList = bean.getContent();
        for (GetUserMessage.ContentBean msg : msgList) {
            if (msg.getIsRead() == 0) {
                return true;
            }
        }
        return false;
    }
}
