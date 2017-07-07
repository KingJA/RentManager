package com.kingja.cardpackage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingja.cardpackage.entiy.RentBean;
import com.tdr.wisdome.R;

import java.util.List;

/**
 * Description：TODO
 * Create Time：2016/8/16 13:58
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class IntermediaryAdapter extends RvAdaper<RentBean> {


    public IntermediaryAdapter(Context context, List<RentBean> list) {
        super(context, list);
    }

    @Override
    protected ViewHolder createViewHolder(View v) {
        return new GiftViewHolder(v);
    }

    @Override
    protected int getItemView() {
        return R.layout.item_house_list;
    }

    @Override
    protected void bindHolder(ViewHolder baseHolder, RentBean bean, final int position) {
        final GiftViewHolder holder = (GiftViewHolder) baseHolder;
        holder.iv_icon.setBackgroundResource(R.drawable.bg_rent);
        holder.tv_houseName.setText(bean.getHOUSENAME());
        holder.iv_houseAddress.setText(bean.getADDRESS());

    }


    class GiftViewHolder extends ViewHolder {
        public TextView tv_houseName;
        public TextView iv_houseAddress;
        public ImageView iv_icon;

        public GiftViewHolder(View itemView) {
            super(itemView);
            tv_houseName = (TextView) itemView.findViewById(R.id.tv_houseName);
            iv_houseAddress = (TextView) itemView.findViewById(R.id.iv_houseAddress);
            iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
        }
    }

}
