package com.xiasuhuei321.gankkotlin.modules.infobrowser

import com.xiasuhuei321.gankkotlin.base.Presenter
import com.xiasuhuei321.gankkotlin.network.asyncUI
import com.xiasuhuei321.gankkotlin.network.gankService


class DateInfoPresenter(var view: DateInfoView?) : Presenter {

    private fun getHistoryData() = asyncUI {
        val res = gankService { getHistory() }.await().body()
        if (res.isSuccess()) {
            view?.setData(res.results)
        }
        view?.closeRefresh()
    }

    fun refresh() {
        getHistoryData()
    }

    override fun release() {
        view = null
    }

}

interface DateInfoView : com.xiasuhuei321.gankkotlin.base.View {
    fun setData(data: List<String>?)

    fun closeRefresh()
}