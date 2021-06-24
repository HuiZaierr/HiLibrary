package com.ych.hiui.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * TODO：定义为抽象类，让所有视图实现。
 *      让用户实现各式各样的下拉刷新视图。
 */
public abstract class HiOverView extends FrameLayout {
    /**
     * 下拉刷新状态
     */
   public enum HiRefreshState{

        /**
         * 初始状态
         */
        STATE_INIT,
        /**
         * Header展示的状态
         */
        STATE_VISIBLE,
        /**
         * 头部超出可刷新距离的状态
         */
        STATE_REFRESH,
        /**
         * 超出刷新位置松开手后的状态
         */
        STATE_OVER_RELEASE
    }

    protected HiRefreshState mState = HiRefreshState.STATE_INIT;
   //触发下拉刷新需要的最小高度
    private int mPullRefreshHeight;

    /**
     * 下拉刷新不跟手（最小阻尼）
     * @param context
     */
    private float minDamp = 1.6f;

    /**
     * 下拉刷新不跟手（最大阻尼）
     * @param context
     */
    private float maxDamp = 2.2f;


    public HiOverView(@NonNull Context context) {
        super(context);
    }

    public HiOverView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HiOverView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 初始化
     */
    public abstract void init();

    protected abstract void onScroll(int scrollY,int pullRefreshHeight);

    /**
     * 显示Overlay
     */
    protected abstract void onVisible();

    /**
     * 超过Overlay，释放就会加载
     */
    public abstract void onOver();

    /**
     * 正在刷新
     */
    public abstract void onRefresh();

    /**
     * 刷新完成
     */
    public abstract void onFinish();

    /**
     * 获取刷新状态
     * @return
     */
    public HiRefreshState getState() {
        return mState;
    }

    /**
     * 设置刷新状态
     * @param state
     */
    public void setState(HiRefreshState state) {
        this.mState = state;
    }
}
