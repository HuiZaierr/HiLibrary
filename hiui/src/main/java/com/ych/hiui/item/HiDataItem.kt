package com.ych.hiui.item

import androidx.recyclerview.widget.RecyclerView
import java.text.FieldPosition

abstract class HiDataItem<DATA,VH:RecyclerView.ViewHolder>(data:DATA) {
    var mData:DATA? = null

    init {
        this.mData = data
    }

    /**
     * 数据绑定
     */
    abstract fun onBindData(holder:RecyclerView.ViewHolder,position: Int)

    /**
     * 返回该item的布局资源id
     */
    fun getItemLayoutRes():Int{
        return -1;
    }

}