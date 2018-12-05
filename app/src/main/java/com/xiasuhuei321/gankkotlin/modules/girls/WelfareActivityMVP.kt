package com.xiasuhuei321.gankkotlin.modules.girls

import com.xiasuhuei321.gankkotlin.base.Presenter
import com.xiasuhuei321.gankkotlin.network.asyncUI
import com.xiasuhuei321.gankkotlin.network.gankService

class WelfareActivityPresenter(var view: WelfareActivityView?) : Presenter {
    override fun release() {
        view = null
    }

    fun getGirlsByIndex(pageIndex: Int) = asyncUI {
        view?.refreshLayout()
        val res = gankService { getWelfare(pageIndex, 10) }.await().body()
        if (res.isSuccess()) {
            res.results?.let { view?.addData(it.map { it.url }) }
        }
        view?.closeRefreshLayout()
    }
}

interface WelfareActivityView {
    fun addData(data: List<String>)

    fun closeRefreshLayout()

    fun refreshLayout()
}