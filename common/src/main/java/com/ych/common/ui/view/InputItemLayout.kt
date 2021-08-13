package com.ych.common.ui.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.annotation.Nullable
import com.ych.common.R


class InputItemLayout:LinearLayout {

    constructor(context: Context):this(context,null)
    constructor(context: Context, @Nullable attrs: AttributeSet?):super(context,attrs,0)
    constructor(context: Context, @Nullable attrs: AttributeSet,defStyleAttr:Int)
            :super(context,attrs,defStyleAttr){

        val array =
            context.obtainStyledAttributes(attrs, R.styleable.InputItemLayout)

        array.recycle()
    }

}