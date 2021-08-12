package com.ych.hilibrary.restful

import java.io.IOException

/**
 * TODO：HiCall他是有工厂来创建的
 */
interface HiCall<T> {

    //同步请求
    @Throws(IOException::class)
    fun execute():HiResponse<T>

    //异步请求
    fun enqueue(callback: HiCallback<T>)

    interface Factory{
        fun newCall(request:HiRequest):HiCall<*>
    }
}