package com.ych.hi_library.demo.tab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ych.hi_library.R
import com.ych.hiui.tab.bottom.HiTabBottomInfo
import kotlinx.android.synthetic.main.activity_hi_tab_bottom_demo.*

class HiTabBottomDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hi_tab_bottom_demo)

        initTabBottom()
    }

    fun initTabBottom(){
        hitablayout.setTabAlpha(1F)
        //定义一个集合
        val bottomInfoList:MutableList<HiTabBottomInfo<*>> = ArrayList()
        //定义每一个Tab
        val infoHome = HiTabBottomInfo(
            "首页",
            "fonts/iconfont.ttf",
            getString(R.string.if_home),
            null,
            "#FF656667",
            "#FFd44949"
        )

        val infoRecommend = HiTabBottomInfo(
            "收藏",
            "fonts/iconfont.ttf",
            getString(R.string.if_favorite),
            null,
            "#ff656667",
            "#ffd44949"
        )

        val infoChat = HiTabBottomInfo(
            "推荐",
            "fonts/iconfont.ttf",
            getString(R.string.if_recommend),
            null,
            "#ff656667",
            "#ffd44949"
        )
        val infoProfile = HiTabBottomInfo(
            "我的",
            "fonts/iconfont.ttf",
            getString(R.string.if_profile),
            null,
            "#ff656667",
            "#ffd44949"
        )

        bottomInfoList.add(infoHome)
        bottomInfoList.add(infoRecommend)
        bottomInfoList.add(infoChat)
        bottomInfoList.add(infoProfile)
        //进行渲染
        hitablayout.inflateInfo(bottomInfoList)
        hitablayout.addTabSelectedChangeListener { index, prevInfo, nextInfo ->
            Toast.makeText(this, nextInfo.name, Toast.LENGTH_SHORT).show()
        }
        //默认选中哪一个
        hitablayout.defaultSelected(infoHome)
    }
}