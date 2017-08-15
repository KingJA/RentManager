package com.kingja.cardpackage.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;

import com.kingja.cardpackage.Event.ReadOneMsg;
import com.kingja.cardpackage.adapter.MsgAdapter;
import com.kingja.cardpackage.adapter.RvAdaper;
import com.kingja.cardpackage.entiy.ErrorResult;
import com.kingja.cardpackage.entiy.User_MessageForShiMing;
import com.kingja.cardpackage.net.ThreadPoolTask;
import com.kingja.cardpackage.net.WebServiceCallBack;
import com.kingja.cardpackage.ui.PullToBottomRecyclerView;
import com.kingja.cardpackage.util.Constants;
import com.kingja.cardpackage.util.DataManager;
import com.kingja.cardpackage.util.TempConstants;
import com.kingja.cardpackage.util.ToastUtil;
import com.kingja.recyclerviewhelper.LayoutHelper;
import com.kingja.recyclerviewhelper.RecyclerViewHelper;
import com.tdr.wisdome.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description：我的消息
 * Create Time：2016/9/3 10:56
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class MsgActivity extends BackTitleActivity {
    private LinearLayout mLlEmpty;
    private SwipeRefreshLayout mSrl;
    private PullToBottomRecyclerView mRv;
    private List<User_MessageForShiMing.ContentBean.MessageListBean> msgs = new ArrayList<>();
    private MsgAdapter mMsgAdapter;
    private int currentPage;
    private boolean hasMore;



    @Override
    protected void initVariables() {

    }

    @Override
    protected void initContentView() {
        mLlEmpty = (LinearLayout) findViewById(R.id.ll_empty);
        mSrl = (SwipeRefreshLayout) findViewById(R.id.srl);
        mRv = (PullToBottomRecyclerView) findViewById(R.id.rv);
    }

    @Override
    protected int getBackContentView() {
        return R.layout.single_rv;
    }

    @Override
    protected void initNet() {
        getMsgs(0);
    }

    @Override
    protected void initData() {
        mMsgAdapter = new MsgAdapter(this, msgs);
        new RecyclerViewHelper.Builder(this)
                .setAdapter(mMsgAdapter)
                .setLayoutStyle(LayoutHelper.LayoutStyle.VERTICAL_LIST)
                .setDividerHeight(1)
                .setDividerColor(0xffbebebe)
                .build()
                .attachToRecyclerView(mRv);
        mRv.setOnPullToBottomListener(new PullToBottomRecyclerView.OnPullToBottomListener() {
            @Override
            public void onPullToBottom() {
                if (mSrl.isRefreshing()) {
                    return;
                }
                if (hasMore) {
                    currentPage++;
                    getMsgs(currentPage);
                } else {
                    ToastUtil.showToast("到底啦");
                }
            }
        });
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSrl.setRefreshing(false);
            }
        });
        mMsgAdapter.setOnItemClickListener(new RvAdaper.OnItemClickListener<User_MessageForShiMing.ContentBean
                .MessageListBean>() {


            @Override
            public void onItemClick(User_MessageForShiMing.ContentBean.MessageListBean messageListBean, int position) {
                if (!mSrl.isRefreshing() && messageListBean.getIsRead() == 0) {
                    setMsgReaded(messageListBean.getMessageID(),position);
                }

            }
        });
    }

    @Override
    protected void setData() {
        setTitle("我的消息");
    }

    private void getMsgs(final int page) {
        mSrl.setRefreshing(true);
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.TaskID, TempConstants.DEFAULT_TASK_ID);
        param.put("PageSize", Constants.PAGE_SIZE);
        param.put("PageIndex", page);
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_EMPTY, Constants
                                .User_MessageForShiMing,
                        param)
                .setBeanType(User_MessageForShiMing.class)
                .setCallBack(new WebServiceCallBack<User_MessageForShiMing>() {
                    @Override
                    public void onSuccess(User_MessageForShiMing bean) {
                        mSrl.setRefreshing(false);
                        msgs = bean.getContent()
                                .getMessageList();
                        if (page == 0) {
                            mMsgAdapter.reset();
                        }
                        hasMore = msgs.size() == Constants.PAGE_SIZE;
                        mLlEmpty.setVisibility(msgs.size() > 0 ? View.GONE : View.VISIBLE);
                        mMsgAdapter.addData(msgs);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        mSrl.setRefreshing(false);
                    }
                }).build().execute();
    }

    private void setMsgReaded(String messageId, final int position) {
        mSrl.setRefreshing(true);
        Map<String, Object> param = new HashMap<>();
        param.put(TempConstants.TaskID, TempConstants.DEFAULT_TASK_ID);
        param.put("MessageID", messageId);
        param.put("IsRead", 1);
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_EMPTY, Constants
                                .User_SetMessageForShiMing,
                        param)
                .setBeanType(Object.class)
                .setCallBack(new WebServiceCallBack<Object>() {
                    @Override
                    public void onSuccess(Object bean) {
                        mMsgAdapter.setReaded(position);
                        EventBus.getDefault().post(new ReadOneMsg());
                        mSrl.setRefreshing(false);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        mSrl.setRefreshing(false);
                    }
                }).build().execute();
    }


}
