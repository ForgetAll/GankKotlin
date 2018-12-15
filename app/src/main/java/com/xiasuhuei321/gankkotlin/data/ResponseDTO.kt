package com.xiasuhuei321.gankkotlin.data

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by xiasuhuei321 on 2018/8/22.
 * author:luo
 * e-mail:xiasuhuei321@163.com
 */
data class GankData<T>(private val error: Boolean, var results: T? = null) {
    fun isSuccess() = !error
}

@Entity
data class Data(val _id: String, val createdAt: String, val desc: String,
                val publishedAt: String, val source: String, val type: String,
                val url: String, val used: Boolean, val who: String, @Id var id: Long = 0)

// 数据比较操蛋，偷懒就这么写了
data class Daily(val Android: List<Data>, val iOS: List<Data>, val 休息视频: List<Data>, val 拓展资源: List<Data>, val 福利: List<Data>)