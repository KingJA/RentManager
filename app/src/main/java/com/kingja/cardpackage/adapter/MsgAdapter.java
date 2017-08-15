package com.kingja.cardpackage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingja.cardpackage.entiy.Police_Policemeninfo;
import com.kingja.cardpackage.entiy.User_MessageForShiMing;
import com.kingja.cardpackage.util.TimeUtil;
import com.tdr.wisdome.R;

import java.util.List;

/**
 * Description：TODO
 * Create Time：2016/8/16 13:58
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class MsgAdapter extends RvAdaper<User_MessageForShiMing.ContentBean.MessageListBean> {


    public MsgAdapter(Context context, List<User_MessageForShiMing.ContentBean.MessageListBean> list) {
        super(context, list);
    }

    @Override
    protected RvAdaper.ViewHolder createViewHolder(View v) {
        return new ViewHolder(v);
    }

    @Override
    protected int getItemView() {
        return R.layout.item_alarm_mine;
    }

    @Override
    protected void bindHolder(RvAdaper.ViewHolder baseHolder, User_MessageForShiMing.ContentBean.MessageListBean bean, final int position) {
        final ViewHolder holder = (ViewHolder) baseHolder;
        holder.tvalarmtype.setText(bean.getMessageTitle());
        holder.tvalarmtime.setText(getTime(bean.getCreateTime()));
        holder.tvalarmmsg.setText(bean.getMessage());
        holder.iv_readed.setVisibility(bean.getIsRead()==1?View.INVISIBLE:View.VISIBLE);

    }


    class ViewHolder extends RvAdaper.ViewHolder {
        public final TextView tvalarmtype;
        public final TextView tvalarmtime;
        public final TextView tvalarmmsg;
        public final ImageView iv_readed;

        public ViewHolder(View itemView) {
            super(itemView);
            tvalarmtype = (TextView) itemView.findViewById(R.id.tv_alarm_type);
            tvalarmtime = (TextView) itemView.findViewById(R.id.tv_alarm_time);
            tvalarmmsg = (TextView) itemView.findViewById(R.id.tv_alarm_msg);
            iv_readed = (ImageView) itemView.findViewById(R.id.iv_readed);
        }
    }
    public String getTime(String time) {
        if (TimeUtil.getFormatDate().equals(time.substring(0, 10))) {
            return time.substring(time.length() - 9);
        }
        return time;
    }

    public void setReaded(int position) {
        list.get(position).setIsRead(1);
        notifyDataSetChanged();
    }
}
