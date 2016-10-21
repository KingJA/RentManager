package com.tdr.wisdome.actvitiy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.tdr.wisdome.R;
import com.tdr.wisdome.adapter.MsgAdapter;
import com.tdr.wisdome.jpush.ExampleUtil;
import com.tdr.wisdome.model.CityInfo;
import com.tdr.wisdome.model.MsgInfo;
import com.tdr.wisdome.util.Constants;
import com.tdr.wisdome.util.Utils;
import com.tdr.wisdome.util.WebServiceUtils;
import com.tdr.wisdome.view.ZProgressHUD;
import com.tdr.wisdome.view.drop.CoverManager;

import net.tsz.afinal.FinalDb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.jpush.android.api.InstrumentedActivity;

/**
 * 我的消息
 * Created by Linus_Xie on 2016/8/10.
 */
public class MsgActivity extends InstrumentedActivity implements View.OnClickListener {
    private static final String TAG = "MsgActivity";

    private Context mContext;

    private MsgAdapter mAdapter;

    private List<MsgInfo> list = new ArrayList<>();

    private FinalDb db;

    private ZProgressHUD mProgressHUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);
        /*仿QQ气泡*/
        CoverManager.getInstance().init(this);

        mContext = this;

        mAdapter = new MsgAdapter(mContext, list);

        db = FinalDb.create(mContext, "WisdomE.db", false);

        initView();
        initData();

        this.registerMessageReceiver();
    }

    private ImageView image_back;
    private TextView text_title;
    private TextView text_deal;

    private ListView list_msg;

    private LinearLayout layout_noData;

    private void initView() {
        image_back = (ImageView) findViewById(R.id.image_back);
        image_back.setOnClickListener(this);
        text_title = (TextView) findViewById(R.id.text_title);
        text_title.setText("我的消息");
        text_deal = (TextView) findViewById(R.id.text_deal);
        text_deal.setVisibility(View.VISIBLE);
        text_deal.setText("全部已读");
        text_deal.setOnClickListener(this);

        list_msg = (ListView) findViewById(R.id.list_msg);
        list_msg.setAdapter(mAdapter);
        CoverManager.getInstance().setMaxDragDistance(250);
        CoverManager.getInstance().setEffectDuration(150);

        layout_noData = (LinearLayout) findViewById(R.id.layout_noData);

        mProgressHUD = new ZProgressHUD(this);
        mProgressHUD.setMessage("获取中...");
        mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
    }

    private void initData() {
        mProgressHUD.show();
        final HashMap<String, String> map = new HashMap<>();
        map.put("token", Constants.getToken());
        map.put("cardType", "");
        map.put("taskId", "");
        map.put("DataTypeCode", "GetUserMessage");
        map.put("content", "");

        WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map, new WebServiceUtils.WebServiceCallBack() {
            @Override
            public void callBack(String result) {
                Logger.json(result);
                if (result != null) {
                    try {
                        JSONObject object = new JSONObject(result);
                        int resultCode = object.getInt("ResultCode");
                        String resultText = Utils.initNullStr(object.getString("ResultText"));
                        if (resultCode == 0) {
                            String content = object.getString("Content");
                            JSONArray jsonArray = new JSONArray(content);
                            if (jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    MsgInfo msgInfo = new MsgInfo();
                                    String cardCode = obj.getString("CardCode");
                                    String messge = obj.getString("Message");
                                    String isRead = obj.getString("IsRead");
                                    String createTime = obj.getString("CreateTime");
                                    String cityCode = obj.getString("CityCode");
                                    String cityName = db.findAllByWhere(CityInfo.class, " CityCode=\"" + cityCode + "\"").get(0).getCityName();
                                    String msgType = obj.getString("MessageType");
                                    String messageId = obj.getString("MessageID");

                                    msgInfo.setCardCode(cardCode);
                                    msgInfo.setMessage(messge);
                                    msgInfo.setIsRead(isRead);
                                    msgInfo.setCityCode(cityCode);
                                    msgInfo.setCityName(cityName);
                                    msgInfo.setMessageType(msgType);
                                    msgInfo.setCreateTime(createTime);
                                    msgInfo.setMessageId(messageId);
                                    list.add(msgInfo);
                                }
                                layout_noData.setVisibility(View.GONE);
                                list_msg.setVisibility(View.VISIBLE);
                                mAdapter.notifyDataSetChanged();

                            } else {
                                //没有车辆处理，缺省页
                                layout_noData.setVisibility(View.VISIBLE);
                                list_msg.setVisibility(View.GONE);
                            }
                            mProgressHUD.dismiss();
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_back:
                Intent intent0 = new Intent(mContext, MainActivity.class);
                startActivity(intent0);
                finish();
                break;
            case R.id.text_deal:
                mProgressHUD.show();
                HashMap<String,String> map = new HashMap<>();
                map.put("token", Constants.getToken());
                map.put("cardType", "");
                map.put("taskId", "");
                map.put("DataTypeCode", "SetUserMessageAll");
                map.put("content", "");
                WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map, new WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {
                        if (result != null){
                            mProgressHUD.dismiss();
                            mAdapter.notifyDataSetInvalidated();
                        } else {
                            mProgressHUD.dismiss();
                            Utils.myToast(mContext, "获取数据错误，请稍后重试！");
                        }
                    }
                });
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            Intent intent0 = new Intent(mContext, MainActivity.class);
            startActivity(intent0);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        this.unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    // for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "收到广播");
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!ExampleUtil.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }

                String cardCode = "";
                String cityName = "";
                String cityCode = "";
                String msgType = "";
                JSONObject json = null;
                try {
                    json = new JSONObject(extras);
                    cardCode = json.getString("CardCode");
                    cityCode = json.getString("CityCode");
                    cityName = db.findAllByWhere(CityInfo.class, " CityCode=\"" + cityCode + "\"").get(0).getCityName();
                    msgType = json.getString("MessageType");
                    String data = json.getString("data");
                    JSONObject object = new JSONObject(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                MsgInfo mInfo = new MsgInfo();
                mInfo.setCardCode(cardCode);
                mInfo.setMessage(messge);
                mInfo.setCityCode(cityCode);
                mInfo.setCityName(cityName);
                mInfo.setMessageType(msgType);
                mInfo.setCreateTime(Utils.getNowTime());
                list.add(mInfo);
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}
