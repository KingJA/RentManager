package com.kingja.cardpackage.activity;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.kingja.cardpackage.fragment.UnregisteredApplyFragment;
import com.kingja.cardpackage.fragment.UnregisteredApplyListFragment;
import com.kingja.ui.SwitchMultiButton;
import com.tdr.wisdome.R;

import java.util.Arrays;

/**
 * Description：未登记地址申报
 * Create Time：2016/8/19 10:52
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class UnregisteredApplyActivity extends BackTitleActivity implements SwitchMultiButton.OnSwitchListener, BackTitleActivity.OnRightClickListener {
    private OnSaveClickListener onSaveClickListener;
    private FragmentTransaction mTransaction;
    private UnregisteredApplyFragment mApplyFragment;
    private UnregisteredApplyListFragment mApplyListFragment;

    @Override
    protected void initVariables() {
    }

    @Override
    protected void initContentView() {
    }

    @Override
    protected int getBackContentView() {
        return R.layout.activity_person_apply;
    }

    @Override
    protected void initNet() {

    }

    @Override
    protected void initData() {
        setSwitch(this, Arrays.asList("申报", "列表"));
        setOnRightClickListener(this, "完成");
        setTab(0);
    }

    @Override
    protected void setData() {

    }

    @Override
    public void onSwitch(int position, String tabText) {
        setTab(position);

    }

    private void setTab(int position) {
        mTransaction = mSupportFragmentManager.beginTransaction();
        hideFragment();
        mRlTopRight.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
        switch (position) {
            case 0://申报
                if (mApplyFragment == null) {
                    mApplyFragment = new UnregisteredApplyFragment();
                    mTransaction.add(R.id.fl_fragment, mApplyFragment);
                } else {
                    mTransaction.show(mApplyFragment);
                }
                break;
            case 1://列表
                if (mApplyListFragment == null) {
                    mApplyListFragment =new UnregisteredApplyListFragment();
                    mTransaction.add(R.id.fl_fragment, mApplyListFragment);
                } else {
                    mTransaction.show(mApplyListFragment);
                }
                break;
            default:
                break;
        }
        mTransaction.commit();
    }

    private void hideFragment() {
        if (mApplyFragment != null) {
            mTransaction.hide(mApplyFragment);
        }
        if (mApplyListFragment != null) {
            mTransaction.hide(mApplyListFragment);
        }
    }

    @Override
    public void onRightClick() {
        if (onSaveClickListener != null) {
            onSaveClickListener.onSaveClick();
        }
    }

    public interface OnSaveClickListener {
        void onSaveClick();
    }

    public void setOnSaveClickListener(OnSaveClickListener onSaveClickListener) {
        this.onSaveClickListener = onSaveClickListener;
    }

    @Override
    public void onBackPressed() {
        showQuitDialog();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == RESULT_OK) {
            if (requestCode == KCamera.REQUEST_CODE_KCAMERA) {
                mApplyFragment.onActivityResult(requestCode,  resultCode,  data);
            }
        }
    }


}
