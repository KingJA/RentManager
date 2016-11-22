package com.tdr.wisdome.actvitiy;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.google.gson.Gson;
import com.kingja.cardpackage.Event.MainDialogEvent;
import com.kingja.cardpackage.activity.AgentActivity;
import com.kingja.cardpackage.activity.HouseActivity;
import com.kingja.cardpackage.activity.RentActivity;
import com.kingja.cardpackage.activity.ShopActivity;
import com.kingja.cardpackage.util.ActivityManager;
import com.kingja.cardpackage.util.DataManager;
import com.kingja.cardpackage.util.DialogUtil;
import com.kingja.cardpackage.util.GoUtil;
import com.tdr.wisdome.R;
import com.tdr.wisdome.adapter.ItemAdapter;
import com.tdr.wisdome.amap.LocationTask;
import com.tdr.wisdome.amap.OnLocationGetListener;
import com.tdr.wisdome.amap.PositionEntity;
import com.tdr.wisdome.base.MyApplication;
import com.tdr.wisdome.model.CardInfo;
import com.tdr.wisdome.model.CityInfo;
import com.tdr.wisdome.util.Constants;
import com.tdr.wisdome.util.ImageService;
import com.tdr.wisdome.util.Utils;
import com.tdr.wisdome.util.WebServiceUtils;
import com.tdr.wisdome.view.DraggableGridViewPager;
import com.tdr.wisdome.view.ZProgressHUD;
import com.tdr.wisdome.view.niftydialog.NiftyDialogBuilder;
import com.tdr.wisdome.view.popupwindow.MenuPop;

import net.tsz.afinal.FinalDb;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * 卡包主页面
 * Created by Linus_Xie on 2016/8/1.
 */
public class MainActivity extends Activity implements View.OnClickListener, MenuPop.OnMenuPopClickListener, OnLocationGetListener, Handler.Callback {
    private static final String TAG = "MainActivity";

    private Context mContext;

    private ItemAdapter mItemAdapter;

    private ArrayAdapter<String> mAdapter;
    private ZProgressHUD mProgressHUD;

    private FinalDb db;
    private Gson mGson = new Gson();

    private List<CardInfo> list = new ArrayList<CardInfo>();//卡片数组列表

    private String locCity = "";
    private String cityCode = "";//城市代码

    //获取城市的回调值
    private final static int LOCKEY = 2003;

    //加载  照片数据
    private int[] mPics = new int[]{R.mipmap.image_myhome, R.mipmap.image_myrental, R.mipmap.image_mycar,
            R.mipmap.image_mystore, R.mipmap.image_mycare, R.mipmap.image_severstore, R.mipmap.image_rentalescrow
    };

    //加载 卡片名字
    private String[] mItems = new String[]{"我的住房", "我的出租房", "我的车", "我的店", "亲情关爱"
            , "服务商城", "出租房代管"};

    private String[] mCardCode = new String[]{"1001", "1002",
            "1003", "1004", "1005", "1006", "1007"};

    private List<Integer> newPic = new ArrayList<>();
    private List<String> newItems = new ArrayList<>();
    private List<String> newCardCode = new ArrayList<>();

    public static List<String> mCardList = Arrays.asList("1001", "1002", "1003", "1004", "1005", "1006", "1007");
    public static List<String> mCurrentList = new ArrayList<>();

    //获取过来的图片
    private List<Bitmap> listPic = new ArrayList<>();
    //获取过来的卡片名字
    private List<String> listCardName = new ArrayList<>();
    //获取过来的卡片代码s
    private List<String> listCardCode = new ArrayList<>();

    private MenuPop menuPop;

    private LocationTask mLocationTask;

    private String cityName = "";

    private Activity mActivity;

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityManager.getAppManager().addActivity(this);
        EventBus.getDefault().register(this);
        Log.e(TAG, "TakkId: "+getTaskId());
        Log.e(TAG, "MainActivity: "+MainActivity.this.hashCode());
        mContext = this;
        mHandler = new Handler(this);
        mActivity = this;
        //Permissions();

