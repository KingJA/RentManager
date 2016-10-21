package com.tdr.wisdome.actvitiy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdr.wisdome.R;
import com.tdr.wisdome.util.Utils;
import com.tdr.wisdome.view.ClearEditTextView;

/**
 * 修改人员配置信息
 * Created by Linus_Xie on 2016/8/23.
 */
public class ModifyActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "ModifyActivity";

    private String value = "";
    private String activityName = "";
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        mContext = this;
        Intent intent = getIntent();
        value = intent.getStringExtra("value");
        activityName = intent.getStringExtra("activityName");

        initView();
    }

    private ImageView image_back;
    private TextView text_title;
    private TextView text_deal;

    private ClearEditTextView clear_modify;

    private void initView() {
        image_back = (ImageView) findViewById(R.id.image_back);
        image_back.setOnClickListener(this);
        text_title = (TextView) findViewById(R.id.text_title);
        if (activityName.equals("olderName")) {
            text_title.setText("关爱人姓名");
        } else if (activityName.equals("olderPhone")) {
            text_title.setText("关爱人手机号码");
        } else if (activityName.equals("olderIdentity")) {
            text_title.setText("关爱人身份证");
        } else if (activityName.equals("olderAddress")) {
            text_title.setText("关爱人联系地址");
        } else if (activityName.equals("olderRemarks")) {
            text_title.setText("备注");
        }
        text_deal = (TextView) findViewById(R.id.text_deal);
        text_deal.setOnClickListener(this);
        text_deal.setText("完成");
        text_deal.setVisibility(View.VISIBLE);

        clear_modify = (ClearEditTextView) findViewById(R.id.clear_modify);
        clear_modify.setText(value);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_back:
                finish();
                break;

            case R.id.text_deal:
                String result = clear_modify.getText().toString().trim();
                if (result.equals("")) {
                    Utils.myToast(this, "输入框不可为空");
                    break;
                } else if (activityName.equals("olderIdentity")) {
                    if (!Utils.isIDCard18(result)) {
                        Utils.myToast(this, "输入的身份证有误");
                        break;
                    } else {
                        Intent intent = new Intent();
                        intent.setClass(ModifyActivity.this, OlderShareActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("activity", activityName);
                        bundle.putString("result", result);
                        intent.putExtras(bundle);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                } else {
                    Intent intent = new Intent();
                    intent.setClass(ModifyActivity.this, OlderShareActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("activity", activityName);
                    bundle.putString("result", result);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }

                break;
        }
    }
}
