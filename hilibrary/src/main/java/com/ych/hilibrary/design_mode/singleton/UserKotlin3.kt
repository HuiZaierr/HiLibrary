package com.ych.hilibrary.design_mode.singleton

/**
 * TODO：Kotlin静态内部类实现单例（建议使用）
 *      好处：外部类加载并不会加载内部类。因此不会占用我们空间
 */
class UserKotlin3 private constructor(){

    companion object{
        val mInstance = UserKotlin3Holder.holder
    }

    private object UserKotlin3Holder{
        val holder = UserKotlin3()
    }

}