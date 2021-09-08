package com.ych.hi_ability.push

import android.content.Context
import com.google.gson.JsonObject

/**
 * TODO：使用JsonObject就是为了解决当使用其他三方推送时，消息错误类型。统一为处理为JsonObject
 */

interface IPushMessageHandler {
    /**
     * 当用户点击通知栏或者自定义消息
     */
    fun dealWithCustomAction(context: Context?, message: JsonObject?){

    }

    /**
     * TODO：自己解析自定义消息格式，弹窗，notification，上传log文件
     */
    fun dealWithCustomMessage(context: Context?, jsonObject: JsonObject){

    }

    /**
     * TODO：自己解析通知栏消息格式
     */
    fun dealWithNotificationMessage(context: Context?, jsonObject: JsonObject){

    }

    /**
     * TODO：向UMeng注册推送服务成功，拿到devicetoken
     */
    fun onRegisterSuccess(deviceToken: String?) {

    }

    /**
     * TODO：注册成功
     */
    fun onFailure(p0: String?, p1: String?) {

    }
}