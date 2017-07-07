package com.kingja.recyclerviewhelper;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Description:SimpleItemTouchHelperCallback
 * Create Time:2017/4/14 15:23
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private final RecyclerViewHelper.OnItemCallback callbackAdapter;
    private boolean dragable;
    private boolean swipeable;

    public SimpleItemTouchHelperCallback(RecyclerViewHelper.OnItemCallback callbackAdapter, boolean dragable, boolean
            swipeable) {
        this.callbackAdapter = callbackAdapter;
        this.dragable = dragable;
        this.swipeable = swipeable;
    }

    /**
     * Returns whether ItemTouchHelper should start a drag and drop operation if an item is long pressed.
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    /**
     * Returns whether ItemTouchHelper should start a swipe operation if a pointer is swiped over the View.
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    /**
     * Should return a composite flag which defines the enabled move directions in each state
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags;
        int swipeFlags;
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        } else {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        }

        return ItemTouchHelper.Callback.makeMovementFlags(dragable ? dragFlags : 0, swipeable ? swipeFlags : 0);
    }

    /**
     * Called when ItemTouchHelper wants to move the dragged item from its old position to the new position.
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder
            target) {
        callbackAdapter.onMove(viewHolder.getAdapterPosition(),
                target.getAdapterPosition());
        return true;
    }

    /**
     * Called when a ViewHolder is swiped by the user.
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        callbackAdapter.onSwipe(viewHolder.getAdapterPosition());
    }

    /**
     * Called when the ViewHolder swiped or dragged by the ItemTouchHelper is changed.
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            viewHolder.itemView.setAlpha(0.5f);
        }
    }

    /**
     * Called when the ViewHolder swiped or dragged by the ItemTouchHelper is changed.
     */
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setAlpha(1.0f);
    }
}
