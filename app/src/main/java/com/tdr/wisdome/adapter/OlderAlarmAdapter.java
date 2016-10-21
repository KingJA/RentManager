package com.tdr.wisdome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tdr.wisdome.R;
import com.tdr.wisdome.model.OlderAlarmInfo;

import java.util.List;

/**
 * 老人预警adapter
 * Created by Linus_Xie on 2016/8/24.
 */
public class OlderAlarmAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;

    private List<OlderAlarmInfo> list;

    public OlderAlarmAdapter(Context mContext, List<OlderAlarmInfo> list) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        this.list = list;
    }

    public class ViewHolder {
        public TextView text_alarmType;
        public TextView text_alarmTime;
        public TextView text_alarmContent;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.olderalarm_item, null);
            viewHolder.text_alarmType = (TextView) convertView.findViewById(R.id.text_alarmType);
            viewHolder.text_alarmTime = (TextView) convertView.findViewById(R.id.text_alarmTime);
            viewHolder.text_alarmContent = (TextView) convertView.findViewById(R.id.text_alarmContent);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (list.get(position).getAlertType().equals("0")) {
            viewHolder.text_alarmType.setText("超生活区");
        } else if (list.get(position).getAlertType().equals("1")) {
            viewHolder.text_alarmType.setText("超时未归");
        } else if (list.get(position).getAlertType().equals("2")) {
            viewHolder.text_alarmType.setText("走失报警");
        } else if (list.get(position).getAlertType().equals("3")) {
            viewHolder.text_alarmType.setText("发现申告");
        } else if (list.get(position).getAlertType().equals("4")) {
            viewHolder.text_alarmType.setText("佩戴设备电量低");
        } else if (list.get(position).getAlertType().equals("5")) {
            viewHolder.text_alarmType.setText("长时间静止不动");
        } else {
            viewHolder.text_alarmType.setText("设备异常");
        }
        viewHolder.text_alarmTime.setText(list.get(position).getAlertTime());
        viewHolder.text_alarmContent.setText(list.get(position).getAlertContent());

        return convertView;
    }
}
