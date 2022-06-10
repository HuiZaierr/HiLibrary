package com.ych.hiui.banner.core

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * TODO：自定义ViewPager
 *    1.带自动播放的ViewPager，通过Handler实现。
 *    2.
 */
class HiViewPager(context: Context,
                  attrs: AttributeSet
) : ViewPager(context,attrs) {
    //ViewPager滚动时间间隔
    var mIntervalTime:Long = 0

    //是否开启自动轮播
    var mAutoPlay:Boolean = true
    set(value) {
        field = value
        if (!field){
            mHandler.removeCallbacks(mRunnable)
        }
    }
    //标志位，表示是否调用过onLayout方法
    var isLayout:Boolean = false
    //自动轮播通过Handler实现
    val mHandler:Handler = object: Handler(Looper.myLooper()!!) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
        }
    }
    //创建一个Runnable对象
    var mRunnable:Runnable = object :Runnable{
        override fun run() {
            //进行切换下一个item
            next()
            mHandler.postDelayed(this,mIntervalTime)
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        isLayout = true
    }

    /**
     * 设置下一个要显示的item。并返回当前item的pos
     */
    private fun next():Int {
        var nextPosition = -1
        //只有一个元素，直接停止，并返回当前位置
        if (adapter == null || adapter?.count!! <=1){
            stop()
            return nextPosition
        }

        nextPosition = currentItem + 1
        //如果下一个元素大于adapter最大数量时，那么从头开始
        if (nextPosition > adapter!!.count){
            //设置为第一个item的索引
//            nextPosition = adapter.getItemPosition()
        }

        /**
         * 进行设置
         *  1.当前item位置
         *  2.smoothScroll：是否圆滑的滚动
         */
        setCurrentItem(nextPosition,true)
        return nextPosition
    }


    /**
     * TODO：手指触摸Item时，禁止自动播放，松开后开启自动播放
     */
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        when(ev?.action){
            MotionEvent.ACTION_CANCEL,
            MotionEvent.ACTION_UP -> stop()
            else -> start()
        }
        return super.onTouchEvent(ev)
    }

    /**
     * TODO：用于解决RecyclerView嵌套ViewPager出现的问题
     *  1.滑动RecyclerView将ViewPager划出屏幕在划进时就会出现划到一半的情况。
     *  2.划出在划进不在执行自动滚动动画。就是根据mFirstLayout值判断的。
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (isLayout && adapter!=null && adapter?.run { count > 0 }!!){
            kotlin.runCatching {
                //通过反射获取ViewPager的mFirstLayout属性
                val mScroller = ViewPager::class.java.getDeclaredField("mFirstLayout")
                mScroller.isAccessible = true
                mScroller.set(this,false)
                start()
            }
        }
    }

    /**
     * TODO：解决RecyclerView嵌套ViewPager出现问题
     *   1.当滑动RecyclerView时，ViewPager被划出屏幕此时会进行回收，会调用onDetachedFromWindow方法，在划回来会调用
     *     onAttachedToWindow方法，此时就会出现一些问题，会停止动画，所以当我们在划进屏幕是就不会播放了。
     */
    override fun onDetachedFromWindow() {
        //只在Activity关闭的时候才调用父类的onDetachedFromWindow方法
        if ((context as Activity).isFinishing){
            super.onDetachedFromWindow()
        }
        stop()
    }

    /**
     * TODO：开始自动播放
     */
    fun start() {
        mHandler.removeCallbacksAndMessages(null)
        //如果是自动播放
        if (mAutoPlay){
            mHandler.postDelayed(mRunnable,mIntervalTime)
        }
    }

    /**
     * TODO：停止播放
     */
    fun stop() {
        mHandler.removeCallbacksAndMessages(null)
    }
}