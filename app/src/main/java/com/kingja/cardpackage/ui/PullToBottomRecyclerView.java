package com.kingja.cardpackage.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.AbsListView;

/**
 * Description：监听滑动到底部的RecyclerView
 * Create Time：2017/3/21 10:23
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class PullToBottomRecyclerView extends RecyclerView {
    private OnPullToBottomListener onPullToBottomListener;

    public PullToBottomRecyclerView(Context context) {
        this(context, null);
    }

    public PullToBottomRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToBottomRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        if (isBottom(PullToBottomRecyclerView.this)&&onPullToBottomListener!=null) {
                            onPullToBottomListener.onPullToBottom();
                        }
                        break;
                }
            }

        });
    }

    private boolean isBottom(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        //屏幕中最后一个可见子项的position
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        //当前屏幕所看到的子项个数
        int visibleItemCount = layoutManager.getChildCount();
        //当前RecyclerView的所有子项个数
        int totalItemCount = layoutManager.getItemCount();
        //RecyclerView的滑动状态
        int state = recyclerView.getScrollState();
        if (visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && state == recyclerView.SCROLL_STATE_IDLE) {
            return true;
        } else {
            return false;
        }
    }

    public interface OnPullToBottomListener{
        void onPullToBottom();
    }

    public void setOnPullToBottomListener(OnPullToBottomListener onPullToBottomListener) {
        this.onPullToBottomListener = onPullToBottomListener;
    }
}
