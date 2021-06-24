package com.yc.hidebugtool

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ych.hilibrary.util.HiDisplayUtil
import kotlinx.android.synthetic.main.hi_debug_tool.*
import java.lang.reflect.Method

class DebugToolDialogFragment :AppCompatDialogFragment(){

    //通过数据类进行遍历获取类中所有方法
    private val debugTools = arrayOf(DebugTools::class.java)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val parent = dialog?.window?.findViewById<ViewGroup>(android.R.id.content)?:container
        val view = inflater.inflate(R.layout.hi_debug_tool,parent,false)
        //指定弹窗的宽高
        dialog?.window?.setLayout(
            (HiDisplayUtil.getDisplayWidthInPx(view.context) * 0.7f).toInt(),
            WindowManager.LayoutParams.WRAP_CONTENT)
        //设置背景圆角
        dialog?.window?.setBackgroundDrawableResource(R.drawable.shape_hi_debug_tool)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //设置列表项分割线
        val itemDecoration = DividerItemDecoration(view.context,DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(
            ContextCompat.getDrawable(
                view.context,
                R.drawable.shape_hi_debug_divider)!!)


        var functions = mutableListOf<DebugFunction>()
        //获取数组元素个数
        val size = debugTools.size  
        //遍历
        for (index in 0 until size){
            //获取所有类对象
            val claz = debugTools[index]
            //实例化数组每一个元素对象
            val target = claz.getConstructor().newInstance()
            //获取类中所有方法
            val declaredMethods= target.javaClass.declaredMethods
            for (method in declaredMethods){
                var title = ""
                var desc = ""
                var enable = false
                val annotation = method.getAnnotation(HiDebug::class.java)
                //包含@HiDebug注解的方法都可以被点击
                if (annotation!=null){
                    title = annotation.name
                    desc = annotation.desc
                    enable = true
                }else{
                    method.isAccessible = true   //开启访问权限
                    title = method.invoke(target) as String
                }
                val func = DebugFunction(title,desc,method,enable,target)
                functions.add(func)
            }
        }

        recycler_view.addItemDecoration(itemDecoration)
        recycler_view.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        recycler_view.adapter = DebugToolAdapter(functions)
    }

    inner class DebugToolAdapter(val list:List<DebugFunction>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            //创建item
            var itemView = layoutInflater.inflate(R.layout.hi_debug_tool_item,parent,false)
            return object :RecyclerView.ViewHolder(itemView){

            }
        }

        override fun getItemCount(): Int {
            return list.size
        }

        //绑定数据
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val debugFunction = list[position]
            val itemTitle = holder.itemView.findViewById<TextView>(R.id.item_title)
            val itemDesc = holder.itemView.findViewById<TextView>(R.id.item_desc)
            itemTitle.text = debugFunction.name
            if (TextUtils.isEmpty(debugFunction.desc)){
                itemDesc.visibility = View.GONE
            }else{
                itemDesc.visibility = View.VISIBLE
                itemDesc.text = debugFunction.desc
            }

            //是否可以点击
            if (debugFunction.enable){
                holder.itemView.setOnClickListener {
                    debugFunction.invoke()
                }
            }
        }
    }

    /**
     * name：方法名称
     * desc：方法藐视
     * method：方法的实体
     * enable：是否可以点击，有些返回字符串不需要点击
     * target：方法所在类的对象
     */
    data class DebugFunction(
        val name:String,
        val desc:String,
        val method:Method,
        val enable:Boolean,
        val target:Any) {

        //点击事件，通过反射就执行该方法
        fun invoke() {
            method.invoke(target)
        }
    }
}