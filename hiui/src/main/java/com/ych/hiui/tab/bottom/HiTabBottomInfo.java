package com.ych.hiui.tab.bottom;

import android.graphics.Bitmap;

import androidx.fragment.app.Fragment;

public class HiTabBottomInfo<Color>{
    public enum TabType {
        BITMAP, ICON
    }

    public Class<? extends Fragment> fragment;
    public String name;
    //只有图片的Tab
    public Bitmap defaultBitmap;
    public Bitmap selectedBitmap;
    /**
     * Tips：图片 下边有文字的图片
     */
    public Bitmap defaultIcon;
    public Bitmap selectedIcon;
    public Color defaultColor;
    public Color tintColor;
    public TabType tabType;

    public HiTabBottomInfo(String name, Bitmap defaultBitmap, Bitmap selectedBitmap) {
        this.name = name;
        this.defaultBitmap = defaultBitmap;
        this.selectedBitmap = selectedBitmap;
        this.tabType = TabType.BITMAP;
    }

    public HiTabBottomInfo(String name, Bitmap defaultIcon, Bitmap selectedIcon, Color defaultColor, Color tintColor) {
        this.name = name;
        this.defaultIcon = defaultIcon;
        this.selectedIcon = selectedIcon;
        this.defaultColor = defaultColor;
        this.tintColor = tintColor;
        this.tabType = TabType.ICON;
    }
}
