package com.kingja.cardpackage.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Description:TODO
 * Create Time:2017/7/6 13:54
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class FixImageView extends ImageView {
    public FixImageView(Context context) {
        super(context);
    }

    public FixImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FixImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(),getMeasuredWidth());
    }
}
