package com.kingja.cardpackage.Event;

/**
 * Description：TODO
 * Create Time：2016/9/27 9:43
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class HouseDialogEvent {
    private boolean showDialog;

    public HouseDialogEvent(boolean showDialog) {
        this.showDialog = showDialog;
    }

    public boolean isShowDialog() {
        return showDialog;
    }

    public void setShowDialog(boolean showDialog) {
        this.showDialog = showDialog;
    }
}
