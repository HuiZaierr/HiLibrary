package com.ych.hi_library.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ych.hi_library.R
import com.ych.hilibrary.log.HiLog
import com.ych.hilibrary.util.HiDataBus
import kotlinx.android.synthetic.main.activity_hi_log_demo.*

class HiLogDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hi_log_demo)


        HiDataBus.with<String>("stickyData").observerSticky(this,false, Observer {
            Toast.makeText(this, "data from dataBus：${it}", Toast.LENGTH_SHORT).show()
        })
        button.setOnClickListener {
            printLog()
        }
    }

    private fun printLog(){
        HiLog.a("9999")
    }
}