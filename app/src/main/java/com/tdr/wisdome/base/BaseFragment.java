package com.tdr.wisdome.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Linus_Xie on 2016/8/2.
 */
public abstract class BaseFragment extends Fragment {

    public Activity mActivity;
    public final static int ERROR = 4001;

    // fragment创建
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    // 处理fragment的布局
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView();
    }

    // 依附的activity创建完成
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
    }

    // 子类必须实现初始化布局的方法
    public abstract View initView();

    // 初始化数据, 可以不实现
    public void initData() {

    }

    // 回调
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
    }


}

