package com.tdr.wisdome.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.tdr.wisdome.R;

import java.util.List;

/**
 * Created by Linus_Xie on 2016/8/30.
 */
public abstract class BaseCardAdapter extends BaseAdapter {
    protected Context context;
    protected List<String> list;

    public BaseCardAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return onGetView(i, view, viewGroup);
    }


    protected abstract View onGetView(int i, View convertView, ViewGroup viewGroup);

    protected String getCardName(String cardType) {
        String cardName = "";
        switch (cardType) {
            case "1001":
                cardName = "我的住房";
                break;
            case "1002":
                cardName = "我家出租房";
                break;
            case "1003":
                cardName = "我的车";
                break;
            case "1004":
                cardName = "我的店";
                break;
            case "1005":
                cardName = "亲情关爱";
                break;
            case "1006":
                cardName = "服务商城";
                break;
            case "1007":
                cardName = "出租房代管";
                break;
            //default:
            //cardName = "未知卡";
            //  break;
        }
        return cardName;
    }

    protected int getCardImage(String cardType) {
        int cardImage = 0;
        switch (cardType) {
            case "1001":
                cardImage = R.mipmap.image_house;
                break;
            case "1002":
                cardImage = R.mipmap.image_rental;
                break;
            case "1003":
                cardImage = R.mipmap.image_car;
                break;
            case "1004":
                cardImage = R.mipmap.image_shop;
                break;
            case "1005":
                cardImage = R.mipmap.image_care;
                break;
            case "1006":
                cardImage = R.mipmap.image_store;
                break;
            case "1007":
                cardImage = R.mipmap.image_rental;
                break;
            //default:
            //cardImage = R.mipmap.image_house;
            //  break;
        }
        return cardImage;
    }

    public void addCard(String cardType) {
        list.add(cardType);
        notifyDataSetChanged();
    }

    public void deleteCard(String cardType) {
        list.remove(cardType);
        notifyDataSetChanged();
    }
}
