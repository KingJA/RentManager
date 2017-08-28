package com.kingja.cardpackage.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kingja.cardpackage.util.ToastUtil;
import com.kingja.ui.R;


/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/19 17:15
 * 修改备注：
 */
public class DialogInput extends Dialog {
    private static final String TAG = "DialogInput";
    private Activity activity;
    private String operation;
    private Context context;
    private EditText mEtStationInput;
    private OnInputListener onInputListener;
    private String stationNo;
    private TextView mTvConfirm;
    private TextView mTvCancle;


    public DialogInput(Context context,String operation) {

        super(context, R.style.CustomDialog);
        this.context = context;
        this.activity = (Activity) context;
        this.operation = operation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_input);
        initView();
        initEvent();
    }


    public void initView() {
        mEtStationInput = (EditText) findViewById(R.id.et_station_input);
        mTvConfirm = (TextView) findViewById(R.id.tv_confirm);
        mTvCancle = (TextView) findViewById(R.id.tv_cancle);
        mEtStationInput.setHint("请输入中介身份证后6位进行"+operation);
    }


    public void initEvent() {
        mTvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onInputListener != null) {
                    stationNo = mEtStationInput.getText().toString().trim();
                    if (checkIdCard(stationNo)){
                        dismiss();
                        onInputListener.onInput(stationNo.toUpperCase());
                    }
                }
            }
        });
        mTvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public interface OnInputListener {
        void onInput(String idCard);
    }

    public void setOnInputListener(OnInputListener onInputListener) {
        this.onInputListener = onInputListener;
    }

    private static boolean checkIdCard(String s) {
        if (s.length()!=6) {
            ToastUtil.showToast("请输入中介身份证后6位");
            return false;
        }
        return true;
    }
}
