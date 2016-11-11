package com.tdr.wisdome.actvitiy;

import android.os.Bundle;

import com.tdr.wisdome.fragment.LoginFragment;
import com.tdr.wisdome.fragment.RegisterFragment;

import java.util.List;

import lib.king.kupdate.UpdateManager;
import lib.king.kupdate.strategy.WebServiceStrategy;

public class LoginActivity extends IndicatorFragmentActivity {

    public static final int FRAGMENT_ONE = 0;
    public static final int FRAGMENT_TWO = 1;

    @Override
    protected int supplyTabs(List<TabInfo> tabs) {
        tabs.add(new TabInfo(FRAGMENT_ONE, "登录", LoginFragment.class));
        tabs.add(new TabInfo(FRAGMENT_TWO, "注册", RegisterFragment.class));
        return FRAGMENT_ONE;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkUpdate();
    }

    private void checkUpdate() {
        UpdateManager.Builder builder = new UpdateManager.Builder(this);
        builder.setUpdateCancleable(false)
                .setShowDownloadDialog(true)
                .setLoadStrategy(new WebServiceStrategy())
                .setUpdateContent("发现新版本，请马上更新" )
                .build()
                .checkUpdate();
    }
}
