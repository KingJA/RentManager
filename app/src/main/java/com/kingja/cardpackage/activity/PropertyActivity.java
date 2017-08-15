package com.kingja.cardpackage.activity;

import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.kingja.cardpackage.util.DataManager;
import com.tdr.wisdome.R;

/**
 * Description:TODO
 * Create Time:2017/8/1 13:45
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class PropertyActivity extends BackTitleActivity {
    private String url="http://hr.kaoqincloud.com/logon_content.html?mobileNumber=";

    private ProgressBar mProgress;
    private WebView mWebview;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initContentView() {
        mProgress = (ProgressBar) findViewById(R.id.progress);
        mWebview = (WebView) findViewById(R.id.webview);
        mWebview.loadUrl(url+ DataManager.getPhone());
    }

    @Override
    protected int getBackContentView() {
        return R.layout.single_wb;
    }

    @Override
    protected void initNet() {

    }

    @Override
    protected void initData() {
        WebSettings settings = mWebview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(false);
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e(TAG, "shouldOverrideUrlLoading: "+url );
                view.loadUrl(url);
                return true;
            }
        });
        mWebview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                mProgress.setProgress(newProgress);
                mProgress.setVisibility(newProgress == 100 ? View.GONE : View.VISIBLE);
            }
        });


    }

    @Override
    protected void setData() {
        setTitle("物业申报");
    }
}
