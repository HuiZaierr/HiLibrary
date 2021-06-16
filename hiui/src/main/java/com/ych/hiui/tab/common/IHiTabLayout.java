package com.ych.hiui.tab.common;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.List;

public interface IHiTabLayout<Tab extends ViewGroup,D>{

    Tab findTab(@NonNull D data);

    void addTabSelectedChangeListener(OnTaSelectedListener<D> listener);

    void inflateInfo(@NonNull List<D> infoList);

    //Tab选中的时候监听器
    interface OnTaSelectedListener<D>{
        /**
         *
         * @param index
         * @param prevInfo 上一个选中的Tab数据
         * @param nextInfo 下一个选中的Tab数据
         */
        void onTabSelectedChange(int index,@NonNull D prevInfo,@NonNull D nextInfo);
    }

}
