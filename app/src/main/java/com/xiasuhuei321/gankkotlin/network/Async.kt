package com.xiasuhuei321.gankkotlin.network

import android.content.Context
import com.xiasuhuei321.gankkotlin.base.BaseActivity
import com.xiasuhuei321.gankkotlin.data.GankData
import com.xiasuhuei321.gankkotlin.util.XLog
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.CoroutineStart
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import okhttp3.ResponseBody
import org.jetbrains.anko.coroutines.experimental.bg
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException

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

fun <T> gankService(
        context: Context? = null,
        doSomethingInThread: (Response<GankData<T>>) -> Unit = {},
        request: GankService.() -> Call<GankData<T>>
) = bg<Response<GankData<T>>> {
    val call = request(GankService)
    try {
        val res = call.execute()
        doSomethingInThread(res)
        return@bg res
    } catch (e: Exception) {
        XLog.i("http", e.toString())
        val error = when (e) {
            is IOException -> Response.error<GankData<T>>(GankService.TIMEOUT_ERROR, ResponseBody.create(null, "IOException 可能是读写超时"))
            is UnknownHostException -> Response.error<GankData<T>>(GankService.NETWORK_ERROR, ResponseBody.create(null, "UnknownHostException 可能是网络断开"))
            is RuntimeException -> Response.error<GankData<T>>(GankService.UNKNOWN_ERROR, ResponseBody.create(null, "UnknownError：${e.message}"))
            else -> Response.error<GankData<T>>(1001, ResponseBody.create(null, "未处理的错误：$e"))
        }

        doSomethingInThread(error)
        return@bg error
    }
}