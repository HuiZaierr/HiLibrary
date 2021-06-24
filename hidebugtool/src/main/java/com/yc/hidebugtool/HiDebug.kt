package com.yc.hidebugtool

/**
 * @Target：表示作用于哪里。FUNCTION方法
 * @Retention：作用于什么时候，RUNTIME运行时期
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class HiDebug(val name:String,val desc:String)