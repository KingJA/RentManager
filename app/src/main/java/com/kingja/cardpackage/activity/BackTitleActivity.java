package com.kingja.cardpackage.activity;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kingja.cardpackage.base.BaseActivity;
import com.kingja.cardpackage.ui.NoDoubleClickListener;
import com.kingja.ui.SwitchMultiButton;
import com.tdr.wisdome.R;

import java.util.List;

/**
 * Description：TODO
 * Create Time：2016/8/5 15:57
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public abstract class BackTitleActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout mRlTopRoot;
    private RelativeLayout mRlTopBack;
    private ImageView mIvTopBack;
    protected RelativeLayout mRlTopMenu;
    private TextView mTvTopTitle;
    private FrameLayout mFlContent;
    private OnMenuClickListener onMenuClickListener;
    private OnRightClickListener onRightClickListener;
    protected RelativeLayout mRlTopRight;
    private TextView mTvTopRight;
    private View mVDivider;
    private SwitchMultiButton mSbSwitch;


    @Override
    protected abstract void initVariables();

    @Override
    protected void initView() {
        mRlTopRoot = (RelativeLayout) findViewById(R.id.rl_top_root);
        mRlTopBack = (RelativeLayout) findViewById(R.id.rl_top_back);
        mIvTopBack = (ImageView) findViewById(R.id.iv_top_back);
        mRlTopMenu = (RelativeLayout) findViewById(R.id.rl_top_menu);
        mRlTopRight = (RelativeLayout) findViewById(R.id.rl_top_right);
        mTvTopTitle = (TextView) findViewById(R.id.tv_top_title);
        mTvTopRight = (TextView) findViewById(R.id.tv_top_right);
        mFlContent = (FrameLayout) findViewById(R.id.fl_content);
        mVDivider = (View) findViewById(R.id.v_divider);
        mSbSwitch = (SwitchMultiButton) findViewById(R.id.sb_swtich);

        mRlTopBack.setOnClickListener(this);
        mRlTopMenu.setOnClickListener(this);
        mRlTopRight.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (onRightClickListener != null) {
                    onRightClickListener.onRightClick();
                }
            }
        });
        View content = View.inflate(this, getBackContentView(), null);
        if (content != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT);
            mFlContent.addView(content, params);
        }
        initContentView();

    }

    protected abstract void initContentView();

    public enum TopColor {
        WHITE, BLUE
    }

    /**
     * 设置头部主题，默认蓝色
     *
     * @param topColor 颜色枚举:TopColor.WHITE 白色 TopColor.BLUE 蓝色
     */
    public void setTopColor(TopColor topColor) {
        mRlTopRoot.setBackgroundColor(ContextCompat.getColor(this, topColor == TopColor.WHITE ? R.color.bg_white : R.color.bg_blue));
        mIvTopBack.setBackgroundResource(topColor == TopColor.WHITE ? R.drawable.sel_back_black : R.drawable.sel_back_white);
        mTvTopTitle.setTextColor(ContextCompat.getColor(this, topColor == TopColor.WHITE ? R.color.bg_black : R.color.bg_white));
        mTvTopRight.setTextColor(ContextCompat.getColor(this, topColor == TopColor.WHITE ? R.color.bg_black : R.color.bg_white));
        mVDivider.setBackgroundColor(ContextCompat.getColor(this, topColor == TopColor.WHITE ? R.color.bg_divider : R.color.bg_blue));
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        mTvTopTitle.setText(title);
        mTvTopTitle.setVisibility(View.VISIBLE);
    }

    /**
     * 切换按钮
     * @param swtichList 按钮文字
     */
    public void setSwitch(SwitchMultiButton.OnSwitchListener onSwitchListener , List<String> swtichList) {
        mSbSwitch.setText(swtichList);
        mSbSwitch.setOnSwitchListener(onSwitchListener);
        mSbSwitch.setVisibility(View.VISIBLE);
    }

    /**
     * 设置菜单图标点击事件
     * @param onMenuClickListener 菜单点击监听器
     */
    public void setOnMenuClickListener(OnMenuClickListener onMenuClickListener,int res) {
        mRlTopMenu.setBackgroundResource(res);
        mRlTopMenu.setVisibility(View.VISIBLE);
        this.onMenuClickListener = onMenuClickListener;
    }

    /**
     * 设置右侧文字点击事件
     * @param onRightClickListener 右侧文字点击监听器
     */

    public void setOnRightClickListener(OnRightClickListener onRightClickListener,String rightText) {
        mRlTopRight.setVisibility(View.VISIBLE);
        mTvTopRight.setText(rightText);
        this.onRightClickListener = onRightClickListener;
    }


    /**
     * 菜单点击接口
     */
    public interface OnMenuClickListener {
        void onMenuClick();
    }

    /**
     * 右侧文字点击接口
     */
    public interface OnRightClickListener {
        void onRightClick();
    }

    /**
     * 获取内容页布局
     *
     * @return 布局文件ID
     */
    protected abstract int getBackContentView();

    /**
     * 初始化网络访问
     */
    @Override
    protected abstract void initNet();

    /**
     * 初始化数据，如设置事件
     */
    @Override
    protected abstract void initData();

    /**
     * 设置数据
     */
    @Override
    protected abstract void setData();


    @Override
    protected int getContentView() {
        return R.layout.activity_back;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_top_back:
                onClickBack();
                break;
            case R.id.rl_top_menu:
                if (onMenuClickListener != null) {
                    onMenuClickListener.onMenuClick();
                }
                break;
//            case R.id.rl_top_right:
//
//                break;
            default:
                break;
        }
    }

    protected void onClickBack() {
        finish();
    }
}
