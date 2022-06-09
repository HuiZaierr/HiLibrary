package com.ych.hiui.item

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ych.hiui.R


class TopGridDataItem(data: ItemData):HiDataItem<ItemData,TopGridDataItem.MyHolder>(data) {


    override fun onBindData(holder: MyHolder, position: Int) {
        holder.tvName?.text = "TopGridDataItem 页面"
        holder
    }

    override fun getItemLayoutRes(): Int {
        return R.layout.item_top_grid_data
    }

    class MyHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
        var tvName:TextView?=null

        init {
            tvName = itemView.findViewById(R.id.tv_name)
            tvName?.setOnClickListener {

            }
        }
    }
}