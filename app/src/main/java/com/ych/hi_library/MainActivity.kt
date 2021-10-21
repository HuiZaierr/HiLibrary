package com.ych.hi_library

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.TraceCompat
import androidx.fragment.app.DialogFragment
import com.ych.hi_library.logic.MainActivityLogic
import com.ych.hilibrary.aspectj.MethodTrace
import com.ych.hilibrary.design_mode.mvvm.User
import com.ych.hilibrary.log.HiLog
import com.ych.hilibrary.util.FoldableDeviceUtil
import com.ych.hilibrary.util.HiDataBus
import com.ych.hilibrary.util.HiViewUtil
import java.lang.ClassLoader as ClassLoader


class MainActivity : AppCompatActivity(), MainActivityLogic.ActivityProvider{

    @MethodTrace
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        TraceCompat.beginSection("MainActivity_onCreate")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //通过MianActivityLogic来进行处理MainActivity中的代码逻辑。
        var activityLogic = MainActivityLogic(this)
//        //发送一个对象
//        var user = User()
//        user.address = "花基地背离"
//        user.nikeName = "闫长辉"
//        HiDataBus.with<User>("stickyData").setStickyData(user)

        //发送一个字符串
        HiDataBus.with<String>("stickyData").setStickyData("hahahahahahah")

        TraceCompat.endSection()
    }


    override fun onStart() {
        super.onStart()
        HiLog.et("TAG","MainActivity：onStart")
    }

    override fun onResume() {
        super.onResume()
        HiLog.et("TAG","MainActivity：onResume")
    }

    override fun onPause() {
        super.onPause()
        HiLog.et("TAG","MainActivity：onPause")
    }

    override fun onStop() {
        super.onStop()
        HiLog.et("TAG","MainActivity：onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        HiLog.et("TAG","MainActivity：onDestroy")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        HiLog.et("TAG","MainActivity：onConfigurationChanged")
        //是否是正常状态
        if (FoldableDeviceUtil.isFold()){
            HiLog.et("TAG","正常状态")
        }else{
            HiLog.et("TAG","折叠打开状态")
        }

        if (HiViewUtil.isLightMode()){
            Log.e("TAG","是正常模式")
            recreate()
        }else{
            Log.e("TAG","是暗黑模式")
            recreate()
        }
    }

    /**
     * 监听音量键按下
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
            //显示弹窗,只有在Debug的情况下显示
            if (BuildConfig.DEBUG){
                //通过反射获取类
                try {
                    val clazz = Class.forName("com.yc.hidebugtool.DebugToolDialogFragment")
                    val target:DialogFragment = clazz.getConstructor().newInstance() as DialogFragment
                    target.show(supportFragmentManager,"debug_tool")
                }catch (e:ClassNotFoundException){
                    e.printStackTrace()
                }
            }
            val set = HashSet<String>()
            set.add("123")
        }
        return super.onKeyDown(keyCode, event)
    }

    /**
     * 该方法会在View的绘制流程结束后回掉，但是并没有进行网络请求数据，
     * 我们具体的时间可以根据顶部的Banner数据回掉来进行监听
     */
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        TraceCompat.beginSection("MainActivity_onCreate")
        super.onWindowFocusChanged(hasFocus)
        TraceCompat.endSection()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("name","长辉")
        outState.putInt("age",25)
        HiLog.et("TAG","MainActivity：onSaveInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        HiLog.et("TAG","MainActivity：onRestoreInstanceState")
    }
}