package com.kingja.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

/**
 * Description：TODO
 * Create Time：2016/8/28 15:25
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */

public class DialogProgress extends AlertDialog {


    public DialogProgress(Context context) {
        super(context, R.style.dialog_progress);
        this.setCancelable(true);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress);
    }

}
