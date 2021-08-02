package com.ych.hi_library

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ych.hiui.item.HiDataItem

class GirdDataItem(data: ItemData): HiDataItem<ItemData, GirdDataItem.MyHolder>(data) {

    override fun onBindData(holder: MyHolder, position: Int) {
        holder.imageView!!.setImageResource(R.drawable.ic_launcher_background)
    }

    override fun getItemLayoutRes(): Int {
        return R.layout.layout_list_item_gird
    }

    inner class MyHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var imageView:ImageView? = null

        init {
            imageView = itemView.findViewById(R.id.item_image)
        }
    }

}