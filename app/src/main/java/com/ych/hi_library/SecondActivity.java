package com.ych.hi_library;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.ych.hilibrary.manager.ActivityManager;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        TextView textView = findViewById(R.id.tv_text);

       Activity activity = ActivityManager.Companion.getInstance().getTopActivity();
       if (activity!=null){
           textView.setText(activity.getLocalClassName());
       }

    }
}