package com.tdr.wisdome.actvitiy;

import com.tdr.wisdome.fragment.LoginFragment;
import com.tdr.wisdome.fragment.RegisterFragment;

import java.util.List;

public class LoginActivity extends IndicatorFragmentActivity {

    public static final int FRAGMENT_ONE = 0;
    public static final int FRAGMENT_TWO = 1;

    @Override
    protected int supplyTabs(List<TabInfo> tabs) {
        tabs.add(new TabInfo(FRAGMENT_ONE, "登录", LoginFragment.class));
        tabs.add(new TabInfo(FRAGMENT_TWO, "注册", RegisterFragment.class));
        return FRAGMENT_ONE;
    }
}
