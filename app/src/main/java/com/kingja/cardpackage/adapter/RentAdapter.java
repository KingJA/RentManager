package com.kingja.cardpackage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingja.cardpackage.entiy.RentBean;
import com.tdr.wisdome.R;

import java.util.List;

/**
 * Description：TODO
 * Create Time：2016/8/5 14:32
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class RentAdapter extends BaseLvAdapter<RentBean> {
    public RentAdapter(Context context, List<RentBean> list) {
        super(context, list);
    }

    @Override
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_house_list, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.ivicon.setBackgroundResource(R.drawable.bg_rent);
        viewHolder.tvhouseName.setText(list.get(position).getHOUSENAME());
        viewHolder.ivhouseAddress.setText(list.get(position).getADDRESS());

        return convertView;
    }


    public class ViewHolder {
        public final ImageView ivicon;
        public final TextView tvhouseName;
        public final TextView ivhouseAddress;
        public final View root;

        public ViewHolder(View root) {
            ivicon = (ImageView) root.findViewById(R.id.iv_icon);
            tvhouseName = (TextView) root.findViewById(R.id.tv_houseName);
            ivhouseAddress = (TextView) root.findViewById(R.id.iv_houseAddress);
            this.root = root;
        }
    }
}
