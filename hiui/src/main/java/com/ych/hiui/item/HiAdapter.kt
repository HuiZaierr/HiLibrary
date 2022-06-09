package com.ych.hiui.item

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import java.lang.RuntimeException
import java.lang.ref.WeakReference
import java.lang.reflect.ParameterizedType
import java.util.*

/**
 * TODO：统一的Adapter
 */
class HiAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val recyclerViewRef: WeakReference<RecyclerView>? = null

    private var mContext: Context = context

    //能够加载布局的LayoutInflater
    private var mInflater: LayoutInflater? = null

    //数据源集合
    private var dataSets = ArrayList<HiDataItem<*, out RecyclerView.ViewHolder>>()

    //列表不同视图的itemType集合，通过SparseArray装载
    private var typeArrays = SparseArray<HiDataItem<*, out RecyclerView.ViewHolder>>()

    init {
        mInflater = LayoutInflater.from(mContext)
    }

    /**
     * TODO：定义一个可以指定位置添加HiDataItem的方法
     *  参数3：notify表示是否刷新当前Item
     */
    fun addItemAt(index: Int, dataItem: HiDataItem<*, out RecyclerView.ViewHolder>, notify: Boolean) {
        if (index > 0) {
            //添加到指定位置
            dataSets.add(index, dataItem)
        } else {
            //否则，添加到末尾
            dataSets.add(dataItem)
        }
        //需要刷新的位置,如果大于0，刷新指定位置，否则刷新最后一个Item
        var notifyPos = if (index > 0) index else dataSets.size - 1
        if (notify) {
            notifyItemInserted(notifyPos)
        }
    }

    /**
     * TODO：定义一个可以添加一个集合的数据
     *  遍历集合，添加到dataSets集合中
     */
    fun addItems(items: List<HiDataItem<*, out RecyclerView.ViewHolder>>, notify: Boolean) {
        //定义当前dataSets的数量，用于局部刷新用
        var startItemPos = dataSets.size
        //遍历需要添加的集合到dataSets中
        for (item in items) {
            dataSets.add(item)
        }

        if (notify) {
            notifyItemRangeInserted(startItemPos, items.size)
        }
    }

    /**
     * TODO：定义一个可以根据下标删除对应的Item，并返回删除的元素HiDataItem
     */
    fun removeItemAt(index: Int): HiDataItem<*, out RecyclerView.ViewHolder>? {
        return if (index > 0 && index < dataSets.size) {
            //会返回老的值，也就是删除的HiDataItem的值
            val remove = dataSets.removeAt(index)
            notifyItemRemoved(index)
            remove
        } else {
            null
        }
    }

    /**
     * TODO：定义一个可以根据指定的HiDataItem来进行删除
     */
    fun removeItem(dataItem: HiDataItem<*, out RecyclerView.ViewHolder>) {
        //获取需要删除的元素对应的下标
        val index = dataSets.indexOf(dataItem)
        //将其删除
        removeItemAt(index)
    }

    /**
     * TODO：刷新Item
     */
    fun refreshItem(dataItem: HiDataItem<*, out RecyclerView.ViewHolder>) {
        val index = dataSets.indexOf(dataItem)
        notifyItemChanged(index)
    }

    /**
     * TODO：获取Item的类型
     *   我们根据，我们传递的HiDataItem的hashcode来判断，相同的肯定相等
     */
    override fun getItemViewType(position: Int): Int {
        val dataItem = dataSets[position]
        val type = dataItem.javaClass.hashCode()
        //判断typeArrays中是否存在type类型的数据，没有进行添加
        if (typeArrays.indexOfKey(type) < 0) {
            typeArrays.put(type, dataItem)
        }
        return type
    }

    /**
     * TODO：创建ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //通过typeArrays来根据viewType获取对应的HiDataItem
        val dataItem = typeArrays.get(viewType)
        //根据dataItem获取对应的视图View
        var view: View? = dataItem.getItemView(parent)
        if (view == null) {
            val layoutRes = dataItem.getItemLayoutRes()
            if (layoutRes < 0) {
                RuntimeException("dataItem: ${dataItem.javaClass.name} must be override getItemView or getItemLayoutRes")
            }
            view = mInflater!!.inflate(layoutRes, parent, false)
        }
        //实例化我们的ViewHolder，是通过HiDataItem中获取泛型VH
        return createViewHolderInternal(dataItem.javaClass, view!!)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //获取当前对应的HiDataItem
        val dataItem = getItem(position)
        if (dataItem != null) {
            dataItem.onBindData(holder, position)
        }
    }

    private fun createViewHolderInternal(javaClass: Class<HiDataItem<*, out RecyclerView.ViewHolder>>, view: View): RecyclerView.ViewHolder {
        //获取的就是HiDataItem的Class对象
        var superClass = javaClass.genericSuperclass
        //如果是参数类型泛型
        if (superClass is ParameterizedType) {
            //它会返回泛型参数的集合，比如HiDataItem泛型Data和VH
            var arguments = superClass.actualTypeArguments
            for (argument in arguments) {
                //如果泛型参数为Class类型，并且为RecyclerView.ViewHolder的泛型参数
                if (argument is Class<*> && RecyclerView.ViewHolder::class.java.isAssignableFrom(argument)) {
                    //获取到RecyclerView.ViewHolder对象的Class,通过反射创建ViewHolder实例,ViewHolder接受一个参数View
                    try {
                        //如果是则使用反射 实例化类上标记的实际的泛型对象
                        //这里需要  try-catch 一把，如果咱们直接在HiDataItem子类上标记 RecyclerView.ViewHolder，抽象类是不允许反射的
                        return argument.getConstructor(View::class.java).newInstance(view) as RecyclerView.ViewHolder
                    } catch (e: Throwable) {
                        e.printStackTrace()

                    }
                }
            }
        }
        //返回一个默认的RecyclerView的ViewHolder对象
        return object : RecyclerView.ViewHolder(view) {}
    }



    override fun getItemCount(): Int {
        return dataSets.size
    }

    /**
     * TODO：该方法是RecyclerView和Adapter进行相关联时调用
     */
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        /**
         * 为列表上的item 适配网格布局
         */
        val layoutManager = recyclerView.layoutManager;
        if (layoutManager is GridLayoutManager) {
            val spanCount = layoutManager.spanCount
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    if (position < dataSets.size) {
                        val dataItem = getItem(position)
                        if (dataItem != null) {
                            val spanSize = dataItem.getSpanSize()
                            return if (spanSize <= 0) spanCount else spanSize
                        }
                    }
                    return spanCount
                }
            }
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        recyclerViewRef?.clear()
    }

    fun getAttachRecyclerView(): RecyclerView? {
        return recyclerViewRef?.get()
    }

    fun getItem(position: Int): HiDataItem<*, RecyclerView.ViewHolder>? {
        if (position < 0 || position >= dataSets.size)
            return null
        return dataSets[position] as HiDataItem<*, RecyclerView.ViewHolder>
    }


    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        val recyclerView = getAttachRecyclerView()
        if (recyclerView != null) {
            //瀑布流的item占比适配
            val position = recyclerView.getChildAdapterPosition(holder.itemView)
            val dataItem = getItem(position) ?: return
            val lp = holder.itemView.layoutParams
            if (lp != null && lp is StaggeredGridLayoutManager.LayoutParams) {
                val manager = recyclerView.layoutManager as StaggeredGridLayoutManager?
                val spanSize = dataItem.getSpanSize()
                if (spanSize == manager!!.spanCount) {
                    lp.isFullSpan = true
                }
            }

            dataItem.onViewAttachedToWindow(holder)
        }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        val dataItem = getItem(holder.adapterPosition) ?: return
        dataItem.onViewDetachedFromWindow(holder)
    }




}