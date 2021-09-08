package com.ych.hi_ability

import android.app.Application
import com.ych.hi_ability.push.IPushMessageHandler
import com.ych.hi_ability.push.PushInitlalization

object HiAbility {

    fun init(application: Application,
             channel:String,
             iPushMessageHandler: IPushMessageHandler? = null){
        PushInitlalization.init(application,channel,iPushMessageHandler)
    }
}