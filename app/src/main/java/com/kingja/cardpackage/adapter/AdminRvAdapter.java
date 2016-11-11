package com.kingja.cardpackage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingja.cardpackage.db.DbDaoXutils3;
import com.kingja.cardpackage.entiy.Basic_Dictionary_Kj;
import com.kingja.cardpackage.entiy.ChuZuWu_AdminList;
import com.kingja.cardpackage.entiy.ChuZuWu_MenPaiAuthorizationList;
import com.kingja.cardpackage.ui.NoDoubleClickListener;
import com.tdr.wisdome.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description：TODO
 * Create Time：2016/8/16 13:58
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class AdminRvAdapter extends BaseRvAdaper<ChuZuWu_AdminList.ContentBean.AdminListBean> {
    private OnDeliteItemListener onDeliteItemListener;
    private Map<String,String> typeMap=new HashMap<>();

    public AdminRvAdapter(Context context, List<ChuZuWu_AdminList.ContentBean.AdminListBean> list) {
        super(context, list);
        List<Basic_Dictionary_Kj> adminTypeList = (List<Basic_Dictionary_Kj>) DbDaoXutils3.getInstance().selectAllWhere(Basic_Dictionary_Kj.class, "COLUMNCODE", "ADMINTYPE");
        for (Basic_Dictionary_Kj bean : adminTypeList) {
            typeMap.put(bean.getCOLUMNVALUE(),bean.getCOLUMNCOMMENT());
        }
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
        holder.tv_type.setText(typeMap.get(bean.getADMINTYPE()+""));
        holder.tv_cardId.setText("身份证号: " + bean.getIDENTITYCARD());
        holder.iv_delete.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
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
        public TextView tv_type;
        public ImageView iv_delete;

        public PersonManagerViewHolder(View itemView) {
            super(itemView);
            tv_cardId = (TextView) itemView.findViewById(R.id.tv_cardId);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
            iv_delete = (ImageView) itemView.findViewById(R.id.iv_delete);
        }
    }
}
