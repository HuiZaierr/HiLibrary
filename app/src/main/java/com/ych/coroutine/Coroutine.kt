package com.ych.coroutine

import com.ych.hilibrary.log.HiLog
import kotlinx.coroutines.delay

class Coroutine {

    suspend fun request1(): String {
        //delay方法并不会暂停线程，但是会暂停当前所在的协程
        delay(2 * 1000)
        HiLog.e("TAG","request1 work on ${Thread.currentThread().name}")
        return "result from request1"
    }
}