package com.kingja.cardpackage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.kingja.cardpackage.entiy.ChuZuWu_AdminList;
import com.kingja.cardpackage.entiy.ChuZuWu_MenPaiAuthorizationList;
import com.tdr.wisdome.R;

import java.util.List;

/**
 * Description：TODO
 * Create Time：2016/8/16 13:58
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class AdminRvAdapter extends BaseRvAdaper<ChuZuWu_AdminList.ContentBean.AdminListBean> {
    private OnDeliteItemListener onDeliteItemListener;

    public AdminRvAdapter(Context context, List<ChuZuWu_AdminList.ContentBean.AdminListBean> list) {
        super(context, list);
    }

    @Override
    protected ViewHolder createViewHolder(View v) {
        return new PersonManagerViewHolder(v);
    }

    @Override
    protected int getItemView() {
        return R.layout.item_admin;
    }

    @Override
    protected void bindHolder(ViewHolder baseHolder, ChuZuWu_AdminList.ContentBean.AdminListBean bean, final int position) {
        PersonManagerViewHolder holder = (PersonManagerViewHolder) baseHolder;
        holder.tv_name.setText(bean.getNAME());
        holder.tv_cardId.setText("身份证号: " + bean.getIDENTITYCARD());
        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeliteItemListener != null) {
                    onDeliteItemListener.onDeliteItem(list.get(position).getIDENTITYCARD(),position);
                }
            }
        });
    }

    public void setOnDeliteItemListener(OnDeliteItemListener onDeliteItemListener) {
        this.onDeliteItemListener = onDeliteItemListener;
    }

    public interface OnDeliteItemListener {
        void onDeliteItem(String cardId, int position);
    }

    public void deleteItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }


    class PersonManagerViewHolder extends ViewHolder {
        public TextView tv_cardId;
        public TextView tv_name;
        public TextView tv_delete;

        public PersonManagerViewHolder(View itemView) {
            super(itemView);
            tv_cardId = (TextView) itemView.findViewById(R.id.tv_cardId);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_delete = (TextView) itemView.findViewById(R.id.tv_delete);
        }
    }
}
