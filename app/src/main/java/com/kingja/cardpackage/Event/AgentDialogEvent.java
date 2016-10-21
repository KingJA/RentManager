package com.kingja.cardpackage.Event;

/**
 * Description：TODO
 * Create Time：2016/9/27 9:43
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class AgentDialogEvent {
    private boolean showDialog;

    public AgentDialogEvent(boolean showDialog) {
        this.showDialog = showDialog;
    }

    public boolean isShowDialog() {
        return showDialog;
    }

    public void setShowDialog(boolean showDialog) {
        this.showDialog = showDialog;
    }
}
