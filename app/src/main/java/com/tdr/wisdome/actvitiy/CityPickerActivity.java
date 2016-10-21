package com.tdr.wisdome.actvitiy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tdr.wisdome.R;
import com.tdr.wisdome.adapter.CityListAdapter;
import com.tdr.wisdome.adapter.ResultListAdapter;
import com.tdr.wisdome.amap.LocationTask;
import com.tdr.wisdome.amap.OnLocationGetListener;
import com.tdr.wisdome.amap.PositionEntity;
import com.tdr.wisdome.model.CityInfo;
import com.tdr.wisdome.model.LocateState;
import com.tdr.wisdome.util.Utils;
import com.tdr.wisdome.view.SideLetterBar;

import net.tsz.afinal.FinalDb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 城市选择
 * Created by Linus_Xie on 2016/8/5.
 */
public class CityPickerActivity extends Activity implements View.OnClickListener, OnLocationGetListener {
    private static final String TAG = "CityPickerActivity";

    public static final int REQUEST_CODE_PICK_CITY = 2333;
    public static final String KEY_PICKED_CITY = "picked_city";

    private Context mContext;

    private CityListAdapter mCityListAdapter;
    private ResultListAdapter mResultListAdapter;
    private List<CityInfo> mAllCities;

    private double lon;//经度
    private double lat;//纬度

    private CityInfo mInfo;
    private FinalDb db;

    private LocationTask mLocationTask;

    private String actvity= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citypicker);

        mContext = this;

        actvity = getIntent().getStringExtra("activity");

        db = FinalDb.create(mContext, "WisdomE.db", false);

        mLocationTask = LocationTask.getInstance(getApplicationContext());
        mLocationTask.setOnLocationGetListener(this);
        mLocationTask.startSingleLocate();

        initData();
        initView();

    }

    private ImageView image_back;
    private TextView text_title;

    private EditText edit_search;
    private ImageView image_searchClear;
    private ListView list_allCity;
    private TextView text_letterOverlay;
    private SideLetterBar side_letter_bar;
    private ListView list_searchResult;
    private ViewGroup linear_empty;

    private void initView() {
        image_back = (ImageView) findViewById(R.id.image_back);
        image_back.setOnClickListener(this);
        text_title = (TextView) findViewById(R.id.text_title);
        text_title.setText("选择城市");

        list_allCity = (ListView) findViewById(R.id.list_allCity);
        list_allCity.setAdapter(mCityListAdapter);

        text_letterOverlay = (TextView) findViewById(R.id.text_letterOverlay);
        side_letter_bar = (SideLetterBar) findViewById(R.id.side_letter_bar);
        side_letter_bar.setOverlay(text_letterOverlay);
        side_letter_bar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                int position = mCityListAdapter.getLetterPosition(letter);
                list_allCity.setSelection(position);
            }
        });

        edit_search = (EditText) findViewById(R.id.edit_search);

        edit_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyWord = s.toString();
                if (TextUtils.isEmpty(keyWord)) {
                    image_searchClear.setVisibility(View.GONE);
                    linear_empty.setVisibility(View.GONE);
                    list_searchResult.setVisibility(View.GONE);
                } else {
                    image_searchClear.setVisibility(View.VISIBLE);
                    list_searchResult.setVisibility(View.VISIBLE);
                    String DB_PATH = getDatabasePath("WisdomE.db").getPath();
                    SQLiteDatabase ddb = SQLiteDatabase.openOrCreateDatabase(DB_PATH, null);
                    Cursor cursor = ddb.rawQuery("select * from " + "city_db" + " where CityName like \"%" + keyWord
                            + "%\" or CityPinYin like \"%" + keyWord.toLowerCase() + "%\"", null);
                    List<CityInfo> result = new ArrayList<CityInfo>();
                    CityInfo city;
                    while (cursor.moveToNext()) {
                        String name = cursor.getString(cursor.getColumnIndex("CityName"));
                        String pinyin = cursor.getString(cursor.getColumnIndex("CityPinYin"));
                        city = new CityInfo(name, pinyin);
                        result.add(city);
                    }
                    cursor.close();
                    ddb.close();
                    Collections.sort(result, new CityComparator());
                    if (result == null || result.size() == 0) {
                        linear_empty.setVisibility(View.VISIBLE);
                    } else {
                        linear_empty.setVisibility(View.GONE);
                        mResultListAdapter.changeData(result);
                    }
                }
            }
        });

        linear_empty = (ViewGroup) findViewById(R.id.linear_empty);
        list_searchResult = (ListView) findViewById(R.id.list_searchResult);
        list_searchResult.setAdapter(mResultListAdapter);
        list_searchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityBack(mResultListAdapter.getItem(position));
            }
        });

        image_searchClear = (ImageView) findViewById(R.id.image_searchClear);
        image_searchClear.setOnClickListener(this);
    }

    /**
     * 单击选择城市
     *
     * @param city
     */
    private void cityBack(CityInfo city) {
        String cityName = city.getCityName();
        Utils.myToast(mContext, "选择的城市：" + cityName);
        Intent intent = new Intent();
        if (actvity.equals("MainActivity")){
            intent.setClass(CityPickerActivity.this, MainActivity.class);
        } else {
            intent.setClass(CityPickerActivity.this, PersonInfoActivity.class);
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("city", (Serializable) city);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void initData() {
        mAllCities = db.findAll(CityInfo.class);
        mCityListAdapter = new CityListAdapter(this, mAllCities);
        mCityListAdapter.setOnCityClickListener(new CityListAdapter.OnCityClickListener() {
            @Override
            public void onCityClick(String name) {
                Log.e(TAG, name);
                List<CityInfo> resultList = db.findAllByWhere(CityInfo.class, " CityName=\"" + name + "\"");
                if (resultList.size() != 0) {
                    cityBack(resultList.get(0));
                } else {
                    Utils.myToast(mContext, name + "即将开放");
                }
            }

            @Override
            public void onLocateClick() {
                Log.e(TAG, "重新定位...");
                mCityListAdapter.updateLocateState(LocateState.LOCATING, null);
            }
        });
        mResultListAdapter = new ResultListAdapter(this, null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_back:
                finish();
                break;

            case R.id.image_searchClear:
                edit_search.setText("");
                image_searchClear.setVisibility(View.GONE);
                linear_empty.setVisibility(View.GONE);
                list_searchResult.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onLocationGet(PositionEntity entity) {
        String locCity = entity.city;
        if (locCity.equals("")){
            mCityListAdapter.updateLocateState(LocateState.FAILED, null);
        } else {
            mCityListAdapter.updateLocateState(LocateState.SUCCESS, locCity);
        }
    }

    @Override
    public void onRegecodeGet(PositionEntity entity) {
        String locCity = entity.city;
        if (locCity.equals("")){
            mCityListAdapter.updateLocateState(LocateState.FAILED, null);
        } else {
            mCityListAdapter.updateLocateState(LocateState.SUCCESS, locCity);
        }

    }


    /**
     * a-z排序
     */
    private class CityComparator implements Comparator<CityInfo> {
        @Override
        public int compare(CityInfo lhs, CityInfo rhs) {
            String a = lhs.getCityPinYin().substring(0, 1);
            String b = rhs.getCityPinYin().substring(0, 1);
            return a.compareTo(b);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationTask.onDestroy();
    }
}
