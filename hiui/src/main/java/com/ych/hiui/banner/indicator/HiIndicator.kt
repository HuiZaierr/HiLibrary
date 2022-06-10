package com.ych.hiui.banner.indicator

/**
 * TODO：指示器统一接口
 *    实现改接口，来定义你需要样式的指示器
 *
 */
interface HiIndicator<out T> {

    fun get():T

    /**
     * TODO：初始化Indicator
     *  @param count 幻灯片的数量
     */
    fun onInflate(count:Int)

    /**
     * TODO：幻灯片切换的回掉
     *  @param current 切换到幻灯片的位置
     *  @param count 幻灯片的数量
     */
    fun onPointChange(current:Int,count:Int)
}