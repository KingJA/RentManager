package com.tdr.wisdome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tdr.wisdome.R;
import com.tdr.wisdome.model.CardInfo;

import java.util.List;

/**
 * Created by Linus_Xie on 2016/8/3.
 */
public class CardAdapter extends ArrayAdapter<CardInfo> implements View.OnClickListener {

    private Context mContext;
    private LayoutInflater mInflater;

    public CardAdapter(Context context, int resource, List<CardInfo> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public class ViewHolder {
        public LinearLayout linear_card;
        public TextView text_card;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        CardInfo mInfo = getItem(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_card, null);
            holder = new ViewHolder();
            holder.linear_card = (LinearLayout) convertView.findViewById(R.id.linear_card);
            holder.text_card = (TextView) convertView.findViewById(R.id.text_card);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // holder.image_card.setImageBitmap(Utils.stringtoBitmap(mInfo.getCardBg()));
        holder.text_card.setText(mInfo.getCardName());

        return convertView;
    }

    @Override
    public void onClick(View v) {

    }

}
