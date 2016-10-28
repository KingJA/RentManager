package com.kingja.cardpackage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingja.cardpackage.entiy.AlarmList;
import com.kingja.cardpackage.entiy.GetUserMessage;
import com.kingja.cardpackage.util.TimeUtil;
import com.tdr.wisdome.R;

import java.util.List;

/**
 * Description：TODO
 * Create Time：2016/8/5 14:32
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class AlarmMineAdapter extends BaseLvAdapter<GetUserMessage.ContentBean> {
    private String cardName;

    public AlarmMineAdapter(Context context, List<GetUserMessage.ContentBean> list, String cardName) {
        super(context, list);
        this.cardName = cardName;
    }

    @Override
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_alarm_mine, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvalarmtype.setText(getCardName( list.get(position).getCardCode()));
        viewHolder.tvalarmtime.setText(getTime(list.get(position).getCreateTime()));
        viewHolder.tvalarmmsg.setText(list.get(position).getMessage());
        viewHolder.iv_readed.setVisibility(list.get(position).getIsRead()==1?View.INVISIBLE:View.VISIBLE);
        return convertView;
    }

    private String getCardName(String cardCode) {
        if (cardCode == null) {
            cardCode="";
        }
        String result="";
        switch (cardCode) {
            case "1001" :
                result="我的住房";
                break;
             case "1002" :
                 result="我的出租房";
                break;
             case "1007" :
                 result="出租房代管";
                break;
            default:
                result=cardCode;
                break;
        }
        return result;
    }

    public void reset() {
        this.list.clear();
    }

    public class ViewHolder {
        public final TextView tvalarmtype;
        public final TextView tvalarmtime;
        public final TextView tvalarmmsg;
        public final ImageView iv_readed;
        public final View root;

        public ViewHolder(View root) {
            tvalarmtype = (TextView) root.findViewById(R.id.tv_alarm_type);
            tvalarmtime = (TextView) root.findViewById(R.id.tv_alarm_time);
            tvalarmmsg = (TextView) root.findViewById(R.id.tv_alarm_msg);
            iv_readed = (ImageView) root.findViewById(R.id.iv_readed);
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
