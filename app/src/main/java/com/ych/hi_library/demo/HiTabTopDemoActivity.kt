package com.ych.hi_library.demo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.ych.hi_library.R
import com.ych.hiui.tab.top.HiTabTopInfo
import kotlinx.android.synthetic.main.activity_hi_tab_top_demo.*


class HiTabTopDemoActivity : AppCompatActivity() {
    val tabsStr = arrayOf(
        "热门",
        "服装",
        "数码",
        "鞋子",
        "零食",
        "家电",
        "汽车",
        "百货",
        "家居",
        "装修",
        "运动"
    )

    private var binding: ViewDataBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_hi_tab_top_demo)
         binding = DataBindingUtil.setContentView(this,R.layout.activity_hi_tab_top_demo);
        initTabTop()
    }

    fun initTabTop(){
        var infoList: MutableList<HiTabTopInfo<*>> = ArrayList()
        val defaultColor = resources.getColor(R.color.tabBottomDefaultColor)
        val tintColor = resources.getColor(R.color.tabBottomTintColor)

        for (str in tabsStr){
            var info = HiTabTopInfo(str,defaultColor,tintColor)
            infoList.add(info)
        }
        tab_top.inflateInfo(infoList)
        tab_top.addTabSelectedChangeListener { index, prevInfo, nextInfo ->
            Toast.makeText(application, nextInfo.name, Toast.LENGTH_SHORT).show()
        }
        tab_top.defaultSelected(infoList[0])
    }
}