package com.ych.hi_library.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ych.hi_library.R
import com.ych.hilibrary.log.HiLog
import kotlinx.android.synthetic.main.activity_hi_log_demo.*

class HiLogDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hi_log_demo)

        button.setOnClickListener {
            printLog()
        }
    }

    private fun printLog(){
        HiLog.a("9999")
    }
}