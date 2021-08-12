package com.ych.hilibrary.restful.annotation


/**
 * TODO：Tatget表示作用范围，可以为类，方法，属性等等。
 *      Retention表示有效范围
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class BaseUrl(val value:String) {}