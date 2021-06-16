package com.ych.hiui.tab.common;

import androidx.annotation.NonNull;
import androidx.annotation.Px;

/**
 * HiTab对外接口
 * @param <D>
 */
public interface IHiTab<D> extends IHiTabLayout.OnTaSelectedListener<D> {
    void setHiTabInfo(@NonNull D data);

    /**
     * 动态修改某个item的大小
     * @param height
     */
    void resetHeight(@Px int height);
}
