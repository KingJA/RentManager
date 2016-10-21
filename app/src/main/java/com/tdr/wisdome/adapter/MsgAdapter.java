package com.tdr.wisdome.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.orhanobut.logger.Logger;
import com.tdr.wisdome.R;
import com.tdr.wisdome.actvitiy.MainCarActivity;
import com.tdr.wisdome.actvitiy.MainCareActivity;
import com.tdr.wisdome.model.MsgInfo;
import com.tdr.wisdome.util.Constants;
import com.tdr.wisdome.util.WebServiceUtils;
import com.tdr.wisdome.view.drop.CoverManager;
import com.tdr.wisdome.view.drop.WaterDrop;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * 我的信息适配器
 * Created by Linus_Xie on 2016/8/10.
 */
public class MsgAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;

    private List<MsgInfo> list;

    public MsgAdapter(Context mContext, List<MsgInfo> list) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        this.list = list;
    }

    public final class ViewHolder {
        public SwipeLayout sample;
        public TextView text_read;
        public TextView text_msgType;
        public TextView text_msg;
        public TextView text_loc;
        public TextView text_msgTime;
        public TextView text_msgContent;
        public WaterDrop drop;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.msg_item, null);
            holder.sample = (SwipeLayout) convertView.findViewById(R.id.sample);
            holder.sample.setShowMode(SwipeLayout.ShowMode.PullOut);
            holder.sample.addDrag(SwipeLayout.DragEdge.Right, holder.sample.findViewWithTag("Bottom1"));
            holder.text_read = (TextView) convertView.findViewById(R.id.text_read);
            holder.text_msgType = (TextView) convertView.findViewById(R.id.text_msgType);
            holder.text_msg = (TextView) convertView.findViewById(R.id.text_msg);
            holder.text_loc = (TextView) convertView.findViewById(R.id.text_loc);
            holder.text_msgTime = (TextView) convertView.findViewById(R.id.text_msgTime);
            holder.text_msgContent = (TextView) convertView.findViewById(R.id.text_msgContent);
            holder.drop = (WaterDrop) convertView.findViewById(R.id.drop);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.text_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("消息列表", "按钮点击阅读");
                list.get(position).setIsRead("1");
                setMsgRead(list.get(position).getMessageId());
            }
        });

        holder.drop.setText("");
        holder.drop.setEffectResource(R.drawable.explosion1);
        holder.drop.setOnDragCompeteListener(new CoverManager.OnDragCompeteListener() {
            @Override
            public void onDragComplete() {
                Log.e("消息列表", "拖动阅读");
                list.get(position).setIsRead("1");
                setMsgRead(list.get(position).getMessageId());
            }
        });
        if (list.get(position).getIsRead().equals("0")) {
            holder.drop.setVisibility(View.VISIBLE);
        } else {
            holder.drop.setVisibility(View.GONE);
        }

        String msgType = list.get(position).getMessageType();
        if (msgType.equals("1")) {
            holder.text_msgType.setText("普通");
        } else if (msgType.equals("2")) {
            holder.text_msg.setText("报警");
        } else if (msgType.equals("3")) {
            holder.text_msg.setText("预警");
        }

        final String cardCode = list.get(position).getCardCode();
        if (cardCode.equals("1001")) {
            holder.text_msg.setText("我的住房");
        } else if (cardCode.equals("1002")) {
            holder.text_msg.setText("我的出租房");
        } else if (cardCode.equals("1003")) {
            holder.text_msg.setText("我的车");
        } else if (cardCode.equals("1004")) {
            holder.text_msg.setText("我的店");
        } else if (cardCode.equals("1005")) {
            holder.text_msg.setText("亲情关爱");
        } else if (cardCode.equals("1006")) {
            holder.text_msg.setText("服务商城");
        } else if (cardCode.equals("1007")) {
            holder.text_msg.setText("出租房代管");
        }

        holder.text_loc.setText(list.get(position).getCityName());
        holder.text_msgTime.setText(list.get(position).getCreateTime());
        holder.text_msgContent.setText(list.get(position).getMessage());

        holder.sample.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Intent intent = new Intent(mContext, OlderSelectActivity.class);
                //  intent.putExtra("messageId", messageId);
                //  mContext.startActivity(intent);
                if (cardCode.equals("1001")) {
                } else if (cardCode.equals("1002")) {
                } else if (cardCode.equals("1003")) {
                    Intent intent1003 = new Intent(mContext, MainCarActivity.class);
                    mContext.startActivity(intent1003);
                } else if (cardCode.equals("1004")) {
                } else if (cardCode.equals("1005")) {
                    Intent intent1005 = new Intent(mContext, MainCareActivity.class);
                    mContext.startActivity(intent1005);
                } else if (cardCode.equals("1006")) {
                } else if (cardCode.equals("1007")) {
                }
            }
        });

        return convertView;
    }

    private void setMsgRead(String messageId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Messageid", messageId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("token", Constants.getToken());
        map.put("cardType", "");
        map.put("taskId", "");
        map.put("encryption", "");
        map.put("DataTypeCode", "SetUserMessage");
        map.put("content", jsonObject.toString());

        WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map, new WebServiceUtils.WebServiceCallBack() {
            @Override
            public void callBack(String result) {
                Logger.json(result);
                if (result != null) {
                    //不做处理
                    notifyDataSetChanged();
                }
            }
        });
    }
}
