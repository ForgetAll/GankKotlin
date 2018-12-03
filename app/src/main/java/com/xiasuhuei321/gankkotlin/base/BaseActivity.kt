package com.xiasuhuei321.gankkotlin.base

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager

/**
 * Created by xiasuhuei321 on 2018/8/16.
 * author:luo
 * e-mail:xiasuhuei321@163.com
 */
open class BaseActivity : AppCompatActivity() {
    // todo 提供一个初始化和恢复数据的方法

    open val hideActionBar = false
    // 这里初始化是比较理想的情况，如果需要自己在合适的时机初始化，但是想要统一写法，就屏蔽这里的初始化
    open val initBySelf = false

    override fun setContentView(layoutResID: Int) {
        if (hideActionBar) {
            supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        }

        super.setContentView(layoutResID)
        initStatusBar()

        if (!initBySelf) {
            beforeInit()
            initView()
            initEvent()
            initPresenter()
        }
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

    open fun beforeInit() = Unit
}