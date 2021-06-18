package com.ych.hilibrary.design_mode.adapter

class AdapterObj(private var adaptee: Adaptee):Target {
    override fun request1() {
        adaptee.request1()
    }

    override fun request2() {
        println("AdapterObj: request2")
    }
}