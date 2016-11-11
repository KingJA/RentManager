package com.kingja.cardpackage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kingja.cardpackage.entiy.Basic_Dictionary_Kj;
import com.kingja.cardpackage.entiy.RentBean;
import com.tdr.wisdome.R;

import java.util.List;

/**
 * Description：TODO
 * Create Time：2016/8/5 14:32
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class AdminTypeAdapter extends BaseLvAdapter<Basic_Dictionary_Kj> {
    public AdminTypeAdapter(Context context, List<Basic_Dictionary_Kj> list) {
        super(context, list);
    }

    @Override
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.single_tv, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvroomnumber.setText(list.get(position).getCOLUMNCOMMENT());

        return convertView;
    }


    public class ViewHolder {
        public final TextView tvroomnumber;
        public final View root;

        public ViewHolder(View root) {
            tvroomnumber = (TextView) root.findViewById(R.id.tv_room_number);
            this.root = root;
        }
    }
}
