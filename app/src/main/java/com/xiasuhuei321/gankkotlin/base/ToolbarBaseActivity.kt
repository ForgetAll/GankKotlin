package com.xiasuhuei321.gankkotlin.base

import android.view.View

/**
 * Created by xiasuhuei321 on 2018/8/22.
 * author:luo
 * e-mail:xiasuhuei321@163.com
 */
class ToolbarBaseActivity : BaseActivity(){
    override fun setContentView(layoutResID: Int) {
        setContentView(View.inflate(this, layoutResID, null))
    }

}