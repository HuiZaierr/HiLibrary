package com.ych.hiui.item

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ych.hiui.R
import java.util.*

class HiDataActivity : AppCompatActivity() {
    private var recyclerView:RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hi_data)

        recyclerView = findViewById<RecyclerView>(R.id.rv)
        recyclerView?.let {
            var hiAdapter = HiAdapter(this)
            it.layoutManager = GridLayoutManager(this,2)
            it.adapter = hiAdapter
            var dataSets: ArrayList<HiDataItem<*, out RecyclerView.ViewHolder>> = ArrayList()
            dataSets.add(TopTabDataItem(ItemData()))
            dataSets.add(TopBannerDataItem(ItemData()))
            dataSets.add(TopGridDataItem(ItemData()))
            hiAdapter.addItems(dataSets,false)
        }
    }
}