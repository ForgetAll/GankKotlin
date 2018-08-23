package com.xiasuhuei321.gankkotlin.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View

/**
 * Created by xiasuhuei321 on 2018/8/16.
 * author:luo
 * e-mail:xiasuhuei321@163.com
 */
open class BaseFragment : Fragment() {
    companion object {
        val TAG = "BaseFragment"
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initEvent()
    }

    protected open fun initView() = Unit

    protected open fun initEvent() = Unit

    protected open fun getPresenter(): Presenter? = null

    override fun onDestroy() {
        getPresenter()?.release()
        super.onDestroy()
    }
}