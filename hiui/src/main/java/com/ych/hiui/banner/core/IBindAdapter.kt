package com.ych.hiui.banner.core

/**
 * TODO：HiBanner的数据绑定接口，基于该接口可以实现数据的绑定和框架层解耦
 */
interface IBindAdapter {

    fun onBind(viewHoler:HiBannerAdapter.HiBannerViewHolder?,mo:HiBannerMo?,position:Int)
}