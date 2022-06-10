package com.ych.hiui.banner.core

import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import androidx.viewpager.widget.ViewPager
import com.ych.hiui.banner.indicator.HiIndicator

interface IHiBanner<T> {
    //HiBannerMo数据类
    fun setBannerData(@LayoutRes layoutResId:Int,@NonNull models:List<out HiBannerMo>)

    fun setBannerData(@NonNull models: List<out HiBannerMo>)

    fun setHaIndicator(hiIndicator: HiIndicator<T>)

    fun serAutoPlay(autoPlay:Boolean)

    fun setLoop(loop:Boolean)

    fun setIntervalTime(intervalTime:Int)

    fun setBindAdapter(bindAdapter: IBindAdapter)

    fun setOnPageChangerListener(onPageChangeListener: ViewPager.OnPageChangeListener)

    fun onBannerClick(onBannerClickListener: OnBannerClickListener)

    interface OnBannerClickListener{
        fun onBannerCLick(
            @NonNull viewHolder: HiBannerAdapter.HiBannerViewHolder?,
            @NonNull mo:HiBannerMo?,
            @NonNull position: Int)
    }
}