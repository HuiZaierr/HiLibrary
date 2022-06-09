package com.ych.hiui.item;


import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.ych.hiui.R;

import org.jetbrains.annotations.NotNull;

public class TopTabDataItem extends HiDataItem<ItemData, RecyclerView.ViewHolder> {

    public TopTabDataItem(ItemData itemData) {
        super(itemData);
    }

    @Override
    public void onBindData(@NotNull RecyclerView.ViewHolder holder, int position) {
        TextView tvName = holder.itemView.findViewById(R.id.tv_name);
        tvName.setText("TopTabDataItem 页面");
    }

    @Override
    public int getItemLayoutRes() {
        return R.layout.item_top_tab_data;
    }
}
