package com.tdr.wisdome.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdr.wisdome.R;

import java.util.List;

/**
 * 未添加 卡列表
 * Created by Linus_Xie on 2016/8/30.
 */
public class LeftCardAdapter extends BaseCardAdapter {
    private OnCardAddListener onCardAddListener;

    public LeftCardAdapter(Context context, List<String> list) {
        super(context, list);
    }

    @Override
    protected View onGetView(final int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_left_card, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvcardName.setText(getCardName(list.get(i)));
        viewHolder.ivicon.setBackgroundResource(getCardImage(list.get(i)));
        viewHolder.btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onCardAddListener != null) {
                    onCardAddListener.onCardAdd(list.get(i));
                }
            }
        });
        return convertView;
    }

    public class ViewHolder {
        public final Button btnadd;
        public final ImageView ivicon;
        public final TextView tvcardName;
        public final View root;

        public ViewHolder(View root) {
            ivicon = (ImageView) root.findViewById(R.id.iv_icon);
            tvcardName = (TextView) root.findViewById(R.id.tv_cardName);
            btnadd = (Button) root.findViewById(R.id.btn_add);
            this.root = root;
        }
    }

    public void setOnCardAddListener(OnCardAddListener onCardAddListener) {


        this.onCardAddListener = onCardAddListener;
    }

    public interface OnCardAddListener {
        void onCardAdd(String cardType);

    }

}
