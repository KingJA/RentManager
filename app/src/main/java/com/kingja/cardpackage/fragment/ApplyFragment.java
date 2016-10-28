package com.kingja.cardpackage.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.kingja.cardpackage.util.StringUtil;
import com.kingja.cardpackage.util.ToastUtil;
import com.tdr.wisdome.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Description：设备申报
 * Create Time：2016/8/19 14:20
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ApplyFragment extends BaseFragment implements View.OnClickListener, PersonApplyActivity.OnSaveClickListener, OnOperItemClickL {
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
    private String imgBase64="";

    public static ApplyFragment newInstance(RentBean bean) {
        ApplyFragment mApplyFragment = new ApplyFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("ENTIY", bean);
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
        mLlOcrCamera = (LinearLayout) view.findViewById(R.id.ll_ocr_camera);
    }


    @Override
    public void initFragmentVariables() {
        entiy = (RentBean) getArguments().getSerializable("ENTIY");
        mRoomList = entiy.getRoomList();
    }

    @Override
    public void initFragmentNet() {

    }

    @Override
    public void initFragmentData() {
        mLlOcrCamera.setOnClickListener(this);
        mLlSelectRoom.setOnClickListener(this);
        mPersonApplyActivity.setOnSaveClickListener(this);
    }

    @Override
    public void setFragmentData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_selectRoom:
                if (mRoomList.size() > 0) {
                    mNormalListDialog = DialogUtil.getListDialog(getActivity(), "房间号", new RoomListAdapter(getActivity(), mRoomList));
                    mNormalListDialog.setOnOperItemClickL(ApplyFragment.this);
                } else {
                    ToastUtil.showToast("该出租屋暂时没有房间");
                }
                break;
            case R.id.ll_ocr_camera:
                KCamera.GoCamera(getActivity());
                break;

        }
    }

    @Override
    public void onSaveClick() {
        name = mTvApplyName.getText().toString().trim();
        cardId = mEtApplyCardId.getText().toString().trim().toUpperCase();
        phone = mEtApplyPhone.getText().toString().trim();
        if (CheckUtil.checkEmpty(mTvApplyRoomNum.getText().toString(), "请选择房间号")
                && CheckUtil.checkEmpty(name, "请通过相机获取姓名")
                && CheckUtil.checkEmpty(cardId, "请通过相机获取身份证号")
                && CheckUtil.checkPhoneFormat(phone)) {
            onApply();
        }

    }

    /**
     * 自主申报
     */
    private void onApply() {

//        Map<String, Object> param = new HashMap<>();
//        param.put(TempConstants.TaskID, "1");
//        param.put(TempConstants.HOUSEID, entiy.getHOUSEID());
//        param.put(TempConstants.ROOMID, mRoomId);
//        param.put("LISTID", StringUtil.getUUID());
//        /*通过OCR获取*/
//        param.put("NAME", name);
//        param.put("IDENTITYCARD", cardId);
//        param.put("PHONE", phone);
//        /**/
//        param.put("REPORTERROLE", "2");
//        param.put("OPERATOR", DataManager.getUserId());
//        param.put("STANDARDADDRCODE", entiy.getSTANDARDADDRCODE());
//        param.put("TERMINAL", "2");
//        param.put("XQCODE", entiy.getXQCODE());
//        param.put("PCSCODE", entiy.getPCSCODE());
//        param.put("JWHCODE", entiy.getJWHCODE());
//        param.put("OPERATORPHONE", "0");
        setProgressDialog(true);
        ChuZuWu_LKSelfReportingInParam bean = new ChuZuWu_LKSelfReportingInParam();
        bean.setTaskID("1");
        bean.setHOUSEID(entiy.getHOUSEID());
        bean.setROOMID(mRoomId);
        bean.setLISTID(StringUtil.getUUID());
        bean.setNAME(name);
        bean.setIDENTITYCARD(cardId);
        bean.setPHONE(phone);
        bean.setREPORTERROLE(2);
        bean.setOPERATOR(DataManager.getUserId());
        bean.setSTANDARDADDRCODE(entiy.getSTANDARDADDRCODE());
        bean.setTERMINAL(2);
        bean.setXQCODE( entiy.getXQCODE());
        bean.setPCSCODE(entiy.getPCSCODE());
        bean.setJWHCODE(entiy.getJWHCODE());
        bean.setOPERATORPHONE("18888888888");
        bean.setPHOTOCOUNT(1);
        List<ChuZuWu_LKSelfReportingInParam.PHOTOLISTBean> photolist = new ArrayList<>();
        ChuZuWu_LKSelfReportingInParam.PHOTOLISTBean photolistBean = new ChuZuWu_LKSelfReportingInParam.PHOTOLISTBean();
        photolistBean.setLISTID(StringUtil.getUUID());
        photolistBean.setTAG("身份证头像");
        photolistBean.setIMAGE(imgBase64);
        photolist.add(photolistBean);
        bean.setPHOTOLIST(photolist);
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_HOUSE, Constants.ChuZuWu_LKSelfReportingIn, bean)
                .setBeanType(ChuZuWu_LKSelfReportingIn.class)
                .setCallBack(new WebServiceCallBack<ChuZuWu_LKSelfReportingIn>() {
                    @Override
                    public void onSuccess(ChuZuWu_LKSelfReportingIn bean) {
                        setProgressDialog(false);
                        final NormalDialog doubleDialog = DialogUtil.getDoubleDialog(getActivity(), "是否要继续进行人员申报", "离开", "继续");
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
                                doubleDialog.dismiss();

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
        switch (requestCode) {
            case KCamera.REQUEST_CODE_KCAMERA:
                if (Activity.RESULT_OK == resultCode) {
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
//                    Bitmap bitmap = BitmapFactory.decodeFile(data.getStringExtra("img"));
                }
                break;
        }
    }
}
