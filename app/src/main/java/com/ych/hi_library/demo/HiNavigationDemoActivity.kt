package com.ych.hi_library.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ych.hi_library.R

class HiNavigationDemoActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hi_navigation_demo)

        val navView:BottomNavigationView = findViewById(R.id.nav_view)

        //寻找出路由控制器对象，他是我们路由跳转的唯一入口
        var navController = findNavController(R.id.nav_host_fragment)
        //将navController和BottomNavigationView绑定，形成联动效果
        navView.setupWithNavController(navController)
    }


}