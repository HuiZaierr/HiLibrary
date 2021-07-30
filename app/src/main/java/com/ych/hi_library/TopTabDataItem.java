package com.ych.hi_library;

import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.ych.hiui.item.HiDataItem;

import org.jetbrains.annotations.NotNull;

public class TopTabDataItem extends HiDataItem<ItemData,RecyclerView.ViewHolder> {
    private ImageView imageView;

    public TopTabDataItem(ItemData itemData) {
        super(itemData);
    }

    @Override
    public void onBindData(@NotNull RecyclerView.ViewHolder holder, int position) {
        imageView = holder.itemView.findViewById(R.id.item_image);
        imageView.setImageResource(R.drawable.ic_launcher_background);
    }

    @Override
    public int getItemLayoutRes() {
        return R.layout.layout_list_item_top_tab;
    }
}
