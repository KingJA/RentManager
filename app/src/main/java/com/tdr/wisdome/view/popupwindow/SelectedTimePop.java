package com.tdr.wisdome.view.popupwindow;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tdr.wisdome.R;

import java.util.Calendar;

/**
 * Created by Linus_Xie on 2016/8/17.
 */
public class SelectedTimePop extends PopupWindowBaseDown implements View.OnClickListener {

    private TextView text_startTime;
    private TextView text_endTime;
    private TextView text_reset;
    private TextView text_confirm;

    public SelectedTimePop(View parentView, Activity activity) {
        super(parentView, activity);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setAnimationStyle(R.style.PopupBottomAnimation);
    }

    @Override
    public View setPopupView(Activity activity) {
        popupView = View.inflate(activity, R.layout.pop_selectedtime, null);
        return popupView;
    }

    @Override
    public void initChildView() {
        text_startTime = (TextView) popupView.findViewById(R.id.text_startTime);
        text_endTime = (TextView) popupView.findViewById(R.id.text_endTime);
        text_reset = (TextView) popupView.findViewById(R.id.text_reset);
        text_confirm = (TextView) popupView.findViewById(R.id.text_confirm);
        text_startTime.setOnClickListener(this);
        text_endTime.setOnClickListener(this);
        text_reset.setOnClickListener(this);
        text_confirm.setOnClickListener(this);
    }

    private Dialog dateDialog;// 日期选择弹出框
    private Calendar c = null;

    @Override
    public void OnChildClick(View v) {
        if (onSelectedTimePopClickListener == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.text_startTime:
                onSelectedTimePopClickListener.onSelectedTimePop(0);
                break;

            case R.id.text_endTime:
                onSelectedTimePopClickListener.onSelectedTimePop(1);
                break;

            case R.id.text_reset:
                onSelectedTimePopClickListener.onSelectedTimePop(2);
                break;
            case R.id.text_confirm:
                onSelectedTimePopClickListener.onSelectedTimePop(2);
                break;
        }
    }

    public interface OnSelectedTimePopClickListener {
        void onSelectedTimePop(int position);
    }

    private OnSelectedTimePopClickListener onSelectedTimePopClickListener;

    public void setOnSelectedTimePopClickListener(OnSelectedTimePopClickListener onSelectedTimePopClickListener) {
        this.onSelectedTimePopClickListener = onSelectedTimePopClickListener;
    }
}
