package com.kingja.ui.wheelview;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Description：TODO
 * Create Time：2016/9/2 16:43
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class SingleWheelView extends WheelView {
    protected static final int[] SHADOWS_COLORS = new int[] { 0xeeffffff, 0xeeffffff, 0xeeffffff };
    public SingleWheelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SingleWheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SingleWheelView(Context context) {
        super(context);
    }
}
