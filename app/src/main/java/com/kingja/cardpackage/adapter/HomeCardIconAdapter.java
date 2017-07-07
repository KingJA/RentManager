package com.kingja.cardpackage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingja.cardpackage.entiy.User_HomePageApplication;
import com.kingja.cardpackage.util.ResUtil;
import com.tdr.wisdome.R;

import java.util.List;


/**
 * Description：TODO
 * Create Time：2016/8/5 14:32
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class HomeCardIconAdapter extends BaseLvAdapter<User_HomePageApplication.ContentBean> {

    public static final int LAST_POSITION = 10086;
    public static final String[] defaultCardCodes = {"1001", "1002", "1003", "1004", "3001", "3006", "2001"};

    public HomeCardIconAdapter(Context context, List<User_HomePageApplication.ContentBean> list) {
        super(context, list);
        if (list.size() == 0) {
            for (int i = 0; i < defaultCardCodes.length; i++) {
                User_HomePageApplication.ContentBean card = new User_HomePageApplication.ContentBean();
                card.setCARDCODE(defaultCardCodes[i]);
                list.add(card);
            }
        }
    }


    @Override
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_home_small_icon, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (position == getCount() - 1) {
            viewHolder.iv_home_card_icon.setBackgroundResource(R.drawable.card_hide);
            return convertView;
        }

        viewHolder.iv_home_card_icon.setBackgroundResource(ResUtil.getCardRes(list.get(position).getCARDCODE()));

        return convertView;
    }


    public class ViewHolder {
        public final ImageView iv_home_card_icon;
        public final View root;

        public ViewHolder(View root) {
            iv_home_card_icon = (ImageView) root.findViewById(R.id.iv_home_card_icon);
            this.root = root;
        }
    }

    @Override
    public int getCount() {
        int count = list.size();
        if (count > 7) {
            count = 7;
        }
        return count + 1;
    }

    @Override
    public long getItemId(int position) {
        if (position == (getCount() - 1)) {
            return LAST_POSITION;
        }
        return super.getItemId(position);
    }
}
