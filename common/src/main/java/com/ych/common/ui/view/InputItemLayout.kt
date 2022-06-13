package com.ych.common.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.text.InputType
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.ych.common.R


class InputItemLayout : LinearLayout {

    private lateinit var titleView: TextView
    lateinit var editText: EditText
    private lateinit var topLine: Line
    private lateinit var bottomLine: Line
    var topPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    var bottomPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {

        dividerDrawable = ColorDrawable()
        showDividers = SHOW_DIVIDER_BEGINNING

        //设置LinearLayout水平方向
        orientation = HORIZONTAL
        //解析自定义属性
        val array = context.obtainStyledAttributes(attrs, R.styleable.InputItemLayout)
        //1.标题
        val titleStyleId = array.getResourceId(R.styleable.InputItemLayout_titleTextAppearance, 0)
        val title = array.getString(R.styleable.InputItemLayout_title)
        parseTitleStyle(titleStyleId, title)
        //2.输入
        val inputStyleId = array.getResourceId(R.styleable.InputItemLayout_inputTextAppearance, 0)
        val hint = array.getString(R.styleable.InputItemLayout_hint)
        val inputType = array.getInteger(R.styleable.InputItemLayout_inputType, 0)
        parseInputStyle(inputStyleId, hint, inputType)
        //3.分割线
        val topLineStyleId = array.getResourceId(R.styleable.InputItemLayout_topLineAppearance, 0)
        val bottomLineStyleId =
            array.getResourceId(R.styleable.InputItemLayout_bottomLineAppearance, 0)
        topLine = parseLineStyle(topLineStyleId)
        bottomLine = parseLineStyle(bottomLineStyleId)
        if (topLine.enable) {
            topPaint.color = topLine.color
            topPaint.style = Paint.Style.FILL_AND_STROKE
            topPaint.strokeWidth = topLine.height
        }
        if (bottomLine.enable) {
            bottomPaint.color = bottomLine.color
            bottomPaint.style = Paint.Style.FILL_AND_STROKE
            bottomPaint.strokeWidth = bottomLine.height
        }
        array.recycle()
    }


    /**
     * TODO：解析左侧标题资源属性
     */
    private fun parseTitleStyle(resId: Int, title: String?) {
        val array = context.obtainStyledAttributes(resId, R.styleable.titleTextAppearance)
        val titleColor = array.getColor(
            R.styleable.titleTextAppearance_titleColor,
            resources.getColor(R.color.color_000)
        )
        val titleSize =
            array.getDimensionPixelSize(R.styleable.titleTextAppearance_titleSize, sp2px(14f))
        val minWidth = array.getDimensionPixelOffset(R.styleable.titleTextAppearance_minWidth, 0)
        val leftMargin = array.getDimensionPixelSize(R.styleable.titleTextAppearance_leftMar, dp2px(15f))

        titleView = TextView(context)
        titleView.setTextColor(titleColor)
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize.toFloat())
        titleView.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
        val lp = titleView.layoutParams as LinearLayout.LayoutParams
        lp.setMargins(leftMargin,0,0,0)
        titleView.layoutParams = lp
        titleView.minWidth = minWidth
        titleView.text = title
        titleView.gravity = Gravity.LEFT or (Gravity.CENTER_VERTICAL)
        addView(titleView)
        array.recycle()
    }

    /**
     * TODO：解析右侧输入框资源属性
     */
    private fun parseInputStyle(resId: Int, hint: String?, inputType: Int) {
        val array = context.obtainStyledAttributes(resId, R.styleable.inputTextAppearance)
        val hintColor = array.getColor(
            R.styleable.inputTextAppearance_hintColor,
            ContextCompat.getColor(context,R.color.color_000)
        )
        val inputColor = array.getColor(
            R.styleable.inputTextAppearance_inputColor,
            ContextCompat.getColor(context,R.color.color_000)
        )
        val testSize = array.getDimensionPixelSize(
            R.styleable.inputTextAppearance_textSize,
            sp2px(14F)
        ) //它会将我们传入的14sp转换为px。

        editText = EditText(context)
        editText.setPadding(0, 0, 0, 0)
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
        params.weight = 1f
        editText.layoutParams = params
        editText.hint = hint
        editText.setTextColor(inputColor)
        editText.setHintTextColor(hintColor)
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, testSize.toFloat())
        editText.setBackgroundColor(Color.TRANSPARENT)
        editText.gravity = Gravity.LEFT or (Gravity.CENTER_VERTICAL)
        when (inputType) {
            0 -> editText.inputType = InputType.TYPE_CLASS_TEXT
            1 -> editText.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD or (InputType.TYPE_CLASS_TEXT)
            2 -> editText.inputType = InputType.TYPE_CLASS_NUMBER
        }
        addView(editText)
        array.recycle()
    }

    /**
     * TODO：解析上下分割线资源属性
     */
    private fun parseLineStyle(resId: Int): Line {
        var line = Line()
        val array = context.obtainStyledAttributes(resId, R.styleable.LineAppearance)
        line.color =
            array.getColor(R.styleable.LineAppearance_color, ContextCompat.getColor(context,R.color.color_000))
        line.height = array.getDimensionPixelOffset(R.styleable.LineAppearance_height, 0).toFloat()
        line.leftMargin = array.getDimensionPixelOffset(R.styleable.LineAppearance_leftMargin, 0).toFloat()
        line.rightMargin = array.getDimensionPixelOffset(R.styleable.LineAppearance_rightMargin, 0).toFloat()
        line.enable = array.getBoolean(R.styleable.LineAppearance_enable, false)
        array.recycle()
        return line
    }

    /**
     * TODO：该方法进行回执Line
     */
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //判断是否初始化
        if (topLine.enable) {
            canvas!!.drawLine(
                topLine.leftMargin,
                0F,
                measuredWidth - topLine.rightMargin,
                0F,
                topPaint
            )
        }
        if (bottomLine.enable) {
            canvas!!.drawLine(
                bottomLine.leftMargin,
                height - bottomLine.height,
                measuredWidth - bottomLine.rightMargin,
                height - bottomLine.height,
                bottomPaint
            )
        }
    }

    /**
     * TODO：分割线解析的属性值存储
     */
    inner class Line {
        var color = 0
        var height = 0F
        var leftMargin = 0F
        var rightMargin = 0F
        var enable = false
    }

    /**
     * TODO：将sp转换为px
     */
    private fun sp2px(value: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            value,
            resources.displayMetrics
        ).toInt()
    }

    /**
     * TODO：将dp转换为px
     */
    private fun dp2px(value: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value,
            resources.displayMetrics
        ).toInt()
    }
}