package com.ych.hi_library

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import com.ych.hi_library.demo.HiLogDemoActivity
import com.ych.hilibrary.util.HiDataBus
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_test.setOnClickListener(this)

        HiDataBus.with<String>("stickyData").setStickyData("hahahahahahah")
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.tv_test -> {
                startActivity(Intent(this,HiLogDemoActivity::class.java))
            }
        }
    }
}