package com.kingja.cardpackage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingja.cardpackage.entiy.Application_List;
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
public class HomeCardSettingAdapter extends BaseLvAdapter<Application_List.ContentBean.CARDPROPERTYBean> {

    public HomeCardSettingAdapter(Context context, List<Application_List.ContentBean.CARDPROPERTYBean> list) {
        super(context, list);
    }


    @Override
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_card_setting, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_card_name.setText(list.get(position).getCARDNAME());
        viewHolder.iv_card_img.setBackgroundResource(ResUtil.getCardRes(list.get(position).getCARDCODE()));
        viewHolder.iv_card_select.setBackgroundResource(list.get(position).getISHOMEAPP()==1?R.drawable.card_remove:R.drawable.card_add);

        return convertView;
    }


    public class ViewHolder {
        public final TextView tv_card_name;
        public final ImageView iv_card_img;
        public final ImageView iv_card_select;
        public final View root;

        public ViewHolder(View root) {
            tv_card_name = (TextView) root.findViewById(R.id.tv_card_name);
            iv_card_img = (ImageView) root.findViewById(R.id.iv_card_img);
            iv_card_select = (ImageView) root.findViewById(R.id.iv_card_select);
            this.root = root;
        }
    }
}
