package com.tdr.wisdome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tdr.wisdome.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 热门城市列表适配器
 * Created by Linus_Xie on 2016/8/5.
 */
public class HotCityGridAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mCities;

    public HotCityGridAdapter(Context context) {
        this.mContext = context;
        mCities = new ArrayList<>();
        mCities.add("北京");
        mCities.add("上海");
        mCities.add("广州");
        mCities.add("深圳");
        mCities.add("杭州");
        mCities.add("南京");
        mCities.add("天津");
        mCities.add("武汉");
        mCities.add("重庆");
    }

    public class ViewHolder {
        public TextView text_hotCityName;
    }

    @Override
    public int getCount() {
        return mCities == null ? 0 : mCities.size();
    }

    @Override
    public String getItem(int position) {
        return mCities == null ? null : mCities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.hotcity_item, null);
            holder = new ViewHolder();
            holder.text_hotCityName = (TextView) convertView.findViewById(R.id.text_hotCityName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.text_hotCityName.setText(mCities.get(position));
        return convertView;
    }
}
