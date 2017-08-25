package com.kingja.cardpackage.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.kingja.cardpackage.entiy.RentBean;
import com.kingja.cardpackage.fragment.ApplyFragment;
import com.kingja.cardpackage.fragment.ApplyListFragment;
import com.kingja.cardpackage.fragment.UnregisteredApplyFragment;
import com.kingja.ui.SwitchMultiButton;
import com.tdr.wisdome.R;

import java.util.Arrays;

/**
 * Description：人员申报Activity
 * Create Time：2016/8/19 10:52
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class PersonApplyActivity extends BackTitleActivity implements SwitchMultiButton.OnSwitchListener, BackTitleActivity.OnRightClickListener {
    private OnSaveClickListener onSaveClickListener;
    private RentBean entiy;
    private FragmentTransaction mTransaction;
    private ApplyFragment mApplyFragment;
    private ApplyListFragment mApplyListFragment;
    private String cardType;
    private int reporterRole;
    private String agencyId;

    @Override
    protected void initVariables() {
        entiy = (RentBean) getIntent().getSerializableExtra("ENTIY");
        cardType = getIntent().getStringExtra("cardType");
        agencyId = getIntent().getStringExtra("agencyId");
        reporterRole = getIntent().getIntExtra("reporterRole", 0);
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
                    mApplyFragment = ApplyFragment.newInstance(entiy,cardType,reporterRole,agencyId);
                    mTransaction.add(R.id.fl_fragment, mApplyFragment);
                } else {
                    mTransaction.show(mApplyFragment);
                }
                break;
            case 1://列表
                if (mApplyListFragment == null) {
                    mApplyListFragment = ApplyListFragment.newInstance(entiy,agencyId);
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

    public static void goActivity(Context context, RentBean entiy, String cardType, int reporterRole, String agencyId) {
        Intent intent = new Intent(context, PersonApplyActivity.class);
        intent.putExtra("ENTIY", entiy);
        intent.putExtra("cardType", cardType);
        intent.putExtra("reporterRole", reporterRole);
        intent.putExtra("agencyId", agencyId);
        context.startActivity(intent);
    }
  public static void goActivity(Context context, RentBean entiy) {
        Intent intent = new Intent(context, PersonApplyActivity.class);
        intent.putExtra("ENTIY", entiy);
        context.startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == RESULT_OK) {
            if (requestCode == KCamera.REQUEST_CODE_KCAMERA||requestCode == UnregisteredApplyFragment.REQUEST_CAMARA) {
                mApplyFragment.onActivityResult(requestCode,  resultCode,  data);
            }
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.e(TAG, "onSaveInstanceState: ");
    }

    @Override
    protected void onCreate(Bundle arg0) {
        Log.e(TAG, "onCreate: " );
        super.onCreate(arg0);
    }

    @Override
    protected void onRestart() {
        Log.e(TAG, "onRestart: " );
        super.onRestart();
    }
    @Override
    public void onBackPressed() {
        if (mApplyFragment.isShowBigImg()) {
            mApplyFragment.hideBigImg();
        }else{
            showQuitDialog();
        }
    }

    @Override
    protected void onClickBack() {
        onBackPressed();
    }
}
