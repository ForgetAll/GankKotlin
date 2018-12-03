package com.xiasuhuei321.gankkotlin.modules.girls

import android.os.Bundle
import com.xiasuhuei321.gankkotlin.R
import com.xiasuhuei321.gankkotlin.base.BaseToolbarActivity

class WelfareActivity : BaseToolbarActivity() {
    override val hideActionBar: Boolean
        get() = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welfare)
        initToolbar("图片们")
    }

    override fun initView() {
        setBackBtn()
    }
}