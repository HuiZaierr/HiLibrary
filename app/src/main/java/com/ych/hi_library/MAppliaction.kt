package com.ych.hi_library

import android.app.Application
import com.ych.hilibrary.log.HiLogConfig
import com.ych.hilibrary.log.HiLogManager

class MAppliaction: Application() {

    override fun onCreate() {
        super.onCreate()
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
        })
    }
}