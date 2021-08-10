package com.ych.hilibrary.executor

import java.util.concurrent.PriorityBlockingQueue

object HiExecutor{

    init {
        //定义线程池的参数信息
        //获取CPU个数
        val cpuCount = Runtime.getRuntime().availableProcessors()
        //核心线程数，定义为CPU个数+1
        val corePoolSize = cpuCount + 1
        //最大线程数，定义为CPU个数 * 2 + 1
        val maxPoolSize = cpuCount * 2 + 1
        //非核心线程的存活时间
        val keepAliveTime = 30
//        val blockingQueue:PriorityBlockingQueue
    }

}