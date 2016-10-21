package com.tdr.wisdome.view.popupwindow;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;

import com.tdr.wisdome.R;

/**
 * 我的车弹窗
 * Created by Linus_Xie on 2016/8/19.
 */
public class CarPop extends PopupWindowBaseDown implements View.OnClickListener {

    private LinearLayout linear_carBinding;
    private LinearLayout linear_preRegistration;

    public CarPop(View parentView, Activity activity) {
        super(parentView, activity);
    }

    @Override
    public View setPopupView(Activity activity) {
        popupView = View.inflate(activity, R.layout.pop_car, null);
        return popupView;
    }

    @Override
    public void initChildView() {
        linear_carBinding = (LinearLayout) popupView.findViewById(R.id.linear_carBinding);
        linear_preRegistration = (LinearLayout) popupView.findViewById(R.id.linear_preRegistration);
        linear_carBinding.setOnClickListener(this);
        linear_preRegistration.setOnClickListener(this);
    }

    @Override
    public void OnChildClick(View v) {
        if (onCarPopClickListener == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.linear_carBinding:
                onCarPopClickListener.onCarPop(0);
                break;

            case R.id.linear_preRegistration:
                onCarPopClickListener.onCarPop(1);
                break;
        }
    }

    public interface OnCarPopClickListener {
        void onCarPop(int position);
    }

    private OnCarPopClickListener onCarPopClickListener;

    public void setOnCarPopClickListener(OnCarPopClickListener onCarPopClickListener) {
        this.onCarPopClickListener = onCarPopClickListener;
    }
}
