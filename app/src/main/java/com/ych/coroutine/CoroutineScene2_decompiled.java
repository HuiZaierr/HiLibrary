package com.ych.coroutine;

import com.ych.hilibrary.log.HiLog;

import org.jetbrains.annotations.NotNull;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlinx.coroutines.DelayKt;

/**
 * TODO：Java代码实现coroutine的挂起函数
 *   当我们将协程代码反编译后，它就是通过return结合callback进行实现的。
 *
 *     suspend fun request1(request2:String): String {
 *         delay(2 * 1000)
 *         HiLog.e(TAG,"request1 work on ${Thread.currentThread().name}")
 *         return "result from request1"
 *     }
 *
 *    suspend fun request2(): String {
 *         //delay方法并不会暂停线程，但是会暂停当前所在的协程
 *         delay(2 * 1000)
 *         HiLog.e("TAG","request2 work on ${Thread.currentThread().name}")
 *         return "result from request2"
 *     }
 */
public class CoroutineScene2_decompiled {

    public static final Object request1(Continuation preCallback){
        /**
         * TODO：注册一个回调
         */
        ContinuationImpl request1Callback;
        //判断一下是否已经包装过，就是为了防止恢复的时候再次包装。
        if (!(preCallback instanceof ContinuationImpl) || (((ContinuationImpl) preCallback).label & Integer.MIN_VALUE) == 0){
            request1Callback = new ContinuationImpl(preCallback){
                @Override
                Object invokeSuspend(@NotNull Object resumeResult) {
                    this.result = resumeResult;
                    this.label |= Integer.MIN_VALUE;
                    HiLog.e("TAG","request1 has resume");
                    //恢复当前挂起函数
                    return request1(this);
                }
            };
        }else {
            request1Callback = (ContinuationImpl) preCallback;
        }

        /**
         * TODO：进行判断状态
         */
        switch (request1Callback.label){
            case 0: //创建的回调，默认为0.
                Object request2 = request2(request1Callback);
                if (request2 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                    HiLog.e("TAG","request1 has suspend");
                    return IntrinsicsKt.getCOROUTINE_SUSPENDED();
                }
                break;
        }
        HiLog.e("TAG","request1 work on "+Thread.currentThread().getName());
        return "result from request1 ----------- " + request1Callback.result;
    }



    public static final Object request2(Continuation preCallback){
        /**
         * TODO：注册一个回调
         */
        ContinuationImpl request2Callback;
        //判断一下是否已经包装过，就是为了防止恢复的时候再次包装。
        if (!(preCallback instanceof ContinuationImpl) || (((ContinuationImpl) preCallback).label & Integer.MIN_VALUE) == 0){
            request2Callback = new ContinuationImpl(preCallback){
                @Override
                Object invokeSuspend(@NotNull Object resumeResult) {
                    this.result = resumeResult;
                    this.label |= Integer.MIN_VALUE;
                    //恢复当前挂起函数
                    HiLog.e("TAG","request2 has resume");
                    return request2(this);
                }
            };
        }else {
            request2Callback = (ContinuationImpl) preCallback;
        }

        /**
         * TODO：进行判断状态
         */
        switch (request2Callback.label){
            case 0: //创建的回调，默认为0.
                Object delay = DelayKt.delay(2000, request2Callback);
                if (delay == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                    HiLog.e("TAG","request2 has suspend");
                    return IntrinsicsKt.getCOROUTINE_SUSPENDED();
                }
                break;
        }

        HiLog.e("TAG","request2 work on "+Thread.currentThread().getName());
        return "result from request2";
    }


    static abstract class ContinuationImpl<T> implements Continuation<T>{
        private Continuation preCallback;
        Object result;
        int label;

        public ContinuationImpl(Continuation preCallback) {
            this.preCallback = preCallback;
        }

        @NotNull
        @Override
        public CoroutineContext getContext() {
            return preCallback.getContext();
        }

        /**
         * TODO：该函数就是被挂起函数恢复时所返回的值
         * @param resumeResult
         */
        @Override
        public void resumeWith(@NotNull Object resumeResult) {
            Object suspend = invokeSuspend(resumeResult);
            //1.再次判断当前值是否还等于SUSPENDED,如果还等于表示你虽然恢复了，但是又被挂起了
            if (suspend == IntrinsicsKt.getCOROUTINE_SUSPENDED()){
                return;
            }
            //2.此时就是真正的恢复了，调用我们包装传入的Continuation回调的resumeResult。
            preCallback.resumeWith(suspend);
        }

        abstract Object invokeSuspend(@NotNull Object resumeResult);
    }

}
