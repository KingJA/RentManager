package com.kingja.cardpackage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.kingja.cardpackage.entiy.ShangPu_DeviceLists;
import com.kingja.cardpackage.util.DeviceTypeUtil;
import com.kingja.cardpackage.util.TempConstants;
import com.tdr.wisdome.R;

import java.util.List;
import java.util.Map;

/**
 * Description：TODO
 * Create Time：2016/8/16 13:58
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ShopDeviceAdapter extends BaseRvAdaper<ShangPu_DeviceLists.ContentBean> {
    private OnShopDeviceDeliteListener onShopDeviceDeliteListener;
    private final Map<String, String> typeMap;

    public ShopDeviceAdapter(Context context, List<ShangPu_DeviceLists.ContentBean> list) {
        super(context, list);
        typeMap = DeviceTypeUtil.getTypeMap(TempConstants.DEVICETYPE);
    }

    @Override
    protected ViewHolder createViewHolder(View v) {
        return new PersonManagerViewHolder(v);
    }

    @Override
    protected int getItemView() {
        return R.layout.item_person_device;
    }

    @Override
    protected void bindHolder(ViewHolder baseHolder, ShangPu_DeviceLists.ContentBean bean, final int position) {
        PersonManagerViewHolder holder = (PersonManagerViewHolder) baseHolder;
        holder.tv_deviceName.setText(bean.getDEVICENAME());
        holder.tv_deviceType.setText(typeMap.get(bean.getDEVICETYPE()));
        holder.tv_deviceCode.setText(bean.getDEVICECODE());


        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onShopDeviceDeliteListener != null) {
                    onShopDeviceDeliteListener.onShopDeviceDelite(position, list.get(position).getDEVICEID(), list.get(position).getDEVICETYPE(), list.get(position).getDEVICECODE());
                }
            }
        });
    }


    public void setOnShopDeviceDeliteListener(OnShopDeviceDeliteListener onShopDeviceDeliteListener) {
        this.onShopDeviceDeliteListener = onShopDeviceDeliteListener;
    }

    public interface OnShopDeviceDeliteListener {
        void onShopDeviceDelite(int position, String deviceId, String deviceType, String deviceCode);
    }

    public void deleteItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }


    class PersonManagerViewHolder extends ViewHolder {
        public TextView tv_deviceName;
        public TextView tv_deviceType;
        public TextView tv_deviceCode;
        public TextView tv_delete;

        public PersonManagerViewHolder(View itemView) {
            super(itemView);
            tv_deviceName = (TextView) itemView.findViewById(R.id.tv_deviceName);
            tv_deviceType = (TextView) itemView.findViewById(R.id.tv_deviceType);
            tv_deviceCode = (TextView) itemView.findViewById(R.id.tv_deviceCode);
            tv_delete = (TextView) itemView.findViewById(R.id.tv_delete);
        }
    }
}
