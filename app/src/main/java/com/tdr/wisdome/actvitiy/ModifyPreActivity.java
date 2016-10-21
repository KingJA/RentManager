package com.tdr.wisdome.actvitiy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tdr.wisdome.R;
import com.tdr.wisdome.adapter.BrandAdapter;
import com.tdr.wisdome.model.BikeCode;
import com.tdr.wisdome.model.SortModel;
import com.tdr.wisdome.util.CharacterParser;
import com.tdr.wisdome.util.Constants;
import com.tdr.wisdome.util.PinyinComparator;
import com.tdr.wisdome.util.Utils;
import com.tdr.wisdome.util.WebServiceUtils;
import com.tdr.wisdome.view.ZProgressHUD;
import com.tdr.wisdome.view.niftydialog.NiftyDialogBuilder;

import net.tsz.afinal.FinalDb;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 预登记信息修改
 * Created by Linus_Xie on 2016/8/29.
 */
public class ModifyPreActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener, Handler.Callback {
    private static final String TAG = "ModifyPreActivity";

    private String prerateID = "";
    private Context mContext;
    private Handler mHandler;

    private ZProgressHUD mProgressHUD;

    private CharacterParser characterParser;// 汉字转拼音类
    private PinyinComparator pinyinComparator;// 根据拼音来排列ListView里面的数据类

    private BrandAdapter brandsAdapter; // 品牌适配器
    private BrandAdapter colorsAdapter;// 颜色适配器
    private List<SortModel> brandsList = new ArrayList<SortModel>();// 品牌列表
    private List<SortModel> colorsList = new ArrayList<SortModel>();// 颜色列表
    private HashMap<Integer, Integer> brandMap = new HashMap<Integer, Integer>();
    private HashMap<Integer, Integer> colorsMap = new HashMap<Integer, Integer>();
    private List<BikeCode> brands_code = new ArrayList<BikeCode>();
    private List<BikeCode> colors_code = new ArrayList<BikeCode>();

    private String mBrandId = "";// 品牌的ID
    private String mBrand = "";// 品牌名称
    private String selectedBrand;// 选中品牌名称
    private String mColor = "";// 颜色
    private String mColorId = "";// 颜色的ID
    private String selectedColor;// 选中颜色


