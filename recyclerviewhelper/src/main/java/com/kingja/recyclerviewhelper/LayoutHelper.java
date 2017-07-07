package com.kingja.recyclerviewhelper;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Description:LayoutHelper
 * Create Time:2017/4/15 13:47
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class LayoutHelper {
    public enum LayoutStyle {
        VERTICAL_LIST, HORIZONTAL_LIST, GRID
    }

    public static RecyclerView.LayoutManager getLayoutManager(Context context, LayoutStyle layoutStyle, int rowCount) {
        RecyclerView.LayoutManager layoutManager;
        switch (layoutStyle) {
            case VERTICAL_LIST:
                layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                break;
            case HORIZONTAL_LIST:
                layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                break;
            case GRID:
                layoutManager = new GridLayoutManager(context, rowCount);
                break;
            default:
                layoutManager = new LinearLayoutManager(context);
                break;
        }
        return layoutManager;
    }
}
