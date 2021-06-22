package com.ych.common.tab;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * TODO：将Fragment的操作内聚，提供一些通用的API
 */
public class HiFragmentTabView extends FrameLayout {

    private HiTabViewAdapter mAdapter;
    private int currentPosition;

    public HiFragmentTabView(@NonNull Context context) {
        this(context,null);
    }

    public HiFragmentTabView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HiFragmentTabView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HiTabViewAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(HiTabViewAdapter adapter) {
        if (mAdapter!=null || adapter == null){
            return;
        }
        this.mAdapter = adapter;
        currentPosition = -1;   //它不对应任何一个Fragment
    }

    public void setCurrentItem(int position){
        //小于0或者大于底部Fragment的数量，表示越界直接返回
        if (position<0 || position > mAdapter.getCount()){
            return;
        }
        //如果当前的position不等于设置的进行替换
        if (currentPosition!=position){
            currentPosition = position;
            mAdapter.instantiateItem(this,position);    //实例化我们当前位置的Fragment
        }

    }

    /**
     * 获取当前的Fragment的位置
     * @return
     */
    public int getCurrentItem(){
        return currentPosition;
    }

    public Fragment getCurrentFragment(){
        if (mAdapter==null){
            throw new IllegalArgumentException("please call setAdapter first.");
        }
        return mAdapter.getCurrentFragment();
    }

}
