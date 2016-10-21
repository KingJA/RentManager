package com.kingja.cardpackage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingja.cardpackage.entiy.ShopBean;
import com.tdr.wisdome.R;

import java.util.List;

/**
 * Description：TODO
 * Create Time：2016/8/5 14:32
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ShopAdapter extends BaseLvAdapter<ShopBean> {
    public ShopAdapter(Context context, List<ShopBean> list) {
        super(context, list);
    }

    @Override
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_shop_list, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvhouseName.setText(list.get(position).getSHOPNAME());

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
