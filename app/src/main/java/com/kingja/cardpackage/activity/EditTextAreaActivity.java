package com.kingja.cardpackage.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.Selection;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.tdr.wisdome.R;


/**
 * Description：TODO
 * Create Time：2016/8/17 13:23
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class EditTextAreaActivity extends BackTitleActivity implements BackTitleActivity.OnRightClickListener {

    private String mValue;
    private String mType;


    private EditText mEtValue;
    private LinearLayout mLlClear;


    @Override
    protected void initVariables() {

        mValue = getIntent().getStringExtra("VALUE");
        mType = getIntent().getStringExtra("TYPE");

    }

    @Override
    protected void initContentView() {
        mEtValue = (EditText) findViewById(R.id.et_value);
        mLlClear = (LinearLayout) findViewById(R.id.ll_clear);
    }

    @Override
    protected int getBackContentView() {
        return R.layout.activity_edit_textarea;
    }

    @Override
    protected void initNet() {

    }

    @Override
    protected void initData() {
        mLlClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtValue.setText("");
            }
        });
    }

    @Override
    protected void setData() {
        setTopColor(TopColor.WHITE);
        setOnRightClickListener(this, "保存");
        setTitle(mType);
        mEtValue.setText(mValue);

        Editable etext = mEtValue.getText();
        Selection.setSelection(etext, etext.length());
    }


    public static void goActivity(Activity activity, String type, String value, int requestCode) {
        Intent intent = new Intent(activity, EditTextAreaActivity.class);
        intent.putExtra("TYPE", type);
        intent.putExtra("VALUE", value);
        activity.startActivityForResult(intent, requestCode);

    }

    @Override
    public void onRightClick() {
        mValue = mEtValue.getText().toString().trim();
        Intent intent = new Intent();
        intent.putExtra("VALUE", mValue);
        setResult(RESULT_OK, intent);
        finish();
    }
}
