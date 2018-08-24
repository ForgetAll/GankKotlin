package com.xiasuhuei321.gankkotlin.modules.girls

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.xiasuhuei321.gankkotlin.Global
import com.xiasuhuei321.gankkotlin.R
import com.xiasuhuei321.gankkotlin.base.BaseFragment
import com.xiasuhuei321.gankkotlin.base.Presenter
import com.xiasuhuei321.gankkotlin.data.Data
import com.xiasuhuei321.gankkotlin.extension.getScreenWidth
import com.xiasuhuei321.gankkotlin.network.asyncUI
import com.xiasuhuei321.gankkotlin.network.gankService
import com.xiasuhuei321.gankkotlin.util.XLog
import kotlinx.android.synthetic.main.fragment_welfare.*
import java.util.*

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
        refreshLayout.setOnRefreshListener {
            if (!refreshLayout.isRefreshing)
                presenter.refresh()
        }
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

class GirlImageAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var data: List<Data> = ArrayList()
    private var notifyCount = 10
    private var currentIndex = 0
    var fragment: Fragment? = null
    val sizeMap = HashMap<Int, Size>()

    fun setData(data: List<Data>?, startIndex: Int = currentIndex) {
        data?.let {
            this.data = it
            if (currentIndex != startIndex) currentIndex = startIndex
            XLog.i("data.size = ${data.size}")
            notifyItemRangeChanged(currentIndex, notifyCount)
            currentIndex += notifyCount
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_welfare, parent, false)
        return ImageHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (position >= data.size) return
        val h = holder as ImageHolder
        val data = data[position]
        h.timeTv.text = data.desc
        h.girlIv.setImageBitmap(null)

        val simpleTarget = object : SimpleTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap?, glideAnimation: GlideAnimation<in Bitmap>?) {
                if (sizeMap[position] == null) {
                    val width = Global.context.getScreenWidth() / 2
                    resource?.let {
                        val ratio = it.height.toFloat() / it.width.toFloat()
                        val height = width * ratio
                        sizeMap.put(position, Size(width, height.toInt()))
                    }
                }

                resizeView(position, h, resource)
            }

        }

        Glide.with(fragment).load(data.url).asBitmap().into(simpleTarget)
    }

    private fun resizeView(position: Int, holder: ImageHolder, bitmap: Bitmap?) {
        val params = holder.rootRl.layoutParams
        val size = sizeMap[position]
        size?.let {
            params.width = it.width
            params.height = it.height
        }

        holder.rootRl.layoutParams = params
        holder.girlIv.setImageBitmap(bitmap)
    }

}

data class Size(val width: Int, val height: Int)

class ImageHolder : RecyclerView.ViewHolder {
    val girlIv: ImageView
    val timeTv: TextView
    val rootRl: RelativeLayout

    constructor(itemView: View) : super(itemView) {
        girlIv = itemView.findViewById(R.id.girlIv)
        timeTv = itemView.findViewById(R.id.timeTv)
        rootRl = itemView.findViewById(R.id.rootRl)
    }
}

class WelfarePresenter(var view: WelfareView?) : Presenter {
    var data: MutableList<Data> = ArrayList()
    override fun release() {
        view = null
    }

    fun getGirls() = asyncUI {
        val res = gankService { getWelfare() }.await().body()
        if (res.isSuccess()) {
            res.results?.let {
                data.addAll(it)
                view?.setData(data)
            }
        }
        view?.closeRefresh()
    }

    fun refresh() = asyncUI {
        val res = gankService { getWelfare(1) }.await().body()
        if (res.isSuccess()) {
            res.results?.let {
                data.clear()
                data.addAll(it)
                view?.setData(data)
            }
        }
        view?.closeRefresh()
    }
}

interface WelfareView {
    fun setData(data: List<Data>?)

    fun resetData(data: List<Data>?)

    fun closeRefresh()
}