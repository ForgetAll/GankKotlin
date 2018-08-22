package com.xiasuhuei321.gankkotlin.base

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager

/**
 * Created by xiasuhuei321 on 2018/8/16.
 * author:luo
 * e-mail:xiasuhuei321@163.com
 */
open class BaseActivity : AppCompatActivity() {

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        initStatusBar()
        initView()
        initEvent()
        initPresenter()
    }

    private fun initStatusBar() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.parseColor("#4aa5e2")
    }

    open fun getPresenter(): Presenter? = null

    override fun onDestroy() {
        getPresenter()?.release()
        super.onDestroy()
    }

    open fun initView() = Unit

    open fun initEvent() = Unit

    open fun initPresenter() = Unit
}