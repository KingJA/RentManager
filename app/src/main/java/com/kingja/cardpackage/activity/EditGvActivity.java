package com.kingja.cardpackage.activity;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.kingja.cardpackage.adapter.SelectAdapter;
import com.kingja.cardpackage.db.DbDaoXutils3;
import com.kingja.cardpackage.entiy.Basic_Dictionary_Kj;
import com.tdr.wisdome.R;

import java.util.List;

/**
 * Description：TODO
 * Create Time：2016/8/17 13:23
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class EditGvActivity extends BackTitleActivity implements AdapterView.OnItemClickListener {

    private String mType;
    private String mValue;
    private GridView mGvEdit;
    private List<Basic_Dictionary_Kj> selectList;
    private SelectAdapter mSelectAdapter;


    @Override
    protected void initVariables() {
        mType = getIntent().getStringExtra("TYPE");
        mValue = getIntent().getStringExtra("VALUE");
        selectList = (List<Basic_Dictionary_Kj>) DbDaoXutils3.getInstance().selectAllWhereAndOrder(Basic_Dictionary_Kj.class, "COLUMNCODE", mType.equals("装修程度") ? "FIXTURE" : "DEPOSIT","COLUMNVALUE",false);
        for (Basic_Dictionary_Kj bean : selectList) {
            Log.e(TAG, bean.getCOLUMNCOMMENT());
        }
    }

    @Override
    protected void initContentView() {
        mGvEdit = (GridView) findViewById(R.id.gv_edit);
        mSelectAdapter = new SelectAdapter(this, selectList, mValue);
        mGvEdit.setAdapter(mSelectAdapter);
    }

    @Override
    protected int getBackContentView() {
        return R.layout.activity_edit_gv;
    }

    @Override
    protected void initNet() {

    }

    @Override
    protected void initData() {
        mGvEdit.setOnItemClickListener(this);
    }

    @Override
    protected void setData() {
        setTopColor(TopColor.WHITE);
        setTitle(mType);
    }


    public static void goActivity(Activity activity, String type, String value, int requestCode) {
        Intent intent = new Intent(activity, EditGvActivity.class);
        intent.putExtra("TYPE", type);
        intent.putExtra("VALUE", value);
        activity.startActivityForResult(intent, requestCode);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Basic_Dictionary_Kj bean = (Basic_Dictionary_Kj) parent.getItemAtPosition(position);
        mSelectAdapter.selectItem(position);
        Intent intent = new Intent();
        intent.putExtra("CODE", bean.getCOLUMNVALUE());
        intent.putExtra("VALUE", bean.getCOLUMNCOMMENT());
        setResult(RESULT_OK, intent);
        finish();
    }
}
