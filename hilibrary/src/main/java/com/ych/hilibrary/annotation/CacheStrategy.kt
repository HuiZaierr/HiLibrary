package com.ych.hilibrary.annotation

/**
 * TODO：使用注解来实现缓存策略
 *   @Target()：表示注解用于哪里
 *      AnnotationTarget.FUNCTION：用于函数上
 *      AnnotationTarget.VALUE_PARAMETER：用于参数
 *   @Retention()：表示什么时候起作用
 *      AnnotationRetention.RUNTIME：表示运行期
 */
@Target(AnnotationTarget.FUNCTION,AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class CacheStrategy(val value:Int = NET_ONLY){

    companion object{
        const val CACHE_FIRST = 0 //先读取本地缓存，在请求接口，接口成功后更新缓存(页面初始化数据)
        const val NET_ONLY = 1    //只请求接口（一般是分页和独立非列表页）
        const val NET_CACHE = 2   //先请求接口。接口成功后更新缓存（一般是下拉刷新）
    }

}