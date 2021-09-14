package com.ych.hi_library.loading

import android.content.Context
import kotlin.jvm.JvmOverloads
import android.util.AttributeSet
import android.graphics.Paint
import android.graphics.Canvas
import android.view.View

/**
 * Created by ych on 2019/9/18.
 * Description: 绘制原点
 */
class CircleView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var mPaint: Paint? = null

    /**
     * 获取当前颜色
     * @return
     */
    var color = 0
        private set

    private fun initPaint() {
        mPaint = Paint()
        mPaint!!.isAntiAlias = true
        mPaint!!.isDither = true
    }

    override fun onDraw(canvas: Canvas) {
        val cx = width / 2
        val cy = height / 2
        canvas.drawCircle(cx.toFloat(), cy.toFloat(), cx.toFloat(), mPaint!!)
    }

    /**
     * 切换颜色
     * @param color
     */
    fun exchangeColor(color: Int) {
        this.color = color
        mPaint!!.color = color
        invalidate()
    }

    init {
        initPaint()
    }
}