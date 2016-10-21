package com.tdr.wisdome.actvitiy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.tdr.wisdome.R;

/**
 * 车辆预登记二维码
 * Created by Linus_Xie on 2016/8/29.
 */
public class CarQrActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "CarQrActivity";

    private Context mContext;
    private String prerateID = "";
    private Bitmap bitmapQrCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carcode);
        mContext = this;
        prerateID = getIntent().getStringExtra("prerateID");
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
        text_title.setText("预登记二维码");
        text_deal = (TextView) findViewById(R.id.text_deal);
        text_deal.setText("查看");
        text_deal.setOnClickListener(this);
        text_deal.setVisibility(View.VISIBLE);

        image_qrCode = (ImageView) findViewById(R.id.image_qrCode);
    }

    private void initData() {
        try {
            bitmapQrCode = com.tdr.wisdome.zxing.Utils.Create2DCode(prerateID);
            image_qrCode.setImageBitmap(bitmapQrCode);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_back:
                finish();
                break;

            case R.id.text_deal:
                Intent intent = new Intent(mContext, ModifyPreActivity.class);
                intent.putExtra("prerateID", prerateID);
                mContext.startActivity(intent);
                break;
        }
    }
}
