package com.kingja.cardpackage.base;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.kingja.cardpackage.db.DatebaseManager;
import com.kingja.cardpackage.db.DownloadDbManager;
import com.kingja.cardpackage.net.PoolManager;
import com.kingja.cardpackage.util.CrashHandler;

import net.tsz.afinal.FinalDb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.BuildConfig;
import org.xutils.x;

import cn.jpush.android.api.JPushInterface;



public class App extends Application {
    public static Context context;

    public static FinalDb db;
    private static Context mAppContext;
    private static SharedPreferences mSharedPreferences;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        CrashHandler.getInstance().init(this);
        db = FinalDb.create(context, "WisdomE.db", false);

        JPushInterface.setDebugMode(true);// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);


        mAppContext = getApplicationContext();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mAppContext);
        initXutils3();
        copyPoliceDb();


    }

    private void copyPoliceDb() {
        PoolManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                DatebaseManager.getInstance(getApplicationContext()).copyDataBase(DownloadDbManager.DB_NAME);
            }
        });
    }

    private void initXutils3() {
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
    }

    public static Context getContext() {
        return mAppContext;
    }

    public static SharedPreferences getSP() {
        return mSharedPreferences;
    }

}
