package com.tdr.wisdome.actvitiy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tdr.wisdome.R;
import com.tdr.wisdome.adapter.OlderAlarmAdapter;
import com.tdr.wisdome.model.OlderAlarmInfo;
import com.tdr.wisdome.model.OlderInfo;
import com.tdr.wisdome.util.Constants;
import com.tdr.wisdome.util.DateTimePickDialogUtil;
import com.tdr.wisdome.util.Utils;
import com.tdr.wisdome.util.WebServiceUtils;
import com.tdr.wisdome.view.SegmentControlView;
import com.tdr.wisdome.view.ZProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 老人详细列表
 * Created by Linus_Xie on 2016/8/16.
 */
public class OlderSelectActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "OlderSelectActivity";

    private String smartcareId = "";
    private String targetType = "";
    private String personType = "";

    private ZProgressHUD mProgressHUD;
    private Context mContext;

    private OlderInfo mInfo = new OlderInfo();
    private List<OlderInfo> listOlder = new ArrayList<>();

    private String h5PageUrl = "";//H5网页

    private List<HashMap<String, String>> listGuardian = new ArrayList<>();
    private HashMap<String, String> mapGuardian = new HashMap<>();

    private OlderAlarmAdapter olderAlarmAdapter;
    private List<OlderAlarmInfo> listOlderAlarm = new ArrayList<>();
    private List<OlderAlarmInfo> listOutLivingQuarters = new ArrayList<>();
    private List<OlderAlarmInfo> listOlderOutTime = new ArrayList<>();
    private List<OlderAlarmInfo> listOlderMiss = new ArrayList<>();

    private String olderBase = "";//老人图像

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olderselect);
        mContext = this;
        initView();
        smartcareId = getIntent().getStringExtra("smartcareId");
        targetType = getIntent().getStringExtra("targetType");
        initData();
    }

    private RelativeLayout relative_title;
    private ImageView image_back;
    private SegmentControlView segmentControlView;

    private WebView webview_olderInfo;
    private FrameLayout layout_olderInfo;
    private LinearLayout layout_olderConfiguration;
    private FrameLayout layout_olderWarning;

    private FloatingActionButton fab_alarm;

    private RelativeLayout relative_alarmType, relative_selectedTime;
    private TextView text_alarmType, text_selectedTime, text_outLivingQuarters, text_olderOutTime;
    private ImageView image_alarmType, image_selectTime;
    private LinearLayout linear_1, linear_2, linear_alarmType, linear_outLivingQuarters, linear_olderOutTime, linear_selectedTime;
    private TextView text_startTime, text_endTime, text_reset, text_confirm;

    private ListView list_olderAlarm;

    private RelativeLayout relative_personConfiguration, relative_personSetting;

    private void initView() {
        image_back = (ImageView) findViewById(R.id.image_back);
        image_back.setOnClickListener(this);

        relative_title = (RelativeLayout) findViewById(R.id.relative_title);

        segmentControlView = (SegmentControlView) findViewById(R.id.segmentControlView);
        segmentControlView.setOnSegmentControlViewClickListener(new SegmentControlView.onSegmentControlViewClickListener() {
            @Override
            public void onSegmentControlViewClick(View v, int position) {
                switch (position) {
                    case 0:
                        layout_olderInfo.setVisibility(View.VISIBLE);
                        layout_olderWarning.setVisibility(View.GONE);
                        layout_olderConfiguration.setVisibility(View.GONE);
                        webview_olderInfo.loadUrl(h5PageUrl);
                        webview_olderInfo.setWebViewClient(new webViewClient());
                        fab_alarm.setVisibility(View.VISIBLE);
                        break;

                    case 1:
                        layout_olderInfo.setVisibility(View.GONE);
                        layout_olderWarning.setVisibility(View.VISIBLE);
                        layout_olderConfiguration.setVisibility(View.GONE);
                        fab_alarm.setVisibility(View.GONE);
                        break;

                    case 2:
                        layout_olderInfo.setVisibility(View.GONE);
                        layout_olderWarning.setVisibility(View.GONE);
                        layout_olderConfiguration.setVisibility(View.VISIBLE);
                        fab_alarm.setVisibility(View.GONE);
                        break;
                }
            }
        });

        webview_olderInfo = (WebView) findViewById(R.id.webview_olderInfo);
        WebSettings webSettings = webview_olderInfo.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setBuiltInZoomControls(true);//设置支持缩放

        fab_alarm = (FloatingActionButton) findViewById(R.id.fab_alarm);
        fab_alarm.setOnClickListener(this);

        layout_olderInfo = (FrameLayout) findViewById(R.id.layout_olderInfo);
        layout_olderWarning = (FrameLayout) findViewById(R.id.layout_olderWarning);
        layout_olderConfiguration = (LinearLayout) findViewById(R.id.layout_olderConfiguration);

        relative_alarmType = (RelativeLayout) findViewById(R.id.relative_alarmType);
        relative_alarmType.setOnClickListener(this);
        relative_selectedTime = (RelativeLayout) findViewById(R.id.relative_selectedTime);
        relative_selectedTime.setOnClickListener(this);

        text_alarmType = (TextView) findViewById(R.id.text_alarmType);
        image_alarmType = (ImageView) findViewById(R.id.image_alarmType);
        text_selectedTime = (TextView) findViewById(R.id.text_selectedTime);
        image_selectTime = (ImageView) findViewById(R.id.image_selectTime);

        linear_1 = (LinearLayout) findViewById(R.id.linear_1);
        linear_2 = (LinearLayout) findViewById(R.id.linear_2);

        linear_alarmType = (LinearLayout) findViewById(R.id.linear_alarmType);
        linear_outLivingQuarters = (LinearLayout) findViewById(R.id.linear_outLivingQuarters);
        linear_outLivingQuarters.setOnClickListener(this);
        linear_olderOutTime = (LinearLayout) findViewById(R.id.linear_olderOutTime);
        linear_olderOutTime.setOnClickListener(this);
        text_outLivingQuarters = (TextView) findViewById(R.id.text_outLivingQuarters);
        text_olderOutTime = (TextView) findViewById(R.id.text_olderOutTime);

        linear_selectedTime = (LinearLayout) findViewById(R.id.linear_selectedTime);

        list_olderAlarm = (ListView) findViewById(R.id.list_olderAlarm);
        olderAlarmAdapter = new OlderAlarmAdapter(mContext, listOlderAlarm);
        list_olderAlarm.setAdapter(olderAlarmAdapter);

        text_startTime = (TextView) findViewById(R.id.text_startTime);
        text_startTime.setOnClickListener(this);
        text_endTime = (TextView) findViewById(R.id.text_endTime);
        text_endTime.setOnClickListener(this);
        text_reset = (TextView) findViewById(R.id.text_reset);
        text_reset.setOnClickListener(this);
        text_confirm = (TextView) findViewById(R.id.text_confirm);
        text_confirm.setOnClickListener(this);

        relative_personConfiguration = (RelativeLayout) findViewById(R.id.relative_personConfiguration);
        relative_personConfiguration.setOnClickListener(this);
        relative_personSetting = (RelativeLayout) findViewById(R.id.relative_personSetting);
        relative_personSetting.setOnClickListener(this);

        mProgressHUD = new ZProgressHUD(OlderSelectActivity.this);
        mProgressHUD.setMessage("数据获取中...");
        mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
    }

    //Web视图
    private class webViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private void initData() {
        mProgressHUD.show();
        //查看对象列表
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("SMARTCAREID", smartcareId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final HashMap<String, String> map = new HashMap<>();
        map.put("token", Constants.getToken());
        map.put("cardType", "1005");
        map.put("taskId", "");
        map.put("DataTypeCode", "CheckElder");
        map.put("content", jsonObject.toString());

        WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map, new WebServiceUtils.WebServiceCallBack() {
            @Override
            public void callBack(String result) {
                if (result != null) {
                    Log.e(TAG, result);
                    try {
                        JSONObject json = new JSONObject(result);
                        int resultCode = json.getInt("ResultCode");
                        String resultText = Utils.initNullStr(json.getString("ResultText"));
                        if (resultCode == 0) {
                            String content = json.getString("Content");
                            JSONObject obj = new JSONObject(content);
                            mapGuardian.put("smartcareId", obj.getString("SMARTCAREID"));
                            mapGuardian.put("personType", obj.getString("PERSONTYPE"));
                            personType = obj.getString("PERSONTYPE");

                            String lrInfo = obj.getString("LRINFO");
                            JSONObject lrObj = new JSONObject(lrInfo);
                            mapGuardian.put("customerName", lrObj.getString("CUSTOMERNAME"));
                            mapGuardian.put("customerIdCard", lrObj.getString("CUSTOMERIDCARD"));
                            mapGuardian.put("customMobile", lrObj.getString("CUSTOMMOBILE"));
                            mapGuardian.put("customerAddress", lrObj.getString("CUSTOMERADDRESS"));

                            String lrParam = obj.getString("LRPARAM");
                            JSONObject paramObj = new JSONObject(lrParam);
                            mapGuardian.put("centrepointLat", paramObj.getString("CENTREPOINTLAT"));
                            mapGuardian.put("centrepointLng", paramObj.getString("CENTREPOINTLNG"));
                            mapGuardian.put("radius", paramObj.getString("RADIUS"));

                            String lrPhoto = obj.getString("PHOTOINFO");
                            JSONObject photoObj = new JSONObject(lrPhoto);
                            //mapGuardian.put("photo", photoObj.getString("CUSTOMERPHOTO"));
                            olderBase = photoObj.getString("CUSTOMERPHOTO");
                            Constants.setBodyPhoto(olderBase);
                            mapGuardian.put("photoId", photoObj.getString("PHOTOID"));

                            String lrCustmerHealthInfo = obj.getString("CUSTMERHEALTHINFO");
                            JSONObject healthObj = new JSONObject(lrCustmerHealthInfo);
                            mapGuardian.put("healthCondition", healthObj.getString("HEALTHCONDITION"));
                            mapGuardian.put("emtnotice", healthObj.getString("EMTNOTICE"));
                            listGuardian.add(mapGuardian);

                            listOlder.add(mInfo);
                            h5PageUrl = obj.getString("H5PAGEURL");
                            Log.e(TAG, h5PageUrl);

                            //处理监护人数据
                            String guarderList = obj.getString("GUARDERLIST");
                            JSONArray array = new JSONArray(guarderList);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(0);
                                HashMap<String, String> mapGuardian = new HashMap<>();
                                mapGuardian.put("guardianId", object.getString("GUARDIANID"));
                                mapGuardian.put("guardianName", object.getString("GUARDIANNAME"));
                                mapGuardian.put("guardianIdCard", object.getString("GUARDIANIDCARD"));
                                mapGuardian.put("guardianMobile", object.getString("GUARDIANMOBILE"));
                                mapGuardian.put("guardianAddress", object.getString("GUARDIANADDRESS"));
                                mapGuardian.put("enmergencyCall", object.getString("ENMERGENCYCALL"));
                                listGuardian.add(mapGuardian);
                            }
                            mProgressHUD.dismiss();
                            webview_olderInfo.loadUrl(h5PageUrl);
                            webview_olderInfo.setWebViewClient(new webViewClient());
                            webview_olderInfo.reload();
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
        //查看对象预警信息列表
        searchAlarm();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_back:
                finish();
                break;

            case R.id.fab_alarm:
                Intent intent = new Intent(OlderSelectActivity.this, LostAlarmActivity.class);
                intent.putExtra("smartcareId", smartcareId);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;

            case R.id.relative_alarmType:
                if (linear_alarmType.getVisibility() == 0) {
                    linear_alarmType.setVisibility(View.GONE);
                    linear_selectedTime.setVisibility(View.GONE);
                    text_alarmType.setTextColor(getResources().getColor(R.color.colorInput));
                    image_alarmType.setBackgroundResource(R.mipmap.image_arrowoff);
                    text_selectedTime.setTextColor(getResources().getColor(R.color.colorInput));
                    image_selectTime.setBackgroundResource(R.mipmap.image_arrowoff);
                } else {
                    linear_alarmType.setVisibility(View.VISIBLE);
                    linear_selectedTime.setVisibility(View.GONE);
                    text_alarmType.setTextColor(getResources().getColor(R.color.colorStatus));
                    image_alarmType.setBackgroundResource(R.mipmap.image_arrowon);
                    text_selectedTime.setTextColor(getResources().getColor(R.color.colorInput));
                    image_selectTime.setBackgroundResource(R.mipmap.image_arrowoff);
                }

                break;

            case R.id.relative_selectedTime:
                if (linear_selectedTime.getVisibility() == 0) {
                    linear_alarmType.setVisibility(View.GONE);
                    linear_selectedTime.setVisibility(View.GONE);
                    text_alarmType.setTextColor(getResources().getColor(R.color.colorInput));
                    image_alarmType.setBackgroundResource(R.mipmap.image_arrowoff);
                    text_selectedTime.setTextColor(getResources().getColor(R.color.colorInput));
                    image_selectTime.setBackgroundResource(R.mipmap.image_arrowoff);
                } else {
                    linear_alarmType.setVisibility(View.GONE);
                    linear_selectedTime.setVisibility(View.VISIBLE);
                    text_alarmType.setTextColor(getResources().getColor(R.color.colorInput));
                    image_alarmType.setBackgroundResource(R.mipmap.image_arrowoff);
                    text_selectedTime.setTextColor(getResources().getColor(R.color.colorStatus));
                    image_selectTime.setBackgroundResource(R.mipmap.image_arrowon);
                }

                break;

            case R.id.linear_outLivingQuarters:
                linear_alarmType.setVisibility(View.GONE);
                linear_selectedTime.setVisibility(View.GONE);
                text_alarmType.setTextColor(getResources().getColor(R.color.colorInput));
                image_alarmType.setBackgroundResource(R.mipmap.image_arrowoff);
                text_selectedTime.setTextColor(getResources().getColor(R.color.colorInput));
                image_selectTime.setBackgroundResource(R.mipmap.image_arrowoff);
                text_outLivingQuarters.setTextColor(getResources().getColor(R.color.colorStatus));
                text_olderOutTime.setTextColor(getResources().getColor(R.color.colorHint));
                for (int i = 0; i < listOlderAlarm.size(); i++) {
                    if (listOlderAlarm.get(i).getAlertType().equals("0")) {
                        listOlderAlarm.add(listOlderAlarm.get(i));
                    }
                }
                olderAlarmAdapter.notifyDataSetChanged();
                break;

            case R.id.linear_olderOutTime:
                linear_alarmType.setVisibility(View.GONE);
                linear_selectedTime.setVisibility(View.GONE);
                text_alarmType.setTextColor(getResources().getColor(R.color.colorInput));
                image_alarmType.setBackgroundResource(R.mipmap.image_arrowoff);
                text_selectedTime.setTextColor(getResources().getColor(R.color.colorInput));
                image_selectTime.setBackgroundResource(R.mipmap.image_arrowoff);
                text_outLivingQuarters.setTextColor(getResources().getColor(R.color.colorHint));
                text_olderOutTime.setTextColor(getResources().getColor(R.color.colorStatus));
                for (int i = 0; i < listOlderAlarm.size(); i++) {
                    if (listOlderAlarm.get(i).getAlertType().equals("2")) {
                        listOlderAlarm.add(listOlderAlarm.get(i));
                    }
                }
                olderAlarmAdapter.notifyDataSetChanged();
                break;


            case R.id.text_startTime:
                DateTimePickDialogUtil dateTimePicKDialog1 = new DateTimePickDialogUtil(OlderSelectActivity.this, "");
                dateTimePicKDialog1.dialogShow(text_startTime);
                break;

            case R.id.text_endTime:
                DateTimePickDialogUtil dateTimePicKDialog2 = new DateTimePickDialogUtil(OlderSelectActivity.this, "");
                dateTimePicKDialog2.dialogShow(text_endTime);
                break;

            case R.id.text_reset:
                text_startTime.setText("");
                text_endTime.setText("");
                break;

            case R.id.text_confirm:
                searchAlarm();
                linear_alarmType.setVisibility(View.GONE);
                linear_selectedTime.setVisibility(View.GONE);
                text_alarmType.setTextColor(getResources().getColor(R.color.colorInput));
                image_alarmType.setBackgroundResource(R.mipmap.image_arrowoff);
                text_selectedTime.setTextColor(getResources().getColor(R.color.colorInput));
                image_selectTime.setBackgroundResource(R.mipmap.image_arrowoff);
                break;

            case R.id.relative_personConfiguration:
                Intent intent1 = new Intent(OlderSelectActivity.this, OlderShareActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("listGuardian", (Serializable) listGuardian);
                bundle1.putString("targetType", targetType);
                //bundle1.putString("olderBase", olderBase);
                intent1.putExtras(bundle1);
                startActivity(intent1);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                finish();
                break;

            case R.id.relative_personSetting:
                Intent intent2 = new Intent(OlderSelectActivity.this, DeviceConfigActivity.class);
                intent2.putExtra("smartcareId", smartcareId);
                intent2.putExtra("personType", personType);
                startActivity(intent2);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
        }
    }

    private void searchAlarm() {
        //查看对象预警信息列表
        mProgressHUD.show();
        JSONObject object = new JSONObject();
        try {
            object.put("SMARTCAREID", smartcareId);
            if (text_startTime.getText().toString().equals("")) {
                object.put("STARTTIME", Utils.getNowDate());
            } else {
                object.put("STARTTIME", text_startTime.getText().toString());
            }
            if (text_endTime.getText().toString().equals("")) {
                object.put("ENDTIME", Utils.getNowTime());
            } else {
                object.put("ENDTIME", text_endTime.getText().toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        HashMap<String, String> map1 = new HashMap<>();
        map1.put("token", Constants.getToken());
        map1.put("cardType", "1005");
        map1.put("taskId", "");
        map1.put("DataTypeCode", "GETTARGETALERTS");
        map1.put("content", object.toString());
        WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map1, new WebServiceUtils.WebServiceCallBack() {
            @Override
            public void callBack(String result) {
                if (result != null) {
                    Log.e(TAG + "老人报警列表", result);
                    JSONObject json = null;
                    try {
                        json = new JSONObject(result);
                        int resultCode = json.getInt("ResultCode");
                        String resultText = Utils.initNullStr(json.getString("ResultText"));
                        if (resultCode == 0) {
                            String content = json.getString("Content");
                            JSONObject obj = new JSONObject(content);
                            String alertList = obj.getString("ALERTLIST");
                            JSONArray jsonArray = new JSONArray(alertList);
                            listOlderAlarm.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object1 = jsonArray.getJSONObject(i);
                                OlderAlarmInfo olderAlarmInfo = new OlderAlarmInfo();
                                olderAlarmInfo.setAlertType(object1.getString("ALERTTYPE"));
                                olderAlarmInfo.setAlertTime(object1.getString("ALERTTIME"));
                                olderAlarmInfo.setAlertContent(object1.getString("ALEARTCONTENT"));
                                listOlderAlarm.add(olderAlarmInfo);
                            }
                            mProgressHUD.dismiss();
                            olderAlarmAdapter.notifyDataSetChanged();
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

}
