package com.ych.hiui.silder

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ych.hiui.R
import com.ych.hiui.item.HiViewHolder
import kotlinx.android.synthetic.main.hi_slider_menu_item.view.*
import java.lang.reflect.Type


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
        menuView.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
        menuView.overScrollMode = OVER_SCROLL_NEVER     //这个就是当我们滑动到底部时，禁用调滑动光晕效果
        menuView.itemAnimator = null                    //去掉其动画
        contentView.layoutParams =
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        contentView.overScrollMode = OVER_SCROLL_NEVER
        contentView.itemAnimator = null                 //去掉其动画
        //4.添加View
        addView(menuView)
        addView(contentView)
    }


    //----------------------------------- 左侧MenuView数据绑定，以及Adapter  start--------------------------------
    /**
     * TODO：绑定左侧Menu的布局资源
     *   参数1：layoutRes：布局资源，如果我们没有传入，我们需要给一个默认的。
     *   参数2：itemCount：条目个数
     *   参数3：onBindView：数据的绑定，传入HiViewHolder和position位置。
     *   参数4：onItemClick：条目的点击事件，传入HiViewHolder，以及点击item的位置。
     *
     */
    fun bindMenuView(
        layoutRes: Int = MENU_ITEM_LAYOUT_RES_ID,
        itemCount: Int,
        onBindView: (HiViewHolder, Int) -> Unit,
        onItemClick: (HiViewHolder, Int) -> Unit
    ) {

        menuView.layoutManager = LinearLayoutManager(context)
        menuView.adapter = MenuAdapter(layoutRes, itemCount, onBindView, onItemClick)
    }

    /***
     * TODO：左侧Menu的适配器
     */
    inner class MenuAdapter(
        val layoutRes: Int,
        val itemcount: Int,
        val onBindView: (HiViewHolder, Int) -> Unit,
        val onItemClick: (HiViewHolder, Int) -> Unit
    ) : RecyclerView.Adapter<HiViewHolder>() {

        private var currentSelectIndex = 0      //当前选中的位置
        private var lastSelectIndex = 0         //上一次选中的位置

        override fun getItemCount(): Int {
            return itemcount
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HiViewHolder {
            var itemView = LayoutInflater.from(context).inflate(layoutRes, parent, false)
            var params = RecyclerView.LayoutParams(menuItemAttr.width, menuItemAttr.height)
            itemView.layoutParams =
                params                                                                      //设置itemView的宽高，通过自定义属性来获取
            itemView.setBackgroundColor(menuItemAttr.normalBackgroundColor)                                     //背景，默认未选中
            itemView.findViewById<TextView>(R.id.content_item_title)
                ?.setTextColor(menuItemAttr.textColor)      //设置标题
            itemView.findViewById<ImageView>(R.id.content_item_image)
                ?.setImageDrawable(menuItemAttr.indicator) //设置左侧指示器
            return HiViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: HiViewHolder, position: Int) {
            holder.itemView.setOnClickListener {
                currentSelectIndex = position
                notifyItemChanged(currentSelectIndex)   //更新当前位置
                notifyItemChanged(lastSelectIndex)      //更新上一次位置
            }

            //如果当前选中位置相同
            if (currentSelectIndex == position) {
                onItemClick(holder, position)
                lastSelectIndex = currentSelectIndex
            }

            applyItemAttr(holder, position)
            onBindView(holder, position)
        }

        /**
         * 处理选中未选中
         */
        private fun applyItemAttr(holder: HiViewHolder, position: Int) {
            var selected = position == currentSelectIndex
            //findViewByid获取空间
            var itemTitle: TextView? = holder.itemView.menu_item_title
            var itemIndicator: ImageView? = holder.itemView.menu_item_indicator
            //根据选中状态，来设置空间的显示与隐藏，以及颜色，大小等。
            itemIndicator?.visibility = if (selected) View.VISIBLE else View.GONE
            itemTitle?.setTextColor(menuItemAttr.textColor)
            itemTitle?.setTextSize(
                TypedValue.COMPLEX_UNIT_SP,
                if (selected) menuItemAttr.selectTextSize.toFloat() else menuItemAttr.textSize.toFloat()
            )
            holder.itemView.setBackgroundColor(if (selected) menuItemAttr.selectBackgroundColor else menuItemAttr.normalBackgroundColor)
            itemTitle?.isSelected = selected
        }
    }
    //----------------------------------- 左侧MenuView数据绑定，以及Adapter   end --------------------------------


    //----------------------------------- 右侧ContentView数据绑定，以及Adapter  start--------------------------------
    /**、
     * TODO：由于右侧数据是不断的变化的，我们需要复用.
     *   注意：由于每个左侧菜单的数据是不相同的，每次都需要请求数据，我们不能在Adapter构造函数中传递数量等
     *        我们通过刷新适配器
     */
    fun bindContentView(
        layoutRes: Int = CONTENT_ITEM_LAYOUT_RES_ID,
        itemCount: Int,
        itemDecoration:RecyclerView.ItemDecoration?,
        layoutManager: RecyclerView.LayoutManager,
        onBindView: (HiViewHolder, Int) -> Unit,
        onItemClick: (HiViewHolder, Int) -> Unit
    ) {
        //防止多次创建
        if (contentView.layoutManager == null){
            //设置layoutManager
            contentView.layoutManager = layoutManager
            //设置适配器
            contentView.adapter = ContentAdapter(layoutRes)
            //添加itemDecoration
            itemDecoration?.let {
                contentView.addItemDecoration(itemDecoration)
            }
        }

       val contentAdapter =  contentView.adapter as ContentAdapter
        contentAdapter.updata(itemCount,onBindView,onItemClick)
        contentAdapter.notifyDataSetChanged()

        //每次调用bindContentView时都让其从第0位置起
        contentView.scrollToPosition(0)
    }

    inner class ContentAdapter(val layoutRes: Int):RecyclerView.Adapter<HiViewHolder>(){

        private var count = 0
        private lateinit var onBindView: (HiViewHolder, Int) -> Unit
        private lateinit var onItemClick: (HiViewHolder, Int) -> Unit

        override fun getItemCount(): Int {
            return count
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HiViewHolder {
            var itemView = LayoutInflater.from(context).inflate(layoutRes,parent,false)
            //获取contentView宽度，通过HiSilderView的宽度 - 左右的padding - 左侧菜单的宽度
            var remainWidth = width - paddingLeft - paddingRight - menuItemAttr.width
            //通过获取当前的LayoutManager
            var layoutManager = (parent as RecyclerView).layoutManager
            var spanCount = 0
            if (layoutManager is GridLayoutManager){
                spanCount = layoutManager.spanCount
            }else if (layoutManager is StaggeredGridLayoutManager){
                spanCount = layoutManager.spanCount
            }

            //如果数量大于0，我们设置宽和高，防止显示数据是屏幕跳动
            if (spanCount > 0){
                var itemWidth = remainWidth / spanCount
                //创建contentView，设置layoutParams的原因是防止图片未加载出来之前，列表滑动出现闪动。
                itemView.layoutParams = RecyclerView.LayoutParams(itemWidth,itemWidth)
            }
            return HiViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: HiViewHolder, position: Int) {
            onBindView(holder,position)
            holder.itemView.setOnClickListener {
                onItemClick(holder,position)
            }
        }

        /**
         * TODO：刷新适配器
         */
        fun updata(itemCount: Int, onBindView: (HiViewHolder, Int) -> Unit, onItemClick: (HiViewHolder, Int) -> Unit) {
            this.count = itemCount
            this.onBindView = onBindView
            this.onItemClick = onItemClick
        }
    }




    //----------------------------------- 右侧ContentView数据绑定，以及Adapter  end--------------------------------

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
            array.getColorStateList(R.styleable.HiSliderView_menuItemTextColor)
                ?: generateColorStateList()
        val menuItemIndicator = array.getDrawable(R.styleable.HiSliderView_menuItemIndicator)
            ?: ContextCompat.getDrawable(context, R.drawable.shape_hi_slider_indicator)
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
        var states = Array(2) { IntArray(2) }
        var colors = IntArray(2)
        //颜色数组
        colors[0] = TEXT_COLOR_SELECT
        colors[1] = TEXT_COLOR_NORMAL

        //状态数组，表示当选中时，就为colors[0]
        states[0] = IntArray(1) { android.R.attr.state_selected }
        states[1] = IntArray(1)
        return ColorStateList(states, colors)
    }


    fun applyUnit(unit: Int, value: Float): Int {
        return TypedValue.applyDimension(unit, value, resources.displayMetrics).toInt()
    }


}