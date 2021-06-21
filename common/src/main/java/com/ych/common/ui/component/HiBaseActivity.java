package com.ych.common.ui.component;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HiBaseActivity extends AppCompatActivity implements HiBaseActionInterface{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void showToast(String msg){
        if (!TextUtils.isEmpty(msg)){
            Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
        }
    }
}
