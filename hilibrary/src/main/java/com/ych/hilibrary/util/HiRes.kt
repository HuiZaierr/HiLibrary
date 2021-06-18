package com.ych.hilibrary.util

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

/**
 * TODO:获取资源信息工具类。
 *    strings,drawable下的资源
 */
class HiRes private constructor(){
    //单例
    companion object{
        val INSTANCE = HiResProvide.holder
    }

    private object HiResProvide{
        val holder = HiRes()
    }

    /**
     * 获取字符资源
     */
    fun getString(@StringRes id:Int):String{
        return context().getString(id)
    }

    /**
     * 获取字符资源
     * vararg修饰符表示，多个参数。
     * *号表示打散。传入一个一个的字符。
     */
    fun getString(@StringRes id:Int,vararg formatArgs:Any?):String{
        return context().getString(id,*formatArgs)
    }

    /**
     * 获取颜色资源
     */
    fun getColor(@ColorRes id:Int): Int {
        return ContextCompat.getColor(context(),id)
    }

    /**
     * 获取资源图片
     */
    fun getDrawable(@DrawableRes drawableId:Int):Drawable?{
        return ContextCompat.getDrawable(context(),drawableId)
    }

    fun context() :Context{
        return AppGlobals.get() as Context
    }
}