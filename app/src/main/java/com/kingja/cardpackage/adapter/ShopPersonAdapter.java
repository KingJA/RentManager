package com.kingja.cardpackage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.kingja.cardpackage.entiy.ShangPu_EmployeeList;
import com.tdr.wisdome.R;

import java.util.List;

/**
 * Description：TODO
 * Create Time：2016/8/16 13:58
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ShopPersonAdapter extends BaseRvAdaper<ShangPu_EmployeeList.ContentBean.PERSONNELINFOLISTBean> {
    private OnShopPersonDeliteListener onShopPersonDeliteListener;

    public ShopPersonAdapter(Context context, List<ShangPu_EmployeeList.ContentBean.PERSONNELINFOLISTBean> list) {
        super(context, list);
    }

    @Override
    protected ViewHolder createViewHolder(View v) {
        return new PersonManagerViewHolder(v);
    }

    @Override
    protected int getItemView() {
        return R.layout.item_person_apply;
    }

    @Override
    protected void bindHolder(ViewHolder baseHolder, ShangPu_EmployeeList.ContentBean.PERSONNELINFOLISTBean bean, final int position) {
        PersonManagerViewHolder holder = (PersonManagerViewHolder) baseHolder;
        holder.tv_name.setText(bean.getNAME());
        holder.tv_cardId.setText("身份证号: " + bean.getIDENTITYCARD());
        holder.tv_phone.setText(bean.getPHONENUM());
        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onShopPersonDeliteListener != null) {
                    onShopPersonDeliteListener.OnShopPersonDelite(list.get(position).getLISTID(),position);
                }
            }
        });
    }


    public void setOnShopPersonDeliteListener(OnShopPersonDeliteListener onShopPersonDeliteListener) {
        this.onShopPersonDeliteListener = onShopPersonDeliteListener;
    }

    public interface OnShopPersonDeliteListener {
        void OnShopPersonDelite(String listId, int position);
    }

    public void deleteItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }


    class PersonManagerViewHolder extends ViewHolder {
        public TextView tv_cardId;
        public TextView tv_phone;
        public TextView tv_name;
        public TextView tv_delete;

        public PersonManagerViewHolder(View itemView) {
            super(itemView);
            tv_cardId = (TextView) itemView.findViewById(R.id.tv_cardId);
            tv_phone = (TextView) itemView.findViewById(R.id.tv_phone);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_delete = (TextView) itemView.findViewById(R.id.tv_delete);
        }
    }
}
