package com.kingja.cardpackage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingja.cardpackage.entiy.Basic_Dictionary_Kj;
import com.kingja.cardpackage.entiy.User_HomePageApplication;
import com.kingja.cardpackage.util.ResUtil;
import com.tdr.wisdome.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Description：TODO
 * Create Time：2016/8/5 14:32
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class HomeCardAdapter extends BaseLvAdapter<User_HomePageApplication.ContentBean> {

    public static final int LAST_POSITION = 10086;
    public static final String[] defaultCardNames = {"房东申报", "中介申报", "物业申报", "企业申报", "民警查询", "防范宣传",
            "手环申领"};
    public static final String[] defaultCardCodes = {"1001", "1002", "1003", "1004", "3001", "3006", "2001"};

    public HomeCardAdapter(Context context, List<User_HomePageApplication.ContentBean> list) {
        super(context, list);
        if (list.size() == 0) {
            for (int i = 0; i < defaultCardNames.length; i++) {
                User_HomePageApplication.ContentBean card = new User_HomePageApplication.ContentBean();
                card.setCARDCODE(defaultCardCodes[i]);
                card.setCARDNAME(defaultCardNames[i]);
                list.add(card);
            }
        }
    }


    @Override
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_card, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (position == getCount() - 1) {
            viewHolder.tv_card_name.setText("更多");
            viewHolder.iv_card_img.setBackgroundResource(R.drawable.card_home_more);
            return convertView;
        }

        viewHolder.tv_card_name.setText(list.get(position).getCARDNAME());
        viewHolder.iv_card_img.setBackgroundResource(ResUtil.getCardRes(list.get(position).getCARDCODE()));

        return convertView;
    }


    public class ViewHolder {
        public final TextView tv_card_name;
        public final ImageView iv_card_img;
        public final View root;

        public ViewHolder(View root) {
            tv_card_name = (TextView) root.findViewById(R.id.tv_card_name);
            iv_card_img = (ImageView) root.findViewById(R.id.iv_card_img);
            this.root = root;
        }
    }

    @Override
    public int getCount() {
        int count = list.size();
        if (count > 11) {
            count = 11;
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
