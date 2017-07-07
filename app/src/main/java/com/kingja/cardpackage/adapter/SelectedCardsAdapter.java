package com.kingja.cardpackage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingja.cardpackage.entiy.User_HomePageApplication;
import com.kingja.cardpackage.util.ResUtil;
import com.kingja.recyclerviewhelper.BaseRvAdaper;
import com.kingja.recyclerviewhelper.RecyclerViewHelper;
import com.tdr.wisdome.R;

import java.util.Collections;
import java.util.List;

/**
 * Description：TODO
 * Create Time：2016/8/16 13:58
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class SelectedCardsAdapter extends BaseRvAdaper<User_HomePageApplication.ContentBean> implements
        RecyclerViewHelper.OnItemCallback {


    private OnRemoveCardListener onRemoveCardListener;

    public SelectedCardsAdapter(Context context, List<User_HomePageApplication.ContentBean> list) {
        super(context, list);
    }

    @Override
    protected ViewHolder createViewHolder(View v) {
        return new GiftViewHolder(v);
    }

    @Override
    protected int getItemView() {
        return R.layout.item_card_selected;
    }

    @Override
    protected void bindHolder(ViewHolder baseHolder, final User_HomePageApplication.ContentBean bean, final int position) {
        final GiftViewHolder holder = (GiftViewHolder) baseHolder;
        holder.tv_card_name.setText(bean.getCARDNAME());
        holder.iv_card_img.setBackgroundResource(ResUtil.getCardRes(bean.getCARDCODE()));
        holder.iv_card_select.setBackgroundResource(R.drawable.card_remove);
        holder.iv_card_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRemoveCardListener != null) {
                    onRemoveCardListener.onReove(position,bean.getCARDCODE());
                }
            }
        });
    }

    public void onMove(int fromPosition, int toPosition) {
        Collections.swap(list, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onSwipe(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        if (position != list.size()) {
            notifyItemRangeChanged(position, list.size() - position);
        }
    }


    class GiftViewHolder extends ViewHolder {
        public TextView tv_card_name;
        public ImageView iv_card_img;
        public ImageView iv_card_select;

        public GiftViewHolder(View itemView) {
            super(itemView);
            tv_card_name = (TextView) itemView.findViewById(R.id.tv_card_name);
            iv_card_img = (ImageView) itemView.findViewById(R.id.iv_card_img);
            iv_card_select = (ImageView) itemView.findViewById(R.id.iv_card_select);
        }
    }

    public interface OnRemoveCardListener {
        void onReove(int position, String cardCode);

    }

    public void setOnRemoveCardListener(OnRemoveCardListener onRemoveCardListener) {
        this.onRemoveCardListener = onRemoveCardListener;
    }
}
