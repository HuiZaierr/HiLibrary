package com.ych.hilibrary.restful.annotation

/**
 * @POST("/xxx/xxxx/")
 * fun test(@Filed("province") int provinceId)
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
/**
 * formPost：表示是否使用表单提交FormUrlEncoded
 */
annotation class POST(val value:String,val formPost:Boolean = true)