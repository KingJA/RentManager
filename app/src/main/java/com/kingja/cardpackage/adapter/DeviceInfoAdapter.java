package com.kingja.cardpackage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingja.cardpackage.db.DbDaoXutils3;
import com.kingja.cardpackage.entiy.Basic_Dictionary_Kj;
import com.kingja.cardpackage.entiy.ChuZuWu_DeviceLists;
import com.kingja.cardpackage.util.TimeUtil;
import com.tdr.wisdome.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description：TODO
 * Create Time：2016/8/18 16:12
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class DeviceInfoAdapter extends BaseLvAdapter<ChuZuWu_DeviceLists.ContentBean> {
    private Map<String, String> typeMap = new HashMap<>();
    private List<Basic_Dictionary_Kj> typeList;
    public DeviceInfoAdapter(Context context, List<ChuZuWu_DeviceLists.ContentBean> list) {
        super(context, list);
        initDeviceType();
    }
    private void initDeviceType() {

        typeList = (List<Basic_Dictionary_Kj>) DbDaoXutils3.getInstance().selectAllWhere(Basic_Dictionary_Kj.class, "COLUMNCODE", "DEVICETYPE");
        for (Basic_Dictionary_Kj bean : typeList) {
            typeMap.put(bean.getCOLUMNVALUE(), bean.getCOLUMNCOMMENT());
        }
    }
    @Override
    public View simpleGetView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_device_info, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvdeviceinfo.setText(list.get(position).getDEVICENAME() + "  (" + list.get(position).getDEVICECODE() + ")");
        viewHolder.tvdevicetype.setText( typeMap.get(list.get(position).getDEVICETYPE()) );
        if ("32770".equals(list.get(position).getDEVICETYPE())) {
            viewHolder.ivstatus.setBackgroundResource(R.drawable.bg_cycle_on);
        }else{
            viewHolder.ivstatus.setBackgroundResource(TimeUtil.isOneDay(list.get(position).getDEVICETIME())? R.drawable.bg_cycle_on: R.drawable.bg_cycle_off);
        }

        return convertView;
    }


    public class ViewHolder {
        public final ImageView ivstatus;
        public final TextView tvdeviceinfo;
        public final TextView tvdevicetype;
        public final View root;

        public ViewHolder(View root) {
            ivstatus = (ImageView) root.findViewById(R.id.iv_status);
            tvdeviceinfo = (TextView) root.findViewById(R.id.tv_device_info);
            tvdevicetype = (TextView) root.findViewById(R.id.tv_device_type);
            this.root = root;
        }
    }
}
