package com.kingja.ui.wheelview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kingja.ui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：
 * 类描述：上班时间选择器
 * 创建人：KingJA
 * 创建时间：2016/6/23 13:46
 * 修改备注：
 */
public class WorkTimeSelector extends Dialog implements View.OnClickListener {

    private Context context;
    private boolean isChecked;

    private WheelView wv_up_hour;
    private WheelView wv_up_second;

    private WheelView wv_down_hour;
    private WheelView wv_down_second;


    private List<String> hourList = new ArrayList<>();
    private List<String> secondList = new ArrayList<>();


    private TimeAdapter mUpHourAdapter;
    private TimeAdapter mUpSecondAdapter;

    private TimeAdapter mDownHourAdapter;
    private TimeAdapter mDownSecondAdapter;


    private int maxTextSize = 18;
    private int minTextSize = 14;

    private String selectUpHour = "09";
    private String selectUpSecond = "30";


    private String selectDownHour = "17";
    private String selectDownSecond = "30";


    private OnTimeSelectListener onTimeSelectListener;
    private CheckBox cb_workTime;

    public WorkTimeSelector(Context context) {
        super(context, R.style.KjAlertDialog);
        this.context = context;
    }

    public WorkTimeSelector(Context context, String time) {
        super(context, R.style.KjAlertDialog);
        this.context = context;
        if (!TextUtils.isEmpty(time)) {
            String[] split = time.split(":");
            this.selectUpHour = split[0];
            this.selectUpSecond = split[1];
        }

    } public WorkTimeSelector(Context context, String startTime,String endTime,boolean isChecked) {
        super(context, R.style.KjAlertDialog);
        this.context = context;
        this.isChecked = isChecked;
        if (!TextUtils.isEmpty(startTime)) {
            String[] split = startTime.split(":");
            this.selectUpHour = split[0];
            this.selectUpSecond = split[1];
        }

        if (!TextUtils.isEmpty(endTime)) {
            String[] split = endTime.split(":");
            this.selectDownHour = split[0];
            this.selectDownSecond = split[1];
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selector_work_time);
        wv_up_hour = (WheelView) findViewById(R.id.wv_up_hour);
        wv_down_hour = (WheelView) findViewById(R.id.wv_down_hour);
        wv_up_second = (WheelView) findViewById(R.id.wv_up_second);
        wv_down_second = (WheelView) findViewById(R.id.wv_down_second);

        cb_workTime = (CheckBox) findViewById(R.id.cb_workTime);

        cb_workTime.setChecked(isChecked);
        RelativeLayout rl_confirm = (RelativeLayout) findViewById(R.id.rl_confirm);
        RelativeLayout rl_cancel = (RelativeLayout) findViewById(R.id.rl_cancel);

        rl_confirm.setOnClickListener(this);
        rl_cancel.setOnClickListener(this);


        initHours();
        initSeconds();


        mUpHourAdapter = new TimeAdapter(context, hourList, Integer.valueOf(selectUpHour), maxTextSize, minTextSize);
        mDownHourAdapter = new TimeAdapter(context, hourList, Integer.valueOf(selectDownHour), maxTextSize, minTextSize);
        mUpSecondAdapter = new TimeAdapter(context, secondList, Integer.valueOf(selectUpSecond), maxTextSize, minTextSize);
        mDownSecondAdapter = new TimeAdapter(context, secondList, Integer.valueOf(selectDownSecond), maxTextSize, minTextSize);

        wv_up_hour.setVisibleItems(1);
        wv_up_hour.setViewAdapter(mUpHourAdapter);
        wv_up_hour.setCurrentItem(Integer.valueOf(selectUpHour));

        wv_up_second.setVisibleItems(1);
        wv_up_second.setViewAdapter(mUpSecondAdapter);
        wv_up_second.setCurrentItem(Integer.valueOf(selectUpSecond));

        wv_down_hour.setVisibleItems(1);
        wv_down_hour.setViewAdapter(mDownHourAdapter);
        wv_down_hour.setCurrentItem(Integer.valueOf(selectDownHour));

        wv_down_second.setVisibleItems(1);
        wv_down_second.setViewAdapter(mDownSecondAdapter);
        wv_down_second.setCurrentItem(Integer.valueOf(selectDownSecond));


        wv_up_hour.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                selectUpHour = (String) mUpHourAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(selectUpHour, mUpHourAdapter);
            }
        });

        wv_up_hour.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                selectUpHour = (String) mUpHourAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(selectUpHour, mUpHourAdapter);
            }
        });



        wv_down_hour.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                selectDownHour = (String) mDownHourAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(selectDownHour, mDownHourAdapter);
            }
        });

        wv_down_hour.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                selectDownHour = (String) mDownHourAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(selectDownHour, mDownHourAdapter);
            }
        });





        wv_up_second.addChangingListener(new OnWheelChangedListener() {


            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                selectUpSecond = (String) mUpSecondAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(selectUpSecond, mUpSecondAdapter);
            }
        });

        wv_up_second.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                selectUpSecond = (String) mUpSecondAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(selectUpSecond, mUpSecondAdapter);
            }
        });

        wv_down_second.addChangingListener(new OnWheelChangedListener() {


            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                selectDownSecond = (String) mDownSecondAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(selectDownSecond, mDownSecondAdapter);
            }
        });

        wv_down_second.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                selectDownSecond = (String) mDownSecondAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(selectDownSecond, mDownSecondAdapter);
            }
        });


    }



    private class TimeAdapter extends AbstractWheelTextAdapter {
        List<String> list;

        protected TimeAdapter(Context context, List<String> list, int currentItem, int maxsize, int minsize) {
            super(context, R.layout.item_birth_year, NO_RESOURCE, currentItem, maxsize, minsize);
            this.list = list;
            setItemTextResource(R.id.tempValue);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return list.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            return list.get(index) + "";
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rl_confirm) {
            if (onTimeSelectListener != null) {
                onTimeSelectListener.onTimeSelect(selectUpHour, selectUpSecond, selectDownHour, selectDownSecond,cb_workTime.isChecked());
                return;
            }
        }
        if (v.getId() == R.id.rl_cancel) {
            dismiss();
            return;
        }

    }



    public void setOnTimeSelectListener(OnTimeSelectListener onTimeSelectListener) {
        this.onTimeSelectListener = onTimeSelectListener;
    }

    public interface OnTimeSelectListener {
        void onTimeSelect(String upHour, String upSecond, String downHour, String downSecond,boolean isChecked);
    }

    /**
     * 设置字体大小
     *
     * @param curriteItemText
     * @param adapter
     */
    public void setTextviewSize(String curriteItemText, TimeAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            currentText = textvew.getText().toString();
            if (curriteItemText.equals(currentText)) {
                textvew.setTextSize(maxTextSize);
            } else {
                textvew.setTextSize(minTextSize);
            }
        }
    }

    public void initHours() {
        for (int i = 0; i < 24; i++) {
            hourList.add(String.format("%02d", i));
        }
    }

    private void initSeconds() {
        for (int i = 0; i < 60; i++) {
            secondList.add(String.format("%02d", i));
        }
    }
}
