package com.ych.coroutine

import com.ych.hilibrary.log.HiLog
import kotlinx.coroutines.delay

object CoroutineScene2{
    private val TAG :String = "TAG"

    suspend fun request1():String{
        val request2 = request2()
        HiLog.e(TAG,"request1 completed")
        return "result from request1 ----------- $request2"
    }

    suspend fun request2():String{
        delay(5 * 1000)
        HiLog.e(TAG,"request2 completed")
        return "result2 from requests"
    }
}