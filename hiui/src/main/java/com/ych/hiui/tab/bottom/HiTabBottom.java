package com.ych.hiui.tab.bottom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.ych.hiui.R;
import com.ych.hiui.tab.common.IHiTab;

public class HiTabBottom extends RelativeLayout implements IHiTab<HiTabBottomInfo<?>> {
    public HiTabBottom(Context context) {
        this(context,null);
    }

    public HiTabBottom(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public HiTabBottom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
//        LayoutInflater.from(getContext()).inflate(R.layout.)
    }

    @Override
    public void setHiTabInfo(@NonNull HiTabBottomInfo<?> data) {

    }

    @Override
    public void resetHeight(int height) {

    }

    @Override
    public void onTabSelectedChange(int index, @NonNull HiTabBottomInfo<?> prevInfo, @NonNull HiTabBottomInfo<?> nextInfo) {

    }
}
