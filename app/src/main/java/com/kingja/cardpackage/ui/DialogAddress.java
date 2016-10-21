package com.kingja.cardpackage.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.kingja.cardpackage.adapter.CzfInitAdapter;
import com.kingja.cardpackage.entiy.Basic_StandardAddressCodeByKey;
import com.kingja.cardpackage.entiy.ErrorResult;
import com.kingja.cardpackage.net.ThreadPoolTask;
import com.kingja.cardpackage.net.WebServiceCallBack;
import com.kingja.cardpackage.util.AppUtil;
import com.kingja.cardpackage.util.CheckUtil;
import com.kingja.cardpackage.util.Constants;
import com.kingja.cardpackage.util.DataManager;
import com.kingja.cardpackage.util.ToastUtil;
import com.tdr.wisdome.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/19 17:15
 * 修改备注：
 */
public class DialogAddress extends Dialog implements View.OnClickListener,AdapterView.OnItemClickListener,SwipeRefreshLayout.OnRefreshListener{
    private static final String TAG = "DialogAddress";
    private EditText mEtPopAddress;
    private ImageView mIvPopAddress;
    private ListView mLvPopAddress;
    private Button mBtnPopAddressCancel;
    private Button mBtnPopAddressConfirm;
    private SwipeRefreshLayout mSrl;
    private Activity activity;
    private List<Basic_StandardAddressCodeByKey.ContentBean> addressList = new ArrayList<>();
    private CzfInitAdapter czfQueryAdapter;
    private String queryAddress;
    private Context context;
    private OnSearchListener onSearchListener;
    private Basic_StandardAddressCodeByKey.ContentBean bean;
    private String addressCode;
    private String address;

    public DialogAddress(Context context) {

        super(context, R.style.CustomDialog);
        this.context = context;
        this.activity= (Activity) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_czf_address);

        initView();
        initEvent();
    }


    public void initView() {
        mEtPopAddress = (EditText) findViewById(R.id.et_pop_address);
        mIvPopAddress = (ImageView) findViewById(R.id.iv_pop_address);
        mLvPopAddress = (ListView) findViewById(R.id.lv_pop_address);
        mBtnPopAddressCancel = (Button) findViewById(R.id.btn_pop_address_cancel);
        mBtnPopAddressConfirm = (Button) findViewById(R.id.btn_pop_address_confirm);
        czfQueryAdapter = new CzfInitAdapter(context, addressList);
        mSrl = (SwipeRefreshLayout) findViewById(R.id.srl);
        mSrl.setColorSchemeResources(R.color.bg_blue);
        mSrl.setProgressViewOffset(false, 0, AppUtil.dp2px(24));
        mLvPopAddress.setAdapter(czfQueryAdapter);
        mLvPopAddress.setOnItemClickListener(this);

    }


    public void initEvent() {
        mSrl.setOnRefreshListener(this);
        mIvPopAddress.setOnClickListener(this);
        mBtnPopAddressCancel.setOnClickListener(this);
        mBtnPopAddressConfirm.setOnClickListener(this);
    }

    public void showAndReset() {
        show();
        czfQueryAdapter.reset();
        mEtPopAddress.setText("");
        address = "";
        addressCode = "";
    }
    /**
     * 根据字符串检索
     *
     * @param queryAddress
     */
    @SuppressWarnings("unchecked ")
    private void serach(String queryAddress) {
        hideInput();
        mSrl.setRefreshing(true);
        Map<String, Object> param = new HashMap<>();
        param.put("TaskID", "1");
        param.put("PageSize", "30");
        param.put("PageIndex", "0");
        param.put("KEY", queryAddress);

        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), Constants.CARD_TYPE_SHOP, Constants.Basic_StandardAddressCodeByKey, param)
                .setBeanType(Basic_StandardAddressCodeByKey.class)
                .setCallBack(searchCallBack).build().execute();

    }

    /**
     * 隐藏软键盘
     */
    private void hideInput() {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private WebServiceCallBack<Basic_StandardAddressCodeByKey> searchCallBack = new WebServiceCallBack<Basic_StandardAddressCodeByKey>() {
        @Override
        public void onSuccess(Basic_StandardAddressCodeByKey bean) {
            mSrl.setRefreshing(false);
            addressList = bean.getContent();
            Log.i(TAG, "addressList: " + addressList.size());
            if (addressList.size() == 0) {
                ToastUtil.showToast("无搜索结果，请核对地址");
            }
            czfQueryAdapter.setData(addressList);
        }

        @Override
        public void onErrorResult(ErrorResult errorResult) {
            mSrl.setRefreshing(false);
        }
    };

    @Override
    public void onClick(View v) {
        queryAddress = mEtPopAddress.getText().toString().trim();
        switch (v.getId()) {
            case R.id.iv_pop_address:
                if (CheckUtil.checkEmpty(queryAddress, "请输入搜索地址")) {
                    serach(queryAddress);
                }
                break;
            case R.id.btn_pop_address_cancel:
                dismiss();
                break;
            case R.id.btn_pop_address_confirm:
                if (onSearchListener != null) {
                    if (CheckUtil.checkEmpty(address, "请查询并选择地址")) {
                        onSearchListener.onConfirm(bean);
                        dismiss();
                    }
                }

                break;
            default:
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        czfQueryAdapter.selectItem(position);
        bean = (Basic_StandardAddressCodeByKey.ContentBean) parent.getItemAtPosition(position);
        address = bean.getAddress();
        addressCode = bean.getId();
        Log.i(TAG, "address: "+address);
        Log.i(TAG, "Id: "+addressCode);

    }

    @Override
    public void onRefresh() {
        mSrl.setRefreshing(false);
    }

    public interface OnSearchListener{
        void onConfirm(Basic_StandardAddressCodeByKey.ContentBean bean);
    }

    public void setOnSearchListener(OnSearchListener onSearchListener) {
        this.onSearchListener = onSearchListener;
    }
}
