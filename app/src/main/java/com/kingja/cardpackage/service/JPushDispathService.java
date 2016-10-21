package com.kingja.cardpackage.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.kingja.cardpackage.Event.AgentDialogEvent;
import com.kingja.cardpackage.Event.HouseDialogEvent;
import com.kingja.cardpackage.Event.RentDialogEvent;
import com.kingja.cardpackage.Event.ShopDialogEvent;
import com.kingja.cardpackage.activity.DetailHouseActivity;
import com.kingja.cardpackage.activity.DetailRentActivity;
import com.kingja.cardpackage.activity.DetailShopActivity;
import com.kingja.cardpackage.entiy.ChuZuWu_Info;
import com.kingja.cardpackage.entiy.ErrorResult;
import com.kingja.cardpackage.entiy.Message_InquireDetailOfMessage;
import com.kingja.cardpackage.entiy.RentBean;
import com.kingja.cardpackage.entiy.ShangPu_ViewInfo;
import com.kingja.cardpackage.net.ThreadPoolTask;
import com.kingja.cardpackage.net.WebServiceCallBack;
import com.kingja.cardpackage.util.Constants;
import com.kingja.cardpackage.util.DataManager;
import com.kingja.cardpackage.util.TempConstants;
import com.kingja.cardpackage.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description：TODO
 * Create Time：2016/9/8 10:41
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class JPushDispathService extends Service {

    private String cardCode;
    private String sourceId;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        cardCode = intent.getStringExtra("CARD_CODE");
        sourceId = intent.getStringExtra("SOURCE_ID");
        getSourceId(cardCode, sourceId);
        return super.onStartCommand(intent, flags, startId);
    }

    public static void goService(Context context, String cardCode, String sourceId) {
        Intent intent = new Intent(context, JPushDispathService.class);
        intent.putExtra("CARD_CODE", cardCode);
        intent.putExtra("SOURCE_ID", sourceId);
        context.startService(intent);

    }

    /**
     * 获取SOURCEID
     *
     * @param cardCode
     * @param sourceId
     */
    private void getSourceId(final String cardCode, String sourceId) {
        ToastUtil.showToast("页面加载中，请耐心等待");
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.TaskID, TempConstants.DEFAULT_TASK_ID);
        param.put("SOURCEID", sourceId);
        param.put("APPCARDTYPE", cardCode);
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), cardCode, Constants.Message_InquireDetailOfMessage, param)
                .setBeanType(Message_InquireDetailOfMessage.class)
                .setCallBack(new WebServiceCallBack<Message_InquireDetailOfMessage>() {
                    @Override
                    public void onSuccess(Message_InquireDetailOfMessage bean) {
                        dispathCardCode(cardCode, bean.getContent().getOTHERID(), bean.getContent().getROOMID());

                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                    }
                }).build().execute();
    }

    private void postEventOrderByCardCode(String cardCode, boolean ifShow) {

        switch (cardCode) {
            case "1001"://我的住房
                EventBus.getDefault().post(new HouseDialogEvent(ifShow));
                break;
            case "1002"://我的出租房
                EventBus.getDefault().post(new RentDialogEvent(ifShow));
                break;
            case "1004"://我的店
                EventBus.getDefault().post(new ShopDialogEvent(ifShow));
                break;
            case "1007"://出租屋代管
                EventBus.getDefault().post(new AgentDialogEvent(ifShow));
                break;
        }
    }

    /**
     * 获取otherid 后根据cardCode进行事件分发
     *
     * @param cardCode
     * @param otherid
     */

    private void dispathCardCode(String cardCode, String otherid, String roomId) {
        switch (cardCode) {
            case "1001"://我的住房
                loadHouse(cardCode, otherid, roomId);
                break;
            case "1002"://我的出租房
                loadRent(cardCode, otherid);
                break;
            case "1004"://我的店
                loadShop(cardCode, otherid);
                break;
            case "1007"://出租屋代管
                loadRent(cardCode, otherid);
                break;
        }

    }

    /**
     * 获取我的住房信息
     *
     * @param cardCode
     * @param otherid
     * @param roomId
     */
    private void loadHouse(String cardCode, String otherid, final String roomId) {
        EventBus.getDefault().post(new HouseDialogEvent(true));
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.TaskID, TempConstants.DEFAULT_TASK_ID);
        param.put("HouseID", otherid);
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), cardCode, Constants.ChuZuWu_Info, param)
                .setBeanType(ChuZuWu_Info.class)
                .setCallBack(new WebServiceCallBack<ChuZuWu_Info>() {
                    @Override
                    public void onSuccess(ChuZuWu_Info bean) {
                        EventBus.getDefault().post(new HouseDialogEvent(false));
                        DetailHouseActivity.goActivityInReceiver(getApplicationContext(), bean.getContent(), getRoomBeanByRoomId(bean.getContent().getRoomList(), roomId));
                    }


                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        EventBus.getDefault().post(new HouseDialogEvent(false));
                    }
                }).build().execute();
    }

    private RentBean.RoomListBean getRoomBeanByRoomId(List<RentBean.RoomListBean> roomList, String roomId) {
        for (RentBean.RoomListBean bean : roomList) {
            if (roomId.equals(bean.getROOMID())) {
                return bean;
            }
        }
        return null;
    }


    /**
     * 获取出租屋信息
     *
     * @param cardCode
     * @param otherid
     */
    private void loadRent(final String cardCode, String otherid) {
        EventBus.getDefault().post(new RentDialogEvent(true));
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.TaskID, TempConstants.DEFAULT_TASK_ID);
        param.put(TempConstants.HOUSEID, otherid);
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), cardCode, Constants.ChuZuWu_Info, param)
                .setBeanType(ChuZuWu_Info.class)
                .setCallBack(new WebServiceCallBack<ChuZuWu_Info>() {
                    @Override
                    public void onSuccess(ChuZuWu_Info bean) {
                        EventBus.getDefault().post(new RentDialogEvent(false));
                        DetailRentActivity.goActivityInReceiver(getApplicationContext(), bean.getContent());
                    }


                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        EventBus.getDefault().post(new RentDialogEvent(false));
                    }
                }).build().execute();
    }

    /**
     * 获取店铺信息
     *
     * @param cardCode
     * @param otherid
     */
    private void loadShop(String cardCode, String otherid) {
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.TaskID, TempConstants.DEFAULT_TASK_ID);
        param.put(TempConstants.SHOPID, otherid);
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), cardCode, Constants.ShangPu_ViewInfo, param)
                .setBeanType(ShangPu_ViewInfo.class)
                .setCallBack(new WebServiceCallBack<ShangPu_ViewInfo>() {
                    @Override
                    public void onSuccess(ShangPu_ViewInfo bean) {
                        DetailShopActivity.goActivityInReceiver(getApplicationContext(), bean.getContent());
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                    }
                }).build().execute();
    }


}
