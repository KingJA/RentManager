package com.tdr.wisdome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdr.wisdome.R;
import com.tdr.wisdome.model.Item;
import com.tdr.wisdome.view.SpanVariableGridView;

import java.util.List;

/**
 * Created by Linus_Xie on 2016/8/4.
 */
public class ItemAdapter extends ArrayAdapter<Item> implements SpanVariableGridView.CalculateChildrenPosition {

    public final class ItemViewHolder {
        public TextView itemName;
        public TextView itemDescription;
        public ImageView itemBg;
    }

    private Context mContext;
    private LayoutInflater mLayoutInflater = null;

    private View.OnClickListener onRemoveItemListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Integer position = (Integer) v.getTag();
            removeItem(getItem(position));
        }
    };

    public void insertItem(Item item, int where) {
        if (where < 0 || where > (getCount() - 1)) {
            return;
        }
        insert(item, where);
    }

    public boolean removeItem(Item item) {
        remove(item);
        return true;
    }

    public ItemAdapter(Context context, List<Item> plugins) {
        super(context, R.layout.cooldrag_item, plugins);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ItemViewHolder itemViewHolder;

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.cooldrag_item, parent, false);

            itemViewHolder = new ItemViewHolder();
            itemViewHolder.itemName = (TextView) convertView.findViewById(R.id.text_title);
            itemViewHolder.itemDescription = (TextView) convertView.findViewById(R.id.text_description);
            itemViewHolder.itemBg = (ImageView) convertView.findViewById(R.id.imageViewIcon);
            convertView.setTag(itemViewHolder);
        } else {
            itemViewHolder = (ItemViewHolder) convertView.getTag();
        }

        final Item item = getItem(position);

        SpanVariableGridView.LayoutParams lp = new SpanVariableGridView.LayoutParams(convertView.getLayoutParams());
        lp.span = item.getSpans();
        convertView.setLayoutParams(lp);

        itemViewHolder.itemName.setText(item.getTitle());
        itemViewHolder.itemDescription.setText(item.getDescription());
        itemViewHolder.itemBg.setBackgroundResource(item.getIcon());

        return convertView;
    }

    @Override
    public void onCalculatePosition(View view, int position, int row, int column) {

    }
}
