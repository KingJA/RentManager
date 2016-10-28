package com.kingja.cardpackage.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kingja.cardpackage.util.ZeusManager;
import com.tdr.wisdome.view.ZProgressHUD;

/**
 * Description：TODO
 * Create Time：2016/8/19 13:57
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public abstract class BaseFragment extends Fragment {

    protected BaseActivity mActivity;
    private ZProgressHUD mProgressDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        initFragmentView(view, savedInstanceState);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initConmonView();
        initFragmentVariables();
        initFragmentNet();
        initFragmentData();
        setFragmentData();
    }

    public abstract int getLayoutId();
    public abstract void initFragmentView(View view, Bundle savedInstanceState);
    public abstract void initFragmentVariables();
    public abstract void initFragmentNet();
    public abstract void initFragmentData();
    public abstract void setFragmentData();

    private void initConmonView() {
        mProgressDialog = new ZProgressHUD(getActivity());
        mProgressDialog.setMessage("加载中...");
        mProgressDialog.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
    }
    protected void setProgressDialog(boolean show) {
        if (show) {
            mProgressDialog.show();
        } else {
            mProgressDialog.dismiss();
        }
    }
}
