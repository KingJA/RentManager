package com.kingja.cardpackage.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Description：TODO
 * Create Time：2016/8/19 13:57
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public abstract class BaseFragment extends Fragment {

    protected BaseActivity mActivity;

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
}
