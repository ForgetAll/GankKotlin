package com.xiasuhuei321.gankkotlin.base

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager

/**
 * Created by xiasuhuei321 on 2018/8/16.
 * author:luo
 * e-mail:xiasuhuei321@163.com
 */
open class BaseActivity : AppCompatActivity(){

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        initStatusBar()
        initView()
        initEvent()
    }

    protected fun initStatusBar(){
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.parseColor("#4aa5e2")
    }

    protected open fun initView() = Unit

    protected open fun initEvent() = Unit
}