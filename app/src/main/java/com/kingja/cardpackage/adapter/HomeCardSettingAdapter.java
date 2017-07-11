package com.kingja.cardpackage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private boolean isEditMode;
    private OnAddHomeCardListener onAddHomeCardListener;

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
        if (isEditMode) {
            boolean isHomeApp = list.get(position).getISHOMEAPP() == 1;
            viewHolder.ll_card_backgroud.setBackgroundColor(context.getResources().getColor(R.color.bg_gray_kj));
            viewHolder.iv_card_ishomeapp.setVisibility(View.VISIBLE);
            viewHolder.iv_card_ishomeapp.setBackgroundResource(isHomeApp ? R.drawable
                    .card_selected : R.drawable.card_add);
            if (isHomeApp) {
                viewHolder.iv_card_ishomeapp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onAddHomeCardListener != null) {
                            onAddHomeCardListener.onAddHomeCard(list.get(position).getCARDCODE(),list.get(position).getCARDNAME());
                        }
                    }
                });
            }

        } else {
            viewHolder.iv_card_ishomeapp.setVisibility(View.GONE);
            viewHolder.ll_card_backgroud.setBackgroundColor(context.getResources().getColor(R.color.bg_white));
        }


        return convertView;
    }

    public void setEditMode(boolean isEditMode) {
        this.isEditMode = isEditMode;
        notifyDataSetChanged();
    }

    public void setHomeCardStatus(String cardCode, int status) {
        for (Application_List.ContentBean.CARDPROPERTYBean card : list) {
            if (cardCode.equals(card.getCARDCODE())) {
                card.setISHOMEAPP(status);
            }
        }
        notifyDataSetChanged();
    }


    public class ViewHolder {
        public final TextView tv_card_name;
        public final ImageView iv_card_img;
        public final ImageView iv_card_ishomeapp;
        public final LinearLayout ll_card_backgroud;
        public final View root;

        public ViewHolder(View root) {
            tv_card_name = (TextView) root.findViewById(R.id.tv_card_name);
            iv_card_img = (ImageView) root.findViewById(R.id.iv_card_img);
            iv_card_ishomeapp = (ImageView) root.findViewById(R.id.iv_card_ishomeapp);
            ll_card_backgroud = (LinearLayout) root.findViewById(R.id.ll_card_backgroud);
            this.root = root;
        }
    }

    public interface OnAddHomeCardListener {
        void onAddHomeCard(String cardCode,String cardName);
    }

    public void setOnAddHomeCardListener(OnAddHomeCardListener onAddHomeCardListener) {
        this.onAddHomeCardListener = onAddHomeCardListener;
    }
}
