package com.xiasuhuei321.gankkotlin.data

/**
 * Created by xiasuhuei321 on 2018/8/22.
 * author:luo
 * e-mail:xiasuhuei321@163.com
 */
data class GankData<T>(private val error: Boolean, var results: T? = null) {
    fun isSuccess() = !error
}

data class Data(val _id: String, val createdAt: String, val desc: String,
                val publishedAt: String, val source: String, val type: String,
                val url: String, val used: Boolean, val who: String)