package com.ych.hiui.item

import android.util.SparseArray
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

open class HiViewHolder(val view: View) : RecyclerView.ViewHolder(view), LayoutContainer {
    override val containerView: View?
        get() = view

    //通过缓存，减少findViewById。
    private var viewCache = SparseArray<View>()
    fun <T : View> findViewById(viewId: Int): T? {
        var view = viewCache.get(viewId)
        if (view == null) {
            view = itemView.findViewById<T>(viewId)
            viewCache.put(viewId, view)
        }
        return view as? T
    }
}