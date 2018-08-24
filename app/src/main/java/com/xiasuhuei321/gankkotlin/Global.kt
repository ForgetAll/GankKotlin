package com.xiasuhuei321.gankkotlin

import android.content.Context

/**
 * Created by xiasuhuei321 on 2018/8/24.
 * author:luo
 * e-mail:xiasuhuei321@163.com
 */

// 取消静态 context 内存泄漏的警告，这里引用 application 的 context
@SuppressWarnings("all")
object Global {
    lateinit var context: Context
    fun initContext(context: Context){
        this.context = context
    }
}