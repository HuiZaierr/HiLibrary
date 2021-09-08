package com.ych.hilibrary.util

import android.os.Build
import android.text.TextUtils
import org.w3c.dom.Text

/**
 * TODO：折叠屏适配，判断是否是折叠状态
 *   折叠屏：
 *      1.samsung  Galaxy Z Fold2   最大宽度1768
 *      2.huawei   MateX            最大宽度2200
 *      3.虚拟机   google           最大宽度
 */
object FoldableDeviceUtil {

    private val application = AppGlobals.get()!!

    //官方没有给我们api，我们只能去检测，针对具体机型适配
    fun isFold():Boolean{
        return if (TextUtils.equals(Build.BRAND,"samsung") &&
                TextUtils.equals(Build.DEVICE,"Galaxy Z Fold2")){
            //是否为展开状态，判断宽度samsung Galaxy Z Fold2最大宽度为1768，官方提供的
            return HiDisplayUtil.getDisplayWidthInPx(application)!=1768
        }
        else if (TextUtils.equals(Build.BRAND,"huawei") &&
            TextUtils.equals(Build.DEVICE,"MateX")){
            //是否为展开状态，判断宽度huawei MateX最大宽度为2200，官方提供的
            return HiDisplayUtil.getDisplayWidthInPx(application)!=2200
        }
        else if (TextUtils.equals(Build.BRAND,"google") &&
            TextUtils.equals(Build.DEVICE,"generic_x86")){
                return HiDisplayUtil.getDisplayWidthInPx(application)!=2200
        }else{
            true
        }
    }

}