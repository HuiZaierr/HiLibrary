package com.ych.hi_library

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.ych.hi_library.logic.MainActivityLogic
import com.ych.hilibrary.util.HiDataBus
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainActivityLogic.ActivityProvider{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //通过MianActivityLogic来进行处理MainActivity中的代码逻辑。
        var activityLogic = MainActivityLogic(this)
        HiDataBus.with<String>("stickyData").setStickyData("hahahahahahah")
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
        }
        return super.onKeyDown(keyCode, event)
    }
}