package com.xiasuhuei321.gankkotlin.modules.infobrowser

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import com.xiasuhuei321.gankkotlin.BuildConfig
import com.xiasuhuei321.gankkotlin.R
import com.xiasuhuei321.gankkotlin.base.BaseActivity
import com.xiasuhuei321.gankkotlin.base.adapter.adapterOf
import kotlinx.android.synthetic.main.activity_date_info.*

class DateInfoActivity : BaseActivity() {
    override val hideActionBar: Boolean
        get() = true

    val data = mutableListOf<String>().apply {
        add("哈哈哈哈")
        add("哈哈哈哈")
        add("哈哈哈哈")
        add("哈哈哈哈")
        add("哈哈哈哈")
        add("哈哈哈哈")
        add("哈哈哈哈")
        add("哈哈哈哈")
        add("哈哈哈哈")
        add("哈哈哈哈")
        add("哈哈哈哈")
        add("哈哈哈哈")
        add("哈哈哈哈")
        add("哈哈哈哈")
        add("哈哈哈哈")
        add("哈哈哈哈")
        add("哈哈哈哈")
        add("哈哈哈哈")
        add("哈哈哈哈")
        add("哈哈哈哈")
        add("哈哈哈哈")
        add("哈哈哈哈")
        add("哈哈哈哈")
        add("哈哈哈哈")
        add("哈哈哈哈")
        add("哈哈哈哈")
        add("哈哈哈哈")
        add("哈哈哈哈")
        add("哈哈哈哈")
        add("哈哈哈哈")
        add("哈哈哈哈")
        add("哈哈哈哈")


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_info)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }

        Handler().postDelayed({
            containerApp.layoutParams.height = imgIv.height
            containerColl.layoutParams.height = imgIv.height

            containerColl.requestLayout()
        }, 500)
    }

    override fun initView() {
        infoRv.layoutManager = LinearLayoutManager(this)
        infoRv.adapter = adapterOf<String>(convert = { view, pos, s, type ->
            (view as? TextView)?.text = s
        }, initCode = {
            addViewResLayoutId(R.layout.item_ganhuo_daily_title, 0)
            setData(data)
        })
    }
}