        db = FinalDb.create(mContext, "WisdomE.db", false);

        mLocationTask = LocationTask.getInstance(getApplicationContext());
        mLocationTask.setOnLocationGetListener(this);
        mLocationTask.startSingleLocate();

        initView();//初始化布局
        initLoc();//初始化定位
        initCityData();//获取支持的城市列表

    }

    private ImageView image_person, image_add;
    private LinearLayout linear_city;
    private TextView text_cityName, text_cardName;
    private DraggableGridViewPager draggable_grid_view_pager;


    private void initView() {
        initPerfectTipDialog();
        image_person = (ImageView) findViewById(R.id.image_person);
        image_person.setOnClickListener(this);
        image_add = (ImageView) findViewById(R.id.image_add);
        image_add.setOnClickListener(this);

        linear_city = (LinearLayout) findViewById(R.id.linear_city);
        linear_city.setOnClickListener(this);
        text_cityName = (TextView) findViewById(R.id.text_cityName);
        draggable_grid_view_pager = (DraggableGridViewPager) findViewById(R.id.draggable_grid_view_pager);

        mAdapter = new ArrayAdapter<String>(this, 0) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = (RelativeLayout) getLayoutInflater().inflate(R.layout.draggable_item, null);
                    TextView text_cardName = (TextView) convertView.findViewById(R.id.text_cardName);
                    TextView text_cardCode = (TextView) convertView.findViewById(R.id.text_cardCode);
                    ImageView image_cardLine = (ImageView) convertView.findViewById(R.id.image_cardLine);
                    if (Constants.getToken().equals("")) {
                        convertView.setBackgroundResource(mPics[position]);
                        text_cardName.setText(mItems[position]);
                        text_cardCode.setText(mCardCode[position]);
                        image_cardLine.setVisibility(View.VISIBLE);
                    } else {
                        String[] cardCode = Constants.getCardCode().split(",");
                        String[] cardName = Constants.getCardName().split(",");
                        //for (int i = 0; i < cardCode.length; i++) {
                        text_cardCode.setText(cardCode[position]);
                        //text_cardName.setText(cardName[i]);
                        if (cardCode[position].equals("1001")) {
                            convertView.setBackgroundResource(mPics[0]);
                            text_cardName.setText(mItems[0]);
                            image_cardLine.setVisibility(View.VISIBLE);
                        } else if (cardCode[position].equals("1002")) {
                            convertView.setBackgroundResource(mPics[1]);
                            text_cardName.setText(mItems[1]);
                            image_cardLine.setVisibility(View.VISIBLE);
                        } else if (cardCode[position].equals("1003")) {
                            convertView.setBackgroundResource(mPics[2]);
                            text_cardName.setText(mItems[2]);
                            image_cardLine.setVisibility(View.VISIBLE);
                        } else if (cardCode[position].equals("1004")) {
                            convertView.setBackgroundResource(mPics[3]);
                            text_cardName.setText(mItems[3]);
                            image_cardLine.setVisibility(View.VISIBLE);
                        } else if (cardCode[position].equals("1005")) {
                            convertView.setBackgroundResource(mPics[4]);
                            text_cardName.setText(mItems[4]);
                            image_cardLine.setVisibility(View.VISIBLE);
                        } else if (cardCode[position].equals("1006")) {
                            convertView.setBackgroundResource(mPics[5]);
                            text_cardName.setText(mItems[5]);
                            image_cardLine.setVisibility(View.VISIBLE);
                        } else if (cardCode[position].equals("1007")) {
                            convertView.setBackgroundResource(mPics[6]);
                            text_cardName.setText(mItems[6]);
                            image_cardLine.setVisibility(View.VISIBLE);
                        }
                        //}
                    }

                    text_cardName.setTextColor(getResources().getColor(R.color.white));
                }
                return convertView;
            }
        };

        draggable_grid_view_pager.setAdapter(mAdapter);
        draggable_grid_view_pager.setOnPageChangeListener(new DraggableGridViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        draggable_grid_view_pager.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView text_cardCode = (TextView) draggable_grid_view_pager.getChildAt(position).findViewById(R.id.text_cardCode);
                String codeName = text_cardCode.getText().toString();
                Intent intent = new Intent();
                if (Constants.getToken().equals("")) {
                    intent.setClass(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.slide_in_left,
                            android.R.anim.slide_out_right);
                    return;
                }
                switch (codeName) {
                    case "1003"://我的车
                        intent.setClass(MainActivity.this, MainCarActivity.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.slide_in_left,
                                android.R.anim.slide_out_right);
                        break;
                    case "1005"://亲情关爱
                        intent.setClass(MainActivity.this, MainCareActivity.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.slide_in_left,
                                android.R.anim.slide_out_right);
                        break;
                    case "1006"://服务商城
                        Utils.myToast(mContext, "当前地区暂未开通此项服务");
                        break;
                    case "1001"://我的住房
                        if (checkIfPerfect()) break;
                        GoUtil.goActivity(MainActivity.this, HouseActivity.class);
                        break;
                    case "1002"://我家出租房
                        if (checkIfPerfect()) break;
                        GoUtil.goActivity(MainActivity.this, RentActivity.class);
                        break;
                    case "1004"://我的店
                        if (checkIfPerfect()) break;
                        GoUtil.goActivity(MainActivity.this, ShopActivity.class);
                        break;
                    case "1007"://出租房代管
                        if (checkIfPerfect()) break;
                        GoUtil.goActivity(MainActivity.this, AgentActivity.class);
                        break;
                }
            }
        });

        draggable_grid_view_pager.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return true;
            }
        });

        draggable_grid_view_pager.setOnRearrangeListener(new DraggableGridViewPager.OnRearrangeListener() {
            @Override
            public void onRearrange(int oldIndex, int newIndex) {
                String item = mAdapter.getItem(oldIndex);
                mAdapter.setNotifyOnChange(false);
                mAdapter.remove(item);
                mAdapter.insert(item, newIndex);
                mAdapter.notifyDataSetChanged();
                //String cardCode = mAdapter.getItem(newIndex);
                //TextView text_cardCode = (TextView) draggable_grid_view_pager.getChildAt(newIndex).findViewById(R.id.text_cardCode);
                //Constants.setCardCode();
            }
        });

        if (!Constants.getCardCode().equals("")) {
            for (int i = 0; i < Constants.getCardCode().split(",").length; i++) {
                mAdapter.add("");
            }
        } else if (!Constants.getToken().equals("")) {

        } else {
            for (int i = 0; i < mPics.length; i++) {
                mAdapter.add("");
            }
        }
        menuPop = new MenuPop(image_add, MainActivity.this);
        menuPop.setOnMenuPopClickListener(MainActivity.this);

        mProgressHUD = new ZProgressHUD(mContext);
        mProgressHUD.setMessage("数据加载中");
        mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
    }

    private void initPerfectTipDialog() {
        mAddInfoDialog = DialogUtil.getDoubleDialog(MainActivity.this, "资料不完整，前去完善资料", "取消", "确定");
        mAddInfoDialog.setOnBtnClickL(new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                mAddInfoDialog.dismiss();
            }
        }, new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                GoUtil.goActivity(MainActivity.this, PerfectActivity.class);
                mAddInfoDialog.dismiss();
            }
        });
    }

    private NormalDialog mAddInfoDialog;

    private boolean checkIfPerfect() {
        if ("".equals(DataManager.getIdCard())) {
            mAddInfoDialog.show();
            return true;
        }
        return false;
    }

    private void initCityData() {



        HashMap<String, String> map = new HashMap<>();
        map.put("token", "");
        map.put("cardType", "");
        map.put("taskId", "");
        map.put("DataTypeCode", "GetCityList");
        map.put("content", "");

        WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map, new WebServiceUtils.WebServiceCallBack() {
            @Override
            public void callBack(String result) {
                if (result != null) {
                    try {
                        JSONObject object = new JSONObject(result);
                        int resultCode = object.getInt("ResultCode");
                        String resultText = Utils.initNullStr(object.getString("ResultText"));
                        if (resultCode == 0) {
                            String content = object.getString("Content");
                            JSONArray jsonArray = new JSONArray(content);
                            List<CityInfo> list = new ArrayList<CityInfo>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                CityInfo mInfo = new CityInfo();
                                mInfo.setCityCode(Utils.initNullStr(obj.getString("CityCode")));
                                mInfo.setCityName(Utils.initNullStr(obj.getString("CityName")));
                                mInfo.setCityPinYin(Utils.initNullStr(obj.getString("FullWord").toLowerCase()));
                                mInfo.setShortName(Utils.initNullStr(obj.getString("ShortName")));
                                mInfo.setFirstWord(Utils.initNullStr(obj.getString("FirstWord")));
                                mInfo.setParentCode(Utils.initNullStr(obj.getString("ParentCode")));
                                mInfo.setCityType(Utils.initNullStr(obj.getString("CityType")));
                                mInfo.setSort(Utils.initNullStr(obj.getString("Sort")));
                                mInfo.setIsValid(Utils.initNullStr(obj.getString("IsValid")));
                                list.add(mInfo);
                            }
                            if (list != null && list.size() > 0) {
                                db.deleteAll(CityInfo.class);
                                for (int i = 0; i < list.size(); i++) {
                                    db.save(list.get(i));
                                }
                            }
                            mProgressHUD.dismiss();
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

    private void initCard(String cardLogo, String cardCode) {
        ImageService.getPic(cardLogo, cardCode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_person:
                if (Constants.getToken().equals("")) {
                    //Utils.myToast(mContext, "请先登录");
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                } else {
                    Intent intent = new Intent(mContext, PersonalActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    finish();
                }
                break;

            case R.id.image_add:
                menuPop.showPopupWindowDownOffset();
                break;

            case R.id.linear_city:
                Intent intent = new Intent(mContext, CityPickerActivity.class);
                intent.putExtra("activity", "MainActivity");
                startActivityForResult(intent, LOCKEY);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
        }
    }

    @Override
    public void onMenuPop(int position) {
        switch (position) {
            case 0:
                if (Constants.getToken().equals("")) {
                    dialogShow();
                } else {
                    Intent intent0 = new Intent(mContext, MsgActivity.class);
                    startActivity(intent0);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    finish();
                }
                break;

            case 1:
                if (Constants.getToken().equals("")) {
                    dialogShow();
                } else {
                    Intent intent1 = new Intent(mContext, AddManagerActivity.class);
                    startActivity(intent1);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    finish();
                }
                break;

            case 2:
                if (Constants.getToken().equals("")) {
                    dialogShow();
                } else {
                    Intent intent2 = new Intent(mContext, PersonalActivity.class);
                    startActivity(intent2);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    finish();
                }
                break;
        }
        menuPop.dismiss();
    }

    private NiftyDialogBuilder dialogBuilder;
    private NiftyDialogBuilder.Effectstype effectstype;

    private void dialogShow() {
        if (dialogBuilder != null && dialogBuilder.isShowing())
            return;

        dialogBuilder = NiftyDialogBuilder.getInstance(this);

        effectstype = NiftyDialogBuilder.Effectstype.Fadein;
        dialogBuilder.withTitle("提示").withTitleColor("#333333").withMessage("请先登录账号")
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
                Intent intent = new Intent();
                intent.setClass(mContext, LoginActivity.class);
                startActivity(intent);
            }
        }).show();

    }

    @Override
    protected void onResume() {
        super.onResume();

        HashMap<String, String> map = new HashMap<>();
        map.put("token", "");
        map.put("cardType", "");
        map.put("taskId", "");
        map.put("DataTypeCode", "GetVersion");
        map.put("content", "");

        WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map, new WebServiceUtils.WebServiceCallBack() {
            @Override
            public void callBack(String result) {
                if (result != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Utils.myToast(mContext, "获取数据错误，请稍后重试！");
                }
            }
        });

        if (!Constants.getToken().equals("")) {
            image_person.setImageResource(R.mipmap.image_personon);
            getUserCard();
        }
        if (!Constants.getCityCode().equals("")) {
            sendCurrentCityCode(Constants.getCityCode(), Constants.getCityName());
        }

        if (Constants.getCityCode().equals("")) {
            Utils.myToast(mContext, "请先去城市选择页设置城市");
        }

        JPushInterface.onResume(mContext);

    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(mContext);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case LOCKEY:
                if (resultCode == RESULT_OK) {
                    CityInfo mInfo = (CityInfo) data.getSerializableExtra("city");
                    cityCode = mInfo.getCityCode();
                    Constants.setCityCode(cityCode);
                    Constants.setCityName(mInfo.getCityName());
                    text_cityName.setText(mInfo.getCityName());
                    sendCurrentCityCode(cityCode, mInfo.getCityName());
                }

                break;

            case 100:
                if (data == null) {
                    Utils.myToast(mContext, "没有扫描到二维码");
                    break;
                } else {
                    String cardCode = data.getStringExtra("cardCode");
                    draggable_grid_view_pager.removeAllViews();
                    mAdapter.clear();
                    for (int i = 0; i < cardCode.split(",").length; i++) {
                        mAdapter.add("");
                    }
                    mAdapter.notifyDataSetChanged();
                    draggable_grid_view_pager.setAdapter(mAdapter);
                }
                break;
        }
    }

    private void sendCurrentCityCode(final String cityCode, final String cityName) {
        if (!Constants.getToken().equals("")) {
            //Token不为空则说明用户已经登录，发送设置当前城市指令
            JSONObject json = new JSONObject();
            try {
                json.put("CityCode", cityCode);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HashMap<String, String> map = new HashMap<>();
            map.put("token", Constants.getToken());
            map.put("cardType", "");
            map.put("taskId", "");
            map.put("DataTypeCode", "EditCurrentCity");
            map.put("content", json.toString());
            WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map, new WebServiceUtils.WebServiceCallBack() {
                @Override
                public void callBack(String result) {
                    if (result != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            int resultCode = jsonObject.getInt("ResultCode");
                            String resultText = Utils.initNullStr(jsonObject.getString("ResultText"));
                            if (resultCode == 0) {
                                Constants.setCityCode(cityCode);
                                Constants.setCityName(cityName);
                                getUserCard();
                            } else {
                                Log.e(TAG, resultText);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, "设置当前城市JSON解析出错");
                        }
                    } else {
                        Log.e(TAG, "获取数据错误，请稍后重试！");
                    }
                }
            });
        }
    }

    private void getUserCard() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("citycode", Constants.getCityCode());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("token", Constants.getToken());
        map.put("cardType", "");
        map.put("taskId", "");
        map.put("DataTypeCode", "GetUserCards");
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
                            String content = object.getString("Content");
                            Log.e(TAG, content);
                            JSONObject obj1 = new JSONObject(content);
                            String cardList = obj1.getString("cardlist");
                            JSONArray jsonArray = new JSONArray(cardList);
                            StringBuffer cardName = new StringBuffer("");
                            StringBuffer cardCode = new StringBuffer("");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                CardInfo mInfo = new CardInfo();
                                mInfo.setListID(Utils.initNullStr(obj.getString("ListID")));
                                mInfo.setUserID(Utils.initNullStr(obj.getString("UserID")));
                                mInfo.setUserCityID(Utils.initNullStr(obj.getString("UserCityID")));
                                mInfo.setCityCode(Utils.initNullStr(obj.getString("CityCode")));
                                mInfo.setCardCode(Utils.initNullStr(obj.getString("CardCode")));
                                mInfo.setCardName(Utils.initNullStr(obj.getString("CardName")));
                                mInfo.setCardLogo(Utils.initNullStr(obj.getString("CardLogo")));
                                mInfo.setCreateTime(Utils.initNullStr(obj.getString("CreateTime")));
                                mInfo.setIsDelete(Utils.initNullStr(obj.getString("IsDelete")));
                                listCardName.add(Utils.initNullStr(obj.getString("CardName")));
                                listCardCode.add(Utils.initNullStr(obj.getString("CardCode")));
                                if (i == 0) {
                                    // cardName.append(Utils.initNullStr(obj.getString("CardName")));
                                    cardName.append("亲情关爱");
                                    cardCode.append(Utils.initNullStr(obj.getString("CardCode")));
                                } else {
                                    //cardName.append("," + Utils.initNullStr(obj.getString("CardName")));
                                    cardName.append("," + "亲情关爱");
                                    cardCode.append("," + Utils.initNullStr(obj.getString("CardCode")));
                                }
                                list.add(mInfo);
                            }
                            Constants.setCardCode(cardCode.toString());
                            Constants.setCardName(cardName.toString());

                            draggable_grid_view_pager.removeAllViews();
                            mAdapter.clear();
                            for (int i = 0; i < Constants.getCardCode().split(",").length; i++) {
                                mAdapter.add("");
                            }
                            // mAdapter.notifyDataSetChanged();
                            draggable_grid_view_pager.setAdapter(mAdapter);
                            if (list != null && list.size() > 0) {
                                db.deleteAll(CardInfo.class);
                                for (int i = 0; i < list.size(); i++) {
                                    db.save(list.get(i));
                                    //initCard(list.get(i).getCardLogo(), list.get(i).getCardCode());
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e(TAG, "获取卡片数据错误");
                }
            }
        });
    }


    private void initLoc() {
       /* mLocationClient = new AMapLocationClient(this);
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setOnceLocation(true);
        mLocationClient.setLocationOption(option);
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        locCity = aMapLocation.getCity();
                        text_cityName.setText(locCity);
                        String district = aMapLocation.getDistrict();
                    } else {
                        //定位失败
                    }
                }
            }
        });
        mLocationClient.startLocation();*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationTask.onDestroy();
        ActivityManager.getAppManager().finishActivity(this);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onLocationGet(PositionEntity entity) {
        cityName = entity.city;
        text_cityName.setText(cityName);
    }

    @Override
    public void onRegecodeGet(PositionEntity entity) {
        cityName = entity.city;
        text_cityName.setText(cityName);
    }

    //安卓6.0 有些手机需要动态添加权限
    private final int requestCode = 1;
    int granted = PackageManager.PERMISSION_GRANTED;
    String[] phone = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE};
    String[] sd = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    String[] location = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE
    };

    String[] camera = {
            Manifest.permission.CAMERA,
    };

    public void Permissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            // Check if we have write permission
            int permission = ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.READ_PHONE_STATE);

            if (permission != granted) {
                // We don't have permission so prompt the user
                ActivityCompat.requestPermissions(mActivity, phone, requestCode);
            }

            int permission2 = ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (permission2 != granted) {
                ActivityCompat.requestPermissions(mActivity, sd, requestCode);
            }

            int permission3 = ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION);
            int permission4 = ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION);
            int permission5 = ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_WIFI_STATE);

            if (permission3 != granted || permission4 != granted || permission5 != granted) {
                ActivityCompat.requestPermissions(mActivity, location, requestCode);
            }

            int permission6 = ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA);
            if (permission6 != granted) {
                ActivityCompat.requestPermissions(mActivity, camera, requestCode);
            }
        }
    }

    private int updateCount;// 更新次数

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case Constants.HANDLER_KEY_GETVERSION_SUCCESS:
                break;
            case Constants.HANDLER_KEY_GETVERSION_FAIL:
                break;
        }
        return false;
    }

    public static void goActivityInReceiver() {
        Intent intent = new Intent(MyApplication.getContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyApplication.getContext().startActivity(intent);
    }



    private long firstTime;

    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if ((secondTime - firstTime) > 2000) {
            Toast.makeText(this, "长按两次退出", Toast.LENGTH_SHORT).show();
            firstTime = secondTime;
        } else {
            finish();
            System.exit(0);
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MainDialogEvent event) {
        Log.e(TAG, "onMessageEvent: "+event.isShowDialog() );
        if (event.isShowDialog()) {
            mProgressHUD.show();
        }else{
            mProgressHUD.dismiss();
        }

    }
}
