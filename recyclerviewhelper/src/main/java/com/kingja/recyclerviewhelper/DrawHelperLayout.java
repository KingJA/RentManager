package com.kingja.recyclerviewhelper;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Description:TODO
 * Create Time:2016/8/15 16:18
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class DrawHelperLayout extends FrameLayout {
    /**
     * 步骤
     * 1. ViewDragHelper.create(this, new ViewDragHelper.Callback()
     * 2. 复写public boolean onInterceptTouchEvent(MotionEvent ev)和public boolean onTouchEvent(MotionEvent event)
     * 3. 复写callBack里的方法
     */

    private ViewDragHelper mViewDragHelper;
    private int mRange;
    private int mWidth;
    private int mHeight;
    private OnRootClickListener onRootClickListener;

    public ViewGroup getContentView() {
        return mContentView;
    }

    public ViewGroup getControlView() {
        return mControlView;
    }

    private ViewGroup mContentView;
    private ViewGroup mControlView;
    private State mState = State.CLOSE;
    private OnDragListener mOnDragListener;


    private enum State {
        OPEN, CLOSE, DRAGING;
    }

    public interface OnDragListener {
        void onOpen(DrawHelperLayout drawHelperLayout);

        void onClose(DrawHelperLayout drawHelperLayout);

        void onDrag(DrawHelperLayout drawHelperLayout);

        void onStartOpen(DrawHelperLayout drawHelperLayout);

        void onStartClose(DrawHelperLayout drawHelperLayout);
    }

    public void setOnDragListener(OnDragListener mOnDragListener) {
        this.mOnDragListener = mOnDragListener;

    }

    public DrawHelperLayout(Context context) {
        this(context, null);
    }

    public DrawHelperLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawHelperLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mViewDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            //是否可以拖动,true 可以，flase 不可以
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return true;
            }


            //可以左右拖动 返回left,可在方法中加入判断进行拖动范围的限定
            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                if (child == getContentView()) {
                    if (left < -mRange) {
                        left = -mRange;
                    } else if (left > 0) {
                        left = 0;
                    }
                } else if (child == getControlView()) {
                    if (left < mWidth - mRange) {
                        left = mWidth - mRange;
                    } else if (left > mWidth) {
                        left = mWidth;
                    }
                }
                return left;
            }

            //子View被捕获时调用，前提是tryCaptureView返回true
            @Override
            public void onViewCaptured(View capturedChild, int activePointerId) {
                super.onViewCaptured(capturedChild, activePointerId);
            }

            //设置横向拖动范围，不影响拖动位置
            @Override
            public int getViewHorizontalDragRange(View child) {
                return super.getViewHorizontalDragRange(child);
            }

            //设置纵向拖动范围，不影响拖动位置
            @Override
            public int getViewVerticalDragRange(View child) {
                return super.getViewVerticalDragRange(child);
            }

            //子View位置改变后的回调，重绘界面，伴随动画，更细状态
            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
                if (changedView == getContentView()) {
                    getControlView().offsetLeftAndRight(dx);
                } else if (changedView == getControlView()) {
                    getContentView().offsetLeftAndRight(dx);
                }
                dispathDragEvent();
                invalidate();
            }

            /**
             * 释放拖拽
             * @param releasedChild
             * @param xvel
             * @param yvel
             */
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                //xvel，yvel松手时水平和垂直方向的速度。向下向右为正。
                if (xvel == 0 && getContentView().getLeft() < -mRange / 2) {
                    open(true);
                } else if (xvel < 0) {
                    open(true);
                } else {
                    close(true);
                }
            }

            /**
             * 状态发生变化
             * @param state
             */
            @Override
            public void onViewDragStateChanged(int state) {
                super.onViewDragStateChanged(state);
//                STATE_IDLE 0 静止
//                STATE_DRAGGING 1 拖拽
//                STATE_SETTLING 2 松手
            }


        });
    }

    private void dispathDragEvent() {
        if (mOnDragListener == null) {
            return;
        }
        State preState = mState;
        mState = getCurrentState();
        if (preState != mState) {
            if (mState == State.OPEN) {
                mOnDragListener.onOpen(this);
            } else if (mState == State.CLOSE) {
                mOnDragListener.onClose(this);
            } else {
                if (preState == State.CLOSE) {
                    //将要打开
                    mOnDragListener.onStartOpen(this);
                } else if (preState == State.OPEN) {
                    //将要关闭
                    mOnDragListener.onStartClose(this);
                }

            }
        }
    }

    private State getCurrentState() {
        int left = getContentView().getLeft();
        if (left == -mRange) {
            return State.OPEN;
        } else if (left == 0) {
            return State.CLOSE;
        }
        return State.DRAGING;
    }

    private void close(boolean isSmooth) {
        if (isSmooth) {
            if (mViewDragHelper.smoothSlideViewTo(getContentView(), 0, 0)) {
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            layoutView(false);
        }
    }

    private void open(boolean isSmooth) {
        if (isSmooth) {
            if (mViewDragHelper.smoothSlideViewTo(getContentView(), -mRange, 0)) {
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            layoutView(true);
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //关闭时的布局
        layoutView(false);
    }

    private void layoutView(boolean isOpen) {
        Rect contentRect = getContentViewRect(isOpen);
        getContentView().layout(contentRect.left, contentRect.top, contentRect.right, contentRect.bottom);
        Rect controlRect = getcontrolViewRect(contentRect);
        getControlView().layout(controlRect.left, controlRect.top, controlRect.right, controlRect.bottom);
        //前置View
        bringChildToFront(getContentView());
    }

    private Rect getcontrolViewRect(Rect contentRect) {
        int left = contentRect.right;
        return new Rect(left, 0, left + mRange, mHeight);
    }

    private Rect getContentViewRect(boolean isOpen) {
        int left = 0;
        if (isOpen) {
            left = 0 - mRange;
        }
        return new Rect(left, 0, left + mWidth, mHeight);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    private float downX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                float daltX = Math.abs(downX - event.getX());
                if (daltX < 10 && onRootClickListener != null) {
                    onRootClickListener.onRootClick();
                }
                break;

        }
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    /**
     * XML加载后调用，可以通过getChildAt()获取子View对象
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mContentView = (ViewGroup) getChildAt(0);
        mControlView = (ViewGroup) getChildAt(1);
    }

    /**
     * View的尺寸发生变化时候的回调。可以在该回调中获取控件尺寸
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRange = getControlView().getMeasuredWidth();
        mWidth = getContentView().getMeasuredWidth();
        mHeight = getContentView().getMeasuredHeight();

    }

    public void setOnRootClickListener(OnRootClickListener onRootClickListener) {

        this.onRootClickListener = onRootClickListener;
    }

    public interface OnRootClickListener {
        void onRootClick();
    }
}
