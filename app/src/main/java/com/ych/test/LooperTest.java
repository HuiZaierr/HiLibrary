package com.ych.test;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

public class LooperTest {

    public static void main(String[] args) {

//        LooperThread looperThread = new LooperThread("looper-thread");
        HandlerThread looperThread = new HandlerThread("looper-thread");
        looperThread.start();


        Handler handler = new Handler(looperThread.getLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Log.e("TAG","handleMessage ： " + msg.what);
                Log.e("TAG","handleMessage ： " + Thread.currentThread().getName());
            }
        };
        handler.sendEmptyMessage(10);
    }
}
