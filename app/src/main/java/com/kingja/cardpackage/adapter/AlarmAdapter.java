package com.kingja.cardpackage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kingja.cardpackage.entiy.AlarmList;
import com.kingja.cardpackage.util.TimeUtil;
import com.tdr.wisdome.R;

import java.util.List;

/**
 * Description：TODO
 * Create Time：2016/8/5 14:32
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class AlarmAdapter extends BaseLvAdapter<AlarmList.ContentBean> {
    private String cardName;

    public AlarmAdapter(Context context, List<AlarmList.ContentBean> list, String cardName) {
        super(context, list);
        this.cardName = cardName;
    }

    @Override
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_alarm, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvalarmtype.setText(cardName);
        viewHolder.tvalarmtime.setText(getTime(list.get(position).getDEVICETIME()));
        viewHolder.tvalarmmsg.setText(list.get(position).getMESSAGETEXT());

        return convertView;
    }

    public void reset() {
        this.list.clear();
    }

    public class ViewHolder {
        public final TextView tvalarmtype;
        public final TextView tvalarmtime;
        public final TextView tvalarmmsg;
        public final View root;

        public ViewHolder(View root) {
            tvalarmtype = (TextView) root.findViewById(R.id.tv_alarm_type);
            tvalarmtime = (TextView) root.findViewById(R.id.tv_alarm_time);
            tvalarmmsg = (TextView) root.findViewById(R.id.tv_alarm_msg);
            this.root = root;
        }
    }

    public String getTime(String time) {
        if (TimeUtil.getFormatDate().equals(time.substring(0, 10))) {
            return time.substring(time.length() - 9);
        }
        return time;
    }
}
