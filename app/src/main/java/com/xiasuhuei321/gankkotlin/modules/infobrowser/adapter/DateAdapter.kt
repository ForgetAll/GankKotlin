package com.xiasuhuei321.gankkotlin.modules.infobrowser.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.xiasuhuei321.gankkotlin.R

class DateAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var data: List<String> = ArrayList()

    fun setData(data: List<String>?) {
        data?.let {
            this.data = data
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_date, parent, false)
        return DateHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val h = holder as DateHolder
        if (position >= data.size) return
        h.dateTv.text = data[position]
        h.itemClick = { index ->
        }
    }

}

class DateHolder : RecyclerView.ViewHolder {
    val dateTv: TextView
    private val root: RelativeLayout
    var itemClick: (Int) -> Unit = {}

    constructor(itemView: View) : super(itemView) {
        dateTv = itemView.findViewById(R.id.dateTv)
        root = itemView.findViewById(R.id.rootRl)
        root.setOnClickListener {
            itemClick.invoke(layoutPosition)
        }
    }
}