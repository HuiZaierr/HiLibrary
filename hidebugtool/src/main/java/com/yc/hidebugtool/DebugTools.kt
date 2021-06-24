package com.yc.hidebugtool

import android.content.Intent
import android.os.Build
import android.os.Process
import com.ych.hilibrary.util.AppGlobals
import com.ych.hilibrary.util.SPUtil

class DebugTools{

    /**
     * 构建版本
     */
    fun buildVersion():String{
        //构建版本1.0.1
        return "构建版本：${BuildConfig.VERSION_NAME}.${BuildConfig.VERSION_CODE}"
    }

    /**
     * 构建时间
     *  并不能直接new Date，他是返回的编译的时间，而我们需要的是打包后的时间
     */
    fun buildTime():String{
        return "构建时间：${BuildConfig.BUILD_TIME}"
    }

    /**
     * 构建环境
     */
    fun buildEnvironment():String{
        return "构建环境：${if (BuildConfig.DEBUG) "测试环境" else "正式环境"}"
    }

    /**
     * 构建设备信息
     */
    fun buildDevice():String{
        return "设备信息：${Build.BRAND}-${Build.VERSION.SDK_INT}-${Build.CPU_ABI}"
    }

    /**
     * 使Https降级为Http
     */
    @HiDebug(name = "一键开启Https降级",desc = "将继承Http，可以使用抓包工具文明抓包")
    fun degrade2Http(){
        SPUtil.putBoolean("degrade_http",true)
        val context = AppGlobals.get()?.applicationContext?:return
        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        //得到了 启动页的intent
        intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)

        //杀掉当前进行，并主动启动新的启动页，以完成重启的动作
        Process.killProcess(Process.myPid())
    }

}