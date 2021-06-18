package com.ych.hilibrary.design_mode.adapter

import android.widget.Adapter

fun main() {
    //类适配器对象
    var adapterClass = AdapterClass()
    adapterClass.apply {
        request1()
        request2()
    }

    println("------------------------")

    //对象适配器模式
    var adaptee = Adaptee()
    var adapterObj = AdapterObj(adaptee)
    adapterObj.apply {
        request1()
        request2()
    }
}