package com.ych.hilibrary.util

import android.os.Handler
import android.os.Looper
import android.os.Message

/**
 * TODO：Hanlder工具类
 */
object MainHandler {

    private val handler = Handler(Looper.getMainLooper())

    /**
     * 发送消息
     */
    fun post(runnable: Runnable){
        handler.post(runnable)
    }

    /**
     * 发送延时消息
     */
    fun postDelay(delayMills:Long,runnable: Runnable){
        handler.postDelayed(runnable,delayMills)
    }

    /**
     * 将消息插入到消息队列的首位。
     */
    fun sendAtFrontOfQueue(runnable: Runnable){
        val msg = Message.obtain(handler,runnable)
        handler.sendMessageAtFrontOfQueue(msg)
    }
}