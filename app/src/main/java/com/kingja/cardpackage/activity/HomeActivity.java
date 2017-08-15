package com.kingja.cardpackage.activity;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.kingja.cardpackage.Event.ClearMsgEvent;
import com.kingja.cardpackage.Event.RefreshMsgEvent;
import com.kingja.cardpackage.adapter.HomeAdapter;
import com.kingja.cardpackage.base.BaseActivity;
import com.kingja.cardpackage.entiy.ErrorResult;
import com.kingja.cardpackage.entiy.GetUserMessage;
import com.kingja.cardpackage.net.ThreadPoolTask;
import com.kingja.cardpackage.net.WebServiceCallBack;
import com.kingja.cardpackage.util.Constants;
import com.kingja.cardpackage.util.DataManager;
import com.kingja.cardpackage.util.DialogUtil;
import com.kingja.cardpackage.util.GoUtil;
import com.kingja.cardpackage.util.TempConstants;
import com.kingja.ui.popupwindow.BottomListPop;
import com.tdr.wisdome.R;
import com.tdr.wisdome.actvitiy.LoginActivity;
import com.tdr.wisdome.actvitiy.PerfectActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description：TODO
 * Create Time：2016/10/21 10:19
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class HomeActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener,
        BottomListPop.OnPopItemClickListener {
    private String mLargeTexts[] = {"我是房东", "我是租客", "我是管理员", "我的中介"};
    private String mSmallTexts[] = {"我的出租房", "我的住房", "出租房代管", "出租房中介"};
    private int mHouseImgs[] = {R.drawable.home_rent, R.drawable.home_house, R.drawable.home_agent, R.drawable
            .home_intermediary};
    private RelativeLayout mRlHomeMenu;
    private RelativeLayout mRlHomeMsg;
    private TextView mTvHomeCount;
    private ListView mLvHomeMenu;
    private HomeAdapter mHomeAdapter;
    private int mLvHeight;
    private long firstTime;
    private BottomListPop mBottomListPop;


    @Override
    protected void initVariables() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        mRlHomeMenu = (RelativeLayout) findViewById(R.id.rl_home_menu);
        mRlHomeMsg = (RelativeLayout) findViewById(R.id.rl_home_msg);
        mTvHomeCount = (TextView) findViewById(R.id.tv_home_count);
        mLvHomeMenu = (ListView) findViewById(R.id.lv_home_menu);
        getLvHeight();
        initAddInfoDialog();
        initBottomListPop();
    }

    private void initBottomListPop() {
        mBottomListPop = new BottomListPop(mRlHomeMenu, this, Arrays.asList("完善资料", "修改密码", "退出登录"));
        mBottomListPop.setOnPopItemClickListener(this);
    }

    private void getLvHeight() {
        ViewTreeObserver vto2 = mLvHomeMenu.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mLvHomeMenu.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mLvHeight = mLvHomeMenu.getHeight();
                mHomeAdapter = new HomeAdapter(HomeActivity.this, mLargeTexts, mSmallTexts, mHouseImgs, mLvHeight);
                mLvHomeMenu.setAdapter(mHomeAdapter);
            }
        });
    }

    @Override
    protected void initNet() {
        getMsg(0);
    }

    @Override
    protected void initData() {
        mLvHomeMenu.setOnItemClickListener(this);
        mRlHomeMenu.setOnClickListener(this);
        mRlHomeMsg.setOnClickListener(this);
    }

    @Override
    protected void setData() {
        autoToLastPage();
    }

    private void autoToLastPage() {
        switch (DataManager.getLastPage()) {
            case 0://我的出租房
                GoUtil.goActivity(HomeActivity.this, RentActivity.class);
                break;
            case 1://我的住房
                GoUtil.goActivity(HomeActivity.this, HouseActivity.class);
                break;
            case 2://代管出租
                GoUtil.goActivity(HomeActivity.this, AgentActivity.class);
                break;
            case 3://出租房中介
                GoUtil.goActivity(HomeActivity.this, AgencyActivity.class);
                break;
            default:
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_home_menu:
                mBottomListPop.showPopupWindow();
                break;
            case R.id.rl_home_msg:
                GoUtil.goActivity(this, MsgActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    protected boolean setStatusBar() {
        return false;
    }


    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!checkInfoCompletely()) {
            return;
        }
        switch (position) {
            case 0://我的出租房
                GoUtil.goActivity(HomeActivity.this, RentActivity.class);
                break;
            case 1://我的住房
                GoUtil.goActivity(HomeActivity.this, HouseActivity.class);
                break;
            case 2://代管出租
                GoUtil.goActivity(HomeActivity.this, AgentActivity.class);
                break;
            case 3://出租房中介
                GoUtil.goActivity(HomeActivity.this, AgencyActivity.class);
                break;
        }


    }

    private NormalDialog mAddInfoDialog;

    private void initAddInfoDialog() {
        mAddInfoDialog = DialogUtil.getDoubleDialog(HomeActivity.this, "资料不完整，前去完善资料", "取消", "确定");
        mAddInfoDialog.setOnBtnClickL(new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                mAddInfoDialog.dismiss();
            }
        }, new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                GoUtil.goActivity(HomeActivity.this, PerfectActivity.class);
                mAddInfoDialog.dismiss();
            }
        });
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
                GoUtil.goActivityAndFinish(HomeActivity.this, LoginActivity.class);
            }
        });
        quitDialog.show();
    }

    private void exitApp() {
        DataManager.putToken("");
        DataManager.putLastPage(-1);
        finish();
        System.exit(0);
    }

    @Override
    public void onPopItemClick(int position, String tag) {
        switch (position) {
            case 0://完善资料
                if (TextUtils.isEmpty(DataManager.getToken())) {
                    GoUtil.goActivityAndFinish(this, LoginActivity.class);
                    break;
                }
                GoUtil.goActivity(HomeActivity.this, PerfectActivity.class);
                break;
            case 1://修改密码
                if (TextUtils.isEmpty(DataManager.getToken())) {
                    GoUtil.goActivityAndFinish(this, LoginActivity.class);
                    break;
                }
                GoUtil.goActivity(HomeActivity.this, ModifyPasswordActivity.class);
                break;
            case 2://退出登录
                makeSureQuit();
                break;

        }
    }

    private void getMsg(int index) {
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.TaskID, TempConstants.DEFAULT_TASK_ID);
        param.put(TempConstants.PageIndex, index);
        param.put(TempConstants.PageSize, TempConstants.DEFAULT_PAGE_SIZE);
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), "", Constants.GetUserMessage, param)
                .setBeanType(GetUserMessage.class)
                .setCallBack(new WebServiceCallBack<GetUserMessage>() {
                    @Override
                    public void onSuccess(GetUserMessage bean) {
                        mTvHomeCount.setVisibility(isHasNewMsg(bean) ? View.VISIBLE : View.GONE);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onClearMsgEvent(ClearMsgEvent messageEvent) {
        mTvHomeCount.setVisibility(View.GONE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshMsgEvent(RefreshMsgEvent messageEvent) {
        mTvHomeCount.setVisibility(View.VISIBLE);
    }
}
