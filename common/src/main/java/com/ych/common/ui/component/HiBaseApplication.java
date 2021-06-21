package com.ych.common.ui.component;

import android.app.Application;

public class HiBaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initLog();
    }

    private void initLog() {

    }
}
