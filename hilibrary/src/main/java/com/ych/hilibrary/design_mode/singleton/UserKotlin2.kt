package com.ych.hilibrary.design_mode.singleton

/**
 * TODO：Kotlin的懒汉式单例（双重校验）
 *      1.在我们使用时才会创建
 *      2.主构造方法私有化
 */
class UserKotlin2 private constructor(){
    //使用Kotlin的伴生对象
    companion object{
        val INSTANCE:UserKotlin2 by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED){
            UserKotlin2()
        }
    }
}

