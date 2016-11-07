package com.tdr.wisdome.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.kingja.cardpackage.Event.ClearMsgEvent;
import com.kingja.cardpackage.Event.RefreshMsgEvent;
import com.kingja.cardpackage.activity.AgentActivity;
import com.kingja.cardpackage.activity.AlarmMineActivity;
import com.kingja.cardpackage.activity.HouseActivity;
import com.kingja.cardpackage.activity.RentActivity;
import com.kingja.cardpackage.activity.ShopActivity;
import com.kingja.cardpackage.util.GoUtil;
import com.tdr.wisdome.actvitiy.MsgActivity;
import com.tdr.wisdome.base.MyApplication;
import com.tdr.wisdome.util.Utils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * <p/>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 * Created by Linus_xie on 2016/8/31.
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            processCustomMessage(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
//            processCustomMessage(context, bundle);
//            sendMsgToChild(context, bundle);
            EventBus.getDefault().post(new RefreshMsgEvent());

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");

            //打开自定义的Activity
            //i.putExtras(bundle);
            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //context.startActivity(i);
            try {
                JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                String cardCode = json.getString("CardCode");
                String data = Utils.initNullStr(json.getString("data"));
                String sourceId = json.getString("data");
                GoUtil.goActivityOutOfActivity(MyApplication.getContext(), AlarmMineActivity.class);
                //  switch (cardCode) {
                //      case "1001"://我的住房
                //     case "1002"://我的出租房
                //      case "1004"://我的店
                //      case "1007"://出租屋代管
                //          if (!AppUtil.isAppForeground()) {
                //              goActivityOrderByCardCode(cardCode);
                //          }
//
                //                      if (com.tdr.wisdome.util.Constants.getToken().equals("")) {
                //                        GoUtil.goActivityInReceiver(context, LoginActivity.class);
                //                      break;
                //                }
                //              if (com.tdr.wisdome.util.Constants.getUserIdentitycard().equals("")) {
                //                GoUtil.goActivityInReceiver(context, PerfectActivity.class);
                //              break;
                //        }
                //      JPushDispathService.goService(MyApplication.getContext(),cardCode, sourceId);
//
                //                      break;
                //                default:
                //                  break;
//
                //              }


//                Log.e("卡片ID：", cardCode);
//                if (cardCode.equals("1003")) {
//                    Intent intent1003 = new Intent(context, MainCarActivity.class);
//                    intent1003.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    context.startActivity(intent1003);
//                } else if (cardCode.equals("1005")) {
//                    Intent intent1005 = new Intent(context, MainCareActivity.class);
//                    intent1005.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    context.startActivity(intent1005);
//                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction()))

        {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction()))

        {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else

        {
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }

    }

    private void goActivityOrderByCardCode(String cardCode) {
        switch (cardCode) {
            case "1001"://我的住房
                GoUtil.goActivityOutOfActivity(MyApplication.getContext(), HouseActivity.class);
                break;
            case "1002"://我的出租房
                GoUtil.goActivityOutOfActivity(MyApplication.getContext(), RentActivity.class);
                break;
            case "1004"://我的店
                GoUtil.goActivityOutOfActivity(MyApplication.getContext(), ShopActivity.class);
                break;
            case "1007"://出租屋代管
                GoUtil.goActivityOutOfActivity(MyApplication.getContext(), AgentActivity.class);
                break;
            default:
                break;
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    //send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Intent msgIntent = new Intent(MsgActivity.MESSAGE_RECEIVED_ACTION);
        msgIntent.putExtra(MsgActivity.KEY_MESSAGE, message);
        if (!ExampleUtil.isEmpty(extras)) {
            try {
                JSONObject extraJson = new JSONObject(extras);
                if (null != extraJson && extraJson.length() > 0) {
                    msgIntent.putExtra(MsgActivity.KEY_EXTRAS, extras);
                }
            } catch (JSONException e) {

            }

        }
        context.sendBroadcast(msgIntent);
    }

    /***
     * 分发广播，各个子模块根据action接收操作
     *
     * @param context
     * @param bundle
     */
    private void sendMsgToChild(Context context, Bundle bundle) {
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        String cardCode = "";
        try {
            JSONObject jsonObject = new JSONObject(extras);
            cardCode = jsonObject.getString("CardCode");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (cardCode.equals("1001") || cardCode.equals("1002") || cardCode.equals("1004") || cardCode.equals("1007")) {
            Intent msgIntent = new Intent("com.kingja.cardpackage");
            msgIntent.putExtra(MsgActivity.KEY_MESSAGE, message);
            if (!ExampleUtil.isEmpty(extras)) {
                try {
                    JSONObject extraJson = new JSONObject(extras);
                    if (null != extraJson && extraJson.length() > 0) {
                        msgIntent.putExtra(MsgActivity.KEY_EXTRAS, extras);
                    }
                } catch (JSONException e) {

                }

            }
            context.sendBroadcast(msgIntent);
        }
    }

}