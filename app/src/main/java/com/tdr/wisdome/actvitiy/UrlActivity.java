package com.tdr.wisdome.actvitiy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdr.wisdome.R;

/**
 * 网页
 * Created by Linus_Xie on 2016/9/2.
 */
public class UrlActivity extends Activity {

    private Context mContext;

    private String type = "";

    private String title = "";
    private String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        title = bundle.getString("title");
        url = intent.getStringExtra("url");

        initView();

    }

    private ImageView image_back;
    private TextView text_title;

    private WebView web_view;

    private void initView() {
        image_back = (ImageView) findViewById(R.id.image_back);
        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        text_title = (TextView) findViewById(R.id.text_title);
        text_title.setText(title);

        web_view = (WebView) findViewById(R.id.web_view);

        WebSettings webSettings = web_view.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        //加载需要显示的网页
        //web_view.loadUrl("http://www.baidu.com");
        web_view.loadUrl(url);
        //设置Web视图
        web_view.setWebViewClient(new webViewClient());
    }

    @Override
    //设置回退
    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && web_view.canGoBack()) {
            web_view.goBack(); //goBack()表示返回WebView的上一页面
            return true;
        }
        finish();
        return false;
    }

    //Web视图
    private class webViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
