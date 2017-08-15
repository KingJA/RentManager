package com.kingja.cardpackage.activity;

import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.kingja.cardpackage.Event.SetLoginTab;
import com.kingja.cardpackage.adapter.CommonFragmentPagerAdapter;
import com.kingja.cardpackage.base.BaseActivity;
import com.kingja.cardpackage.db.DownloadDbManager;
import com.kingja.cardpackage.fragment.LoginFragment;
import com.kingja.cardpackage.fragment.RegisterFragment;
import com.tdr.wisdome.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import lib.king.kupdate.UpdateManager;
import lib.king.kupdate.strategy.WebServiceStrategy;

/**
 * Description:TODO
 * Create Time:2017/7/25 9:20
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class LoginActivity extends BaseActivity {
    private TabLayout mTlLogin;
    private ViewPager mVpLogin;
    private String[] tabTexts = {"登录", "注册"};
    private Fragment[] fragments = new Fragment[2];


    @Override
    protected void initVariables() {
        EventBus.getDefault().register(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login_k;
    }

    @Override
    protected void initView() {
        mTlLogin = (TabLayout) findViewById(R.id.tl_login);
        mVpLogin = (ViewPager) findViewById(R.id.vp_login);
    }

    @Override
    protected void initNet() {
        checkUpdate();
    }

    @Override
    protected void initData() {
        mTlLogin.setTabMode(TabLayout.MODE_FIXED);
        mTlLogin.addTab(mTlLogin.newTab().setText(tabTexts[0]));
        mTlLogin.addTab(mTlLogin.newTab().setText(tabTexts[1]));

        fragments[0] = new LoginFragment();
        fragments[1] = new RegisterFragment();

        CommonFragmentPagerAdapter mainPagerAdapter = new CommonFragmentPagerAdapter(getSupportFragmentManager(),
                fragments, tabTexts);
        mVpLogin.setAdapter(mainPagerAdapter);
        mVpLogin.setOffscreenPageLimit(2);
        mTlLogin.setupWithViewPager(mVpLogin);
    }

    @Override
    protected void setData() {

    }

    private void checkUpdate() {
        UpdateManager.Builder builder = new UpdateManager.Builder(this);
        builder.setUpdateCancleable(false)
                .setShowDownloadDialog(true)
                .setLoadStrategy(new WebServiceStrategy())
                .setUpdateContent("发现新版本，请马上更新")
                .build()
                .checkUpdate();

    }

    @Subscribe
    public void setLoginTab(SetLoginTab bean) {
        mVpLogin.setCurrentItem(0);
        mTlLogin.getTabAt(0).select();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
