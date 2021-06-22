package com.ych.hi_library.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ych.hi_library.R
import com.ych.hi_library.databinding.ActivityDataBindingBinding
import com.ych.hilibrary.design_mode.mvvm.HomeViewModel

class DataBindingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_data_binding)

        var binding:ActivityDataBindingBinding = DataBindingUtil.setContentView(this,R.layout.activity_data_binding)
        var model:HomeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        model.userInfo.observe(this, Observer {
            binding.user = it
        })
    }
}