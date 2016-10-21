package com.tdr.wisdome.actvitiy;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.tdr.wisdome.R;

/**
 * Created by Linus_Xie on 2016/8/26.
 */
public class QrCodeActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "QrCodeActivity";

    private Context mContext;
    private String code = "";
    private Bitmap bitmapQrCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        mContext = this;
        code = getIntent().getStringExtra("code");
        initView();
        initData();
    }

    private ImageView image_back;
    private TextView text_title;
    private TextView text_deal;

    private ImageView image_qrCode;

    private void initView() {
        image_back = (ImageView) findViewById(R.id.image_back);
        image_back.setOnClickListener(this);
        text_title = (TextView) findViewById(R.id.text_title);
        text_title.setText("关爱二维码");
        text_deal = (TextView) findViewById(R.id.text_deal);
        text_deal.setText("截屏");
        text_deal.setOnClickListener(this);
        text_deal.setVisibility(View.GONE);

        image_qrCode = (ImageView) findViewById(R.id.image_qrCode);

    }


    private void initData() {
        try {
            bitmapQrCode = com.tdr.wisdome.zxing.Utils.Create2DCode(code);
            image_qrCode.setImageBitmap(bitmapQrCode);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_back:
                finish();
                break;

            case R.id.text_deal:

                break;
        }
    }
}
