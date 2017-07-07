package com.kingja.cardpackage.activity;

import android.os.Handler;
import android.text.TextUtils;
import android.widget.TextView;

import com.kingja.cardpackage.base.BaseActivity;
import com.kingja.cardpackage.util.AppInfoUtil;
import com.kingja.cardpackage.util.DataManager;
import com.kingja.cardpackage.util.GoUtil;
import com.tdr.wisdome.R;
import com.tdr.wisdome.actvitiy.LoginActivity;

import cn.jpush.android.api.JPushInterface;

/**
 * Description：TODO
 * Create Time：2016/10/21 9:32
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class SplashActivity extends BaseActivity {
    private final long DELAYED_MILLS = 2000;
    private Handler handler=new Handler();
    private TextView tv_version;

    @Override
    protected void initVariables() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        tv_version = (TextView) findViewById(R.id.tv_version);
    }

    @Override
    protected void initNet() {
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setData() {
        tv_version.setText("当前版本:"+ AppInfoUtil.getVersionName());
        handler.postDelayed(skipRunnable,DELAYED_MILLS);
    }


    private Runnable skipRunnable = new Runnable() {
        @Override
        public void run() {
            if (TextUtils.isEmpty(DataManager.getToken())) {
                GoUtil.goActivityAndFinish(SplashActivity.this, LoginActivity.class);
            }else{
                GoUtil.goActivityAndFinish(SplashActivity.this, NewHomeActivity.class);
            }

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(skipRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    protected int getStatusBarColor() {
        return R.color.bg_blue;
    }
}
