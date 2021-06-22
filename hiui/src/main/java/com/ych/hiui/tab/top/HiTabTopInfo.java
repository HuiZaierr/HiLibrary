package com.ych.hiui.tab.top;

import android.graphics.Bitmap;

import androidx.fragment.app.Fragment;

public class HiTabTopInfo<Color>{
    //图片和文本
    public enum TabType {
        BITMAP, TEXT
    }

    public Class<? extends Fragment> fragment;
    public String name;
    //只有图片的Tab
    public Bitmap defaultBitmap;
    public Bitmap selectedBitmap;
    public Color defaultColor;
    public Color tintColor;
    public TabType tabType;

    public HiTabTopInfo(String name, Bitmap defaultBitmap, Bitmap selectedBitmap) {
        this.name = name;
        this.defaultBitmap = defaultBitmap;
        this.selectedBitmap = selectedBitmap;
        this.tabType = TabType.BITMAP;
    }

    public HiTabTopInfo(String name, Color defaultColor, Color tintColor) {
        this.name = name;
        this.defaultColor = defaultColor;
        this.tintColor = tintColor;
        this.tabType = TabType.TEXT;
    }
}
