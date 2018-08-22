package com.xiasuhuei321.gankkotlin.modules.infobrowser

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.xiasuhuei321.gankkotlin.R
import com.xiasuhuei321.gankkotlin.base.BaseFragment
import com.xiasuhuei321.gankkotlin.network.asyncUI
import com.xiasuhuei321.gankkotlin.network.gankService
import com.xiasuhuei321.gankkotlin.util.XLog
import kotlinx.android.synthetic.main.fragment_dateinfo.*

/**
 * Created by xiasuhuei321 on 2018/8/22.
 * author:luo
 * e-mail:xiasuhuei321@163.com
 */
class DateInfoFragment : BaseFragment() {
    val TAG = "DateInfoFragment"
    val adapter = DateAdapter()
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_dateinfo, null)
    }

    override fun initView() {
        XLog.i(TAG, "initView")
        dateRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        dateRv.adapter = adapter
    }

    override fun initEvent() {
        asyncUI {
            val res = gankService { getHistory() }.await().body()
            if (res.isSuccess()) {
                adapter.setData(res.results)
            }
        }
    }
}

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
    }

}

class DateHolder : RecyclerView.ViewHolder {
    val dateTv: TextView

    constructor(itemView: View) : super(itemView) {
        dateTv = itemView.findViewById(R.id.dateTv)
    }
}