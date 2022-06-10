package com.ych.coroutine

import android.util.Log
import com.ych.hilibrary.log.HiLog
import kotlinx.coroutines.*
import kotlin.coroutines.suspendCoroutine

/**
 *
 */
object CoroutineScene{

    private val TAG: String = "CoroutineScene"

    /**
     * TODO：GlobalScope.launch参数
     *      参数2:CoroutineStart启动模式
     *            DEFAULT：默认启动模式，创建即启动协程，可随时取消
     *            LAZY：延时启动模式，只有当我们调用start方法才会启动
     *            ATOMIC：自动模式，同样创建即启动协程，但启动前不能取消
     */
    fun startScene1(){
        HiLog.e(TAG,"coroutine is running")
        //并不会阻塞当前线程。
        val job = GlobalScope.launch(Dispatchers.Main,CoroutineStart.LAZY) {
            val request1 = request1()
            val request2 = request2(request1)
            val request3 = request3(request2)

            updateUI(request3)
        }
        job.start()

        HiLog.e(TAG,"coroutine has launched")
    }


    /**
     * TODO：启动一个线程，先执行request1，再同时启动request2，request3，当request2，request3执行完成后更行UI
     */
    fun startScene2(){
        HiLog.e(TAG,"coroutine is running")
        val job = GlobalScope.launch(Dispatchers.Main) {
            val request1 = request1()
            val deferred2 = GlobalScope.async { request2(request1)}
            val deferred3 = GlobalScope.async { request3(request1)}

            updateUI2(deferred2.await(),deferred3.await())
        }
        job.start()
        HiLog.e(TAG,"coroutine has launched")
    }

    private fun updateUI(request3: String) {
        HiLog.e(TAG,"updateUI work on ${Thread.currentThread().name}")
        HiLog.e(TAG,"paramter: $request3")
    }

    private fun updateUI2(request2: String,request3: String) {
        HiLog.e(TAG,"updateUI work on ${Thread.currentThread().name}")
        HiLog.e(TAG,"paramter: $request2 ---- $request3")
    }

    private suspend fun request1(): String {
        //delay方法并不会暂停线程，但是会暂停当前所在的协程
        delay(2 * 1000)
        HiLog.e(TAG,"request1 work on ${Thread.currentThread().name}")
        return "result from request1"
    }

    private suspend fun request2(request1:String): String {
        delay(2 * 1000)
        HiLog.e(TAG,"request2 work on ${Thread.currentThread().name}")
        return "result from request2"
    }

    private suspend fun request3(request2:String): String {
        delay(2 * 1000)
        HiLog.e(TAG,"request3 work on ${Thread.currentThread().name}")
        return "result from request3"
    }

}