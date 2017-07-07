package com.kingja.cardpackage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.kingja.cardpackage.entiy.Police_Policemeninfo;
import com.tdr.wisdome.R;

import java.util.List;

/**
 * Description：TODO
 * Create Time：2016/8/16 13:58
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class PoliceSearchAdapter extends RvAdaper<Police_Policemeninfo.ContentBean> {


    public PoliceSearchAdapter(Context context, List<Police_Policemeninfo.ContentBean> list) {
        super(context, list);
    }

    @Override
    protected RvAdaper.ViewHolder createViewHolder(View v) {
        return new ViewHolder(v);
    }

    @Override
    protected int getItemView() {
        return R.layout.item_police_search;
    }

    @Override
    protected void bindHolder(RvAdaper.ViewHolder baseHolder, Police_Policemeninfo.ContentBean bean, final int position) {
        final ViewHolder holder = (ViewHolder) baseHolder;
        holder.tv_police_name.setText(bean.getCHINESENAME());
        holder.tv_police_phone.setText(bean.getPHONE());
        holder.tv_police_number.setText(bean.getPOLICENO());
        holder.tv_police_policestation.setText(bean.getSHPCS());

    }


    class ViewHolder extends RvAdaper.ViewHolder {
        public TextView tv_police_name;
        public TextView tv_police_phone;
        public TextView tv_police_number;
        public TextView tv_police_policestation;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_police_name = (TextView) itemView.findViewById(R.id.tv_police_name);
            tv_police_phone = (TextView) itemView.findViewById(R.id.tv_police_phone);
            tv_police_number = (TextView) itemView.findViewById(R.id.tv_police_number);
            tv_police_policestation = (TextView) itemView.findViewById(R.id.tv_police_policestation);
        }
    }
}
