package com.ych.hiui.banner.core

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.contains
import androidx.viewpager.widget.PagerAdapter
import com.ych.hilibrary.extension.setOnClickListener
import java.lang.IllegalArgumentException

/**
 * TODO：HiViewPager的适配器， 为页面填充数据
 */
class HiBannerAdapter(val context: Context) : PagerAdapter() {
    //缓存的ViewHolder
    var mCachedViews:SparseArray<HiBannerViewHolder>? = null
    //banner的点击事件
    var mBannerClickListener:IHiBanner.OnBannerClickListener? = null
    var mBindAdapter:IBindAdapter? = null
    var models:List<out HiBannerMo>? = null

    //是否开启自动轮播
    var mAutoPlay:Boolean = true
    //非自动轮播状态下是否可以循环切换
    var mLoop:Boolean = false
    var mLayoutResId = -1

    /**
     * TODO:获取数量，需要根据我们的状态设置不同个数
     */
    override fun getCount(): Int {
        if (mAutoPlay){
            return Integer.MAX_VALUE
        } else if (mLoop){ //表示是否我们手动的滑动切换，可以无限切换
            return Integer.MAX_VALUE
        }else{
            return getRealCount()
        }
    }

    /**
     * 获取初次展示的Item位置
     */
    fun getFirstItem(): Int {
        return Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 ) % getRealCount()
    }

    /**
     * 获取真实的数量
     */
    private fun getRealCount(): Int {
        return if (models.isNullOrEmpty()) 0 else models!!.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    /**
     * TODO：获取获取当前的ViewHolder的rootView，进行两次判断是否添加，防止重复的添加
     */
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var realPosition = position
        if (getRealCount()>0){
            realPosition = position % getRealCount()
        }
        var viewHolder = mCachedViews?.get(realPosition)
        if (viewHolder?.rootView?.let { container.contains(it) } == true){
            container.removeView(viewHolder.rootView)
        }
        //数据绑定
        onBind(viewHolder,models?.get(position),realPosition)
        if (viewHolder?.rootView?.parent!=null){
            (viewHolder?.rootView?.parent as ViewGroup).removeView(viewHolder?.rootView)
        }
        container.addView(viewHolder?.rootView)
        return viewHolder?.rootView!!
    }

    override fun getItemPosition(`object`: Any): Int {
        //让我们的item每次都会刷新
        return POSITION_NONE
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

    }

    fun onBind(viewHoler:HiBannerAdapter.HiBannerViewHolder?,mo:HiBannerMo?,position:Int){
        setOnClickListener{
            if (mBannerClickListener!=null){
                mBannerClickListener!!.onBannerCLick(viewHoler,mo,position)
            }
        }
        if (mBindAdapter!=null){
            mBindAdapter!!.onBind(viewHoler,mo,position)
        }
    }

    //设置数据
    fun setBannerData(model:List<out HiBannerMo>?){
        this.models = model
        //初始化数据
        initCacheView()
        notifyDataSetChanged()
    }


    private fun initCacheView(){
        mCachedViews = SparseArray()
        for (index in models?.indices!!){
            //创建对应的ViewHolder
            var holder = HiBannerViewHolder(createView(LayoutInflater.from(context),null))
            mCachedViews?.put(index,holder)
        }
    }

    private fun createView(layoutInflater: LayoutInflater, parent: ViewGroup?):View{
        if (mLayoutResId == -1){
            throw IllegalArgumentException("you must be set setLayoutResId first")
        }
        return layoutInflater.inflate(mLayoutResId,parent,false)
    }

    //静态内部类
    companion object class HiBannerViewHolder{
        var viewSparseArray:SparseArray<View>? = null
        var rootView:View? = null

        constructor(rootView: View?) {
            this.rootView = rootView
        }

        fun findViewById(id:Int):View{
            if (rootView !is ViewGroup){
                return rootView as View
            }
            if (viewSparseArray == null){
                viewSparseArray = SparseArray(1)
            }
            var childView = viewSparseArray!!.get(id) as View
            if (childView==null){
                childView = rootView!!.findViewById(id)
                viewSparseArray?.put(id,childView)
            }
            return childView
        }
    }
}

