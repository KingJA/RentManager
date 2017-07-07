package com.kingja.cardpackage.ui;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingja.cardpackage.entiy.ApplyPerson;
import com.kingja.cardpackage.util.DataManager;
import com.kingja.cardpackage.util.StringUtil;
import com.kingja.cardpackage.util.ToastUtil;
import com.tdr.wisdome.R;

import java.util.regex.Pattern;

/**
 * Description:TODO
 * Create Time:2017/5/10 10:34
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ApplyView extends FrameLayout implements View.OnClickListener {
    private EditText mEtApplyName;
    private ImageView mIvApplyCamera;
    private EditText mEtApplyCardId;
    private EditText mEtApplyPhone;
    private EditText mEtApplyHeight;
    private TextView mTvApplyIndex;
    private TextView mTvApplyCancle;
    private int index;
    private OnOperatorListener onOperatorListener;


    public ApplyView(Context context, int index) {
        super(context);
        this.index = index;
        addView(context);
    }


    private void addView(Context context) {
        View rootView = View.inflate(context, R.layout.view_unregistered_apply, null);
        mTvApplyCancle = (TextView) rootView.findViewById(R.id.tv_apply_cancle);
        mTvApplyIndex = (TextView) rootView.findViewById(R.id.tv_apply_index);
        mIvApplyCamera = (ImageView) rootView.findViewById(R.id.iv_apply_camera);
        mEtApplyName = (EditText) rootView.findViewById(R.id.et_apply_name);
        mEtApplyCardId = (EditText) rootView.findViewById(R.id.et_apply_cardId);
        mEtApplyPhone = (EditText) rootView.findViewById(R.id.et_apply_phone);
        mEtApplyHeight = (EditText) rootView.findViewById(R.id.et_apply_height);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(rootView, layoutParams);
        mTvApplyIndex.setText(index + 1 + "");
        mTvApplyCancle.setOnClickListener(this);
        mIvApplyCamera.setOnClickListener(this);
    }

    public void setOnOperatorListener(OnOperatorListener onOperatorListener) {
        this.onOperatorListener = onOperatorListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_apply_cancle:
                if (onOperatorListener != null) {
                    onOperatorListener.onCancle(index);
                }
                break;
            case R.id.iv_apply_camera:
                if (onOperatorListener != null) {
                    onOperatorListener.onOpenOCR(index);
                }
                break;

        }
    }

    public interface OnOperatorListener {
        void onCancle(int index);

        void onOpenOCR(int index);
    }

    public void setIndex(int index) {
        this.index = index;

        mTvApplyIndex.setText(index + 1 + "");
    }

    public ApplyPerson getApplyPerson() {
        String applyName = mEtApplyName.getText().toString().trim();
        String cardId = mEtApplyCardId.getText().toString().trim().toUpperCase();
        String phone = mEtApplyPhone.getText().toString().trim();
        String height = mEtApplyHeight.getText().toString().trim();
        if (checkName(applyName, index + 1) && checkIdCard(cardId, index + 1) && checkPhoneFormat(phone, index + 1)
                && checkHeight(height, 80, 210, index + 1)) {
            ApplyPerson applyPerson = new ApplyPerson();
            applyPerson.setLISTID(StringUtil.getUUID());
            applyPerson.setREPORTERROLE(2);
            applyPerson.setOPERATOR(DataManager.getUserId());
            applyPerson.setTERMINAL(2);
            applyPerson.setOPERATORPHONE(DataManager.getUserPhone());

            applyPerson.setNAME(applyName);
            applyPerson.setIDENTITYCARD(cardId);
            applyPerson.setPHONE(phone);
            applyPerson.setHEIGHT(Integer.valueOf(height));
            return applyPerson;
        }
        return null;
    }

    private boolean checkName(String s, int index) {
        if (TextUtils.isEmpty(s)) {
            ToastUtil.showToast("请输入申报人员" + index + "的姓名");
            return false;
        }
        return true;
    }

    private boolean checkPhoneFormat(String phone, int index) {
        // 判断非空
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.showToast("请输入申报人员" + index + "的身高");
            return false;
        }

        // 判断手机号格式
        if (!Pattern.matches(
                "^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\\d{8}$", phone)) {
            ToastUtil.showToast("申报人员" + index + "的手机号码格式错误");
            return false;
        }
        return true;
    }

    private static boolean checkHeight(String height, int min, int max, int index) {
        if (TextUtils.isEmpty(height)) {
            ToastUtil.showToast("请输入申报人员" + index + "的身高");
            return false;
        }
        int heightInt = Integer.valueOf(height);

        if (heightInt < min || heightInt > max) {
            ToastUtil.showToast("申报人员" + index + "的身高需在" + min + "到" + max + "cm之间");
            return false;
        }

        return true;
    }

    private static boolean checkIdCard(final String value, int index) {
        if (TextUtils.isEmpty(value)) {
            ToastUtil.showToast("请输入申报人员" + index + "的身份证号");
            return false;
        }
        if (value.length() != 18) {
            ToastUtil.showToast("申报人员" + index + "的身份证号格式错误");
            return false;
        }

        if (!value.matches("[\\d]+[X]?")) {
            ToastUtil.showToast("申报人员" + index + "的身份证号格式错误");
            return false;
        }
        String code = "10X98765432";
        int weight[] = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};
        int nSum = 0;
        for (int i = 0; i < 17; ++i) {
            nSum += (int) (value.charAt(i) - '0') * weight[i];
        }
        int nCheckNum = nSum % 11;
        char chrValue = value.charAt(17);
        char chrCode = code.charAt(nCheckNum);
        if (chrValue == chrCode) {
            return true;
        }

        if (nCheckNum == 2 && (chrValue + ('a' - 'A') == chrCode)) {
            return true;
        }
        ToastUtil.showToast("申报人员" + index + "的身份证号格式错误");
        return false;
    }

    public void setName(String name) {
        mEtApplyName.setText(name);
    }

    public void setCardId(String cardId) {
        mEtApplyCardId.setText(cardId);
    }
}
