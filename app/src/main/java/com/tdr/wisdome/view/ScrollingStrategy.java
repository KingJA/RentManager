package com.tdr.wisdome.view;

/**
 * Created by Linus_Xie on 2016/8/4.
 */
public interface ScrollingStrategy {
    boolean performScrolling(final int x, final int y, final CoolDragAndDropGridView view);
}
