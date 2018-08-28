package com.xiasuhuei321.gankkotlin.modules

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.xiasuhuei321.gankkotlin.R
import com.xiasuhuei321.gankkotlin.base.BaseActivity
import com.xiasuhuei321.gankkotlin.base.Presenter
import com.xiasuhuei321.gankkotlin.base.View
import com.xiasuhuei321.gankkotlin.network.asyncUI
import com.xiasuhuei321.gankkotlin.network.gankService
import com.xiasuhuei321.gankkotlin.util.IntentKey

/**
 * Created by xiasuhuei321 on 2018/8/24.
 * author:luo
 * e-mail:xiasuhuei321@163.com
 */
class DailyDetailActivity : BaseActivity(), DailyView {
    private val presenter = DailyPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily)
    }

    override fun beforeInit() {
        presenter.handleIntent(intent)
    }

    override fun initView() {

    }

    override fun getPresenter(): Presenter? {
        return presenter
    }

}

class DailyAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

class DailyPresenter(var view: DailyView?) : Presenter {
    lateinit var timeStamp: String
    lateinit var time: List<String>

    fun getDaily() = asyncUI {
        gankService { getDaily(timeStamp) }.await()
    }

    override fun release() {
        view = null
    }

    fun handleIntent(intent: Intent) {
        timeStamp = intent.getStringExtra(IntentKey.TIME_STAMP)
        time = timeStamp.split("-")
    }
}

interface DailyView : View {

}