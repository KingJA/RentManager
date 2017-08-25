package com.kingja.cardpackage.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.flyco.dialog.widget.NormalListDialog;
import com.kingja.cardpackage.activity.KCamera;
import com.kingja.cardpackage.activity.PersonApplyActivity;
import com.kingja.cardpackage.adapter.RoomListAdapter;
import com.kingja.cardpackage.base.BaseFragment;
import com.kingja.cardpackage.entiy.ChuZuWu_LKSelfReportingIn;
import com.kingja.cardpackage.entiy.ChuZuWu_LKSelfReportingInParam;
import com.kingja.cardpackage.entiy.ErrorResult;
import com.kingja.cardpackage.entiy.RentBean;
import com.kingja.cardpackage.net.ThreadPoolTask;
import com.kingja.cardpackage.net.WebServiceCallBack;
import com.kingja.cardpackage.util.CheckUtil;
import com.kingja.cardpackage.util.Constants;
import com.kingja.cardpackage.util.DataManager;
import com.kingja.cardpackage.util.DialogUtil;
import com.kingja.cardpackage.util.OCRUtil;
import com.kingja.cardpackage.util.StringUtil;
import com.kingja.cardpackage.util.ToastUtil;
import com.pizidea.imagepicker.ImageUtil;
import com.tdr.wisdome.R;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * Description：设备申报
 * Create Time：2016/8/19 14:20
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ApplyFragment extends BaseFragment implements View.OnClickListener, PersonApplyActivity
        .OnSaveClickListener, OnOperItemClickL {
    private LinearLayout mLlSelectRoom;
    private TextView mTvApplyRoomNum;
    private EditText mTvApplyName;
    private EditText mEtApplyCardId;
    private ImageView mIvApplyCamera;
    private EditText mEtApplyPhone;
    private RentBean entiy;
    private NormalListDialog mNormalListDialog;
    private PersonApplyActivity mPersonApplyActivity;
    private String mRoomId;
    public static final int REQ_OCR = 100;
    private String name;
    private String cardId;
    private String phone;
    private List<RentBean.RoomListBean> mRoomList;
    private LinearLayout mLlOcrCamera;
    private Intent mICardData;
    private String imgBase64 = "";
    private ImageView mIvIdcard;
    private EditText mEtApplyHeigh;
    private String height;
    private String cardType;
    private int reporterRole;
    private LinearLayout mLlApplyAvatar;
    private ImageView mIvApplyAvatar;
    private static final int REQUEST_CAMARA = 1001;
    private String base64Avatar;
    private ImageView mIvBigAvatar;
    private String TAG = "ApplyFragment";
    private String agencyId;

    public static ApplyFragment newInstance(RentBean bean, String cardType, int reporterRole, String agencyId) {
        ApplyFragment mApplyFragment = new ApplyFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("ENTIY", bean);
        bundle.putString("cardType", cardType);
        bundle.putInt("reporterRole", reporterRole);
        bundle.putString("agencyId", agencyId);
        mApplyFragment.setArguments(bundle);
        return mApplyFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_apply;
    }

    @Override
    public void initFragmentView(View view, Bundle savedInstanceState) {
        mPersonApplyActivity = (PersonApplyActivity) getActivity();
        mLlSelectRoom = (LinearLayout) view.findViewById(R.id.ll_selectRoom);
        mIvApplyCamera = (ImageView) view.findViewById(R.id.iv_apply_camera);
        mTvApplyRoomNum = (TextView) view.findViewById(R.id.tv_apply_roomNum);

        mTvApplyName = (EditText) view.findViewById(R.id.et_apply_name);
        mEtApplyCardId = (EditText) view.findViewById(R.id.et_apply_cardId);
        mEtApplyPhone = (EditText) view.findViewById(R.id.et_apply_phone);
        mEtApplyHeigh = (EditText) view.findViewById(R.id.et_apply_height);
        mLlOcrCamera = (LinearLayout) view.findViewById(R.id.ll_ocr_camera);
        mIvIdcard = (ImageView) view.findViewById(R.id.iv_idcard);
        mLlApplyAvatar = (LinearLayout) view.findViewById(R.id.ll_apply_avatar);
        mIvApplyAvatar = (ImageView) view.findViewById(R.id.iv_apply_avatar);
        mIvBigAvatar = (ImageView) view.findViewById(R.id.iv_big_avatar);
    }


    @Override
    public void initFragmentVariables() {
        entiy = (RentBean) getArguments().getSerializable("ENTIY");
        cardType = getArguments().getString("cardType");
        agencyId = getArguments().getString("agencyId");
        reporterRole = getArguments().getInt("reporterRole");
        mRoomList = entiy.getRoomList();
    }

    @Override
    public void initFragmentNet() {

    }

    @Override
    public void initFragmentData() {
        mLlOcrCamera.setOnClickListener(this);
        mLlSelectRoom.setOnClickListener(this);
        mLlApplyAvatar.setOnClickListener(this);
        mIvApplyAvatar.setOnClickListener(this);
        mIvBigAvatar.setOnClickListener(this);
        mPersonApplyActivity.setOnSaveClickListener(this);
    }

    @Override
    public void setFragmentData() {

    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMARA);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_selectRoom:
                if (mRoomList.size() > 0) {
                    mNormalListDialog = DialogUtil.getListDialog(getActivity(), "房间号", new RoomListAdapter
                            (getActivity(), mRoomList));
                    mNormalListDialog.setOnOperItemClickL(ApplyFragment.this);
                    mNormalListDialog.show();
                } else {
                    ToastUtil.showToast("该出租屋暂时没有房间");
                }
                break;
            case R.id.ll_ocr_camera:
                KCamera.GoCamera(getActivity());
                break;
            case R.id.ll_apply_avatar:
                takePhoto();
                break;
            case R.id.iv_apply_avatar:
                mIvBigAvatar.setVisibility(View.VISIBLE);
                mIvBigAvatar.setImageBitmap(ImageUtil.base64ToBitmap(base64Avatar));
                break;
            case R.id.iv_big_avatar:
                mIvBigAvatar.setVisibility(View.INVISIBLE);
                break;

        }
    }

    @Override
    public void onSaveClick() {
        name = mTvApplyName.getText().toString().trim();
        cardId = mEtApplyCardId.getText().toString().trim().toUpperCase();
        phone = mEtApplyPhone.getText().toString().trim();
        height = mEtApplyHeigh.getText().toString().trim();
        if (CheckUtil.checkEmpty(mTvApplyRoomNum.getText().toString(), "请选择房间号")
                && CheckUtil.checkEmpty(name, "请通过相机获取姓名")
                && CheckUtil.checkIdCard(cardId, "身份证格式错误")
                && CheckUtil.checkPhoneFormat(phone)
                && CheckUtil.checkHeight(height, 50, 210)) {
            mIvBigAvatar.setVisibility(View.GONE);
            onApply();
        }

    }

    private int photoCount;

    /**
     * 自主申报
     */
    private void onApply() {
        setProgressDialog(true);
        ChuZuWu_LKSelfReportingInParam bean = new ChuZuWu_LKSelfReportingInParam();
        bean.setTaskID("1");
        bean.setHOUSEID(entiy.getHOUSEID());
        bean.setROOMID(mRoomId);
        bean.setLISTID(StringUtil.getUUID());
        bean.setNAME(name);
        bean.setIDENTITYCARD(cardId);
        bean.setPHONE(phone);
        bean.setHEIGHT(Integer.valueOf(height));
        bean.setREPORTERROLE(reporterRole);
        bean.setOPERATOR(DataManager.getUserId());
        bean.setOPERATUNIT(agencyId);
        bean.setSTANDARDADDRCODE(entiy.getSTANDARDADDRCODE());
        bean.setTERMINAL(2);
        bean.setXQCODE(entiy.getXQCODE());
        bean.setPCSCODE(entiy.getPCSCODE());
        bean.setJWHCODE(entiy.getJWHCODE());
        bean.setOPERATORPHONE(DataManager.getPhone());
        bean.setPHOTOCOUNT(photoCount);
        List<ChuZuWu_LKSelfReportingInParam.PHOTOLISTBean> photolist = new ArrayList<>();
        if (!TextUtils.isEmpty(imgBase64)) {
            bean.setPHOTOCOUNT(++photoCount);
            ChuZuWu_LKSelfReportingInParam.PHOTOLISTBean idcard = new ChuZuWu_LKSelfReportingInParam.PHOTOLISTBean();
            idcard.setLISTID(StringUtil.getUUID());
            idcard.setTAG("身份证");
            idcard.setIMAGE(imgBase64);
            photolist.add(idcard);
        }
        if (!TextUtils.isEmpty(base64Avatar)) {
            bean.setPHOTOCOUNT(++photoCount);
            ChuZuWu_LKSelfReportingInParam.PHOTOLISTBean avatar = new ChuZuWu_LKSelfReportingInParam.PHOTOLISTBean();
            avatar.setLISTID(StringUtil.getUUID());
            avatar.setTAG("人像");
            avatar.setIMAGE(base64Avatar);
            photolist.add(avatar);
        }
        bean.setPHOTOLIST(photolist);
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), cardType, Constants
                        .ChuZuWu_LKSelfReportingIn, bean)
                .setBeanType(ChuZuWu_LKSelfReportingIn.class)
                .setCallBack(new WebServiceCallBack<ChuZuWu_LKSelfReportingIn>() {
                    @Override
                    public void onSuccess(ChuZuWu_LKSelfReportingIn bean) {
                        setProgressDialog(false);
                        final NormalDialog doubleDialog = DialogUtil.getDoubleDialog(getActivity(), "是否要继续进行人员申报",
                                "离开", "继续");
                        doubleDialog.show();
                        doubleDialog.setOnBtnClickL(new OnBtnClickL() {
                            @Override
                            public void onBtnClick() {
                                doubleDialog.dismiss();
                                mPersonApplyActivity.finish();
                            }
                        }, new OnBtnClickL() {
                            @Override
                            public void onBtnClick() {
                                mTvApplyName.setText("");
                                mEtApplyCardId.setText("");
                                mEtApplyPhone.setText("");
                                mEtApplyHeigh.setText("");
                                doubleDialog.dismiss();
                                imgBase64 = "";
                                base64Avatar="";
                                mIvIdcard.setImageResource(R.drawable.transparency_full);
                                mIvApplyAvatar.setVisibility(View.INVISIBLE);
                            }
                        });
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build().execute();
    }

    @Override
    public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
        RentBean.RoomListBean bean = (RentBean.RoomListBean) parent.getItemAtPosition(position);
        mRoomId = bean.getROOMID();
        mTvApplyRoomNum.setText(bean.getROOMNO() + "");
        mNormalListDialog.dismiss();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, "onActivityResult: ");
        switch (requestCode) {
            case KCamera.REQUEST_CODE_KCAMERA:
                if (RESULT_OK == resultCode) {
                    String card = data.getStringExtra("card");
                    String name = data.getStringExtra("name");
                    //身份证base64字符串
                    imgBase64 = data.getStringExtra("img");
                    if (card != null) {
                        mEtApplyCardId.setText(card);
                    }
                    if (name != null) {
                        mTvApplyName.setText(name);
                    }
                    Bitmap bitmap = OCRUtil.base64ToBitmap(imgBase64);
                    mIvIdcard.setImageBitmap(bitmap);
                }
                break;
            case REQUEST_CAMARA:
                if (resultCode == RESULT_OK && data != null) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    mIvApplyAvatar.setVisibility(View.VISIBLE);
                    mIvApplyAvatar.setImageBitmap(bitmap);
                    mIvApplyAvatar.setEnabled(true);
                    base64Avatar = new String(ImageUtil.bitmapToBase64(bitmap));
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(TAG, "onSaveInstanceState: ");
        outState.putString("photo", base64Avatar);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.e(TAG, "onViewStateRestored: ");
        if (savedInstanceState != null) {
            base64Avatar = savedInstanceState.getString("photo");
        }
    }

    public boolean isShowBigImg() {
        return mIvBigAvatar.getVisibility() == View.VISIBLE;
    }

    public void hideBigImg() {
        mIvBigAvatar.setVisibility(View.GONE);
    }

}
