package com.kingja.cardpackage.base;

import android.Manifest;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;

import com.kingja.cardpackage.ui.SystemBarTint.StatusBarCompat;
import com.kingja.cardpackage.util.ActivityManager;
import com.kingja.cardpackage.util.ToastUtil;
import com.kingja.cardpackage.util.ZeusManager;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.tdr.wisdome.R;
import com.tdr.wisdome.view.ZProgressHUD;

public abstract class BaseActivity extends FragmentActivity implements ZeusManager.OnPermissionCallback {
    protected String TAG = getClass().getSimpleName();
    protected ProgressDialog mProgressDialog;
    protected FragmentManager mSupportFragmentManager;
    protected ZeusManager mZeusManager;
    protected static final String[] permissionArr = {Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION};


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        mSupportFragmentManager = getSupportFragmentManager();
        if (setStatusBar()) {
            StatusBarCompat.initStatusBar(this, getStatusBarColor() == -1 ? R.color.bg_black : getStatusBarColor());
        }
        ActivityManager.getAppManager().addActivity(this);
        setContentView(getContentView());
        initConmonView();
        initVariables();
        initView();
        initNet();
        initData();
        setData();
    }

    protected boolean setStatusBar() {
        return true;
    }

    protected int getStatusBarColor() {
        return -1;
    }

    private void initConmonView() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setMessage("加载中...");

        mZeusManager = new ZeusManager(this);
        mZeusManager.setOnPermissionCallback(this);
    }

    protected abstract void initVariables();

    protected abstract int getContentView();

    protected abstract void initView();

    protected abstract void initNet();

    protected abstract void initData();

    protected abstract void setData();


    protected void setProgressDialog(boolean show) {
        if (show) {
            mProgressDialog.show();
        } else {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        mZeusManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onClose() {
        ToastUtil.showToast("权限未允许，自动关闭页面");
        finish();
    }

    @Override
    public void onAllow() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getAppManager().finishActivity(this);
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    protected void showQuitDialog() {
        final NormalDialog dialog = new NormalDialog(this);
        dialog.isTitleShow(false)
                .content("是否要退出当前页面")
                .contentTextSize(15f)
                .bgColor(ContextCompat.getColor(this, R.color.bg_white))
                .cornerRadius(5)
                .contentGravity(Gravity.CENTER)
                .widthScale(0.85f)
                .contentTextColor(ContextCompat.getColor(this, R.color.font_3))
                .dividerColor(ContextCompat.getColor(this, R.color.bg_divider))
                .btnTextSize(15f, 15f)
                .btnTextColor(ContextCompat.getColor(this, R.color.bg_blue), ContextCompat.getColor(this, R.color.bg_blue))
                .btnPressColor(ContextCompat.getColor(this, R.color.bg_press))
                .show();
        dialog.setOnBtnClickL(
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        finish();
                    }
                });
    }


}
