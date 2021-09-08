package com.ych.hilibrary.fps

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.net.Uri
import android.nfc.Tag
import android.os.Build
import android.provider.Settings
import android.text.Layout
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import com.ych.hilibrary.R
import com.ych.hilibrary.log.HiLog
import com.ych.hilibrary.util.AppGlobals
import kotlinx.android.synthetic.main.fps_view.view.*
import java.text.DecimalFormat

/**
 * TODO：通过监听页面显示的fps值(每秒的帧数)
 */
object FpsMonitor {
    private val fpsViewer = FpsViewer()
    fun toggle() {
        fpsViewer.toggle()
    }

    fun listener(callback:FpsCallback){
        fpsViewer.addListener(callback)
    }

    interface FpsCallback {
        fun onFrame(fps:Double)
    }

    private class FpsViewer{
        private var params = WindowManager.LayoutParams()
        //是否显示悬浮窗，当应用处于后台是不显示。
        private var isPlaying = false
        private var application  = AppGlobals.get()!!
        private var fpsView = LayoutInflater.from(application).inflate(R.layout.fps_view,null,false) as TextView
        //格式化fps的值
        private val decimal = DecimalFormat("#.0 fps")
        //windowManage对象
        private var windowManager:WindowManager? = null

        private val frameMonitor = FrameMonitor()

        init {
            //获取WindowManager
            windowManager = application.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            params.width = WindowManager.LayoutParams.WRAP_CONTENT
            params.height = WindowManager.LayoutParams.WRAP_CONTENT
            //不可获取焦点,不能点击,不拦截手势
            params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
            //设置透明的窗体
            params.format = PixelFormat.TRANSLUCENT
            //显示在右边中间
            params.gravity = Gravity.RIGHT or Gravity.CENTER
            //适配版本显示问题,版本大于等于8.0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            }else{
                params.type = WindowManager.LayoutParams.TYPE_TOAST
            }

            frameMonitor.addListener(object :FpsMonitor.FpsCallback{
                override fun onFrame(fps: Double) {
                    fpsView.text = decimal.format(fps)
                    HiLog.et("tag","fps的值："+decimal.format(fps))
                }
            })

            //监听前后台切换
            com.ych.hilibrary.manager.ActivityManager.instance.addFrontBackCallback(object:com.ych.hilibrary.manager.ActivityManager.FrontBackCallback{
                override fun onChange(front: Boolean) {
                    if (front){
                        play()
                    }else{
                        stop()
                    }
                }
            })
        }

        /**
         * 处于后台，进行隐藏
         */
        private fun stop() {
            frameMonitor.stop()
            if (isPlaying){
                isPlaying = false
//                windowManager!!.removeView(fpsView)
            }
        }

        /**
         * 处于前台，将进行展示
         */
        private fun play() {
            //注意在Android6.0之后需要显示悬浮窗需要开启权限
//            if (!hasOverlayPermission()){
//                //没有权限，让他跳转到设置开启权限地方
//                startOverlaySettingActivity()
//                HiLog.e("app has no overlay permission")
//                return
//            }

            frameMonitor.start()
            //如果未显示，就让他显示
            if (!isPlaying){
                isPlaying = true
//                windowManager!!.addView(fpsView,params)
            }
        }

//        private fun startOverlaySettingActivity() {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//                application.startActivity(
//                    Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                        Uri.parse("package:${application.packageName}"))
//                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
//            }
//        }

        /**
         * TODO：判断是否已经开始权限
         */
        private fun hasOverlayPermission(): Boolean {
            //判断当前应用是否已经获取权限，如果小于6.0获取已经获取了权限。
            return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.canDrawOverlays(application)
        }

        fun toggle() {
            if (!isPlaying){
                play()
            }else{
                stop()
            }
        }

        fun addListener(callback: FpsMonitor.FpsCallback) {
            frameMonitor.addListener(callback)
        }

    }
}


