package com.kingja.cardpackage.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kingja.cardpackage.adapter.LvTextAdapter;
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
public class EditLvActivity extends BackTitleActivity implements AdapterView.OnItemClickListener {

    private List<Basic_Dictionary_Kj> selectList;
    private LvTextAdapter mLvTextAdapter;
    private String mColumnCode;
    private String mColumnValue;
    private ListView lv;
    private String mColumnComment;


    @Override
    protected void initVariables() {
        mColumnCode = getIntent().getStringExtra("COLUMNCODE");
        mColumnValue = getIntent().getStringExtra("COLUMNVALUE");
        Basic_Dictionary_Kj bean =  (Basic_Dictionary_Kj) DbDaoXutils3.getInstance().sleectFirst(Basic_Dictionary_Kj.class, "COLUMNCODE", mColumnCode, "COLUMNVALUE", mColumnValue);
        if (bean != null) {
            mColumnComment = bean.getCOLUMNCOMMENT();
        }
        selectList = (List<Basic_Dictionary_Kj>) DbDaoXutils3.getInstance().selectAllWhereAndOrder(Basic_Dictionary_Kj.class, "COLUMNCODE", mColumnCode,"COLUMNVALUE",false);
    }

    @Override
    protected void initContentView() {
        lv = (ListView) findViewById(R.id.lv);
        mLvTextAdapter = new LvTextAdapter(this, selectList, mColumnValue);
        lv.setAdapter(mLvTextAdapter);
    }

    @Override
    protected int getBackContentView() {
        return R.layout.lv;
    }

    @Override
    protected void initNet() {

    }

    @Override
    protected void initData() {
        lv.setOnItemClickListener(this);
    }

    @Override
    protected void setData() {
        setTopColor(TopColor.WHITE);
        setTitle(mColumnComment);
    }


    public static void goActivity(Activity activity, String columnCode, String columnValue, int requestCode) {
        Intent intent = new Intent(activity, EditLvActivity.class);
        intent.putExtra("COLUMNCODE", columnCode);
        intent.putExtra("COLUMNVALUE", columnValue);
        activity.startActivityForResult(intent, requestCode);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Basic_Dictionary_Kj bean = (Basic_Dictionary_Kj) parent.getItemAtPosition(position);
        mLvTextAdapter.selectItem(position);
        Intent intent = new Intent();
        intent.putExtra("COMUMNMENT", bean.getCOLUMNCOMMENT());
        intent.putExtra("COLUMNVALUE", bean.getCOLUMNVALUE());
        setResult(RESULT_OK, intent);
        finish();
    }
}
