package com.ych.coroutine

import android.util.Log
import com.ych.hilibrary.log.HiLog
import kotlinx.coroutines.delay

object CoroutineScene2{
    private val TAG :String = "CoroutineScene2"

    suspend fun request2():String{
        delay(2 * 1000)
        HiLog.e(TAG,"request2 completed")
        return "result from requests"
    }
}