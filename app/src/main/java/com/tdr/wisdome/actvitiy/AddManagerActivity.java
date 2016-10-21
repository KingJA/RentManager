package com.tdr.wisdome.actvitiy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdr.wisdome.R;
import com.tdr.wisdome.adapter.LeftCardAdapter;
import com.tdr.wisdome.adapter.OwnedCardAdapter;
import com.tdr.wisdome.model.CardInfo;
import com.tdr.wisdome.util.Constants;
import com.tdr.wisdome.util.Utils;
import com.tdr.wisdome.util.WebServiceUtils;
import com.tdr.wisdome.view.FixedListView;

import net.tsz.afinal.FinalDb;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 卡管理
 * Created by Linus_Xie on 2016/8/30.
 */
public class AddManagerActivity extends AppCompatActivity implements LeftCardAdapter.OnCardAddListener, OwnedCardAdapter.OnCardDeleteListener {

    private FixedListView mLvOwnedCard;
    private FixedListView mLvLeftCard;
    private List<String> mOwnedCardList = new ArrayList<>();
    private List<String> mLeftCardList;
    private OwnedCardAdapter mOwnedCardAdapter;
    private LeftCardAdapter mLeftCardAdapter;

    private ImageView image_back;
    private TextView text_title;

    // FinalDb
    private FinalDb db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getSupportActionBar().hide();
        db = FinalDb.create(AddManagerActivity.this, "WisdomE.db");

        initData();

        image_back = (ImageView) findViewById(R.id.image_back);
        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        text_title = (TextView) findViewById(R.id.text_title);
        text_title.setText("我的卡包");

        mLvOwnedCard = (FixedListView) findViewById(R.id.lv_ownedCard);
        mLvLeftCard = (FixedListView) findViewById(R.id.lv_leftCard);

        mOwnedCardAdapter = new OwnedCardAdapter(this, mOwnedCardList);
        mLeftCardAdapter = new LeftCardAdapter(this, mLeftCardList);
        mLvOwnedCard.setAdapter(mOwnedCardAdapter);
        mLvLeftCard.setAdapter(mLeftCardAdapter);
        mOwnedCardAdapter.setOnCardDeleteListener(this);
        mLeftCardAdapter.setOnCardAddListener(this);
    }

    private void initData() {
        if (!Constants.getCardCode().equals("")) {
            String[] owner = Constants.getCardCode().split(",");
            for (int i = 0; i < Constants.getCardCode().split(",").length; i++) {
                mOwnedCardList.add(owner[i]);
            }
        }
        mLeftCardList = getLeftList(MainActivity.mCardList, mOwnedCardList);

    }

    private void getIntentData() {
        mOwnedCardList = (List<String>) getIntent().getSerializableExtra("Extra_CARD");
        mLeftCardList = getLeftList(MainActivity.mCardList, mOwnedCardList);
    }

    public static List getLeftList(List totelList, List ownList) {
        List leftList = new ArrayList(Arrays.asList(new Object[totelList.size()]));
        Collections.copy(leftList, totelList);
        leftList.removeAll(ownList);
        return leftList;
    }


    @Override
    public void onCardAdd(final String cardType) {
        mLeftCardAdapter.deleteCard(cardType);
        mOwnedCardAdapter.addCard(cardType);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cardcode", cardType);
            jsonObject.put("citycode", Constants.getCityCode());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final HashMap<String, String> map = new HashMap<>();
        map.put("token", Constants.getToken());
        map.put("cardType", "");
        map.put("taskId", "");
        map.put("DataTypeCode", "AddUserCards");
        map.put("content", jsonObject.toString());
        WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map, new WebServiceUtils.WebServiceCallBack() {
            @Override
            public void callBack(String result) {
                if (result != null) {
                    Log.e("添加卡片", result);
                    try {
                        JSONObject json = new JSONObject(result);
                        int resultCode = json.getInt("ResultCode");
                        String resultText = Utils.initNullStr(json.getString("ResultText"));
                        if (resultCode == 0) {
                            String content = json.getString("Content");
                            JSONObject obj = new JSONObject(content);
                            db.deleteById(CardInfo.class, " cardCode=\'" + obj.getString("CardCode") + "\'");
                            CardInfo mInfo = new CardInfo();
                            mInfo.setListID(Utils.initNullStr(obj.getString("ListID")));
                            mInfo.setUserID(Utils.initNullStr(obj.getString("UserID")));
                            mInfo.setUserCityID(Utils.initNullStr(obj.getString("UserCityID")));
                            mInfo.setCityCode(Utils.initNullStr(obj.getString("CityCode")));
                            mInfo.setCardCode(Utils.initNullStr(obj.getString("CardCode")));
                            mInfo.setCardName(Utils.initNullStr(obj.getString("CardName")));
                            mInfo.setCardLogo(Utils.initNullStr(obj.getString("CardLogo")));
                            mInfo.setIsDelete(Utils.initNullStr(obj.getString("IsDelete")));
                            db.save(mInfo);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    @Override
    public void onCardDelete(String cardType) {
        mOwnedCardAdapter.deleteCard(cardType);
        mLeftCardAdapter.addCard(cardType);
        JSONObject jsonObject = new JSONObject();
        String cardList = db.findAllByWhere(CardInfo.class, " CardCode=\'" + cardType + "\'").get(0).getListID();
        try {
            jsonObject.put("ListID", cardList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("token", Constants.getToken());
        map.put("cardType", "");
        map.put("taskId", "");
        map.put("DataTypeCode", "DeleteUserCards");
        map.put("content", jsonObject.toString());
        WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_CARDHOLDER, map, new WebServiceUtils.WebServiceCallBack() {
            @Override
            public void callBack(String result) {
                if (result != null) {
                    Log.e("删除卡片", result);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        //卡选择状态保存到本地
        StringBuilder sb = new StringBuilder();
        for (String card : mOwnedCardList) {
            sb.append(card);
            sb.append(",");
        }
        //卡选择状态传递到主界面
        //MainActivity.mSp.edit().putString("LOCAL_CARD", sb.toString()).commit();
        Constants.setCardCode(sb.toString());
        //Intent intent = new Intent();
        // intent.putExtra("cardCode", sb.toString());
        //setResult(RESULT_OK, intent);
        Intent intent0 = new Intent(AddManagerActivity.this, MainActivity.class);
        startActivity(intent0);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            Intent intent0 = new Intent(AddManagerActivity.this, MainActivity.class);
            startActivity(intent0);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}