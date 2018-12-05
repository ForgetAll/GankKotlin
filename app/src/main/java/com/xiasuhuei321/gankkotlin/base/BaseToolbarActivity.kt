package com.xiasuhuei321.gankkotlin.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.xiasuhuei321.gankkotlin.R
import kotlinx.android.synthetic.main.activity_base_toolbar.*

open class BaseToolbarActivity : BaseActivity() {
    override val initBySelf: Boolean
        get() = true
    override val hideActionBar: Boolean
        get() = true

    var total = 0
    var current = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.activity_base_toolbar)
        setSupportActionBar(toolbar)
    }

    override fun setContentView(layoutResID: Int) {
        val v = LayoutInflater.from(this).inflate(layoutResID, rootLl, false)
        rootLl.addView(v)
        initView()
    }

    protected fun setBackBtn() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    protected fun initToolbar(title: String) {
        supportActionBar?.title = title
    }

    protected fun initCountTv(total: Int) {
        countTv.visibility = View.VISIBLE
        this.total = total
        setCurrent(current, total)
    }

    protected fun setCurrent(current: Int, total: Int = this.total) {
        this.current = current
        countTv.text = "${current} / ${total}"
    }
}