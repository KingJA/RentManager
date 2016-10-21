package com.tdr.wisdome.actvitiy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tdr.wisdome.R;

/**
 * 病情描述
 * Created by Linus_Xie on 2016/8/23.
 */
public class HealthActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "HealthActivity";

    private String value = "";
    private String[] disease;

    private boolean hypertensionChecked = false;
    private boolean diabetesChecked = false;
    private boolean heartdiseaseChecked = false;

    private String hypertension = "";
    private String diabetes = "";
    private String heartdisease = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        Intent intent = getIntent();
        value = intent.getStringExtra("value");
        disease = value.split(",");

        initView();
    }

    private ImageView image_back;
    private TextView text_title;
    private TextView text_deal;

    private RelativeLayout relative_hypertension, relative_diabetes, relative_heartdisease;
    private TextView text_hypertension, text_diabetes, text_heartdisease;

    private void initView() {
        image_back = (ImageView) findViewById(R.id.image_back);
        image_back.setOnClickListener(this);
        text_title = (TextView) findViewById(R.id.text_title);
        text_title.setText("病情描述");
        text_deal = (TextView) findViewById(R.id.text_deal);
        text_deal.setOnClickListener(this);
        text_deal.setText("完成");
        text_deal.setVisibility(View.VISIBLE);

        relative_hypertension = (RelativeLayout) findViewById(R.id.relative_hypertension);
        relative_hypertension.setOnClickListener(this);
        text_hypertension = (TextView) findViewById(R.id.text_hypertension);

        relative_diabetes = (RelativeLayout) findViewById(R.id.relative_diabetes);
        relative_diabetes.setOnClickListener(this);
        text_diabetes = (TextView) findViewById(R.id.text_diabetes);

        relative_heartdisease = (RelativeLayout) findViewById(R.id.relative_heartdisease);
        relative_heartdisease.setOnClickListener(this);
        text_heartdisease = (TextView) findViewById(R.id.text_heartdisease);

        if (value.contains("高血压")) {
            relative_hypertension.setBackgroundResource(R.drawable.disease_on);
            text_hypertension.setTextColor(getResources().getColor(R.color.colorStatus));
            hypertensionChecked = true;
            hypertension = "高血压";
        }
        if (value.contains("糖尿病")) {
            relative_diabetes.setBackgroundResource(R.drawable.disease_on);
            text_diabetes.setTextColor(getResources().getColor(R.color.colorStatus));
            diabetesChecked = true;
            diabetes = "糖尿病";
        }
        if (value.contains("心脏病")) {
            relative_heartdisease.setBackgroundResource(R.drawable.disease_on);
            text_heartdisease.setTextColor(getResources().getColor(R.color.colorStatus));
            heartdiseaseChecked = true;
            heartdisease = "心脏病";
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_back:
                finish();
                break;

            case R.id.text_deal:
                Intent intent = new Intent();
                intent.setClass(HealthActivity.this, OlderShareActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("activity", "healthActivity");
                bundle.putString("result", hypertension + "," + diabetes + "," + heartdisease);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
                break;

            case R.id.relative_hypertension:
                if (hypertensionChecked) {
                    relative_hypertension.setBackgroundResource(R.drawable.disease_off);
                    text_hypertension.setTextColor(getResources().getColor(R.color.colorHint));
                    hypertensionChecked = false;
                    hypertension = "";
                } else {
                    relative_hypertension.setBackgroundResource(R.drawable.disease_on);
                    text_hypertension.setTextColor(getResources().getColor(R.color.colorStatus));
                    hypertensionChecked = true;
                    hypertension = "高血压";
                }
                break;

            case R.id.relative_diabetes:
                if (diabetesChecked) {
                    relative_diabetes.setBackgroundResource(R.drawable.disease_off);
                    text_diabetes.setTextColor(getResources().getColor(R.color.colorHint));
                    diabetesChecked = false;
                    diabetes = "";
                } else {
                    relative_diabetes.setBackgroundResource(R.drawable.disease_on);
                    text_diabetes.setTextColor(getResources().getColor(R.color.colorStatus));
                    diabetesChecked = true;
                    diabetes = "糖尿病";
                }
                break;

            case R.id.relative_heartdisease:
                if (heartdiseaseChecked) {
                    relative_heartdisease.setBackgroundResource(R.drawable.disease_off);
                    text_heartdisease.setTextColor(getResources().getColor(R.color.colorHint));
                    heartdiseaseChecked = false;
                    heartdisease = "";
                } else {
                    relative_heartdisease.setBackgroundResource(R.drawable.disease_on);
                    text_heartdisease.setTextColor(getResources().getColor(R.color.colorStatus));
                    heartdiseaseChecked = true;
                    heartdisease = "心脏病";
                }
                break;

        }
    }


}
