package com.tdr.wisdome.util;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;

public class CloseActivityUtil {
    public static ArrayList<Activity> activitys = new ArrayList<Activity>();

    public static void AddActivity(Activity act) {
        activitys.add(act);
        Log.e("act",activitys.size()+"");
    }

    public static void ExitApp() {
        for (Activity activity:activitys) {
            activity.finish();
        }
        activitys= new ArrayList<Activity>();
    }
    public static void activityFinish(Activity act){
        act.finish();
        activitys.remove(act);
    }
}
