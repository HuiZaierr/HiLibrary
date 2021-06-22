package com.ych.hi_library

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ych.hi_library.demo.DataBindingActivity
import com.ych.hi_library.demo.HiLogDemoActivity
import com.ych.hi_library.demo.tab.HiTabBottomDemoActivity
import com.ych.hi_library.logic.MainActivityLogic
import com.ych.hilibrary.util.HiDataBus
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainActivityLogic.ActivityProvider,View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var activityLogic = MainActivityLogic(this)


//        btnHiLog.setOnClickListener(this)
//        btnHiTabBottom.setOnClickListener(this)
//        btnDataBind.setOnClickListener(this)
        HiDataBus.with<String>("stickyData").setStickyData("hahahahahahah")
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btnHiLog -> {
                startActivity(Intent(this,HiLogDemoActivity::class.java))
            }
            R.id.btnHiTabBottom -> {
                startActivity(Intent(this,HiTabBottomDemoActivity::class.java))
            }
            R.id.btnDataBind ->{
                startActivity(Intent(this,DataBindingActivity::class.java))
            }
        }
    }
}