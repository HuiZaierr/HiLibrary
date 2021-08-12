package com.ych.coroutine

import android.content.res.AssetManager
import android.util.Log
import com.ych.hilibrary.log.HiLog
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.concurrent.thread

/**
 * TODO：演示以异步的方式读取assets目录下的文件，并且适配协程的写法，让他真正的挂起函数
 *     方便调用方，以同步的方式拿到返回值
 */
object CoroutineScene3{
    private val TAG :String = "CoroutineScene2"

    suspend fun parseAssetsFile(assetManager: AssetManager,fileName:String):String{
        //suspendCancellableCoroutine可以监听协程的取消
        return suspendCancellableCoroutine { continuation ->
            continuation.invokeOnCancellation {
                continuation.cancel()
            }
            thread {
                val inputStream = assetManager.open(fileName)
                val br = BufferedReader(InputStreamReader(inputStream))
                var line:String?
                var stringBuilder = StringBuilder()

                do {
                    //读取每一行
                    line = br.readLine()
                    if (line!=null) stringBuilder.append(line) else break
                }while (true)
                inputStream.close()
                br.close()

                Thread.sleep(2000)
                //成功或失败的返回方法
                continuation.resumeWith(Result.success(stringBuilder.toString()))
            }
        }
    }
}