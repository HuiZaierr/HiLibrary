package com.ych.hiui.silder

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ych.hiui.R


/**
 * TODO：实现商城商品选择（左侧商品类型选择，联动右侧数据列表）
 *  1.实现自定义View，继承LineaLayout，水平方向，通过两个RecyclerView实现。
 *  2.定义左侧类型选项自定义属性
 *  3.
 */
class HiSilderView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var menuItemAttr: MenuItemAttr

    //菜单的宽度
    private val MENU_WIDTH = applyUnit(TypedValue.COMPLEX_UNIT_DIP, 100f)

    //菜单的高度
    private val MENU_HEIGHT = applyUnit(TypedValue.COMPLEX_UNIT_DIP, 45f)

    //菜单的字体大小
    private val MENU_TEXT_SIZE = applyUnit(TypedValue.COMPLEX_UNIT_SP, 14f)

    //字体默认颜色
    private val TEXT_COLOR_NORMAL = Color.parseColor("#666666")

    //字体选中颜色
    private val TEXT_COLOR_SELECT = Color.parseColor("#DD3127")

    //默认背景颜色
    private val BG_COLOR_NORMAL = Color.parseColor("#f7f8f9")

    //选中背景颜色
    private val BG_COLOR_SELECT = Color.parseColor("#ffffff")

    //默认的左侧菜单View的布局文件
    private val MENU_ITEM_LAYOUT_RES_ID = R.layout.hi_slider_menu_item

    //默认的右侧内容View的布局文件
    private val CONTENT_ITEM_LAYOUT_RES_ID = R.layout.hi_slider_content_item

    //左侧菜单项
    private var menuView = RecyclerView(context)

    //右侧对应左侧选中菜单类型的内容
    private var contentView = RecyclerView(context)

    /**
     * TODO：解析自定义属性
     *  初始化自定义View的一些信息
     */
    init {
        //1.解析左侧菜单类型自定一属性
        menuItemAttr = parseMenuItemAttr(attrs)
        //2.设置LinearLayout的方向为水平方向
        orientation = HORIZONTAL
        //3.设置两个RecyclerView的LayoutParams
        menuView.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT)
        menuView.overScrollMode = OVER_SCROLL_NEVER     //这个就是当我们滑动到底部时，禁用调滑动光晕效果
        menuView.itemAnimator = null                    //去掉其动画
        contentView.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT)
        contentView.overScrollMode = OVER_SCROLL_NEVER
        contentView.itemAnimator = null                 //去掉其动画
        //4.添加View
        addView(menuView)
        addView(contentView)
    }


    /**
     * TODO：绑定左侧Menu的布局资源
     *   参数1：layoutRes：布局资源，如果我们没有传入，我们需要给一个默认的。
     *
     */
    fun bindMenuView(layoutRes:Int = MENU_ITEM_LAYOUT_RES_ID,itemCount:Int){

    }

    fun bindContentView(layoutRes:Int = R.layout.hi_slider_content_item){

    }


    /**
     * TODO：1.解析左侧菜单自定义属性
     */
    private fun parseMenuItemAttr(attrs: AttributeSet?): MenuItemAttr {
        val array = context.obtainStyledAttributes(attrs, R.styleable.HiSliderView)
        val menuItemWidth =
            array.getDimensionPixelOffset(R.styleable.HiSliderView_menuItemWidth, MENU_WIDTH)
        val menuItemHeight =
            array.getDimensionPixelOffset(R.styleable.HiSliderView_menuItemHeight, MENU_HEIGHT)
        val menuItemTextSize =
            array.getDimensionPixelOffset(R.styleable.HiSliderView_menuItemTextSize, MENU_TEXT_SIZE)
        val menuItemSelectTextSize = array.getDimensionPixelOffset(
            R.styleable.HiSliderView_menuItemSelectTextSize,
            MENU_TEXT_SIZE
        )
        var menuItemTextColor =
            array.getColorStateList(R.styleable.HiSliderView_menuItemTextColor)?:generateColorStateList()
        val menuItemIndicator = array.getDrawable(R.styleable.HiSliderView_menuItemIndicator)?: ContextCompat.getDrawable(context,R.drawable.shape_hi_slider_indicator)
        var menuItemBackgroundColor =
            array.getColor(R.styleable.HiSliderView_menuItemBackgroundColor, BG_COLOR_NORMAL)
        var menuItemBackgroundSelectColor =
            array.getColor(R.styleable.HiSliderView_menuItemSelectBackgroundColor, BG_COLOR_SELECT)
        array.recycle()
        //将其属性值封装在一个实体类中
        return MenuItemAttr(
            menuItemWidth,
            menuItemHeight,
            menuItemTextColor,
            menuItemBackgroundSelectColor,
            menuItemBackgroundColor,
            menuItemTextSize,
            menuItemSelectTextSize,
            menuItemIndicator
        )
    }

    /**
     * TODO：TextColor颜色，使用ColorStateList实现。
     */
    private fun generateColorStateList(): ColorStateList {
        var states = Array(2){ IntArray(2)}
        var colors = IntArray(2)
        //颜色数组
        colors[0] = TEXT_COLOR_SELECT
        colors[1] = TEXT_COLOR_NORMAL

        //状态数组，表示当选中时，就为colors[0]
        states[0] = IntArray(1){android.R.attr.state_selected}
        states[1] = IntArray(1)
        return ColorStateList(states,colors)
    }


    fun applyUnit(unit: Int, value: Float): Int {
        return TypedValue.applyDimension(unit, value, resources.displayMetrics).toInt()
    }
}