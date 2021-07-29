package com.ych.hi_library;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;



import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {
    private ArrayList<String> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        TextView textView = findViewById(R.id.tv_text);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        for (int i = 0; i < 20; i++) {
            list.add(String.valueOf(i));
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        linearLayoutManager.setReverseLayout(true); //表示反转布局，从下到上
        linearLayoutManager.setStackFromEnd(true);  //表示正常布局，但是布局后会自动滚动到最后一个item位置
        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setAdapter();
    }
}