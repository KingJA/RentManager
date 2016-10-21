package com.tdr.wisdome.model;

/**
 * Created by Linus_Xie on 2016/8/4.
 */
public class Item {
    int mIcon;
    int mSpans;
    String mTitle;
    String mDescription;
    String mActivity;

    public Item(int mIcon, int mSpans, String mTitle, String mActivity) {
        this.mIcon = mIcon;
        this.mSpans = mSpans;
        this.mTitle = mTitle;
        this.mActivity = mActivity;
    }

    public Item(int icon, int spans, String title, String description,
                String activity) {
        mIcon = icon;
        mSpans = spans;
        mTitle = title;
        mDescription = description;
        mActivity = activity;
    }

    public int getSpans() {
        return mSpans;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getIcon() {
        return mIcon;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getmActivity() {
        return mActivity;
    }

    public void setmActivity(String mActivity) {
        this.mActivity = mActivity;
    }

}

