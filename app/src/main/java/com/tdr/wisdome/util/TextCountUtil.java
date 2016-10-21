package com.tdr.wisdome.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * 倒计时
 * Created by Linus_Xie on 2016/6/25.
 */
public class TextCountUtil extends CountDownTimer {

    private Activity mActivity;
    private TextView msg;

    // 在这个构造方法里需要传入三个参数，一个是Activity，一个是总的时间millisInFuture，一个是countDownInterval，然后就是你在哪个按钮上做这个是，就把这个按钮传过来就可以了
    public TextCountUtil(Activity mActivity, long millisInFuture, long countDownInterval, TextView msg) {
        super(millisInFuture, countDownInterval);
        this.mActivity = mActivity;
        this.msg = msg;
    }

    @SuppressLint("NewApi")
    @Override
    public void onTick(long millisUntilFinished) {
        msg.setClickable(false);//设置不能点击
        msg.setText(millisUntilFinished / 1000 + " 秒");//设置倒计时时间
        msg.setTextColor(Color.parseColor("#777777"));
    }

    @SuppressLint("NewApi")
    @Override
    public void onFinish() {
        msg.setText("获取验证码");
        msg.setClickable(true);
        msg.setTextColor(Color.parseColor("#4B8CD6"));
    }
}
