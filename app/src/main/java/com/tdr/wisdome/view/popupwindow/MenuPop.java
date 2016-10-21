package com.tdr.wisdome.view.popupwindow;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;

import com.tdr.wisdome.R;

/**
 * Created by Linus_Xie on 2016/8/11.
 */
public class MenuPop extends PopupWindowBaseDown implements View.OnClickListener {

    private LinearLayout linear_msg;
    private LinearLayout linear_package;
    private LinearLayout linear_personal;

    public MenuPop(View parentView, Activity activity) {
        super(parentView, activity);
    }

    @Override
    public View setPopupView(Activity activity) {
        popupView = View.inflate(activity, R.layout.pop_menu, null);
        return popupView;
    }

    @Override
    public void initChildView() {
        linear_msg = (LinearLayout) popupView.findViewById(R.id.linear_msg);
        linear_package = (LinearLayout) popupView.findViewById(R.id.linear_package);
        linear_personal = (LinearLayout) popupView.findViewById(R.id.linear_personal);
        linear_msg.setOnClickListener(this);
        linear_package.setOnClickListener(this);
        linear_personal.setOnClickListener(this);
    }

    @Override
    public void OnChildClick(View v) {
        if (onMenuPopClickListener == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.linear_msg:
                onMenuPopClickListener.onMenuPop(0);
                break;

            case R.id.linear_package:
                onMenuPopClickListener.onMenuPop(1);
                break;

            case R.id.linear_personal:
                onMenuPopClickListener.onMenuPop(2);
                break;
        }
    }

    public interface OnMenuPopClickListener {
        void onMenuPop(int position);
    }

    private OnMenuPopClickListener onMenuPopClickListener;

    public void setOnMenuPopClickListener(OnMenuPopClickListener onMenuPopClickListener) {
        this.onMenuPopClickListener = onMenuPopClickListener;
    }

}
