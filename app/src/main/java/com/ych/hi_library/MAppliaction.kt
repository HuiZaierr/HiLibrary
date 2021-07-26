package com.ych.hi_library

import android.app.Application
import com.google.gson.Gson
import com.ych.hilibrary.log.HiConsolePrinter
import com.ych.hilibrary.log.HiFilePrinter
import com.ych.hilibrary.log.HiLogConfig
import com.ych.hilibrary.log.HiLogManager

class MAppliaction: Application() {

    override fun onCreate() {
        super.onCreate()
        com.ych.hilibrary.manager.ActivityManager.instance.init(this)
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
                return true
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