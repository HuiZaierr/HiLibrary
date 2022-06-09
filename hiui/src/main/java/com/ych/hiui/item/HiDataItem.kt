package com.ych.hiui.item

import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.recyclerview.widget.RecyclerView

/**
 * TODO：数据类的基类
 */
abstract class HiDataItem<Data,VH:RecyclerView.ViewHolder>(data:Data?) {

    private var mAdapter: HiAdapter? = null
    var mData:Data? = null

    //进行赋值
    init {
        this.mData = data
    }

    /**
     * TODO：数据绑定
     */
    abstract fun onBindData(holder: VH,position:Int)

    /**
     * TODO：获取Item对应的布局资源文件
     */
    open fun getItemLayoutRes():Int{
        return -1
    }

    /**
     * TODO：返回可能有些不想写布局文件，直接new一个组件
     */
    open fun getItemView(parent: ViewGroup):View?{
        return null
    }

    /**
     * TODO：設置Adapter
     */
    fun setAdapter(hiAdapter: HiAdapter){
        this.mAdapter = hiAdapter
    }
    
    /**
     * TODO：刷新列表
     */
    fun refreshItem(){
        if (mAdapter != null) {
            mAdapter!!.refreshItem(this)
        }
    }

    /**
     * TODO：从列表上移除
     */
    fun removeItem(){
        if (mAdapter != null) {
            mAdapter!!.removeItem(this)
        }
    }

    /**
     * 该item在列表上占几列
     */
    fun getSpanSize():Int{
        return 0
    }

    /**
     * 该item被滑进屏幕
     */
    open fun onViewAttachedToWindow(holder: VH) {

    }

    /**
     * 该item被滑出屏幕
     */
    open fun onViewDetachedFromWindow(holder: VH) {

    }
}