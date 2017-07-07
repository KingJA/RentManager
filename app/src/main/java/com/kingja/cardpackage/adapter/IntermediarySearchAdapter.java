package com.kingja.cardpackage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingja.cardpackage.entiy.Agency_List;
import com.kingja.cardpackage.entiy.Police_Policemeninfo;
import com.tdr.wisdome.R;

import java.util.List;

/**
 * Description：TODO
 * Create Time：2016/8/16 13:58
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class IntermediarySearchAdapter extends RvAdaper<Agency_List.ContentBean> {


    public IntermediarySearchAdapter(Context context, List<Agency_List.ContentBean> list) {
        super(context, list);
    }

    @Override
    protected RvAdaper.ViewHolder createViewHolder(View v) {
        return new ViewHolder(v);
    }

    @Override
    protected int getItemView() {
        return R.layout.item_intermediary_search;
    }

    @Override
    protected void bindHolder(RvAdaper.ViewHolder baseHolder, Agency_List.ContentBean bean, final int position) {
        final ViewHolder holder = (ViewHolder) baseHolder;
        holder.tv_agency_name.setText(bean.getAGENCYNAME());
        holder.tv_agency_owner.setText(bean.getOWNERNAME());
        holder.tv_agency_phone.setText(bean.getPHONE());
        holder.tv_agency_license.setText(bean.getLICENSE());
        holder.tv_agency_address.setText(bean.getADDRESS());

//        holder.iv_agency_isbind.setBackgroundResource(bean.get);

    }


    class ViewHolder extends RvAdaper.ViewHolder {
        public TextView tv_agency_name;
        public TextView tv_agency_owner;
        public TextView tv_agency_phone;
        public TextView tv_agency_license;
        public TextView tv_agency_address;
        public ImageView iv_agency_isbind;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_agency_name = (TextView) itemView.findViewById(R.id.tv_agency_name);
            tv_agency_owner = (TextView) itemView.findViewById(R.id.tv_agency_owner);
            tv_agency_phone = (TextView) itemView.findViewById(R.id.tv_agency_phone);
            tv_agency_license = (TextView) itemView.findViewById(R.id.tv_agency_license);
            tv_agency_address = (TextView) itemView.findViewById(R.id.tv_agency_address);
            iv_agency_isbind = (ImageView) itemView.findViewById(R.id.iv_agency_isbind);
        }
    }

}
