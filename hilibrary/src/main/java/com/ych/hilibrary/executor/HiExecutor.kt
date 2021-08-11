package com.ych.hilibrary.executor

import com.ych.hilibrary.log.HiLog
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock

/**
 * TODO：支持按任务优先级去执行。
 */
object HiExecutor{

    private val TAG: String = "HiExecutor"
    var hiExecutor:ThreadPoolExecutor
    val lock:ReentrantLock
    //定义线程池暂停的条件队列
    val pauseCondition:Condition
    var isPause:Boolean = false
    init {
        lock = ReentrantLock()
        pauseCondition = lock.newCondition()

        //定义线程池的参数信息
        //获取CPU个数
        val cpuCount = Runtime.getRuntime().availableProcessors()
        //核心线程数，定义为CPU个数+1
        val corePoolSize = cpuCount + 1
        //最大线程数，定义为CPU个数 * 2 + 1
        val maxPoolSize = cpuCount * 2 + 1
        //非核心线程空闲状态下存活时间
        val keepAliveTime = 30L
        //非核心线程空闲状态下存活时间单位
        val unit = TimeUnit.SECONDS
        //定义阻塞队列，PriorityBlockingQueue为有序的队列
        val blockingQueue:PriorityBlockingQueue<out Runnable> = PriorityBlockingQueue()
        val seq = AtomicLong()
        val threadFactory = ThreadFactory{
            val thread = Thread(it)
            //设置线程名称hi-executor-0
            thread.name = "hi-executor-${seq}"
            return@ThreadFactory thread
        };



        //创建线程池
        hiExecutor = object: ThreadPoolExecutor(
                            corePoolSize,
                            maxPoolSize,
                            keepAliveTime,
                            unit,
                            blockingQueue as BlockingQueue<Runnable>,
                            threadFactory){
            /**
             * TODO：通过线程池源码发现，该方法在线程执行前会被调用，我们用来处理线程的暂停
             */
            override fun beforeExecute(t: Thread?, r: Runnable?) {
                //阻塞需要使用ReentrantLock
                if (isPause){
                    lock.lock()
                    try {
                        //将线程进入阻塞状态，等待其他线程调用singnal方法将其唤醒。
                        pauseCondition.await()
                    }finally {
                        lock.unlock()
                    }
                }
            }

            /**
             * TODO：通过线程池源码发现，该方法在线程执行完成后会被调用，我们可以用来监控线程池的状态，创建个数，耗时任务等等信息
             */
            override fun afterExecute(r: Runnable?, t: Throwable?) {

            }
        }
    }

    /**
     * TODO：线程池的暂停
     */
    fun pause(){
        isPause = true
        HiLog.et(TAG,"hiExecutor is pause!!!")
    }

    /**
     * TODO：线程池的恢复
     */
    fun resume(){
        isPause = false
        lock.lock()
        try {
            //唤醒所有await的线程
            pauseCondition.signalAll()
        }finally {
            lock.unlock()
        }
    }

}