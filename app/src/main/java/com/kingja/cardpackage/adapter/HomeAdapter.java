package com.kingja.cardpackage.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingja.cardpackage.util.AppUtil;
import com.tdr.wisdome.R;

/**
 * Description：TODO
 * Create Time：2016/10/21 13:05
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class HomeAdapter extends BaseAdapter {
    private Context context;
    private String[] largeTexts;
    private String[] smallTexts;
    private int[] homeImgs;
    private int totelHeight;
    private final int itemHeight;
    private ViewGroup.LayoutParams mLayoutParams;

    public HomeAdapter(Context context, String[] largeTexts, String[] smallTexts, int[] homeImgs,int totelHeight) {
        this.context = context;
        this.largeTexts = largeTexts;
        this.smallTexts = smallTexts;
        this.homeImgs = homeImgs;
        this.totelHeight = totelHeight;
        itemHeight = (int) ((totelHeight- 2*AppUtil.dp2px(10))/largeTexts.length);
    }

    @Override
    public int getCount() {
        return largeTexts.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_home, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvlarge.setText(largeTexts[position]);
        viewHolder.tvsmall.setText(smallTexts[position]);
        mLayoutParams = viewHolder.ivhomeimg.getLayoutParams();
        mLayoutParams.height=itemHeight;
        viewHolder.ivhomeimg.setLayoutParams(mLayoutParams);
        viewHolder.ivhomeimg.setImageResource(homeImgs[position]);
        return convertView;
    }

    public class ViewHolder {
        public final ImageView ivhomeimg;
        public final TextView tvlarge;
        public final TextView tvsmall;
        public final View root;

        public ViewHolder(View root) {
            ivhomeimg = (ImageView) root.findViewById(R.id.iv_home_img);
            tvlarge = (TextView) root.findViewById(R.id.tv_large);
            tvsmall = (TextView) root.findViewById(R.id.tv_small);
            this.root = root;
        }
    }
}
