package com.xiasuhuei321.gankkotlin.extension

import android.content.Context
import android.view.WindowManager

/**
 * Created by xiasuhuei321 on 2018/8/24.
 * author:luo
 * e-mail:xiasuhuei321@163.com
 */

var width: Int = 0

fun Context.getScreenWidth(): Int {
    if (width == 0) {
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        width = windowManager.defaultDisplay.width
    }
    return width
}