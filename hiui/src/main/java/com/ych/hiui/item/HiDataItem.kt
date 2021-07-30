package com.ych.hiui.item

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * TODO:每个item的数据类
 *     泛型1：DATA表示每一个item的数据实体类
 *     泛型2：对应的ViewHolder
 *     所以我们基于HiDataItem来构建相对应的；
 *          TopTabItem，BannerDataItem,ActivityItem,GridItem等等

 */
abstract class HiDataItem<DATA,VH:RecyclerView.ViewHolder>(data:DATA) {
    private lateinit var mAdapter: HiAdapter
    var mData:DATA? = null

    init {
        this.mData = data
    }

    /**
     * TODO:绑定本Item数据
     *   position：表示在列表上处于第几个
     */
    abstract fun onBindData(holder:RecyclerView.ViewHolder,position: Int)

    /**
     * TODO:获取该item的布局资源id，
     *      当不想使用布局文件时可以通过getItemView，直接获取一个View对象来当作item视图
     */
    open fun getItemLayoutRes():Int{
        return -1;
    }

    /**
     * TODO:获取本item视图View
     */
    open fun getItemView(parent: ViewGroup):View?{
        return null
    }

    fun setAdapter(adapter: HiAdapter){
        this.mAdapter = adapter
    }

    /**
     * TODO:刷新列表
     */
    fun refreshItem(){
        mAdapter.refreshItem(this)
    }

    /**
     * TODO:从列表上移除Item
     */
    fun removeItem(){
        mAdapter.removeItem(this)
    }

    /**
     * TODO：该Item在列表上占据几列，当我们使用网格布局，瀑布流布局时
     */
    fun getSpanSize():Int{
        return 0
    }
}