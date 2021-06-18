package com.ych.hi_library.demo.tab

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ych.hi_library.R
import com.ych.hilibrary.util.HiDisplayUtil
import com.ych.hiui.tab.bottom.HiTabBottomInfo
import kotlinx.android.synthetic.main.activity_hi_tab_bottom_demo.*

class HiTabBottomDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hi_tab_bottom_demo)

        initTabBottom()
    }

    fun initTabBottom(){
        //定义一个集合
        val bottomInfoList:MutableList<HiTabBottomInfo<*>> = ArrayList()
        //定义每一个Tab
        val infoHome = HiTabBottomInfo(
            "首页",
            BitmapFactory.decodeResource(resources,R.mipmap.ic_maintab0_white_24dp_np,null),
            BitmapFactory.decodeResource(resources,R.mipmap.ic_maintab0_white_24dp,null),
            "#FF656667",
            "#FFd44949"
        )

        val infoRecommend = HiTabBottomInfo(
            "收藏",
            BitmapFactory.decodeResource(resources,R.mipmap.ic_maintab1_white_24dp_np,null),
            BitmapFactory.decodeResource(resources,R.mipmap.ic_maintab1_white_24dp,null),
            "#ff656667",
            "#ffd44949"
        )

        val infoLuckDraw = HiTabBottomInfo<String>(
            "抽奖",
            BitmapFactory.decodeResource(resources,R.mipmap.fire,null),
            BitmapFactory.decodeResource(resources,R.mipmap.fire,null)
        )

        val infoChat = HiTabBottomInfo(
            "推荐",
            BitmapFactory.decodeResource(resources,R.mipmap.ic_maintab2_white_24dp_np,null),
            BitmapFactory.decodeResource(resources,R.mipmap.ic_maintab2_white_24dp,null),
            "#ff656667",
            "#ffd44949"
        )
        val infoProfile = HiTabBottomInfo(
            "我的",
            BitmapFactory.decodeResource(resources,R.mipmap.ic_maintab3_white_24dp_np,null),
            BitmapFactory.decodeResource(resources,R.mipmap.ic_maintab3_white_24dp,null),
            "#ff656667",
            "#ffd44949"
        )

        bottomInfoList.add(infoHome)
        bottomInfoList.add(infoRecommend)
        bottomInfoList.add(infoLuckDraw)
        bottomInfoList.add(infoChat)
        bottomInfoList.add(infoProfile)
        //进行渲染
        hitablayout.inflateInfo(bottomInfoList)
        hitablayout.addTabSelectedChangeListener { index, prevInfo, nextInfo ->
            Toast.makeText(this, nextInfo.name, Toast.LENGTH_SHORT).show()
        }
        //默认选中哪一个
        hitablayout.defaultSelected(infoHome)
        //改变某个tab的高度
        val tabBottom = hitablayout.findTab(bottomInfoList[2])
        tabBottom?.apply { resetHeight(HiDisplayUtil.dp2px(66f, resources)) }
    }
}