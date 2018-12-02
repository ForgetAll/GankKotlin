package com.xiasuhuei321.gankkotlin.modules.girls

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xiasuhuei321.gankkotlin.R
import com.xiasuhuei321.gankkotlin.base.BaseFragment
import com.xiasuhuei321.gankkotlin.base.Presenter
import com.xiasuhuei321.gankkotlin.data.Data
import com.xiasuhuei321.gankkotlin.modules.girls.adapter.GirlImageAdapter
import kotlinx.android.synthetic.main.fragment_welfare.*

/**
 * Created by xiasuhuei321 on 2018/8/16.
 * author:luo
 * e-mail:xiasuhuei321@163.com
 */
class WelfareFragment : BaseFragment(), WelfareView {
    companion object {
        val TAG = "WelfareFragment"
    }

    private val presenter = WelfarePresenter(this)
    private val adapter = GirlImageAdapter()
    private val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_welfare, null)
    }

    override fun initView() {
        imageRv.layoutManager = layoutManager
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        imageRv.adapter = adapter
        adapter.fragment = this
        imageRv.addOnScrollListener(scrollListener)

        refreshLayout.isRefreshing = true
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        var position = IntArray(2) { -1 }

        override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            val index = if (position[0] >= position[1]) position[0] else position[1]
            if (newState == RecyclerView.SCROLL_STATE_IDLE && index + 1 == recyclerView?.adapter?.itemCount) {
                if (!refreshLayout.isRefreshing) {
                    refreshLayout.isRefreshing = true
                    presenter.getGirls()
                }
            }

            layoutManager.invalidateSpanAssignments();
        }

        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            layoutManager.findLastVisibleItemPositions(position)
        }
    }

    override fun initEvent() {
        refreshLayout.setOnRefreshListener {
            presenter.refresh()
        }
        presenter.getGirls()
    }

    override fun getPresenter(): Presenter? {
        return presenter
    }

    override fun setData(data: List<Data>?) {
        adapter.setData(data)
    }

    override fun resetData(data: List<Data>?) {
        adapter.setData(data, 0)
    }

    override fun closeRefresh() {
        refreshLayout.isRefreshing = false
    }
}