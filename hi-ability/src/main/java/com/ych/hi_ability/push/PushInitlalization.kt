package com.ych.hi_ability.push

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import com.google.gson.JsonObject
import com.umeng.commonsdk.UMConfigure
import com.umeng.message.IUmengRegisterCallback
import com.umeng.message.PushAgent
import com.umeng.message.UmengMessageHandler
import com.umeng.message.UmengNotificationClickHandler
import com.umeng.message.entity.UMessage
import com.ych.hi_ability.BuildConfig
import com.ych.hilibrary.log.HiLog

object PushInitlalization {
    const val TAG = "PushInitlalization"
    fun init(application: Application,channel:String,iPushMessageHandler: IPushMessageHandler? = null){
        //1.初始化友盟Push SDK
        initUmenPushSdk(application,channel,iPushMessageHandler)
        //2.初始化厂商的Push
        initOEMPushSdk()
    }

    private fun initUmenPushSdk(application: Application,channel:String,iPushMessageHandler: IPushMessageHandler?=null) {
        // 在此处调用基础组件包提供的初始化函数 相应信息可在应用管理 -> 应用信息 中找到 http://message.umeng.com/list/apps
        // 参数一：当前上下文context；
        // 参数二：应用申请的Appkey；
        // 参数三：渠道名称；
        // 参数四：设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机；
        // 参数五：Push推送业务的secret 填充Umeng Message Secret对应信息
        UMConfigure.init(
            application,
            BuildConfig.UMENG_API_KEY,
            channel,
            UMConfigure.DEVICE_TYPE_PHONE,
            BuildConfig.UMENG_MESSAGE_SECRET
        )

        //获取消息推送实例
        val pushAgent = PushAgent.getInstance(application)
        //注册推送服务，每次调用register方法都会回调该接口
        pushAgent.register(object : IUmengRegisterCallback{
            override fun onSuccess(deviceToken: String?) {
                HiLog.e(TAG,"UMengPush注册成功")
                iPushMessageHandler?.onRegisterSuccess(deviceToken)
            }

            override fun onFailure(errCode: String?, errDesc: String?) {
                HiLog.e(TAG,"UMengPush注册失败-------$errCode ---------$errDesc")
                iPushMessageHandler?.onFailure(errCode,errDesc)
            }
        })

        //自定义行为，通知栏
        pushAgent.messageHandler = object: UmengMessageHandler() {
            //通知栏消息解析
            override fun dealWithNotificationMessage(p0: Context?, p1: UMessage?) {
                if (iPushMessageHandler!=null){
                    //需要自己按照应用约定的数据结构，去解析UMessage,将其转换为JsonObject
                    iPushMessageHandler.dealWithNotificationMessage(p0, JsonObject())
                }else{
                    super.dealWithNotificationMessage(p0, p1)

                }
            }

            //自定义消息解析
            override fun dealWithCustomMessage(p0: Context?, p1: UMessage?) {
                if (iPushMessageHandler!=null){
                    //需要自己按照应用约定的数据结构，去解析UMessage,将其转换为JsonObject
                    iPushMessageHandler.dealWithCustomMessage(p0, JsonObject())
                }else{
                    super.dealWithCustomMessage(p0, p1)

                }
            }
        }

        //通知栏自定义消息的点击
        pushAgent.notificationClickHandler = object : UmengNotificationClickHandler() {
            override fun dealWithCustomAction(p0: Context?, p1: UMessage?) {
                if (iPushMessageHandler!=null){
                    //需要自己按照应用约定的数据结构，去解析UMessage,将其转换为JsonObject
                    iPushMessageHandler.dealWithCustomAction(p0, JsonObject())
                }else{
                    super.dealWithCustomAction(p0, p1)
                }
            }
        }

        //监听应用的Activity的声明周期，为了统计应用的推送日活
        application.registerActivityLifecycleCallbacks(object :
            SimpleLifecycleCallbcks() {
            override fun onActivityPreCreated(activity: Activity, savedInstanceState: Bundle?) {
                super.onActivityPreCreated(activity, savedInstanceState)
                PushAgent.getInstance(application).onAppStart()
            }
        })


    }

    private fun initOEMPushSdk() {


    }

}