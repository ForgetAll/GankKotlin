package com.xiasuhuei321.gankkotlin.modules.infobrowser

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xiasuhuei321.gankkotlin.R
import com.xiasuhuei321.gankkotlin.base.BaseFragment
import com.xiasuhuei321.gankkotlin.base.Presenter
import com.xiasuhuei321.gankkotlin.modules.infobrowser.adapter.DateAdapter
import com.xiasuhuei321.gankkotlin.util.XLog
import kotlinx.android.synthetic.main.fragment_dateinfo.*

/**
 * Created by xiasuhuei321 on 2018/8/22.
 * author:luo
 * e-mail:xiasuhuei321@163.com
 */
class DateInfoFragment : BaseFragment(), DateInfoView {
    companion object {
        val TAG = "DateInfoFragment"
    }

    private val adapter = DateAdapter()
    private val presenter = DateInfoPresenter(this)

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_dateinfo, null)
    }

    override fun getPresenter(): Presenter? {
        return presenter
    }

    override fun initView() {
        XLog.i(TAG, "initView")
        dateRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        dateRv.adapter = adapter
    }

    override fun initEvent() {
        refreshLayout.setOnRefreshListener { presenter.refresh() }
        refreshLayout.isRefreshing = true
        presenter.refresh()
    }

    override fun setData(data: List<String>?) {
        adapter.setData(data)
    }

    override fun closeRefresh() {
        refreshLayout.isRefreshing = false
    }
}