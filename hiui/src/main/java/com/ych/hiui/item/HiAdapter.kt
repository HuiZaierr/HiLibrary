package com.ych.hiui.item

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.reflect.ParameterizedType

class HiAdapter(context:Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mContext:Context? = null
    private var mInflater:LayoutInflater? = null
    //ItemData数据集合
    private var dataSets = ArrayList<HiDataItem<*,RecyclerView.ViewHolder>>()
    //ItemType集合
    private var typeArrays = SparseArray<HiDataItem<*,RecyclerView.ViewHolder>>()


    init {
        this.mContext = context
        this.mInflater = LayoutInflater.from(context)
    }

    /**
     * TODO：能够向Adapter中注册不同类型的HiDataItem
     *     参数1：index允许在指定位置进行添加
     *     参数2：Item的数据
     *     参数3：是否在添加时，对列表进行刷新
     */
    fun addItem(index:Int,item:HiDataItem<*,RecyclerView.ViewHolder>,notify:Boolean){
        //表示想往指定的位置添加Item
        if (index>0){
            dataSets.add(index,item)
        }else{
            dataSets.add(item)
        }

        /**
         * TODO：处理刷新获取刷新的item的位置
         *      如果index大于0，就刷新指定的位置，否则就是最后一个位置
         */
        val notifyPos = if(index>0) index else dataSets.size - 1
        if (notify){
            notifyItemInserted(notifyPos)
        }
    }


    /**
     * TODO：一次性向Item中添加一个集合
     */
    fun addItems(items:List<HiDataItem<*,RecyclerView.ViewHolder>>,notify:Boolean){
        val start = dataSets.size
        //遍历，添加到集合当中
        for (item in items){
            dataSets.add(item)
        }
        //判断是否刷新，只刷新我们添加进来的数据
        if (notify){
            notifyItemRangeChanged(start,items.size)
        }
    }

    /**
     * TODO：删除指定位置的Item
     */
    fun removeItem(index: Int):HiDataItem<*,RecyclerView.ViewHolder>?{
        if (index>0 && index<dataSets.size){
            val remove = dataSets.removeAt(index)
            notifyItemRemoved(index)
            return remove
        }else{
            return null
        }
    }

    /**
     * TODO：删除指定的Item
     */
    fun removeItem(item: HiDataItem<*, *>){
        if (item!=null){
            val index = dataSets.indexOf(item)
            removeItem(index)
        }
    }

    /**
     * TODO：返回每个Item在视图上的类型
     *      我们可以通过获取position位置的HiDataItem，获取他的hashCode值作为type类型，hashCode值相同表示类型相同
     */
    override fun getItemViewType(position: Int): Int {
        val dataItem = dataSets.get(position)
        val type = dataItem.javaClass.hashCode()
        //如果还没包含这种类型的item，则进行添加
        if (typeArrays.indexOfKey(type) < 0){
            typeArrays.put(type,dataItem)
        }
        return type
    }

    /**
     * TODO：创建ViewHolder对象，根据viewType类型
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //1.从typeArrays中取出viewType对应的类型
        val dataItem = typeArrays.get(viewType)
        //2.获取dataItem的视图View
        var view:View? = dataItem.getItemView(parent)
        if (view == null){
            //通过布局文件获取
            val layoutRes = dataItem.getItemLayoutRes()
            if (layoutRes<0){
                //表示没有获取到,直接抛出异常
                RuntimeException("dataItem：${dataItem.javaClass.name} must override getItemView")
            }
            //不为空，通过layoutInflate加载出来
            view = mInflater!!.inflate(layoutRes,parent,false)
        }
        return createViewHolderInternal(dataItem.javaClass,view)
    }

    private fun createViewHolderInternal(
        javaClass: Class<HiDataItem<*, RecyclerView.ViewHolder>>,
        view: View?
        ): RecyclerView.ViewHolder {
        //获取HiItemData的对象
        val superclass = javaClass.genericSuperclass
        //如果是参数泛型类型
        if (superclass is ParameterizedType){
            //获取到泛型参数的集合
            val arguments =  superclass.actualTypeArguments
            //遍历集合
            for (argument in arguments){
                //如果他是一个Class对象，并且是RecyclerView的ViewHolder
                if (argument is Class<*> && RecyclerView.ViewHolder::class.java.isAssignableFrom(argument)){
                    return argument.getConstructor(View::class.java).newInstance(view) as RecyclerView.ViewHolder
                }
            }
        }
        //返回一个默认的RecyclerView的ViewHolder对象
        return object :RecyclerView.ViewHolder(view!!){}
    }

    override fun getItemCount(): Int {
        return dataSets.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val hiDataItem = dataSets.get(position)
        hiDataItem.onBindData(holder,position)
    }

    /**
     * TODO：当Adapte和RecyclerView相关联的时候会回调该方法
     *     此时去适配layoutManager的列数
     */
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val layoutManager = recyclerView.layoutManager

        if (layoutManager is GridLayoutManager){
            //获取我们指定的GridLayoutManager的列数
            val spanCount = layoutManager.spanCount
            layoutManager.spanSizeLookup = object :GridLayoutManager.SpanSizeLookup(){
                override fun getSpanSize(position: Int): Int {
                    if (position < dataSets.size){
                        val hiDataItem = dataSets.get(position)
                        val spanSize = hiDataItem.getSpanSize()
                        return if (spanSize<=0) spanCount else spanSize
                    }
                    return spanCount
                }
            }
        }
    }

    /**
     * TODO：根据ItemData来刷新对应的数据
     */
    fun refreshItem(hiDataItem: HiDataItem<*, *>) {
        //判断出入的hiDataItem是第几条，进行刷新
        val indexOf = dataSets.indexOf(hiDataItem)
        notifyItemChanged(indexOf)
    }
}