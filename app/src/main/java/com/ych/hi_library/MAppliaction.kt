package com.ych.hi_library

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.core.os.TraceCompat
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.ych.hi_ability.HiAbility
import com.ych.hilibrary.BuildConfig
import com.ych.hilibrary.log.*
import com.ych.hilibrary.log.HiLogConfig.JsonParser
import com.ych.hilibrary.manager.ActivityManager
import com.ych.hilibrary.util.CrashHandler
import com.ych.hilibrary.util.DateUtil
import com.ych.taskflow.TaskStarUp
import java.util.*

class MAppliaction: Application() {

    override fun attachBaseContext(base: Context?) {
        TraceCompat.beginSection("MAppliaction_attachBaseContext")
        super.attachBaseContext(base)
        TraceCompat.endSection()
        MultiDex.install(this)
    }

    override fun onCreate() {
        TraceCompat.beginSection("MAppliaction_onCreate")

        super.onCreate()

        ActivityManager.instance.init(this)

        //ARouter
        if (BuildConfig.DEBUG) {  // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this) // 尽可能早，推荐在Application中初始化

        //CrashHandler
        CrashHandler

        //UMeng Push, 让他默认使用系统的消息处理方式
        HiAbility.init(this,"Umeng",null)

//        com.ych.hilibrary.manager.ActivityManager.instance.init(this)
        //配置Log信息
        HiLogManager.init(object :HiLogConfig(){
            //全局的Tag
            override fun getGlobalTag(): String {
                return "MAppliaction"
            }
            //是否开启
            override fun enable(): Boolean {
                return true
            }
            //是否引入线程信息
            override fun includeThread(): Boolean {
                return false
            }
            //打印堆栈的深度，如果为0，表示不打印
            override fun stackTraceDepth(): Int {
                return 0
            }
            //配置Json转化器
            override fun injectJsonParser(): JsonParser {
                return JsonParser { src ->  Gson().toJson(src)}
            }

        },
            HiConsolePrinter(),
            HiFilePrinter.getInstance(applicationContext.cacheDir.absolutePath, 0))
//        TaskStarUp.start()
        TraceCompat.endSection()

    }
}