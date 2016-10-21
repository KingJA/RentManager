package com.kingja.cardpackage.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kingja.cardpackage.entiy.Basic_Dictionary_Kj;
import com.tdr.wisdome.R;

import java.util.List;

/**
 * Description：TODO
 * Create Time：2016/8/18 10:32
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class SelectAdapter extends BaseLvAdapter<Basic_Dictionary_Kj> {
    private String value;

    public SelectAdapter(Context context, List<Basic_Dictionary_Kj> list, String value) {
        super(context, list);
        this.value = value;
    }

    @Override
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_tv_center, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvroomnumber.setText(list.get(position).getCOLUMNCOMMENT());

        if (selectPosition == -1) {
            viewHolder.tvroomnumber.setTextColor(ContextCompat.getColor(context, value.equals(list.get(position).getCOLUMNVALUE()) ? R.color.bg_blue : R.color.font_9));
        } else {
            viewHolder.tvroomnumber.setTextColor(ContextCompat.getColor(context, position == selectPosition ? R.color.bg_blue : R.color.font_9));
        }
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
