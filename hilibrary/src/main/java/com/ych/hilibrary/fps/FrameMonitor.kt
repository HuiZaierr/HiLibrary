package com.ych.hilibrary.fps

import android.view.Choreographer
import com.ych.hilibrary.log.HiLog
import java.util.concurrent.TimeUnit

internal class FrameMonitor:Choreographer.FrameCallback {
    private val TAG:String = "FrameMonitor"
    private val choreographer = Choreographer.getInstance()
    //这里记录上一帧到达的时间戳
    private var frameStartTime:Long = 0
    private val listeners = arrayListOf<FpsMonitor.FpsCallback>()
    private var frameCount = 0  //1s内确切绘制了多少帧

    /**
     * 每帧的开始绘制都会回到该方法
     */
    override fun doFrame(frameTimeNanos: Long) {
        //将纳秒转换为毫秒
        var currentTimeMills = TimeUnit.NANOSECONDS.toMillis(frameTimeNanos)
        //如果上一帧的时间大于0，我们需要计算两帧之间的差异
        if (frameStartTime > 0){
            //计算两帧之间的时间差，当前帧的时间 - 上一帧的时间
            val timeSpan = currentTimeMills - frameStartTime
            frameCount++
            //fps一般是都是按秒来计算,每秒多少帧。可能两帧之间时间小1s时不处理
            if (timeSpan > 1000){
                val fps:Double =  frameCount * 1000 / timeSpan.toDouble()
                HiLog.e(TAG,fps)
                for (listener in listeners) {
                    listener.onFrame(fps)
                }
                frameCount = 0
                frameStartTime = currentTimeMills
            }
        }else{
            //表示第一帧
            frameStartTime = currentTimeMills
        }
        start()

    }

    fun start(){
        //监听应用程序的fps值，只需要调用该方法postFrameCallback
        choreographer.postFrameCallback(this)
    }

    fun stop(){
        frameStartTime = 0
        listeners.clear()
        choreographer.removeFrameCallback(this)
    }


    fun addListener(l: FpsMonitor.FpsCallback) {
        listeners.add(l)
    }
}