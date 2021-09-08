package com.ych.hilibrary.executor

import android.os.Handler
import android.os.Looper
import androidx.annotation.IntRange
import com.ych.hilibrary.design_mode.adapter.main
import com.ych.hilibrary.log.HiLog
import kotlinx.coroutines.*
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock

/**
 * TODO：支持按任务的优先级去执行。
 *      支持线程池的暂停与恢复（比如批量下载文件，上传等）
 *      支持异步结果主动回掉主线程的能力
 *      //线程池远远不止这些，还可以线程池能力监控，耗时任务检测，定时，延迟。（后期需要编写）
 */
object HiExecutor{

    private val TAG: String = "HiExecutor"
    var hiExecutor:ThreadPoolExecutor
    val lock:ReentrantLock
    //定义线程池暂停的条件队列
    val pauseCondition:Condition
    var isPause:Boolean = false
    private val mainHandler:Handler = Handler(Looper.getMainLooper())

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
             * TODO：通过线程池源码发现，该方法在线程执行完成后会被调用
             *      我们可以用来监控线程池耗时任务，线程创建数量，正在运行的数量
             */
            override fun afterExecute(r: Runnable?, t: Throwable?) {
                HiLog.et(TAG,"已执行完的任务的优先级是：${(r as PriorityRunnable).priority}")

            }
        }
    }

    /**
     * TODO：调用线程池的executor方法
     *  通过IntRange注解来标识优先级的范围。
     */
    @JvmOverloads
    fun executor(@IntRange(from = 0, to = 10)priority:Int = 0,runnable: Runnable){
        //将传入进来的Runnable包装为PriortyRunnable
        hiExecutor.execute(PriorityRunnable(priority,runnable))
    }

    /**
     * TODO：处理带返回结果的任务
     */
    @JvmOverloads
    fun executor(@IntRange(from = 0, to = 10)priority:Int = 0,callable: Callable<*>){
        //将传入进来的Runnable包装为PriortyRunnable
        hiExecutor.execute(PriorityRunnable(priority,callable))
    }

    abstract class Callable<T>:Runnable{
        override fun run() {
            //1.loading抛到主线程执行
            mainHandler.post { onPrepare() }
            //2.异步任务真正的执行
            val t :T? = onBackground();
            //移除所有消息.防止需要执行onCompleted了，onPrepare还没被执行，那就不需要执行了
            mainHandler.removeCallbacksAndMessages(null)
            //3.将异步任务执行结果抛到主线程处理
            mainHandler.post { onCompleted(t) }
        }

        //任务执行开始前，我们可以通过loading
        fun onPrepare(){
            //转菊花
        }

        //真正执行后台任务的，有返回值
        abstract fun onBackground():T?

        //任务执行完成，净返回值抛到主线程当中
        abstract fun onCompleted(t:T?)

    }

    class PriorityRunnable(val priority: Int,val runnable: Runnable):Runnable,Comparable<PriorityRunnable>{

        override fun run() {
            runnable.run()
        }

        /**
         * 通过对比优先级的大小，进行执行
         */
        override fun compareTo(other: PriorityRunnable): Int {
           //如果当前任务的优先级小于其他线程的优先级，此时返回1,否则大于其他线程优先级就返回-1，否则就为0
           return if (this.priority < other.priority) 1 else if (this.priority > other.priority) -1 else 0
        }


    }

    /**
     * TODO：线程池的暂停
     */
    fun pause(){
        lock.lock()
        try {
            isPause = true
            HiLog.et(TAG,"hiExecutor is pause!!!")
        } finally {
            lock.unlock()
        }
    }

    /**
     * TODO：线程池的恢复
     */
    @Synchronized
    fun resume(){
        lock.lock()
        try {
            isPause = false
            //唤醒所有await的线程
            pauseCondition.signalAll()
        } finally {
            lock.unlock()
        }
        HiLog.et(TAG,"hiExecutor is resume!!!")
    }

}