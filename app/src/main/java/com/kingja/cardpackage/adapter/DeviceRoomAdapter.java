package com.kingja.cardpackage.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kingja.cardpackage.entiy.RentBean;
import com.tdr.wisdome.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description：TODO
 * Create Time：2016/8/5 14:32
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class DeviceRoomAdapter extends BaseSimpleAdapter<RentBean.RoomListBean> {
    private OnExplandListener onExplandListener;
    private Map<Integer, DeviceInfoAdapter> adapterMap = new HashMap<>();

    public DeviceRoomAdapter(Context context, List<RentBean.RoomListBean> list) {
        super(context, list);
    }

    @Override
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_device_room, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvdeviceroom.setText("房间 " + list.get(position).getROOMNO());
        boolean expland = list.get(position).isExpland();
        if (expland) {
            viewHolder.ivdevicearrow.setBackgroundResource(R.drawable.arrow_right);
            if (getAdapter(position) != null) {
                viewHolder.lvdevice.setAdapter(getAdapter(position));
            }
            viewHolder.lvdevice.setVisibility(View.VISIBLE);

        } else {
            viewHolder.ivdevicearrow.setBackgroundResource(R.drawable.arrow_down);
            viewHolder.lvdevice.setVisibility(View.GONE);
        }
        return convertView;
    }

    public void saveAdapter(int position, DeviceInfoAdapter adapter) {
        this.adapterMap.put(position, adapter);
    }

    public DeviceInfoAdapter getAdapter(int position) {
        return this.adapterMap.get(position);
    }

    public void setVisibility(boolean expand, int position) {
        list.get(position).setExpland(expand);
        this.notifyDataSetChanged();
    }

    public interface OnExplandListener {
        void onExpland(String roomid, String roomno, ListView lv, ImageView iv, int position, boolean expland);
    }

    public void setOnExplandListener(OnExplandListener onExplandListener) {

        this.onExplandListener = onExplandListener;
    }

    public class ViewHolder {
        public final TextView tvdeviceroom;
        public final ImageView ivdevicearrow;
        public final LinearLayout llroom;
        public final ListView lvdevice;
        public final LinearLayout lldevice;
        public final RelativeLayout rl_room;
        public final View root;

        public ViewHolder(View root) {
            rl_room = (RelativeLayout) root.findViewById(R.id.rl_room);
            tvdeviceroom = (TextView) root.findViewById(R.id.tv_device_room);
            ivdevicearrow = (ImageView) root.findViewById(R.id.iv_device_arrow);
            llroom = (LinearLayout) root.findViewById(R.id.ll_room);
            lvdevice = (ListView) root.findViewById(R.id.lv_device);
            lldevice = (LinearLayout) root.findViewById(R.id.ll_device);
            this.root = root;
        }
    }
}
