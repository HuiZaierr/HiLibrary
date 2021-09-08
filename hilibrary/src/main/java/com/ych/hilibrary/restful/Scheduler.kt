package com.ych.hilibrary.restful

/**
 * TODO：代理CallFactory创建出来的Call对象，从而实现拦截器的派发动作
 */
class Scheduler(val callFactory: HiCall.Factory,val interceptors:MutableList<HiInterceptor>) {

    fun newCall(request: HiRequest):HiCall<*>{
        val newCall = callFactory.newCall(request)
        return ProxyCall(newCall,request)
    }

    internal class ProxyCall<T>(val delegate:HiCall<T>,val request: HiRequest):HiCall<T>{
        override fun execute(): HiResponse<T> {
            //分发一遍拦截器
            dispatchInterceptor(request,null)
            val response = delegate.execute()
            dispatchInterceptor(request,response)
            return response

        }

        override fun enqueue(callback: HiCallback<T>) {
            dispatchInterceptor(request,null)
            val response = delegate.enqueue(object :HiCallback<T>{
                override fun onSuccess(response: HiResponse<T>) {
                    dispatchInterceptor(request,response)
                    if (callback!=null) callback.onSuccess(response)
                }

                override fun onFailed(throwable: Throwable) {
                    if (callback!=null) callback.onFailed(throwable)
                }

            })
//            dispatchInterceptor(request,response)
        }


        private fun dispatchInterceptor(request: HiRequest, response: HiResponse<T>?) {
            InteceptorChain(request,response).dispatch()
        }

        internal inner class InteceptorChain(val request: HiRequest,val response: HiResponse<T>?):HiInterceptor.Chain{

            override val isRequestPeriod: Boolean
                get() = response == null

            override fun request(): HiRequest {
                return request
            }

            override fun response(): HiResponse<*>? {
                return response
            }

            fun dispatch(){
//                interceptors[0]
            }
        }
    }
}