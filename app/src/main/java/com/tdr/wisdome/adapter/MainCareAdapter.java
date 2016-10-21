package com.tdr.wisdome.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.tdr.wisdome.R;
import com.tdr.wisdome.actvitiy.OlderSelectActivity;
import com.tdr.wisdome.actvitiy.QrCodeActivity;
import com.tdr.wisdome.model.OlderInfo;
import com.tdr.wisdome.util.Constants;
import com.tdr.wisdome.util.Utils;
import com.tdr.wisdome.util.WebServiceUtils;
import com.tdr.wisdome.view.ZProgressHUD;
import com.tdr.wisdome.view.niftydialog.NiftyDialogBuilder;
import com.tdr.wisdome.zbar.CaptureActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * 亲情关爱适配器
 * Created by Linus_Xie on 2016/8/16.
 */
public class MainCareAdapter extends BaseSwipeAdapter {

    private Context mContext;
    private LayoutInflater mInflater;

    private List<OlderInfo> list;

    private ZProgressHUD mProgressHUD;

    private final static int SCANNIN_GREQUEST_CODE = 2002;

    public MainCareAdapter(Context mContext, List<OlderInfo> list) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        this.list = list;
        mProgressHUD = new ZProgressHUD(mContext);

    }

    public class ViewHolder {
        public SwipeLayout sample;
        public TextView text_share;
        public TextView text_del;
        public TextView text_msgType;
        public TextView text_olderName;
        public TextView text_braceletBattery;
    }

    @Override
    public int getCount() {
        if (null != list) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (null != list && position < getCount()) {
            return list.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.sample;
    }

    @Override
    public View generateView(final int position, ViewGroup parent) {
        View convertView = mInflater.inflate(R.layout.maincare_item, null);
        SwipeLayout sample = (SwipeLayout) convertView.findViewById(getSwipeLayoutResourceId(position));
        sample.setShowMode(SwipeLayout.ShowMode.PullOut);
        sample.addDrag(SwipeLayout.DragEdge.Right, sample.findViewWithTag("Bottom1"));
        sample.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String smartcareId = list.get(position).getCareNumber();
                String targetType = list.get(position).getTargetType();
                String isRegedit = list.get(position).getIsRegedit();
                if (isRegedit.equals("1")) {
                    Intent intent = new Intent(mContext, OlderSelectActivity.class);
                    intent.putExtra("smartcareId", smartcareId);
                    intent.putExtra("targetType", targetType);
                    mContext.startActivity(intent);
                } else {
                    Utils.myToast(mContext, "请先登记");
                }

            }
        });

        convertView.findViewById(R.id.text_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String IsRegedit = list.get(position).getIsRegedit();
                if (IsRegedit.equals("0")) {
                    Utils.myToast(mContext, "请先登记");
                } else {
                    mProgressHUD.setMessage("生成分享二维码...");
                    mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
                    mProgressHUD.show();
                    String smartcareId = list.get(position).getCareNumber();

                    String targetType = list.get(position).getTargetType();
                    String operatorName = "";
                    if (!Constants.getUserName().equals("")) {
                        operatorName = Constants.getUserName();
                    } else {
                        operatorName = Constants.getUserPhone();
                    }
                    String shareTime = Utils.getNowTime();

                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("SMARTCAREID", smartcareId);
                        jsonObject.put("TARGETTYPE", targetType);
                        jsonObject.put("OPERATORNAME", operatorName);
                        jsonObject.put("SHARETIME", shareTime);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    HashMap<String, String> map = new HashMap<>();
                    map.put("token", Constants.getToken());
                    map.put("cardType", "1005");
                    map.put("taskId", "");
                    map.put("DataTypeCode", "Share_Target");
                    map.put("content", jsonObject.toString());

                    WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map, new WebServiceUtils.WebServiceCallBack() {
                        @Override
                        public void callBack(String result) {
                            if (result != null) {
                                try {
                                    JSONObject json = new JSONObject(result);
                                    int resultCode = json.getInt("ResultCode");
                                    String resultText = Utils.initNullStr(json.getString("ResultText"));
                                    if (resultCode == 0) {
                                        String content = json.getString("Content");
                                        JSONObject object = new JSONObject(content);
                                        String shareId = object.getString("SHAREID");
                                        //Intent intent = new Intent(mContext, QrCodeActivity.class);
                                        //intent.putExtra("targetType", list.get(position).getTargetType());
                                        //intent.putExtra("shareId", guid);
                                        //mContext.startActivity(intent);
                                        generateQRcode("Q3", list.get(position).getTargetType(), shareId);

                                    } else {
                                        mProgressHUD.dismiss();
                                        Utils.myToast(mContext, result);
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
        });

        convertView.findViewById(R.id.text_del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String IsRegedit = list.get(position).getIsRegedit();
                if (IsRegedit.equals("0")) {
                    Utils.myToast(mContext, "请去申领系统删除");
                } else {
                    String careNumber = list.get(position).getCareNumber();
                    dialogShow(careNumber, position, list.get(position).getPersonType());
                }
            }
        });
        return convertView;
    }

    private void generateQRcode(String q3, String targetType, String shareId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("QRType", q3);
            jsonObject.put("Type", targetType);
            jsonObject.put("ID", shareId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("token", Constants.getToken());
        map.put("cardType", "1005");
        map.put("taskId", "");
        map.put("DataTypeCode", "GenerateQRcode");
        map.put("content", jsonObject.toString());
        WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map, new WebServiceUtils.WebServiceCallBack() {
            @Override
            public void callBack(String result) {
                if (result != null) {
                    try {
                        JSONObject json = new JSONObject(result);
                        int resultCode = json.getInt("ResultCode");
                        String resultText = Utils.initNullStr(json.getString("ResultText"));
                        if (resultCode == 0) {
                            mProgressHUD.dismiss();
                            String content = json.getString("Content");
                            JSONObject object = new JSONObject(content);
                            String QRCode = object.getString("QRCode");
                            Intent intent = new Intent(mContext, QrCodeActivity.class);
                            intent.putExtra("code", QRCode);
                            mContext.startActivity(intent);
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

    private String SmartId = "";

    @Override
    public void fillValues(final int position, View convertView) {
        TextView text_tag = (TextView) convertView.findViewById(R.id.text_tag);
        LinearLayout linear_tag = (LinearLayout) convertView.findViewById(R.id.linear_tag);
        TextView text_msgType = (TextView) convertView.findViewById(R.id.text_msgType);
        TextView text_olderName = (TextView) convertView.findViewById(R.id.text_olderName);
        TextView text_braceletBattery = (TextView) convertView.findViewById(R.id.text_braceletBattery);
        final Button btn_register = (Button) convertView.findViewById(R.id.btn_register);

        if (needTitle(position)) {
            // 显示标题并设置内容
            if (list.get(position).getIsRegedit().equals("0")) {
                text_tag.setText("未关爱对象");
            } else {
                text_tag.setText("已关爱对象");
            }
            linear_tag.setVisibility(View.VISIBLE);
            text_tag.setVisibility(View.VISIBLE);
        } else {
            // 内容项隐藏标题
            linear_tag.setVisibility(View.GONE);
            text_tag.setVisibility(View.GONE);
        }

        if (list.get(position).getIsRegedit().equals("0")) {
            btn_register.setVisibility(View.VISIBLE);
        }

        if (list.get(position).getTargetType().equals("0")) {
            text_msgType.setText("老人");
        } else if (list.get(position).getTargetType().equals("1")) {
            text_msgType.setText("小孩");
        } else if (list.get(position).getTargetType().equals("2")) {
            text_msgType.setText("宠物");
        } else if (list.get(position).getTargetType().equals("3")) {
            text_msgType.setText("精神病人");
        }
        text_olderName.setText(list.get(position).getCustomerName());

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).getIsRegedit().equals("0")) {
                   /* String smartcareId = list.get(position).getCareNumber();
                    String targetType = list.get(position).getTargetType();
                    Intent intent = new Intent(mContext, OlderSelectActivity.class);
                    intent.putExtra("smartcareId", smartcareId);
                    intent.putExtra("targetType", targetType);
                    mContext.startActivity(intent);*/
                    SmartId = list.get(position).getCareNumber();
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("activity", "mainCare");
                    bundle.putString("SmartId", SmartId);
                    intent.putExtras(bundle);
                    intent.setClass(mContext, CaptureActivity.class);
                    ((Activity) mContext).startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
                }
            }
        });

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
        //GroupItem currentEntity = (GroupItem) getItem(position);
        //GroupItem previousEntity = (GroupItem) getItem(position - 1);
        OlderInfo currentEntity = list.get(position);
        OlderInfo previousEntity = list.get(position - 1);
        if (null == currentEntity || null == previousEntity) {
            return false;
        }

        String currentTitle = currentEntity.getIsRegedit();
        String previousTitle = previousEntity.getIsRegedit();
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

    private void dialogShow(final String careNumber, final int position, final String personType) {
        if (dialogBuilder != null && dialogBuilder.isShowing())
            return;

        dialogBuilder = NiftyDialogBuilder.getInstance(mContext);

        effectstype = NiftyDialogBuilder.Effectstype.Fadein;
        dialogBuilder.withTitle("提示").withTitleColor("#333333").withMessage("是否删除此关爱人")
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
                doDel(careNumber, position, personType);

            }
        }).show();

    }

    private void doDel(String careNumber, final int position, final String personType) {
        mProgressHUD.setMessage("删除中...");
        mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
        mProgressHUD.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("SMARTCAREID", careNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("token", Constants.getToken());
        map.put("cardType", "1005");
        map.put("taskId", "");
        if (personType.equals("0")) {//登记人，走删除流程
            map.put("DataTypeCode", "DelElder");
        } else if (personType.equals("1")) {//关爱人，走解除绑定流程
            map.put("DataTypeCode", "DelConcerned");
        }

        map.put("content", jsonObject.toString());
        WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map, new WebServiceUtils.WebServiceCallBack() {
            @Override
            public void callBack(String result) {
                if (result != null) {
                    Log.e("删除老人", result);
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int resultCode = jsonObject.getInt("ResultCode");
                        String resultText = Utils.initNullStr(jsonObject.getString("ResultText"));
                        if (resultCode == 0) {
                            mProgressHUD.dismiss();
                            list.remove(position);
                            notifyDataSetChanged();
                            Utils.myToast(mContext, "删除此关爱人成功");
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

    /*protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if (data == null) {
                    Utils.myToast(mContext, "没有扫描到二维码");
                    break;
                } else {
                    mProgressHUD.setMessage("二维码解析中...");
                    mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
                    mProgressHUD.show();
                    Bundle bundle = data.getExtras();
                    String scanResult = bundle.getString("result");
                    Log.e("二维码内容：", scanResult);
                    Utils.myToast(mContext, "二维码扫描成功，请稍候...");
                    String url = "http://ga.iotone.cn/";
                    if (scanResult.contains(url)) {
                        JSONObject object = new JSONObject();
                        try {
                            object.put("QRCode", scanResult);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        HashMap<String, String> map = new HashMap<>();
                        map.put("token", Constants.getToken());
                        map.put("cardType", "1005");
                        map.put("taskId", "");
                        map.put("DataTypeCode", "ParseQRcode");
                        map.put("content", object.toString());
                        WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map, new WebServiceUtils.WebServiceCallBack() {
                            @Override
                            public void callBack(String result) {
                                if (result != null) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(result);
                                        int resultCode = jsonObject.getInt("ResultCode");
                                        String resultText = jsonObject.getString("ResultText");
                                        if (resultCode == 0) {
                                            String content = jsonObject.getString("Content");
                                            JSONObject obj = new JSONObject(content);
                                            String QRType = obj.getString("QRType");
                                            String type = obj.getString("Type");
                                            String Id = obj.getString("ID");
                                            if (QRType.equals("Q1")) {
                                                mProgressHUD.dismiss();
                                                checkDevice(Id);
                                                *//*Intent intent = new Intent(MainCareActivity.this, AddOlderActivity.class);
                                                intent.putExtra("code", Id);
                                                startActivity(intent);
                                                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);*//*
                                            } else if (QRType.equals("Q2")) {
                                                mProgressHUD.dismiss();
                                                Utils.myToast(mContext, "此设备为网关设备，请到关爱对象的设备配置页面进行添加");
                                            } else if (QRType.equals("Q3")) {
                                                mProgressHUD.dismiss();
                                                Utils.myToast(mContext, "分享的关爱人，请使用右上角的扫描二维码");
                                            }
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
                                    Utils.myToast(mContext, "请确认设备是否为关爱设备");
                                }
                            }
                        });

                    } else if (scanResult.contains("?U=")) {
                        final int i = scanResult.indexOf("?U=");
                        String content = scanResult.substring(i + 3);

                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("StrString", content);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("token", Constants.getToken());
                        map.put("cardType", "1005");
                        map.put("taskId", "");
                        map.put("DataTypeCode", "AESDecrypt");
                        map.put("content", jsonObject.toString());

                        WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map, new WebServiceUtils.WebServiceCallBack() {
                            @Override
                            public void callBack(String result) {
                                if (result != null) {
                                    JSONObject object = null;
                                    try {
                                        object = new JSONObject(result);
                                        int resultCode = object.getInt("ResultCode");
                                        String resultText = object.getString("ResultText");
                                        if (resultCode == 0) {
                                            mProgressHUD.dismiss();
                                            String content = object.getString("Content");
                                            JSONObject obj = new JSONObject(content);
                                            String code = obj.getString("StrString");
                                            //edit_code.setText(content);
                                            checkDevice(code);
                                           *//* Intent intent = new Intent(MainCareActivity.this, AddOlderActivity.class);
                                            intent.putExtra("code", code);
                                            startActivity(intent);
                                            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);*//*
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
                    } else {
                        mProgressHUD.dismiss();
                        Utils.myToast(mContext, "非指定亲情关爱设备");
                        break;
                    }
                }
                break;
        }
    }

    *//**
     * 检查设备编码
     *
     * @param id
     *//*
    private void checkDevice(final String id) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("DEVICEID", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("token", Constants.getToken());
        map.put("cardType", "1005");
        map.put("taskId", "");
        map.put("DataTypeCode", "CheckDeviceNumber");
        map.put("content", jsonObject.toString());

        WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map, new WebServiceUtils.WebServiceCallBack() {
            @Override
            public void callBack(String result) {
                if (result != null) {
                    try {
                        JSONObject json = new JSONObject(result);
                        int resultCode = json.getInt("ResultCode");
                        String resultText = json.getString("ResultText");
                        if (resultCode == 0) {
                            String content = json.getString("Content");
                            JSONObject object = new JSONObject(content);
                            String isMultiuse = object.getString("ISMULTIUSE");
                            String isOccupied = object.getString("ISOCCUPIED");
                            if (isMultiuse.equals("0")) {
                                if (isOccupied.equals("1")) {
                                    Utils.myToast(mContext, "该设备已被使用");
                                } else {
                                    Intent intent = new Intent(mContext, AddOlderActivity.class);
                                    intent.putExtra("code", id);
                                    intent.putExtra("SmartId", SmartId);
                                    mContext.startActivity(intent);
                                    ((Activity) mContext).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                                }
                            }
                        } else {
                            Utils.myToast(mContext, resultText);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Utils.myToast(mContext, "JSON解析出错");
                    }
                } else {
                    Utils.myToast(mContext, "获取数据错误，请稍后重试！");
                }
            }
        });
    }*/
}
