package com.ych.hiui.silder

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.*
import com.ych.hiui.R
import com.ych.hiui.item.HiViewHolder
import kotlinx.android.synthetic.main.hi_slider_menu_item.view.*


class HiSilderView @JvmOverloads constructor(
    context:Context,attrs:AttributeSet,defStyleAttr:Int = 0
) :LinearLayout(context,attrs,defStyleAttr){
     private var menuItemAttr:MenuItemAttr
     //菜单的宽度
     private val MENU_WIDTH = applyUnit(TypedValue.COMPLEX_UNIT_DIP,100f)
     //菜单的高度
     private val MENU_HEIGHT = applyUnit(TypedValue.COMPLEX_UNIT_DIP,45f)
     //菜单的字体大小
     private val MENU_TEXT_SIZE = applyUnit(TypedValue.COMPLEX_UNIT_SP,14f)
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
     //左侧菜单View
     val menuView = RecyclerView(context)
     //右侧内容View
     val contentView = RecyclerView(context)

    /**
     * 解析自定义属性
     */
    init {
        menuItemAttr = parseMenuItemAttr(attrs)
        orientation = HORIZONTAL

        menuView.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT)
        menuView.overScrollMode = View.OVER_SCROLL_NEVER    //去掉滑动到底部后继续向下滑动的阴影
        menuView.itemAnimator = null

        contentView.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT)
        contentView.overScrollMode = View.OVER_SCROLL_NEVER    //去掉滑动到底部后继续向下滑动的阴影
        contentView.itemAnimator = null
        addView(menuView)
        addView(contentView)
    }

    fun bindMenuView(
        layoutRes: Int = MENU_ITEM_LAYOUT_RES_ID,
        itemCount:Int,
        onBindView:(HiViewHolder, Int)->Unit,
        onItemClick:(HiViewHolder,Int)->Unit
       ){
        menuView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        menuView.adapter = MenuAdapter(layoutRes,itemCount,onBindView,onItemClick)
    }


    fun bindContentView(
        layoutRes: Int = CONTENT_ITEM_LAYOUT_RES_ID,
        itemCount:Int,
        itemDecoration: RecyclerView.ItemDecoration?,
        layoutManager: RecyclerView.LayoutManager,
        onBindView:(HiViewHolder, Int)->Unit,
        onItemClick:(HiViewHolder,Int)->Unit
    ){
        if (contentView.layoutManager == null){
            contentView.layoutManager = layoutManager
            contentView.adapter = ContentAdapter(layoutRes)
            itemDecoration?.let {
                contentView.addItemDecoration(itemDecoration)
            }
        }
        val contentAdapter = contentView.adapter as ContentAdapter
        contentAdapter.update(itemCount,onBindView,onItemClick)
        contentAdapter.notifyDataSetChanged()

        contentView.scrollToPosition(0)
    }

    inner class ContentAdapter( val layoutRes: Int): RecyclerView.Adapter<HiViewHolder>() {

        private lateinit var onItemClick: (HiViewHolder, Int) -> Unit
        private lateinit var onBindView: (HiViewHolder, Int) -> Unit
        private var count: Int = 0

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HiViewHolder {
            var itemView = LayoutInflater.from(context).inflate(layoutRes,parent,false)
            //为了防止右侧内容区域高度未显示和显示后高度的跳动，先计算高度并设置高度
            val remainSpace = width - paddingLeft - paddingRight - menuItemAttr.width
            val layoutManager = (parent as RecyclerView).layoutManager
            var spanCount = 0
            if (layoutManager is GridLayoutManager) {
                spanCount = layoutManager.spanCount
            } else if (layoutManager is StaggeredGridLayoutManager) {
                spanCount = layoutManager.spanCount
            }

            if (spanCount > 0) {
                val itemWidth = remainSpace / spanCount
                //创建content itemview  ，设置它的layoutparams 的原因，是防止图片未加载出来之前，列表滑动时 上下闪动的效果
                itemView.layoutParams = RecyclerView.LayoutParams(itemWidth, itemWidth)
            }
            return HiViewHolder(itemView)
        }

        override fun getItemCount(): Int {
            return count
        }

        override fun onBindViewHolder(holder: HiViewHolder, position: Int) {
            onBindView(holder,position)
            holder.itemView.setOnClickListener{
                onItemClick(holder,position)
            }
        }

        fun update(
            itemCount: Int,
            onBindView: (HiViewHolder, Int) -> Unit,
            onItemClick: (HiViewHolder, Int) -> Unit) {
            this.count = itemCount
            this.onBindView = onBindView
            this.onItemClick = onItemClick
        }

    }


    inner class MenuAdapter(
        val layoutRes: Int,
        val count: Int,
        val onBindView:(HiViewHolder, Int)->Unit,
        val onItemClick:(HiViewHolder,Int)->Unit
    ):RecyclerView.Adapter<HiViewHolder>(){
        //当前选中的item的位置
        private var currentSelectIndex = 0
        //上一次选中的item的位置
        private var lastSelectIndex = 0

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HiViewHolder {
            val itemView = LayoutInflater.from(context).inflate(layoutRes,parent,false)
            val params = RecyclerView.LayoutParams(menuItemAttr.width,menuItemAttr.height)
            itemView.layoutParams = params
            itemView.setBackgroundColor(menuItemAttr.normalBackgroundColor)
            itemView.findViewById<TextView>(R.id.menu_item_title)?.setTextColor(menuItemAttr.textColor)
            itemView.findViewById<ImageView>(R.id.menu_item_indicator)?.setImageDrawable(menuItemAttr.indicator)
            return HiViewHolder(itemView)
        }

        override fun getItemCount(): Int {
            return itemCount
        }

        override fun onBindViewHolder(holder: HiViewHolder, position: Int) {
            holder.itemView.setOnClickListener {
                currentSelectIndex = position
                //更新样式
                notifyItemChanged(currentSelectIndex)
                notifyItemChanged(lastSelectIndex)
            }
            if (currentSelectIndex == position){
                onItemClick(holder,position)
                lastSelectIndex = currentSelectIndex
            }
            applyItemAttr(position,holder)
            onBindView(holder,position)

        }

        private fun applyItemAttr(position:Int,holder:HiViewHolder){
            val selected = position == currentSelectIndex
            val titleView:TextView? = holder.itemView.menu_item_title
            val indicatorView:ImageView? = holder.itemView.menu_item_indicator

            indicatorView?.visibility = if (selected) View.VISIBLE else View.GONE
            titleView?.setTextSize(TypedValue.COMPLEX_UNIT_PX,if (selected) menuItemAttr.selectTextSize.toFloat() else menuItemAttr.textSize.toFloat())
            holder.itemView.setBackgroundColor(if (selected) menuItemAttr.selectBackgroundColor else menuItemAttr.normalBackgroundColor)
            titleView?.isSelected = selected
        }

    }

    private fun parseMenuItemAttr(attrs: AttributeSet) :MenuItemAttr{
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.HiSliderView)
        var menuItemWidth = typeArray.getDimensionPixelOffset(R.styleable.HiSliderView_meniItemWidth,MENU_WIDTH)
        var menuItemHeight = typeArray.getDimensionPixelOffset(R.styleable.HiSliderView_meniItemHeight,MENU_HEIGHT)
        var menuItemTextColor = typeArray.getColorStateList(R.styleable.HiSliderView_meniItemTextColor)?:generateColorStateList()
        var menuItemTextSize = typeArray.getDimensionPixelOffset(R.styleable.HiSliderView_meniItemTextSize,MENU_TEXT_SIZE)
        var menuItemSelectTextSize = typeArray.getDimensionPixelOffset(R.styleable.HiSliderView_meniItemSelectTextSize,MENU_TEXT_SIZE)
        var menuItemIndicator = typeArray.getDrawable(R.styleable.HiSliderView_meniItemIndicator)?:ContextCompat.getDrawable(context,R.drawable.shape_hi_slider_indicator)
        var menuItemBackgroundColor = typeArray.getColor(R.styleable.HiSliderView_meniItemBackgroundColor,BG_COLOR_NORMAL)
        var menuItemBackgroundSelectColor = typeArray.getColor(R.styleable.HiSliderView_meniItemSelectBackgroundColor,BG_COLOR_SELECT)
        typeArray.recycle()
        return MenuItemAttr(
            menuItemWidth,
            menuItemHeight,
            menuItemTextColor,
            menuItemBackgroundSelectColor,
            menuItemBackgroundColor,
            menuItemTextSize,
            menuItemSelectTextSize,
            menuItemIndicator)
    }

    data class MenuItemAttr(
        val width:Int,
        val height:Int,
        val textColor:ColorStateList,
        val selectBackgroundColor:Int,
        val normalBackgroundColor:Int,
        val textSize:Int,
        val selectTextSize:Int,
        val indicator:Drawable?)

    private fun generateColorStateList():ColorStateList{
        val states = Array(2){ IntArray(2)}
        val colors = IntArray(2)

        colors[0] = TEXT_COLOR_SELECT
        colors[1] = TEXT_COLOR_NORMAL

        states[0] = IntArray(1){android.R.attr.state_selected}
        states[1] = IntArray(1)
        return ColorStateList(states,colors)
    }



    /**
     * todo:将DP转换为PX
     */
    private fun applyUnit(unit:Int,value:Float):Int{
        return TypedValue.applyDimension(unit,value,context.resources.displayMetrics).toInt()
    }
}