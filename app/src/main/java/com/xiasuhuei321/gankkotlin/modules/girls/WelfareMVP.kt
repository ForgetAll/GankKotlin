package com.xiasuhuei321.gankkotlin.modules.girls

import android.app.Activity
import com.xiasuhuei321.gankkotlin.base.Presenter
import com.xiasuhuei321.gankkotlin.data.Data
import com.xiasuhuei321.gankkotlin.network.asyncUI
import com.xiasuhuei321.gankkotlin.network.gankService
import com.xiasuhuei321.gankkotlin.util.IntentKey
import org.jetbrains.anko.startActivity
import java.util.ArrayList

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

    fun lookBitImg(activity: Activity, position: Int) {
        activity.startActivity<WelfareActivity>(IntentKey.IMG_URL_ARRAY to data.map { it.url }.toTypedArray(),
                IntentKey.IMG_POSITION to position)
    }
}

interface WelfareView {
    fun setData(data: List<Data>?)

    fun resetData(data: List<Data>?)

    fun closeRefresh()
}