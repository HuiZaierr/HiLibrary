package com.ych.coroutine.androiduse

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * TODO：它用于处理真正的网络请求（ViewModel持有该对象）
 */
class NewsRepository {

    suspend fun getUser() =
        //开启同一个协成，执行网络请求
        withContext(Dispatchers.Default){

        }

}