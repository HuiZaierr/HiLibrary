package com.ych.hi_library.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.ych.hi_library.R
import com.ych.hilibrary.log.*
import com.ych.hilibrary.util.HiDataBus
import kotlinx.android.synthetic.main.activity_hi_log_demo.*
import kotlinx.coroutines.launch

class HiLogDemoActivity : AppCompatActivity() {
    var viewPrinter:HiViewPrinter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hi_log_demo)
        viewPrinter = HiViewPrinter(this)

        HiDataBus.with<String>("stickyData").observerSticky(this,true, Observer {
            Toast.makeText(this, "data from dataBus：${it}", Toast.LENGTH_SHORT).show()
        })
        button.setOnClickListener {
            printLog()
        }
        viewPrinter!!.viewProvider.showFloatingView()
    }

    private fun printLog(){
        HiLogManager.getInstance().addPrinter(viewPrinter)
        HiLog.log(object: HiLogConfig(){
            override fun includeThread(): Boolean {
                return true
            }

            override fun stackTraceDepth(): Int {
                return 0
            }
        },HiLogType.E,"-----","5566778899")

        HiLog.et("HAHAHAHAHA","8877665544")
        HiLog.a("9999")
    }
}