package com.xiasuhuei321.gankkotlin.base.adapter

import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.util.SparseIntArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseRecyclerAdapter<T> : RecyclerView.Adapter<BaseViewHolder>() {
    private val holderLayoutList = SparseIntArray()
    private var dataSource = mutableListOf<T>()

    fun addViewResLayoutId(layoutRes: Int, type: Int) {
        holderLayoutList.put(type, layoutRes)
    }

    abstract fun getViewType(position: Int, t: T): Int

    fun setData(data: MutableList<T>) {
        this.dataSource = data
        notifyDataSetChanged()
    }

    abstract fun convert(view: View?, position: Int, data: T, viewType: Int)

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder {
        return BaseViewHolder(LayoutInflater.from(parent?.context)
                .inflate(holderLayoutList.get(viewType), parent, false))
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    override fun getItemViewType(position: Int): Int {
        if (position >= dataSource.size) return 0
        return getViewType(position, dataSource[position])
    }

    override fun onBindViewHolder(holder: BaseViewHolder?, position: Int) {
        if (position >= dataSource.size) return
        convert(holder?.itemView, position, dataSource[position], getItemViewType(position))
    }
}

class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val viewArray = SparseArray<View>()

    fun <T : View> find(id: Int): T {
        var v = viewArray.get(id)
        if (v == null) {
            v = itemView.findViewById(id)
            viewArray.put(id, v)
        }

        return v as T
    }
}

fun <T> adapterOf(convert: (View?, Int, T, Int) -> Unit,
                  initCode: BaseRecyclerAdapter<T>.() -> Unit = {},
                  getViewType: (pos: Int, data: T) -> Int = { _, _ -> 0 }) = object : BaseRecyclerAdapter<T>() {
    override fun getViewType(position: Int, t: T): Int {
        return getViewType.invoke(position, t)
    }

    override fun convert(view: View?, position: Int, data: T, viewType: Int) {
        convert.invoke(view, position, data, viewType)
    }

}.apply {
    initCode.invoke(this)
}