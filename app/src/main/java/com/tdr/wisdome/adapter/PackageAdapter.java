package com.tdr.wisdome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.tdr.wisdome.R;
import com.tdr.wisdome.model.PackageItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Linus_Xie on 2016/8/22.
 */
public class PackageAdapter extends BaseAdapter {
    private Context mContext;
    private List<PackageItem> mData;
    private LayoutInflater mInflater;

    private List<PackageItem> data1 = new ArrayList<>();
    private List<PackageItem> data2 = new ArrayList<>();
    private List<PackageItem> temp1 = new ArrayList<>();
    private List<PackageItem> temp2 = new ArrayList<>();

    public PackageAdapter(Context mContext, List<PackageItem> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mInflater = LayoutInflater.from(mContext);
    }

    public class ViewHolder {
        public TextView text_tag;
        public SwipeLayout sample;
        public TextView text_deal;
        public ImageView image_cardIcon;
        public TextView text_cardName;
    }

    @Override
    public int getCount() {
        if (null != mData) {
            return mData.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (null != mData && position < getCount()) {
            return mData.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        // 常见的优化ViewHolder
        ViewHolder viewHolder = null;
        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.pack_item, null);
            viewHolder = new ViewHolder();
            viewHolder.text_tag = (TextView) convertView.findViewById(R.id.text_tag);
            viewHolder.sample = (SwipeLayout) convertView.findViewById(R.id.sample);
            viewHolder.sample.setShowMode(SwipeLayout.ShowMode.PullOut);
            viewHolder.sample.addDrag(SwipeLayout.DragEdge.Right, viewHolder.sample.findViewWithTag("Bottom1"));
            viewHolder.text_deal = (TextView) convertView.findViewById(R.id.text_deal);
            viewHolder.image_cardIcon = (ImageView) convertView.findViewById(R.id.image_cardIcon);
            viewHolder.text_cardName = (TextView) convertView.findViewById(R.id.text_cardName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 获取数据
        PackageItem itemEntity = (PackageItem) getItem(position);
        viewHolder.text_cardName.setText(itemEntity.getContent());
        viewHolder.image_cardIcon.setBackgroundResource(itemEntity.getmIcon());

        if (mData.get(position).getTitle().equals("已拥有")) {
            data1.add(mData.get(position));
        } else {
            data2.add(mData.get(position));
        }

        if (needTitle(position)) {
            // 显示标题并设置内容
            viewHolder.text_tag.setText(itemEntity.getTitle());
            viewHolder.text_tag.setVisibility(View.VISIBLE);
        } else {
            // 内容项隐藏标题
            viewHolder.text_tag.setVisibility(View.GONE);
        }

        if (mData.get(position).getTitle().equals("可添加")) {
            viewHolder.text_deal.setText("添加");
        } else {
            viewHolder.text_deal.setText("删除");
        }

        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.text_deal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalViewHolder.text_deal.getText().equals("删除")) {
                    data2.add(data1.get(position));
                    data1.remove(position);
                } else {
                    data1.add(data2.get(position - 1));
                    data2.remove(position - 1);
                }
                mData.clear();
                for (int i = 0; i < data1.size(); i++) {
                    mData.add(data1.get(i));
                }
                for (int i = 0; i < data2.size(); i++) {
                    mData.add(data2.get(i));
                }

                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    /**
     * 判断是否需要显示标题
     *
     * @param position
     * @return
     */
    private boolean needTitle(int position) {
        // 第一个肯定是分类
        if (position == 0) {
            return true;
        }

        // 边界处理
        if (position < 0) {
            return false;
        }

        // 当前  // 上一个
        PackageItem currentEntity = (PackageItem) getItem(position);
        PackageItem previousEntity = (PackageItem) getItem(position - 1);
        if (null == currentEntity || null == previousEntity) {
            return false;
        }

        String currentTitle = currentEntity.getTitle();
        String previousTitle = previousEntity.getTitle();
        if (null == previousTitle || null == currentTitle) {
            return false;
        }

        // 当前item分类名和上一个item分类名不同，则表示两item属于不同分类
        if (currentTitle.equals(previousTitle)) {
            return false;
        }

        return true;
    }
}
