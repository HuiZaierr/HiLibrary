package com.ych.hi_library.loading

import androidx.annotation.RequiresApi
import android.os.Build
import android.util.AttributeSet
import android.widget.RelativeLayout
import android.util.Log
import android.animation.ObjectAnimator
import android.animation.AnimatorSet
import android.view.animation.DecelerateInterpolator
import android.animation.AnimatorListenerAdapter
import android.animation.Animator
import android.content.Context
import android.view.animation.AccelerateInterpolator
import android.graphics.Color
import com.ych.hi_library.R
import android.util.TypedValue
import android.view.ViewGroup

/**
 * 使用：直接隐藏已经处理优化
 * loadingView.setOnClickListener(new View.OnClickListener() {
 * @Override
 * public void onClick(View v) {
 * loadingView.setVisibility(View.GONE);
 * }
 * });
 */
/**
 * Created by ych on 2019/9/18.
 * Description: 花束动画效果      三个圆进行交换位置
 * 效果分析：不同颜色的三个圆点，进行交换位置
 * 思路：1.使用RelativeLayout
 * 2.绘制三个不同颜色的原点
 * 3.使用属性动画轮询进行位置改动
 */
class LoadingView @RequiresApi(api = Build.VERSION_CODES.M) constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int
) : RelativeLayout(context, attrs, defStyleAttr) {
    //三个View
    private var mLeftView: CircleView? = null
    private var mMiddleView: CircleView? = null
    private var mRightView: CircleView? = null
    private var mLeftColor = 0
    private var mMiddleColor = 0
    private var mRightColor = 0

    //移动距离 这里都是px，我们转为dp
    private var mTranslationDistance = 30
    private val ANIMATOR_TIME: Long = 300

    //是否停止动画
    private var isStopAnimator = false

    @RequiresApi(api = Build.VERSION_CODES.M)
    constructor(context: Context) : this(context, null) {
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
    }

    /**
     * 往外跑
     */
    fun expendAnimation() {
        if (isStopAnimator) {
            return
        }
        Log.e("TAG", "请你杀了我expendAnimation")
        //1.往左边跑 是负数，定义一个距离
        val leftAnimator =
            ObjectAnimator.ofFloat(mLeftView, "translationX", 0f, -mTranslationDistance.toFloat())
        val rightAnimator =
            ObjectAnimator.ofFloat(mRightView, "translationX", 0f, mTranslationDistance.toFloat())
        val set = AnimatorSet()
        set.duration = ANIMATOR_TIME
        set.playTogether(leftAnimator, rightAnimator)
        //刚开始快之后变慢
        set.interpolator = DecelerateInterpolator()
        set.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                //切换颜色  自己定义一个顺序  左边的给中间 中间的给右边 右边的给左边
                exchangeColor()
                innerAnimation()
            }
        })
        set.start()
    }

    /**
     * 切换颜色  自己定义一个顺序  左
     * 边的给中间
     * 中间的给右边
     * 右边的给左边
     */
    private fun exchangeColor() {
        mLeftColor = mMiddleView!!.color
        mMiddleColor = mRightView!!.color
        mRightColor = mLeftView!!.color
        mLeftView!!.exchangeColor(mLeftColor)
        mMiddleView!!.exchangeColor(mMiddleColor)
        mRightView!!.exchangeColor(mRightColor)
    }

    /**
     * 往里跑
     */
    private fun innerAnimation() {
        if (isStopAnimator) {
            return
        }
        Log.e("TAG", "请你杀了我innerAnimation")
        //1.往左边跑 是负数，定义一个距离
        val leftAnimator =
            ObjectAnimator.ofFloat(mLeftView, "translationX", -mTranslationDistance.toFloat(), 0f)
        val rightAnimator =
            ObjectAnimator.ofFloat(mRightView, "translationX", mTranslationDistance.toFloat(), 0f)
        val set = AnimatorSet()
        set.duration = ANIMATOR_TIME
        set.playTogether(leftAnimator, rightAnimator)
        set.interpolator = AccelerateInterpolator()
        set.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                exchangeColor()
                expendAnimation()
            }
        })
        set.start()
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun addViews(context: Context) {
        setBackgroundColor(Color.WHITE)
        //移动距离转换px转dp
        mTranslationDistance = dip2px(mTranslationDistance)
        mLeftView = getCircleView(context)
        mLeftView!!.exchangeColor(context.resources.getColor(R.color.colorPrimary))
        //        mLeftView.exchangeColor(Color.BLUE);
        mMiddleView = getCircleView(context)
        //        mMiddleView.exchangeColor(Color.RED);
        mMiddleView!!.exchangeColor(context.resources.getColor(R.color.colorAccent))
        mRightView = getCircleView(context)
        //        mRightView.exchangeColor(Color.GREEN);
        mRightView!!.exchangeColor(context.resources.getColor(R.color.color_d43))
        addView(mLeftView)
        addView(mRightView)
        addView(mMiddleView)
    }

    fun getCircleView(context: Context?): CircleView {
        val circleView = CircleView(context)
        //设置他的大小
        val params = LayoutParams(dip2px(10), dip2px(10))
        //在父容器中间
        params.addRule(CENTER_IN_PARENT)
        circleView.layoutParams = params
        return circleView
    }

    private fun dip2px(dip: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dip.toFloat(),
            resources.displayMetrics
        ).toInt()
    }

    fun closeLoading() {
        visibility = GONE
    }

    /**
     * 优化：
     * 1.将我们的自定义动画设置为INVISIBLE,减少一些不必要的测量和摆放，减少一些系统的源码(View的绘制流程)
     * 2.将我们的动画进行清除
     * 3.设置一个标志，是否需要启动动画
     * 4.判断我们父容器是否存在，把自己清除，移除自己所有的View
     * @param visibility
     */
    override fun setVisibility(visibility: Int) {
        //1.将我们的自定义动画设置为INVISIBLE,减少一些不必要的测量和摆放，减少一些系统的源码(View的绘制流程)
        super.setVisibility(INVISIBLE)
        //2.将我们的动画进行清除
        clearAnimation()
        //3.设置一个标志，是否需要启动动画
        isStopAnimator = true
        //4.判断我们父容器是否存在，把自己清除
        val parent = parent as ViewGroup
        if (parent != null) {
            parent.removeView(this)
            removeAllViews()
        }
    }

    //该方法在onCreate中执行
    init {

        //1.添加三个View
        addViews(context)

        //2.开启我们的动画  我们可以将动画放在onResume之后执行
        post { //开启动画
            expendAnimation()
        }
    }
}