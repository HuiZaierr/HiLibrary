package com.ych.hilibrary.restful

import com.ych.hilibrary.util.HiRes

interface HiInterceptor {

    fun interceptor(chain:Chain):Boolean

    /**
     * Chain 对象会在我们派发拦截器的时候创建
     */
    interface Chain{
        //是否是网络请求发起之前的拦截器的派发
        val isRequestPeriod:Boolean get() = false

        fun request():HiRequest

        /**
         * 这个response对象，在网络放弃之前是为空的
         */
        fun response():HiResponse<*>?
    }
}