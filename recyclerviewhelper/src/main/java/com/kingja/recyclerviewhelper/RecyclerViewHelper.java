package com.kingja.recyclerviewhelper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Description:TODO
 * Create Time:2017/4/15 9:02
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class RecyclerViewHelper {
    private Object mAdapter;
    private ItemTouchHelper.Callback mHelperCallback;
    private boolean mDragable;
    private boolean mSwipeable;
    private ItemTouchHelper mItemTouchHelper;
    private Context mContext;
    private LayoutHelper.LayoutStyle mLayoutStyle;
    private int mDividerHeight;
    private int mDividerColor;
    private int mColumns;

    public RecyclerViewHelper(Builder builder) {
        this.mColumns = builder.mColumns;
        this.mDividerHeight = builder.dividerHeight;
        this.mDividerColor = builder.dividerColor;
        this.mContext = builder.mContext;
        this.mAdapter = builder.onItemCallback;
        this.mLayoutStyle = builder.layoutStyle;
        this.mDragable = builder.mDragable;
        this.mSwipeable = builder.mSwipeable;
        if (mDragable || mSwipeable) {
            if (mAdapter instanceof OnItemCallback) {
                OnItemCallback callBackAdapter = (OnItemCallback) mAdapter;
                mHelperCallback = new SimpleItemTouchHelperCallback(callBackAdapter, mDragable, mSwipeable);
                mItemTouchHelper = new ItemTouchHelper(mHelperCallback);
            } else {
                throw new IllegalArgumentException("The mAdapter which is mDragable or mSwipeable must implement the " +
                        "OnItemCallback interface ");
            }
        }
    }


    public void attachToRecyclerView(RecyclerView recyclerView) {
        if (recyclerView == null) {
            throw new IllegalArgumentException("The recyclerView attached can't be null");
        }

        if (mAdapter instanceof RecyclerView.Adapter) {
            RecyclerView.Adapter normalAdapter = (RecyclerView.Adapter) mAdapter;
            recyclerView.setAdapter(normalAdapter);
            recyclerView.setLayoutManager(LayoutHelper.getLayoutManager(mContext, mLayoutStyle, mColumns));
            if (mLayoutStyle == mLayoutStyle.GRID) {
                recyclerView.addItemDecoration(new GridItemDecoration(
                        mContext, mDividerHeight, mDividerColor));
            } else {
                recyclerView.addItemDecoration(new ListItemDecoration(
                        mContext, mLayoutStyle, mDividerHeight, mDividerColor));
            }
        }
        if (mDragable || mSwipeable) {
            mItemTouchHelper.attachToRecyclerView(recyclerView);
        }
    }

    public static class Builder {
        private static final int DEFAULT_COLUMNS = 2;
        private static final int DEFAULT_DIVIDER_HEIGHT = 1;
        private static final int DEFAULT_DIVIDER_COLOR = 0XFFEEEEEE;
        private int dividerHeight = DEFAULT_DIVIDER_HEIGHT;
        private int dividerColor = DEFAULT_DIVIDER_COLOR;
        private int mColumns = DEFAULT_COLUMNS;
        private Context mContext;
        private LayoutHelper.LayoutStyle layoutStyle;
        private Object onItemCallback;
        private boolean mDragable;
        private boolean mSwipeable;

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder setAdapter(Object onItemCallback) {
            this.onItemCallback = onItemCallback;
            return this;
        }

        public Builder setLayoutStyle(LayoutHelper.LayoutStyle layoutStyle) {
            this.layoutStyle = layoutStyle;
            return this;
        }

        public Builder setDragable(boolean dragable) {
            this.mDragable = dragable;
            return this;
        }

        public Builder setSwipeable(boolean swipeable) {
            this.mSwipeable = swipeable;
            return this;
        }

        public Builder setDividerHeight(int dividerHeight) {
            this.dividerHeight = dividerHeight;
            return this;
        }

        public Builder setColumns(int columns) {
            this.mColumns = columns;
            return this;
        }

        public Builder setDividerColor(int dividerColor) {
            this.dividerColor = dividerColor;
            return this;
        }

        public RecyclerViewHelper build() {
            if (onItemCallback == null) {
                throw new IllegalArgumentException("A mAdapter is required");
            }
            return new RecyclerViewHelper(this);
        }
    }

    public interface OnItemCallback {
        void onMove(int fromPosition, int toPosition);

        void onSwipe(int position);
    }

}
