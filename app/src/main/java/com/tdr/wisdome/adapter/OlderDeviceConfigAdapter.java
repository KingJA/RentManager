package com.tdr.wisdome.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.tdr.wisdome.R;
import com.tdr.wisdome.model.GroupItem;
import com.tdr.wisdome.util.Constants;
import com.tdr.wisdome.util.Utils;
import com.tdr.wisdome.util.WebServiceUtils;
import com.tdr.wisdome.view.ZProgressHUD;
import com.tdr.wisdome.view.niftydialog.NiftyDialogBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * 关爱人设备适配器
 * Created by Linus_Xie on 2016/8/22.
 */
public class OlderDeviceConfigAdapter extends BaseAdapter {
    private Context mContext;
    private List<GroupItem> mData;
    private LayoutInflater mInflater;
    private String personType;

    private ZProgressHUD mProgressHUD;

    public OlderDeviceConfigAdapter(Context mContext, List<GroupItem> mData,String personType) {
        this.mContext = mContext;
        this.mData = mData;
        this.personType = personType;
        mInflater = LayoutInflater.from(mContext);

        mProgressHUD = new ZProgressHUD(mContext);
        mProgressHUD.setMessage("删除中...");
        mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
    }

    public class ViewHolder {
        public TextView text_tag;
        public SwipeLayout sample;
        public TextView text_unbundling;
        public TextView text_olderDevice;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        // 常见的优化ViewHolder
        ViewHolder viewHolder = null;
        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.device_item, null);
            viewHolder = new ViewHolder();
            viewHolder.text_tag = (TextView) convertView.findViewById(R.id.text_tag);
            viewHolder.sample = (SwipeLayout) convertView.findViewById(R.id.sample);
            viewHolder.sample.setShowMode(SwipeLayout.ShowMode.PullOut);
            viewHolder.sample.addDrag(SwipeLayout.DragEdge.Right, viewHolder.sample.findViewWithTag("Bottom1"));
            viewHolder.text_unbundling = (TextView) convertView.findViewById(R.id.text_unbundling);
            viewHolder.text_olderDevice = (TextView) convertView.findViewById(R.id.text_olderDevice);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 获取数据
        GroupItem itemEntity = (GroupItem) getItem(position);
        viewHolder.text_olderDevice.setText(itemEntity.getContent());
        // viewHolder.contentIcon.setImageResource(R.drawable.ic_launcher);

        if (needTitle(position)) {
            // 显示标题并设置内容
            viewHolder.text_tag.setText(itemEntity.getTitle());
            viewHolder.text_tag.setVisibility(View.VISIBLE);
        } else {
            // 内容项隐藏标题
            viewHolder.text_tag.setVisibility(View.GONE);
        }

        viewHolder.text_unbundling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //list.remove(position);
                String deviceId = mData.get(position).getContent();
                String smartcareId = mData.get(position).getSmartcareId();
                dialogShow(deviceId, smartcareId, position);
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
        GroupItem currentEntity = (GroupItem) getItem(position);
        GroupItem previousEntity = (GroupItem) getItem(position - 1);
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

    private NiftyDialogBuilder dialogBuilder;
    private NiftyDialogBuilder.Effectstype effectstype;

    private void dialogShow(final String deviceId, final String smartcareId, final int position) {
        if (dialogBuilder != null && dialogBuilder.isShowing())
            return;

        dialogBuilder = NiftyDialogBuilder.getInstance(mContext);

        effectstype = NiftyDialogBuilder.Effectstype.Fadein;
        dialogBuilder.withTitle("提示").withTitleColor("#333333").withMessage("是否删除此设备")
                .isCancelableOnTouchOutside(false).withEffect(effectstype).withButton1Text("取消")
                .setCustomView(R.layout.custom_view, mContext).withButton2Text("确认").setButton1Click(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.dismiss();
            }
        }).setButton2Click(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.dismiss();
                doDel(deviceId, smartcareId, position);
            }
        }).show();

    }

    private void doDel(String deviceId, String smartcareId, final int position) {
        mProgressHUD.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("SMARTCAREID",smartcareId);
            jsonObject.put("DEVICEID",deviceId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("token", Constants.getToken());
        map.put("cardType", "1005");
        map.put("taskId", "");
        map.put("DataTypeCode", "TargetUNBandDevice");
        map.put("content", jsonObject.toString());
        WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map, new WebServiceUtils.WebServiceCallBack() {
            @Override
            public void callBack(String result) {
                if (result != null) {
                    Log.e("删除设备", result);
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int resultCode = jsonObject.getInt("ResultCode");
                        String resultText = Utils.initNullStr(jsonObject.getString("ResultText"));
                        if (resultCode == 0) {
                            mProgressHUD.dismiss();
                            mData.remove(position);
                            Utils.myToast(mContext, "删除此设备成功");
                        } else {
                            mProgressHUD.dismiss();
                            Utils.myToast(mContext, resultText);
                        }
                    } catch (JSONException e) {
                        mProgressHUD.dismiss();
                        e.printStackTrace();
                        Utils.myToast(mContext, "JSON解析出错");
                    }
                } else {
                    mProgressHUD.dismiss();
                    Utils.myToast(mContext, "获取数据错误，请稍后重试！");
                }
            }
        });
    }
}
