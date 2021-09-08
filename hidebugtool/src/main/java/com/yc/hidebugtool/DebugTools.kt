package com.yc.hidebugtool

import android.app.ActivityManager
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Process
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.ych.hi_ability.share.ShareBundle
import com.ych.hi_ability.share.ShareManager
import com.ych.hilibrary.fps.FpsMonitor
import com.ych.hilibrary.util.AppGlobals
import com.ych.hilibrary.util.HiViewUtil
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


    @HiDebug(name = "打开/关闭fps",desc = "打开后可以查看页面实时的FPS")
    fun toggleFps(){
        FpsMonitor.toggle()
    }

    @HiDebug(name = "打开/关闭暗黑模式",desc = "打开暗黑模式在夜间使用更温和")
    fun toggleTheme(){
        //如果是正常的时候，打开暗黑，暗黑的时候关闭
        if (HiViewUtil.isLightMode()){
            //打开暗黑
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            //关闭暗黑模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    @HiDebug(name = "分享到QQ好友",desc = "卡片类型分享")
    fun share2QQFriend(){
        val shareBundle = ShareBundle()
        shareBundle.title = ""
        shareBundle.summary = ""
        shareBundle.targetUrl = ""
        shareBundle.thumbUrl = ""
        shareBundle.appName = ""
        shareBundle.appName = ""
        val topActivity = com.ych.hilibrary.manager.ActivityManager.instance.topActivity
        topActivity?.apply { ShareManager.share(topActivity,shareBundle) }
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