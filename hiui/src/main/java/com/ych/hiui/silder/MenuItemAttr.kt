package com.ych.hiui.silder

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable

data class MenuItemAttr(
    val width:Int,
    val height:Int,
    val textColor: ColorStateList,
    val selectBackgroundColor:Int,
    val normalBackgroundColor:Int,
    val textSize:Int,
    val selectTextSize:Int,
    val indicator: Drawable?)