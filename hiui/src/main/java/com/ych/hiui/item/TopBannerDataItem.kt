package com.ych.hiui.item

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ych.hiui.R


class TopBannerDataItem(itemData: ItemData): HiDataItem<ItemData, RecyclerView.ViewHolder>(itemData) {

    override fun onBindData(holder: RecyclerView.ViewHolder, position: Int) {
        var tvName = holder.itemView.findViewById<View>(R.id.tv_name) as TextView
        tvName.text = "TopBannerDataItem 页面"
    }

    override fun getItemLayoutRes(): Int {
        return R.layout.item_top_banner_data
    }
}