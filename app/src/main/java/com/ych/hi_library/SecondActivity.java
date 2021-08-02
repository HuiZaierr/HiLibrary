package com.ych.hi_library;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


import com.ych.hiui.item.HiAdapter;
import com.ych.hiui.item.HiDataItem;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {
    private ArrayList<String> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
//        TextView textView = findViewById(R.id.tv_text);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

//        for (int i = 0; i < 20; i++) {
//            list.add(String.valueOf(i));
//        }
//        GridLayoutManager linearLayoutManager = new GridLayoutManager(this,2);
//        linearLayoutManager.setReverseLayout(true); //表示反转布局，从下到上
//        linearLayoutManager.setStackFromEnd(true);  //表示正常布局，但是布局后会自动滚动到最后一个item位置
//        recyclerView.setLayoutManager(linearLayoutManager);
////        recyclerView.setAdapter();
        HiAdapter hiAdapter = new HiAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(hiAdapter);

        List<? super HiDataItem<ItemData,? extends RecyclerView.ViewHolder>> dataSets = new ArrayList();
        dataSets.add(new TopBanner(new ItemData()));
        dataSets.add(new TopTabDataItem(new ItemData()));
        dataSets.add(new GirdDataItem(new ItemData()));

//        hiAdapter.addItems(dataSets,false);
    }
}