package com.ych.hilibrary.executor

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.IntRange
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock

/**
 * TODO：线程池的封装，全局通用的线程池组件
 *  1.支持任务优先级
 *      使用PriorityBlockingQueue优先级队列。
 *  2.支持线程池暂停，恢复，关闭（比如批量的文件上传，下载）
 *      添加Pause / Resume / Close
 *  3.支持异步任务结果回调
 *      想要获取任务的执行结果，我们可以通过Callable来实现，它的call方法返回一个结果。
 *  以下待完善。
 *  4.线程池能力监控
 *  5.耗时任务检测
 *  6.定时，延时任务
 */
object HiExecutor {

    private const val TAG: String = "HiExecutor"
    //是否需要暂停线程池
    private var isPaused:Boolean = false
    //线程池对象
    private var hiExecutor:ThreadPoolExecutor
    //创建锁对象，采用内部的Condition对象来实现线程池的暂停/恢复
    private var lock :ReentrantLock
    //暂停线程池的Condition对象
    private var pauseCondition:Condition
    private val mainHandler = Handler(Looper.getMainLooper())


    init {
        //获取CPU个数
        val cpuCount = Runtime.getRuntime().availableProcessors()
        //创建锁对象
        lock = ReentrantLock()
        //创建暂停线程池的Condition对象
        pauseCondition = lock.newCondition()
        //1.定义核心线程数(CPU个数 + 1)
        val corePoolSize = cpuCount +1
        //2.定义最大线程数(CPU个数 * 2 + 1)
        val maximumPoolSize = cpuCount * 2 + 1
        //3.定义非核心线程存活时间
        val keepAliveTime = 30L
        //4.定义非核心线程存活时间单位为秒
        val unit = TimeUnit.SECONDS
        //5.定义阻塞队列，采用优先级的队列
        val blockingQueue:PriorityBlockingQueue<out Runnable> = PriorityBlockingQueue()
        //6.定义一个线程工厂，用于创建线程.
        val seq = AtomicLong()
        val threadFactory = ThreadFactory {
            val thread = Thread(it)
            //为每一个线程设置名称，序列号通过Atomic原子类实现 名称为：hi-executor-0序号会增加
            thread.name = "hi-executor-${seq.getAndIncrement()}"
            return@ThreadFactory thread
        }

        //创建线程池对象
        hiExecutor = object :ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                blockingQueue as BlockingQueue<Runnable>,
                threadFactory){
            /**
             * TODO：任务执行开始前
             *   1.比如做一些操作，比如线程池暂停等（我们采用锁ReentrantLock）
             */
            override fun beforeExecute(t: Thread?, r: Runnable?) {
                //判断是否要暂停线程池
                if (isPaused){
                    //先获取锁对象,一般用于try/finally代码块中
                    lock.lock()
                    try {
                        pauseCondition.await()
                    }finally {
                        lock.unlock()
                    }
                }
            }

            /**
             * TODO：任务执行结束后
             *  1.可以统计任务的耗时时间
             *  2.可以统计当前线程池还有多少任务正在进行
             *  3.可以统计当前线程池创建了多少线程
             */
            override fun afterExecute(r: Runnable?, t: Throwable?) {
                Log.e(TAG,"已执行完的任务的优先级是：${(r as PriorityRunnable).priority}")
            }
        }
    }

    /**
     * TODO：执行任务（不需要获取返回值的任务）
     *  参数1：任务优先级（0 - 10）
     */
    @JvmOverloads
    fun executor(@IntRange(from = 0,to = 10) priority:Int = 0,runnable: Runnable){
        hiExecutor.execute(PriorityRunnable(priority,runnable))
    }

    /**
     * TODO：执行任务（需要获取返回值的任务）
     *  参数1：任务优先级（0 - 10）
     */
    @JvmOverloads
    fun executor(@IntRange(from = 0,to = 10) priority:Int = 0,callable: Callable<*>){
        hiExecutor.execute(PriorityRunnable(priority,callable))
    }


    /**
     * TODO：暂停线程池
     *  注意：该方法可能存在多线程中调用，我们通过synchronized关键字来实现同步
     */
    @Synchronized
    fun pause(){
        if (!isPaused){
            isPaused = true
        }
        Log.e(TAG,"hiexecutor is pause")
    }

    /**
     * TODO：恢复线程池
     */
    @Synchronized
    fun resume(){
        if (isPaused){
            isPaused = false
        }
        lock.lock()
        try {
            //恢复阻塞状态的线程
            pauseCondition.signalAll()
        }finally {
            lock.unlock()
        }
        Log.e(TAG,"hiexecutor is resumed")
    }

    /**
     * TODO：任务优先级对比的Runnable
     */
    private class PriorityRunnable(val priority:Int,val runnable: Runnable):Runnable,Comparable<PriorityRunnable>{
        override fun run() {
            runnable.run()
        }

        /**
         * TODO：对比当前任务的和其他任务的优先级。
         *   当前任务优先级大于其它任务，返回1
         *   当前任务优先级小于其他任务，返回-1
         *   否则0
         */
        override fun compareTo(other: PriorityRunnable): Int {
            return if (this.priority < other.priority) 1 else if (this.priority > other.priority) -1 else 0
        }
    }

    abstract class Callable<T> : Runnable {
        override fun run() {
            mainHandler.post { onPrepare() }

            val t:T = onBackground();

            //移除所有消息.防止需要执行onCompleted了，onPrepare还没被执行，那就不需要执行了
            mainHandler.removeCallbacksAndMessages(null)
            mainHandler.post { onCompleted(t) }
        }

        //真正任务执行前（运行在主线程）
        open fun onPrepare() {
            //转菊花
        }
        //真正执行后台任务的
        abstract fun onBackground(): T
        //任务执行完成将值抛到主线程（运行在主线程）
        abstract fun onCompleted(t: T)
    }
}