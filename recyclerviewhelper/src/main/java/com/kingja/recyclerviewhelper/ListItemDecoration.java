package com.kingja.recyclerviewhelper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import static com.kingja.recyclerviewhelper.LayoutHelper.LayoutStyle.VERTICAL_LIST;

/**
 * Description:ListItemDecoration
 * Create Time:2017/4/15 14:08
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ListItemDecoration extends RecyclerView.ItemDecoration {
    private Paint mPaint;
    private LayoutHelper.LayoutStyle mLayoutStyle;
    private int mDividerHeight;


    public ListItemDecoration(Context context, LayoutHelper.LayoutStyle layoutStyle, int dividerHeight, int
            dividerColor) {
        mLayoutStyle = layoutStyle;
        mDividerHeight = Util.dp2px(context, dividerHeight);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(dividerColor);
        mPaint.setStyle(Paint.Style.FILL);
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        super.getItemOffsets(outRect, view, parent, state);
//        outRect.set(0, 0, 0, mDividerHeight);
        if (mLayoutStyle == VERTICAL_LIST) {
            outRect.set(0, 0, 0, mDividerHeight);
        } else {
            outRect.set(0, 0, mDividerHeight, 0);
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mLayoutStyle == VERTICAL_LIST) {
            drawHorizontal(c, parent);
        } else if (mLayoutStyle == LayoutHelper.LayoutStyle.HORIZONTAL_LIST) {
            drawVertical(c, parent);
        } else {
            drawVertical(c, parent);
            drawHorizontal(c, parent);
        }
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getMeasuredWidth() - parent.getPaddingRight();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize - 1; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + layoutParams.bottomMargin;
            final int bottom = top + mDividerHeight;
            canvas.drawRect(left, top, right, bottom, mPaint);
        }
    }

    private void drawVertical(Canvas canvas, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + layoutParams.rightMargin;
            final int right = left + mDividerHeight;
            canvas.drawRect(left, top, right, bottom, mPaint);
        }
    }
}