    // FinalDb
    private FinalDb db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifypre);
        mContext = this;
        mHandler = new Handler(this);
        prerateID = getIntent().getStringExtra("prerateID");
        db = FinalDb.create(mContext, "WisdomE.db");

        initView();
        initData();
    }

    private ImageView image_back;
    private TextView text_title;
    private TextView text_deal;

    private EditText edit_registrationNumber, edit_carMotorNum, edit_carFrameNum, edit_carBuyPrice, edit_carBuyName, edit_carBuyIdentity, edit_carBuyPhone, edit_carBuyAlternatePhone, edit_remarks;
    private TextView text_carBrand, text_carColor, text_carBuyTime;

    private void initView() {
        image_back = (ImageView) findViewById(R.id.image_back);
        image_back.setOnClickListener(this);
        text_title = (TextView) findViewById(R.id.text_title);
        text_title.setText("预登记信息");
        text_deal = (TextView) findViewById(R.id.text_deal);
        text_deal.setText("完成");
        text_deal.setOnClickListener(this);
        text_deal.setVisibility(View.VISIBLE);

        edit_registrationNumber = (EditText) findViewById(R.id.edit_registrationNumber);
        text_carBrand = (TextView) findViewById(R.id.text_carBrand);
        text_carBrand.setOnClickListener(this);
        text_carColor = (TextView) findViewById(R.id.text_carColor);
        text_carColor.setOnClickListener(this);
        edit_carMotorNum = (EditText) findViewById(R.id.edit_carMotorNum);
        edit_carFrameNum = (EditText) findViewById(R.id.edit_carFrameNum);
        text_carBuyTime = (TextView) findViewById(R.id.text_carBuyTime);
        text_carBuyTime.setOnClickListener(this);
        edit_carBuyPrice = (EditText) findViewById(R.id.edit_carBuyPrice);
        edit_carBuyName = (EditText) findViewById(R.id.edit_carBuyName);
        edit_carBuyIdentity = (EditText) findViewById(R.id.edit_carBuyIdentity);
        edit_carBuyPhone = (EditText) findViewById(R.id.edit_carBuyPhone);
        edit_carBuyAlternatePhone = (EditText) findViewById(R.id.edit_carBuyAlternatePhone);
        edit_remarks = (EditText) findViewById(R.id.edit_remarks);

        mProgressHUD = new ZProgressHUD(mContext);

    }


    private void initData() {
        mProgressHUD.setMessage("数据获取中...");
        mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
        mProgressHUD.show();
        JSONObject object = new JSONObject();
        try {
            object.put("prerateID", prerateID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("token", Constants.getToken());
        map.put("cardType", "1003");
        map.put("taskId", "");
        map.put("encryption", "");
        map.put("DataTypeCode", "GetPreRateOne");
        map.put("content", object.toString());

        WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map, new WebServiceUtils.WebServiceCallBack() {
            @Override
            public void callBack(String result) {
                if (result != null) {
                    try {
                        JSONObject object = new JSONObject(result);
                        int resultCode = object.getInt("ResultCode");
                        String resultText = Utils.initNullStr(object.getString("ResultText"));
                        if (resultCode == 0) {
                            String preInfo = object.getString("Content");
                            JSONObject jsonObject = new JSONObject(preInfo);
                            edit_registrationNumber.setText(Utils.initNullStr(jsonObject.getString("PrerateName")));
                            text_carBrand.setText(Utils.initNullStr(jsonObject.getString("Vehiclebrand")));
                            mBrandId = Utils.initNullStr(jsonObject.getString("Vehiclebrand"));
                            String brandName = db.findAllByWhere(BikeCode.class, " code=\'" + mBrandId + "\'").get(0).getName();
                            text_carBrand.setText(brandName);
                            mColorId = Utils.initNullStr(jsonObject.getString("ColorID"));
                            String colorName = db.findAllByWhere(BikeCode.class, " code=\'" + mColorId + "\'").get(0).getName();
                            text_carColor.setText(colorName);
                            edit_carMotorNum.setText(Utils.initNullStr(jsonObject.getString("Engineno")));
                            edit_carFrameNum.setText(Utils.initNullStr(jsonObject.getString("Shelvesno")));
                            text_carBuyTime.setText(Utils.initNullStr(jsonObject.getString("BuyDate")));
                            edit_carBuyPrice.setText(Utils.initNullStr(jsonObject.getString("Price")));
                            edit_carBuyName.setText(Utils.initNullStr(jsonObject.getString("OwnerName")));
                            edit_carBuyIdentity.setText(Utils.initNullStr(jsonObject.getString("Cardid")));
                            edit_carBuyPhone.setText(Utils.initNullStr(jsonObject.getString("Phone1")));
                            edit_carBuyAlternatePhone.setText(Utils.initNullStr(jsonObject.getString("Phone2")));
                            edit_remarks.setText(Utils.initNullStr(jsonObject.getString("Remark")));
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
                finish();
                break;

            case R.id.text_deal:
                if (text_deal.getText().equals("编辑")) {
                    text_deal.setText("完成");
                    edit_registrationNumber.setFocusable(true);
                    text_carBrand.setClickable(true);
                    text_carBrand.setFocusable(true);
                    text_carColor.setClickable(true);
                    text_carColor.setFocusable(true);
                    edit_carMotorNum.setEnabled(true);
                    edit_carMotorNum.setFocusable(true);
                    edit_carFrameNum.setEnabled(true);
                    edit_carFrameNum.setFocusable(true);
                    text_carBuyTime.setClickable(true);
                    text_carBuyTime.setFocusable(true);
                    edit_carBuyPrice.setEnabled(true);
                    edit_carBuyPrice.setFocusable(true);
                    edit_carBuyName.setEnabled(true);
                    edit_carBuyName.setFocusable(true);
                    edit_carBuyIdentity.setEnabled(true);
                    edit_carBuyIdentity.setFocusable(true);
                    edit_carBuyPhone.setEnabled(true);
                    edit_carBuyPhone.setFocusable(true);
                    edit_carBuyAlternatePhone.setEnabled(true);
                    edit_carBuyAlternatePhone.setFocusable(true);
                    edit_remarks.setEnabled(true);
                    edit_remarks.setFocusable(true);
                } else {
                    text_deal.setText("编辑");
                    edit_registrationNumber.setFocusable(false);
                    text_carBrand.setClickable(false);
                    text_carBrand.setFocusable(false);
                    text_carColor.setClickable(false);
                    text_carColor.setFocusable(false);
                    edit_carMotorNum.setEnabled(false);
                    edit_carMotorNum.setFocusable(false);
                    edit_carFrameNum.setEnabled(false);
                    edit_carFrameNum.setFocusable(false);
                    text_carBuyTime.setClickable(false);
                    text_carBuyTime.setFocusable(false);
                    edit_carBuyPrice.setEnabled(false);
                    edit_carBuyPrice.setFocusable(false);
                    edit_carBuyName.setEnabled(false);
                    edit_carBuyName.setFocusable(false);
                    edit_carBuyIdentity.setEnabled(false);
                    edit_carBuyIdentity.setFocusable(false);
                    edit_carBuyPhone.setEnabled(false);
                    edit_carBuyPhone.setFocusable(false);
                    edit_carBuyAlternatePhone.setEnabled(false);
                    edit_carBuyAlternatePhone.setFocusable(false);
                    edit_remarks.setEnabled(false);
                    edit_remarks.setFocusable(false);
                    String registrationNumber = edit_registrationNumber.getText().toString().trim();
                    if (registrationNumber.equals("")) {
                        Utils.myToast(mContext, "请输入登记编号");
                        break;
                    }
                    String carBrand = text_carBrand.getText().toString().trim();
                    if (mBrandId.equals("")) {
                        Utils.myToast(mContext, "请选择车辆品牌");
                        break;
                    }
                    //String carType = edit_carType.getText().toString().trim();

                    String carColor = text_carColor.getText().toString().trim();
                    if (mColorId.equals("")) {
                        Utils.myToast(mContext, "请选择车辆颜色");
                        break;
                    }
                    String carMotorNum = edit_carMotorNum.getText().toString().trim();

                    String carFrameNum = edit_carFrameNum.getText().toString().trim();

                    String carBuyTime = text_carBuyTime.getText().toString().trim();
                    if (carBuyTime.equals("")) {
                        Utils.myToast(mContext, "请选择车辆购买时间");
                        break;
                    }
                    String carBuyPrice = edit_carBuyPrice.getText().toString().trim();

                    String carBuyName = edit_carBuyName.getText().toString().trim();
                    if (carBuyName.equals("")) {
                        Utils.myToast(mContext, "请输入购买人姓名");
                        break;
                    }
                    String carBuyIdentity = edit_carBuyIdentity.getText().toString().trim();
                    if (carBuyIdentity.equals("")) {
                        Utils.myToast(mContext, "请输入购买人身份证");
                        break;
                    }
                    String carBuyPhone = edit_carBuyPhone.getText().toString().trim();
                    if (carBuyPhone.equals("")) {
                        Utils.myToast(mContext, "请输入购买人手机号码");
                        break;
                    }
                    mProgressHUD.setMessage("修改预登记信息...");
                    mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
                    mProgressHUD.show();
                    JSONObject json = new JSONObject();
                    try {
                        json.put("PrerateID", prerateID);
                        json.put("PrerateName", registrationNumber);
                        json.put("Vehiclebrand", mBrandId);
                        json.put("Vehiclemodels", "");
                        json.put("ColorID", mColorId);
                        json.put("Engineno", carMotorNum);
                        json.put("Shelvesnoe", carFrameNum);
                        json.put("BuyDate", carBuyTime);
                        json.put("Price", carBuyPrice);
                        json.put("OwnerName", carBuyName);
                        json.put("Cardid", carBuyIdentity);
                        json.put("Phone1", carBuyPhone);
                        json.put("Phone2", edit_carBuyAlternatePhone.getText().toString().trim());
                        json.put("Remark", edit_remarks.getText().toString().trim());
                        json.put("CreateTime", Utils.getNowTime());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    HashMap<String, String> map = new HashMap<>();
                    map.put("token", Constants.getToken());
                    map.put("cardType", "1003");
                    map.put("taskId", "");
                    map.put("encryption", "");
                    map.put("DataTypeCode", "EditPreRate");
                    map.put("content", json.toString());
                    WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map, new WebServiceUtils.WebServiceCallBack() {
                        @Override
                        public void callBack(String result) {
                            if (result != null) {
                                Log.e(TAG, result);
                                try {
                                    JSONObject object = new JSONObject(result);
                                    int resultCode = object.getInt("ResultCode");
                                    String resultText = Utils.initNullStr(object.getString("ResultText"));
                                    if (resultCode == 0) {
                                        mProgressHUD.dismiss();
                                        Intent intent = new Intent(ModifyPreActivity.this, MainCarActivity.class);
                                        startActivity(intent);
                                        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                                        finish();
                                    } else {
                                        mProgressHUD.dismiss();
                                        Utils.myToast(mContext, resultText);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    mProgressHUD.dismiss();
                                    Utils.myToast(mContext, "JSON解析异常");
                                }
                            } else {
                                mProgressHUD.dismiss();
                                Utils.myToast(mContext, "获取数据错误，请稍后重试！");
                            }
                        }
                    });

                    break;

                }
                break;
        }

    }

    private ListView list_brand;
    private TextView text_brandsearch;
    private LinearLayout progress_layout;

    private NiftyDialogBuilder dialogBuilder;
    private NiftyDialogBuilder.Effectstype effectstype;

    public void dialogShow(int flag, String message) {
        if (dialogBuilder != null && dialogBuilder.isShowing())
            return;

        dialogBuilder = NiftyDialogBuilder.getInstance(this);

        if (flag == 1) {// 车辆品牌
            effectstype = NiftyDialogBuilder.Effectstype.Fadein;
            LayoutInflater mInflater = LayoutInflater.from(this);
            View brand_view = mInflater.inflate(R.layout.layout_brand, null);
            progress_layout = (LinearLayout) brand_view
                    .findViewById(R.id.progress_layout);
            final EditText edit_search = (EditText) brand_view
                    .findViewById(R.id.edit_search);
            text_brandsearch = (TextView) brand_view
                    .findViewById(R.id.text_brandsearch);
            text_brandsearch.setEnabled(false);
            text_brandsearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progress_layout.setVisibility(View.VISIBLE);
                    text_brandsearch.setEnabled(false);
                    list_brand.setVisibility(View.GONE);
                    final String con = edit_search.getText().toString();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            filterData(con);
                        }
                    }).start();
                }
            });
            list_brand = (ListView) brand_view.findViewById(R.id.list_brand);
            list_brand.setOnItemClickListener(this);

            brandsAdapter = new BrandAdapter(mContext, brandsList,
                    brandMap, R.layout.brand_item,
                    new String[]{"text_brandname"},
                    new int[]{R.id.text_brandname});
            list_brand.setAdapter(brandsAdapter);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<SortModel> tempList = new ArrayList<SortModel>();
                    // 查询车牌放在这儿，界面不容易卡顿，用户体验效果更好
                    brands_code = db
                            .findAllByWhere(BikeCode.class, " type='1'");// type=\"4\"，也可以查询到数据
                    Log.e("车牌数量：", "" + brands_code.size());
                    try {
                        for (int i = 0; i < brands_code.size(); i++) {
                            SortModel sortModel = new SortModel();
                            sortModel.setGuid(brands_code.get(i).getCode());
                            sortModel.setName(brands_code.get(i).getName());
                            String pinyin = characterParser
                                    .getSelling(brands_code.get(i).getName());
                            String sortString = pinyin.substring(0, 1)
                                    .toUpperCase();
                            // 正则表达式，判断首字母是否是英文字母
                            if (sortString.matches("[A-Z]")) {
                                sortModel.setSortLetters(sortString
                                        .toUpperCase());
                            } else {
                                sortModel.setSortLetters("#");
                            }
                            tempList.add(sortModel);
                        }
                        // 根据a-z进行排序源数据
                        Collections.sort(tempList, pinyinComparator);
                    } catch (Exception e) {
                        e.printStackTrace();
                        mHandler.sendEmptyMessage(101);
                    }
                    Message message = new Message();
                    message.what = 100;
                    message.obj = tempList;
                    mHandler.sendMessage(message);
                }
            }).start();

            dialogBuilder.withTitle("品牌列表").withTitleColor("#333333").withButton1Text("取消")
                    .withButton2Text("选择")
                    .isCancelableOnTouchOutside(false).withEffect(effectstype)
                    .setCustomView(brand_view, mContext).withMessage(null)//
                    .setButton1Click(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogBuilder.dismiss();
                        }
                    }).setButton2Click(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogBuilder.dismiss();
                    text_carBrand.setText(mBrand);
                    selectedBrand = mBrandId;
                    System.out.println("----------------选中品牌:"
                            + selectedBrand);
                }
            }).show();

        } else if (flag == 2) {
            effectstype = NiftyDialogBuilder.Effectstype.Fadein;
            LayoutInflater mInflater = LayoutInflater.from(this);
            View color_view = mInflater.inflate(R.layout.layout_color, null);
            ListView list_colors = (ListView) color_view
                    .findViewById(R.id.list_colors);
            list_colors.setOnItemClickListener(this);
            colorsList.clear();
            colorsAdapter = new BrandAdapter(mContext, colorsList,
                    colorsMap, R.layout.brand_item,
                    new String[]{"color_name"},
                    new int[]{R.id.text_brandname});
            list_colors.setAdapter(colorsAdapter);
            colors_code = db.findAllByWhere(BikeCode.class, " type='4'");
            Log.e("颜色数量：", "" + colors_code.size());
            for (int i = 0; i < colors_code.size(); i++) {
                SortModel sortModel = new SortModel();
                sortModel.setGuid(colors_code.get(i).getCode());
                sortModel.setName(colors_code.get(i).getName());
                // 汉字转换成拼音
                String pinyin = characterParser.getSelling(colors_code.get(i)
                        .getName());
                String sortString = pinyin.substring(0, 1).toUpperCase();
                // 正则表达式，判断首字母是否是英文字母
                if (sortString.matches("[A-Z]")) {
                    sortModel.setSortLetters(sortString.toUpperCase());
                } else {
                    sortModel.setSortLetters("#");
                }
                colorsList.add(sortModel);
            }

            colorsAdapter.notifyDataSetChanged();

            dialogBuilder.isCancelable(false);
            dialogBuilder.setCustomView(color_view, mContext);
            dialogBuilder.withTitle("颜色列表").withTitleColor("#333333")
                    .withButton1Text("取消").withButton2Text("选择")
                    .withMessage(null).withEffect(effectstype)
                    .setButton1Click(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogBuilder.dismiss();

                        }
                    }).setButton2Click(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogBuilder.dismiss();
                    text_carColor.setText(mColor);
                    selectedColor = mColorId;
                    System.out.println("----------------选中颜色:"
                            + selectedColor);
                }
            }).show();
        }
    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        initCodes();
        brandMap.clear();
        mBrandId = null;
        selectedBrand = null;
        mBrand = null;
        List<SortModel> filterDateList = new ArrayList<SortModel>();
        if (TextUtils.isEmpty(filterStr)) {
            // adapter1.notifyDataSetChanged();
            Message message = new Message();
            message.what = 100;
            filterDateList = brandsList;
            message.obj = filterDateList;
            mHandler.sendMessage(message);
        } else {
            filterDateList.clear();
            for (SortModel sortModel : brandsList) {
                String name = sortModel.getName();
                if (name.toUpperCase().indexOf(
                        filterStr.toString().toUpperCase()) != -1
                        || characterParser.getSelling(name).toUpperCase()
                        .startsWith(filterStr.toString().toUpperCase())
                        || characterParser.getSelling(name).toUpperCase()
                        .contains(filterStr.toString().toUpperCase())) {
                    filterDateList.add(sortModel);
                }
            }
            // 根据a-z进行排序
            Collections.sort(filterDateList, pinyinComparator);
            brandsList.clear();

            Message message = new Message();
            message.what = 102;
            message.obj = filterDateList;
            mHandler.sendMessage(message);
        }
    }

    private void initCodes() {
        brandsList.clear();
        try {
            for (int i = 0; i < brands_code.size(); i++) {
                SortModel sortModel = new SortModel();
                sortModel.setGuid(brands_code.get(i).getCode());
                sortModel.setName(brands_code.get(i).getName());
                // 汉字转换成拼音
                String pinyin = characterParser.getSelling(brands_code.get(i)
                        .getName());
                String sortString = pinyin.substring(0, 1).toUpperCase();// 一雅，yiya，Y
                // 正则表达式，判断首字母是否是英文字母
                if (sortString.matches("[A-Z]")) {
                    sortModel.setSortLetters(sortString.toUpperCase());
                } else {
                    sortModel.setSortLetters("#");
                }
                brandsList.add(sortModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(101);
        }
        // 根据a-z进行排序源数据
        Collections.sort(brandsList, pinyinComparator);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case 100:
                if (dialogBuilder != null && dialogBuilder.isShowing()) {
                    text_brandsearch.setEnabled(true);
                    brandsList = (List<SortModel>) msg.obj;
                    if (brandsList.size() > 0) {
                        progress_layout.setVisibility(View.GONE);
                        brandsAdapter.updateListView(brandsList);
                        list_brand.setVisibility(View.VISIBLE);
                        dialogBuilder.onContentChanged();
                    } else {
                        Utils.myToast(mContext, "没有读取到品牌信息，请确认编码表是否下载成功");
                        dialogBuilder.dismiss();
                    }
                }
                break;

            case 101:
                Utils.myToast(mContext, "车辆品牌加载失败，请重新读取！");
                if (dialogBuilder != null && dialogBuilder.isShowing()) {
                    dialogBuilder.dismiss();
                }
                break;

            case 102:
                if (dialogBuilder != null && dialogBuilder.isShowing()) {
                    progress_layout.setVisibility(View.GONE);
                    text_brandsearch.setEnabled(true);
                    brandsList.clear();
                    brandsList = (List<SortModel>) msg.obj;
                    if (brandsList.size() > 0) {
                        brandsAdapter.updateListView(brandsList);
                        list_brand.setVisibility(View.VISIBLE);
                        dialogBuilder.onContentChanged();
                    } else {
                        Utils.myToast(mContext, "暂时没有相关的品牌信息！");
                        brandsAdapter.updateListView(brandsList);
                        list_brand.setVisibility(View.VISIBLE);
                        dialogBuilder.onContentChanged();
                    }
                }

        }
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        switch (parent.getId()) {
            case R.id.list_brand:
                brandMap.clear();
                brandMap.put(position, 100);
                brandsAdapter.notifyDataSetChanged();
                mBrandId = brandsList.get(position).getGuid();
                mBrand = brandsList.get(position).getName();
                break;
            case R.id.list_colors:
                colorsMap.clear();
                colorsMap.put(position, 100);
                colorsAdapter.notifyDataSetChanged();
                mColor = colorsList.get(position).getName();
                mColorId = colorsList.get(position).getGuid();
                break;
            default:
                break;
        }
    }
}
