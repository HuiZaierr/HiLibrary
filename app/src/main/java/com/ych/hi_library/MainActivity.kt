package com.ych.hi_library

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.ych.hi_library.demo.HiLogDemoActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_test.text = "跳转"
        tv_test.setOnClickListener {
            startActivity(Intent(this,HiLogDemoActivity::class.java))
        }
    }

    override fun onClick(v: View?) {
//        when(v!!.id){
//            R.id.tv_test -> {
//                Log.e("TAG","11111")
//                startActivity(Intent(this,HiLogDemoActivity::class.java))
//            }
//        }
    }
}