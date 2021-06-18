package com.ych.hilibrary.design_mode.adapter

/**
 * TODO：适配器对象
 *     1.适配器类继承适配者类，并实现客户所希望的接口。
 *     解决：这样就解决了适配器类适配了request2方法。
 */
class AdapterClass:Adaptee(),Target {
    override fun request2() {
        println("AdapterClass: request2")
    }
}