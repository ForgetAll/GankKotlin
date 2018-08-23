package com.xiasuhuei321.gankkotlin.network

import com.xiasuhuei321.gankkotlin.base.BaseActivity
import com.xiasuhuei321.gankkotlin.data.GankData
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.CoroutineStart
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import okhttp3.ResponseBody
import org.jetbrains.anko.coroutines.experimental.bg
import retrofit2.Call
import retrofit2.Response

/**
 * Created by xiasuhuei321 on 2018/8/16.
 * author:luo
 * e-mail:xiasuhuei321@163.com
 */

// 不要纠结这里的写法，向 async 函数看齐
fun <T> asyncUI(
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> T): Deferred<T> {
    val tryCatch: suspend CoroutineScope.() -> T = {
        try {
            block()
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    return async(UI, start, tryCatch)
}

inline fun <reified T> BaseActivity.gankService(crossinline request: GankService.() -> Call<GankData<T>>) = bg<Response<GankData<T>>> {
    val call = request(GankService)
    try {
        val res = call.execute()
        return@bg res
    } catch (e: Exception) {
        e.printStackTrace()
        return@bg Response.error(1001, ResponseBody.create(null, "error"))
    }
}

inline fun <reified T> gankService(crossinline request: GankService.() -> Call<GankData<T>>) = bg<Response<GankData<T>>> {
    val call = request(GankService)
    try {
        val res = call.execute()
        return@bg res
    } catch (e: Exception) {
        e.printStackTrace()
        return@bg Response.error(1001, ResponseBody.create(null, "error"))
    }
}