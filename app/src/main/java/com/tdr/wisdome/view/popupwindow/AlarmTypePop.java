package com.tdr.wisdome.view.popupwindow;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tdr.wisdome.R;

/**
 * 报警类型Pop
 * Created by Linus_Xie on 2016/8/17.
 */
public class AlarmTypePop extends PopupWindowBaseDown implements View.OnClickListener {
    private LinearLayout linear_braceletState;
    private LinearLayout linear_olderOuter;
    private LinearLayout linear_olderMiss;

    public AlarmTypePop(View parentView, Activity activity) {
        super(parentView, activity);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setAnimationStyle(R.style.PopupBottomAnimation);
    }

    @Override
    public View setPopupView(Activity activity) {
        popupView = View.inflate(activity, R.layout.pop_alramtype, null);
        return popupView;
    }

    @Override
    public void initChildView() {
        linear_braceletState = (LinearLayout) popupView.findViewById(R.id.linear_braceletState);
        linear_olderOuter = (LinearLayout) popupView.findViewById(R.id.linear_olderOuter);
        linear_olderMiss = (LinearLayout) popupView.findViewById(R.id.linear_olderMiss);
        linear_braceletState.setOnClickListener(this);
        linear_olderOuter.setOnClickListener(this);
        linear_olderMiss.setOnClickListener(this);
    }

    @Override
    public void OnChildClick(View v) {
        if (onAlarmTypePopClickListener == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.linear_braceletState:
                onAlarmTypePopClickListener.onAlarmTypePop(0);
                break;

            case R.id.linear_olderOuter:
                onAlarmTypePopClickListener.onAlarmTypePop(1);
                break;

            case R.id.linear_olderMiss:
                onAlarmTypePopClickListener.onAlarmTypePop(2);
                break;
        }
    }

    public interface OnAlarmTypePopClickListener {
        void onAlarmTypePop(int position);
    }

    private OnAlarmTypePopClickListener onAlarmTypePopClickListener;

    public void setOnAlarmTypePopClickListener(OnAlarmTypePopClickListener onAlarmTypePopClickListener) {
        this.onAlarmTypePopClickListener = onAlarmTypePopClickListener;
    }
}
