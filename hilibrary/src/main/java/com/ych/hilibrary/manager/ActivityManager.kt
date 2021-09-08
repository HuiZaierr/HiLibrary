package com.ych.hilibrary.manager

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.lang.ref.WeakReference

/**
 * TODO：Activity的任务栈管理
 *    1.通过单例实现
 */
class ActivityManager private constructor(){

    private val activityRefs = ArrayList<WeakReference<Activity>>()
    private val frontbackCallback = ArrayList<FrontBackCallback>()
    private var activityStartCount = 0  //默认是在后台显示
    public var front = true            //默认是在前台的
    /**
     * 获取栈顶Activity
     */
    public val topActivity:Activity?
        get() {
            if (activityRefs.size<=0){
                return null
            }else{
                return activityRefs[activityRefs.size - 1].get()
            }
            return null
        }

    companion object{
        val instance:ActivityManager by lazy(LazyThreadSafetyMode.SYNCHRONIZED){
            ActivityManager()
        }
    }

    public fun init(application: Application) {
        application.registerActivityLifecycleCallbacks(InnerActivityLifecycleCallbacks())
    }

    //将状态回掉回去
    private fun onFrontBackChanged(front: Boolean) {
        for (callback in frontbackCallback) {
            callback.onChange(front)
        }
    }





    /**
     * 添加回掉
     */
    public fun addFrontBackCallback(callback:FrontBackCallback){
        frontbackCallback.add(callback)
    }

    /**
     * 移除回掉
     */
    fun removeFrontBackCallback(callback:FrontBackCallback){
        frontbackCallback.remove(callback)
    }

    //前后台切换回掉
    interface FrontBackCallback{
        fun onChange(front:Boolean)
    }

    /**
     * 内部类，继承ActivityLifecycleCallbacks，实现生命周期方法
     */
    inner class InnerActivityLifecycleCallbacks:Application.ActivityLifecycleCallbacks{
        override fun onActivityPaused(activity: Activity) {
            TODO("Not yet implemented")
        }

        override fun onActivityStarted(activity: Activity) {
            //当Activity启动是进行加一
            activityStartCount++
            //判断是否是从后台切换到前台的
            if (!front && activityStartCount > 0){
                front = true
                onFrontBackChanged(front)
            }


        }

        override fun onActivityDestroyed(activity: Activity) {
            for (activityRef in activityRefs) {
                //判断activityRef是否为空，是否是当前的activity
                if (activityRef!=null && activityRef.get()== activity){
                    activityRefs.remove(activityRef)
                    break
                }
            }
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            TODO("Not yet implemented")
        }

        override fun onActivityStopped(activity: Activity) {
           //统计是否在前台，可以通过计数器进行统计。
            activityStartCount--
            //判断是不是从前台变到了后台
            if (activityStartCount<=0 && front){
                front = false
                onFrontBackChanged(front)
            }
        }

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            activityRefs.add(WeakReference(activity))
        }

        override fun onActivityResumed(activity: Activity) {

        }

    }

}