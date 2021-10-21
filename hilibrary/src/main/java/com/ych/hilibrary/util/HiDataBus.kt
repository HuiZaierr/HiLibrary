package com.ych.hilibrary.util

import androidx.lifecycle.*
import java.util.concurrent.ConcurrentHashMap

/**
 * TODO:事件总线，跨页面数据传递，那么就采用单例
 *      由于LiveData的粘性事件，当我们新注册的观察者时，他的mLastVersion默认为-1，此时可能
 *      存在小于LiveData发送数据的的version的值，所以新注册的观察者也能收到消息。
 *      源码：
            private void considerNotify(ObserverWrapper observer) {
                if (!observer.mActive) {
                    return;
                }
                if (!observer.shouldBeActive()) {
                    observer.activeStateChanged(false);
                    return;
                }
                //新注册的观察者的mLastVersion可能小于LiveData的mVersion值。所以就会调用onChanged方法
                if (observer.mLastVersion >= mVersion) {
                    return;
                }
                observer.mLastVersion = mVersion;
                observer.mObserver.onChanged((T) mData);
           }
        总结：LiveData核心就是将我们注册的观察者进行包装为LifecycleBoundObserver，然后将包装的观察者进行map存储。
             (key就是我们传入的观察者，value为我们包装后的观察者)，之后利用Lifecycle的能力，当宿主每一次状态变
             化的时候，都会回掉LifecycleBoundObserver的onStateChange方法，就会判断每一个观察者时候处于活跃状态
             如果是，用于observe方法注册的观察者而言，是否处于活跃就等于宿主是否处于活跃，用于observeForever方
             法注册的观察者而言，那么它会一直处于活跃状态，因为他的值一直返回为true。所以使用这种方法注册的话，
             即便页面处于不可见状态也能收到数据。
 */

object HiDataBus{
    //用于存储 事件名称 对应 的StickyLiveData
    private val eventMap = ConcurrentHashMap<String,StickyLiveData<*>>()
    /**
     * TODO:基于事件名称 订阅，分发消息。
     *  由于一个LiveData只能发送一种数据类型，所以不同的event事件，需要使用不同的LiveData实例，去分发。
     */
    fun <T> with(eventName:String):StickyLiveData<T>{
        //根据事件名称获取LiveData
        var liveData = eventMap[eventName]
        //如果为空，进行创建
        if (liveData == null){
            liveData = StickyLiveData<T>(eventName)
            eventMap[eventName] = liveData
        }
        return liveData as StickyLiveData<T>
    }

    /**
     * 粘性的LiveData对象
     */
    class StickyLiveData<T>(val eventName:String): LiveData<T>() {
        var mStickyData:T? = null
        var mVersion = 0

        /**
         * 只能在主线程中发送数据
         */
        fun setStickyData(stickyData:T){
            mStickyData = stickyData
            //还是调用LiveData的setVaue进行发送数据。
            setValue(stickyData)
        }

        /**
         * 不受线程的控制，可以在子线程也可以在主线程
         */
        fun postStickyData(stickyData: T){
            mStickyData = stickyData
            postValue(stickyData)
        }

        override fun setValue(value: T) {
            mVersion++
            super.setValue(value)
        }

        override fun postValue(value: T) {
            mVersion++
            super.postValue(value)
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
            observerSticky(owner,false,observer)
        }

        fun observerSticky(owner: LifecycleOwner,sticky:Boolean,observer: Observer<in T>){
            //允许指定注册的观察者，是否需要关心粘性事件
            //sticky == true，如果之前存在已经发送的数据，那么这个observer会收到之前的黏性事件消息。
            owner.lifecycle.addObserver(LifecycleEventObserver { source, event ->
                //监听 宿主 发生销毁事件，主动把livedata移除
                if (event == Lifecycle.Event.ON_DESTROY){
                    eventMap.remove(eventName)
                }
            })
            super.observe(owner,StickyObserver(this,sticky,observer))
        }
    }
}

class StickyObserver<T>(val stickyLiveData: HiDataBus.StickyLiveData<T>, val sticky: Boolean, val observer: Observer<in T>) : Observer<T> {
    /**
     * lastVersion和liveData的mVersion对齐的原因，就是为了控制粘性事件的分发。
     * sticky为false，只能接收到注册之后发送的消息，为true，他能接受粘性事件消息。
     */
    private var lastVersion = stickyLiveData.mVersion

    override fun onChanged(t: T) {
        if (lastVersion >= stickyLiveData.mVersion){
            //表示stickyLiveData没有更新的数据需要发送。判断是否需要处理粘性事件
            if (sticky && stickyLiveData.mStickyData!=null){
                observer.onChanged(stickyLiveData.mStickyData)
            }
            return
        }

        lastVersion = stickyLiveData.mVersion
        observer.onChanged(t)
    }
}
