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
import com.kingja.cardpackage.adapter.HomeAdapter;
import com.kingja.cardpackage.base.BaseActivity;
import com.kingja.cardpackage.util.DataManager;
import com.kingja.cardpackage.util.DialogUtil;
import com.kingja.cardpackage.util.GoUtil;
import com.kingja.cardpackage.util.ToastUtil;
import com.kingja.ui.popupwindow.BottomListPop;
import com.tdr.wisdome.R;
import com.tdr.wisdome.actvitiy.LoginActivity;
import com.tdr.wisdome.actvitiy.PerfectActivity;

import java.util.Arrays;

/**
 * Description：TODO
 * Create Time：2016/10/21 10:19
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class HomeActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener ,BottomListPop.OnPopItemClickListener{
    private String mLargeTexts[] = {"我是房东", "我是租客", "出租房代管"};
    private String mSmallTexts[] = {"我的出租房", "我的住房", "代管出租"};
    private int mHouseImgs[] = {R.drawable.home_rent, R.drawable.home_house, R.drawable.home_agent};
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
        mRlHomeMenu = (RelativeLayout) findViewById(R.id.rl_home_menu);
        mRlHomeMsg = (RelativeLayout) findViewById(R.id.rl_home_msg);
        mTvHomeCount = (TextView) findViewById(R.id.tv_home_count);
        mLvHomeMenu = (ListView) findViewById(R.id.lv_home_menu);
        getLvHeight();
        initAddInfoDialog();
        initBottomListPop();
    }

    private void initBottomListPop() {
        mBottomListPop = new BottomListPop(mRlHomeMenu, this, Arrays.asList("完善资料", "修改密码","退出登录"));
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

    }

    @Override
    protected void initData() {
        mLvHomeMenu.setOnItemClickListener(this);
        mRlHomeMenu.setOnClickListener(this);
        mRlHomeMsg.setOnClickListener(this);
    }

    @Override
    protected void setData() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_home_menu:
                mBottomListPop.showPopupWindow();
                break;
            case R.id.rl_home_msg:
               GoUtil.goActivity(this,AlarmMineActivity.class);
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
        if (TextUtils.isEmpty(DataManager.getIdCard())) {
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
            exitApp();
        }
    }

    private void exitApp() {
        DataManager.putToken("");
        finish();
        System.exit(0);
    }

    @Override
    public void onPopItemClick(int position, String tag) {
        switch (position) {
            case 0://完善资料
                GoUtil.goActivity(HomeActivity.this, PerfectActivity.class);
                break;
            case 1://修改密码
                GoUtil.goActivity(HomeActivity.this, EditPwdActivity.class);
                break;
            case 2://退出登录
                exitApp();
                break;

        }
    }
}
