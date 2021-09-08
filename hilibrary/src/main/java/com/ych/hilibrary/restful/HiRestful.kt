package com.ych.hilibrary.restful

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.util.concurrent.ConcurrentHashMap


/**
 * TODO：构建网络请求
 *    参数1：全局的url
 *    参数2：用于构建我们的Call对象。通过传入不同类型的实现类。可以为Retrofit的  或者 OkHttp的
 */
open class HiRestful constructor(val baseUrl:String,val callFactory: HiCall.Factory) {
    private var interceptors:MutableList<HiInterceptor> = mutableListOf()
    private var methodService:ConcurrentHashMap<Method,MethodParser> = ConcurrentHashMap()

    /**
     * TODO：添加拦截器
     */
    fun addInterceptor(interceptor: HiInterceptor){
        interceptors.add(interceptor)
    }

    fun <T> create(service:Class<T>):T{
        return Proxy.newProxyInstance(
            service.classLoader,
            arrayOf<Class<*>>(service)){
            proxy, method, args ->

            var methodParser  = methodService.get(method)
            if (methodParser == null){
                methodParser = MethodParser.parse(baseUrl, method, args)
                methodService.put(method,methodParser)
            }
            val request = methodParser.newRequest()
            callFactory.newCall(request)


        } as T
    }

}