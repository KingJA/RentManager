package com.tdr.wisdome.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.tdr.wisdome.R;
import com.tdr.wisdome.actvitiy.CarQrActivity;
import com.tdr.wisdome.model.PreInfo;
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
 * 车辆预登记列表适配器
 * Created by Linus_Xie on 2016/8/25.
 */
public class PreCarAdapter extends BaseSwipeAdapter {

    private Context mContext;
    private LayoutInflater mInflater;

    private List<PreInfo> list;

    private ZProgressHUD mProgressHUD;

    public PreCarAdapter(List<PreInfo> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        mProgressHUD = new ZProgressHUD(mContext);
        mProgressHUD.setMessage("删除中...");
        mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.sample;
    }

    @Override
    public View generateView(final int position, ViewGroup parent) {

        View convertView = mInflater.inflate(R.layout.pre_item, null);
        SwipeLayout sample = (SwipeLayout) convertView.findViewById(getSwipeLayoutResourceId(position));
        sample.setShowMode(SwipeLayout.ShowMode.PullOut);
        sample.addDrag(SwipeLayout.DragEdge.Right, sample.findViewWithTag("Bottom1"));
        sample.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String prerateID = list.get(position).getPrerateID();
                Intent intent = new Intent(mContext, CarQrActivity.class);
                intent.putExtra("prerateID", prerateID);
                mContext.startActivity(intent);
            }
        });

        convertView.findViewById(R.id.text_del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String prerateID = list.get(position).getPrerateID();
                dialogShow(prerateID, position);
            }
        });

        return convertView;
    }

    @Override
    public void fillValues(final int position, View convertView) {
        TextView text_preNum = (TextView) convertView.findViewById(R.id.text_preNum);
        TextView text_state = (TextView) convertView.findViewById(R.id.text_state);

        text_preNum.setText(list.get(position).getPrerateName());
        if (list.get(position).getState().equals("0")) {
            text_state.setText("有效");
            text_state.setTextColor(mContext.getResources().getColor(R.color.colorStatus));
        } else if (list.get(position).getState().equals("1")) {
            text_state.setText("失效");
            text_state.setTextColor(mContext.getResources().getColor(R.color.colorHint));
        } else {
            text_state.setText("已使用");
            text_state.setTextColor(mContext.getResources().getColor(R.color.colorHint));
        }
    }

    private NiftyDialogBuilder dialogBuilder;
    private NiftyDialogBuilder.Effectstype effectstype;

    private void dialogShow(final String prerateID, final int position) {
        if (dialogBuilder != null && dialogBuilder.isShowing())
            return;

        dialogBuilder = NiftyDialogBuilder.getInstance(mContext);

        effectstype = NiftyDialogBuilder.Effectstype.Fadein;
        dialogBuilder.withTitle("提示").withTitleColor("#333333").withMessage("是否删除此预登记车辆")
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
                doDel(prerateID, position);
            }
        }).show();

    }

    private void doDel(String prerateID, final int position) {
        mProgressHUD.show();
        JSONObject json = new JSONObject();
        try {
            json.put("PrerateID",prerateID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("token", Constants.getToken());
        map.put("cardType", "1003");
        map.put("taskId", "");
        map.put("Encryption", "");
        map.put("DataTypeCode", "DelPreRate");
        map.put("content", json.toString());
        WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map, new WebServiceUtils.WebServiceCallBack() {
            @Override
            public void callBack(String result) {
                if (result != null){
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int resultCode = jsonObject.getInt("ResultCode");
                        String resultText = Utils.initNullStr(jsonObject.getString("ResultText"));
                        if (resultCode == 0){
                            mProgressHUD.dismiss();
                            list.remove(position);
                            notifyDataSetChanged();
                            Utils.myToast(mContext, resultText);
                        } else {
                            mProgressHUD.dismiss();
                            Utils.myToast(mContext,resultText);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        mProgressHUD.dismiss();
                        Utils.myToast(mContext,"JSON解析出错");
                    }
                } else {
                    mProgressHUD.dismiss();
                    Utils.myToast(mContext, "获取数据错误，请稍后重试！");
                }
            }
        });
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
}