package com.tdr.wisdome.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdr.wisdome.R;

import java.util.List;

/**
 * 主界面卡列表
 * Created by Linus_Xie on 2016/8/30.
 */
public class CardMainAdapter extends BaseCardAdapter {
    public CardMainAdapter(Context context, List<String> list) {
        super(context, list);
    }

    @Override
    protected View onGetView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_card_main, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvcardName.setText(getCardName(list.get(i)));
        viewHolder.ivicon.setBackgroundResource(getCardImage(list.get(i)));
        return convertView;
    }

    public class ViewHolder {
        public final ImageView ivicon;
        public final TextView tvcardName;
        public final View root;

        public ViewHolder(View root) {
            ivicon = (ImageView) root.findViewById(R.id.iv_icon);
            tvcardName = (TextView) root.findViewById(R.id.tv_cardName);
            this.root = root;
        }
    }

    public void refreshData(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}

