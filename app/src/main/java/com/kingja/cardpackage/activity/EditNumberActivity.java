package com.kingja.cardpackage.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.Selection;
import android.widget.EditText;
import android.widget.TextView;

import com.kingja.cardpackage.util.CheckUtil;
import com.tdr.wisdome.R;

/**
 * Description：TODO
 * Create Time：2016/8/17 13:23
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class EditNumberActivity extends BackTitleActivity implements BackTitleActivity.OnRightClickListener {

    private String mType;
    private String mValue;
    private String mUnit;

    private TextView mTvType;
    private TextView mTvUnit;
    private EditText mTvValue;


    @Override
    protected void initVariables() {
        mType = getIntent().getStringExtra("TYPE");
        mValue = getIntent().getStringExtra("VALUE");
        mUnit = getIntent().getStringExtra("UNIT");
    }

    @Override
    protected void initContentView() {
        mTvType = (TextView) findViewById(R.id.tv_type);
        mTvUnit = (TextView) findViewById(R.id.tv_unit);
        mTvValue = (EditText) findViewById(R.id.et_value);
    }

    @Override
    protected int getBackContentView() {
        return R.layout.activity_edit_number;
    }

    @Override
    protected void initNet() {

    }

    @Override
    protected void initData() {
    }

    @Override
    protected void setData() {
        setTopColor(TopColor.WHITE);
        setOnRightClickListener(this, "保存");
        setTitle(mType);

        mTvType.setText(mType);
        mTvValue.setText(mValue);
        mTvUnit.setText(mUnit);

        Editable etext = mTvValue.getText();
        Selection.setSelection(etext, etext.length());
    }


    public static void goActivity(Activity activity, String type, String value, String unit, int requestCode) {
        Intent intent = new Intent(activity, EditNumberActivity.class);
        intent.putExtra("TYPE", type);
        intent.putExtra("VALUE", value);
        intent.putExtra("UNIT", unit);
        activity.startActivityForResult(intent, requestCode);

    }

    @Override
    public void onRightClick() {
        mValue = mTvValue.getText().toString().trim();
        if (CheckUtil.checkEmpty(mValue,"输入不能为空")) {
            Intent intent = new Intent();
            intent.putExtra("VALUE", mValue);
            setResult(RESULT_OK, intent);
            finish();
        }

    }
}
