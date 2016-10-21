package com.kingja.cardpackage.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;
import com.kingja.cardpackage.adapter.DividerItemDecoration;
import com.kingja.cardpackage.adapter.PersonApplyRvAdapter;
import com.kingja.cardpackage.adapter.RoomListAdapter;
import com.kingja.cardpackage.base.BaseFragment;
import com.kingja.cardpackage.entiy.ChuZuWu_LKSelfReportingList;
import com.kingja.cardpackage.entiy.ChuZuWu_LKSelfReportingOut;
import com.kingja.cardpackage.entiy.ErrorResult;
import com.kingja.cardpackage.entiy.RentBean;
import com.kingja.cardpackage.net.ThreadPoolTask;
import com.kingja.cardpackage.net.WebServiceCallBack;
import com.kingja.cardpackage.util.AppUtil;
import com.kingja.cardpackage.util.Constants;
import com.kingja.cardpackage.util.DataManager;
import com.kingja.cardpackage.util.DialogUtil;
import com.kingja.cardpackage.util.TempConstants;
import com.kingja.cardpackage.util.ToastUtil;
import com.tdr.wisdome.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description：申报列表
 * Create Time：2016/8/19 14:20
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */

public class ApplyListFragment extends BaseFragment implements OnOperItemClickL, SwipeRefreshLayout.OnRefreshListener,PersonApplyRvAdapter.OnDeliteItemListener {
    private LinearLayout mLlSelectRoom;
    private TextView mTvApplyRoomNum;
    private SwipeRefreshLayout mSrlApplyList;
    private RecyclerView mRvApplyList;
    private RentBean entiy;
    private NormalListDialog mNormalListDialog;
    private PersonApplyRvAdapter mPersonApplyRvAdapter;
    private LinearLayout mLlEmpty;
    private List<RentBean.RoomListBean> mRoomList;

    public static ApplyListFragment newInstance(RentBean bean) {
        ApplyListFragment mApplyListFragment = new ApplyListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("ENTIY", bean);
        mApplyListFragment.setArguments(bundle);
        return mApplyListFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_apply_list;
    }

    @Override
    public void initFragmentView(View view, Bundle savedInstanceState) {
        mLlEmpty = (LinearLayout) view.findViewById(R.id.ll_empty);
        mLlSelectRoom = (LinearLayout) view.findViewById(R.id.ll_selectRoom);
        mTvApplyRoomNum = (TextView) view.findViewById(R.id.tv_apply_roomNum);
        mSrlApplyList = (SwipeRefreshLayout) view.findViewById(R.id.srl_apply_list);
        mRvApplyList = (RecyclerView) view.findViewById(R.id.rv_apply_list);

        mSrlApplyList.setColorSchemeResources(R.color.bg_black);
        mSrlApplyList.setProgressViewOffset(false, 0, AppUtil.dp2px(24));
    }

    @Override
    public void initFragmentVariables() {
        entiy = (RentBean) getArguments().getSerializable("ENTIY");
        mRoomList = entiy.getRoomList();
    }

    @Override
    public void initFragmentNet() {

    }

    private void doNet(String houseId, String rommId) {
        mSrlApplyList.setRefreshing(true);
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.HOUSEID, houseId);
        param.put(TempConstants.ROOMID, rommId);
        param.put(TempConstants.TaskID, "1");
        param.put(TempConstants.PageSize, "100");
        param.put(TempConstants.PageIndex, "0");
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_HOUSE, Constants.ChuZuWu_LKSelfReportingList, param)
                .setBeanType(ChuZuWu_LKSelfReportingList.class)
                .setCallBack(new WebServiceCallBack<ChuZuWu_LKSelfReportingList>() {
                    @Override
                    public void onSuccess(ChuZuWu_LKSelfReportingList bean) {
                        mSrlApplyList.setRefreshing(false);
                        List<ChuZuWu_LKSelfReportingList.ContentBean.PERSONNELINFOLISTBean> mApplyList = bean.getContent().getPERSONNELINFOLIST();
                        mLlEmpty.setVisibility(mApplyList.size()>0?View.GONE:View.VISIBLE);
                        mPersonApplyRvAdapter = new PersonApplyRvAdapter(getActivity(), mApplyList);
                        mPersonApplyRvAdapter.setOnDeliteItemListener(ApplyListFragment.this);
                        mRvApplyList.setLayoutManager(new LinearLayoutManager(getActivity()));
                        mRvApplyList.addItemDecoration(new DividerItemDecoration(getActivity(),
                                DividerItemDecoration.VERTICAL_LIST));
                        mRvApplyList.setHasFixedSize(true);
                        mRvApplyList.setAdapter(mPersonApplyRvAdapter);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        mSrlApplyList.setRefreshing(false);
                    }
                }).build().execute();
    }

    @Override
    public void initFragmentData() {
        mLlSelectRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRoomList.size() > 0) {
                    mNormalListDialog = DialogUtil.getListDialog(getActivity(), "房间号", new RoomListAdapter(getActivity(), mRoomList));
                    mNormalListDialog.setOnOperItemClickL(ApplyListFragment.this);
                }else{
                    ToastUtil.showToast("该出租屋暂时没有房间");
                }
            }
        });
        mSrlApplyList.setOnRefreshListener(ApplyListFragment.this);
    }

    @Override
    public void setFragmentData() {

    }

    @Override
    public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
        RentBean.RoomListBean bean = (RentBean.RoomListBean) parent.getItemAtPosition(position);
        mTvApplyRoomNum.setText(bean.getROOMNO() + "");
        doNet(entiy.getHOUSEID(), bean.getROOMID());
        mNormalListDialog.dismiss();
    }

    @Override
    public void onRefresh() {
        mSrlApplyList.setRefreshing(false);
    }

    @Override
    public void onDeliteItem(String listId, final int position) {
        mSrlApplyList.setRefreshing(true);
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.TaskID, "1");
        param.put("LISTID", listId);
        param.put("OUTREPORTERROLE", "1");
        param.put("OUTOPERATOR",  DataManager.getUserId());
        param.put("OUTOPERATORPHONE", DataManager.getUserPhone());

        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_HOUSE, Constants.ChuZuWu_LKSelfReportingOut, param)
                .setBeanType(ChuZuWu_LKSelfReportingOut.class)
                .setCallBack(new WebServiceCallBack<ChuZuWu_LKSelfReportingOut>() {
                    @Override
                    public void onSuccess(ChuZuWu_LKSelfReportingOut bean) {
                        mSrlApplyList.setRefreshing(false);
                        mPersonApplyRvAdapter.deleteItem(position);

                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        mSrlApplyList.setRefreshing(false);
                    }
                }).build().execute();
    }
}
