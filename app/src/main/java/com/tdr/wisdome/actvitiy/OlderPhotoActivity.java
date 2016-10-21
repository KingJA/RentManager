package com.tdr.wisdome.actvitiy;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdr.wisdome.R;

/**
 * 老人照片修改
 * Created by Linus_Xie on 2016/8/23.
 */
public class OlderPhotoActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "OlderPhotoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olderphoto);
        
        initView();
    }

    private ImageView image_back;
    private TextView text_title;

    private void initView() {
        image_back = (ImageView) findViewById(R.id.image_back);
        image_back.setOnClickListener(this);
        text_title = (TextView) findViewById(R.id.text_title);
        text_title.setText("修改照片");

    }

    @Override
    public void onClick(View v) {

    }
}
