package com.tdr.wisdome.permission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * Created by Linus_Xie on 2016/9/7.
 */
public class PermissionsChecker {
    private final Activity context;
    public static final int MY_PERMISSIONS_REQUEST=200;
    static PermissionsChecker checker=null;
    private PermissionsChecker(Activity context) {
        this.context=context;
    }

    public static PermissionsChecker getInstance(Activity context){
        return new PermissionsChecker(context);
    }

    // 判断权限集合
    public boolean lacksPermissions(String... permissions) {
        for (int i=0;i<permissions.length;i++){
            if (lacksPermission(permissions[i])) {//无权限
                return true;
            }
        }
        return false;
    }
    // 判断是否缺少权限  无权限返回true
    private boolean lacksPermission(String permission) {
        return ContextCompat.checkSelfPermission(context, permission) ==
                PackageManager.PERMISSION_DENIED;
    }
}
