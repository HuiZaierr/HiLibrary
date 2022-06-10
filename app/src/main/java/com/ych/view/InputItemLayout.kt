package com.ych.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.LinearLayout
import com.ych.hi_library.R

class InputItemLayout:LinearLayout {

    private lateinit var topLine:Line
    private lateinit var bottomLine:Line

    constructor(context: Context):this(context,null)
    constructor(context: Context,attrs: AttributeSet?):this(context,attrs,0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int):super(context,attrs,defStyleAttr){

        //解析自定义属性
        val array = context.obtainStyledAttributes(attrs, R.styleable.InputItemLayout)
        //1.标题
        val titleStyleId = array.getResourceId(R.styleable.InputItemLayout_titleTextAppearance,0)
        val title = array.getString(R.styleable.InputItemLayout_title)
        parseTitleStyle(titleStyleId,title)
        //2.输入
        val inputStyleId = array.getResourceId(R.styleable.InputItemLayout_inputTextAppearance,0)
        val hint = array.getString(R.styleable.InputItemLayout_hint)
        val inputType = array.getInteger(R.styleable.InputItemLayout_inputType,0)
        parseInputStyle(inputStyleId,hint,inputType)
        //3.分割线
        val topLineStyleId = array.getResourceId(R.styleable.InputItemLayout_topLineAppearance,0)
        val bottomLineStyleId = array.getResourceId(R.styleable.InputItemLayout_bottomLineAppearance,0)
        topLine = parseLineStyle(topLineStyleId)
        bottomLine = parseLineStyle(bottomLineStyleId)
        array.recycle()
    }




    /**
     * TODO：解析左侧标题资源属性
     */
    private fun parseTitleStyle(resId: Int, title: String?) {

    }

    /**
     * TODO：解析右侧输入框资源属性
     */
    private fun parseInputStyle( resId: Int, hint: String?,inputType:Int) {

    }

    /**
     * TODO：解析上下分割线资源属性
     */
    private fun parseLineStyle(resId: Int) :Line{
        var line = Line()
        val array = context.obtainStyledAttributes(resId,R.styleable.LineAppearance)
        line.color = array.getColor(R.styleable.LineAppearance_color,resources.getColor(R.color.color_000))
        line.height = array.getDimensionPixelOffset(R.styleable.LineAppearance_height,0)
        line.leftMargin = array.getDimensionPixelOffset(R.styleable.LineAppearance_leftMargin,0)
        line.rightMargin = array.getDimensionPixelOffset(R.styleable.LineAppearance_rightMargin,0)
        line.enable = array.getBoolean(R.styleable.LineAppearance_enable,false)
        array.recycle()
        return line
    }

    /**
     * TODO：分割线解析的属性值存储
     */
    inner class Line{
        var color = 0
        var height = 0
        var leftMargin = 0
        var rightMargin = 0
        var enable = false
    }
}