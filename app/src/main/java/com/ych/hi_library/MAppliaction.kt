package com.ych.hi_library

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.ych.hilibrary.log.HiConsolePrinter
import com.ych.hilibrary.log.HiFilePrinter
import com.ych.hilibrary.log.HiLogConfig
import com.ych.hilibrary.log.HiLogManager

class MAppliaction: Application() {

    override fun onCreate() {
        super.onCreate()
        //ARouter
        if (BuildConfig.DEBUG) {  // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this) // 尽可能早，推荐在Application中初始化

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
                return 5
            }
            //配置Json转化器
            override fun injectJsonParser(): JsonParser {
                return JsonParser { src ->  Gson().toJson(src)}
            }

        },
            HiConsolePrinter(),
            HiFilePrinter.getInstance(applicationContext.cacheDir.absolutePath, 0))
    }
}