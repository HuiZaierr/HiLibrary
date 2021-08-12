package com.ych.hi_library.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import com.ych.hi_library.R
import com.ych.hi_library.databinding.ActivityDataBindingBinding
import com.ych.hilibrary.design_mode.mvvm.HomeViewModel
import com.ych.hilibrary.log.HiLog
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DataBindingActivity : AppCompatActivity() {
    private val TAG:String = "DataBindingActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_data_binding)

        var binding:ActivityDataBindingBinding = DataBindingUtil.setContentView(this,R.layout.activity_data_binding)
        var model:HomeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        model.userInfo.observe(this, Observer {
            binding.user = it
        })

        lifecycleScope.launch {

        }

        lifecycleScope.async {

        }

        //当我们宿主的声明周期至少为onCreate的时候才会启动
        lifecycleScope.launchWhenCreated {
            HiLog.e(TAG,"launchWhenCreated")
            whenCreated {
                //这里的代码 只有宿主的声明周期为onCreate才会执行，否则暂停
                HiLog.e(TAG,"whenCreated")
            }
            whenResumed {
                //这里的代码 只有宿主的声明周期为onResume才会执行，否则暂停
                HiLog.e(TAG,"whenResumed")
            }
            whenStarted {
                //这里的代码 只有宿主的声明周期为onStrart才会执行，否则暂停
                HiLog.e(TAG,"whenStarted")
            }
        }

        //当我们宿主的声明周期至少为onStart的时候才会启动
        lifecycleScope.launchWhenStarted {

        }

    }
}