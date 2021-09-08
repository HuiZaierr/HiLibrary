package com.ych.hilibrary.aspectj

/**
 * TODO：Target作用在哪里，FUNCTION方法，CONSTRUCTOR构造方法
 *      Retention什么时候有效：编译运行期
 */
@Target(AnnotationTarget.FUNCTION,AnnotationTarget.CONSTRUCTOR)
@Retention(AnnotationRetention.RUNTIME)
annotation class MethodTrace {
}
