package com.ych.hilibrary.restful.annotation

/**
 * @GET("/xxx/xxxx/{province}")
 * fun test(@Path("province") int provinceId)
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Path(val value:String)