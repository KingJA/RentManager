package com.tdr.wisdome.model;

/**
 * 卡包分组适配器
 * Created by Linus_Xie on 2016/8/22.
 */
public class PackageItem {

    private String mTitle;
    private String mContent;
    //private Bitmap mIcon;
    private int mIcon;

    public PackageItem(String pTitle, String pContent, int mIcon) {
        mTitle = pTitle;
        mContent = pContent;
        this.mIcon = mIcon;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getContent() {
        return mContent;
    }

    public int getmIcon() {
        return mIcon;
    }
}
