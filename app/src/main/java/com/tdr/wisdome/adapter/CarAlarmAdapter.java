package com.tdr.wisdome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.tdr.wisdome.R;
import com.tdr.wisdome.model.CarAlarmInfo;
import com.tdr.wisdome.util.Constants;
import com.tdr.wisdome.util.WebServiceUtils;
import com.tdr.wisdome.view.drop.CoverManager;
import com.tdr.wisdome.view.drop.WaterDrop;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Linus_Xie on 2016/8/24.
 */
public class CarAlarmAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;

    private List<CarAlarmInfo> list;

    public CarAlarmAdapter(Context mContext, List<CarAlarmInfo> list) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        this.list = list;
    }

    public class ViewHolder {
        public SwipeLayout sample;
        public TextView text_carNumber;
        public TextView text_carAlarmTime;
        public TextView text_carAramContent;
        public TextView text_read;
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.caralarm_item, null);
            viewHolder.sample = (SwipeLayout) convertView.findViewById(R.id.sample);
            viewHolder.sample.setShowMode(SwipeLayout.ShowMode.PullOut);
            viewHolder.sample.addDrag(SwipeLayout.DragEdge.Right, viewHolder.sample.findViewWithTag("Bottom1"));
            viewHolder.text_carNumber = (TextView) convertView.findViewById(R.id.text_carNumber);
            viewHolder.text_carAlarmTime = (TextView) convertView.findViewById(R.id.text_carAlarmTime);
            viewHolder.text_carAramContent = (TextView) convertView.findViewById(R.id.text_carAramContent);
            viewHolder.drop = (WaterDrop) convertView.findViewById(R.id.drop);
            viewHolder.text_read = (TextView) convertView.findViewById(R.id.text_read);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.sample.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // String smartcareId = list.get(position).getCareNumber();
                // Intent intent = new Intent(mContext, OlderSelectActivity.class);
                //intent.putExtra("smartcareId", smartcareId);
                // mContext.startActivity(intent);
            }
        });

        viewHolder.text_carNumber.setText(list.get(position).getPlateNumber());
        viewHolder.text_carAlarmTime.setText(list.get(position).getMonitorTime());
        viewHolder.text_carAramContent.setText(list.get(position).getMessage());
        viewHolder.drop.setText("");
        viewHolder.drop.setEffectResource(R.drawable.explosion1);
        if (list.get(position).getIsRead().equals("0")) {
            viewHolder.drop.setVisibility(View.VISIBLE);
        } else {
            viewHolder.drop.setVisibility(View.GONE);
        }
        viewHolder.text_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRead(list.get(position).getListId());
            }
        });
        viewHolder.drop.setOnDragCompeteListener(new CoverManager.OnDragCompeteListener() {
            @Override
            public void onDragComplete() {
                setRead(list.get(position).getListId());
            }
        });
        return convertView;
    }

    private void setRead(String listId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("listid",listId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HashMap<String,String> map = new HashMap<>();
        map.put("token", Constants.getToken());
        map.put("cardType", "1003");
        map.put("taskId", "");
        map.put("encryption", "");
        map.put("DataTypeCode", "KB_IsRead");
        map.put("content", jsonObject.toString());

        WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map, new WebServiceUtils.WebServiceCallBack() {
            @Override
            public void callBack(String result) {
                if (result != null){
                    //不做处理
                }
            }
        });

    }
}
