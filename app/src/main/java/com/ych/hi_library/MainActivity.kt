package com.ych.hi_library

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ych.hi_library.logic.MainActivityLogic
import com.ych.hilibrary.util.HiDataBus
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainActivityLogic.ActivityProvider{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //通过MianActivityLogic来进行处理MainActivity中的代码逻辑。
        var activityLogic = MainActivityLogic(this)
        HiDataBus.with<String>("stickyData").setStickyData("hahahahahahah")
    }
}