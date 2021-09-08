package com.ych.hi_ability.share

import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.text.TextUtils
import android.widget.Toast
import com.ych.hilibrary.log.HiLog

object ShareManager {
    const val CHANNEL_QQ_FRIEND: String = "com.tencent.mobileqq.activity.JumpActivity"

    /**
     * TODO：
     *   channelName:List<String>是调用者想要分享面板展示出来的渠道
     *   但是此时，某些渠道（QQ）就没有安装，那么我们需要查找本机已安装的应用列表
     *   和channels做个过滤
     */
    fun share(context: Context,shareBundle: ShareBundle){
        //1.获取本地的应用渠道，方法中我们只收集了微信和QQ的
        val localChannels = queryLocalChannel(context, shareBundle.type)
        val pm = context.packageManager
        val shareChannels = arrayListOf<ResolveInfo>()
        if (shareBundle.channels != null) {
            //2.通过和调用者想要使用的进行筛选。相同的显示，不同的不显示
            for (localChannel in localChannels) {
                //获取渠道的名称
                val loadLabel = localChannel.loadLabel(pm)
                for (channel in shareBundle.channels!!) {
                    if (TextUtils.equals(channel, loadLabel)) {
                        shareChannels.add(localChannel)
                    }
                }
            }
        } else {
            shareChannels.addAll(localChannels)
        }

        val shareDialog = ShareDialog(context)
        shareDialog.setChannels(shareChannels, object : ShareDialog.onShareChannelClickListener {
            override fun onClickChannel(resolveInfo: ResolveInfo) {
                //点击分享框时
                HiLog.e("ShareManager","${resolveInfo.loadLabel(pm)}")
                share2Channel(context, resolveInfo, shareBundle)
            }
        })
        shareDialog.show()
    }


    private fun share2Channel(
        context: Context,
        resolveInfo: ResolveInfo,
        shareBundle: ShareBundle
    ) {
        val activityName = resolveInfo.activityInfo.name
        when(activityName){
            CHANNEL_QQ_FRIEND ->{
                //QQ朋友圈
            }
            else->showToast(context, "微信分享请查看feature/wx_share分支代码")
        }

    }

    /**
     * TODO：查询本地的可分享的应用
     *      通过设置type类型来决定是，获取手机什么类型的应用
     *      1.text/plain   只接受文本分享的渠道
     *      2.images/plain 只接受图片分享的渠道
     *      3.*斜杠*（不能打/，和下边冲突，通过文字代替）  查询所有类型的
     */
    private fun queryLocalChannel(context: Context,shareType:String):List<ResolveInfo>{
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.type = shareType

        var resolveInfos = arrayListOf<ResolveInfo>()

        //可分享数据的内容,根据类型来获取
        val pm = context.packageManager
        //查询所有渠道
        val activitys = pm.queryIntentActivities(intent, 0)
        //将渠道进行过滤，比如为朋友圈的，QQ的等等
        for (activity in activitys){
            val packageName = activity.activityInfo.packageName
            //判断是否为微信和qq的
            if (TextUtils.equals(packageName,"com.tencent.mm") ||
                TextUtils.equals(packageName,"com.tencent.mobileqq")){
                resolveInfos.add(activity)
            }
        }

        return resolveInfos
    }

    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}