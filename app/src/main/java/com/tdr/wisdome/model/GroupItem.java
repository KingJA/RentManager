package com.tdr.wisdome.model;

/**
 * Created by Linus_Xie on 2016/8/22.
 */
public class GroupItem {
    private String mTitle;
    private String mContent;
    private String smartcareId;

    public GroupItem(String pTitle, String pContent, String smartcareId) {
        mTitle = pTitle;
        mContent = pContent;
        this.smartcareId = smartcareId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getContent() {
        return mContent;
    }

    public String getSmartcareId()

    {
        return smartcareId;
    }
}
