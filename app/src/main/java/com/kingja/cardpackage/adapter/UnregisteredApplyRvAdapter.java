package com.kingja.cardpackage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingja.cardpackage.entiy.ChuZuWu_AgencySelfReportingList;
import com.kingja.cardpackage.util.NoDoubleClickListener;
import com.kingja.cardpackage.util.StringUtil;
import com.tdr.wisdome.R;

import java.util.List;

/**
 * Description：人员申报
 * Create Time：2016/8/16 13:58
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class UnregisteredApplyRvAdapter extends BaseRvAdaper<ChuZuWu_AgencySelfReportingList.ContentBean> {
    private OnDeliteItemListener onDeliteItemListener;

    public UnregisteredApplyRvAdapter(Context context, List<ChuZuWu_AgencySelfReportingList.ContentBean> list) {
        super(context, list);
    }

    @Override
    protected BaseRvAdaper.ViewHolder createViewHolder(View v) {
        return new ViewHolder(v);
    }

    @Override
    protected int getItemView() {
        return R.layout.item_unregistered_apply;
    }

    @Override
    protected void bindHolder(BaseRvAdaper.ViewHolder baseHolder, ChuZuWu_AgencySelfReportingList.ContentBean bean,
                              final int position) {
        final ViewHolder holder = (ViewHolder) baseHolder;
        holder.tv_name.setText(bean.getNAME()+"(" + bean.getHEIGHT() + "cm)");
        holder.tv_cardId.setText("身份证号: " + StringUtil.hideID(bean.getIDENTITYCARD()));
        holder.tv_phone.setText(bean.getPHONE() + "(" + bean.getROOMNO() + "房间)");
        holder.tv_area.setText("辖区: " + bean.getSSQYMC());
        holder.tv_address.setText("地址: " + bean.getADDRESS());
        holder.iv_delete.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (onDeliteItemListener != null) {
                    onDeliteItemListener.onDeliteItem(list.get(position).getLISTID(), position);
                }
            }
        });
    }


    public void setOnDeliteItemListener(OnDeliteItemListener onDeliteItemListener) {
        this.onDeliteItemListener = onDeliteItemListener;
    }

    public interface OnDeliteItemListener {
        void onDeliteItem(String listId, int position);
    }

    public void deleteItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        if (position != list.size()) {
            notifyItemRangeChanged(position, list.size() - position);
        }
    }


    class ViewHolder extends BaseRvAdaper.ViewHolder {
        public TextView tv_cardId;
        public TextView tv_phone;
        public TextView tv_name;
        public TextView tv_address;
        public TextView tv_area;
        public ImageView iv_delete;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_cardId = (TextView) itemView.findViewById(R.id.tv_cardId);
            tv_phone = (TextView) itemView.findViewById(R.id.et_phone);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_address = (TextView) itemView.findViewById(R.id.tv_address);
            tv_area = (TextView) itemView.findViewById(R.id.tv_area);
            iv_delete = (ImageView) itemView.findViewById(R.id.iv_delete);
        }
    }
}
