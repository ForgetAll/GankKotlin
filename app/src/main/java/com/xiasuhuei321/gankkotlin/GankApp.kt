package com.xiasuhuei321.gankkotlin

import android.app.Application
import com.xiasuhuei321.gankkotlin.data.DataManager

/**
 * Created by xiasuhuei321 on 2018/8/24.
 * author:luo
 * e-mail:xiasuhuei321@163.com
 */
class GankApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Global.initContext(this)
        DataManager.initStore()
    }
}