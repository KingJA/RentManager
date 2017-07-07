package com.kingja.recyclerviewhelper;

import android.content.Context;
import android.util.TypedValue;

/**
 * Description:TODO
 * Create Time:2017/4/15 14:57
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class Util {
    public static int dp2px(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources()
                .getDisplayMetrics());
    }
}
