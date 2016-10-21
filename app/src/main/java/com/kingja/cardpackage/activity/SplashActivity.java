package com.kingja.cardpackage.activity;

import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.kingja.cardpackage.base.BaseActivity;
import com.kingja.cardpackage.db.DatebaseManager;
import com.kingja.cardpackage.net.PoolManager;
import com.kingja.cardpackage.util.AppInfoUtil;
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
    private TextView tv_skip;

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
        tv_skip = (TextView) findViewById(R.id.tv_skip);
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
        copyDb();
        handler.postDelayed(skipRunnable,DELAYED_MILLS);
        tv_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(skipRunnable);
                GoUtil.goActivityAndFinish(SplashActivity.this, HomeActivity.class);
            }
        });
    }

    private void copyDb() {
        PoolManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                DatebaseManager.getInstance(getApplicationContext()).copyDataBase("citypolice_wz.db");
            }
        });
    }

    private Runnable skipRunnable = new Runnable() {
        @Override
        public void run() {
            GoUtil.goActivityAndFinish(SplashActivity.this, HomeActivity.class);
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
