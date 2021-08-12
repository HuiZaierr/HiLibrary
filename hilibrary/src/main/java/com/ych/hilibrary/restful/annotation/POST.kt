package com.ych.hilibrary.restful.annotation

/**
 * @POST("/xxx/xxxx/")
 * fun test(@Filed("province") int provinceId)
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class POST(val value:String,val formPost:Boolean = true)