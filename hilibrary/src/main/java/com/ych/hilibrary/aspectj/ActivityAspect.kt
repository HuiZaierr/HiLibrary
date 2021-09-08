package com.ych.hilibrary.aspectj

import com.ych.hilibrary.log.HiLog
//import org.aspectj.lang.ProceedingJoinPoint
//import org.aspectj.lang.annotation.Around
//import org.aspectj.lang.annotation.Aspect

/**
 * TODO：通过AspectJ面向切面来为其方法添加耗时。
 *    第一种：直接为某个方法在编译时期插入耗时任务的代码
 *    第二种：通过注解的方法，来进行在编译时期插入耗时任务的代码。
 */
//@Aspect
class ActivityAspect {

//    private val TAG: String? = "ActivityAspect"
//
//    /**
//     * TODO:第一种：
//     *      注解：（Before方法前插入，After方法后插入，Around方法前后插入）
//     *      访问权限：（call，execution，set，get）
//     *      返回值的类型：（Object，Int，*）
//     *      包名.函数名(参数)
//     *      示例：@Around(value = "execution(* android.app.Activity.setContentView(..))")
//     *     重点：如果使用的注解是Before或者After，那么参数只能是JoinPoint，Around的话就是ProceedingJoinPoint
//     */
//    @Around(value = "execution(* android.app.Activity.setContentView(..))")
//    fun setContentView(joinPoint:ProceedingJoinPoint){
//        //获取方法的签名
//        val signature  = joinPoint.signature
//        //获取当前类的名称
//        val className = signature.declaringType.simpleName
//        //获取当前方法的名称
//        val methodName = signature.name
//
//        //在所有Activity的setContentView前面添加时间
//        val time  = System.currentTimeMillis()
//        //让其方法继续执行
//        joinPoint.proceed()
//        //在所有Activity的setContentView方法结尾获取结束时间
//        HiLog.et(TAG,"$className  $methodName cost：${System.currentTimeMillis() - time}")
//    }
//
//    /**
//     * 提取注入的代码
//     */
//    fun adviceCode(joinPoint:ProceedingJoinPoint){
//        //获取方法的签名
//        val signature  = joinPoint.signature
//        //获取当前类的名称
//        val className = signature.declaringType.simpleName
//        //获取当前方法的名称
//        val methodName = signature.name
//
//        //在所有Activity的setContentView前面添加时间
//        val time  = System.currentTimeMillis()
//        //让其方法继续执行
//        joinPoint.proceed()
//        //在所有Activity的setContentView方法结尾获取结束时间
//        HiLog.et(TAG,"$className  $methodName cost：${System.currentTimeMillis() - time}")
//    }
//
//
//    @Around("execution(@com.ych.hilibrary.aspectj.MethodTrace * *(..))")
//    fun methodTrace(joinPoint:ProceedingJoinPoint){
//        adviceCode(joinPoint)
//    }

}