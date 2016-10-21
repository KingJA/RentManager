package com.tdr.wisdome.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.tdr.wisdome.R;
import com.tdr.wisdome.actvitiy.CarAlarmActivity;
import com.tdr.wisdome.model.CarInfo;
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
 * 我的车适配器
 * Created by Linus_Xie on 2016/8/24.
 */
public class MainCarAdapter extends BaseSwipeAdapter {

    private Context mContext;
    private LayoutInflater mInflater;

    private List<CarInfo> list;

    private ZProgressHUD mProgressHUD;

    public MainCarAdapter(List<CarInfo> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        mProgressHUD = new ZProgressHUD(mContext);

    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.sample;
    }

    @Override
    public View generateView(final int position, ViewGroup parent) {

        View convertView = mInflater.inflate(R.layout.car_item, null);
        SwipeLayout sample = (SwipeLayout) convertView.findViewById(getSwipeLayoutResourceId(position));
        sample.setShowMode(SwipeLayout.ShowMode.PullOut);
        sample.addDrag(SwipeLayout.DragEdge.Right, sample.findViewWithTag("Bottom1"));
        sample.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String plateNumber = list.get(position).getPlateNumber();
                Intent intent = new Intent(mContext, CarAlarmActivity.class);
                intent.putExtra("plateNumber", plateNumber);
                mContext.startActivity(intent);
            }
        });

        convertView.findViewById(R.id.text_del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bindingID = list.get(position).getBindingID();
                dialogShow(bindingID, position);
            }
        });

        return convertView;
    }

    @Override
    public void fillValues(final int position, final View convertView) {
        TextView text_bikeNum = (TextView) convertView.findViewById(R.id.text_bikeNum);
        ImageView image_point = (ImageView) convertView.findViewById(R.id.image_point);
        Button btn_defenceState = (Button) convertView.findViewById(R.id.btn_defenceState);
        btn_defenceState.setTag(position);
        text_bikeNum.setText(list.get(position).getPlateNumber());
        if (list.get(position).getIsRead().equals("0")) {
            image_point.setVisibility(View.VISIBLE);
        } else {
            image_point.setVisibility(View.GONE);
        }
        if (list.get(position).getStatus().equals("0") || list.get(position).getStatus().equals("2")) {
            btn_defenceState.setText("布防");
            btn_defenceState.setTextColor(mContext.getResources().getColor(R.color.colorStatus));
            btn_defenceState.setBackgroundResource(R.mipmap.image_unprotection);

        } else {
            btn_defenceState.setText("已布防");
            btn_defenceState.setTextColor(mContext.getResources().getColor(R.color.white));
            btn_defenceState.setBackgroundResource(R.mipmap.image_protection);
        }
        btn_defenceState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).getStatus().equals("1")) {
                    //已布防，点击则撤布防
                    delElecDeploy(list.get(position).getPlateNumber(), position, convertView);
                } else {
                    //否则，则布防
                    addElecDeploy(list.get(position).getPlateNumber(), position, convertView);
                }
            }
        });
    }

    /**
     * 撤防
     *
     * @param bikeNum
     */
    private void delElecDeploy(String bikeNum, final int position, View convertView) {
        mProgressHUD.setMessage("撤防中...");
        mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
        mProgressHUD.show();
        final Button btn_defenceState = (Button) convertView.findViewById(R.id.btn_defenceState);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("platenumber", bikeNum);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("token", Constants.getToken());
        map.put("cardType", "1003");
        map.put("taskId", "");
        map.put("Encryption", "");
        map.put("DataTypeCode", "DelElecDeploy");
        map.put("content", jsonObject.toString());
        WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map, new WebServiceUtils.WebServiceCallBack() {
            @Override
            public void callBack(String result) {
                if (result != null) {
                    try {
                        JSONObject object = new JSONObject(result);
                        int resultCode = object.getInt("ResultCode");
                        String resultText = Utils.initNullStr(object.getString("ResultText"));
                        if (resultCode == 0) {
                            mProgressHUD.dismiss();
                            Utils.myToast(mContext, "撤布防成功");
                            //updateView(position);
                            if ((int)btn_defenceState.getTag() == position) {
                                btn_defenceState.setText("布防");
                                btn_defenceState.setTextColor(mContext.getResources().getColor(R.color.colorStatus));
                                btn_defenceState.setBackgroundResource(R.mipmap.image_unprotection);
                                list.get(position).setStatus("0");
                            }
                        } else {
                            mProgressHUD.dismiss();
                            Utils.myToast(mContext, resultText);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        mProgressHUD.dismiss();
                        Utils.myToast(mContext, "JSON解析出错");
                    }
                } else {
                    mProgressHUD.dismiss();
                    Utils.myToast(mContext, "获取数据错误，请稍后重试！");
                }
            }
        });

    }

    /**
     * 布防
     *
     * @param bikeNum
     */
    private void addElecDeploy(String bikeNum, final int position, View convertView) {
        mProgressHUD.setMessage("布防中...");
        mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
        mProgressHUD.show();
        final Button btn_defenceState = (Button) convertView.findViewById(R.id.btn_defenceState);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("platenumber", bikeNum);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("token", Constants.getToken());
        map.put("cardType", "1003");
        map.put("taskId", "");
        map.put("Encryption", "");
        map.put("DataTypeCode", "AddElecDeploy");
        map.put("content", jsonObject.toString());
        WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map, new WebServiceUtils.WebServiceCallBack() {
            @Override
            public void callBack(String result) {
                if (result != null) {
                    try {
                        JSONObject object = new JSONObject(result);
                        int resultCode = object.getInt("ResultCode");
                        String resultText = Utils.initNullStr(object.getString("ResultText"));
                        if (resultCode == 0) {
                            mProgressHUD.dismiss();
                            Utils.myToast(mContext, "布防成功");
                            if ((int)btn_defenceState.getTag() == position) {
                                btn_defenceState.setText("已布防");
                                btn_defenceState.setTextColor(mContext.getResources().getColor(R.color.white));
                                btn_defenceState.setBackgroundResource(R.mipmap.image_protection);
                                list.get(position).setStatus("1");
                            }
                        } else {
                            mProgressHUD.dismiss();
                            Utils.myToast(mContext, resultText);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        mProgressHUD.dismiss();
                        Utils.myToast(mContext, "JSON解析出错");
                    }
                } else {
                    mProgressHUD.dismiss();
                    Utils.myToast(mContext, "获取数据错误，请稍后重试！");
                }
            }
        });
    }

    private NiftyDialogBuilder dialogBuilder;
    private NiftyDialogBuilder.Effectstype effectstype;

    private void dialogShow(final String bindingID, final int position) {
        if (dialogBuilder != null && dialogBuilder.isShowing())
            return;

        dialogBuilder = NiftyDialogBuilder.getInstance(mContext);

        effectstype = NiftyDialogBuilder.Effectstype.Fadein;
        dialogBuilder.withTitle("提示").withTitleColor("#333333").withMessage("是否删除该辆电瓶车")
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
                mProgressHUD.setMessage("删除中...");
                mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
                //list.remove(position);
                doDel(bindingID, position);
            }
        }).show();

    }

    /**
     * 删除绑定车辆
     *
     * @param bindingID
     * @param position
     */
    private void doDel(String bindingID, final int position) {
        mProgressHUD.setMessage("删除中...");
        mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
        mProgressHUD.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("BindingID", bindingID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("token", Constants.getToken());
        map.put("cardType", "1003");
        map.put("taskId", "");
        map.put("DataTypeCode", "UnBinding");
        map.put("content", jsonObject.toString());
        WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map, new WebServiceUtils.WebServiceCallBack() {
            @Override
            public void callBack(String result) {
                if (result != null) {
                    JSONObject json = null;
                    try {
                        json = new JSONObject(result);
                        int resultCode = json.getInt("ResultCode");
                        String resultText = Utils.initNullStr(json.getString("ResultText"));
                        if (resultCode == 0) {
                            mProgressHUD.dismiss();
                            list.remove(position);
                            notifyDataSetChanged();
                            Utils.myToast(mContext, resultText);
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
